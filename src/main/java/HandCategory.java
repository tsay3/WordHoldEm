import java.util.*;

public class HandCategory {

    private static List<String> allWords;
    private static List<Character> letters;
    private static Integer threeCount;
    private static Integer fourCount;
    private static Integer fivePlusCount;
    private static Map<Character, Integer> flushCount;
    private static Character bestFlush;
    private static Integer wildPoints;
    private static Map<Character, Integer> highCount;
    private static Integer straightPoints;
//    private static List<Integer> allStraights;

    public static void beginRound(List<Character> newLetters) {
        allWords = new ArrayList<>();
        letters = newLetters;
        threeCount = 0;
        fourCount = 0;
        fivePlusCount = 0;
        flushCount = new HashMap<>();
        bestFlush = null;
        wildPoints = 0;
        highCount = new HashMap<>();
        straightPoints = 0;
//        allStraights = new ArrayList<>();

        for (Character c : newLetters) {
            flushCount.put(c, 0);
        }
        createLetterMap();
//        System.out.println(letterIndices);
    }
    private static Map<Character, List<Integer>> letterIndices;
    private static void createLetterMap() {
        letterIndices = new HashMap<>();
        for (int i = 0; i < letters.size(); i++) {
            Character letter = letters.get(i);
            if (!letterIndices.containsKey(letter)) {
                letterIndices.put(letter, new ArrayList<>());
            }
            letterIndices.get(letter).add(i);
        }
        System.out.println("Letters: " + letters);
    }

    public static void addWord(String word){
//        System.out.println("Adding word:  " + word);
        allWords.add(word);
        calculateLengthsAndWilds(word);
        calculateFlushes(word);
//        findStraights(word);
        addIfStraight(word);
        calculateHighCard(word);
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

    private static Character bestCharacter = '-';
    private static Integer bestCount = 0;
    private static void setHighestCharacterAndCount() {
        bestCharacter = '-';
        bestCount = 0;
        for (Character character : highCount.keySet()) {
            if (highCount.get(character) > bestCount) {
                bestCharacter = character;
                bestCount = highCount.get(character);
            }
        }
    }

    public static Character getHighestCharacter() {
        if (bestCharacter == '-') setHighestCharacterAndCount();
        return bestCharacter;
    }

    public static Integer getHighestCharacterCount() {
        if (bestCharacter == '-') setHighestCharacterAndCount();
        return bestCount;
    }

    public static Integer getHighestCharacterPoints() {
        int pointsPerChar = 3;
        if (bestCharacter == '-') {
            setHighestCharacterAndCount();
        }
        return bestCount * pointsPerChar;
    }

    public static Integer getThreeCount() {
        return threeCount;
    }

    public static Integer getThreeScore() {
        return threeCount * 4;
    }

    public static Integer getFourCount() {
        return fourCount;
    }

    public static Integer getFourScore() {
        if (fourCount >= 10) {
            return fourCount * 6 + 30;
        }
        return fourCount * 6;
    }

    public static Integer getFivePlusCount() {
        return fivePlusCount;
    }

    public static Integer getFivePlusScore() {
        if (fivePlusCount >= 10) {
            return fivePlusCount * 8 + 100;
        }
        return fivePlusCount * 8;
    }

    private static void calculateLengthsAndWilds(String word) {
        if (word.length() >= 5) {
            fivePlusCount++;
            if (word.length() == 8) {
                wildPoints += 15;
            } else if (word.length() == 7) {
                wildPoints += 10;
            } else if (word.length() == 6) {
                wildPoints += 7;
            } else if (word.length() == 5) {
                wildPoints += 5;
            }
        } else if (word.length() == 4) {
            fourCount++;
            wildPoints += 2;
        } else if (word.length() == 3) {
            threeCount++;
            wildPoints++;
        }
    }

    public static Integer getWordCount() {
        return allWords.size();
    }

    public static Integer getWildPoints() {
        return wildPoints;
    }

    public static Integer getFullHouseScore() {
        if (allWords.size() >= 25) {
            return 150;
        } else if (allWords.size() >= 15) {
            return 50;
        }
        return 0;
    }

    public static Character getBestFlush() {
        return bestFlush;
    }

    public static Integer getBestFlushCount() {
        return flushCount.get(bestFlush);
    }

    public static Integer getBestFlushScore() {
        return 10 * flushCount.get(bestFlush);
    }

    private static void calculateFlushes(String word) {
        Character first = word.charAt(0);
        flushCount.put(first, flushCount.get(first) + 1);
        if (bestFlush != null) {
            if (flushCount.get(first) >= flushCount.get(bestFlush)) {
                bestFlush = first;
            }
        } else {
            bestFlush = first;
        }
    }

    // add if straight does something different;
    // it ONLY adds a straight if ALL the letters can form a straight
    private static void addIfStraight(String word) {
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
        // now check if at least
        boolean notFound = true;
        Iterator<List<Integer>> selectionIter = possibleSelections.iterator();
        while (notFound && selectionIter.hasNext()) {
            List<Integer> selection = selectionIter.next();
            Collections.sort(selection);
            if (selection.get(selection.size()-1) - selection.get(0) == selection.size() - 1) {
                notFound = false;
            }
        }
        if (!notFound) {
            addToStraightScore(word.length());
        }
    }

    private static void addToStraightScore(int length) {
        switch(length) {
            case 3:
                straightPoints += 4;
                break;
            case 4:
                straightPoints += 8;
                break;
            case 5:
                straightPoints += 16;
                break;
            case 6:
                straightPoints += 25;
                break;
            case 7:
            case 8:
                straightPoints += 30;
                break;

            default:
        }
    }

    public static Integer getStraightPoints() {
        return straightPoints;
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
