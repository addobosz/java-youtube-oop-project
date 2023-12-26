import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame();
            frame.pack();
            frame.setVisible(true);
        });

//        RandomLinePicker randomVideoTitlePicker = new RandomLinePicker("src/text_files/video_titles.txt");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(randomVideoTitlePicker.getRandomLine());
//        }

        Channel c = new Channel("some thumbnail", "Medion", new Date(), new ArrayList<Channel>(), true, new Media("some thumbnail", "Medion", "description blah blah", 1000), new ArrayList<Video>(), new ArrayList<UserAccount>(), new ArrayList<Video>(), new Stream("some thumbnail", "Medion stream", "description blah blah", 1000, new Date(), 10000));
        Channel c2 = new Channel("some thumbnail", "abab", new Date(), new ArrayList<Channel>(), true, new Media("some thumbnail", "Medion", "description blah blah", 1000), new ArrayList<Video>(), new ArrayList<UserAccount>(), new ArrayList<Video>(), new Stream("some thumbnail", "abab stream", "description blah blah", 1000, new Date(), 10000));
        System.out.println(c.getName());
        System.out.println(c.getFollowers());
        c.subscribe(c2);
        System.out.println(c2.getFollowers());
        System.out.println(c.getFollowingChannels());
        c.unsubscribe(c2);
        System.out.println(c2.getFollowers());
        System.out.println(c.getFollowingChannels());

//        Video m = new Video("some thumbnail", "Medion", "description blah blah", 1000, 60, new Date(), 10000, true);
//        System.out.println(m.getDescription());
//        m.setNumberOfLikes(2000);
//        System.out.println(m.getNumberOfLikes());
//        System.out.println(m.getUploadDate());
//        m.setNumberOfViews(2000);
//        System.out.println(m.getNumberOfViews());
    }
}
