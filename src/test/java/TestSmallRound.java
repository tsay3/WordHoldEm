import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestSmallRound {

    @BeforeAll
    static void setupRound() {
        List<String> words = Arrays.asList(
                "coax","jog","focal","fox","goal","lag"
        );
        List<Character> characters = Arrays.asList(
                'l','x','c','a','f','j','g','o'
        );
        HandCategory.beginRound(characters);
        for (String word: words) {
            HandCategory.addWord(word);
        }
    }

    @Test
    void testThrees() {
        Assertions.assertEquals(3, (int) HandCategory.getThreeCount('c'));
        Assertions.assertEquals(3*4, (int) HandCategory.getThreeScore('c'));
        Assertions.assertEquals(1, (int) HandCategory.getThreeCount('o'));
        Assertions.assertEquals(1*4, (int) HandCategory.getThreeScore('o'));
        Assertions.assertEquals(2, (int) HandCategory.getThreeCount('l'));
        Assertions.assertEquals(2*4, (int) HandCategory.getThreeScore('l'));
    }

    @Test
    void testFours() {
        Assertions.assertEquals(1, (int) HandCategory.getFourCount('c'));
        Assertions.assertEquals(1*6, (int) HandCategory.getFourScore('c'));
        Assertions.assertEquals(1, (int) HandCategory.getFourCount('x'));
        Assertions.assertEquals(1*6, (int) HandCategory.getFourScore('x'));
        Assertions.assertEquals(2, (int) HandCategory.getFourCount('f'));
        Assertions.assertEquals(2*6, (int) HandCategory.getFourScore('f'));
        Assertions.assertEquals(0, (int) HandCategory.getFourCount('o'));
        Assertions.assertEquals(0*6, (int) HandCategory.getFourScore('o'));
    }

    @Test
    void testFives() {
        Assertions.assertEquals(0, (int) HandCategory.getFivePlusCount('c'));
        Assertions.assertEquals(0*8, (int) HandCategory.getFivePlusScore('c'));
        Assertions.assertEquals(0, (int) HandCategory.getFivePlusCount('f'));
        Assertions.assertEquals(0*8, (int) HandCategory.getFivePlusScore('f'));
        Assertions.assertEquals(1, (int) HandCategory.getFivePlusCount('x'));
        Assertions.assertEquals(1*8, (int) HandCategory.getFivePlusScore('x'));
    }

    @Test
    void testFullHouse() {
        Assertions.assertEquals(4, (int) HandCategory.getWordCount('c'));
        Assertions.assertEquals(0, (int) HandCategory.getFullHouseScore('c'));
        Assertions.assertEquals(4, (int) HandCategory.getWordCount('f'));
        Assertions.assertEquals(0, (int) HandCategory.getFullHouseScore('f'));
        Assertions.assertEquals(4, (int) HandCategory.getWordCount('x'));
        Assertions.assertEquals(0, (int) HandCategory.getFullHouseScore('x'));
        Assertions.assertEquals(4, (int) HandCategory.getWordCount('g'));
        Assertions.assertEquals(0, (int) HandCategory.getFullHouseScore('g'));
        Assertions.assertEquals(3, (int) HandCategory.getWordCount('l'));
        Assertions.assertEquals(0, (int) HandCategory.getFullHouseScore('l'));
        Assertions.assertEquals(2, (int) HandCategory.getWordCount('a'));
        Assertions.assertEquals(0, (int) HandCategory.getFullHouseScore('a'));
        Assertions.assertEquals(1, (int) HandCategory.getWordCount('o'));
        Assertions.assertEquals(0, (int) HandCategory.getFullHouseScore('o'));
    }

    @Test
    void testFlush() {
        Assertions.assertEquals('f', (char) HandCategory.getBestFlush('c'));
        Assertions.assertEquals(2, (int) HandCategory.getBestFlushCount('c'));
        Assertions.assertEquals(2*10, (int) HandCategory.getBestFlushScore('c'));
        Assertions.assertEquals('l', (char) HandCategory.getBestFlush('o'));
        Assertions.assertEquals(1, (int) HandCategory.getBestFlushCount('o'));
        Assertions.assertEquals(1*10, (int) HandCategory.getBestFlushScore('o'));
        Assertions.assertEquals('l', (char) HandCategory.getBestFlush('f'));
        Assertions.assertEquals(1, (int) HandCategory.getBestFlushCount('f'));
        Assertions.assertEquals(1*10, (int) HandCategory.getBestFlushScore('f'));
        Assertions.assertEquals('f', (char) HandCategory.getBestFlush('l'));
        Assertions.assertEquals(1, (int) HandCategory.getBestFlushCount('l'));
        Assertions.assertEquals(1*10, (int) HandCategory.getBestFlushScore('l'));
        Assertions.assertEquals('f', (char) HandCategory.getBestFlush('a'));
        Assertions.assertEquals(1, (int) HandCategory.getBestFlushCount('a'));
        Assertions.assertEquals(1*10, (int) HandCategory.getBestFlushScore('a'));
        Assertions.assertEquals('f', (char) HandCategory.getBestFlush('g'));
        Assertions.assertEquals(2, (int) HandCategory.getBestFlushCount('g'));
        Assertions.assertEquals(2*10, (int) HandCategory.getBestFlushScore('g'));
    }

    @Test
    void testWilds() {
        Assertions.assertEquals(1*3 + 2*1, (int) HandCategory.getWildPoints('c')); // 5
        Assertions.assertEquals(1*3, (int) HandCategory.getWildPoints('o')); // 3
    }

    @Test
    void testHighCard() {
        Assertions.assertEquals('o', (char) HandCategory.getHighestCharacter('c'));
        Assertions.assertEquals(3, (int) HandCategory.getHighestCharacterCount('c'));
        Assertions.assertEquals(3*3, (int) HandCategory.getHighestCharacterPoints('c'));
    }

    @Test
    void testStraight() {
        Assertions.assertEquals(4, (int) HandCategory.getStraightPoints('c'));
        Assertions.assertEquals(0, (int) HandCategory.getStraightPoints('j'));
    }
}
