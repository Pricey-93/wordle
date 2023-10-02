import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Daniel Price 2022-04
 */

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
        private final JLabel[] columns = new JLabel[5];

        private WordPanel() { // panel holding a row of JLabels
            this.setLayout(new GridLayout(1, 5));

            for (int i = 0; i < 5; i++) {
                columns[i] = new JLabel("", JLabel.CENTER);
                columns[i].setOpaque(true);
                columns[i].setForeground(WORDLE_WHITE);
                columns[i].setBackground(WORDLE_BLACK);
                columns[i].setBorder(BorderFactory.createLineBorder(WORDLE_GREY, 1));
                this.add(columns[i]);
            }
        }
        public JLabel[] getColumns() {return columns;}
        public void clearColumns() {
            for (int i = 0; i < 5; i++) {
                columns[i].setText("");
                columns[i].setBackground(WORDLE_BLACK);
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
        toggleAnswerLabel(); //check model for testMode flag, reveal answer if testMode == true
    }

    private void createWordPanels() {
        wordPanels = new ArrayList<>();
        for (int i = 0; i < 6; i++) { // 6 rows of wordPanels, each containing 5 columns of JLabels
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
        frame.setSize(450, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
    }
    public void displayGuess() {
        JLabel[] labels = getColumns(model.getNumberOfGuesses()); //get the column of the current row in play
        ArrayList<Character> list;
        list = model.getGuessArrayList();

        int i = 0;
        for (char c : list) {
            labels[i].setText(String.valueOf(c).toUpperCase());
            if (model.getGreenLetters().contains(c) && model.getCorrectAnswerArrayList().get(i).equals(c)) { // only colour it green if it is in the exact position, even if it has been found on a previous turn
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
    public void displayKeyboardColours() {
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
    public JLabel[] getColumns(int index) {return wordPanels.get(index).getColumns();}
    public String getInput() {return inputField.getText();}

    public void setAnswerLabel(String answer) {answerLabel.setText(answer);}

    public void toggleButton(boolean bool) {button.setEnabled(bool);}
    public void toggleAnswerLabel() {answerLabel.setVisible(model.isTestMode());}
    public void toggleInputField(boolean bool) {inputField.setEnabled(bool);}

    public void addInputListener(ActionListener listener) {inputField.addActionListener(listener);}
    public void addButtonListener(ActionListener listener) {button.addActionListener(listener);}
    public void displayErrorMessage(String message) {JOptionPane.showMessageDialog(frame, message);}

    public void clearGrid() {
        wordPanels.forEach(WordPanel::clearColumns);
    }
    public void clearKeyboard() {
        keyboardLabels.forEach(label -> label.setBackground(WORDLE_GREY));
    }
    public void clearInputField() {inputField.setText("");}
    public void clearAnswerLabel() {answerLabel.setText("");}
    public void restart() {
        clearGrid();
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