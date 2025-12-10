package library.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import library.entities.Loan;
import library.entities.Fine;
import library.entities.User;

import library.data.FineData;

import library.services.BorrowService;
import library.services.BorrowCDService;

public class MyLoansFinesDialog extends JDialog {

    public MyLoansFinesDialog(JFrame parent, User currentUser) {
        super(parent, "My Loans & Fines", true);

        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(area), BorderLayout.CENTER);

        StringBuilder out = new StringBuilder();

        // ================= BOOK LOANS =================
        out.append("===== Borrowed Books =====\n");

        BorrowService bookService = new BorrowService();
        List<Loan> bookLoans = bookService.getUserLoans(currentUser.getId());

        if (bookLoans.isEmpty()) {
            out.append("No borrowed books.\n\n");
        } else {
            for (Loan loan : bookLoans) {
                if (loan.getBook() != null) {
                    out.append("Book ISBN: ").append(loan.getBook().getISBN()).append("\n")
                       .append("Borrow Date: ").append(loan.getBorrowDate()).append("\n")
                       .append("Due Date: ").append(loan.getDueDate()).append("\n")
                       .append("Returned: ").append(loan.isReturned() ? "YES" : "NO").append("\n")
                       .append("--------------------------\n");
                }
            }
        }

        // ================= CD LOANS =================
        out.append("\n===== Borrowed CDs =====\n");

        BorrowCDService cdService = new BorrowCDService();
        List<Loan> cdLoans = cdService.getUserCDLoans(currentUser.getId());

        
        if (cdLoans.isEmpty()) {
            out.append("No borrowed CDs.\n\n");
        } else {
            for (Loan loan : cdLoans) {
                if (loan.getCD() != null) {
                    out.append("CD ID: ").append(loan.getCD().getCdId()).append("\n")
                       .append("Borrow Date: ").append(loan.getBorrowDate()).append("\n")
                       .append("Due Date: ").append(loan.getDueDate()).append("\n")
                       .append("Returned: ").append(loan.isReturned() ? "YES" : "NO").append("\n")
                       .append("--------------------------\n");
                }
            }
        }

        // ================= FINES =================
        out.append("\n===== Fines =====\n");

        List<Fine> fines = FineData.getFinesByUser(currentUser.getId());

        if (fines.isEmpty()) {
            out.append("No fines.\n");
        } else {
            for (Fine fine : fines) {
                out.append("Fine Amount: ").append(fine.getAmount()).append(" NIS\n")
                   .append("Reason: ").append(fine.getReason()).append("\n")
                   .append("--------------------------\n");
            }
        }

        area.setText(out.toString());
        setVisible(true);
    }
}
