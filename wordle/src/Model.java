import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Daniel Price 2022-04
 */

public class Model extends Observable {

    private static final boolean ERROR_MODE = true;
    private static final boolean TEST_MODE = true;
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
    private final ArrayList<Character> darkGreyLetters;
    private final ArrayList<Character> greyLetters;

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
        greyLetters = new ArrayList<>();
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
    public void initialise() {
        setGameOver(false);
        guesses = 0;
        getCorrectAnswerArrayList().clear();
        greenLetters.clear();
        yellowLetters.clear();
        darkGreyLetters.clear();
        greyLetters.clear();
        char[] chars = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p',
                'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
                'z', 'x', 'c', 'v', 'b', 'n', 'm'};
        for (char c : chars) {
            greyLetters.add(c);
        }
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
    public boolean isValid(String input) {
        assert input != null : "String value must not be null";
        return validWordBank.contains(input) || secretWordBank.contains(input);
    }

    /**
     * @pre true
     * @post true
     * @return gameOver
     */
    public boolean isGameOver() {return gameOver;}

    /**
     * @pre true
     * @post true
     * @return ERROR_MODE
     */
    public boolean isErrorMode() {return ERROR_MODE;}

    /**
     * @pre true
     * @post true
     * @return TEST_MODE
     */
    public boolean isTestMode() {return TEST_MODE;}

    /**
     * @pre true
     * @post true
     * @return RANDOM_MODE
     */
    public boolean isRandomMode() {return RANDOM_MODE;}

    /**
     * @pre true
     * @post true
     * @return MAX_GUESSES
     */
    public int getMaxGuesses() {return MAX_GUESSES;}

    /**
     * @pre true
     * @post true
     * @return MAX_WORD_LENGTH
     */
    public int getMaxWordLength() {return MAX_WORD_LENGTH;}

    /**
     * @pre true
     * @post true
     * @return secretWordBank.size()
     */
    public int getSecretWordBankLength() {return secretWordBank.size();}

    /**
     * @pre true
     * @post true
     * @return guesses
     */
    public int getNumberOfGuesses() {return guesses;}

    /**
     * @pre true
     * @post true
     * @return gamesCompleted
     */
    public int getGamesCompleted() {return gamesCompleted;}

    /**
     * @param bool true or false
     * @pre bool is not null
     * @post this.gameOver == (\old(gameOver) = bool)
     */
    public void setGameOver(Boolean bool) {
        assert bool != null : "must be non-null boolean";
        gameOver = bool;
    }

    /**
     * @param gamesCompleted the value of this.gamesCompleted
     * @pre the amount of games completed is no less than 0 and no more than the size of secretWordBank
     * @post this.correctAnswerArrayList = \old(correctAnswerArrayList) + secretWordBank.get(gamesCompleted)
     */
    public void setCorrectWord(int gamesCompleted) {
        assert gamesCompleted >= 0 && gamesCompleted <= secretWordBank.size():
                "games completed must be between 0 and " + secretWordBank.size();
        String word = secretWordBank.get(gamesCompleted);
        toCharArrayList(word, correctAnswerArrayList);
    }

    /**
     * @param guess String inputted by user
     * @pre Guess string length is equal to MAX_WORD_LENGTH
     * @post this.guessArrayList == \old(guessArrayList) + guess
     */
    public void setGuessedWord(String guess) {
        assert guess.length() == getMaxWordLength();
        toCharArrayList(guess, guessArrayList);
    }

    /**
     * @pre true
     * @post this.correctAnswerArrayList == \old(correctAnswerArrayList) + randomWord
     */
    public void setRandomCorrectWord() {
        String randomWord = secretWordBank.get((int)(Math.random() * secretWordBank.size() - 1));
        toCharArrayList(randomWord, correctAnswerArrayList);
    }

    /**
     * @pre true
     * @post true
     * @return correctAnswerArrayList
     */
    public ArrayList<Character> getCorrectAnswerArrayList() {
        return correctAnswerArrayList;
    }

    /**
     * @pre true
     * @post true
     * @return guessArrayList
     */
    public ArrayList<Character> getGuessArrayList() {
        return guessArrayList;
    }

    /**
     * @pre true
     * @post this.guesses == \old(guesses) + 1
     */
    public void increaseGuesses() {
        guesses++;
    }

    /**
     * @pre true
     * @post this.gamesCompleted == \old(gamesCompleted) + 1
     */
    public void increaseGamesCompleted() {gamesCompleted++;}

    /**
     * @pre true
     * @post true
     * @return greenLetters
     */
    public ArrayList<Character> getGreenLetters() {
        return greenLetters;
    }

    /**
     * @pre true
     * @post true
     * @return yellowLetters
     */
    public ArrayList<Character> getYellowLetters() {
        return yellowLetters;
    }

    /**
     * @pre true
     * @post true
     * @return darkGreyLetters
     */
    public ArrayList<Character> getDarkGreyLetters() {
        return darkGreyLetters;
    }

    /**
     * @pre true
     * @post true
     * @return greyLetters
     */
    public ArrayList<Character> getGreyLetters() {
        return greyLetters;
    }

    /**
     * @pre guessArrayList and correctAnswerArrayList are not empty
     * @post char c of guessArrayList is added to greenLetters iff char at current index matches
     * the char at correctAnswerArrayList of the same index && char c is not already present in greenLetters
     */
    public void addToGreenLetters() {
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
    public void addToYellowLetters() {
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
    public void addToDarkGreyLetters() {
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
    public void removeFromGreyLetters() { //remove letters that have already been found or guessed
        greenLetters.forEach(greyLetters::remove);
        yellowLetters.forEach(greyLetters::remove);
        darkGreyLetters.forEach(greyLetters::remove);
    }

    /**
     * @pre true
     * @post true
     */
    public void changeColours() {
        addToGreenLetters();
        addToYellowLetters();
        addToDarkGreyLetters();
        removeFromGreyLetters();
        setChanged();
        notifyObservers(); // notify view after all changes have been made
    }
}