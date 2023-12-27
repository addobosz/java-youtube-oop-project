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

        // Initialize the simulation
        UserAccountFactory.initializeUsers(10);
        ChannelFactory.initializeChannels(10);
        UserAccountFactory.createVideos();


//        Channel c = new Channel("some thumbnail", "Medion", new Date(), new ArrayList<Channel>(), true, new Media("some thumbnail", "Medion", "description blah blah", 1000), new ArrayList<Video>(), new ArrayList<UserAccount>(), new ArrayList<Video>(), new Stream("some thumbnail", "Medion stream", "description blah blah", 1000, new Date(), 10000));
//        Channel c2 = new Channel("some thumbnail", "abab", new Date(), new ArrayList<Channel>(), true, new Media("some thumbnail", "Medion", "description blah blah", 1000), new ArrayList<Video>(), new ArrayList<UserAccount>(), new ArrayList<Video>(), new Stream("some thumbnail", "abab stream", "description blah blah", 1000, new Date(), 10000));
    }
}
