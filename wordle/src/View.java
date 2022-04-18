import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private static final Color WORDLE_BLACK = new Color(0x121213);
    private static final Color WORDLE_GREY = new Color(0x818384);
    private static final Color WORDLE_DARK_GREY = new Color(0x3a3a3c);
    private static final Color WORDLE_WHITE = new Color(0xffffff);
    private static final Color WORDLE_GREEN = new Color(0x538d4e);
    private static final Color WORDLE_YELLOW = new Color(0xb59f3b);

    private final Model model;
    private final Controller controller;
    private final String[] keyboardKeys = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                                            "A", "S", "D", "F", "G", "H", "J", "K", "L",
                                            "Z", "X", "C", "V", "B", "N", "M",};
    private ArrayList<JLabel> keyboardLabels;
    private ArrayList<WordPanel> wordPanels; // stores a reference to each panel (a panel holding an array of 5 JLabels)
    private JFrame frame;
    private JPanel keyboardPanel, answerPanel, bottomPanel;
    private JLabel answerLabel;
    private JTextField inputField;
    private JButton button;

//----------------------------------WordPanel Inner Class---------------------------------
    private static class WordPanel extends JPanel {
        private final JLabel[] charColumns = new JLabel[5];

        private WordPanel() { // panel holding a row of JLabels
            Border border = BorderFactory.createLineBorder(WORDLE_GREY, 2);
            this.setLayout(new GridLayout(1, 5));
            this.setSize(300, 300);
            for (int i = 0; i < 5; i++) {
                charColumns[i] = new JLabel("", JLabel.CENTER);
                charColumns[i].setOpaque(true);
                charColumns[i].setForeground(WORDLE_WHITE);
                charColumns[i].setBackground(WORDLE_BLACK);
                charColumns[i].setBorder(border);
                this.add(charColumns[i]);
            }
        }
        public JLabel[] getCharColumns() {return charColumns;}
        public void clearColumns() {
            for (int i = 0; i < 5; i++) {
                charColumns[i].setText("");
                charColumns[i].setBackground(WORDLE_BLACK);
            }
        }
    }
//-------------------------View----------------------------------------------------------
    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        controller.setView(this);

        model.addObserver(this);
        configureFrame();
        controller.setListeners();
        toggleAnswerLabel();
    }

    private void createWordPanels() {
        wordPanels = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            wordPanels.add(new WordPanel());
            frame.add(wordPanels.get(i));
        }
    }
    private void createKeyboardPanel() {
        keyboardPanel = new JPanel();
        keyboardLabels = new ArrayList<>();
        keyboardPanel.setLayout(new GridLayout(3, 9));
        for (String s : keyboardKeys) {
            JLabel label = new JLabel(s, JLabel.CENTER);
            label.setOpaque(true);
            label.setForeground(WORDLE_WHITE);
            label.setBackground(WORDLE_GREY);
            keyboardLabels.add(label);
            keyboardPanel.add(label);
        }
        frame.add(keyboardPanel);
    }
    private void createAnswerPanel() {
        answerPanel = new JPanel();
        answerLabel = new JLabel(model.getCorrectAnswerArrayList().toString().trim().toUpperCase(), JLabel.CENTER);
        answerPanel.setBackground(WORDLE_GREY);
        answerLabel.setOpaque(false);
        answerLabel.setVisible(false);
        answerLabel.setForeground(WORDLE_WHITE);
        answerPanel.add(answerLabel);
        frame.add(answerPanel);
    }
    private void createBottomPanel() {
        bottomPanel = new JPanel();
        inputField = new JTextField(5);
        button = new JButton("Restart");
        button.setEnabled(false);
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(inputField);
        bottomPanel.add(button);
        frame.add(bottomPanel);
    }
    private void configureFrame() {
        frame = new JFrame("wordle-clone");

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(9, 1));
        contentPane.setBackground(WORDLE_GREY);
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
    protected void displayGuess() {
        JLabel[] labels = getCharColumns(model.getNumberOfGuesses());
        ArrayList<Character> list;
        list = model.getGuessArrayList();

        int i = 0;
        for (char c : list) {
            labels[i].setText(String.valueOf(c).toUpperCase());
            if (model.getGreenLetters().contains(c)) {
                labels[i].setBackground(WORDLE_GREEN);
                i++;
            }
            else if (model.getYellowLetters().contains(c)) {
                labels[i].setBackground(WORDLE_YELLOW);
                i++;
            }
            else {
                labels[i].setBackground(WORDLE_DARK_GREY);
                i++;
            }
        }
    }
    protected void displayKeyboardColours() {
        for (JLabel l : keyboardLabels) {
            if (model.getGreenLetters().contains(l.getText().toLowerCase().charAt(0))) {
                l.setBackground(WORDLE_GREEN);
            }
            else if (model.getYellowLetters().contains(l.getText().toLowerCase().charAt(0))) {
                l.setBackground(WORDLE_YELLOW);
            }
            else if (model.getDarkGreyLetters().contains(l.getText().toLowerCase().charAt(0))) {
                l.setBackground(WORDLE_DARK_GREY);
            }
        }
    }
    protected JLabel[] getCharColumns(int index) {return wordPanels.get(index).getCharColumns();}
    protected void clearBoard() {
        wordPanels.forEach(WordPanel::clearColumns);
    }
    protected void clearKeyboard() {
        keyboardLabels.forEach(label -> label.setBackground(WORDLE_GREY));
    }

    protected void setAnswerLabel(String answer) {answerLabel.setText(answer);}
    protected String getInput() {return inputField.getText();}
    protected void clearInputField() {inputField.setText("");}
    protected void clearAnswerLabel() {answerLabel.setText("");}
    protected void toggleButton(boolean bool) {button.setEnabled(bool);}
    protected void toggleAnswerLabel() {answerLabel.setVisible(model.isTestMode());}
    protected void toggleInputField(boolean bool) {inputField.setEnabled(bool);}

    protected void addInputListener(ActionListener listener) {inputField.addActionListener(listener);}
    protected void addButtonListener(ActionListener listener) {button.addActionListener(listener);}
    protected void displayErrorMessage(String message) {JOptionPane.showMessageDialog(frame, message);}

    protected void restart() {
        clearBoard();
        clearKeyboard();
        clearAnswerLabel();
        setAnswerLabel(model.getCorrectAnswerArrayList().toString().trim().toUpperCase());
    }
        @Override
    public void update(Observable o, Object arg) {
        displayGuess();
        displayKeyboardColours();
        frame.repaint();
    }
}