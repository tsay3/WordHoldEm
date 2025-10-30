import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import round.RoundTracker;

public class TestDictionary {

    @Test
    public void testPermutations() {
        List<List<Integer>> testData = new ArrayList<>(new ArrayList<>());
        // ( [], [1], 1) -> [[1]]
        List<Integer> testParts = Collections.singletonList(1);
        List<List<Integer>> results =
                RoundTracker.expandPermutations(testData, testParts, 1);
        List<List<Integer>> expectedArray = Collections.singletonList(
                Collections.singletonList(1)
        );
        Assertions.assertIterableEquals(expectedArray, results);
        testData = expectedArray;
        testParts = Arrays.asList(2,4);
        results = RoundTracker.expandPermutations(testData, testParts, 1);
        expectedArray = Arrays.asList(
                Arrays.asList(1,2),
                Arrays.asList(1,4)
        );
        Assertions.assertIterableEquals(expectedArray, results);

        // ( [[1,2],[1,4]] , [3,5], 1) -> [[1,2,3],[1,4,3],[1,2,5],[1,4,5]]
        testData = expectedArray;
        testParts = Arrays.asList(3,5);
        results = RoundTracker.expandPermutations(testData, testParts, 1);

//        System.out.println("( [[1,2],[1,4]] , [3,5], 1) -> [[1,2,3],[1,4,3],[1,2,5],[1,4,5]]");
//        System.out.println(results);
        expectedArray = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(1,4,3),
                Arrays.asList(1,2,5),
                Arrays.asList(1,4,5));
        Assertions.assertIterableEquals(expectedArray, results);

        // ( [[1,2],[1,4]] , [3,5], 2) -> [[1,2,3,5],[1,4,3,5]]
        results = RoundTracker.expandPermutations(testData, testParts, 2);
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
}
