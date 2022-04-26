import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
class ModelTest {

    Model model;

    @BeforeEach
    void setUp() {
        model = new Model();
        model.initialise();
    }

    /**
     * Test the isValid() method to ensure correctly matching words returns true
     */
    @Test
    @Order(1)
    @DisplayName("ensure returns true if input matches word in secretWordBank or validWordBank")
    void testIsValid() {
        String input = "abcde";
        assertFalse(model.isValid(input));
        input = "cigar"; // first word of common.txt
        assertTrue(model.isValid(input));
        input = "shave"; // last word of common.txt
        assertTrue(model.isValid(input));
        input = "aahed"; // first word of words.txt
        assertTrue(model.isValid(input));
        input = "zymic"; // last word of words.txt
        assertTrue(model.isValid(input));
    }

    @Test
    @Order(2)
    @DisplayName("ensure guessed word is 5 chars long and guessArrayList is replaced by the new guessed word")
    void testSetGuessedWord() {
        model.setGuessedWord("paper");
        assertEquals("[p, a, p, e, r]", model.getGuessArrayList().toString());
        assertEquals(model.getGuessArrayList().size(), model.getMaxWordLength());
    }

    /**
     * Using the first word of common.txt which is 'cigar', test that correct letters enter the appropriate array
     * green array for correct letter && correct index
     * yellow array for correct letter && incorrect index
     * dark grey array for incorrect letters
     */
    @Test
    @Order(3)
    @DisplayName("ensure correct characters enter coloured arrays")
    void testChangeColours() {
        model.setCorrectWord(0); //cigar
        model.setGuessedWord("cider");
        model.changeColours();
        assertEquals("[c, i, r]", model.getGreenLetters().toString());
        assertEquals("[d, e]", model.getDarkGreyLetters().toString());
        model.setGuessedWord("grave");
        model.changeColours();
        assertEquals("[g, r, a]", model.getYellowLetters().toString());
        assertEquals("[d, e, v]", model.getDarkGreyLetters().toString());
    }
}