import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller {

    private final Model model;
    private View view;

//-------------------------Action listeners for GUI---------------------------
    private class inputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!isInputLengthValid()) {
                refuseInput();
            }
            else if (model.isErrorMode() && !model.isValid(view.getInput())) {
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
        private void passInput() {
            model.getGuessArrayList().clear();
            model.setGuessedWord(view.getInput());
            view.clearInputField();
        }
        private void refuseInput() {
            view.displayErrorMessage("Word not valid or not length 5");
            view.clearInputField();
        }
        private void checkGameOver() {
            if (model.getNumberOfGuesses() >= model.getMaxGuesses() || model.getGuessArrayList().equals(model.getCorrectAnswerArrayList())) {
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
        public void actionPerformed(ActionEvent e) {
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
        this.view.addInputListener(new inputListener());
        this.view.addButtonListener(new buttonListener());
    }
    protected void checkColours() {
        model.checkColours();
    }
    protected void toggleButton() {
        view.toggleButton(model.getNumberOfGuesses() > 0);
    }
    protected void toggleInputField() {
        view.toggleInputField(!model.isGameOver());
    }
}
