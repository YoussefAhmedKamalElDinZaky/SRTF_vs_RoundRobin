import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class SchedulerGUI extends JFrame {

    private final DefaultTableModel tableModel;
    private final JTable processTable;
    private final JTextField nameField;
    private final JTextField arrivalField;
    private final JTextField burstField;
    private final JTextField quantumField;
    private final JTextArea outputArea;
    private final Set<String> usedNames;

    public SchedulerGUI() {
        super("CPU Scheduling Simulator");

        usedNames = new HashSet<>();

        JPanel content = new JPanel(new BorderLayout(10, 10));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Process Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(10);
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Arrival Time:"), gbc);

        gbc.gridx = 1;
        arrivalField = new JTextField(6);
        inputPanel.add(arrivalField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Burst Time:"), gbc);

        gbc.gridx = 1;
        burstField = new JTextField(6);
        inputPanel.add(burstField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Time Quantum:"), gbc);

        gbc.gridx = 1;
        quantumField = new JTextField(6);
        quantumField.setText("2");
        inputPanel.add(quantumField, gbc);

        JButton addButton = new JButton("Add Process");
        addButton.addActionListener(this::onAddProcess);

        JButton removeButton = new JButton("Remove Selected");
        removeButton.addActionListener(this::onRemoveSelected);

        JButton clearButton = new JButton("Clear Processes");
        clearButton.addActionListener(this::onClearProcesses);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        tableModel = new DefaultTableModel(
                new Object[] { "Name", "Arrival", "Burst" }, 0);
        processTable = new JTable(tableModel);
        processTable.setFillsViewportHeight(true);
        JScrollPane tableScroll = new JScrollPane(processTable);
        tableScroll.setPreferredSize(new Dimension(360, 220));

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(tableScroll, BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setPreferredSize(new Dimension(460, 420));

        JButton runSRTF = new JButton("Run SRTF");
        runSRTF.addActionListener(e -> runAlgorithms(true, false));

        JButton runRR = new JButton("Run Round Robin");
        runRR.addActionListener(e -> runAlgorithms(false, true));

        JButton runBoth = new JButton("Run Both");
        runBoth.addActionListener(e -> runAlgorithms(true, true));

        JButton clearOutput = new JButton("Clear Output");
        clearOutput.addActionListener(e -> outputArea.setText(""));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controls.add(runSRTF);
        controls.add(runRR);
        controls.add(runBoth);
        controls.add(clearOutput);

        content.add(leftPanel, BorderLayout.WEST);
        content.add(outputScroll, BorderLayout.CENTER);
        content.add(controls, BorderLayout.SOUTH);

        setContentPane(content);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onAddProcess(ActionEvent event) {
        String name = nameField.getText().trim();
        String arrivalText = arrivalField.getText().trim();
        String burstText = burstField.getText().trim();

        if (name.isEmpty()) {
            showError("Process name cannot be empty.");
            return;
        }

        if (usedNames.contains(name)) {
            showError("Process name must be unique.");
            return;
        }

        int arrival;
        int burst;

        try {
            arrival = Integer.parseInt(arrivalText);
        } catch (NumberFormatException ex) {
            showError("Arrival time must be an integer.");
            return;
        }

        try {
            burst = Integer.parseInt(burstText);
        } catch (NumberFormatException ex) {
            showError("Burst time must be an integer.");
            return;
        }

        if (arrival < 0) {
            showError("Arrival time cannot be negative.");
            return;
        }

        if (burst <= 0) {
            showError("Burst time must be greater than zero.");
            return;
        }

        usedNames.add(name);
        tableModel.addRow(new Object[] { name, arrival, burst });
        nameField.setText("");
        arrivalField.setText("");
        burstField.setText("");
        nameField.requestFocus();
    }

    private void onRemoveSelected(ActionEvent event) {
        int row = processTable.getSelectedRow();
        if (row < 0) {
            showError("Select a process row to remove.");
            return;
        }

        String name = tableModel.getValueAt(row, 0).toString();
        usedNames.remove(name);
        tableModel.removeRow(row);
    }

    private void onClearProcesses(ActionEvent event) {
        tableModel.setRowCount(0);
        usedNames.clear();
    }

    private void runAlgorithms(boolean runSRTF, boolean runRR) {
        if (tableModel.getRowCount() == 0) {
            showError("Add at least one process before running.");
            return;
        }

        int quantum;
        try {
            quantum = Integer.parseInt(quantumField.getText().trim());
        } catch (NumberFormatException ex) {
            showError("Time Quantum must be a valid integer.");
            return;
        }

        if (quantum <= 0) {
            showError("Time Quantum must be greater than zero.");
            return;
        }

        ArrayList<Process> processes = createProcessList();
        SimulationRunner runner = new SimulationRunner();

        outputArea.append("==============================\n");

        if (runSRTF) {
            outputArea.append("SRTF Simulation:\n");
            outputArea.append(captureSimulationOutput(() -> runner.runSRTF(processes)));
            outputArea.append("\n");
        }

        if (runRR) {
            outputArea.append("Round Robin Simulation:\n");
            outputArea.append(captureSimulationOutput(() -> runner.runRoundRobin(processes, quantum)));
            outputArea.append("\n");
        }

        outputArea.append("==============================\n\n");
    }

    private ArrayList<Process> createProcessList() {
        ArrayList<Process> list = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String name = tableModel.getValueAt(i, 0).toString();
            int arrival = Integer.parseInt(tableModel.getValueAt(i, 1).toString());
            int burst = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
            list.add(new Process(name, arrival, burst));
        }
        return list;
    }

    private String captureSimulationOutput(Runnable action) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        try (PrintStream ps = new PrintStream(buffer)) {
            System.setOut(ps);
            action.run();
        } finally {
            System.setOut(oldOut);
        }
        return buffer.toString();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SchedulerGUI::new);
    }
}
