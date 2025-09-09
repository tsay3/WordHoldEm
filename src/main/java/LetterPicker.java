import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LetterPicker {
    /* The LetterPicker class is called when a new list of 8 letters is needed.
    * Each call is independent, and does not guarantee the same letters aren't returned.
    * However, it does guarantee that certain letters aren't called multiple times.
    *
    * Every round, a letter is picked from one of different pools */

    private static final char[] allVowels = {'e', 'a', 'i', 'o', 'u', 'e', 'a', 'i', 'o', 'u', 'y'};
    private static final char[] oddVowels = {'a', 'i', 'o'};
    private static final char[] repeatableVowels = {'e', 'e', 'e', 'a', 'i', 'o'};
    private static final char[] commonConsonants = {'d', 's', 's', 't', 'r', 'r'};
    private static final char[] upperConsonants = {
                'c', 'c', 'd', 'd', 'g', 'g', 'h', 'l', 'l', 'm', 'm', 'n',
                'n', 'p', 'p', 'r', 's', 't', 't', 'w'};
    private static final char[] midConsonants = {
                'b', 'b', 'c', 'd', 'f', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n',
                'p', 'r', 's', 't', 'v', 'w', 'y'};

    private static final char[] oneOffLetters = {'q', 'x', 'z'};

    private static final char[] commonLetters = {'e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd'};

    private static char getRandomFromPool(char[] pool) {
        int index = (int) (Math.random() * pool.length);
        return pool[index];
    }

    public static List<Character> getNewLetters() {
        List<Character> newLetters = new ArrayList<>();
        char lastCharacter;

        if (Math.random() < 0.15) {
            newLetters.add(lastCharacter = getRandomFromPool(oneOffLetters));
        } else {
            newLetters.add(lastCharacter = getRandomFromPool(midConsonants));
        }

        if (lastCharacter == 'q') {
            newLetters.add('u');
        } else if (lastCharacter == 'x') {
            newLetters.add(getRandomFromPool(oddVowels));
        } else if (Math.random() < 0.5 && lastCharacter != 'y') {
            newLetters.add(getRandomFromPool(midConsonants));
        } else {
            newLetters.add(getRandomFromPool(upperConsonants));
        }

        if (Math.random() < 0.4 ) {
            newLetters.add(getRandomFromPool(upperConsonants));
        } else {
            newLetters.add(getRandomFromPool(commonConsonants));
        }

        if (Math.random() < 0.6) {
            newLetters.add(getRandomFromPool(allVowels));
        } else {
            newLetters.add(getRandomFromPool(commonConsonants));
        }

        newLetters.add(getRandomFromPool(repeatableVowels));
        newLetters.add(getRandomFromPool(commonLetters));

        if (Math.random() < 0.5) {
            newLetters.add(lastCharacter = getRandomFromPool(allVowels));
        } else {
            newLetters.add(lastCharacter = getRandomFromPool(midConsonants));
        }

        if (lastCharacter == 'y') {
            newLetters.add(getRandomFromPool(upperConsonants));
        } else if (Math.random() < 0.5) {
            newLetters.add(getRandomFromPool(allVowels));
        } else {
            newLetters.add(getRandomFromPool(midConsonants));
        }

        Collections.shuffle(newLetters);

        return newLetters;
    }
}
