package round.category;

import java.util.HashSet;
import java.util.Set;

import round.RoundTracker;

public class WildCategory extends HandCategory {

    public WildCategory(RoundTracker tracker) {
        super(tracker);
    }
    @Override
    public void addIfValid(String word) {
        Set<Character> validLetters = new HashSet<>(tracker.letters);
        for (Character c : validLetters) {
            if (wordValidAbsentLetter(word, c)) {
                count.put(c, count.get(c) + 1);
                switch (word.length()) {
                    case 8:
                        // only possible for final round
                        score.put(c, score.get(c) + 15);
                        break;
                    case 7:
                        score.put(c, score.get(c) + 10);
                        break;
                    case 6:
                        score.put(c, score.get(c) + 7);
                        break;
                    case 5:
                        score.put(c, score.get(c) + 5);
                        break;
                    case 4:
                        score.put(c, score.get(c) + 2);
                        break;
                    case 3:
                        score.put(c, score.get(c) + 1);
                        break;
                    default:
                }
            }
        }
    }
}