import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import round.RoundTracker;
import round.category.HighCardCategory;
import round.category.StraightCategory;

public class TestSmallRound {

    static RoundTracker thisRound;

    @BeforeAll
    static void setupRound() {
        List<String> words = Arrays.asList(
                "coax","jog","focal","fox","goal","lag",
                "flax","loaf","calf"
        );
        List<Character> characters = Arrays.asList(
                'l','x','c','a','f','j','g','o'
        );
        thisRound = new RoundTracker(characters);
        // thisRound.beginRound(characters);
        for (String word: words) {
            thisRound.addWord(word);
        }
    }

    @Test
    void testThrees() {
        // jog, fox, lag
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('l', 2);
        expectedMap.put('x', 2);
        expectedMap.put('c', 3);
        expectedMap.put('a', 2);
        expectedMap.put('f', 2);
        expectedMap.put('j', 2);
        expectedMap.put('g', 1);
        expectedMap.put('o', 1);
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedMap.get(c), thisRound.getThreeCount(c));
            Assertions.assertEquals(expectedMap.get(c)*4, (int) thisRound.getThreeScore(c));
        }
    }

    @Test
    void testFours() {
        // coax, goal, flax, loaf, calf
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('l', 1);
        expectedMap.put('x', 3);
        expectedMap.put('c', 3);
        expectedMap.put('a', 0);
        expectedMap.put('f', 2);
        expectedMap.put('j', 5);
        expectedMap.put('g', 4);
        expectedMap.put('o', 2);
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFourCount(c));
            Assertions.assertEquals(expectedMap.get(c)*6, (int) thisRound.getFourScore(c));
        }
    }

    @Test
    void testFives() {
        // focal
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('l', 0);
        expectedMap.put('x', 1);
        expectedMap.put('c', 0);
        expectedMap.put('a', 0);
        expectedMap.put('f', 0);
        expectedMap.put('j', 1);
        expectedMap.put('g', 1);
        expectedMap.put('o', 0);
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFivePlusCount(c));
            Assertions.assertEquals(expectedMap.get(c)*8, (int) thisRound.getFivePlusScore(c));
        }
    }

    @Test
    void testFullHouse() {
        // fox, jog, lag
        // calf, coax, flax, goal, loaf
        // focal
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('l', 3);
        expectedMap.put('x', 6);
        expectedMap.put('c', 6);
        expectedMap.put('a', 2);
        expectedMap.put('f', 4);
        expectedMap.put('j', 8);
        expectedMap.put('g', 6);
        expectedMap.put('o', 3);
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFullHouseCount(c));
            Assertions.assertEquals(0, (int) thisRound.getFullHouseScore(c));
        }
    }

    @Test
    void testFlush() {
        // fox, jog, lag
        // calf, coax, flax, goal, loaf
        // focal
        // coax, jog, focal, fox, goal, lag, flax, loaf, calf
        Map<Character, Integer> expectedMap = new HashMap<>();
        Map<Character, Character> expectedChar = new HashMap<>();
        expectedMap.put('l', 1); // coax, jog, fox
        expectedChar.put('l', 'f');
        expectedMap.put('x', 2); // jog, focal, goal, lag, loaf, calf
        expectedChar.put('x', 'l');
        expectedMap.put('c', 2); // jog, fox, goal, lag, flax, loaf
        expectedChar.put('c', 'l');
        expectedMap.put('a', 1); // jog, fox
        expectedChar.put('a', 'f');
        expectedMap.put('f', 1); // coax, jog, goal, lag
        expectedChar.put('f', 'l');
        expectedMap.put('j', 3); // coax, focal, fox, goal, lag, flax, loaf, calf
        expectedChar.put('j', 'f');
        expectedMap.put('g', 3); // coax, focal, fox, flax, loaf, calf
        expectedChar.put('g', 'f');
        expectedMap.put('o', 1); // lag, flax, calf
        expectedChar.put('o', 'c');
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedChar.get(c), thisRound.getBestFlush(c));
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFlushCount(c));
            Assertions.assertEquals(expectedMap.get(c) * 10, (int) thisRound.getFlushScore(c));
        }
    }

    @Test
    void testWilds() {
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('l', 3); // coax, jog, fox
        expectedMap.put('x', 6); // jog, focal, goal, lag, loaf, calf
        expectedMap.put('c', 6); // jog, fox, goal, lag, flax, loaf
        expectedMap.put('a', 2); // jog, fox
        expectedMap.put('f', 4); // coax, jog, goal, lag
        expectedMap.put('j', 8); // coax, focal, fox, goal, lag, flax, loaf, calf
        expectedMap.put('g', 6); // coax, focal, fox, flax, loaf, calf
        expectedMap.put('o', 3); // lag, flax, calf
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedMap.get(c), thisRound.getWildCount(c));
        }
        expectedMap.put('l', 2+1+1); // coax, jog, fox
        expectedMap.put('x', 1+5+2+1+2+2); // jog, focal, goal, lag, loaf, calf
        expectedMap.put('c', 1+1+2+1+2+2); // jog, fox, goal, lag, flax, loaf
        expectedMap.put('a', 1+1); // jog, fox
        expectedMap.put('f', 2+1+2+1); // coax, jog, goal, lag
        expectedMap.put('j', 2+5+1+2+1+2+2+2); // coax, focal, fox, goal, lag, flax, loaf, calf
        expectedMap.put('g', 2+5+1+2+2+2); // coax, focal, fox, flax, loaf, calf
        expectedMap.put('o', 1+2+2); // lag, flax, calf
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedMap.get(c), thisRound.getWildScore(c));
        }
    }

    @Test
    void testHighCard() {
        Map<Character, Integer> expectedMap = new HashMap<>();
        Map<Character, Character> expectedChar = new HashMap<>();
        expectedMap.put('l', 3); // coax, jog, fox
        expectedChar.put('l', 'o');
        expectedMap.put('x', 5); // jog, focal, goal, lag, loaf, calf
        expectedChar.put('x', 'a');
        expectedMap.put('c', 4); // jog, fox, goal, lag, flax, loaf
        expectedChar.put('c', 'a');
        expectedMap.put('a', 2); // jog, fox
        expectedChar.put('a', 'o');
        expectedMap.put('f', 3); // coax, jog, goal, lag
        expectedChar.put('f', 'a');
        expectedMap.put('j', 7); // coax, focal, fox, goal, lag, flax, loaf, calf
        expectedChar.put('j', 'a');
        expectedMap.put('g', 5); // coax, focal, fox, flax, loaf, calf
        expectedChar.put('g', 'a');
        expectedMap.put('o', 3); // lag, flax, calf
        expectedChar.put('o', 'a');
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedChar.get(c), thisRound.getHighCardCharacter(c));
            Assertions.assertEquals(expectedMap.get(c), thisRound.getHighCardCount(c));
            Assertions.assertEquals(expectedMap.get(c) * HighCardCategory.POINTS_PER_CHAR, (int) thisRound.getHighCardScore(c));
        }
    }

    @Test
    void testStraight() {
        // coax, jog, focal, fox, goal, lag, flax, loaf, calf
        // lxcafjgo
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('l', 1); // jog
        expectedMap.put('x', 2); // jog, calf
        expectedMap.put('c', 2); // jog, flax
        expectedMap.put('a', 1); // jog
        expectedMap.put('f', 1); // jog
        expectedMap.put('j', 0); // 
        expectedMap.put('g', 0); // 
        expectedMap.put('o', 0); // 
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedMap.get(c), thisRound.getStraightCount(c));
        }
        expectedMap.put('l', StraightCategory.THREE_LETTER_STRAIGHT); // jog
        expectedMap.put('x', StraightCategory.THREE_LETTER_STRAIGHT
                            + StraightCategory.FOUR_LETTER_STRAIGHT); // jog, calf
        expectedMap.put('c', StraightCategory.THREE_LETTER_STRAIGHT
                            + StraightCategory.FOUR_LETTER_STRAIGHT); // jog, flax
        expectedMap.put('a', StraightCategory.THREE_LETTER_STRAIGHT); // jog
        expectedMap.put('f', StraightCategory.THREE_LETTER_STRAIGHT); // jog
        expectedMap.put('j', 0); // 
        expectedMap.put('g', 0); // 
        expectedMap.put('o', 0); // 
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedMap.get(c), thisRound.getStraightScore(c));
        }
    }
}
