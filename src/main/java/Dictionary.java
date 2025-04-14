import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {

    private static List<String> wordList;

    public static void buildDictionary() {
        wordList = new ArrayList<>();
//        File dictFile =
        URL dictURL = Dictionary.class.getClassLoader().getResource(
                "dict/wordlist-main/wordlist.txt");

        try {
            String fullDict = Files.readString(Paths.get(dictURL.toURI()), Charset.defaultCharset());
            int dictSize = fullDict.length() - 1;
            System.out.println("dictSize: " + dictSize);
            int index = 0;
            while (index < dictSize) {
                int nextNewline = fullDict.indexOf("\n", index);
                String word = fullDict.substring(index, nextNewline);
                wordList.add(word);
                index = nextNewline + 1;
            }
        } catch (IOException | URISyntaxException e) {

        }
    }

    // isValidWord takes a possible string
    // and determines whether it is a valid English word
    public static boolean isValidWord(String word) {
        if (wordList == null) return false;
        int low = 0;
        int high = wordList.size() - 1;
        while (low <= high) {
            int middle = (low + high) / 2;
            String testWord = wordList.get(middle);
            int comparison = word.compareTo(testWord);
            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                high = middle - 1;
            } else {
                low = middle + 1;
            }
        }
        return false;
    }
}
