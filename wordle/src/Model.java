import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Model extends Observable {

    private static final boolean ERROR_MODE = true;
    private static final boolean TEST_MODE = false;
    private static final boolean RANDOM_MODE = false;
    private static final int MAX_GUESSES = 6;
    private static final int MAX_WORD_LENGTH = 5;

    private int guesses;
    private int gamesCompleted = 0;
    private boolean gameOver;

    private final List<String> secretWordBank;
    private final List<String> validWordBank;

    private final ArrayList<Character> correctAnswerArrayList;
    private final ArrayList<Character> guessArrayList;
    private final ArrayList<Character> greenLetters;
    private final ArrayList<Character> yellowLetters;
    private final ArrayList<Character> greyLetters;
    private final ArrayList<Character> darkGreyLetters;

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
        initialise();
    }

    private List<String> readFile (Path filePath) {
        List<String> words = null;
        try {
            words = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
    protected void initialise() {
        setGameOver(false);
        guesses = 0;
        getCorrectAnswerArrayList().clear();
        greenLetters.clear();
        yellowLetters.clear();
        darkGreyLetters.clear();
        if (isRandomMode()) {
            setRandomCorrectWord();
        } else {
            setCorrectWord(getGamesCompleted());
        }
    }

    protected boolean isValid(String input) {
        return validWordBank.contains(input) || secretWordBank.contains(input);
    }
    protected boolean isGameOver() {
        return gameOver;
    }
    protected boolean isErrorMode() {return ERROR_MODE;}
    protected boolean isTestMode() {return TEST_MODE;}
    protected boolean isRandomMode() {return RANDOM_MODE;}
    protected int getMaxGuesses() {return MAX_GUESSES;}
    protected int getMaxWordLength() {return MAX_WORD_LENGTH;}
    protected int getNumberOfGuesses() {return guesses;}
    protected int getGamesCompleted() {return gamesCompleted;}

    protected void setGameOver(Boolean bool) {
        gameOver = bool;
    }
    protected void setCorrectWord(int gamesCompleted) {
        String word = secretWordBank.get(gamesCompleted);
        toCharArrayList(word, correctAnswerArrayList);
    }
    protected void setGuessedWord(String guess) {
        toCharArrayList(guess, guessArrayList);
    }
    protected void setRandomCorrectWord() {
        String randomWord = secretWordBank.get((int)(Math.random() * secretWordBank.size() - 1));
        toCharArrayList(randomWord, correctAnswerArrayList);
    }
    protected ArrayList<Character> getCorrectAnswerArrayList() {
        return correctAnswerArrayList;
    }
    protected ArrayList<Character> getGuessArrayList() {
        return guessArrayList;
    }

    protected void increaseGuesses() {
        guesses++;
    }
    protected void increaseGamesCompleted() {gamesCompleted++;}
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
            if (c == correctAnswerArrayList.get(index) && !greenLetters.contains(c)) {
                greenLetters.add(c);
            }
            index++;
        }
    }
    protected void checkForYellow() {
        int index = 0;
        for (char c : guessArrayList) {
            if (c != correctAnswerArrayList.get(index) && correctAnswerArrayList.contains(c) && !yellowLetters.contains(c)) {
                yellowLetters.add(c);
            }
            index++;
        }
    }
    protected void checkForDarkGrey() {
        for (char c : guessArrayList) {
            if (!correctAnswerArrayList.contains(c) && !darkGreyLetters.contains(c)) {
                darkGreyLetters.add(c);
            }
        }
    }
    protected void checkColours() {
        checkForGreen();
        checkForYellow();
        checkForDarkGrey();
        setChanged();
        notifyObservers(); // notify view after all checks have been done
    }
    protected void sortArrayList(ArrayList<Character> arrayList)  {
        Collections.sort(arrayList);
    }
    protected void toCharArrayList(String string, ArrayList<Character> arrayList) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            arrayList.add(c);
        }
    }
}