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
    }

    @Override
    public void update(Observable o, Object arg) {}
}
