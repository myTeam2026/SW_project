package gui;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    public StartPanel(MainFrame frame) {

        setLayout(null);
        setBackground(new Color(245, 246, 250));

        JLabel logo = new JLabel("A   H", SwingConstants.CENTER);
        logo.setFont(new Font("Serif", Font.BOLD, 64));
        logo.setForeground(new Color(35, 78, 142));
        logo.setBounds(0, 40, 1000, 80);
        add(logo);

        JLabel subtitle = new JLabel("Library System", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 24));
        subtitle.setForeground(new Color(90, 90, 90));
        subtitle.setBounds(0, 110, 1000, 40);
        add(subtitle);

        RoundedButton btnAdmin = new RoundedButton("Admin Login");
        btnAdmin.setBounds(330, 220, 340, 55);
        stylePrimary(btnAdmin);
        add(btnAdmin);

        RoundedButton btnUser = new RoundedButton("User Login");
        btnUser.setBounds(330, 290, 340, 55);
        styleSecondary(btnUser);
        add(btnUser);

        RoundedButton btnRegister = new RoundedButton("Register New User");
        btnRegister.setBounds(330, 360, 340, 55);
        styleSecondary(btnRegister);
        add(btnRegister);

        RoundedButton btnExit = new RoundedButton("Exit");
        btnExit.setBounds(330, 430, 340, 55);
        styleDanger(btnExit);
        add(btnExit);

        btnAdmin.addActionListener(e -> frame.showPanel("LOGIN_ADMIN"));
        btnUser.addActionListener(e -> frame.showPanel("LOGIN_USER"));
        btnRegister.addActionListener(e -> frame.showPanel("REGISTER"));
        btnExit.addActionListener(e -> System.exit(0));
    }

    private void stylePrimary(JButton btn) {
        btn.setBackground(new Color(35, 78, 142));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
    }

    private void styleSecondary(JButton btn) {
        btn.setBackground(new Color(230, 230, 235));
        btn.setForeground(new Color(35, 78, 142));
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
    }

    private void styleDanger(JButton btn) {
        btn.setBackground(new Color(180, 40, 40));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
    }
}
