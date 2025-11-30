package gui;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    public AdminPanel(MainFrame frame) {

        setLayout(null);
        setBackground(new Color(245, 246, 250));

        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 34));
        title.setBounds(0, 40, 1000, 60);
        add(title);

        // Add Book
        RoundedButton btnAddBook = new RoundedButton("Add Book");
        btnAddBook.setBounds(180, 150, 300, 60);
        add(btnAddBook);

        // Add CD
        RoundedButton btnAddCD = new RoundedButton("Add CD");
        btnAddCD.setBounds(520, 150, 300, 60);
        add(btnAddCD);

        // Search Media
        RoundedButton btnSearch = new RoundedButton("Search Media");
        btnSearch.setBounds(180, 240, 300, 60);
        add(btnSearch);

        // User Fine Report
        RoundedButton btnReport = new RoundedButton("User Fine Report");
        btnReport.setBounds(520, 240, 300, 60);
        add(btnReport);

        // View All Books
        RoundedButton btnViewBooks = new RoundedButton("View All Books");
        btnViewBooks.setBounds(180, 330, 300, 60);
        add(btnViewBooks);

        // View All CDs
        RoundedButton btnViewCDs = new RoundedButton("View All CDs");
        btnViewCDs.setBounds(520, 330, 300, 60);
        add(btnViewCDs);

        // View All Users
        RoundedButton btnViewUsers = new RoundedButton("View All Users");
        btnViewUsers.setBounds(350, 420, 300, 60);
        add(btnViewUsers);

        // Home Button
        RoundedButton btnHome = new RoundedButton("Home");
        btnHome.setBounds(20, 20, 140, 45);
        btnHome.setBackground(new Color(40, 110, 200));
        btnHome.setForeground(Color.WHITE);
        add(btnHome);

        // Logout
        RoundedButton btnLogout = new RoundedButton("Logout");
        btnLogout.setBounds(350, 500, 300, 60);
        add(btnLogout);


        // ACTIONS
        btnAddBook.addActionListener(e -> new DialogAddBook(frame).setVisible(true));
        btnAddCD.addActionListener(e -> new DialogAddCD(frame).setVisible(true));
        btnSearch.addActionListener(e -> new DialogAdminSearch(frame).setVisible(true));

        btnViewBooks.addActionListener(e -> new DialogViewAllBooks(frame).setVisible(true));
        btnViewCDs.addActionListener(e -> new DialogViewAllCDs(frame).setVisible(true));
        btnViewUsers.addActionListener(e -> new DialogViewAllUsers(frame).setVisible(true));

        btnHome.addActionListener(e -> frame.showPanel("START"));
        btnLogout.addActionListener(e -> frame.showPanel("START"));
    }
}
