import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Daniel Price 2022-04
 */

public class Controller {

    private final Model model;
    private View view;

//-------------------------Action listeners for GUI---------------------------
    private class inputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { //verify and then pass input on return keypress in inputField
            if (!isInputLengthValid()) {
                refuseInput();
            }
            else if (model.isErrorMode() && !model.isValid(view.getInput())) { //guess is not in either word list
                refuseInput();
            }
            else {
                passInput();
                checkColours();
                model.increaseGuesses();
                toggleButton();
                checkGameOver();
                toggleInputField();
                increaseGamesCompleted();
            }
        }
        private void refuseInput() {
            view.displayErrorMessage("Word not valid or not length 5");
            view.clearInputField();
        }
        private void passInput() {
            model.getGuessArrayList().clear();
            model.setGuessedWord(view.getInput());
            view.clearInputField();
        }
        private void checkColours() {
            model.checkColours();
        }
        private void checkGameOver() {
            if (model.getNumberOfGuesses() >= model.getMaxGuesses() ||
                    model.getGuessArrayList().equals(model.getCorrectAnswerArrayList())) { //either won or guesses >= 6
                model.setGameOver(true);
            }
        }
        private void increaseGamesCompleted() {
            if (model.isGameOver()) {
                model.increaseGamesCompleted();
            }
        }
        private boolean isInputLengthValid() {
            return view.getInput().length() == model.getMaxWordLength();
        }
    }
    private class buttonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { //restart game when button is clicked
            model.initialise();
            view.restart();
            toggleButton();
            toggleInputField();
        }
    }
//-------------------------Controller-----------------------------------------
    public Controller(Model model) {
        this.model = model;
    }

    protected void setView(View view) {
        this.view = view;
    }
    protected void setListeners() {
        this.view.addInputListener(new inputListener());
        this.view.addButtonListener(new buttonListener());
    }
    protected void toggleButton() {
        view.toggleButton(model.getNumberOfGuesses() > 0);
    }
    protected void toggleInputField() {view.toggleInputField(!model.isGameOver());}
}
