package services;

import library.entities.User;
import library.entities.Book;
import library.entities.CD;
import library.entities.Loan;
import library.data.UserData;
import library.data.LoanData;
import library.data.CDData;

import java.time.LocalDate;
import java.util.List;

/**
 * Service class to generate reports on borrowed media and fines for users.
 * <p>
 * Provides methods to calculate total fines and generate detailed fine reports
 * for books and CDs borrowed by a user.
 * </p>
 * 
 * @author Hamsa
 * @version 1.0
 */
public class MediaReportService {

    /** Fixed fine amount for overdue books */
    private static final double BOOK_FINE = 10.0;

    /** Fixed fine amount for overdue CDs */
    private static final double CD_FINE = 20.0;

    /**
     * Calculates the total fines for a specific user.
     *
     * @param userId the ID of the user
     * @return the total fine amount for overdue books and CDs; returns 0.0 if user not found
     */
    public double getTotalFines(String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) return 0.0;

        double totalFine = 0.0;

        // حساب الكتب المتأخرة
        List<Loan> loans = LoanData.getLoansByUser(userId);
        for (Loan loan : loans) {
            if (loan.getBook() != null && LocalDate.now().isAfter(loan.getDueDate())) {
                totalFine += BOOK_FINE;
            }
        }

        // حساب الأقراص CD المتأخرة
        for (String cdId : user.getBorrowedCDs().keySet()) {
            LocalDate dueDate = user.getBorrowedCDs().get(cdId);
            if (LocalDate.now().isAfter(dueDate)) {
                totalFine += CD_FINE;
            }
        }

        return totalFine;
    }

    /**
     * Generates a detailed fine report for a specific user.
     *
     * @param userId the ID of the user
     * @return a string containing detailed information about overdue books and CDs and the total fines,
     *         or "User not found" if the user does not exist
     */
    public String getDetailedFineReport(String userId) {
        User user = UserData.getUserById(userId);
        if (user == null) return "User not found";

        StringBuilder report = new StringBuilder();
        report.append("Fine Report for ").append(user.getName()).append(":\n");

        // كتب متأخرة
        List<Loan> loans = LoanData.getLoansByUser(userId);
        for (Loan loan : loans) {
            if (loan.getBook() != null && LocalDate.now().isAfter(loan.getDueDate())) {
                Book book = loan.getBook();
                report.append("Book: ").append(book.getTitle())
                      .append(" - Fine: ").append(BOOK_FINE).append(" NIS\n");
            }
        }

        // CDs متأخرة
        for (String cdId : user.getBorrowedCDs().keySet()) {
            LocalDate dueDate = user.getBorrowedCDs().get(cdId);
            if (LocalDate.now().isAfter(dueDate)) {
                CD cd = CDData.getCD(cdId);
                if (cd != null) {
                    report.append("CD: ").append(cd.getCdId())
                          .append(" - Fine: ").append(CD_FINE).append(" NIS\n");
                }
            }
        }

        report.append("Total fines: ").append(getTotalFines(userId)).append(" NIS\n");
        return report.toString();
    }
}
