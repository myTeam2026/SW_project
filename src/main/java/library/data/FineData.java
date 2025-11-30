package library.data;

import library.entities.Fine;
import library.entities.User;
import java.util.ArrayList;
import java.util.List;

public class FineData {

    private static List<Fine> fines = new ArrayList<>();
    private static int fineCounter = 1;

    public static Fine createFine(User user, double amount, String reason) {
        String fineId = String.format("F%03d", fineCounter++); // F001, F002...
        Fine fine = new Fine(fineId, user, amount, reason);

        // أضف الغرامة لقائمة الغرامات
        fines.add(fine);

        // زيادة رصيد الغرامات عند المستخدم
        user.addFine(amount);

        return fine;
    }

    public static List<Fine> getFinesByUser(String userId) {
        List<Fine> result = new ArrayList<>();
        for (Fine fine : fines) {
            if (fine.getUser().getId().equals(userId)) {
                result.add(fine);
            }
        }
        return result;
    }

    public static double getTotalFinesByUser(String userId) {
        double total = 0.0;
        for (Fine fine : fines) {
            if (fine.getUser().getId().equals(userId)) {
                total += fine.getAmount();
            }
        }
        return total;
    }

    public static void addFine(Fine fine) {
        fines.add(fine);
    }

    public static List<Fine> getAllFines() {
        return fines;
    }

    public static void clearFines() {
        fines.clear();
        fineCounter = 1; // إعادة العد للبدء من F001
    }
}
