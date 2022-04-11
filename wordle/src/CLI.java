import java.awt.Color;
import java.util.Scanner;

public class CLI {

    static Model model;
    private final Color wordleGreen = new Color(0x538d4e);
    private final Color wordleYellow = new Color(0xb59f3b);

    public static void main(String[] args) {
        testCode();
    }
    public static void testCode() {
        final int CONSTRAINT = 5;
        String input;
        Scanner scanner = new Scanner(System.in);
        model = new Model();
        model.printSecretWords();
        model.setCorrectWord(model.getGamesCompleted());
        model.printCorrectAnswerArrayList();
        model.clear(model.getCorrectAnswerArrayList());
        model.setRandomCorrectWord();
        model.printCorrectAnswerArrayList();
        System.out.println("--------------------------------");
        input = scanner.nextLine();
        while (input.length() != CONSTRAINT) {
            System.out.println("try again with exactly 5 letters");
            input = scanner.nextLine();
        }
        model.setGuessedWord(input);
        model.printGuessArrayList();
        model.removeLastLetter();
        model.removeLastLetter();
        model.printGuessArrayList();
    }
}