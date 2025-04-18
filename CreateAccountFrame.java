import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateAccountFrame extends JFrame {
    private JTextField nameField;
    private JButton createButton;

    public CreateAccountFrame() {
        setTitle("Create Account");
        setSize(300, 200);
        setLayout(new GridLayout(2, 1, 10, 10));

        nameField = new JTextField("Enter Name");
        createButton = new JButton("Create");

        add(nameField);
        add(createButton);

        createButton.addActionListener(e -> createAccount());

        setVisible(true);
    }

    private void createAccount() {
        String name = nameField.getText();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO accounts (name, balance) VALUES (?, 0)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account Created Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Creating Account!");
        }
    }
}
