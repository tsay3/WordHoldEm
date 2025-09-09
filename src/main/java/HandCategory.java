import java.util.*;

public class HandCategory {
    private static List<String> allWords;
    private static List<Character> letters;
    private static Map<Character, Integer> letterFrequencies;
    private static Map<Character, List<Integer>> letterIndices;

    // Each hand category maps possible withheld letters with points.
    // At the end of the round, these entries indicate the number of points gained
    //    depending on which letter you decide to hold.
    private static Map<Character, Integer> threeCount;
    private static Map<Character, Integer> fourCount;
    private static Map<Character, Integer> fivePlusCount;
    private static Map<Character, Map<Character, Integer>> flushCount;
    private static Map<Character, Character> bestFlush;
    private static Map<Character, Integer> wildPoints;
    private static Map<Character, Integer> highCount;
    private static Map<Character, Integer> straightPoints;
//    private static List<Integer> allStraights;

    public static void beginRound(List<Character> newLetters) {
        allWords = new ArrayList<>();
        letters = newLetters;
        char[] letterArray = new char[newLetters.size()];
        for (int i = 0; i < newLetters.size(); i++) {
            letterArray[i] = newLetters.get(i);
        }
        letterFrequencies = getLetterFrequency(letterArray);
        letterIndices = getLetterIndexMap(newLetters);
        Set<Character> individualLetters = letterIndices.keySet();
        threeCount = new HashMap<>();
        fourCount = new HashMap<>();
        fivePlusCount = new HashMap<>();
        flushCount = new HashMap<>();
        bestFlush = new HashMap<>();
        wildPoints = new HashMap<>();
        highCount = new HashMap<>();
        straightPoints = new HashMap<>();
//        allStraights = new ArrayList<>();
//        System.out.println(letterIndices);
        bestCharacterCount = new HashMap<>();
        bestCharacterWords = new HashMap<>();
        for (Character letter : individualLetters) {
            threeCount.put(letter, 0);
            fourCount.put(letter, 0);
            fivePlusCount.put(letter, 0);

            flushCount.put(letter, new HashMap<>());
            for (Character c : newLetters) {
                flushCount.get(letter).put(c, 0);
            }
            bestFlush.put(letter, null);
            wildPoints.put(letter, 0);
            bestCharacterWords.put(letter, new HashSet<>());
        }
    }
    private static Map<Character, List<Integer>>
                getLetterIndexMap(List<Character> letters) {
        Map<Character, List<Integer>> letterIndices = new HashMap<>();
        for (int i = 0; i < letters.size(); i++) {
            Character letter = letters.get(i);
            if (!letterIndices.containsKey(letter)) {
                letterIndices.put(letter, new ArrayList<>());
            }
            letterIndices.get(letter).add(i);
        }
        return letterIndices;
    }
    private static Map<Character, Integer> getLetterFrequency(char[] letters) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (Character letter : letters) {
            frequencies.put(letter, frequencies.getOrDefault(letter, 0) + 1);
        }
        return frequencies;
    }

    public static void addWord(String word){
        allWords.add(word);
        calculateLengthsAndWilds(word);
        calculateFlushes(word);
        addIfStraight(word);
        calculateHighCard(word);
        updateHighestCharacterList(word);
    }

    private static void calculateHighCard(String word) {
        Set<Character> foundLetters = new HashSet<>();
        for (Character character : word.toCharArray()) {
            foundLetters.add(character);
        }
        for (Character character : foundLetters) {
            highCount.put(character, highCount.getOrDefault(character, 0)+1);
        }
    }

    // highestcharacter will add 1 for every letter in the word, so long as all letters of one type are not used
    // private static Map<Character, Character> bestCharacter;
    private static Map<Character, Integer> bestCharacterCount;
    private static Map<Character, Character> bestCharacter;
    private static Map<Character, Set<String>> bestCharacterWords;

    // The challenge: we need to know, for any character we remove, which other character shows up most often in the word list.
    // The solution: a map between every character and a set containing all acceptable words without that extra character
    //      This map is updated with every new word.
    //      When time is up, we find the letter that appeared in the most words in each set, along with their count

    private static void updateHighestCharacterList(String word) {
        Map<Character, Integer> wordFreqs = getLetterFrequency(word.toCharArray());
        for (Map.Entry<Character, Integer> entry : wordFreqs.entrySet()) {
            Character character = entry.getKey();
            if (letterFrequencies.get(character) != entry.getValue()) {
                // this word does not use up all of the letters, so we can add the word
                bestCharacterWords.get(character).add(word);
            }
        }
    }

    private static void updateHighestCharacterCounts() {
        for (Map.Entry<Character, Set<String>> entry : bestCharacterWords.entrySet()) {
            Character c = entry.getKey();
            Set<String> words = entry.getValue();

            // initialize letter count
            Map<Character, Integer> letterCount = new HashMap<>();
            for (Character i : letters) {
                if (!letterCount.containsKey(i)) {
                    letterCount.put(i, 0);
                }
            }

            // all word lists have already had a character removed, so we don't have to remove another character
            // just count each unique letter per word
            for (String word : words) {
                Map<Character, Boolean> foundYet = new HashMap<>();
                for (int i = 0; i < word.length(); i++) {
                    Character c2 = word.charAt(i);
                    if (!foundYet.containsKey(c2)) {
                        foundYet.put(c2, true);
                        letterCount.put(c2, letterCount.get(c2) + 1);
                    }
                }
            }

            // get the highest count
            int highest = 0;
            Character bestChar = null;
            for (Map.Entry<Character, Integer> letterFreq : letterCount.entrySet()) {
                if (letterFreq.getValue() > highest) {
                    bestChar = letterFreq.getKey();
                }
            }

            // update the values
            bestCharacter.put(c, bestChar);
            bestCharacterCount.put(c, highest);
        }
    }

    public static Character getHighestCharacter(Character c) {
        if (bestCharacter.size() == 0) updateHighestCharacterCounts();
        return bestCharacter.get(c);
    }

    public static Integer getHighestCharacterCount(Character c) {
        if (bestCharacterCount.size() == 0) updateHighestCharacterCounts();
        return bestCharacterCount.get(c);
    }

    public static Integer getHighestCharacterPoints(Character c) {
        int pointsPerChar = 3;
        if (bestCharacter.size() == 0) {
            updateHighestCharacterCounts();
        }
        return bestCharacterCount.get(c) * pointsPerChar;
    }

    public static Integer getThreeCount(Character c) {
        return threeCount.get(c);
    }

    public static Integer getThreeScore(Character c) {
        return threeCount.get(c) * 4;
    }

    public static Integer getFourCount(Character c) {
        return fourCount.get(c);
    }

    public static Integer getFourScore(Character c) {
        if (fourCount.get(c) >= 10) {
            return fourCount.get(c) * 6 + 30;
        }
        return fourCount.get(c) * 6;
    }

    public static Integer getFivePlusCount(Character c) {
        return fivePlusCount.get(c);
    }

    public static Integer getFivePlusScore(Character c) {
        if (fivePlusCount.get(c) >= 10) {
            return fivePlusCount.get(c) * 8 + 100;
        }
        return fivePlusCount.get(c) * 8;
    }

    private static void calculateLengthsAndWilds(String word) {
        Map<Character, Integer> wordFreqs = getLetterFrequency(word.toCharArray());
        for (Character c : wordFreqs.keySet()) {
            if (letterFrequencies.get(c) > wordFreqs.get(c)) {
                if (word.length() >= 5) {
                    fivePlusCount.put(c, fivePlusCount.get(c) + 1);
                    if (word.length() == 8) {
                        wildPoints.put(c, wildPoints.get(c) + 15);
                    } else if (word.length() == 7) {
                        wildPoints.put(c, wildPoints.get(c) + 10);
                    } else if (word.length() == 6) {
                        wildPoints.put(c, wildPoints.get(c) + 7);
                    } else if (word.length() == 5) {
                        wildPoints.put(c, wildPoints.get(c) + 5);
                    }
                } else if (word.length() == 4) {
                    fourCount.put(c, fourCount.get(c) + 1);
                    wildPoints.put(c, wildPoints.get(c) + 2);
                } else if (word.length() == 3) {
                    threeCount.put(c, threeCount.get(c) + 1);
                    wildPoints.put(c, wildPoints.get(c) + 1);
                }
            }
        }
    }

    public static Integer getWordCount(Character c) {
        // count all words, unless they have every letter of type c
        int maxAmount = letterFrequencies.get(c) - 1;
        int count = 0;
        for (String word : allWords) {
            int letterCount = 0;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == c) letterCount++;
            }
            if (letterCount <= maxAmount) count++;
        }
        return count;
    }

    public static Integer getWildPoints(Character c) {
        return wildPoints.get(c);
    }

    public static Integer getFullHouseScore(Character c) {
        if (allWords.size() >= 25) {
            return 150;
        } else if (allWords.size() >= 15) {
            return 50;
        }
        return 0;
    }

    public static Character getBestFlush(Character c) {
        return bestFlush.get(c);
    }

    public static Integer getBestFlushCount(Character c) {
        return flushCount.get(c).get(bestFlush.get(c));
    }

    public static Integer getBestFlushScore(Character c) {
        return 10 * flushCount.get(c).get(bestFlush.get(c));
    }

    private static void calculateFlushes(String word) {
        Map<Character, Integer> wordFreqs = getLetterFrequency(word.toCharArray());
        for (Character c : letterFrequencies.keySet()) {
            if (letterFrequencies.get(c) > wordFreqs.get(c)) {
                Character first = word.charAt(0);
                flushCount.get(c).put(first, flushCount.get(c).get(first) + 1);
                if (bestFlush != null) {
                    if (flushCount.get(c).get(first) >= flushCount.get(c).get(bestFlush.get(c))) {
                        bestFlush.put(c, first);
                    }
                } else {
                    bestFlush.put(c, first);
                }
            }
        }
    }

    // add if straight does something different;
    // it ONLY adds a straight if ALL the letters can form a straight
    private static void addIfStraight(String word) {
        // Map<Character, Integer> wordFreqs = getLetterFrequency(word.toCharArray());
        // build map for word
        // order is not important for a word, only which letters were used and how many
        Map<Character, Integer> letterCount = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            Character letter = word.charAt(i);
            if (letterCount.containsKey(letter)) {
                letterCount.put(letter, letterCount.get(letter) + 1);
            } else {
                letterCount.put(letter, 1);
            }
        }
        // now that we have a frequency count for each letter in the word,
        // we build an array for every permutation from the letter indices.
        // For example, if there are 4 copies of a letter, and our word uses 2,
        // we need to create 6 arrays just for these letters.
        // That means we go through the letterCount map
        List<List<Integer>> possibleSelections = new ArrayList<>(new ArrayList<>());
        for (Character letter : letterCount.keySet()) {
            possibleSelections = expandPermutations(possibleSelections,
                    letterIndices.get(letter), letterCount.get(letter));
        }
        // now check if at least one combination has all the characters in order
        List<Boolean> straightIfDeleted = new ArrayList<>();
        for (int i = 0; i < letters.size(); i++) {
            straightIfDeleted.add(false);
        }
        Iterator<List<Integer>> selectionIter = possibleSelections.iterator();
        while (selectionIter.hasNext()) {
            List<Integer> selection = selectionIter.next();
            Collections.sort(selection);
            // the integers will all be different and in order,
            // so check if the length of the array equals
            // the difference in the first and last elements.
            if (selection.get(selection.size()-1) - selection.get(0) == selection.size() - 1) {
                for (int i = 0; i < selection.get(0); i++) {
                    straightIfDeleted.set(i, true);
                }
                for (int i = selection.size(); i < letters.size(); i++) {
                    straightIfDeleted.set(i, true);
                }
            }
        }
        for (int i = 0; i < letters.size(); i++) {
            if (straightIfDeleted.get(i)) {
                addToStraightScore(letters.get(i), word.length());
            }
        }
    }

    private static void addToStraightScore(Character c, int length) {
        switch(length) {
            case 3:
                straightPoints.put(c, straightPoints.getOrDefault(c,0) + 4);
                break;
            case 4:
                straightPoints.put(c, straightPoints.getOrDefault(c,0) + 8);
                break;
            case 5:
                straightPoints.put(c, straightPoints.getOrDefault(c,0) + 16);
                break;
            case 6:
                straightPoints.put(c, straightPoints.getOrDefault(c,0) + 25);
                break;
            case 7:
            case 8:
                straightPoints.put(c, straightPoints.getOrDefault(c,0) + 30);
                break;

            default:
        }
    }

    public static Integer getStraightPoints(Character c) {
        return straightPoints.getOrDefault(c, 0);
    }

    // This handles the generic code for creating permutations
    // of the current "whole" list with new additions in "part" list.
    public static List<List<Integer>> expandPermutations(
            List<List<Integer>> whole, List<Integer> part, Integer draws) {
        if (draws > part.size() || draws < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (draws == 0) return whole;
        // create an array of arrays to contain every possible draw
        List<List<Integer>> expandedWhole = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        int lastMovedIndex = draws - 1;
        // initialize indices
        for (int i = 0; i < draws; i++) {
            indices.add(i);
        }
        if (whole.isEmpty()) {
            whole.add(new ArrayList<>());
        }
        // append indices references to the whole, add to a larger array
        for (List<Integer> oneList : whole) {
            List<Integer> addToNewWhole = new ArrayList<>(oneList);
            for (Integer integer : indices) {
                addToNewWhole.add(part.get(integer));
            }
            expandedWhole.add(addToNewWhole);
        }
        // basic algorithm: there are n values in the indices
        //   each representing an index of the part array.
        // each step, see if the lastMovedIndex can be increased
        //    If yes, increase the indices's value in its index
        //    If not, decrease the index, and repeat
        while (lastMovedIndex >= 0) {
            while (canMove(lastMovedIndex, indices, part.size())) {
                indices.set(lastMovedIndex, indices.get(lastMovedIndex) + 1);
                for (int i = lastMovedIndex + 1; i < indices.size(); i++) {
                    indices.set(i, indices.get(i-1) + 1);
                }
                // append indices references to the whole, add to a larger array
                for (List<Integer> oneList : whole) {
                    List<Integer> addToNewWhole = new ArrayList<>(oneList);
                    for (Integer integer : indices) {
                        addToNewWhole.add(part.get(integer));
                    }
                    expandedWhole.add(addToNewWhole);
                }
            }
            lastMovedIndex--;
        }
        return expandedWhole;
    }

    // index: the index of indexArray in question we are trying to move
    // indexArray: the indices as they currently are
    // maxSize: the maximum index that is allowed
    //
    // This function determines if we can increase the index further without conflicting
    // with either other indices, or with the maximum value
    private static boolean canMove(int index, List<Integer> indexArray, int maxSize) {
        if (index < 0) return false;
        if (indexArray.get(index) >= maxSize - 1) return false;
        if (index == indexArray.size() - 1) return true; // if the last item of the array
        return (indexArray.get(index) + 1) != indexArray.get(index + 1);
    }

}
