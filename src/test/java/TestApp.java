import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Test
    void testRound() {
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
        Assertions.assertEquals(12, HandCategory.getThreeCount('c'));
        Assertions.assertEquals(48, HandCategory.getThreeScore('c'));
        Assertions.assertEquals(11, HandCategory.getThreeCount('o'));
        Assertions.assertEquals(44, HandCategory.getThreeScore('o'));
        Assertions.assertEquals(3, HandCategory.getThreeCount('t'));
        Assertions.assertEquals(12, HandCategory.getThreeScore('t'));

        Assertions.assertEquals(11, HandCategory.getFourCount('c'));
        Assertions.assertEquals(96, HandCategory.getFourScore('c'));
        Assertions.assertEquals(10, HandCategory.getFivePlusCount('c'));
        Assertions.assertEquals(180, HandCategory.getFivePlusScore('c'));
        Assertions.assertEquals(33, HandCategory.getWordCount('c'));
        Assertions.assertEquals(150, HandCategory.getFullHouseScore('c'));
        Assertions.assertEquals('c', HandCategory.getBestFlush('c'));
        Assertions.assertEquals(8, HandCategory.getBestFlushCount('c'));
        Assertions.assertEquals(80, HandCategory.getBestFlushScore('c'));
        Assertions.assertEquals(88, HandCategory.getWildPoints('c'));

        Assertions.assertEquals('o', HandCategory.getHighestCharacter('i'));
        Assertions.assertEquals(21, HandCategory.getHighestCharacterCount('i'));
        Assertions.assertEquals(63, HandCategory.getHighestCharacterPoints('i'));
        Assertions.assertEquals('t', HandCategory.getHighestCharacter('o'));
        Assertions.assertEquals(23, HandCategory.getHighestCharacterCount('o'));
        Assertions.assertEquals(69, HandCategory.getHighestCharacterPoints('o'));
        Assertions.assertEquals('t', HandCategory.getHighestCharacter('p'));
        Assertions.assertEquals(10, HandCategory.getHighestCharacterCount('p'));
        Assertions.assertEquals(30, HandCategory.getHighestCharacterPoints('p'));
        Assertions.assertEquals('t', HandCategory.getHighestCharacter('c'));
        Assertions.assertEquals(27, HandCategory.getHighestCharacterCount('c'));
        Assertions.assertEquals(81, HandCategory.getHighestCharacterPoints('c'));
        Assertions.assertEquals('t', HandCategory.getHighestCharacter('s'));
        Assertions.assertEquals(8, HandCategory.getHighestCharacterCount('s'));
        Assertions.assertEquals(24, HandCategory.getHighestCharacterPoints('s'));
        Assertions.assertEquals('o', HandCategory.getHighestCharacter('t'));
        Assertions.assertEquals(6, HandCategory.getHighestCharacterCount('t'));
        Assertions.assertEquals(18, HandCategory.getHighestCharacterPoints('t'));

        Assertions.assertEquals(82, HandCategory.getStraightPoints('c'));
    }
}
