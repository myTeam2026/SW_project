package library.gui;

import javax.swing.*;
import java.awt.*;

import library.entities.User;
import library.services.BorrowService;

public class DialogBorrowBook extends JDialog {

    public DialogBorrowBook(JFrame parent, User currentUser) {
        super(parent, "Borrow Book", true);

        setSize(420, 250);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblISBN = new JLabel("Book ISBN:");
        lblISBN.setBounds(40, 40, 120, 25);
        add(lblISBN);

        JTextField txtISBN = new JTextField();
        txtISBN.setBounds(140, 40, 220, 30);
        add(txtISBN);

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
            String isbn = txtISBN.getText().trim();

            BorrowService service = new BorrowService();
            String result = service.borrowBook(isbn, currentUser.getId());

            JOptionPane.showMessageDialog(this, result);
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setVisible(true);
    }
}
