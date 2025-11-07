package round.category;

import java.util.HashMap;
import java.util.Map;

import round.RoundTracker;
import util.LetterFrequencies;

public class FlushCategory extends HandCategory {

    private final Map<Character, Map<Character, Integer>> flushStats; // for each character, what words are most common?
    private final Map<Character, Character> bestFlush;                // for each character, what letter is best?

    public FlushCategory(RoundTracker tracker) {
        super(tracker);
        flushStats = new HashMap<>();
        bestFlush = new HashMap<>();
        for (Character letter : tracker.letters) {
            flushStats.put(letter, new HashMap<>());
            bestFlush.put(letter, null);
        }
    }
    @Override
    public void addIfValid(String word) {
        Map<Character, Integer> wordFreqs = LetterFrequencies.getLetterFrequency(word.toCharArray());
        for (Character c : tracker.letters) {
            Integer totalOccurrences = tracker.letterFreq.get(c);
            Integer wordOccurrences = wordFreqs.getOrDefault(c, 0);
            // ensure that removing this letter doesn't eliminate the word
            if (totalOccurrences > wordOccurrences) {
                Character first = word.charAt(0);
                flushStats.get(c).put(first, flushStats.get(c).getOrDefault(first, 0) + 1);
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

    @Override
    public Integer getCount(Character c) {
        return flushStats.get(c).get(bestFlush.get(c));
    }

    @Override
    public Integer getScore(Character c) {
        return 10 * getCount(c);
    }

    public Character getBestCharacter(Character c) {
        return bestFlush.get(c);
    }
}