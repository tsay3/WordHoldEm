package util;

import java.util.ArrayList;
import java.util.List;

public class Combinations {

    // This handles the generic code for creating permutations
    // of the current "whole" list with new additions in "part" list.
    public static List<List<Integer>> expandPermutations(
            List<List<Integer>> whole, List<Integer> part, Integer draws) {
        if (draws > part.size() || draws < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (draws == 0) return whole;
        // create an array of arrays to contain every possible draw
        List<List<Integer>> expandedWhole = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        int lastMovedIndex = draws - 1;
        // initialize indices
        for (int i = 0; i < draws; i++) {
            indices.add(i);
        }
        if (whole.isEmpty()) {
            whole.add(new ArrayList<>());
        }
        // append indices references to the whole, add to a larger array
        for (List<Integer> oneList : whole) {
            List<Integer> addToNewWhole = new ArrayList<>(oneList);
            for (Integer integer : indices) {
                addToNewWhole.add(part.get(integer));
            }
            expandedWhole.add(addToNewWhole);
        }
        // basic algorithm: there are n values in the indices
        //   each representing an index of the part array.
        // each step, see if the lastMovedIndex can be increased
        //    If yes, increase the indices's value in its index
        //    If not, decrease the index, and repeat
        while (lastMovedIndex >= 0) {
            while (canMove(lastMovedIndex, indices, part.size())) {
                indices.set(lastMovedIndex, indices.get(lastMovedIndex) + 1);
                for (int i = lastMovedIndex + 1; i < indices.size(); i++) {
                    indices.set(i, indices.get(i-1) + 1);
                }
                // append indices references to the whole, add to a larger array
                for (List<Integer> oneList : whole) {
                    List<Integer> addToNewWhole = new ArrayList<>(oneList);
                    for (Integer integer : indices) {
                        addToNewWhole.add(part.get(integer));
                    }
                    expandedWhole.add(addToNewWhole);
                }
            }
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
        return (indexArray.get(index) + 1) != indexArray.get(index + 1);
    }
}