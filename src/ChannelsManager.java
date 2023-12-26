import java.util.ArrayList;
import java.util.List;

// This class realizes the Singleton design pattern
class ChannelsManager {
    private static final ChannelsManager instance = new ChannelsManager();
    private final List<Channel> allChannels = new ArrayList<>();

    private ChannelsManager() {
        // Private constructor to prevent instantiation
    }

    public static ChannelsManager getInstance() {
        return instance;
    }

    public void addChannel(Channel channel) {
        allChannels.add(channel);
    }

    public List<Channel> getAllChannels() {
        return allChannels;
    }
}