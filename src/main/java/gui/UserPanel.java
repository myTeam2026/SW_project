package gui;

import javax.swing.*;
import java.awt.*;

import library.entities.User;

public class UserPanel extends JPanel {

    private User currentUser;

    public UserPanel(MainFrame frame, User currentUser) {

        this.currentUser = currentUser;

        setLayout(null);
        setBackground(new Color(245, 246, 250));

        JLabel title = new JLabel("User Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 34));
        title.setBounds(0, 40, 1000, 60);
        add(title);

        RoundedButton btnBorrowBook = new RoundedButton("Borrow Book");
        btnBorrowBook.setBounds(180, 150, 300, 60);
        add(btnBorrowBook);

        RoundedButton btnBorrowCD = new RoundedButton("Borrow CD");
        btnBorrowCD.setBounds(520, 150, 300, 60);
        add(btnBorrowCD);

        RoundedButton btnViewReport = new RoundedButton("My Loans / Fines");
        btnViewReport.setBounds(180, 240, 300, 60);
        add(btnViewReport);

        RoundedButton btnResetPass = new RoundedButton("Reset Password");
        btnResetPass.setBounds(520, 240, 300, 60);
        add(btnResetPass);

        RoundedButton btnLogout = new RoundedButton("Logout");
        btnLogout.setBounds(350, 360, 300, 60);
        add(btnLogout);

        // ================== BUTTON ACTIONS ==================

        // Borrow Book
        btnBorrowBook.addActionListener(e ->
            new DialogBorrowBook(frame, currentUser)
        );

        // Borrow CD
        btnBorrowCD.addActionListener(e ->
            new DialogBorrowCD(frame, currentUser)
        );

        // My Loans / Fines
        btnViewReport.addActionListener(e ->
            new MyLoansFinesDialog(frame, currentUser)
        );

        // Reset Password
        btnResetPass.addActionListener(e ->
            new DialogResetPassword(frame, currentUser)
        );

        // Logout
        btnLogout.addActionListener(e -> {
            frame.currentUser = null;
            frame.showPanel("START");
        });
    }
}
