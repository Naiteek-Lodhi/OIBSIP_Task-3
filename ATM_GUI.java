package oasis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ATM_GUI {
    private String name;
    private String accountNumber;
    private int balance = 1000; // Initial balance
    private ArrayList<String> history = new ArrayList<>();
    private final String ATM_NUMBER = "123456"; // Predefined ATM number
    private final String PIN = "7890"; // Predefined PIN (password)

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATM_GUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("ATM System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 1));

        JLabel welcomeLabel = new JLabel("Welcome to the ATM System");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(welcomeLabel);

        JPanel loginDetailsPanel = new JPanel();
        loginDetailsPanel.setLayout(new GridLayout(2, 2));
        JLabel atmLabel = new JLabel("ATM Number:");
        JTextField atmField = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        JPasswordField pinField = new JPasswordField();
        loginDetailsPanel.add(atmLabel);
        loginDetailsPanel.add(atmField);
        loginDetailsPanel.add(pinLabel);
        loginDetailsPanel.add(pinField);
        loginPanel.add(loginDetailsPanel);

        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton);

        JLabel statusLabel = new JLabel("", SwingConstants.CENTER);
        loginPanel.add(statusLabel);

        JPanel atmPanel = new JPanel();
        atmPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name: " + name);
        JLabel accountLabel = new JLabel("Account Number: " + accountNumber);
        JLabel atmNumberLabel = new JLabel("ATM Number: " + ATM_NUMBER);

        atmPanel.add(nameLabel, constraints);
        atmPanel.add(accountLabel, constraints);
        atmPanel.add(atmNumberLabel, constraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 5, 5)); // Rows, 1 column, spacing between components

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton transferButton = new JButton("Transfer");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(transactionHistoryButton);
        buttonPanel.add(exitButton);

        atmPanel.add(buttonPanel, constraints);

        frame.add(loginPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredATM = atmField.getText();
                String enteredPIN = new String(pinField.getPassword());

                if (enteredATM.isEmpty() || enteredPIN.isEmpty()) {
                    statusLabel.setText("Please fill in all fields.");
                } else if (enteredATM.equals(ATM_NUMBER) && enteredPIN.equals(PIN)) {
                    name = "John Doe"; // Set the user's name (you can replace this with actual user input)
                    accountNumber = "1234567890"; // Set the user's account number (you can replace this with actual user input)
                    updateUserInfo(nameLabel, accountLabel);
                    frame.getContentPane().removeAll();
                    frame.add(atmPanel, BorderLayout.CENTER);
                    frame.repaint();
                    frame.revalidate();
                    statusLabel.setText("");
                } else {
                    statusLabel.setText("Invalid ATM number or PIN. Please try again.");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter the amount to withdraw:", "Withdraw",
                        JOptionPane.PLAIN_MESSAGE);

                if (input != null && !input.isEmpty()) {
                    int amount = Integer.parseInt(input);
                    if (amount > 0 && amount <= balance) {
                        balance -= amount;
                        history.add("Withdraw: Rs. " + amount);
                        updateBalanceLabel(accountLabel);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid amount or insufficient balance.",
                                "Withdraw Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter the amount to deposit:", "Deposit",
                        JOptionPane.PLAIN_MESSAGE);

                if (input != null && !input.isEmpty()) {
                    int amount = Integer.parseInt(input);
                    if (amount > 0) {
                        balance += amount;
                        history.add("Deposit: Rs. " + amount);
                        updateBalanceLabel(accountLabel);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid amount.", "Deposit Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Your current balance is Rs. " + balance,
                        "Check Balance", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        transactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder transactionHistory = new StringBuilder("Transaction History:\n");
                for (String transaction : history) {
                    transactionHistory.append(transaction).append("\n");
                }
                JOptionPane.showMessageDialog(frame, transactionHistory.toString(),
                        "Transaction History", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Add action listeners for other ATM operations (transfer, transaction history)
        // ...

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void updateUserInfo(JLabel nameLabel, JLabel accountLabel) {
        nameLabel.setText("Name: " + name);
        accountLabel.setText("Account Number: " + accountNumber);
    }

    private void updateBalanceLabel(JLabel label) {
        label.setText("Available Balance: Rs. " + balance);
    }
}
