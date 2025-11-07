
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import round.RoundTracker;
import round.category.StraightCategory;

public class TestCategoryStraights {
    @Test
    void TestStraights() {
        RoundTracker dummy = new RoundTracker(Arrays.asList('a', 'b', 'b', 'c', 'c', 'c', 'd', 'd'));
        StraightCategory straights = new StraightCategory(dummy);

        straights.addIfValid("abb"); // is a straight
        straights.addIfValid("dad"); // not a straight
        Assertions.assertEquals(1, (int) straights.getCount('c'));
        Assertions.assertEquals(6, (int) straights.getScore('c'));
        Assertions.assertEquals(0, (int) straights.getCount('b'));
        Assertions.assertEquals(0, (int) straights.getScore('b'));

        straights.addIfValid("cabdc"); // not a straight
        straights.addIfValid("cdbcab");  // is a straight
        Assertions.assertEquals(2, (int) straights.getCount('c'));
        Assertions.assertEquals(6+25, (int) straights.getScore('c'));
        Assertions.assertEquals(0, (int) straights.getCount('b'));
        Assertions.assertEquals(0, (int) straights.getScore('b'));

        straights.addIfValid("cdcd");  // is a straight
        Assertions.assertEquals(1, (int) straights.getCount('b'));
        Assertions.assertEquals(12, (int) straights.getScore('b'));
        Assertions.assertEquals(3, (int) straights.getCount('c'));
        Assertions.assertEquals(6+25+12, (int) straights.getScore('c'));
        Assertions.assertEquals(2, (int) straights.getCount('d'));
        Assertions.assertEquals(6+25, (int) straights.getScore('d'));
    }
}