package library.gui;

import javax.swing.*;
import java.awt.*;
import library.services.MediaReportService;

public class DialogViewReport extends JDialog {

    public DialogViewReport(JFrame parent, String userId) {
        super(parent, "My Loans & Fines", true);

        setSize(500, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JTextArea text = new JTextArea();
        text.setEditable(false);
        text.setFont(new Font("Monospaced", Font.PLAIN, 14));

        MediaReportService service = new MediaReportService();
        text.setText(service.getDetailedFineReport(userId));

        JScrollPane scroll = new JScrollPane(text);
        add(scroll, BorderLayout.CENTER);
    }
}
