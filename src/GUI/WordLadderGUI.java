package src.GUI;

import javax.swing.*;

import src.Solver.AStarSolver;
import src.Solver.GBFSSolver;
import src.Solver.ReadDict;
import src.Solver.UCSSolver;
import src.Solver.WordLadderSolver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

public class WordLadderGUI extends JFrame {
    private JTextField startWordField;
    private JTextField endWordField;
    private JList<String> resultDisplay;
    private WordLadderSolver ucsSolver;
    private WordLadderSolver greedySolver;
    private WordLadderSolver aStarSolver;
    private JLabel timeExecLabel;
    private JLabel countVisitedLabel;

    public WordLadderGUI() {
        super("Word Ladder Solver");
        initializeSolvers();
        initializeUI();
    }

    private void initializeSolvers() {
        try {

            HashSet<String> dictionary = ReadDict.readDict("./dictionary/words.txt");
            // Assuming constructors that take a dictionary
            ucsSolver = new UCSSolver(dictionary);
            greedySolver = new GBFSSolver(dictionary);
            aStarSolver = new AStarSolver(dictionary);
        }

        catch (IOException e) {
            // System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error loading dictionary: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel inputPanel = setupInputPanel();
        add(inputPanel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timeExecLabel = new JLabel("Time Exec: 0 ms");
        countVisitedLabel = new JLabel("Nodes Visited: 0");
        statsPanel.add(timeExecLabel);
        statsPanel.add(countVisitedLabel);
        add(statsPanel, BorderLayout.SOUTH);

        resultDisplay = new JList<>();
        resultDisplay.setFont(new Font("Monospaced", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(resultDisplay);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Path"));
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel setupInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        addFieldAndLabel("Start Word:", startWordField = new JTextField(10), inputPanel, c, 0);
        addFieldAndLabel("End Word:", endWordField = new JTextField(10), inputPanel, c, 1);
        addButton("Solve with UCS", this::solveWithUCS, inputPanel, c, 2);
        addButton("Solve with Greedy BFS", this::solveWithGreedyBFS, inputPanel, c, 3);
        addButton("Solve with A*", this::solveWithAStar, inputPanel, c, 4);

        return inputPanel;
    }

    private void addFieldAndLabel(String labelText, JTextField field, JPanel panel, GridBagConstraints constraints,
            int row) {
        constraints.gridx = 0;
        constraints.gridy = row;
        panel.add(new JLabel(labelText), constraints);

        constraints.gridx = 1;
        panel.add(field, constraints);
    }

    private void addButton(String text, ActionListener listener, JPanel panel, GridBagConstraints constraints,
            int row) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridwidth = 2;
        panel.add(button, constraints);
    }

    private void solve(WordLadderSolver solver) {
        String start = startWordField.getText().toLowerCase().trim();
        String end = endWordField.getText().toLowerCase().trim();
        try {
            List<String> result = solver.solve(start, end);

            // Formatting results with numbering
            Vector<String> formattedResult = new Vector<>();
            for (int i = 0; i < result.size(); i++) {
                formattedResult.add((i + 1) + ". " + result.get(i));
            }

            resultDisplay.setListData(formattedResult);
            timeExecLabel.setText("Time Exec: " + solver.getTimeExec() + " ms");
            countVisitedLabel.setText("Nodes Visited: " + solver.getCountVisited());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void solveWithUCS(ActionEvent event) {
        solve(ucsSolver);
    }

    private void solveWithGreedyBFS(ActionEvent event) {
        solve(greedySolver);
    }

    private void solveWithAStar(ActionEvent event) {
        solve(aStarSolver);
    }

}
