package gui;

import javax.swing.*;
import java.awt.*;

import library.entities.User;
import services.BorrowCDService;

public class DialogBorrowCD extends JDialog {

    public DialogBorrowCD(JFrame parent, User currentUser) {
        super(parent, "Borrow CD", true);

        setSize(420, 250);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblID = new JLabel("CD ID:");
        lblID.setBounds(40, 40, 120, 25);
        add(lblID);

        JTextField txtID = new JTextField();
        txtID.setBounds(140, 40, 220, 30);
        add(txtID);

        JButton btnBorrow = new JButton("Borrow");
        btnBorrow.setBounds(90, 120, 110, 40);
        btnBorrow.setBackground(new Color(35, 78, 142));
        btnBorrow.setForeground(Color.white);
        add(btnBorrow);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 120, 110, 40);
        add(btnCancel);

        // ACTION
        btnBorrow.addActionListener(e -> {
            String cdId = txtID.getText().trim();

            BorrowCDService service = new BorrowCDService();
            String result = service.borrowCD(cdId, currentUser);

            JOptionPane.showMessageDialog(this, result);
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setVisible(true);
    }
}
