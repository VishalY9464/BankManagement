import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WithdrawFrame extends JFrame {
    private JTextField accountField, amountField;
    private JButton withdrawButton;

    public WithdrawFrame() {
        setTitle("Withdraw Money");
        setSize(300, 200);
        setLayout(new GridLayout(3, 1, 10, 10));

        accountField = new JTextField("Enter Account Number");
        amountField = new JTextField("Enter Amount");
        withdrawButton = new JButton("Withdraw");

        add(accountField);
        add(amountField);
        add(withdrawButton);

        withdrawButton.addActionListener(e -> withdrawMoney());

        setVisible(true);
    }

    private void withdrawMoney() {
        int account = Integer.parseInt(accountField.getText());
        double amount = Double.parseDouble(amountField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkSql = "SELECT balance FROM accounts WHERE account_number = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, account);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getDouble("balance") >= amount) {
                String updateSql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setDouble(1, amount);
                updateStmt.setInt(2, account);
                updateStmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Withdrawal Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient Balance or Invalid Account!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error in Withdrawal!");
        }
    }
}
