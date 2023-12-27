import java.util.ArrayList;
import java.util.Date;

public class Channel extends UserAccount {
    private ArrayList<UserAccount> mFollowers;
    private ArrayList<Video> mUploadedVideos;
    private Stream mStream;

    public Channel(String thumbnail, String name, Date joinDate, ArrayList<Channel> followingChannels, boolean premium, Media currentlyViewed, ArrayList<Video> queue, ArrayList<UserAccount> followers, ArrayList<Video> uploadedVideos, Stream stream) {
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
        this.setStream(stream);
    }

    public void stopStream() {
        this.setStream(null);
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
