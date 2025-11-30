package gui;

import javax.swing.*;
import java.awt.*;

import library.entities.User;
import services.UserService;

public class DialogResetPassword extends JDialog {

    public DialogResetPassword(JFrame parent, User currentUser) {
        super(parent, "Reset Password", true);

        setSize(420, 300);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblOld = new JLabel("Old Password:");
        lblOld.setBounds(40, 40, 120, 25);
        add(lblOld);

        JPasswordField txtOld = new JPasswordField();
        txtOld.setBounds(160, 40, 200, 30);
        add(txtOld);

        JLabel lblNew = new JLabel("New Password:");
        lblNew.setBounds(40, 100, 120, 25);
        add(lblNew);

        JPasswordField txtNew = new JPasswordField();
        txtNew.setBounds(160, 100, 200, 30);
        add(txtNew);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(90, 180, 110, 40);
        btnUpdate.setBackground(new Color(35, 78, 142));
        btnUpdate.setForeground(Color.white);
        add(btnUpdate);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 180, 110, 40);
        add(btnCancel);

        // ACTION
        btnUpdate.addActionListener(e -> {

            String oldPass = new String(txtOld.getPassword());
            String newPass = new String(txtNew.getPassword());

            if (newPass.length() < 3) {
                JOptionPane.showMessageDialog(this, "Password must be at least 3 characters!");
                return;
            }

            UserService service = new UserService();
            String result = service.resetPassword(currentUser.getId(), oldPass, newPass);

            JOptionPane.showMessageDialog(this, result);

            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setVisible(true);
    }
}
