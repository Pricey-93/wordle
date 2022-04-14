import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private static final Color WORDLE_BLACK = new Color(0x212121);
    private static final Color WORDLE_GREY = new Color(0x3a3a3c);
    private static final Color WORDLE_WHITE = new Color(0xffffff);
    private static final Color WORDLE_GREEN = new Color(0x538d4e);
    private static final Color WORDLE_YELLOW = new Color(0xb59f3b);
    //private static final Font FONT = new Font("Clear Sans", Font.BOLD, 50);

    private final Model model;
    private final Controller controller;
    private String answer;
    private final String[] keyboardKeys = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                                            "A", "S", "D", "F", "G", "H", "J", "K", "L",
                                            "Z", "X", "C", "V", "B", "N", "M",};
    private JFrame frame;
    private ArrayList<WordPanel> panels; // stores a reference to each panel (a panel holding an array of 5 JLabels)
    private ArrayList<JLabel> keyboardLabels = new ArrayList<>();
    private JPanel keyboardPanel, answerPanel, bottomPanel;
    private JLabel answerLabel;
    private JTextField input;
    private JButton button;

//----------------------------------WordPanel Inner Class---------------------------------
    private static class WordPanel extends JPanel {
        private final JLabel[] charColumns = new JLabel[5];

        private WordPanel() { // panel holding a row of JLabels
            this.setLayout(new GridLayout(1, 5));
            this.setSize(300, 300);
            Border border = BorderFactory.createLineBorder(WORDLE_GREY, 2);
            for (int i = 0; i < 5; i++) {
                charColumns[i] = new JLabel("", JLabel.CENTER);
                charColumns[i].setOpaque(true);
                charColumns[i].setBorder(border);
                this.add(charColumns[i]);
            }
        }
        public JLabel[] getCharColumns() {return charColumns;}
        public void cleanAllColumns() {
            for (int i = 0; i < 5; i++) {
                charColumns[i].setText("");
            }
        }
    }
//-------------------------View----------------------------------------------------------
    public View(Model model, Controller controller) {
        this.model = model;
        model.addObserver(this);
        this.controller = controller;

        createContainers();
    }

    private void createWordPanels() {
        panels = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            panels.add(new WordPanel());
            frame.add(panels.get(i));
        }
    }
    private void createKeyboardPanel() {
        keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new GridLayout(3, 9));
        for (String s : keyboardKeys) {
            JLabel label = new JLabel(s, JLabel.CENTER);
            keyboardLabels.add(label);
            keyboardPanel.add(label);
        }
        frame.add(keyboardPanel);
    }
    private void createAnswerPanel() {
        answerPanel = new JPanel();
        answerLabel = new JLabel("", JLabel.CENTER);
        answerPanel.add(answerLabel);
        frame.add(answerPanel);
    }
    private void createBottomPanel() {
        bottomPanel = new JPanel();
        input = new JTextField();
        button = new JButton("Restart");
        button.setEnabled(false);
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(input);
        bottomPanel.add(button);
        frame.add(bottomPanel);
    }
    private void createContainers() {
        frame = new JFrame("wordle-clone");

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(9, 1));

        createWordPanels();
        createKeyboardPanel();
        createAnswerPanel();
        createBottomPanel();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
    }
    protected void setAnswerLabel() {
        answerLabel.setText(answer);
    }
    protected void setAnswer(String answer) {
        this.answer = answer;
    }
    protected void enableButton() {
        button.setEnabled(true);
    }

    /**
     * Test methods
     */
    public void changeColour() {
        JLabel[] row = panels.get(0).getCharColumns();
        row[0].setBackground(WORDLE_YELLOW);
    }

        @Override
    public void update(Observable o, Object arg) {

    }
}
