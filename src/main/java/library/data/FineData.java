package library.data;

import library.entities.Fine;
import library.entities.User;
import java.util.ArrayList;
import java.util.List;

public class FineData {
    private static List<Fine> fines = new ArrayList<>();
    private static int fineCounter = 1;

    public static Fine createFine(User user, double amount, String reason) {
        String fineId = "F" + String.format("%03d", fineCounter++);
        Fine fine = new Fine(fineId, user, amount, reason);
        fines.add(fine);
        user.addFine(amount);
        return fine;
    }

    public static List<Fine> getFinesByUser(String userId) {
        List<Fine> userFines = new ArrayList<>();
        for (Fine fine : fines) {
            if (fine.getUser().getId().equalsIgnoreCase(userId) && !fine.isPaid()) {
                userFines.add(fine);
            }
        }
        return userFines;
    }

    public static double getTotalFinesByUser(String userId) {
        double total = 0;
        for (Fine fine : fines) {
            if (fine.getUser().getId().equalsIgnoreCase(userId) && !fine.isPaid()) {
                total += fine.getAmount();
            }
        }
        return total;
    }
    
    public static void clearFines() {
        fines.clear();
        fineCounter = 1;
    }
}