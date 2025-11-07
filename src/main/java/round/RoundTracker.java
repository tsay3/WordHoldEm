package round;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import round.category.FiveCategory;
import round.category.FlushCategory;
import round.category.FourCategory;
import round.category.HighCardCategory;
import round.category.StraightCategory;
import round.category.ThreeCategory;
import round.category.WildCategory;

public class RoundTracker {
    public boolean finalRound;
    public List<Character> letters;
    public Map<Character, Integer> letterFreq;

    // Each hand category maps possible withheld letters with its score.
    // At the end of the round, these entries indicate the number of points gained
    //    depending on which letter you decide to hold.
    private final ThreeCategory threeCategory;
    private final FourCategory fourCategory;
    private final FiveCategory fivePlusCategory;
    private final FlushCategory flushCategory;
    private final WildCategory wildCategory;
    private final HighCardCategory highCardCategory;
    private final StraightCategory straightCategory;

    public RoundTracker(List<Character> newLetters) {
        finalRound = false;
        letters = newLetters;
        letterFreq = new HashMap<>();
        for (Character letter : newLetters) {
            letterFreq.put(letter, letterFreq.getOrDefault(letter, 0) + 1);
        }

        threeCategory = new ThreeCategory(this);
        fourCategory = new FourCategory(this);
        fivePlusCategory = new FiveCategory(this);
        flushCategory = new FlushCategory(this);
        wildCategory = new WildCategory(this);
        highCardCategory = new HighCardCategory(this);
        straightCategory = new StraightCategory(this);
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

    public void addWord(String word){
        // allWords.add(word);
        threeCategory.addIfValid(word);
        fourCategory.addIfValid(word);
        fivePlusCategory.addIfValid(word);
        wildCategory.addIfValid(word); // also used for full house
        flushCategory.addIfValid(word);
        straightCategory.addIfValid(word);
        highCardCategory.addIfValid(word);
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

    private Integer getWordCount(Character c) {
        return wildCategory.getCount(c);
    }

    public Integer getWildCount(Character c) {
        return getWordCount(c);
    }

    public Integer getWildScore(Character c) {
        return wildCategory.getScore(c);
    }

    public Integer getFullHouseCount(Character c) {
        return getWordCount(c);
    }

    public Integer getFullHouseScore(Character c) {
        if (getWordCount(c) >= 25) {
            return 150;
        } else if (getWordCount(c) >= 15) {
            return 50;
        }
        return 0;
    }

    public Character getBestFlush(Character c) {
        return flushCategory.getBestCharacter(c);
    }

    public Integer getFlushCount(Character c) {
        return flushCategory.getCount(c);
    }

    public Integer getFlushScore(Character c) {
        return flushCategory.getScore(c);
    }

    public Integer getStraightCount(Character c) {
        return straightCategory.getCount(c);
    }

    public Integer getStraightScore(Character c) {
        return straightCategory.getScore(c);
    }

    public Character getHighCardCharacter(Character c) {
        return highCardCategory.getBestCharacter(c);
    }

    public Integer getHighCardCount(Character c) {
        return highCardCategory.getCount(c);
    }

    public Integer getHighCardScore(Character c) {
        return highCardCategory.getScore(c);
    }

}
