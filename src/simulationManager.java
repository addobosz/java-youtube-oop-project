import java.util.ArrayList;
import java.util.List;

// This class realizes the Singleton design pattern
class simulationManager {
    private static final simulationManager instance = new simulationManager();
    private final List<Channel> allChannels = new ArrayList<>();
    private final List<UserAccount> allUsers = new ArrayList<>();
    private final List<Video> allVideos = new ArrayList<>();
    private final List<Stream> allStreams = new ArrayList<>();
    private simulationManager() {
        // Private constructor to prevent instantiation
    }
    public static simulationManager getInstance() {
        return instance;
    }
    public void addChannel(Channel channel) {
        allChannels.add(channel);
    }
    public void addUser(UserAccount user) {
        allUsers.add(user);
    }
    public void addVideo(Video video) {
        allVideos.add(video);
    }
    public void addStream(Stream stream) {
        allStreams.add(stream);
    }
    public void initializeSimulation(int numberOfUsers, int numberOfChannels) {
        UserAccountFactory.initializeUsers(numberOfUsers);
        UserAccountFactory.initializeChannels(numberOfChannels);
        UserAccountFactory.createVideos();
        UserAccountFactory.createStreams();
    }

    public void startSimulation() {
        for (Thread thread : UserAccountFactory.agentThreads) {
            thread.start();
        }
    }

    //getters
    public List<Channel> getAllChannels() {
        return allChannels;
    }
    public List<UserAccount> getAllUsers() {
        return allUsers;
    }
    public List<Video> getAllVideos() {
        return allVideos;
    }
    public List<Stream> getAllStreams() {
        return allStreams;
    }
}