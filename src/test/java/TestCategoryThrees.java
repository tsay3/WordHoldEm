
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import round.RoundTracker;
import round.category.ThreeCategory;

public class TestCategoryThrees {
    @Test
    void TestThrees() {
        RoundTracker dummy = new RoundTracker(Arrays.asList('a', 'b', 'b', 'c', 'c', 'c', 'd', 'd'));
        ThreeCategory threes = new ThreeCategory(dummy);

        Assertions.assertEquals(0, (int) threes.getCount('c'));
        Assertions.assertEquals(0, (int) threes.getScore('c'));
        Assertions.assertEquals(0, (int) threes.getCount('b'));
        Assertions.assertEquals(0, (int) threes.getScore('b'));
        Assertions.assertEquals(0, (int) threes.getCount('a'));
        Assertions.assertEquals(0, (int) threes.getScore('a'));
        threes.addIfValid("abb");
        Assertions.assertEquals(1, (int) threes.getCount('c'));
        Assertions.assertEquals(4, (int) threes.getScore('c'));
        Assertions.assertEquals(0, (int) threes.getCount('b'));
        Assertions.assertEquals(0, (int) threes.getScore('b'));
        Assertions.assertEquals(0, (int) threes.getCount('a'));
        Assertions.assertEquals(0, (int) threes.getScore('a'));
        threes.addIfValid("dad");
        Assertions.assertEquals(2, (int) threes.getCount('c'));
        Assertions.assertEquals(8, (int) threes.getScore('c'));
        Assertions.assertEquals(1, (int) threes.getCount('b'));
        Assertions.assertEquals(4, (int) threes.getScore('b'));
        Assertions.assertEquals(0, (int) threes.getCount('a'));
        Assertions.assertEquals(0, (int) threes.getScore('a'));
    }
}