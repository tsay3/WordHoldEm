package round.category;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import round.RoundTracker;

public class HighCardCategory extends HandCategory {
    public final static int POINTS_PER_CHAR = 4;

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
            for (Character c1 : tracker.letters) {
                highCounts.get(c).put(c1, 0);
            }
            bestCharacters.put(c, null);
        }
    }

    // if there are duplicate letters, only track the first one
    // we're seeing how many INDIVIDUAL letters are used the most
    @Override
    public void addIfValid(String word) {
        Set<Character> validLetters = new HashSet<>(tracker.letters);
        for (Character c : validLetters) {
            Set<Character> usedLetters = new HashSet<>();
            if (wordValidAbsentLetter(word, c)) {
                for (char letter : word.toCharArray()) {
                    if (!usedLetters.contains(letter)) {
                        usedLetters.add(letter);
                        highCounts.get(c).put(letter, highCounts.get(c).get(letter) + 1);
                    }
                }
            }
            updateBestCharacters(c);
        }
    }

    private void updateBestCharacters(Character c) {
        Integer highest = 0;
        Map<Character, Integer> currentMap = highCounts.get(c);
        for (Map.Entry<Character, Integer> entry: currentMap.entrySet()) {
            Integer entryCount = entry.getValue();
            if (entryCount > highest) {
                bestCharacters.put(c, entry.getKey());
                highest = entryCount;
                count.put(c, highest);
                score.put(c, highest * POINTS_PER_CHAR);
            }
        }
    }

    public Character getBestCharacter(Character c) {
        return bestCharacters.get(c);
    }

    public Map<Character, Character> getAllBestCharacters() {
        return bestCharacters;
    }
}