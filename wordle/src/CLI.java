import java.util.Scanner;

public class CLI {

    static Model model;

    public static void main(String[] args) {
        testCode();
    }

    public static void testCode() {
        final int CONSTRAINT = 5;
        String input;
        Scanner scanner = new Scanner(System.in);
        model = new Model();
        model.setCorrectWord(model.getGamesCompleted());
        model.printCorrectAnswerArrayList();
        System.out.println("--------------------------------");
        input = scanner.nextLine();
        while (input.length() != CONSTRAINT || !model.isValid(input)) {
            System.out.println("enter a valid word with exactly 5 letters");
            input = scanner.nextLine();
        }
        model.setGuessedWord(input);
        model.printGuessArrayList();
        System.out.println("-------------Green Letters-------------------");
        model.checkForGreen();
        model.sortArrayList(model.getGreenLetters());
        model.printGreenLetters();
        System.out.println("--------------Yellow Letters-----------------");
        model.checkForYellow();
        model.sortArrayList(model.getYellowLetters());
        model.printYellowLetters();
        System.out.println("--------------Dark Grey Letters-----------------");
        model.checkForDarkGrey();
        model.sortArrayList(model.getDarkGreyLetters());
        model.printDarkGreyLetters();
    }
    public static void displayResult() {
        if (model.getCorrectAnswerArrayList().equals(model.getGuessArrayList())) {
            System.out.println("You won!");
        }
        else
            System.out.println("You lost!");
    }
}