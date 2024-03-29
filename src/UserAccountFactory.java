import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
            UserAccount user = new UserAccount(RandomThumbnailPicker.getRandomUserPicture(), randomChannelNamePicker.getRandomLine(), RandomDatePicker.getInstance().getRandomDate(), new ArrayList<Channel>(), is_premium, null, new ArrayList<Media>());
            Thread userThread = new Thread(user);
            simulationManager.agentThreads.add(userThread);
        }
    }

    public static void initializeChannels(int n) {
        for (int i = 0; i < n; i++) {
            Channel channel = new Channel(RandomThumbnailPicker.getRandomUserPicture(), randomChannelNamePicker.getRandomLine(), RandomDatePicker.getInstance().getRandomDate(), new ArrayList<Channel>(), false, null, new ArrayList<Media>(), new ArrayList<UserAccount>(), new ArrayList<Video>(), null);
            Thread channelThread = new Thread(channel);
            simulationManager.agentThreads.add(channelThread);
        }
    }

    public static void createVideos() {
        // create videos for each channel
        for (Channel channel : simulationManager.getInstance().getAllChannels()) {
            int videoCount = random.nextInt(10)+1; // 1-10 videos (both inclusive)
            int duration = random.nextInt(60)+1; // 1-60 seconds of length (both inclusive)
            boolean isPremium = random.nextBoolean();
            for (int i = 0; i < videoCount; i++) {
                channel.addVideo(new Video(RandomThumbnailPicker.getRandomVideoThumbnail(), randomVideoTitlePicker.getRandomLine(), randomDescriptionPicker.getRandomLine(), 0, duration, RandomDatePicker.getInstance().getRandomDate(), 0, isPremium, channel));
            }
        }
    }

    public static void createStreams() {
        // create streams for each channel
        for (Channel channel : simulationManager.getInstance().getAllChannels()) {
                int delay = random.nextInt(120) + 1; // 1-120 seconds of wait time until the stream is started (both inclusive)
                Stream stream = new Stream(RandomThumbnailPicker.getRandomVideoThumbnail(), randomVideoTitlePicker.getRandomLine(), randomDescriptionPicker.getRandomLine(), 0, delay, 0, channel);
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                Runnable streamRunnable = () -> channel.startStream(stream); // convert the stream to a runnable in order to schedule it
                scheduler.schedule(streamRunnable, delay, TimeUnit.SECONDS); // schedule the stream to start after the delay
        }
    }
}
