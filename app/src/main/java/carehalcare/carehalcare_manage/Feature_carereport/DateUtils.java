package carehalcare.carehalcare_manage.Feature_carereport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatDate(String dateStr) {

        if (dateStr == null) {
            return ""; // 또는 다른 기본값을 반환할 수 있음
        }

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String newDate = "";
        try {
            Date date = originalFormat.parse(dateStr);
            newDate = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    public static String formatDatestring(String dateStr) {

        if (dateStr == null) {
            return ""; // 또는 다른 기본값을 반환할 수 있음
        }

        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.KOREA);
        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초", Locale.KOREA);
        String newDate = "";
        try {
            Date date = originalFormat.parse(dateStr);
            newDate = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
}
