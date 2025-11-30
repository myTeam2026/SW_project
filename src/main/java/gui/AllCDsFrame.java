package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import library.entities.CD;
import library.data.CDFileManager;

public class AllCDsFrame extends JFrame {

	
    public AllCDsFrame() {
        setTitle("All CDs");
        setSize(420, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(area);
        add(scroll, BorderLayout.CENTER);

        List<CD> cds = CDFileManager.loadCDsFromFile();

        if (cds.isEmpty()) {
            area.setText("No CDs found.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (CD cd : cds) {
                sb.append("🎵 ").append(cd.getTitle())
                  .append(" — ").append(cd.getArtist())
                  .append("   (ID: ").append(cd.getCdId()).append(")")
                  .append("\n\n");
            }
            area.setText(sb.toString());
        }

        setVisible(true);
    }
}
