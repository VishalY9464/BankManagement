import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankApp extends JFrame {
    private JButton createAccountBtn, depositBtn, withdrawBtn, viewDetailsBtn;

    public BankApp() {
        setTitle("Bank Account Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        createAccountBtn = new JButton("Create Account");
        depositBtn = new JButton("Deposit Money");
        withdrawBtn = new JButton("Withdraw Money");
        viewDetailsBtn = new JButton("View Account Details");

        add(createAccountBtn);
        add(depositBtn);
        add(withdrawBtn);
        add(viewDetailsBtn);

        createAccountBtn.addActionListener(e -> new CreateAccountFrame());
        depositBtn.addActionListener(e -> new DepositFrame());
        withdrawBtn.addActionListener(e -> new WithdrawFrame());
        viewDetailsBtn.addActionListener(e -> new ViewDetailsFrame());

        setVisible(true);
    }

    public static void main(String[] args) {
        new BankApp();
    }
}
