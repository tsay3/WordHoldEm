
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import round.RoundTracker;
import round.category.HandCategory;
import round.category.HighCardCategory;

public class TestCategoryHighCards {

    @Test
    void HardTestHighCards() {
        RoundTracker dummy = new RoundTracker(Arrays.asList('a', 'b', 'b', 'c', 'c', 'c', 'd', 'd'));
        HighCardCategory highs = new HighCardCategory(dummy);
        Map<Character, Integer> expectedMap = new HashMap<>();
        Map<Character, Character> expectedCharMap = new HashMap<>();
        expectedMap.put('a', 1);
        expectedMap.put('b', 2);
        expectedMap.put('c', 3);
        expectedMap.put('d', 2);
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(dummy.letterFreq));

        // System.out.println("--- dad abb ---");
        highs.addIfValid("dad");
        highs.addIfValid("abb");
        expectedMap.put('a', 0);
        expectedMap.put('b', 1); // dad -> a:1, b:0, c:0, d:1
        expectedMap.put('c', 2); // dad abb -> a:2, b:1, c:0, d:1
        expectedMap.put('d', 1); // abb -> a:1, b:1, c:0, d:0
        expectedCharMap.put('a', null);
        expectedCharMap.put('b', 'a'); // alphabetically first
        expectedCharMap.put('c', 'a');
        expectedCharMap.put('d', 'a');
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(highs.getAllCounts()).toString());
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(highs.getAllCounts()));
        // System.out.println("EXPECTED: " + HandCategory.convertCharMapToList(expectedCharMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertCharMapToList(highs.getAllBestCharacters()).toString());
        Assertions.assertLinesMatch(HandCategory.convertCharMapToList(expectedCharMap), HandCategory.convertCharMapToList(highs.getAllBestCharacters()));
        expectedMap.put('a', 0);
        expectedMap.put('b', HighCardCategory.POINTS_PER_CHAR);
        expectedMap.put('c', 2 * HighCardCategory.POINTS_PER_CHAR);
        expectedMap.put('d', HighCardCategory.POINTS_PER_CHAR);
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(highs.getAllScores()).toString());
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(highs.getAllScores()));

        // System.out.println("--- dad abb cabdc cdbcb ---");
        highs.addIfValid("cabdc");
        highs.addIfValid("cdbcb");
        expectedMap.put('a', 1);  // cdbcb -> a:0 b:1 c:1 d:1
        expectedMap.put('b', 2);  // dad cabdc -> a:2 b:1 c:1 d:2
        expectedMap.put('c', 3);  // dad abb cabdc cdbcb -> a:3 b:3 c:2 d:3
        expectedMap.put('d', 3);  // abb cabdc cdbcb -> a:2 b:3 c:2 d:2
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(highs.getAllCounts()).toString());
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(highs.getAllCounts()));
        expectedCharMap.put('a', 'b');
        expectedCharMap.put('b', 'a');
        expectedCharMap.put('c', 'a');
        expectedCharMap.put('d', 'b');
        // System.out.println("EXPECTED: " + HandCategory.convertCharMapToList(expectedCharMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertCharMapToList(highs.getAllBestCharacters()).toString());
        Assertions.assertLinesMatch(HandCategory.convertCharMapToList(expectedCharMap), HandCategory.convertCharMapToList(highs.getAllBestCharacters()));

        // System.out.println("--- dad abb cabdc cdbcb cdcd ---");
        highs.addIfValid("cdcd");
        expectedMap.put('a', 2); // cdbcb cdcd -> a:0 b:1 c:2 d:2
        expectedMap.put('b', 3); // dad cabdc cdcd -> a:2 b:1 c:2 d:3
        expectedMap.put('c', 4); // dad abb cabdc cdbcb cdcd -> a:3 b:3 c:3 d:4
        expectedMap.put('d', 3); // abb cabdc cdbcb -> a:2 b:3 c:2 d:2
        // System.out.println("EXPECTED: " + HandCategory.convertMapToList(expectedMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertMapToList(highs.getAllCounts()).toString());
        Assertions.assertLinesMatch(HandCategory.convertMapToList(expectedMap), HandCategory.convertMapToList(highs.getAllCounts()));
        expectedCharMap.put('a', 'c');
        expectedCharMap.put('b', 'd');
        expectedCharMap.put('c', 'd');
        expectedCharMap.put('d', 'b');
        // System.out.println("EXPECTED: " + HandCategory.convertCharMapToList(expectedCharMap).toString());
        // System.out.println("ACTUAL:   " + HandCategory.convertCharMapToList(highs.getAllBestCharacters()).toString());
        Assertions.assertLinesMatch(HandCategory.convertCharMapToList(expectedCharMap), HandCategory.convertCharMapToList(highs.getAllBestCharacters()));
    }
}