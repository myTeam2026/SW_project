package gui;

import javax.swing.*;
import java.awt.*;

import library.entities.User;
import library.entities.Admin;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public User currentUser = null;
    public Admin currentAdmin = null;

    public void setCurrentUser(User u) { this.currentUser = u; }
    public void setCurrentAdmin(Admin a) { this.currentAdmin = a; }

    public MainFrame() {

        setTitle("AH Library System");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new StartPanel(this), "START");
        mainPanel.add(new LoginAdminPanel(this), "LOGIN_ADMIN");
        mainPanel.add(new LoginUserPanel(this), "LOGIN_USER");
        mainPanel.add(new RegisterPanel(this), "REGISTER");
        mainPanel.add(new AdminPanel(this), "ADMIN");

        // ❌ لا تنشئي USER PANEL هنا
        // mainPanel.add(new UserPanel(this), "USER");

        add(mainPanel);
        cardLayout.show(mainPanel, "START");
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    
    // ⭐ مهم جداً: إنشاء UserPanel بعد تسجيل الدخول
    public void loadUserPanel() {
        UserPanel userPanel = new UserPanel(this, currentUser);
        mainPanel.add(userPanel, "USER");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
