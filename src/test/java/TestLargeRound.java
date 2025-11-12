import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import round.RoundTracker;
import round.category.StraightCategory;

public class TestLargeRound {

    static RoundTracker thisRound;

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
        thisRound = new RoundTracker(characters);
        for (String word: words) {
            thisRound.addWord(word);
        }
    }

    @Test
    void testThrees() {
        // cop, sit, its, tis, coo, opt, too, top, cot, pit, tip, sop
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('i', 7); // cop, coo, opt, too, top, cot, sop
        expectedMap.put('o', 10); // cop, sit, its, tis, opt, top, cot, pit, tip, sop
        expectedMap.put('p', 6); // sit, its, tis, coo, too, cot
        expectedMap.put('c', 12); // cop, sit, its, tis, coo, opt, too, top, cot, pit, tip, sop
        expectedMap.put('s', 8); // cop, coo, opt, too, top, cot, pit, tip
        expectedMap.put('t', 3); // cop, coo, sop
        for (Character c : expectedMap.keySet()) {
            Assertions.assertEquals(expectedMap.get(c), thisRound.getThreeCount(c));
            Assertions.assertEquals(expectedMap.get(c)*4, (int) thisRound.getThreeScore(c));
        }
    }

    @Test
    void testFours() {
        // cops, pots, post, stop, spot, opts, tops, cost, cots, pits, tips
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('c', 11); // cops, pots, post, stop, spot, opts, tops, cost, cots, pits, tips
        expectedMap.put('o', 11); // cops, pots, post, stop, spot, opts, tops, cost, cots, pits, tips
        expectedMap.put('i', 9); // cops, pots, post, stop, spot, opts, tops, cost, cots
        expectedMap.put('p', 2); // cost, cots
        expectedMap.put('s', 0); // 
        expectedMap.put('t', 1); // cops
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFourCount(c), () -> message);
        }
        expectedMap.put('c', 96);
        expectedMap.put('o', 96);
        expectedMap.put('i', 54);
        expectedMap.put('p', 12);
        expectedMap.put('s', 0);
        expectedMap.put('t', 6);
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedMap.get(c), (int) thisRound.getFourScore(c), () -> message);
        }
    }

    @Test
    void testFives() {
        // scoot,scoop,stoop,stoic,topic,topics,optic,optics,coops,coots
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('c', 10); // scoot,scoop,stoop,stoic,topic,topics,optic,optics,coops,coots
        expectedMap.put('o', 5); // stoic,topic,topics,optic,optics
        expectedMap.put('i', 5); // scoot,scoop,stoop,coops,coots
        expectedMap.put('p', 3); // scoot,stoic,coots
        expectedMap.put('s', 2); // topic,optic
        expectedMap.put('t', 2); // scoop,coops
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFivePlusCount(c), () -> message);
        }
        expectedMap.put('c', 180); // scoot,scoop,stoop,stoic,topic,topics,optic,optics,coops,coots
        expectedMap.put('o', 40); // stoic,topic,topics,optic,optics
        expectedMap.put('i', 40); // scoot,scoop,stoop,coops,coots
        expectedMap.put('p', 24); // scoot,stoic,coots
        expectedMap.put('s', 16); // topic,optic
        expectedMap.put('t', 16); // scoop,coops
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFivePlusScore(c), () -> message);
        }
    }

    @Test
    void testFullHouse() {
        // scoot,scoop,cop,cops,sit,its,tis,coo,pots,post,
        //  stop,stoop,spot,opt,opts,too,top,tops,stoic,cost,
        //  cot,cots,pit,pits,tip,tips,topic,topics,optic,optics,
        //  sop,coops,coots
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('c', 33); // <all words>
        expectedMap.put('o', 26); // <all words except: scoot,scoop,coo,stoop,too,coops,coots>
        expectedMap.put('i', 21); // <all words except: sit,its,tis,stoic,pit,pits,tip,tips,topic,topics,optic,optics>
        expectedMap.put('p', 11); // scoot,sit,its,tis,coo,too,stoic,cost,cot,cots,coots
        expectedMap.put('s', 10); // cop,coo,opt,too,top,cot,pit,tip,topic,optic
        expectedMap.put('t', 6); // scoop,cop,cops,coo,sop,coops
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFullHouseCount(c), () -> message);
        }
        expectedMap.put('c', 150);
        expectedMap.put('o', 150);
        expectedMap.put('i', 50);
        expectedMap.put('p', 0); 
        expectedMap.put('s', 0); 
        expectedMap.put('t', 0);
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFullHouseScore(c), () -> message);
        }
    }

    @Test
    void testFlush() {
        // coo,coops,coots,cop,cops,cost,cot,cots,its,opt,optic,optics,opts,pit,pits,post,
        // pots,scoop,scoot,sit,sop,spot,stoic,stoop,stop,tip,tips,tis,too,top,topic,topics,
        // tops
        // c:8, i:1, o:4, p:4, s:8, t:8
        Map<Character, Integer> expectedMap = new HashMap<>();
        Map<Character, Character> expectedChar = new HashMap<>();
        expectedMap.put('c', 8); // <all words>
        expectedChar.put('c', 'c');
        expectedMap.put('o', 7); // <all words except: scoot,scoop,coo,stoop,too,coops,coots>
        expectedChar.put('o', 't');
        expectedMap.put('i', 8); // <all words except: sit,its,tis,stoic,pit,pits,tip,tips,topic,topics,optic,optics>
        expectedChar.put('i', 'c');
        expectedMap.put('p', 5); // scoot,sit,its,tis,coo,too,stoic,cost,cot,cots,coots
        expectedChar.put('p', 'c');
        expectedMap.put('s', 4); // cop,coo,opt,too,top,cot,pit,tip,topic,optic
        expectedChar.put('s', 't');
        expectedMap.put('t', 4); // scoop,cop,cops,coo,sop,coops
        expectedChar.put('t', 'c');
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedChar.get(c), thisRound.getBestFlush(c), () -> message);
            Assertions.assertEquals(expectedMap.get(c), thisRound.getFlushCount(c), () -> message);
        }
    }

    @Test
    void testWilds() {
        // coo,coops,coots,cop,cops,cost,cot,cots,its,opt,optic,optics,opts,pit,pits,post,
        // pots,scoop,scoot,sit,sop,spot,stoic,stoop,stop,tip,tips,tis,too,top,topic,topics,
        // tops
        // c:8, i:1, o:4, p:4, s:8, t:8
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('c', 12*1 + 11*2 + 8*5 + 2*7); // <all words>
        expectedMap.put('o', 10*1 + 11*2 + 3*5 + 2*7); // <all words except: scoot,scoop,coo,stoop,too,coops,coots>
        expectedMap.put('i', 7*1 + 9*2 + 5*5); // <all words except: sit,its,tis,stoic,pit,pits,tip,tips,topic,topics,optic,optics>
        expectedMap.put('p', 6*1 + 2*2 + 3*5); // scoot,sit,its,tis,coo,too,stoic,cost,cot,cots,coots
        expectedMap.put('s', 8*1 + 0*2 + 2*5); // cop,coo,opt,too,top,cot,pit,tip,topic,optic
        expectedMap.put('t', 3*1 + 1*2 + 2*5); // scoop,cop,cops,coo,sop,coops
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedMap.get(c), thisRound.getWildScore(c), () -> message);
        }
    }

    @Test
    void testHighCard() {
        // coo,coops,coots,cop,cops,cost,cot,cots,its,opt,optic,optics,opts,pit,pits,post,
        // pots,scoop,scoot,sit,sop,spot,stoic,stoop,stop,tip,tips,tis,too,top,topic,topics,
        // tops
        // c:8, i:1, o:4, p:4, s:8, t:8
        Map<Character, Integer> expectedMap = new HashMap<>();
        Map<Character, Character> expectedChar = new HashMap<>();
        expectedMap.put('c', 27); // <all words>
        expectedChar.put('c', 't');
        expectedMap.put('o', 23); // <all words except: scoot,scoop,coo,stoop,too,coops,coots>
        expectedChar.put('o', 't');
        expectedMap.put('i', 21); // <all words except: sit,its,tis,stoic,pit,pits,tip,tips,topic,topics,optic,optics>
        expectedChar.put('i', 'o');
        expectedMap.put('p', 10); // scoot,sit,its,tis,coo,too,stoic,cost,cot,cots,coots
        expectedChar.put('p', 't');
        expectedMap.put('s', 8); // cop,coo,opt,too,top,cot,pit,tip,topic,optic
        expectedChar.put('s', 't');
        expectedMap.put('t', 6); // scoop,cop,cops,coo,sop,coops
        expectedChar.put('t', 'o');
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedChar.get(c), thisRound.getHighCardCharacter(c), () -> message);
            Assertions.assertEquals(expectedMap.get(c), thisRound.getHighCardCount(c), () -> message);
        }
    }

    @Test
    void testStraight() {
        // iopcstco
        // straights are:
        //   cop (istco)
        //   cops (itco)
        //   cot (iopcs)
        //   cost cots (iopc)
        //   sop pots post stop spot opts tops (c)
        //   stoic (p)
        //   topic optic (s)
        //   topics optics (co)
        int str3 = StraightCategory.THREE_LETTER_STRAIGHT;
        int str4 = StraightCategory.FOUR_LETTER_STRAIGHT;
        int str5 = StraightCategory.FIVE_LETTER_STRAIGHT;
        int str6 = StraightCategory.SIX_LETTER_STRAIGHT;
        Map<Character, Integer> expectedMap = new HashMap<>();
        expectedMap.put('c', 3*str3 + 9*str4 + 2*str6);
        expectedMap.put('o', 2*str3 + 3*str4 + 2*str6);
        expectedMap.put('i', 2*str3 + 3*str4);
        expectedMap.put('p', 1*str3 + 2*str4 + 1*str5);
        expectedMap.put('s', 2*str3 + 2*str5);
        expectedMap.put('t', 1*str3 + 1*str4);
        for (Character c : expectedMap.keySet()) {
            String message = "Assertion failed at character " + c;
            Assertions.assertEquals(expectedMap.get(c), thisRound.getStraightScore(c), () -> message);
        }
    }
}
