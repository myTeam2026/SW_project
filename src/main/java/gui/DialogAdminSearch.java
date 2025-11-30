package gui;

import javax.swing.*;
import java.awt.*;

import library.data.BookData;
import library.data.CDData;
import library.entities.Book;
import library.entities.CD;

public class DialogAdminSearch extends JDialog {

    public DialogAdminSearch(MainFrame frame) {
        super(frame, "Search Media", true);
        setSize(450, 420);
        setLocationRelativeTo(frame);
        setLayout(null);

        JLabel lbl = new JLabel("Enter keyword (ISBN / CD ID / Title):");
        lbl.setBounds(40, 40, 300, 30);
        add(lbl);

        JTextField txt = new JTextField();
        txt.setBounds(40, 80, 350, 35);
        add(txt);

        JTextArea area = new JTextArea();
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBounds(40, 130, 350, 200);
        add(scroll);

        JButton btn = new JButton("Search");
        btn.setBounds(150, 340, 150, 40);
        btn.setBackground(new Color(35, 78, 142));
        btn.setForeground(Color.white);
        add(btn);

        btn.addActionListener(e -> {

            String key = txt.getText().trim().toLowerCase();
            StringBuilder result = new StringBuilder();

            // Book Search
            for (Book b : BookData.getAllBooks()) {
                if (b.getTitle().toLowerCase().contains(key) ||
                    b.getAuthor().toLowerCase().contains(key) ||
                    b.getISBN().equalsIgnoreCase(key)) {
                    result.append("BOOK: ").append(b.toString()).append("\n\n");
                }
            }

            // CD Search
            for (CD cd : CDData.getAllCDs()) {
                if (cd.getTitle().toLowerCase().contains(key) ||
                    cd.getArtist().toLowerCase().contains(key) ||
                    cd.getCdId().equalsIgnoreCase(key)) {
                    result.append("CD: ").append(cd.toString()).append("\n\n");
                }
            }

            if (result.length() == 0) {
                area.setText("No results found.");
            } else {
                area.setText(result.toString());
            }
        });
    }
}
