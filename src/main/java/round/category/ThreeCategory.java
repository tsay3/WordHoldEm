package round.category;

import java.util.HashSet;
import java.util.Set;

import round.RoundTracker;

public class ThreeCategory extends HandCategory {

    public ThreeCategory(RoundTracker tracker) {
        super(tracker);
    }
    @Override
    public void addIfValid(String word) {
        Set<Character> validLetters = new HashSet<>(tracker.letters);
        if (word.length() == 3) {
            for (Character c : validLetters) {
                if (wordValidAbsentLetter(word, c)) {
                    count.put(c, count.get(c) + 1);
                    score.put(c, score.get(c) + 4);
                }
            }
        }
    }
}