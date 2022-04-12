import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Model extends Observable {

    private static final boolean ERROR_MODE = false;
    private static final boolean TEST_MODE = false;
    private static final boolean RANDOM_MODE = false;
    private static final int MAX_GUESSES = 6;

    private final List<String> secretWordBank;
    private final List<String> validWordBank;

    private ArrayList<Character> correctAnswerArrayList;
    private ArrayList<Character> guessArrayList;

    private int guesses = 0;
    private int gamesCompleted = 0;
    private boolean gameOver = false;

    private ArrayList<Character> greenLetters;
    private ArrayList<Character> yellowLetters;
    private ArrayList<Character> greyLetters;
    private ArrayList<Character> darkGreyLetters;

    public Model(){
        Path secretWordPath = Paths.get("resources/common.txt");
        Path validWordPath = Paths.get("resources/words.txt");
        secretWordBank = readFile(secretWordPath);
        validWordBank = readFile(validWordPath);
        correctAnswerArrayList = new ArrayList<>(5);
        guessArrayList = new ArrayList<>(5);
        greenLetters = new ArrayList<>();
        yellowLetters = new ArrayList<>();
        greyLetters = new ArrayList<>();
        darkGreyLetters = new ArrayList<>();
    }

    protected ArrayList<Character> getCorrectAnswerArrayList() {
        return correctAnswerArrayList;
    }
    protected ArrayList<Character> getGuessArrayList() {
        return guessArrayList;
    }

    protected List<String> readFile (Path filePath) {
        List<String> words = null;
        try {
            words = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    protected boolean isValid(String input) {
        return validWordBank.contains(input);
    }

    protected boolean isGameOver() {
        return gameOver;
    }

    protected void setCorrectWord(int gamesCompleted) {
        String word = secretWordBank.get(gamesCompleted);
        toCharArrayList(word, correctAnswerArrayList);
    }

    protected void setGuessedWord(String guess) {
        toCharArrayList(guess, guessArrayList);
    }

    protected int getGamesCompleted() {
        return gamesCompleted;
    }

    protected void increaseGamesCompleted() {
        gamesCompleted += 1;
    }

    protected void increaseGuesses() {
        guesses += 1;
    }

    protected void setRandomCorrectWord() {
        String randomWord = secretWordBank.get((int)(Math.random() * secretWordBank.size() - 1));
        toCharArrayList(randomWord, correctAnswerArrayList);
    }

    protected void toCharArrayList(String string, ArrayList<Character> arrayList) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            arrayList.add(c);
        }
    }

    protected ArrayList<Character> getGreenLetters() {
        return greenLetters;
    }
    protected ArrayList<Character> getYellowLetters() {
        return yellowLetters;
    }
    protected ArrayList<Character> getDarkGreyLetters() {
        return darkGreyLetters;
    }
    protected ArrayList<Character> getGreyLetters() {
        return greyLetters;
    }



    protected void checkForGreen() {
        int index = 0;
        for (char c : guessArrayList) {
            if (c == correctAnswerArrayList.get(index))
                greenLetters.add(c); index++;
        }
    }

    protected void checkForYellow() {
        int index = 0;
        for (char c : guessArrayList) {
            if (c != correctAnswerArrayList.get(index) && correctAnswerArrayList.contains(c))
                yellowLetters.add(c); index++;
        }
    }

    protected void checkForDarkGrey() {
        for (char c : guessArrayList) {
            if (!correctAnswerArrayList.contains(c))
                darkGreyLetters.add(c);
        }
    }

    protected void sortArrayList(ArrayList<Character> arrayList)  {
        Collections.sort(arrayList);
    }

    /**
     * Methods for testing
     */
    protected void printGuessArrayList() {
        System.out.println(guessArrayList);
    }
    protected void printCorrectAnswerArrayList() {
        System.out.println(correctAnswerArrayList);
    }
    protected void printGreenLetters() {
        System.out.println(greenLetters);
    }
    protected void printYellowLetters() {
        System.out.println(yellowLetters);
    }
    protected void printDarkGreyLetters() {
        System.out.println(darkGreyLetters);
    }
}