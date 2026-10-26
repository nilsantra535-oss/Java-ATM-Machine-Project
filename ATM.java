import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;

public class ATM extends JFrame {

    // Account Data
    private HashMap<Integer, Integer> customerData = new HashMap<>();
    private int currentCustomerNumber;
    private double checkingBalance = 1000.00; // Initial default balances
    private double savingBalance = 5000.00;

    private DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");

    // Card Layout for switching screens
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // UI Components - Login Screen
    private JTextField customerNumField;
    private JPasswordField pinField;

    // UI Components - Main Menu Screen
    private JLabel balanceLabel;

    public ATM() {
        // Setup initial dummy accounts (Customer Number -> PIN)
        customerData.put(952141, 191904);
        customerData.put(989947, 717976);

        setTitle("ATM Machine");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center screen
        setResizable(false);

        // Build screens
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createMainMenuPanel(), "MainMenu");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    // ------------------- LOGIN PANEL -------------------
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel headerLabel = new JLabel("Welcome to ATM", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Customer Number:"));
        customerNumField = new JTextField();
        inputPanel.add(customerNumField);

        inputPanel.add(new JLabel("PIN Number:"));
        pinField = new JPasswordField();
        inputPanel.add(pinField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setBackground(new Color(70, 130, 180));
        loginBtn.setForeground(Color.WHITE);

        loginBtn.addActionListener(e -> handleLogin());

        panel.add(headerLabel);
        panel.add(inputPanel);
        panel.add(new JLabel("")); // Spacer
        panel.add(loginBtn);

        return panel;
    }

    private void handleLogin() {
        try {
            int customerNum = Integer.parseInt(customerNumField.getText().trim());
            int pin = Integer.parseInt(new String(pinField.getPassword()).trim());

            if (customerData.containsKey(customerNum) && customerData.get(customerNum) == pin) {
                currentCustomerNumber = customerNum;
                JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(mainPanel, "MainMenu");
            } else {
                JOptionPane.showMessageDialog(this, "Wrong Customer Number or PIN!", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter numbers only!", "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // ------------------- MAIN MENU PANEL -------------------
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Select Transaction", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 15, 15));

        JButton viewCheckingBtn = new JButton("Checking Balance");
        JButton viewSavingBtn = new JButton("Savings Balance");
        JButton withdrawCheckingBtn = new JButton("Withdraw Checking");
        JButton withdrawSavingBtn = new JButton("Withdraw Savings");
        JButton depositCheckingBtn = new JButton("Deposit Checking");
        JButton depositSavingBtn = new JButton("Deposit Savings");
        JButton logoutBtn = new JButton("Logout");

        logoutBtn.setBackground(new Color(220, 20, 60));
        logoutBtn.setForeground(Color.WHITE);

        // Action Listeners
        viewCheckingBtn.addActionListener(e -> showBalance("Checking", checkingBalance));
        viewSavingBtn.addActionListener(e -> showBalance("Savings", savingBalance));

        withdrawCheckingBtn.addActionListener(e -> handleWithdraw("Checking"));
        withdrawSavingBtn.addActionListener(e -> handleWithdraw("Savings"));

        depositCheckingBtn.addActionListener(e -> handleDeposit("Checking"));
        depositSavingBtn.addActionListener(e -> handleDeposit("Savings"));

        logoutBtn.addActionListener(e -> {
            customerNumField.setText("");
            pinField.setText("");
            cardLayout.show(mainPanel, "Login");
        });

        buttonPanel.add(viewCheckingBtn);
        buttonPanel.add(viewSavingBtn);
        buttonPanel.add(withdrawCheckingBtn);
        buttonPanel.add(withdrawSavingBtn);
        buttonPanel.add(depositCheckingBtn);
        buttonPanel.add(depositSavingBtn);
        buttonPanel.add(logoutBtn);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    // ------------------- ATM OPERATIONS -------------------
    private void showBalance(String accountType, double balance) {
        JOptionPane.showMessageDialog(this, accountType + " Account Balance: " + moneyFormat.format(balance),
                accountType + " Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleWithdraw(String accountType) {
        String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw from " + accountType + " Account:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (accountType.equals("Checking")) {
                    if (checkingBalance >= amount) {
                        checkingBalance -= amount;
                        JOptionPane.showMessageDialog(this, "Withdrawal Successful!\nNew Checking Balance: " + moneyFormat.format(checkingBalance));
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient Funds!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    if (savingBalance >= amount) {
                        savingBalance -= amount;
                        JOptionPane.showMessageDialog(this, "Withdrawal Successful!\nNew Savings Balance: " + moneyFormat.format(savingBalance));
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient Funds!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Amount Format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleDeposit(String accountType) {
        String input = JOptionPane.showInputDialog(this, "Enter amount to deposit into " + accountType + " Account:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                double amount = Double.parseDouble(input);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a positive amount.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (accountType.equals("Checking")) {
                    checkingBalance += amount;
                    JOptionPane.showMessageDialog(this, "Deposit Successful!\nNew Checking Balance: " + moneyFormat.format(checkingBalance));
                } else {
                    savingBalance += amount;
                    JOptionPane.showMessageDialog(this, "Deposit Successful!\nNew Savings Balance: " + moneyFormat.format(savingBalance));
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Amount Format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ------------------- MAIN METHOD -------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATM frame = new ATM();
            frame.setVisible(true);
        });
    }
}