import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import round.RoundTracker;
import round.category.StraightCategory;

public class TestRoundProgress {
    @Test
    void ProgressTest() {
        RoundTracker round = new RoundTracker(new ArrayList<>(Arrays.asList(
            'a','b','c','d','e','f','g','g'
        )));

        NoWords(round, 'a');

        round.addWord("cab");

        NoWords(round, 'a');
        FirstWord(round, 'g');

        round.addWord("fed");
        FirstWord(round, 'a');
        FirstWord(round, 'e');

        round.addWord("egg");
        TwoWordsOneStraightTwoHigh(round, 'a');
        FirstWord(round, 'e');
        TwoWordsTwoStraightsOneHigh(round, 'g');

        round.addWord("bag");
        round.addWord("beg");
        round.addWord("gag");
        round.addWord("ace");
        ThreeLettersA(round);
        ThreeLettersB(round);
        ThreeLettersC(round);
        ThreeLettersG(round);

        round.addWord("bead");
        round.addWord("bade");
        round.addWord("cage");
        round.addWord("cafe");
        round.addWord("deaf");
        round.addWord("face");
        round.addWord("fade");
        FourLettersA(round);
        FourLettersB(round);
        FourLettersF(round);
        FourLettersG(round);
    }

    void NoWords(RoundTracker round, Character c) {
        Assertions.assertEquals(0, (int) round.getThreeCount(c));
        Assertions.assertEquals(0, (int) round.getThreeScore(c));
        Assertions.assertEquals(0, (int) round.getFourCount(c));
        Assertions.assertEquals(0, (int) round.getFourScore(c));

        Assertions.assertEquals(0, (int) round.getFivePlusCount(c));
        Assertions.assertEquals(0, (int) round.getFivePlusScore(c));
        Assertions.assertEquals(0, (int) round.getWildCount(c));
        Assertions.assertEquals(0, (int) round.getWildScore(c));

        Assertions.assertEquals(0, (int) round.getFullHouseCount(c));
        Assertions.assertEquals(0, (int) round.getFullHouseScore(c));
        Assertions.assertEquals(0, (int) round.getStraightCount(c));
        Assertions.assertEquals(0, (int) round.getStraightScore(c));

        Assertions.assertEquals(0, (int) round.getHighCardCount(c));
        Assertions.assertEquals(0, (int) round.getHighCardScore(c));
        Assertions.assertEquals(0, (int) round.getFlushCount(c));
        Assertions.assertEquals(0, (int) round.getFlushScore(c));
    }

    void FirstWord(RoundTracker round, Character c) {
        Assertions.assertEquals(1, (int) round.getThreeCount(c));
        Assertions.assertEquals(4, (int) round.getThreeScore(c));
        Assertions.assertEquals(0, (int) round.getFourCount(c));
        Assertions.assertEquals(0, (int) round.getFourScore(c));

        Assertions.assertEquals(0, (int) round.getFivePlusCount(c));
        Assertions.assertEquals(0, (int) round.getFivePlusScore(c));
        Assertions.assertEquals(1, (int) round.getWildCount(c));
        Assertions.assertEquals(1, (int) round.getWildScore(c));

        Assertions.assertEquals(1, (int) round.getFullHouseCount(c));
        Assertions.assertEquals(0, (int) round.getFullHouseScore(c));
        Assertions.assertEquals(1, (int) round.getStraightCount(c));
        Assertions.assertEquals(StraightCategory.THREE_LETTER_STRAIGHT, (int) round.getStraightScore(c));

        Assertions.assertEquals(1, (int) round.getHighCardCount(c));
        Assertions.assertEquals(4, (int) round.getHighCardScore(c));
        Assertions.assertEquals(1, (int) round.getFlushCount(c));
        Assertions.assertEquals(10, (int) round.getFlushScore(c));
    }

    void TwoWords(RoundTracker round, Character c) {
        Assertions.assertEquals(2, (int) round.getThreeCount(c));
        Assertions.assertEquals(8, (int) round.getThreeScore(c));
        Assertions.assertEquals(0, (int) round.getFourCount(c));
        Assertions.assertEquals(0, (int) round.getFourScore(c));

        Assertions.assertEquals(0, (int) round.getFivePlusCount(c));
        Assertions.assertEquals(0, (int) round.getFivePlusScore(c));
        Assertions.assertEquals(2, (int) round.getWildCount(c));
        Assertions.assertEquals(2, (int) round.getWildScore(c));

        Assertions.assertEquals(2, (int) round.getFullHouseCount(c));
        Assertions.assertEquals(0, (int) round.getFullHouseScore(c));

        Assertions.assertEquals(1, (int) round.getFlushCount(c));
        Assertions.assertEquals(10, (int) round.getFlushScore(c));
    }

    void TwoWordsOneStraightTwoHigh(RoundTracker round, Character c) {
        TwoWords(round, c);
        
        Assertions.assertEquals(1, (int) round.getStraightCount(c));
        Assertions.assertEquals(StraightCategory.THREE_LETTER_STRAIGHT, (int) round.getStraightScore(c));
        Assertions.assertEquals(2, (int) round.getHighCardCount(c));
        Assertions.assertEquals(8, (int) round.getHighCardScore(c));
    }

    void TwoWordsTwoStraightsOneHigh(RoundTracker round, Character c) {
        TwoWords(round, c);
        
        Assertions.assertEquals(2, (int) round.getStraightCount(c));
        Assertions.assertEquals(2 * StraightCategory.THREE_LETTER_STRAIGHT, (int) round.getStraightScore(c));
        Assertions.assertEquals(1, (int) round.getHighCardCount(c));
        Assertions.assertEquals(4, (int) round.getHighCardScore(c));
    }

    private void ThreeLettersA(RoundTracker round) {
        Character c = 'a';
        // fed, egg, beg
        Assertions.assertEquals(3, (int) round.getThreeCount(c));
        Assertions.assertEquals(1, (int) round.getFlushCount(c));
        Assertions.assertEquals(3, (int) round.getHighCardCount(c));
        Assertions.assertEquals(1, (int) round.getStraightCount(c));
    }

    private void ThreeLettersB(RoundTracker round) {
        Character c = 'b';
        // fed, egg, gag, ace
        Assertions.assertEquals(4, (int) round.getThreeCount(c));
        Assertions.assertEquals(1, (int) round.getFlushCount(c));
        Assertions.assertEquals(3, (int) round.getHighCardCount(c));
        Assertions.assertEquals(1, (int) round.getStraightCount(c));
    }

    private void ThreeLettersC(RoundTracker round) {
        Character c = 'c';
        // fed, egg, bag, beg, gag
        Assertions.assertEquals(5, (int) round.getThreeCount(c));
        Assertions.assertEquals(2, (int) round.getFlushCount(c));
        Assertions.assertEquals(4, (int) round.getHighCardCount(c));
        Assertions.assertEquals(1, (int) round.getStraightCount(c));
    }

    private void ThreeLettersG(RoundTracker round) {
        Character c = 'g';
        // cab, fed, bag, beg, ace
        Assertions.assertEquals(5, (int) round.getThreeCount(c));
        Assertions.assertEquals(2, (int) round.getFlushCount(c));
        Assertions.assertEquals(3, (int) round.getHighCardCount(c));
        Assertions.assertEquals(2, (int) round.getStraightCount(c));
    }

    private void FourLettersA(RoundTracker round) {
        Character c = 'a';
        // fed, egg, beg
        Assertions.assertEquals(1, (int) round.getFlushCount(c));
        Assertions.assertEquals(3, (int) round.getHighCardCount(c));
        Assertions.assertEquals(1, (int) round.getStraightCount(c));
    }

    private void FourLettersB(RoundTracker round) {
        Character c = 'b';
        // fed, egg, gag, ace
        // cage, cafe, deaf, face, fade
        Assertions.assertEquals(3, (int) round.getFlushCount(c));
        Assertions.assertEquals(8, (int) round.getHighCardCount(c));
        Assertions.assertEquals(1, (int) round.getStraightCount(c));
    }

    private void FourLettersF(RoundTracker round) {
        Character c = 'f';
        // cab, egg, bag, beg, gag, ace
        // bead, bade, cage
        Assertions.assertEquals(4, (int) round.getFlushCount(c));
        Assertions.assertEquals(7, (int) round.getHighCardCount(c));
        Assertions.assertEquals(2, (int) round.getStraightCount(c));
    }

    private void FourLettersG(RoundTracker round) {
        Character c = 'g';
        // cab, fed, bag, beg, ace
        // bead, bade, cage, cafe, deaf, face, fade
        Assertions.assertEquals(4, (int) round.getFlushCount(c));
        Assertions.assertEquals(10, (int) round.getHighCardCount(c));
        Assertions.assertEquals(2, (int) round.getStraightCount(c));
    }
}