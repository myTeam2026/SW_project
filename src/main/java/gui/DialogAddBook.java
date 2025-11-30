package gui;

import javax.swing.*;
import java.awt.*;

import library.entities.Book;
import services.add_book_service;
import library.data.BookFileManager;

public class DialogAddBook extends JDialog {

    public DialogAddBook(MainFrame frame) {
        super(frame, "Add New Book", true);
        setSize(420, 330);
        setLocationRelativeTo(frame);
        setLayout(null);

        JLabel lblTitle = new JLabel("Title:");
        lblTitle.setBounds(40, 40, 120, 25);
        add(lblTitle);

        JTextField txtTitle = new JTextField();
        txtTitle.setBounds(140, 40, 220, 30);
        add(txtTitle);

        JLabel lblAuthor = new JLabel("Author:");
        lblAuthor.setBounds(40, 90, 120, 25);
        add(lblAuthor);

        JTextField txtAuthor = new JTextField();
        txtAuthor.setBounds(140, 90, 220, 30);
        add(txtAuthor);

        JLabel lblISBN = new JLabel("ISBN:");
        lblISBN.setBounds(40, 140, 120, 25);
        add(lblISBN);

        JTextField txtISBN = new JTextField();
        txtISBN.setBounds(140, 140, 220, 30);
        add(txtISBN);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(90, 210, 110, 40);
        btnAdd.setBackground(new Color(35, 78, 142));
        btnAdd.setForeground(Color.white);
        add(btnAdd);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 210, 110, 40);
        add(btnCancel);

        btnAdd.addActionListener(e -> {
            add_book_service service = new add_book_service();

            boolean ok = service.addBook(
                    txtTitle.getText(),
                    txtAuthor.getText(),
                    txtISBN.getText()
            );

            if (ok) {

                Book newBook = new Book(txtTitle.getText(), txtAuthor.getText(), txtISBN.getText());
                BookFileManager.saveBookToFile(newBook);   // ← ← تم إصلاح اسم الدالة

                JOptionPane.showMessageDialog(this, "Book added & saved!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Book already exists.");
            }
        });

        btnCancel.addActionListener(e -> dispose());
    }
}
