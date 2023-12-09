package src;
import java.util.Date;
public class Main {
    public static void main(String[] args) {
        Video m = new Video("some thumbnail", "Medion", "description blah blah", 1000, 60, new Date(), 10000, true);
        System.out.println(m.getDescription());
        m.setNumberOfLikes(2000);
        System.out.println(m.getNumberOfLikes());
        System.out.println(m.getUploadDate());
        m.setNumberOfViews(2000);
        System.out.println(m.getNumberOfViews());
    }
}
