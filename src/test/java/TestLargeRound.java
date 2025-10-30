import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import round.RoundTracker;

public class TestLargeRound {

    static RoundTracker thisRound;

    @BeforeAll
    static void setupRound() {
        List<String> words = Arrays.asList(
                "scoot","scoop","cop","cops","sit",
                "its","tis","coo","pots","post",
                "stop","stoop","spot","opt","opts",
                "too","top","tops","stoic","cost",
                "cot","cots","pit","pits","tip",
                "tips","topic","topics","optic",
                "optics","sop","coops","coots"
        );
        List<Character> characters = Arrays.asList(
                'i','o','p','c','s','t','c','o'
        );
        thisRound = new RoundTracker(characters);
        for (String word: words) {
            thisRound.addWord(word);
        }
    }

    @Test
    void testThrees() {
        Assertions.assertEquals(12, (int) thisRound.getThreeCount('c'));
        Assertions.assertEquals(48, (int) thisRound.getThreeScore('c'));
        Assertions.assertEquals(11, (int) thisRound.getThreeCount('o'));
        Assertions.assertEquals(44, (int) thisRound.getThreeScore('o'));
        Assertions.assertEquals(3, (int) thisRound.getThreeCount('t'));
        Assertions.assertEquals(12, (int) thisRound.getThreeScore('t'));
    }

    @Test
    void testFours() {
        Assertions.assertEquals(11, (int) thisRound.getFourCount('c'));
        Assertions.assertEquals(96, (int) thisRound.getFourScore('c'));
        Assertions.assertEquals(9, (int) thisRound.getFourCount('i'));
        Assertions.assertEquals(54, (int) thisRound.getFourScore('i'));
        Assertions.assertEquals(2, (int) thisRound.getFourCount('p'));
        Assertions.assertEquals(12, (int) thisRound.getFourScore('p'));
        Assertions.assertEquals(11, (int) thisRound.getFourCount('o'));
        Assertions.assertEquals(96, (int) thisRound.getFourScore('o'));
    }

    @Test
    void testFives() {
        Assertions.assertEquals(10, (int) thisRound.getFivePlusCount('c'));
        Assertions.assertEquals(180, (int) thisRound.getFivePlusScore('c'));
        Assertions.assertEquals(5, (int) thisRound.getFivePlusCount('i'));
        Assertions.assertEquals(40, (int) thisRound.getFivePlusScore('i'));
        Assertions.assertEquals(3, (int) thisRound.getFivePlusCount('p'));
        Assertions.assertEquals(24, (int) thisRound.getFivePlusScore('p'));
        Assertions.assertEquals(5, (int) thisRound.getFivePlusCount('o'));
        Assertions.assertEquals(40, (int) thisRound.getFivePlusScore('o'));
    }

    @Test
    void testFullHouse() {
        Assertions.assertEquals(33, (int) thisRound.getWordCount('c'));
        Assertions.assertEquals(150, (int) thisRound.getFullHouseScore('c'));
        Assertions.assertEquals(21, (int) thisRound.getWordCount('i'));
        Assertions.assertEquals(50, (int) thisRound.getFullHouseScore('i'));
        Assertions.assertEquals(11, (int) thisRound.getWordCount('p'));
        Assertions.assertEquals(0, (int) thisRound.getFullHouseScore('p'));
        Assertions.assertEquals(26, (int) thisRound.getWordCount('o'));
        Assertions.assertEquals(150, (int) thisRound.getFullHouseScore('o'));
    }

    @Test
    void testFlush() {
        Assertions.assertEquals('c', (char) thisRound.getBestFlush('c'));
        Assertions.assertEquals(8, (int) thisRound.getBestFlushCount('c'));
        Assertions.assertEquals(80, (int) thisRound.getBestFlushScore('c'));
    }

    @Test
    void testWilds() {
        Assertions.assertEquals(1*12 + 2*11 + 5*8 + 7*2, (int) thisRound.getWildPoints('c')); // 88
        Assertions.assertEquals(1*10 + 2*11 + 5*3 + 7*2, (int) thisRound.getWildPoints('o')); // 61
    }

    @Test
    void testHighCard() {
        Assertions.assertEquals('o', (char) thisRound.getHighestCharacter('i'));
        Assertions.assertEquals(21, (int) thisRound.getHighestCharacterCount('i'));
        Assertions.assertEquals(63, (int) thisRound.getHighestCharacterPoints('i'));
        Assertions.assertEquals('t', (char) thisRound.getHighestCharacter('o'));
        Assertions.assertEquals(23, (int) thisRound.getHighestCharacterCount('o'));
        Assertions.assertEquals(69, (int) thisRound.getHighestCharacterPoints('o'));
        Assertions.assertEquals('t', (char) thisRound.getHighestCharacter('p'));
        Assertions.assertEquals(10, (int) thisRound.getHighestCharacterCount('p'));
        Assertions.assertEquals(30, (int) thisRound.getHighestCharacterPoints('p'));
        Assertions.assertEquals('t', (char) thisRound.getHighestCharacter('c'));
        Assertions.assertEquals(27, (int) thisRound.getHighestCharacterCount('c'));
        Assertions.assertEquals(81, (int) thisRound.getHighestCharacterPoints('c'));
        Assertions.assertEquals('t', (char) thisRound.getHighestCharacter('s'));
        Assertions.assertEquals(8, (int) thisRound.getHighestCharacterCount('s'));
        Assertions.assertEquals(24, (int) thisRound.getHighestCharacterPoints('s'));
        Assertions.assertEquals('o', (char) thisRound.getHighestCharacter('t'));
        Assertions.assertEquals(6, (int) thisRound.getHighestCharacterCount('t'));
        Assertions.assertEquals(18, (int) thisRound.getHighestCharacterPoints('t'));
    }

    @Test
    void testStraight() {
        Assertions.assertEquals(82, (int) thisRound.getStraightPoints('c'));
    }
}
