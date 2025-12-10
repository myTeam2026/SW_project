package library.gui;

import javax.swing.*;
import java.awt.*;

import library.entities.User;
import library.data.UserData;
import library.data.UserFileManager;

public class RegisterPanel extends JPanel {

    public RegisterPanel(MainFrame frame) {

        setLayout(null);
        setBackground(new Color(245, 246, 250));

        JLabel title = new JLabel("Register New User", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setBounds(0, 50, 1000, 60);
        add(title);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(310, 150, 200, 30);
        add(lblName);

        JTextField txtName = new JTextField();
        txtName.setBounds(310, 180, 380, 40);
        add(txtName);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(310, 230, 200, 30);
        add(lblEmail);

        JTextField txtEmail = new JTextField();
        txtEmail.setBounds(310, 260, 380, 40);
        add(txtEmail);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(310, 310, 200, 30);
        add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(310, 340, 380, 40);
        add(txtPass);

        RoundedButton btnReg = new RoundedButton("Register");
        btnReg.setBounds(310, 410, 380, 55);
        btnReg.setBackground(new Color(35, 78, 142));
        btnReg.setForeground(Color.WHITE);
        add(btnReg);

        RoundedButton btnBack = new RoundedButton("Back");
        btnBack.setBounds(310, 480, 380, 50);
        add(btnBack);

        btnBack.addActionListener(e -> frame.showPanel("START"));

        btnReg.addActionListener(e -> {

            String name = txtName.getText().trim();
            String email = txtEmail.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields required.");
                return;
            }

            // SAFE USER ID GENERATION
            int nextIdNum = UserData.getAllUsers().size() + 1;
            String newID = String.format("U%03d", nextIdNum);

            User user = new User(newID, name, email);
            user.setPassword(pass);

            UserData.addUser(user);
            UserFileManager.saveUser(user);

            JOptionPane.showMessageDialog(this,
                    "Account created!\nYour ID: " + user.getId());

            frame.showPanel("LOGIN_USER");
        });
    }
}
