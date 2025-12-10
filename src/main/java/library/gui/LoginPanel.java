package library.gui;

import javax.swing.*;
import java.awt.*;


import library.entities.User;
import library.entities.Admin;
import library.data.UserData;
import library.data.AdminData;

public class LoginPanel extends JPanel {

    public LoginPanel(MainFrame frame) {

        setLayout(null);
        setBackground(new Color(240, 240, 245));

        JLabel logo = new JLabel("A  H");
        logo.setFont(new Font("Serif", Font.BOLD, 52));
        logo.setForeground(new Color(40, 80, 160));
        logo.setBounds(360, 40, 400, 70);
        add(logo);

        JLabel sub = new JLabel("Library System");
        sub.setFont(new Font("SansSerif", Font.PLAIN, 20));
        sub.setForeground(new Color(90, 90, 100));
        sub.setBounds(360, 100, 300, 40);
        add(sub);

        JLabel lblUser = new JLabel("ID / Username:");
        lblUser.setBounds(300, 200, 150, 25);
        add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(300, 230, 300, 35);
        add(txtUser);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(300, 280, 150, 25);
        add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(300, 310, 300, 35);
        add(txtPass);

        RoundedButton btnLogin = new RoundedButton("Login");
        btnLogin.setBounds(300, 370, 140, 40);
        btnLogin.setBackground(new Color(40, 80, 160));
        btnLogin.setForeground(Color.white);
        add(btnLogin);

        RoundedButton btnRegister = new RoundedButton("Register");
        btnRegister.setBounds(460, 370, 140, 40);
        btnRegister.setBackground(new Color(220, 220, 230));
        btnRegister.setForeground(new Color(40, 80, 160));
        add(btnRegister);

        btnRegister.addActionListener(e -> frame.showPanel("REGISTER"));

        btnLogin.addActionListener(e -> {

            String id = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            Admin admin = AdminData.getAdminByUsername(id);
            if (admin != null && admin.getPassword().equals(pass)) {

                JOptionPane.showMessageDialog(this, "Welcome Admin " + admin.getUsername());
                frame.setCurrentAdmin(admin);
                frame.showPanel("ADMIN");
                return;
            }

            User user = UserData.getUserById(id);
            if (user != null && user.getPassword().equals(pass)) {

                JOptionPane.showMessageDialog(this, "Welcome " + user.getName());
                frame.setCurrentUser(user);
                frame.showPanel("USER");
                return;
            }

            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        });
    }
}
