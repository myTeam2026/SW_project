package library.gui;

import javax.swing.*;
import java.awt.*;

import library.entities.CD;
import library.data.CDData;

public class DialogAddCD extends JDialog {

    public DialogAddCD(JFrame parent) {
        super(parent, "Add New CD", true);

        setSize(420, 300);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblId = new JLabel("CD ID:");
        lblId.setBounds(40, 40, 120, 25);
        add(lblId);

        JTextField txtId = new JTextField();
        txtId.setBounds(160, 40, 200, 30);
        add(txtId);

        JLabel lblTitle = new JLabel("Title:");
        lblTitle.setBounds(40, 90, 120, 25);
        add(lblTitle);

        JTextField txtTitle = new JTextField();
        txtTitle.setBounds(160, 90, 200, 30);
        add(txtTitle);

        JLabel lblArtist = new JLabel("Artist:");
        lblArtist.setBounds(40, 140, 120, 25);
        add(lblArtist);

        JTextField txtArtist = new JTextField();
        txtArtist.setBounds(160, 140, 200, 30);
        add(txtArtist);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(90, 200, 110, 40);
        btnAdd.setBackground(new Color(35, 78, 142));
        btnAdd.setForeground(Color.white);
        add(btnAdd);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 200, 110, 40);
        add(btnCancel);

        // ACTION
        btnAdd.addActionListener(e -> {

            String id = txtId.getText().trim();
            String title = txtTitle.getText().trim();
            String artist = txtArtist.getText().trim();

            if (id.isEmpty() || title.isEmpty() || artist.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            if (CDData.getCD(id) != null) {
                JOptionPane.showMessageDialog(this, "CD already exists!");
                return;
            }

            CD newCD = new CD(id, title, artist);
            CDData.addCD(newCD);

            JOptionPane.showMessageDialog(this, "CD added successfully!");
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        setVisible(true);
    }
}
