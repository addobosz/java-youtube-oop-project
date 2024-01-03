import java.io.ObjectOutputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// This class realizes the Singleton design pattern
class simulationManager implements Serializable {
    private static volatile simulationManager instance = new simulationManager();
    private volatile List<Channel> allChannels = new ArrayList<>();
    private volatile List<UserAccount> allUsers = new ArrayList<>();
    private volatile List<Video> allVideos = new ArrayList<>();
    private volatile List<Stream> allStreams = new ArrayList<>();
    static volatile ArrayList<Thread> agentThreads = new ArrayList<>();
    static boolean isRunning = false;
    @Serial
    private static final long serialVersionUID = 6529685098267757690L;
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
    public static void initializeSimulation(int numberOfUsers, int numberOfChannels) {
        UserAccountFactory.initializeUsers(numberOfUsers);
        UserAccountFactory.initializeChannels(numberOfChannels);
        UserAccountFactory.createVideos();
        UserAccountFactory.createStreams();
    }

    public static void startSimulation() {
        isRunning = true;
        for (Thread thread : agentThreads) {
            thread.start();
        }
    }

    public static void stopSimulation() {
        isRunning = false;
        System.out.println("\nStopping simulation...\n");
    }

    public List<UserAccount> search(String name) {
        List<UserAccount> resultChannels = new ArrayList<>();
        for (UserAccount channel : this.getAllUsers()) {
            if (channel.getName().contains(name)) {
                resultChannels.add(channel);
            }
        }
        return resultChannels;
    }

    public static void saveState() { // Saving the state of the simulation using serialization.
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("simulationManager.ser"))) {
            oos.writeObject(simulationManager.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("simulationManager.ser"))) { // Loading the state of the simulation using deserialization.
            // Remove all existing channels, users, videos and streams
            instance.getAllChannels().clear();
            instance.getAllUsers().clear();
            instance.getAllVideos().clear();
            instance.getAllStreams().clear();
            // Remove all existing threads
            agentThreads.clear();
            // Cast the deserialized object to the appropriate type and assign it to the instance variable
            instance = (simulationManager) ois.readObject();
            // Reload the old threads
            isRunning = true;
            for (Channel channel : instance.getAllChannels()) {
                Thread channelThread = new Thread(channel);
                agentThreads.add(channelThread);
            }
            // Create new threads for users
            for (UserAccount user : instance.getAllUsers()) {
                Thread userThread = new Thread(user);
                agentThreads.add(userThread);
            }
            startSimulation();
            System.out.println("Simulation loaded successfully.");

        } catch (Exception e) {
            e.printStackTrace();
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