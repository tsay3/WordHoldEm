package round.category;

import java.util.Map;

import round.RoundTracker;

public class HandCategory {
    protected final RoundTracker tracker;
    protected Map<Character, Integer> count;
    protected Map<Character, Integer> score;

    public HandCategory(RoundTracker tracker) {
        this.tracker = tracker;
        for (Character letter : tracker.letters) {
            count.put(letter, 0);
            score.put(letter, 0);
        }
    }

    protected boolean wordValidAbsentLetter(String s, Character c) {
        if (tracker.finalRound) return true;
        return (tracker.getLetterCountForRound(c) > RoundTracker.getLetterCountForWord(s, c));
    }

    public void addIfValid(String word) {
        // to be overridden
    }

    public Integer getCount(Character c) {
        return count.get(c);
    }

    public Integer getScore(Character c) {
        return score.get(c);
    }
}