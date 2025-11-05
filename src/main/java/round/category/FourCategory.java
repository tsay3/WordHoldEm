package round.category;

import round.RoundTracker;

public class FourCategory extends HandCategory {

    public FourCategory(RoundTracker tracker) {
        super(tracker);
    }
    @Override
    public void addIfValid(String word) {
        if (word.length() == 4) {
            for (Character c : tracker.letters) {
                if (wordValidAbsentLetter(word, c)) {
                    count.put(c, count.get(c) + 1);
                    score.put(c, score.get(c) + 6);
                    if (count.get(c) >= 10) {
                        score.put(c, score.get(c) + 30);
                    }
                }
            }
        }
    }
}