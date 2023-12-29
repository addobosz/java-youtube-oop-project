import java.lang.reflect.Array;
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
    static ArrayList<Thread> agentThreads = new ArrayList<>();


    private UserAccountFactory() {
        // Private constructor to prevent instantiation
    }

    public static void initializeUsers(int n) {
        for (int i = 0; i < n; i++) {
            boolean is_premium = random.nextBoolean();
            UserAccount user = new UserAccount("example_thumbnail", randomChannelNamePicker.getRandomLine(), RandomDatePicker.getInstance().getRandomDate(), new ArrayList<Channel>(), is_premium, null, new ArrayList<Media>());
            Thread userThread = new Thread(user);
            agentThreads.add(userThread);
        }
    }

    public static void initializeChannels(int n) {
        for (int i = 0; i < n; i++) {
            Channel channel = new Channel("example_thumbnail", randomChannelNamePicker.getRandomLine(), RandomDatePicker.getInstance().getRandomDate(), new ArrayList<Channel>(), false, null, new ArrayList<Media>(), new ArrayList<UserAccount>(), new ArrayList<Video>(), null);
            Thread channelThread = new Thread(channel);
            agentThreads.add(channelThread);
        }
    }

    public static void createVideos() {
        // create videos for each channel
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

    public static void createStreams() {
        // create streams for each channel
        for (Channel channel : simulationManager.getInstance().getAllChannels()) {
                int numberOfLikes = random.nextInt(1001); // 0-1000 likes (both inclusive)
                int delay = random.nextInt(120) + 1; // 1-120 seconds of wait time until the stream is started (both inclusive)
                Stream stream = new Stream("example thumbnail", randomVideoTitlePicker.getRandomLine(), randomDescriptionPicker.getRandomLine(), numberOfLikes, delay, 0);
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                Runnable streamRunnable = () -> channel.startStream(stream); // convert the stream to a runnable in order to schedule it
                scheduler.schedule(streamRunnable, delay, TimeUnit.SECONDS); // schedule the stream to start after the delay
        }
    }
}
