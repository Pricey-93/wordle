import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Daniel Price 2022-04
 */

public class Model extends Observable {

    private static final boolean ERROR_MODE = true;
    private static final boolean TEST_MODE = false;
    private static final boolean RANDOM_MODE = true;
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
    private void toCharArrayList(String string, ArrayList<Character> arrayList) {
        assert string != null && !string.equals("");
        assert arrayList.isEmpty();
        char[] chars = string.toCharArray();
        for (char c : chars) {
            arrayList.add(c);
        }
    }

    /**
     * @pre true
     * @post true
     */
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

    /**
     * @param input the String inputted by the user
     * @pre input is a non-null string
     * @post true
     * @return true iff input is present in validWordBank or secretWordBank
     */
    protected boolean isValid(String input) {
        assert input != null : "String value must not be null";
        return validWordBank.contains(input) || secretWordBank.contains(input);
    }

    /**
     * @pre true
     * @post true
     * @return gameOver
     */
    protected boolean isGameOver() {return gameOver;}

    /**
     * @pre true
     * @post true
     * @return ERROR_MODE
     */
    protected boolean isErrorMode() {return ERROR_MODE;}

    /**
     * @pre true
     * @post true
     * @return TEST_MODE
     */
    protected boolean isTestMode() {return TEST_MODE;}

    /**
     * @pre true
     * @post true
     * @return RANDOM_MODE
     */
    protected boolean isRandomMode() {return RANDOM_MODE;}

    /**
     * @pre true
     * @post true
     * @return MAX_GUESSES
     */
    protected int getMaxGuesses() {return MAX_GUESSES;}

    /**
     * @pre true
     * @post true
     * @return MAX_WORD_LENGTH
     */
    protected int getMaxWordLength() {return MAX_WORD_LENGTH;}

    /**
     * @pre true
     * @post true
     * @return guesses
     */
    protected int getNumberOfGuesses() {return guesses;}

    /**
     * @pre true
     * @post true
     * @return gamesCompleted
     */
    protected int getGamesCompleted() {return gamesCompleted;}

    /**
     * @param bool true or false
     * @pre bool is not null
     * @post this.gameOver == (\old(gameOver) = bool)
     */
    protected void setGameOver(Boolean bool) {
        assert bool != null : "bool must be non-null boolean";
        gameOver = bool;
    }

    /**
     * @param gamesCompleted the value of this.gamesCompleted
     * @pre the amount of games completed is no less than 0 and no more than the size of secretWordBank
     * @post this.correctAnswerArrayList = \old(correctAnswerArrayList) + secretWordBank.get(gamesCompleted)
     */
    protected void setCorrectWord(int gamesCompleted) {
        assert gamesCompleted >= 0 && gamesCompleted <= secretWordBank.size():
                "games completed must be between 0 and " + secretWordBank.size();
        String word = secretWordBank.get(gamesCompleted);
        toCharArrayList(word, correctAnswerArrayList);
    }

    /**
     * @param guess String inputted by user
     * @pre isValid(guess)
     * @post this.guessArrayList == \old(guessArrayList) + guess
     */
    protected void setGuessedWord(String guess) {
        assert isValid(guess);
        toCharArrayList(guess, guessArrayList);
    }

    /**
     * @pre true
     * @post this.correctAnswerArrayList == \old(correctAnswerArrayList) + randomWord
     */
    protected void setRandomCorrectWord() {
        String randomWord = secretWordBank.get((int)(Math.random() * secretWordBank.size() - 1));
        toCharArrayList(randomWord, correctAnswerArrayList);
    }

    /**
     * @pre true
     * @post true
     * @return correctAnswerArrayList
     */
    protected ArrayList<Character> getCorrectAnswerArrayList() {
        return correctAnswerArrayList;
    }

    /**
     * @pre true
     * @post true
     * @return guessArrayList
     */
    protected ArrayList<Character> getGuessArrayList() {
        return guessArrayList;
    }

    /**
     * @pre true
     * @post this.guesses == \old(guesses) + 1
     */
    protected void increaseGuesses() {
        guesses++;
    }

    /**
     * @pre true
     * @post this.gamesCompleted == \old(gamesCompleted) + 1
     */
    protected void increaseGamesCompleted() {gamesCompleted++;}

    /**
     * @pre true
     * @post true
     * @return greenLetters
     */
    protected ArrayList<Character> getGreenLetters() {
        return greenLetters;
    }

    /**
     * @pre true
     * @post true
     * @return yellowLetters
     */
    protected ArrayList<Character> getYellowLetters() {
        return yellowLetters;
    }

    /**
     * @pre true
     * @post true
     * @return darkGreyLetters
     */
    protected ArrayList<Character> getDarkGreyLetters() {
        return darkGreyLetters;
    }

    /**
     * @pre guessArrayList and correctAnswerArrayList are not empty
     * @post char c of guessArrayList is added to greenLetters iff char at current index matches
     * the char at correctAnswerArrayList of the same index && char c is not already present in greenLetters
     */
    protected void checkForGreen() {
        assert !guessArrayList.isEmpty() && !correctAnswerArrayList.isEmpty();
        int index = 0;
        for (char c : guessArrayList) {
            if (c == correctAnswerArrayList.get(index) && !greenLetters.contains(c)) { //if correct but already in green array, move to yellow array
                greenLetters.add(c);
            }
            index++;
        }
    }

    /**
     * @pre guessArrayList and correctAnswerArrayList are not empty
     * @post char c is added to yellowLetters iff c is present in correctAnswerArrayList but not at the same index
     * && c is not already present in yellowLetters
     */
    protected void checkForYellow() {
        assert !guessArrayList.isEmpty() && !correctAnswerArrayList.isEmpty();
        int index = 0;
        for (char c : guessArrayList) {
            if (c != correctAnswerArrayList.get(index) && correctAnswerArrayList.contains(c) && !yellowLetters.contains(c)) { // present but in the wrong location
                yellowLetters.add(c);
            }
            index++;
        }
    }

    /**
     * @pre guessArrayList and correctAnswerArrayList are not empty
     * @post char c is added to darkGreyLetters iff c is not present in correctAnswerArray && not already present in
     * darkGreyLetters
     */
    protected void checkForDarkGrey() {
        assert !guessArrayList.isEmpty() && !correctAnswerArrayList.isEmpty();
        for (char c : guessArrayList) {
            if (!correctAnswerArrayList.contains(c) && !darkGreyLetters.contains(c)) {
                darkGreyLetters.add(c);
            }
        }
    }

    /**
     * @pre true
     * @post true
     */
    protected void checkColours() {
        checkForGreen();
        checkForYellow();
        checkForDarkGrey();
        setChanged();
        notifyObservers(); // notify view after all checks have been done
    }

    /**
     * @param arrayList the ArrayList object to be sorted
     * @pre arrayList size is at least 2
     * @post each char c of arrayList is arranged in alphabetical order
     */
    protected void sortArrayList(ArrayList<Character> arrayList) {
        assert arrayList.size() > 1;
        Collections.sort(arrayList);
    }
}