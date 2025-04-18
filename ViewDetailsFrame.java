import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewDetailsFrame extends JFrame {
    private JTextField accountField;
    private JButton viewButton;
    private JTextArea resultArea;

    public ViewDetailsFrame() {
        setTitle("View Account Details");
        setSize(400, 300);
        setLayout(new GridLayout(3, 1, 10, 10));

        accountField = new JTextField("Enter Account Number");
        viewButton = new JButton("View Details");
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(accountField);
        add(viewButton);
        add(new JScrollPane(resultArea));

        viewButton.addActionListener(e -> viewAccountDetails());

        setVisible(true);
    }

    private void viewAccountDetails() {
        int account = Integer.parseInt(accountField.getText());

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, account);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String details = "Account Number: " + rs.getInt("account_number") +
                        "\nName: " + rs.getString("name") +
                        "\nBalance: ₹" + rs.getDouble("balance");
                resultArea.setText(details);
            } else {
                resultArea.setText("Account Not Found!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Retrieving Account Details!");
        }
    }
}
