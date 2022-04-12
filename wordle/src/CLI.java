import java.util.Scanner;

public class CLI {

    static Model model;

    public static void main(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);
        model = new Model();
        if (model.isRandomMode()){ //choose answer based on flags
            model.setRandomCorrectWord();
        }
        else
            model.setCorrectWord(model.getGamesCompleted());

        while (!model.isGameOver()) {
            model.getGuessArrayList().clear(); //clear the guess array of previous guesses
            if (model.isTestMode())
                printCorrectAnswerArrayList();
            prompt();
            input = scanner.nextLine();
            if (model.isErrorMode()) {
                while (!model.isValid(input)) {
                    prompt();
                    input = scanner.nextLine();
                }
            }
            else {
                while (input.length() != model.getMaxWordLength()) {
                    prompt();
                    input = scanner.nextLine();
                }
            }
            model.setGuessedWord(input);
            printGuessArrayList();
            if (model.getGuessArrayList().equals(model.getCorrectAnswerArrayList())) {
                displayWin();
                model.increaseGamesCompleted();
                model.setGameOver();
            }
            else {
                checkForColours();
                sortColours();
                displayColourHints();
                model.increaseGuesses();
            }
            if (model.getNumberOfGuesses() == model.getMaxGuesses()) {
                displayLoss();
                model.increaseGamesCompleted();
                model.setGameOver();
            }
        }
    }

    public static void checkForColours() {
        model.checkForGreen();
        model.checkForYellow();
        model.checkForDarkGrey();
    }
    public static void sortColours() {
        model.sortArrayList(model.getGreenLetters());
        model.sortArrayList(model.getYellowLetters());
        model.sortArrayList(model.getDarkGreyLetters());
    }
    public static void displayColourHints() {
        System.out.println("-------------Green Letters-------------------");
        printGreenLetters();
        System.out.println("--------------Yellow Letters-----------------");
        printYellowLetters();
        System.out.println("--------------Dark Grey Letters-----------------");
        printDarkGreyLetters();
        System.out.println("-------------------------------------------------");
    }
    public static void prompt() {System.out.println("enter a valid word with exactly 5 letters");}
    public static void printGuessArrayList() {System.out.println("Your guess is: " + model.getGuessArrayList());}
    public static void printCorrectAnswerArrayList() {System.out.println("The answer is: " + model.getCorrectAnswerArrayList());}
    public static void printGreenLetters() {System.out.println(model.getGreenLetters());}
    public static void printYellowLetters() {System.out.println(model.getYellowLetters());}
    public static void printDarkGreyLetters() {System.out.println(model.getDarkGreyLetters());}
    public static void displayWin() {System.out.println("You won!");}
    public static void displayLoss() {System.out.println("You lost!");}
}