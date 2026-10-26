# Java ATM Machine Project

A simple Java application featuring both a **Console-based (CLI)** interface and a **Graphical User Interface (GUI)** built using Java Swing. This project simulates core ATM functionalities such as account login, balance inquiry, cash deposits, and withdrawals for both Checking and Savings accounts.

## 🚀 Features
- **Dual Interface:** Supports both command-line operation (`OptionMenu`) and a modern desktop GUI (`ATM` using Java Swing).
- **Authentication:** Secure login validation using predefined customer account numbers and PINs.
- **Account Management:**
  - View balances for Checking and Savings accounts.
  - Deposit funds with validation for positive amounts.
  - Withdraw funds with insufficient balance checks.
- **Formatting:** Clean currency formatting using `DecimalFormat`.

## 📂 Project Structure
- `Account.java`: Contains the core business logic, variables, and calculation methods for checking and savings balances.
- `ATM.java`: Implements the Java Swing GUI for a desktop application experience.
- `OptionMenu.java`: Implements the console/terminal-based menu interface extending the `Account` class.

## 🛠️ Requirements
- Java Development Kit (JDK) 8 or higher.
- Any Java IDE (IntelliJ IDEA, Eclipse, NetBeans) or command-line terminal.

## 💻 How to Run

### Option 1: Running the GUI version
1. Compile all Java files:
   ```bash
   javac Account.java ATM.java OptionMenu.java