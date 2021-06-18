package util;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateTransform {

    public static String dateSQLHHMMSS(@NotNull Long date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
    }

    public static String dateSQLYYYYMMDD(@NotNull Long date) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
    }

    public static String DATEBeautyYYYYMMDD(@NotNull String date) {
        DateFormat outPutFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return getOutPutFormat(date, outPutFormat);
    }

    public static String dateBeautyHHMMSS(@NotNull String date) {
        DateFormat outPutFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return getOutPutFormat(date, outPutFormat);
    }

    private static String getOutPutFormat(@NotNull String date, @NotNull DateFormat outPutFormat) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date beautyDate = null;
        try {
            beautyDate = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (beautyDate == null) {
            return "null";
        }

        return outPutFormat.format(beautyDate);
    }

    public static Long dateJava(@NotNull String date) {
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date d = f.parse(date);
            return Objects.requireNonNull(d).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
