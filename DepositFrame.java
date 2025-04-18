import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DepositFrame extends JFrame {
    private JTextField accountField, amountField;
    private JButton depositButton;

    public DepositFrame() {
        setTitle("Deposit Money");
        setSize(300, 200);
        setLayout(new GridLayout(3, 1, 10, 10));

        accountField = new JTextField("Enter Account Number");
        amountField = new JTextField("Enter Amount");
        depositButton = new JButton("Deposit");

        add(accountField);
        add(amountField);
        add(depositButton);

        depositButton.addActionListener(e -> depositMoney());

        setVisible(true);
    }

    private void depositMoney() {
        int account = Integer.parseInt(accountField.getText());
        double amount = Double.parseDouble(amountField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, account);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Deposit Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Account!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error in Deposit!");
        }
    }
}
