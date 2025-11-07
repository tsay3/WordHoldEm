import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import round.RoundTracker;

public class TestSmallRound {

    static RoundTracker thisRound;

    @BeforeAll
    static void setupRound() {
        List<String> words = Arrays.asList(
                "coax","jog","focal","fox","goal","lag"
        );
        List<Character> characters = Arrays.asList(
                'l','x','c','a','f','j','g','o'
        );
        thisRound = new RoundTracker(characters);
        // thisRound.beginRound(characters);
        for (String word: words) {
            thisRound.addWord(word);
        }
    }

    @Test
    void testThrees() {
        Assertions.assertEquals(3, (int) thisRound.getThreeCount('c'));
        Assertions.assertEquals(3*4, (int) thisRound.getThreeScore('c'));
        Assertions.assertEquals(1, (int) thisRound.getThreeCount('o'));
        Assertions.assertEquals(1*4, (int) thisRound.getThreeScore('o'));
        Assertions.assertEquals(2, (int) thisRound.getThreeCount('l'));
        Assertions.assertEquals(2*4, (int) thisRound.getThreeScore('l'));
    }

    @Test
    void testFours() {
        Assertions.assertEquals(1, (int) thisRound.getFourCount('c'));
        Assertions.assertEquals(1*6, (int) thisRound.getFourScore('c'));
        Assertions.assertEquals(1, (int) thisRound.getFourCount('x'));
        Assertions.assertEquals(1*6, (int) thisRound.getFourScore('x'));
        Assertions.assertEquals(2, (int) thisRound.getFourCount('f'));
        Assertions.assertEquals(2*6, (int) thisRound.getFourScore('f'));
        Assertions.assertEquals(0, (int) thisRound.getFourCount('o'));
        Assertions.assertEquals(0*6, (int) thisRound.getFourScore('o'));
    }

    @Test
    void testFives() {
        Assertions.assertEquals(0, (int) thisRound.getFivePlusCount('c'));
        Assertions.assertEquals(0*8, (int) thisRound.getFivePlusScore('c'));
        Assertions.assertEquals(0, (int) thisRound.getFivePlusCount('f'));
        Assertions.assertEquals(0*8, (int) thisRound.getFivePlusScore('f'));
        Assertions.assertEquals(1, (int) thisRound.getFivePlusCount('x'));
        Assertions.assertEquals(1*8, (int) thisRound.getFivePlusScore('x'));
    }

    @Test
    void testFullHouse() {
        Assertions.assertEquals(4, (int) thisRound.getWordCount('c'));
        Assertions.assertEquals(0, (int) thisRound.getFullHouseScore('c'));
        Assertions.assertEquals(4, (int) thisRound.getWordCount('f'));
        Assertions.assertEquals(0, (int) thisRound.getFullHouseScore('f'));
        Assertions.assertEquals(4, (int) thisRound.getWordCount('x'));
        Assertions.assertEquals(0, (int) thisRound.getFullHouseScore('x'));
        Assertions.assertEquals(4, (int) thisRound.getWordCount('g'));
        Assertions.assertEquals(0, (int) thisRound.getFullHouseScore('g'));
        Assertions.assertEquals(3, (int) thisRound.getWordCount('l'));
        Assertions.assertEquals(0, (int) thisRound.getFullHouseScore('l'));
        Assertions.assertEquals(2, (int) thisRound.getWordCount('a'));
        Assertions.assertEquals(0, (int) thisRound.getFullHouseScore('a'));
        Assertions.assertEquals(1, (int) thisRound.getWordCount('o'));
        Assertions.assertEquals(0, (int) thisRound.getFullHouseScore('o'));
    }

    @Test
    void testFlush() {
        Assertions.assertEquals('f', (char) thisRound.getBestFlush('c'));
        Assertions.assertEquals(2, (int) thisRound.getFlushCount('c'));
        Assertions.assertEquals(2*10, (int) thisRound.getFlushScore('c'));
        Assertions.assertEquals('l', (char) thisRound.getBestFlush('o'));
        Assertions.assertEquals(1, (int) thisRound.getFlushCount('o'));
        Assertions.assertEquals(1*10, (int) thisRound.getFlushScore('o'));
        Assertions.assertEquals('l', (char) thisRound.getBestFlush('f'));
        Assertions.assertEquals(1, (int) thisRound.getFlushCount('f'));
        Assertions.assertEquals(1*10, (int) thisRound.getFlushScore('f'));
        Assertions.assertEquals('f', (char) thisRound.getBestFlush('l'));
        Assertions.assertEquals(1, (int) thisRound.getFlushCount('l'));
        Assertions.assertEquals(1*10, (int) thisRound.getFlushScore('l'));
        Assertions.assertEquals('f', (char) thisRound.getBestFlush('a'));
        Assertions.assertEquals(1, (int) thisRound.getFlushCount('a'));
        Assertions.assertEquals(1*10, (int) thisRound.getFlushScore('a'));
        Assertions.assertEquals('f', (char) thisRound.getBestFlush('g'));
        Assertions.assertEquals(2, (int) thisRound.getFlushCount('g'));
        Assertions.assertEquals(2*10, (int) thisRound.getFlushScore('g'));
    }

    @Test
    void testWilds() {
        Assertions.assertEquals(3, (int) thisRound.getWildCount('g'));
        Assertions.assertEquals(4, (int) thisRound.getWildCount('c'));
        Assertions.assertEquals(3, (int) thisRound.getWildCount('o'));
        Assertions.assertEquals(1*3 + 2*1, (int) thisRound.getWildScore('c')); // 5
        Assertions.assertEquals(1*3, (int) thisRound.getWildScore('o')); // 3
    }

    @Test
    void testHighCard() {
        Assertions.assertEquals('o', (char) thisRound.getHighCardCharacter('c'));
        Assertions.assertEquals(3, (int) thisRound.getHighCardCount('c'));
        Assertions.assertEquals(3*3, (int) thisRound.getHighCardScore('c'));
    }

    @Test
    void testStraight() {
        Assertions.assertEquals(4, (int) thisRound.getStraightScore('c'));
        Assertions.assertEquals(0, (int) thisRound.getStraightScore('j'));
    }
}
