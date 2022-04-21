/**
 * @author Daniel Price 2022-04
 */
public class Main {

    static Model model;
    static View view;
    static Controller controller;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
    public static void createAndShowGUI() {
        model = new Model();
        controller = new Controller(model);
        view = new View(model, controller);
    }
}
