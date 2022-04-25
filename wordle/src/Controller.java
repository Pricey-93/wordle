import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Daniel Price 2022-04
 */

public class Controller {

    private final Model model;
    private View view;

//-------------------------Action listeners for GUI---------------------------
    private class InputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { //verify and then pass input on return keypress in inputField
            if (!isInputLengthValid()) {
                refuseInput();
            }
            else if (model.isErrorMode() && !model.isValid(view.getInput().toLowerCase())) { //guess is not in either word list
                refuseInput();
            }
            else {
                passInput();
                changeColours();
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
            model.setGuessedWord(view.getInput().toLowerCase());
            view.clearInputField();
        }
        private void changeColours() {
            model.changeColours();
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
    private class ButtonListener implements ActionListener {
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

    public void setView(View view) {
        this.view = view;
    }
    public void setListeners() {
        this.view.addInputListener(new InputListener());
        this.view.addButtonListener(new ButtonListener());
    }
    public void toggleButton() {
        view.toggleButton(model.getNumberOfGuesses() > 0);
    }
    public void toggleInputField() {view.toggleInputField(!model.isGameOver());}
}
