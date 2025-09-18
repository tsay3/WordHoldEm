import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestApp {

    @Test
    public void testPermutations() {
        List<List<Integer>> testData = new ArrayList<>(new ArrayList<>());
        // ( [], [1], 1) -> [[1]]
        List<Integer> testParts = Collections.singletonList(1);
        List<List<Integer>> results =
                HandCategory.expandPermutations(testData, testParts, 1);
        List<List<Integer>> expectedArray = Collections.singletonList(
                Collections.singletonList(1)
        );
        Assertions.assertIterableEquals(expectedArray, results);
        testData = expectedArray;
        testParts = Arrays.asList(2,4);
        results = HandCategory.expandPermutations(testData, testParts, 1);
        expectedArray = Arrays.asList(
                Arrays.asList(1,2),
                Arrays.asList(1,4)
        );
        Assertions.assertIterableEquals(expectedArray, results);

        // ( [[1,2],[1,4]] , [3,5], 1) -> [[1,2,3],[1,4,3],[1,2,5],[1,4,5]]
        testData = expectedArray;
        testParts = Arrays.asList(3,5);
        results = HandCategory.expandPermutations(testData, testParts, 1);

//        System.out.println("( [[1,2],[1,4]] , [3,5], 1) -> [[1,2,3],[1,4,3],[1,2,5],[1,4,5]]");
//        System.out.println(results);
        expectedArray = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(1,4,3),
                Arrays.asList(1,2,5),
                Arrays.asList(1,4,5));
        Assertions.assertIterableEquals(expectedArray, results);

        // ( [[1,2],[1,4]] , [3,5], 2) -> [[1,2,3,5],[1,4,3,5]]
        results = HandCategory.expandPermutations(testData, testParts, 2);
        expectedArray = Arrays.asList(
                Arrays.asList(1,2,3,5),
                Arrays.asList(1,4,3,5));
        Assertions.assertIterableEquals(expectedArray, results);
    }

    @Test
    void dictionaryTest() {
        Assertions.assertFalse(Dictionary.isValidWord("banana"));
        Dictionary.buildDictionary();
        Assertions.assertTrue(Dictionary.isValidWord("banana"));
        Assertions.assertFalse(Dictionary.isValidWord("bhfgrd"));
    }

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
    }

    @Test
    void testFives() {
        Assertions.assertEquals(10, (int) HandCategory.getFivePlusCount('c'));
        Assertions.assertEquals(180, (int) HandCategory.getFivePlusScore('c'));
    }

    @Test
    void testFullHouse() {
        Assertions.assertEquals(33, (int) HandCategory.getWordCount('c'));
        Assertions.assertEquals(150, (int) HandCategory.getFullHouseScore('c'));
    }

    @Test
    void testFlush() {
        Assertions.assertEquals('c', (char) HandCategory.getBestFlush('c'));
        Assertions.assertEquals(8, (int) HandCategory.getBestFlushCount('c'));
        Assertions.assertEquals(80, (int) HandCategory.getBestFlushScore('c'));
    }

    @Test
    void testWilds() {
        Assertions.assertEquals(88, (int) HandCategory.getWildPoints('c'));
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
