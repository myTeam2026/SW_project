package gui;

import javax.swing.*;
import java.awt.*;
import library.data.BookData;

public class DialogViewAllBooks extends JDialog {

    public DialogViewAllBooks(MainFrame frame) {
        super(frame, "All Books", true);
        setSize(600, 500);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        BookData.getAllBooks().forEach(book -> sb.append(book).append("\n"));

        area.setText(sb.toString());

        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}
