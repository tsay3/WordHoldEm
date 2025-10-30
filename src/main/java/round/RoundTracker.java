package round;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import round.category.*;

// HandCategory is called and updated for every new word that is added.
// Since there is only one game being run at a time, most of these methods are static.
public class RoundTracker {
    private List<String> allWords;
    public List<Character> letters;
    // private static Map<Character, Integer> letterFrequencies;
    // letterIndices is used for the permutations function
    // we can use it for frequency counts as well
    private Map<Character, List<Integer>> letterIndices;

    // Each hand category maps possible withheld letters with points.
    // At the end of the round, these entries indicate the number of points gained
    //    depending on which letter you decide to hold.
    private ThreeCategory threeCount;
    private Map<Character, Integer> fourCount;
    private Map<Character, Integer> fivePlusCount;
    private Map<Character, Map<Character, Integer>> flushStats;
    private Map<Character, Character> bestFlush;
    private Map<Character, Integer> flushPoints;
    private Map<Character, Integer> wildPoints;
    private Map<Character, Integer> highCount;
    private Map<Character, Integer> straightPoints;

    public RoundTracker(List<Character> newLetters) {
        allWords = new ArrayList<>();
        letters = newLetters;
        letterIndices = getLetterIndexMap(newLetters);
        Set<Character> individualLetters = letterIndices.keySet();

        threeCount = new ThreeCategory(letters);
        fourCount = new HashMap<>();
        fivePlusCount = new HashMap<>();
        flushStats = new HashMap<>();
        bestFlush = new HashMap<>();
        flushPoints = new HashMap<>();
        wildPoints = new HashMap<>();
        highCount = new HashMap<>();
        straightPoints = new HashMap<>();
        bestCharacterCount = new HashMap<>();
        bestCharacterWords = new HashMap<>();

        for (Character letter : individualLetters) {
            fourCount.put(letter, 0);
            fivePlusCount.put(letter, 0);

            flushStats.put(letter, new HashMap<>());
            for (Character c : newLetters) {
                flushStats.get(letter).put(c, 0);
            }

            bestFlush.put(letter, null);
            flushPoints.put(letter, 0);
            wildPoints.put(letter, 0);
            bestCharacterWords.put(letter, new HashSet<>());
        }
    }

    public static int getLetterCount(String word, Character c) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    public int getRoundCount(Character c) {
        int count = 0;
        for (Character letter : letters) {
            if (c.charValue() == letter.charValue()) count++;
        }
        return count;
    }

    private Map<Character, List<Integer>>
                getLetterIndexMap(List<Character> letters) {
        Map<Character, List<Integer>> letterMap = new HashMap<>();
        for (int i = 0; i < letters.size(); i++) {
            Character letter = letters.get(i);
            if (!letterMap.containsKey(letter)) {
                letterMap.put(letter, new ArrayList<>());
            }
            letterMap.get(letter).add(i);
        }
        return letterMap;
    }
    private Map<Character, Integer> getLetterFrequency(char[] letters) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (Character letter : letters) {
            frequencies.put(letter, frequencies.getOrDefault(letter, 0) + 1);
        }
        return frequencies;
    }

    public void addWord(String word){
        allWords.add(word);
        calculateLengthsAndWilds(word);
        calculateFlushes(word);
        addIfStraight(word);
        calculateHighCard(word);
        updateHighestCharacterList(word);
    }

    private void calculateHighCard(String word) {
        Set<Character> foundLetters = new HashSet<>();
        for (Character character : word.toCharArray()) {
            foundLetters.add(character);
        }
        for (Character character : foundLetters) {
            highCount.put(character, highCount.getOrDefault(character, 0)+1);
        }
    }

    // highestcharacter will add 1 for every letter in the word, so long as all letters of one type are not used
    private Map<Character, Integer> bestCharacterCount;
    private Map<Character, Character> bestCharacter;
    private Map<Character, Set<String>> bestCharacterWords;

    // The challenge: we need to know, for any character we remove, which other character shows up most often in the word list.
    // The solution: a map between every character and a set containing all acceptable words without that extra character
    //      This map is updated with every new word.
    //      When time is up, we find the letter that appeared in the most words in each set, along with their count

    private void updateHighestCharacterList(String word) {
        Map<Character, Integer> wordFreqs = getLetterFrequency(word.toCharArray());
        for (Map.Entry<Character, Integer> entry : wordFreqs.entrySet()) {
            Character character = entry.getKey();
            if (!Objects.equals(letterIndices.get(character).size(), entry.getValue())) {
                // this word does not use up all of the letters, so we can add the word
                bestCharacterWords.get(character).add(word);
            }
        }
    }

    private void updateHighestCharacterCounts() {
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

    public Character getHighestCharacter(Character c) {
        if (bestCharacter.isEmpty()) updateHighestCharacterCounts();
        return bestCharacter.get(c);
    }

    public Integer getHighestCharacterCount(Character c) {
        if (bestCharacterCount.isEmpty()) updateHighestCharacterCounts();
        return bestCharacterCount.get(c);
    }

    public Integer getHighestCharacterPoints(Character c) {
        int pointsPerChar = 3;
        if (bestCharacter.isEmpty()) {
            updateHighestCharacterCounts();
        }
        return bestCharacterCount.get(c) * pointsPerChar;
    }

    public Integer getThreeCount(Character c) {
        return threeCount.getCount(c);
    }

    public Integer getThreeScore(Character c) {
        return threeCount.getScore(c);
    }

    public Integer getFourCount(Character c) {
        return fourCount.get(c);
    }

    public Integer getFourScore(Character c) {
        if (fourCount.get(c) >= 10) {
            return fourCount.get(c) * 6 + 30;
        }
        return fourCount.get(c) * 6;
    }

    public Integer getFivePlusCount(Character c) {
        return fivePlusCount.get(c);
    }

    public Integer getFivePlusScore(Character c) {
        if (fivePlusCount.get(c) >= 10) {
            return fivePlusCount.get(c) * 8 + 100;
        }
        return fivePlusCount.get(c) * 8;
    }

    private void calculateLengthsAndWilds(String word) {
        Map<Character, Integer> wordFreqs = getLetterFrequency(word.toCharArray());
        threeCount.addIfValid(word);
        for (Character c : wordFreqs.keySet()) {
            if (letterIndices.get(c).size() > wordFreqs.get(c)) {
                if (word.length() >= 5) {
                    fivePlusCount.put(c, fivePlusCount.get(c) + 1);
                    switch (word.length()) {
                        case 8:
                            wildPoints.put(c, wildPoints.get(c) + 15);
                            break;
                        case 7:
                            wildPoints.put(c, wildPoints.get(c) + 10);
                            break;
                        case 6:
                            wildPoints.put(c, wildPoints.get(c) + 7);
                            break;
                        case 5:
                            wildPoints.put(c, wildPoints.get(c) + 5);
                            break;
                        default:
                            break;
                    }
                } else if (word.length() == 4) {
                    fourCount.put(c, fourCount.get(c) + 1);
                    wildPoints.put(c, wildPoints.get(c) + 2);
                } else if (word.length() == 3) {
                    wildPoints.put(c, wildPoints.get(c) + 1);
                }
            }
        }
    }

    public Integer getWordCount(Character c) {
        // count all words, unless they have every letter of type c
        int maxAmount = letterIndices.get(c).size() - 1;
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

    public Integer getWildPoints(Character c) {
        return wildPoints.get(c);
    }

    public Integer getFullHouseScore(Character c) {
        if (allWords.size() >= 25) {
            return 150;
        } else if (allWords.size() >= 15) {
            return 50;
        }
        return 0;
    }

    public Character getBestFlush(Character c) {
        return bestFlush.get(c);
    }

    public Integer getBestFlushCount(Character c) {
        return flushStats.get(c).get(bestFlush.get(c));
    }

    public Integer getBestFlushScore(Character c) {
        return 10 * flushStats.get(c).get(bestFlush.get(c));
    }

    // compute the best flush for every possible letter that could be removed
    private void calculateFlushes(String word) {
        Map<Character, Integer> wordFreqs = getLetterFrequency(word.toCharArray());
        for (Character c : letterIndices.keySet()) {
            Integer totalOccurrences = letterIndices.get(c).size();
            Integer wordOccurrences = wordFreqs.getOrDefault(c, 0);
            // ensure that removing this letter doesn't eliminate the word
            if (totalOccurrences > wordOccurrences) {
                Character first = word.charAt(0);
                flushStats.get(c).put(first, flushStats.get(c).get(first) + 1);
                // best flush for each character
                if (bestFlush != null && bestFlush.get(c) != null) {
                    Integer firstLetterCount = flushStats.get(c).get(first);
                    Integer previousBestLetterCount = flushStats.get(c).get(bestFlush.get(c));
                    if (firstLetterCount >= previousBestLetterCount) {
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
    private void addIfStraight(String word) {
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
        int straightIfDeleted = 0;   // an int as a boolean array
        for (List<Integer> selection : possibleSelections) {
            Collections.sort(selection);
            // the integers will all be different and in order,
            // so check if the length of the array equals
            // the difference in the first and last elements.
            if (selection.get(selection.size()-1) - selection.get(0) == selection.size() - 1) {
                for (int i = 0; i < selection.get(0); i++) {
                    straightIfDeleted |= 0x1 << i;
                }
                for (int i = selection.size(); i < letters.size(); i++) {
                    straightIfDeleted |= 0x1 << i;
                }
            }
        }
        for (int i = 0; i < letters.size(); i++) {
            if ((straightIfDeleted & (0x1 << i)) != 0) {
                addToStraightScore(letters.get(i), word.length());
            }
        }
    }

    private void addToStraightScore(Character c, int length) {
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

    public Integer getStraightPoints(Character c) {
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
