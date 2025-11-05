package util;

import java.util.HashMap;
import java.util.Map;

public class LetterFrequencies {
    public static Map<Character, Integer> getLetterFrequency(char[] letters) {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (Character letter : letters) {
            frequencies.put(letter, frequencies.getOrDefault(letter, 0) + 1);
        }
        return frequencies;
    }
}