package gui;

import javax.swing.*;
import java.awt.*;

import library.data.UserData;
import library.entities.User;

public class DialogViewAllUsers extends JDialog {

    public DialogViewAllUsers(MainFrame frame) {
        super(frame, "All Users", true);
        setSize(650, 500);
        setLocationRelativeTo(frame);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-8s | %-20s | %-25s | %-8s%n",
                "ID", "Name", "Email", "Fines"));
        sb.append("---------------------------------------------------------------\n");

        for (User u : UserData.getAllUsers()) {
            sb.append(String.format("%-8s | %-20s | %-25s | %-8.2f%n",
                    u.getId(), u.getName(), u.getEmail(), u.getFineBalance()));
        }

        area.setText(sb.toString());

        add(new JScrollPane(area), BorderLayout.CENTER);
    }
}
