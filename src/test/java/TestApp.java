import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TestApp {

//    @Test
    public void testLetterCombos() {
        for (int i = 0; i < 400; i++) {
            List<Character> letters = LetterPicker.getNewLetters();
            letters.forEach(System.out::print);
            System.out.println();
        }
    }

    @Test
    public void testPermutations() {
        List<List<Integer>> testData = new ArrayList<>(new ArrayList<>());
        // ( [], [1], 1) -> [[1]]
        List<Integer> testParts = Arrays.asList(1);
        List<List<Integer>> results =
                HandCategory.expandPermutations(testData, testParts, 1);
        List<List<Integer>> expectedArray = Arrays.asList(
                Arrays.asList(1)
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
        assertFalse(Dictionary.isValidWord("banana"));
        Dictionary.buildDictionary();
        assertTrue(Dictionary.isValidWord("banana"));
        assertFalse(Dictionary.isValidWord("bhfgrd"));
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
        Assertions.assertEquals(12, HandCategory.getThreeCount());
        Assertions.assertEquals(48, HandCategory.getThreeScore());
        Assertions.assertEquals(11, HandCategory.getFourCount());
        Assertions.assertEquals(96, HandCategory.getFourScore());
        Assertions.assertEquals(10, HandCategory.getFivePlusCount());
        Assertions.assertEquals(180, HandCategory.getFivePlusScore());
        Assertions.assertEquals(33, HandCategory.getWordCount());
        Assertions.assertEquals(150, HandCategory.getFullHouseScore());
        Assertions.assertEquals('c', HandCategory.getBestFlush());
        Assertions.assertEquals(8, HandCategory.getBestFlushCount());
        Assertions.assertEquals(80, HandCategory.getBestFlushScore());
        Assertions.assertEquals(88, HandCategory.getWildPoints());
        Assertions.assertEquals('t', HandCategory.getHighestCharacter());
        Assertions.assertEquals(27, HandCategory.getHighestCharacterCount());
        Assertions.assertEquals(81, HandCategory.getHighestCharacterPoints());
        Assertions.assertEquals(82, HandCategory.getStraightPoints());
    }
}
