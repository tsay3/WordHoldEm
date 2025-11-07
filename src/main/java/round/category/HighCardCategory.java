package round.category;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import round.RoundTracker;

public class HighCardCategory extends HandCategory {

    // The challenge: we need to know, for any character we remove, which other character shows up most often in the word list.
    // The solution: a map between every character and a set containing all acceptable words without that extra character
    //      This map is updated with every new word.
    //      When time is up, we find the letter that appeared in the most words in each set, along with their count
    
    // essentially, each letter has its own list of words that are valid in its absence
    // the high card tabulates the letter counts for each new word
    // then it looks through for the highest count for each letter
    private final Map<Character, Map<Character, Integer>> highCounts;
    private final Map<Character, Character> bestCharacters;

    public HighCardCategory(RoundTracker tracker) {
        super(tracker);
        highCounts = new HashMap<>();
        bestCharacters = new HashMap<>();
        for (Character c : tracker.letters) {
            highCounts.put(c, new HashMap<>());
            
        }
    }

    // if there are duplicate letters, only track the first one
    // we're seeing how many INDIVIDUAL letters are used the most
    @Override
    public void addIfValid(String word) {
        Set<Character> validLetters = new HashSet<>(tracker.letters);
        Set<Character> usedLetters = new HashSet<>();
        for (Character c : validLetters) {
            if (wordValidAbsentLetter(word, c)) {
                for (char letter : word.toCharArray()) {
                    if (!usedLetters.contains(letter)) {
                        usedLetters.add(letter);
                        highCounts.get(c).put(letter, highCounts.get(c).getOrDefault(letter, 0) + 1);
                    }
                }
            }
            updateCharacters(c);
        }
    }

    private void updateCharacters(Character c) {
        Integer highest = 0;
        Map<Character, Integer> currentMap = highCounts.get(c);
        for (Map.Entry<Character, Integer> entry: currentMap.entrySet()) {
            Integer entryCount = entry.getValue();
            if (entryCount > highest) {
                bestCharacters.put(c, entry.getKey());
                highest = entryCount;
            }
        }
    }

    public Character getBestCharacter(Character c) {
        return bestCharacters.get(c);
    }

    @Override
    public Integer getCount(Character c) {
        return highCounts.get(c).getOrDefault(bestCharacters.get(c), 0);
    }

    @Override
    public Integer getScore(Character c) {
        int pointsPerChar = 3;
        return pointsPerChar * getCount(c);
    }
}