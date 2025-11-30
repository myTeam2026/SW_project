package gui;

import javax.swing.*;
import java.awt.*;

import library.data.CDData;

public class DialogViewAllCDs extends JDialog {

    public DialogViewAllCDs(MainFrame frame) {
        super(frame, "All CDs", true);
        setSize(600, 500);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        CDData.getAllCDs().forEach(cd -> sb.append(cd).append("\n"));

        area.setText(sb.toString());

        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}
