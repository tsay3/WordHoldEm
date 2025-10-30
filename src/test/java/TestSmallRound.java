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
        Assertions.assertEquals(2, (int) thisRound.getBestFlushCount('c'));
        Assertions.assertEquals(2*10, (int) thisRound.getBestFlushScore('c'));
        Assertions.assertEquals('l', (char) thisRound.getBestFlush('o'));
        Assertions.assertEquals(1, (int) thisRound.getBestFlushCount('o'));
        Assertions.assertEquals(1*10, (int) thisRound.getBestFlushScore('o'));
        Assertions.assertEquals('l', (char) thisRound.getBestFlush('f'));
        Assertions.assertEquals(1, (int) thisRound.getBestFlushCount('f'));
        Assertions.assertEquals(1*10, (int) thisRound.getBestFlushScore('f'));
        Assertions.assertEquals('f', (char) thisRound.getBestFlush('l'));
        Assertions.assertEquals(1, (int) thisRound.getBestFlushCount('l'));
        Assertions.assertEquals(1*10, (int) thisRound.getBestFlushScore('l'));
        Assertions.assertEquals('f', (char) thisRound.getBestFlush('a'));
        Assertions.assertEquals(1, (int) thisRound.getBestFlushCount('a'));
        Assertions.assertEquals(1*10, (int) thisRound.getBestFlushScore('a'));
        Assertions.assertEquals('f', (char) thisRound.getBestFlush('g'));
        Assertions.assertEquals(2, (int) thisRound.getBestFlushCount('g'));
        Assertions.assertEquals(2*10, (int) thisRound.getBestFlushScore('g'));
    }

    @Test
    void testWilds() {
        Assertions.assertEquals(1*3 + 2*1, (int) thisRound.getWildPoints('c')); // 5
        Assertions.assertEquals(1*3, (int) thisRound.getWildPoints('o')); // 3
    }

    @Test
    void testHighCard() {
        Assertions.assertEquals('o', (char) thisRound.getHighestCharacter('c'));
        Assertions.assertEquals(3, (int) thisRound.getHighestCharacterCount('c'));
        Assertions.assertEquals(3*3, (int) thisRound.getHighestCharacterPoints('c'));
    }

    @Test
    void testStraight() {
        Assertions.assertEquals(4, (int) thisRound.getStraightPoints('c'));
        Assertions.assertEquals(0, (int) thisRound.getStraightPoints('j'));
    }
}
