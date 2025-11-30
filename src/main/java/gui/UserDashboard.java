package gui;

import javax.swing.*;
import java.awt.*;

import library.entities.User;

public class UserDashboard extends JFrame {

    public UserDashboard(User currentUser) {

        setTitle("User Dashboard");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 246, 250));

        JLabel title = new JLabel("User Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setBounds(280, 40, 600, 40);
        add(title);

        // Buttons
        JButton btnBorrowBook = makeButton("Borrow Book", 150, 150);
        JButton btnBorrowCD   = makeButton("Borrow CD",   480, 150);
        JButton btnMyLoans    = makeButton("My Loans / Fines", 150, 260);
        JButton btnResetPass  = makeButton("Reset Password",   480, 260);
        JButton btnLogout     = makeButton("Logout", 300, 380);

        add(btnBorrowBook);
        add(btnBorrowCD);
        add(btnMyLoans);
        add(btnResetPass);
        add(btnLogout);

        // ================== ACTIONS ==================

        btnBorrowBook.addActionListener(e ->
                new DialogBorrowBook(this, currentUser)
        );

        btnBorrowCD.addActionListener(e ->
                new DialogBorrowCD(this, currentUser)
        );

        btnMyLoans.addActionListener(e ->
                new MyLoansFinesDialog(this, currentUser)
        );

        btnResetPass.addActionListener(e ->
                new DialogResetPassword(this, currentUser)
        );

        btnLogout.addActionListener(e -> {
            dispose();
            new MainFrame().setVisible(true);
        });

        setVisible(true);
    }

    private JButton makeButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 260, 70);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(230, 230, 230));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        return btn;
    }
}
