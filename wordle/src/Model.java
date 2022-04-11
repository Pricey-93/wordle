import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    private final boolean ERROR_MODE = false;
    private final boolean TEST_MODE = false;
    private final boolean RANDOM_MODE = false;
    private final Path SECRET_WORD_PATH = Paths.get("resources/common.txt");
    private final Path VALID_WORD_PATH = Paths.get("resources/words.txt");
    private final List<String> SECRET_WORD_BANK;
    private final List<String> VALID_WORD_BANK;
    private final int INITIAL_CAPACITY = 5;
    private ArrayList<Character> correctAnswerArrayList;
    private ArrayList<Character> guessArrayList;

    private int guesses = 0;
    private int gamesCompleted = 0;

    public Model(){
        SECRET_WORD_BANK = readFile(SECRET_WORD_PATH);
        VALID_WORD_BANK = readFile(VALID_WORD_PATH);
        correctAnswerArrayList = new ArrayList<>(INITIAL_CAPACITY);
        guessArrayList = new ArrayList<>(INITIAL_CAPACITY);
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

    protected void setCorrectWord(int gamesCompleted) {
        String word = SECRET_WORD_BANK.get(gamesCompleted);
        toCharArrayList(word, getCorrectAnswerArrayList());
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
        String randomWord = SECRET_WORD_BANK.get((int)(Math.random() * SECRET_WORD_BANK.size() - 1));
        toCharArrayList(randomWord, getCorrectAnswerArrayList());
    }

    protected void toCharArrayList(String string, ArrayList<Character> arrayList) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            arrayList.add(c);
        }
    }

    protected void clear(ArrayList<Character> arrayList) {
        arrayList.clear();
    }

    protected void removeLastLetter() {
        guessArrayList.remove(guessArrayList.size()-1);
        guessArrayList.trimToSize();
    }

    /**
     * Methods for testing
     */
    protected void printSecretWords() {
        System.out.println(SECRET_WORD_BANK);
    }
    protected void printGuessArrayList() {
        System.out.println(guessArrayList);
    }
    protected void printCorrectAnswerArrayList() {
        System.out.println(correctAnswerArrayList);
    }
}