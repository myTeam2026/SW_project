package gui;

import javax.swing.*;
import java.awt.*;

import library.entities.User;
import services.EmailService;
import library.data.UserData;

public class LoginUserPanel extends JPanel {

    public LoginUserPanel(MainFrame frame) {

        setLayout(null);
        setBackground(new Color(245, 246, 250));

        JLabel title = new JLabel("User Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 34));
        title.setBounds(0, 40, 1000, 60);
        add(title);

        JLabel lblId = new JLabel("User ID:");
        lblId.setFont(new Font("SansSerif", Font.PLAIN, 20));
        lblId.setBounds(320, 170, 150, 30);
        add(lblId);

        JTextField txtId = new JTextField();
        txtId.setBounds(450, 170, 220, 35);
        add(txtId);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setFont(new Font("SansSerif", Font.PLAIN, 20));
        lblPass.setBounds(320, 230, 150, 30);
        add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(450, 230, 220, 35);
        add(txtPass);

        RoundedButton btnLogin = new RoundedButton("Login");
        btnLogin.setBounds(360, 330, 300, 55);
        add(btnLogin);

        RoundedButton btnBack = new RoundedButton("Back");
        btnBack.setBounds(360, 400, 300, 55);
        add(btnBack);

        // =====================================================
        //                     LOGIN ACTION
        // =====================================================
        btnLogin.addActionListener(e -> {

            String id = txtId.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            if (id.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            User user = UserData.getUserById(id);

            if (user == null) {
                JOptionPane.showMessageDialog(this, "User not found.");
                return;
            }

            if (!user.getPassword().equals(pass)) {
                JOptionPane.showMessageDialog(this, "Incorrect password.");
                return;
            }

            // =============== LOGIN SUCCESS ===============
            frame.setCurrentUser(user);
            frame.loadUserPanel();
            frame.showPanel("USER");

            // =============== SEND EMAIL SAFELY ===============
            if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
                EmailService emailService = new EmailService();
                emailService.sendEmail(
                        user.getEmail(),
                        "Login Successful",
                        "Hello " + user.getName() + ",\n\nYou have successfully logged into the Library System!"
                );
            }

            JOptionPane.showMessageDialog(this, "Welcome, " + user.getName() + "!");

        });

        // BACK BUTTON
        btnBack.addActionListener(e -> frame.showPanel("START"));
    }
}
