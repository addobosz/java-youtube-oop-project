import java.util.ArrayList;

public class UserAccountFactory {
    // This class realizes the Singleton design pattern
    static RandomLinePicker randomVideoTitlePicker = new RandomLinePicker("src/text_files/video_titles.txt");
    static RandomLinePicker randomChannelNamePicker = new RandomLinePicker("src/text_files/channel_names.txt");
    static RandomLinePicker randomDescriptionPicker = new RandomLinePicker("src/text_files/descriptions.txt");
    private static final UserAccountFactory instance = new UserAccountFactory();
    private UserAccountFactory() {
        // Private constructor to prevent instantiation
    }

    public static void initializeUsers(int n) {
        for (int i = 0; i < n; i++) {
            UserAccount newUser  = new UserAccount("example_thumbnail", randomChannelNamePicker.getRandomLine(), RandomDatePicker.getInstance().getRandomDate(), new ArrayList<Channel>(), false, null, new ArrayList<Video>());
        }
    }

    public static UserAccountFactory getInstance() {
        return instance;
    }
}
