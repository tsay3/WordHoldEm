
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import round.RoundTracker;
import round.category.HandCategory;
import round.category.StraightCategory;

public class TestCategoryStraights {

    @Test
    void HardTestStraights() {
        RoundTracker dummy = new RoundTracker(Arrays.asList('a', 'b', 'b', 'c', 'c', 'c', 'd', 'd'));
        StraightCategory straights = new StraightCategory(dummy);
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('a', 1);
        expectedMap.put('b', 2);
        expectedMap.put('c', 3);
        expectedMap.put('d', 2);
        // System.out.println("== START ==");
        // System.out.println(HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println(HandCategory.convertMapToList(dummy.letterFreq));
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(dummy.letterFreq));

        straights.addIfValid("dad"); // not a straight
        // System.out.println("== STRAIGHT 1 ==");
        straights.addIfValid("abb"); // is a straight for c and d, not a or b
        expectedMap.put('a', 0);
        expectedMap.put('b', 0);
        expectedMap.put('c', 1);
        expectedMap.put('d', 1);
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(straights.getAllCounts()).toString());
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(straights.getAllCounts()));
        expectedMap.put('a', 0);
        expectedMap.put('b', 0);
        expectedMap.put('c', 6);
        expectedMap.put('d', 6);
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(straights.getAllScores()).toString());
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(straights.getAllScores()));

        straights.addIfValid("cabdc"); // not a straight
        // System.out.println("== STRAIGHT 2 ==");
        straights.addIfValid("cdbcb");  // is a straight for c, not for a, b, or d
        expectedMap.put('a', 0);
        expectedMap.put('b', 0);
        expectedMap.put('c', 2);
        expectedMap.put('d', 1);
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(straights.getAllCounts()).toString());
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(straights.getAllCounts()));
        // Assertions.assertEquals(0, (int) straights.getCount('b'));
        // Assertions.assertEquals(0, (int) straights.getScore('b'));
        // Assertions.assertEquals(2, (int) straights.getCount('c'));
        // Assertions.assertEquals(6+20, (int) straights.getScore('c'));
        // Assertions.assertEquals(1, (int) straights.getCount('d'));
        // Assertions.assertEquals(6, (int) straights.getScore('d'));
        expectedMap.put('a', 0);
        expectedMap.put('b', 0);
        expectedMap.put('c', 6+20);
        expectedMap.put('d', 6);
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(straights.getAllScores()));
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(straights.getAllScores()).toString());

        // System.out.println("== STRAIGHT 3 ==");
        straights.addIfValid("cdcd");  // is a straight for a, b, or c, not for d
        // Assertions.assertEquals(1, (int) straights.getCount('a'));
        // Assertions.assertEquals(12, (int) straights.getScore('a'));
        // Assertions.assertEquals(1, (int) straights.getCount('b'));
        // Assertions.assertEquals(12, (int) straights.getScore('b'));
        // Assertions.assertEquals(3, (int) straights.getCount('c'));
        // Assertions.assertEquals(6+20+12, (int) straights.getScore('c'));
        // Assertions.assertEquals(1, (int) straights.getCount('d'));
        // Assertions.assertEquals(6, (int) straights.getScore('d'));
        expectedMap.put('a', 1);
        expectedMap.put('b', 1);
        expectedMap.put('c', 3);
        expectedMap.put('d', 1);
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(straights.getAllCounts()));
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(straights.getAllCounts()).toString());
        expectedMap.put('a', 12);
        expectedMap.put('b', 12);
        expectedMap.put('c', 6+20+12);
        expectedMap.put('d', 6);
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(straights.getAllScores()));
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(straights.getAllScores()).toString());
    }
}