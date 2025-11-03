/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package library.data;

import library.entities.CD;
import java.util.ArrayList;
import java.util.List;

public class CDData {
    private static List<CD> cds = new ArrayList<>();

    static {
        // بيانات افتراضية (اختيارية)
        cds.add(new CD("CD001", "Best Hits", "Various Artists"));
        cds.add(new CD("CD002", "Classical Magic", "Mozart"));
    }

    // ✅ إرجاع جميع الـ CDs
    public static List<CD> getAllCDs() {
        return new ArrayList<>(cds);
    }

    // ✅ إضافة CD جديد
    public static void addCD(CD cd) {
        cds.add(cd);
    }

    // ✅ البحث عن CD باستخدام الـ ID
    public static CD getCD(String cdId) {
        for (CD cd : cds) {
            if (cd.getCdId().equalsIgnoreCase(cdId)) {
                return cd;
            }
        }
        return null; // إذا لم يُعثر عليه
    }

    // ✅ حذف أو تصفية البيانات (للاختبارات)
    public static void clearCDs() {
        cds.clear();
    }
}

