package round.category;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import round.RoundTracker;
import util.LetterFrequencies;

public class StraightCategory extends HandCategory {

    public StraightCategory(RoundTracker tracker) {
        super(tracker);
    }
    @Override
    public void addIfValid(String word) {
        if (tracker.finalRound) {
            findStraightsWithoutMissingLetter(word);
        }
        else {
            findStraightsWithMissingLetter(word);
        }
    }

    // adds a straight if ALL the letters can form a straight, minus the excluded letter
    private void findStraightsWithMissingLetter(String word) {
        // two possibilities:
        // 1) The straight is contiguous, in which case the missing letter is any of the unused letters;
        // 2) The straight is broken by one letter, which becomes the missing letter
        
        // 8-letter word (word size matches letter set size) is automatically a straight
        // letter set size of n, k-letter word
        
        // Case 1:
        //   For 8 letters and a 3-letter word, there are only 6 sets of letters to look for
        //   Iterate over all n-k+1 subsets
        //   make a letter frequency map of the word, then a frequency map of the subset, and compare them for equality
        //   must be able to differentiate "rear" from "area"

        // Case 2:
        //   Iterate over all n-k subsets
        //   Verify that they are only off by one
        //   This works even if they are contiguous... just check if the extra letter is at the start or end, then do like case 1

        if (word.length() == tracker.letters.size()) return; // no straights possible if a letter has to be removed

        Set<Character> allValidCharacters = new HashSet<>();
        Map<Character, Integer> wordFreq = LetterFrequencies.getLetterFrequency(word.toCharArray());
        Map<Character, Integer> subletterFreq = new HashMap<>(tracker.letterFreq);

        for (int i = 0; i < tracker.letters.size() - word.length(); i++) {
            // for a k-letter straight in n letters, get the sublist from 0 to k
            if (i == 0) {
                // initialize by removing k+1 through n-1
                for (int j = word.length() + 1; j < tracker.letters.size(); j++) {
                    Character c = tracker.letters.get(j);
                    subletterFreq.put(c, subletterFreq.get(c) - 1);
                    // if the removed character is not in word, add it as 0
                    if (!wordFreq.containsKey(c)) {
                        wordFreq.put(c, 0);
                    }
                }
            } else {
                // remove i-1, add i+k
                Character c = tracker.letters.get(i-1);
                subletterFreq.put(c, subletterFreq.get(c) - 1);
                c = tracker.letters.get(i + word.length());
                subletterFreq.put(c, subletterFreq.get(c) + 1);
            }
            Character missingLetter = nearlyEquals(wordFreq, subletterFreq);
            if (missingLetter != null) {
                allValidCharacters.add(missingLetter);
                if (tracker.letters.get(i).equals(missingLetter) || tracker.letters.get(i+word.length()-1).equals(missingLetter)) {
                    // add every letter before and after
                    for (int j = 0; j < i; j++) {
                        allValidCharacters.add(tracker.letters.get(j));
                    }
                    for (int j = i+word.length()-1; j < tracker.letters.size(); j++) {
                        allValidCharacters.add(tracker.letters.get(j));
                    }
                }
            }
        }
        for (Character c : allValidCharacters) {
            count.put(c, count.getOrDefault(c, 0) + 1);
            score.put(c, score.getOrDefault(c, 0) + straightScore(word.length()));
        }
    }
    
    // only check for case 1
    private void findStraightsWithoutMissingLetter(String word) {
        // Map<Character, Integer> wordFreq = LetterFrequencies.getLetterFrequency(word.toCharArray());
        // Map<Character, Integer> subletterFreq = new HashMap<>(tracker.letterFreq);

        for (int i = 0; i < tracker.letters.size() - word.length() - 1; i++) {
            // if (i == 0) {
            //     // initialize by removing 
            //     for (int j = word.length() + 1; j < tracker.letters.size(); j++) {
            //         Character c = tracker.letters.get(j);
            //         subletterFreq.put(c, subletterFreq.get(c) - 1);
            //         // if the removed character is not in word, add it as 0
            //         if (!wordFreq.containsKey(c)) {
            //             wordFreq.put(c, 0);
            //         }
            //     }
            // } else {
            //     // remove i-1, add i+word.length()-1
            //     Character c = tracker.letters.get(i-1);
            //     subletterFreq.put(c, subletterFreq.get(c) - 1);
            //     c = tracker.letters.get(i + word.length() - 1);
            //     subletterFreq.put(c, subletterFreq.get(c) + 1);
            // }
            baseCount += 1;
            baseScore += straightScore(word.length());
            return;
        }
    }

    private int straightScore(int wordLength) {
        switch (wordLength) {
            case 3:
                return 6;
            case 4:
                return 12;
            case 5:
                return 20;
            case 6:
                return 25;
            case 7:
                return 30;
            case 8:
                return 30;
        }
        return 0;
    }

    private Character nearlyEquals(Map<Character, Integer> firstMap, Map<Character, Integer> secondMap) {
        Set<Character> firstSet = firstMap.keySet();
        Set<Character> secondSet = secondMap.keySet();
        if (firstSet.size() != secondSet.size()) return null;
        Character missingLetter = null;
        for (Character c1 : firstSet) {
            int difference = Math.abs(secondMap.get(c1) - firstMap.get(c1));
            if (difference == 1) {
                if (missingLetter == null) {
                    missingLetter = c1;
                } else {
                    return null;
                }
            } else if (difference > 1) {
                return null;
            }
        }
        return missingLetter;
    }
}