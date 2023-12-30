import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Channel extends UserAccount implements Runnable {
    private ArrayList<UserAccount> mFollowers;
    private ArrayList<Video> mUploadedVideos;
    private Stream mStream;


    public Channel(String thumbnail, String name, Date joinDate, ArrayList<Channel> followingChannels, boolean premium, Media currentlyViewed, ArrayList<Media> queue, ArrayList<UserAccount> followers, ArrayList<Video> uploadedVideos, Stream stream) {
        super(thumbnail, name, joinDate, followingChannels, premium, currentlyViewed, queue);
        this.mFollowers = followers;
        this.mUploadedVideos = uploadedVideos;
        this.mStream = stream;
        simulationManager.getInstance().addChannel(this);
    }

    public void addVideo(Video video) {
        this.mUploadedVideos.add(video);
    }

    public void deleteVideo(Video video) {
        this.mUploadedVideos.remove(video);
    }

    public void startStream(Stream stream) {
        this.stopStream();
        this.setStream(stream);
        System.out.println(stream.getName()+" stream has just started.");
    }

    public void stopStream() {
        simulationManager.getInstance().getAllStreams().remove(this.getStream());
        this.setStream(null);
    }

    @Override
    public void run() {
        while (true) {
            if (this.getStream() != null) {
                System.out.println(this.getStream().getName()+" stream has "+this.getStream().getNumberOfViewers()+" viewers.");
                if (UserAccountFactory.random.nextInt(100) < 5) { // 5% chance of stopping a stream every second
                    this.stopStream();
                }
            } else if (UserAccountFactory.random.nextInt(100) < 1) { // 1% chance of starting a stream every second. Scheduled streams cancel randomly instantiated streams.
                Stream stream = new Stream("thumbnail", "stream", "description", 0, 0, 0, this);
                this.startStream(stream);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Getters
    public ArrayList<UserAccount> getFollowers() {
        return mFollowers;
    }

    public ArrayList<Video> getUploadedVideos() {
        return mUploadedVideos;
    }

    public Stream getStream() {
        return mStream;
    }

    // Setters
    public void setFollowers(ArrayList<UserAccount> followers) {
        this.mFollowers = followers;
    }

    public void addFollower(UserAccount follower) {
        this.mFollowers.add(follower);
    }

    public void removeFollower(UserAccount follower) {
        this.mFollowers.remove(follower);
    }

    public void setUploadedVideos(ArrayList<Video> uploadedVideos) {
        this.mUploadedVideos = uploadedVideos;
    }

    public void setStream(Stream stream) {
        this.mStream = stream;
    }
}
