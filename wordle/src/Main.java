public class Main {

    static Model model;
    static View view;
    static Controller controller;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGui);
    }
    public static void createAndShowGui() {
        model = new Model();
        view = new View(model, controller);
        controller = new Controller(model, view);
    }
}
