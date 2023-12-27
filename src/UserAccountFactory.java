import java.util.ArrayList;
import java.util.Random;

public class UserAccountFactory {
    // Utility class
    static RandomLinePicker randomChannelNamePicker = new RandomLinePicker("src/text_files/channel_names.txt");
    static RandomLinePicker randomVideoTitlePicker = new RandomLinePicker("src/text_files/video_titles.txt");
    static RandomLinePicker randomDescriptionPicker = new RandomLinePicker("src/text_files/descriptions.txt");
    static Random random = new Random();

    private UserAccountFactory() {
        // Private constructor to prevent instantiation
    }

    public static void initializeUsers(int n) {
        for (int i = 0; i < n; i++) {
            boolean is_premium = random.nextBoolean();
            new UserAccount("example_thumbnail", randomChannelNamePicker.getRandomLine(), RandomDatePicker.getInstance().getRandomDate(), new ArrayList<Channel>(), is_premium, null, new ArrayList<Video>());
        }
    }

    public static void createVideos() {
        for (Channel channel : simulationManager.getInstance().getAllChannels()) {
            int videoCount = random.nextInt(10)+1; // 1-10 videos (both inclusive)
            int numberOfLikes = random.nextInt(1001); // 0-1000 likes (both inclusive)
            int duration = random.nextInt(60)+1; // 1-60 seconds of length (both inclusive)
            int numberOfViews = random.nextInt(2001) + numberOfLikes; // at least as many views as likes
            boolean isPremium = random.nextBoolean();
            for (int i = 0; i < videoCount; i++) {
                channel.addVideo(new Video("example thumbnail", randomVideoTitlePicker.getRandomLine(), randomDescriptionPicker.getRandomLine(), numberOfLikes, duration, RandomDatePicker.getInstance().getRandomDate(), numberOfViews, isPremium));
            }
        }
    }

}
