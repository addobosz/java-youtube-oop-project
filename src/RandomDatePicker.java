import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

public class RandomDatePicker {
    // This class realizes the Singleton design pattern
    private static final RandomDatePicker instance = new RandomDatePicker();
    private RandomDatePicker() {
        // Private constructor to prevent instantiation
    }

    public Date getRandomDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(2005, 2023);
        gc.set(Calendar.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    public static RandomDatePicker getInstance() {
        return instance;
    }
}