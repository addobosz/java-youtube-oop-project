import java.util.ArrayList;

public class ChannelFactory {
    // Utility class
    static RandomLinePicker randomChannelNamePicker = new RandomLinePicker("src/text_files/channel_names.txt");

    private ChannelFactory() {
        // Private constructor to prevent instantiation
    }

    public static void initializeChannels(int n) {
        for (int i = 0; i < n; i++) {
            new Channel("example_thumbnail", randomChannelNamePicker.getRandomLine(), RandomDatePicker.getInstance().getRandomDate(), new ArrayList<Channel>(), false, null, new ArrayList<Video>(), new ArrayList<UserAccount>(), new ArrayList<Video>(), null);
        }
    }

}
