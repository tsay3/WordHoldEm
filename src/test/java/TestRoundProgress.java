import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import round.RoundTracker;

public class TestRoundProgress {
    @Test
    void ProgressTest() {
        RoundTracker round = new RoundTracker(new ArrayList<Character>(Arrays.asList(
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
        Assertions.assertEquals(4, (int) round.getStraightScore(c));

        Assertions.assertEquals(1, (int) round.getHighCardCount(c));
        Assertions.assertEquals(3, (int) round.getHighCardScore(c));
        Assertions.assertEquals(1, (int) round.getFlushCount(c));
        Assertions.assertEquals(10, (int) round.getFlushScore(c));
    }

    void TwoWords(RoundTracker round, Character c) {
        Assertions.assertEquals(2, (int) round.getThreeCount(c));
        Assertions.assertEquals(6, (int) round.getThreeScore(c));
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
        Assertions.assertEquals(4, (int) round.getStraightScore(c));
        Assertions.assertEquals(2, (int) round.getHighCardCount(c));
        Assertions.assertEquals(6, (int) round.getHighCardScore(c));
    }

    void TwoWordsTwoStraightsOneHigh(RoundTracker round, Character c) {
        TwoWords(round, c);
        
        Assertions.assertEquals(2, (int) round.getStraightCount(c));
        Assertions.assertEquals(8, (int) round.getStraightScore(c));
        Assertions.assertEquals(1, (int) round.getHighCardCount(c));
        Assertions.assertEquals(3, (int) round.getHighCardScore(c));
    }

    private void ThreeLettersA(RoundTracker round) {
        Character c = 'a';
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void ThreeLettersB(RoundTracker round) {
        Character c = 'b';
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void ThreeLettersC(RoundTracker round) {
        Character c = 'c';
        throw new UnsupportedOperationException("Not supported yet.");
    }
}