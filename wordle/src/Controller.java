import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private final Model model;
    private final View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        model.addObserver(this);
        this.view.addInputListener(new inputListener());

    }
    private class inputListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
           if (model.isValid(view.getInput()) && view.getInput().length() == model.getMaxWordLength())
               model.setGuessedWord(view.getInput());
           else
               view.displayErrorMessage("Enter a valid 5 letter word");
        }
    }



    protected void toggleButton() {
        view.enableButton();
    }

    @Override
    public void update(Observable o, Object arg) {}
}
