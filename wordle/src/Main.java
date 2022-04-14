public class Main {

    static Model model;
    static View view;
    static Controller controller;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
    public static void createAndShowGUI() {
        model = new Model();
        view = new View(model, controller);
        controller = new Controller(model, view);
    }
}
