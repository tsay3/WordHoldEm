// played over a series of 9 rounds
// for the first 8 rounds:
//  call the LetterPicker for new Letters
// for the final round:
//   use the letters picked previously
// for each round:
//   allow the player to enter words for 60 seconds
// for the first 8 rounds:
//   allow the player to pick one of remaining categories and 8 letters
//     to count current points and hold their letter for next turn
// for the final round:
//   allow the player to pick any category for additional points
public class App {

    private static void setup() {
        Dictionary.buildDictionary();
    }

    public static void main(String[] args) {
        setup();
    }
}
