package gui;

import javax.swing.*;
import java.awt.*;

import library.entities.Admin;
import library.data.AdminData;

public class LoginAdminPanel extends JPanel {

    public LoginAdminPanel(MainFrame frame) {

        setLayout(null);
        setBackground(new Color(245, 246, 250));

        JLabel title = new JLabel("Admin Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 34));
        title.setBounds(0, 60, 1000, 60);
        add(title);

        JLabel lblUser = new JLabel("Admin Username:");
        lblUser.setBounds(310, 190, 200, 30);
        add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(310, 220, 380, 40);
        add(txtUser);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(310, 270, 200, 30);
        add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(310, 300, 380, 40);
        add(txtPass);

        RoundedButton btnLogin = new RoundedButton("Login");
        btnLogin.setBounds(310, 360, 380, 55);
        btnLogin.setBackground(new Color(35, 78, 142));
        btnLogin.setForeground(Color.WHITE);
        add(btnLogin);

        RoundedButton btnBack = new RoundedButton("Back");
        btnBack.setBounds(310, 430, 380, 50);
        add(btnBack);

        btnBack.addActionListener(e -> frame.showPanel("START"));

        btnLogin.addActionListener(e -> {

            String u = txtUser.getText().trim();
            String p = new String(txtPass.getPassword()).trim();

            Admin admin = AdminData.getAdminByUsername(u);

            if (admin != null && admin.getPassword().equals(p)) {
                frame.setCurrentAdmin(admin);
                JOptionPane.showMessageDialog(this, "Welcome, Admin!");
                frame.showPanel("ADMIN");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
