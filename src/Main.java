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
        UserAccountFactory.createStreams();
        System.out.println(simulationManager.getInstance().getAllStreams().size());
    }
}
