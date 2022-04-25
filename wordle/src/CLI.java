import java.util.Collections;
import java.util.Scanner;

/**
 * @author Daniel Price 2022-04
 */

public class CLI {

    static Model model;
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void main(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);
        model = new Model();

        while (model.getGamesCompleted() < model.getSecretWordBankLength()) { //while we haven't guessed every possible word
            while (!model.isGameOver()) {
                model.getGuessArrayList().clear(); //clear the guess array of previous guesses
                if (model.isTestMode())
                    printCorrectAnswerArrayList();
                prompt();
                input = scanner.nextLine().toLowerCase();
                if (model.isErrorMode()) {
                    while (!model.isValid(input)) {
                        prompt();
                        input = scanner.nextLine().toLowerCase();
                    }
                } else {
                    while (input.length() != model.getMaxWordLength()) {
                        prompt();
                        input = scanner.nextLine().toLowerCase();
                    }
                }
                model.setGuessedWord(input);
                printGuessArrayList();
                if (model.getGuessArrayList().equals(model.getCorrectAnswerArrayList())) {
                    displayWin();
                    model.setGameOver(true);
                } else {
                    model.changeColours();
                    sortColours();
                    displayColourHints();
                    model.increaseGuesses();
                }
                if (model.getNumberOfGuesses() == model.getMaxGuesses()) {
                    displayLoss();
                    model.setGameOver(true);
                }
            }
            model.increaseGamesCompleted();
            model.initialise();
            announceNewGame();
        }
    }

    public static void sortColours() {
        Collections.sort(model.getGreenLetters());
        Collections.sort(model.getYellowLetters());
        Collections.sort(model.getDarkGreyLetters());
        Collections.sort(model.getGreyLetters());
    }
    public static void displayColourHints() {
        System.out.println(ANSI_GREEN + "-------------Green Letters-------------------");
        printGreenLetters();
        System.out.println(ANSI_YELLOW + "--------------Yellow Letters-----------------");
        printYellowLetters();
        System.out.println(ANSI_RESET + "--------------Dark Grey Letters-----------------");
        printDarkGreyLetters();
        System.out.println("------------------Grey Letters---------------------");
        printGreyLetters();
        System.out.println("-------------------------------------------------");
    }
    public static void announceNewGame() {
        System.out.println("You have completed: " + model.getGamesCompleted() + " game(s)!\n");
        System.out.println("Starting next game...! \n");
    }
    public static void prompt() {System.out.println("enter a valid word with exactly 5 letters");}
    public static void printGuessArrayList() {System.out.println("Your guess is: " + model.getGuessArrayList());}
    public static void printCorrectAnswerArrayList() {System.out.println("The answer is: " + model.getCorrectAnswerArrayList());}
    public static void printGreenLetters() {System.out.println(model.getGreenLetters());}
    public static void printYellowLetters() {System.out.println(model.getYellowLetters());}
    public static void printDarkGreyLetters() {System.out.println(model.getDarkGreyLetters());}
    public static void printGreyLetters() {System.out.println(model.getGreyLetters());}
    public static void displayWin() {System.out.println("You won!");}
    public static void displayLoss() {System.out.println("You lost!");}
}