public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGui);
    }
    public static void createAndShowGui() {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(model, controller);
    }
}
