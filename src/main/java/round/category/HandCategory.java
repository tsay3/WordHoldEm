package round.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import round.RoundTracker;

public class HandCategory {
    protected final RoundTracker tracker;
    protected Map<Character, Integer> count;
    protected Map<Character, Integer> score;
    protected Integer baseCount;
    protected Integer baseScore;

    public HandCategory(RoundTracker tracker) {
        this.tracker = tracker;
        count = new HashMap<>();
        score = new HashMap<>();
        for (Character letter : tracker.letters) {
            count.put(letter, 0);
            score.put(letter, 0);
        }
        baseCount = 0;
        baseScore = 0;
    }

    protected boolean wordValidAbsentLetter(String s, Character c) {
        if (tracker.finalRound) return true;
        return (tracker.getLetterCountForRound(c) > RoundTracker.getLetterCountForWord(s, c));
    }

    public void addIfValid(String word) {
        // to be overridden
    }

    public Integer getCount() {
        return baseCount;
    }

    public Integer getCount(Character c) {
        return count.get(c);
    }

    public Integer getScore() {
        return baseScore;
    }

    public Integer getScore(Character c) {
        return score.get(c);
    }

    public Map<Character, Integer> getAllCounts() {
        return count;
    }

    public Map<Character, Integer> getAllScores() {
        return score;
    }
    
    public static List<String> convertMapToList(Map<Character, Integer> input) {
        List<String> output = new ArrayList<>();
        int i = 0;
        for (Map.Entry<Character, Integer> entry : input.entrySet()) {
            output.add(entry.getKey().toString() + entry.getValue().toString());
            i++;
        }
        return output;
    }
    
    public static List<String> convertCharMapToList(Map<Character, Character> input) {
        List<String> output = new ArrayList<>();
        int i = 0;
        for (Map.Entry<Character, Character> entry : input.entrySet()) {
            if (entry.getValue() == null) {
                output.add(entry.getKey().toString());
            } else {
                output.add(entry.getKey().toString() + entry.getValue().toString());
            }
            i++;
        }
        return output;
    }

    // @Override
    // public String toString() {
    //     return convertMapToList(count).toString();
    // }
}