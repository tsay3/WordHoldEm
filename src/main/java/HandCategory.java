import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;

public class HandCategory {

    private static List<String> allWords;
    private static List<Character> letters;
    private static Integer threeCount;
    private static Integer fourCount;
    private static Integer fivePlusCount;
    private static Map<Character, Integer> flushCount;
    private static Character bestFlush;
    private static Integer wildPoints;
    private static Map<Character, Integer> highCount;
    private static Integer straightPoints;
//    private static List<Integer> allStraights;

    public static void beginRound(List<Character> newLetters) {
        allWords = new ArrayList<>();
        letters = newLetters;
        threeCount = 0;
        fourCount = 0;
        fivePlusCount = 0;
        flushCount = new HashMap<>();
        bestFlush = null;
        wildPoints = 0;
        highCount = new HashMap<>();
        straightPoints = 0;
//        allStraights = new ArrayList<>();

        for (Character c : newLetters) {
            flushCount.put(c, 0);
        }
        createLetterMap();
//        System.out.println(letterIndices);
    }
    private static Map<Character, List<Integer>> letterIndices;
    private static void createLetterMap() {
        letterIndices = new HashMap<>();
        for (int i = 0; i < letters.size(); i++) {
            Character letter = letters.get(i);
            if (!letterIndices.containsKey(letter)) {
                letterIndices.put(letter, new ArrayList<>());
            }
            letterIndices.get(letter).add(i);
        }
        System.out.println("Letters: " + letters);
    }

    public static void addWord(String word){
//        System.out.println("Adding word:  " + word);
        allWords.add(word);
        calculateLengthsAndWilds(word);
        calculateFlushes(word);
//        findStraights(word);
        addIfStraight(word);
        calculateHighCard(word);
    }

    private static void calculateHighCard(String word) {
        Set<Character> foundLetters = new HashSet<>();
        for (Character character : word.toCharArray()) {
            foundLetters.add(character);
        }
        for (Character character : foundLetters) {
            highCount.put(character, highCount.getOrDefault(character, 0)+1);
        }
    }

    private static Character bestCharacter = '-';
    private static Integer bestCount = 0;
    private static void setHighestCharacterAndCount() {
        bestCharacter = '-';
        bestCount = 0;
        for (Character character : highCount.keySet()) {
            if (highCount.get(character) > bestCount) {
                bestCharacter = character;
                bestCount = highCount.get(character);
            }
        }
    }

    public static Character getHighestCharacter() {
        if (bestCharacter == '-') setHighestCharacterAndCount();
        return bestCharacter;
    }

    public static Integer getHighestCharacterCount() {
        if (bestCharacter == '-') setHighestCharacterAndCount();
        return bestCount;
    }

    public static Integer getHighestCharacterPoints() {
        int pointsPerChar = 3;
        if (bestCharacter == '-') {
            setHighestCharacterAndCount();
        }
        return bestCount * pointsPerChar;
    }

    public static Integer getThreeCount() {
        return threeCount;
    }

    public static Integer getThreeScore() {
        return threeCount * 4;
    }

    public static Integer getFourCount() {
        return fourCount;
    }

    public static Integer getFourScore() {
        if (fourCount >= 10) {
            return fourCount * 6 + 30;
        }
        return fourCount * 6;
    }

    public static Integer getFivePlusCount() {
        return fivePlusCount;
    }

    public static Integer getFivePlusScore() {
        if (fivePlusCount >= 10) {
            return fivePlusCount * 8 + 100;
        }
        return fivePlusCount * 8;
    }

    private static void calculateLengthsAndWilds(String word) {
        if (word.length() >= 5) {
            fivePlusCount++;
            if (word.length() == 8) {
                wildPoints += 15;
            } else if (word.length() == 7) {
                wildPoints += 10;
            } else if (word.length() == 6) {
                wildPoints += 7;
            } else if (word.length() == 5) {
                wildPoints += 5;
            }
        } else if (word.length() == 4) {
            fourCount++;
            wildPoints += 2;
        } else if (word.length() == 3) {
            threeCount++;
            wildPoints++;
        }
    }

    public static Integer getWordCount() {
        return allWords.size();
    }

    public static Integer getWildPoints() {
        return wildPoints;
    }

    public static Integer getFullHouseScore() {
        if (allWords.size() >= 25) {
            return 150;
        } else if (allWords.size() >= 15) {
            return 50;
        }
        return 0;
    }

    public static Character getBestFlush() {
        return bestFlush;
    }

    public static Integer getBestFlushCount() {
        return flushCount.get(bestFlush);
    }

    public static Integer getBestFlushScore() {
        return 10 * flushCount.get(bestFlush);
    }

    private static void calculateFlushes(String word) {
        Character first = word.charAt(0);
        flushCount.put(first, flushCount.get(first) + 1);
        if (bestFlush != null) {
            if (flushCount.get(first) >= flushCount.get(bestFlush)) {
                bestFlush = first;
            }
        } else {
            bestFlush = first;
        }
    }
    // straights are measured by starting letter and length
    // for 8 letters, this means:
    // a count of all 6 3-letter straights, then all 5 4-letter straights,
    // then 5, 6, 7, and 8
    // Note this list is POSSIBLE straights
    private static Integer getStraightIndex(Integer start, Integer end) {
        // (0, 2) -> 0      (1, 3) -> 1
        if (start + 2 > end) return null;
        if (start < 0) return null;
        if (end >= letters.size()) return null;
        Integer length = end - start + 1;
        Integer value = 0;
        for (int i = 3; i < length; i++) {
            // for 8 characters, there are 6
            value += (letters.size() - i + 1);
        }
        return value + start;
    }

    // does the reverse of the above
    private static ImmutablePair<Integer, Integer> indexToStartAndEnd(Integer index) {
        // 0 -> (0, 2)    1 -> (1, 3)
        // 6 -> (0, 3)    11 -> (0, 4)    16 -> (1, 6)
        Integer size = 3;
        Integer start = index;
        while (start >= (letters.size() - size + 1)) {
            start -= (letters.size() - size + 1);
            size++;
        }
        return new ImmutablePair<>(start, start+size-1);
    }
//    private static Integer straightScore(Integer index) {
//        Integer size = 3;
//        while (index >= (letters.size() - size + 1)) {
//            index -= (letters.size() - size + 1);
//            size++;
//        }
//        // 3-straights: 4 points each
//        // ...
//        // 8-straights: 120 points each
//        int[] modifiers = {4, 10, 20, 50, 80, 120};
//        int modifier = modifiers[size - 3];
//        return allStraights.get(index) * modifier;
//    }

//    private static void addAllStraightsInRange(Integer start, Integer end) {
//        if (end - start < 2) return;
//        if (start < 0) return;
//        if (end > letters.size()) return;
//        for (int i = start; i < end - 2; i++) {
//            for (int j = i + 2; j < end; j++) {
//                int index = getStraightIndex(i, j);
//                allStraights.set(index, allStraights.get(index) + 1);
//            }
//        }
//    }

    private static void setAllStraightsInRange(List<Boolean> foundStraights,
                                               Integer start, Integer end) {
        if (end - start < 2) return;
        if (start < 0) return;
        if (end > letters.size()) return;
        for (int i = start; i < end - 2; i++) {
            for (int j = i + 2; j < end; j++) {
                int index = getStraightIndex(i, j);
                foundStraights.set(index, true);
            }
        }
    }
/*
    // oddCharactersOut: return a list of all characters not found
    // There may be multiple possibilities, due to there being duplicates,
    // so it returns a list of lists.
    // All indices must be in ascending order.
    private static List<List<Integer>> oddCharactersOut(String word) {
        List<List<Integer>> allPossibilities = new ArrayList<>();
        for ()
        return allPossibilities;
    }

    private static void calculateStraights(String word) {
        // how do we determine if a straight is possible?
        // we can start with checking if the characters for indices 0, 1, and 2
        //   are all separately found
        // if any characters match, ALL must be found
        // if [2] is not found, check [5]
        // if [5] is not found, no straights are possible

        // if [2] is found, we can check all straights that start with 0, 1, and 2
        // check if [1] is found... if yes, check if [0] is found
        // if yes, add to straights

        // alternatively, we can determine what straights are possible by what
        //  letters were NOT found
        // for a 5-letter word, there will be 3 letters that weren't selected
        if (word.length() == 8) {
            // everything is a straight
            for (int i = 0; i < allStraights.size(); i++) {
                allStraights.set(i, allStraights.get(i) + 1);
            }
        } else if (word.length() == 7) {
            // find the character(s) that doesn't belong
            List<List<Integer>> unfoundLists = oddCharactersOut(word);
            // 0, 5, 6...
            // we want to add all straights from 1 to 7,
            // but we don't want to duplicate straights for 1 to 4
            Set<Integer> allPossibleStraights = new HashSet<>();
            for (List<Integer> unfoundList: unfoundLists) {
                Integer notFound = unfoundList.get(0);
                addAllStraightsInRange(0, notFound - 1);
                addAllStraightsInRange(notFound + 1, letters.size());
            }
        }
    }*/

    // Instead of worrying about missing letters,
    // let's try looking solely at arrays of arrays, containing all
    // possible combinations of letters, and searching them for straights.
    // To create an array of arrays, we need to find all duplicate letters
    // in both the word and in the current list of letters.
    // Then, for each copy of an existing letter, we add a new array
//    private static void findStraights(String word) {
////         createLetterMap();
//        // build map for word
//        // order is not important for a word, only which letters were used and how many
//        Map<Character, Integer> letterCount = new HashMap<>();
//        for (int i = 0; i < word.length(); i++) {
//            Character letter = word.charAt(i);
//            if (letterCount.containsKey(letter)) {
//                letterCount.put(letter, letterCount.get(letter) + 1);
//            } else {
//                letterCount.put(letter, 1);
//            }
//        }
//        // now that we have a frequency count for each letter in the word,
//        // we build an array for every permutation from the letter indices.
//        // For example, if there are 4 copies of a letter, and our word uses 2,
//        // we need to create 6 arrays just for these letters.
//        // That means we go through the letterCount map
//        List<List<Integer>> possibleSelections = new ArrayList<>(new ArrayList<>());
//        Iterator<Character> wordLetters = letterCount.keySet().iterator();
//        while (wordLetters.hasNext()) {
//            Character letter = wordLetters.next();
//            expandPermutations(possibleSelections,
//                    letterIndices.get(letter), letterCount.get(letter));
//        }
//        // Now, sort every array, and mark off every one that has a straight.
//        // DON'T ADD STRAIGHT-COUNTS. Tally them, then add at the end.
//        Integer capacity = getStraightIndex(0, letters.size());
//        List<Boolean> straightTally = new ArrayList<>(capacity);
//        for (int i = 0; i < capacity; i++) {
//            straightTally.set(i, false);
//        }
//        for (int i = 0; i < possibleSelections.size(); i++) {
//            List<Integer> selection = possibleSelections.get(i);
//            selection.sort(Comparator.naturalOrder());
//            int lowestStraight = -1;
//            int highestStraight = -1;
//            for (int j = 2; j < selection.size(); j++) {
//                if (lowestStraight == highestStraight) {
//                    // none found yet
//                    if (selection.get(j-2) + 1 == selection.get(j-1) &&
//                        selection.get(j-1) + 1 == selection.get(j)) {
//                        lowestStraight = j - 2;
//                        highestStraight = j;
//                    }
//                } else {
//                    // currently in a straight, keep checking
//                    if (selection.get(j-1) + 1 == selection.get(j)) {
//                        // expand straight
//                        highestStraight = j;
//                    } else {
//                        // set the straights
//                        setAllStraightsInRange(straightTally, lowestStraight, highestStraight);
//                        lowestStraight = j;
//                        highestStraight = j;
//                    }
//                }
//            }
//            // if loop ends on a straight, adjust it
//            if (lowestStraight != highestStraight) {
//                setAllStraightsInRange(straightTally, lowestStraight, highestStraight);
//            }
//        }
//        for (int i = 0; i < straightTally.size(); i++) {
//            if (straightTally.get(i)) {
//                allStraights.set(i, allStraights.get(i) + 1);
//            }
//        }
//    }

    // add if straight does something different;
    // it ONLY adds a straight if ALL the letters can form a straight
    private static void addIfStraight(String word) {
//         createLetterMap();
        // build map for word
        // order is not important for a word, only which letters were used and how many
        Map<Character, Integer> letterCount = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            Character letter = word.charAt(i);
            if (letterCount.containsKey(letter)) {
                letterCount.put(letter, letterCount.get(letter) + 1);
            } else {
                letterCount.put(letter, 1);
            }
        }
        // now that we have a frequency count for each letter in the word,
        // we build an array for every permutation from the letter indices.
        // For example, if there are 4 copies of a letter, and our word uses 2,
        // we need to create 6 arrays just for these letters.
        // That means we go through the letterCount map
        List<List<Integer>> possibleSelections = new ArrayList<>(new ArrayList<>());
        Iterator<Character> wordLetters = letterCount.keySet().iterator();
//        System.out.println("addIfStraight on " + letterCount.keySet().toString());
        while (wordLetters.hasNext()) {
            Character letter = wordLetters.next();
//            System.out.print("expandPermutations(");
//            System.out.print(possibleSelections);
//            System.out.print(",");
//            System.out.print(letterIndices.get(letter));
//            System.out.println("," + letterCount.get(letter) + ")");
            possibleSelections = expandPermutations(possibleSelections,
                    letterIndices.get(letter), letterCount.get(letter));
        }
        // now check if at least
        boolean notFound = true;
        Iterator<List<Integer>> selectionIter = possibleSelections.iterator();
        while (notFound && selectionIter.hasNext()) {
            List<Integer> selection = selectionIter.next();
            Collections.sort(selection);
            if (selection.get(selection.size()-1) - selection.get(0) == selection.size() - 1) {
                notFound = false;
//                System.out.println("Straight " + selection + " found for " + word);
            }
        }
        if (!notFound) {
            addToStraightScore(word.length());
        }
    }

    private static void addToStraightScore(int length) {
        switch(length) {
            case 3:
                straightPoints += 4;
                break;
            case 4:
                straightPoints += 8;
                break;
            case 5:
                straightPoints += 16;
                break;
            case 6:
                straightPoints += 25;
                break;
            case 7:
            case 8:
                straightPoints += 30;
                break;

            default:
        }
    }

    public static Integer getStraightPoints() {
        return straightPoints;
    }

    // This handles the generic code for creating permutations
    // of the current "whole" list with new additions in "part" list.
    // ( [[1,2],[1,4]] , [3,5], 1) -> [[1,2,3],[1,4,3],[1,2,5],[1,4,5]]
    // ( [[1,2],[1,4]] , [3,5], 2) -> [[1,2,3,5],[1,4,3,5]]
    public static List<List<Integer>> expandPermutations(
            List<List<Integer>> whole, List<Integer> part, Integer draws) {
        if (draws > part.size() || draws < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (draws == 0) return whole;
        List<List<Integer>> expandedWhole = new ArrayList<>();
        // create an array of arrays to contain every possible draw
        List<Integer> iterator = new ArrayList<>();
        int lastMovedIndex = draws - 1;
        // initialize iterator
        for (int i = 0; i < draws; i++) {
            iterator.add(i);
        }
        if (whole.size() == 0) {
            whole.add(new ArrayList<>());
        }
        // append iterator references to the whole, add to a larger array
        for (List<Integer> oneList : whole) {
            List<Integer> addToNewWhole = new ArrayList<>(oneList);
            for (int j = 0; j < iterator.size(); j++) {
                addToNewWhole.add(part.get(iterator.get(j)));
            }
            expandedWhole.add(addToNewWhole);
        }
        // basic algorithm: there are n values in the iterator
        //   each representing an index of the part array.
        // each step, see if the lastMovedIndex can be increased
        //    If yes, increase the iterator's value in its index
        //    If not, decrease the index, and repeat
        while (lastMovedIndex >= 0) {
//            System.out.println("lastMovedIndex: " + lastMovedIndex);
//            System.out.println("iterator: " + iterator);
//            System.out.println("part.size(): " + part.size());
//            System.out.println("canMove(lastMovedIndex, iterator, part.size()): "
//                    + canMove(lastMovedIndex, iterator, part.size()));
            while (canMove(lastMovedIndex, iterator, part.size())) {
                iterator.set(lastMovedIndex, iterator.get(lastMovedIndex) + 1);
                for (int i = lastMovedIndex + 1; i < iterator.size(); i++) {
                    iterator.set(i, iterator.get(i-1) + 1);
                }
                // append iterator references to the whole, add to a larger array
                for (List<Integer> oneList : whole) {
                    List<Integer> addToNewWhole = new ArrayList<>(oneList);
                    for (int j = 0; j < iterator.size(); j++) {
                        addToNewWhole.add(part.get(iterator.get(j)));
                    }
                    expandedWhole.add(addToNewWhole);
                }
            }
//            System.out.println(expandedWhole);
            lastMovedIndex--;
        }
        return expandedWhole;
    }

    // index: the index of indexArray in question we are trying to move
    // indexArray: the indices as they currently are
    // maxSize: the maximum index that is allowed
    //
    // This function determines if we can increase the index further without conflicting
    // with either other indices, or with the maximum value
    private static boolean canMove(int index, List<Integer> indexArray, int maxSize) {
        if (index < 0) return false;
        if (indexArray.get(index) >= maxSize - 1) return false;
        if (index == indexArray.size() - 1) return true; // if the last item of the array
        if ((indexArray.get(index) + 1) == indexArray.get(index + 1)) return false;
        return true;
    }

//    public static ImmutablePair<Integer, Integer> getBestStraightIndices() {
//        Integer bestIndex = 0;
//        Integer bestScore = 0;
//        for (int i = 0; i < allStraights.size(); i++) {
//            Integer thisScore = straightScore(i);
//            if (bestScore < thisScore) {
//                bestIndex = i;
//                bestScore = thisScore;
//            }
//        }
//        return indexToStartAndEnd(bestIndex);
//    }

//    public static Integer getBestStraightScore() {
//        Integer bestScore = 0;
//        for (int i = 0; i < allStraights.size(); i++) {
//            Integer thisScore = straightScore(i);
//            if (bestScore < thisScore) {
//                bestScore = thisScore;
//            }
//        }
//        return bestScore;
//    }
}
