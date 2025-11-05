package round;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import round.category.FiveCategory;
import round.category.FlushCategory;
import round.category.FourCategory;
import round.category.ThreeCategory;
import round.category.WildCategory;
import util.Combinations;
import util.LetterFrequencies;

// HandCategory is called and updated for every new word that is added.
// Since there is only one game being run at a time, most of these methods are static.
public class RoundTracker {
    private final List<String> allWords;
    public boolean finalRound;
    public List<Character> letters;
    public Map<Character, Integer> letterFreq;
    // private static Map<Character, Integer> letterFrequencies;
    // letterIndices is used for the permutations function
    // we can use it for frequency counts as well
    private final Map<Character, List<Integer>> letterIndices;

    // Each hand category maps possible withheld letters with points.
    // At the end of the round, these entries indicate the number of points gained
    //    depending on which letter you decide to hold.
    private final ThreeCategory threeCategory;
    private final FourCategory fourCategory;
    private final FiveCategory fivePlusCategory;
    private final FlushCategory flushCategory;
    private final WildCategory wildCategory;
    private final Map<Character, Integer> highCount;
    private final Map<Character, Integer> straightPoints;

    public RoundTracker(List<Character> newLetters) {
        finalRound = false;
        allWords = new ArrayList<>();
        letters = newLetters;
        letterIndices = getLetterIndexMap(newLetters);
        letterFreq = new HashMap<>();
        for (Character letter : newLetters) {
            letterFreq.put(letter, letterFreq.getOrDefault(letter, 0) + 1);
        }

        threeCategory = new ThreeCategory(this);
        fourCategory = new FourCategory(this);
        fivePlusCategory = new FiveCategory(this);
        flushCategory = new FlushCategory(this);
        wildCategory = new WildCategory(this);
        highCount = new HashMap<>();
        straightPoints = new HashMap<>();
        bestCharacterCount = new HashMap<>();
        bestCharacterWords = new HashMap<>();
    }

    public static int getLetterCountForWord(String word, Character c) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    public int getLetterCountForRound(Character c) {
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
    private final Map<Character, Integer> bestCharacterCount;
    private Map<Character, Character> bestCharacter;
    private final Map<Character, Set<String>> bestCharacterWords;

    // The challenge: we need to know, for any character we remove, which other character shows up most often in the word list.
    // The solution: a map between every character and a set containing all acceptable words without that extra character
    //      This map is updated with every new word.
    //      When time is up, we find the letter that appeared in the most words in each set, along with their count

    private void updateHighestCharacterList(String word) {
        Map<Character, Integer> wordFreqs = LetterFrequencies.getLetterFrequency(word.toCharArray());
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
        return threeCategory.getCount(c);
    }

    public Integer getThreeScore(Character c) {
        return threeCategory.getScore(c);
    }

    public Integer getFourCount(Character c) {
        return fourCategory.getCount(c);
    }

    public Integer getFourScore(Character c) {
        return fourCategory.getScore(c);
    }

    public Integer getFivePlusCount(Character c) {
        return fivePlusCategory.getCount(c);
    }

    public Integer getFivePlusScore(Character c) {
        return fivePlusCategory.getScore(c);
    }

    private void calculateLengthsAndWilds(String word) {
        threeCategory.addIfValid(word);
        fourCategory.addIfValid(word);
        fivePlusCategory.addIfValid(word);
        wildCategory.addIfValid(word);
    }

    public Integer getWordCount(Character c) {
        // // count all words, unless they have every letter of type c
        // int maxAmount = letterIndices.get(c).size() - 1;
        // int count = 0;
        // for (String word : allWords) {
        //     int letterCount = 0;
        //     for (int i = 0; i < word.length(); i++) {
        //         if (word.charAt(i) == c) letterCount++;
        //     }
        //     if (letterCount <= maxAmount) count++;
        // }
        // return count;
        return wildCategory.getCount(c);
    }

    public Integer getWildPoints(Character c) {
        return wildCategory.getScore(c);
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
        return flushCategory.getBestCharacter(c);
    }

    public Integer getBestFlushCount(Character c) {
        return flushCategory.getCount(c);
    }

    public Integer getBestFlushScore(Character c) {
        return flushCategory.getScore(c);
    }

    // compute the best flush for every possible letter that could be removed
    private void calculateFlushes(String word) {
        flushCategory.addIfValid(word);
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
            possibleSelections = Combinations.expandPermutations(possibleSelections,
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

}
