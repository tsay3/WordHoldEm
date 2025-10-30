import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestLargeRound {

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
        HandCategory.beginRound(characters);
        for (String word: words) {
            HandCategory.addWord(word);
        }
    }

    @Test
    void testThrees() {
        Assertions.assertEquals(12, (int) HandCategory.getThreeCount('c'));
        Assertions.assertEquals(48, (int) HandCategory.getThreeScore('c'));
        Assertions.assertEquals(11, (int) HandCategory.getThreeCount('o'));
        Assertions.assertEquals(44, (int) HandCategory.getThreeScore('o'));
        Assertions.assertEquals(3, (int) HandCategory.getThreeCount('t'));
        Assertions.assertEquals(12, (int) HandCategory.getThreeScore('t'));
    }

    @Test
    void testFours() {
        Assertions.assertEquals(11, (int) HandCategory.getFourCount('c'));
        Assertions.assertEquals(96, (int) HandCategory.getFourScore('c'));
        Assertions.assertEquals(9, (int) HandCategory.getFourCount('i'));
        Assertions.assertEquals(54, (int) HandCategory.getFourScore('i'));
        Assertions.assertEquals(2, (int) HandCategory.getFourCount('p'));
        Assertions.assertEquals(12, (int) HandCategory.getFourScore('p'));
        Assertions.assertEquals(11, (int) HandCategory.getFourCount('o'));
        Assertions.assertEquals(96, (int) HandCategory.getFourScore('o'));
    }

    @Test
    void testFives() {
        Assertions.assertEquals(10, (int) HandCategory.getFivePlusCount('c'));
        Assertions.assertEquals(180, (int) HandCategory.getFivePlusScore('c'));
        Assertions.assertEquals(5, (int) HandCategory.getFivePlusCount('i'));
        Assertions.assertEquals(40, (int) HandCategory.getFivePlusScore('i'));
        Assertions.assertEquals(3, (int) HandCategory.getFivePlusCount('p'));
        Assertions.assertEquals(24, (int) HandCategory.getFivePlusScore('p'));
        Assertions.assertEquals(5, (int) HandCategory.getFivePlusCount('o'));
        Assertions.assertEquals(40, (int) HandCategory.getFivePlusScore('o'));
    }

    @Test
    void testFullHouse() {
        Assertions.assertEquals(33, (int) HandCategory.getWordCount('c'));
        Assertions.assertEquals(150, (int) HandCategory.getFullHouseScore('c'));
        Assertions.assertEquals(21, (int) HandCategory.getWordCount('i'));
        Assertions.assertEquals(50, (int) HandCategory.getFullHouseScore('i'));
        Assertions.assertEquals(11, (int) HandCategory.getWordCount('p'));
        Assertions.assertEquals(0, (int) HandCategory.getFullHouseScore('p'));
        Assertions.assertEquals(26, (int) HandCategory.getWordCount('o'));
        Assertions.assertEquals(150, (int) HandCategory.getFullHouseScore('o'));
    }

    @Test
    void testFlush() {
        Assertions.assertEquals('c', (char) HandCategory.getBestFlush('c'));
        Assertions.assertEquals(8, (int) HandCategory.getBestFlushCount('c'));
        Assertions.assertEquals(80, (int) HandCategory.getBestFlushScore('c'));
    }

    @Test
    void testWilds() {
        Assertions.assertEquals(1*12 + 2*11 + 5*8 + 7*2, (int) HandCategory.getWildPoints('c')); // 88
        Assertions.assertEquals(1*10 + 2*11 + 5*3 + 7*2, (int) HandCategory.getWildPoints('o')); // 61
    }

    @Test
    void testHighCard() {
        Assertions.assertEquals('o', (char) HandCategory.getHighestCharacter('i'));
        Assertions.assertEquals(21, (int) HandCategory.getHighestCharacterCount('i'));
        Assertions.assertEquals(63, (int) HandCategory.getHighestCharacterPoints('i'));
        Assertions.assertEquals('t', (char) HandCategory.getHighestCharacter('o'));
        Assertions.assertEquals(23, (int) HandCategory.getHighestCharacterCount('o'));
        Assertions.assertEquals(69, (int) HandCategory.getHighestCharacterPoints('o'));
        Assertions.assertEquals('t', (char) HandCategory.getHighestCharacter('p'));
        Assertions.assertEquals(10, (int) HandCategory.getHighestCharacterCount('p'));
        Assertions.assertEquals(30, (int) HandCategory.getHighestCharacterPoints('p'));
        Assertions.assertEquals('t', (char) HandCategory.getHighestCharacter('c'));
        Assertions.assertEquals(27, (int) HandCategory.getHighestCharacterCount('c'));
        Assertions.assertEquals(81, (int) HandCategory.getHighestCharacterPoints('c'));
        Assertions.assertEquals('t', (char) HandCategory.getHighestCharacter('s'));
        Assertions.assertEquals(8, (int) HandCategory.getHighestCharacterCount('s'));
        Assertions.assertEquals(24, (int) HandCategory.getHighestCharacterPoints('s'));
        Assertions.assertEquals('o', (char) HandCategory.getHighestCharacter('t'));
        Assertions.assertEquals(6, (int) HandCategory.getHighestCharacterCount('t'));
        Assertions.assertEquals(18, (int) HandCategory.getHighestCharacterPoints('t'));
    }

    @Test
    void testStraight() {
        Assertions.assertEquals(82, (int) HandCategory.getStraightPoints('c'));
    }
}
