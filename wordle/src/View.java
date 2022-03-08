import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private final Model model;
    private final Controller controller;

    private JFrame frame;
    private JPanel gridPanel, keyboardPanel;

    private final Color wordleBlack = new Color(0x212121);
    private final Color wordleGrey = new Color(0x3a3a3c);
    private final Color wordleWhite = new Color(0xffffff);
    private final Color wordleGreen = new Color(0x538d4e);
    private final Color wordleYellow = new Color(0xb59f3b);
    private final Font font = new Font("Clear Sans", Font.BOLD, 50);

    public View(Model model, Controller controller) {
        this.model = model;
        model.addObserver(this);
        this.controller = controller;
        createContainers();
    }

    public void createContainers() {
        frame = new JFrame("wordle-clone");

        createPanels();
        createComponents();

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(gridPanel);
        contentPane.add(keyboardPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createPanels() {
        gridPanel = new JPanel(new GridLayout(6, 5, 10, 10));
        keyboardPanel = new JPanel();

        gridPanel.setBackground(wordleBlack);
        keyboardPanel.setBackground(wordleGrey);
    }

    private void createComponents() {
        for (int i = 0; i < 30; i++) {
            JPanel panel = new JPanel();
            JLabel label = new JLabel("T", SwingConstants.CENTER);
            label.setForeground(wordleWhite);
            label.setFont(font);

            panel.setBorder(new LineBorder(wordleGrey, 2));
            panel.setBackground(wordleBlack);
            panel.add(label);
            gridPanel.add(panel);
        }
    }

        @Override
    public void update(Observable o, Object arg) {

    }
}
