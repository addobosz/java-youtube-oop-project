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
    }

    public void addVideo(Video video) {
        ;
    }

    public void deleteVideo(Video video) {
        ;
    }

    public void startStream(Stream stream) {
        ;
    }

    public void stopStream() {
        ;
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

    public void setUploadedVideos(ArrayList<Video> uploadedVideos) {
        this.mUploadedVideos = uploadedVideos;
    }

    public void setStream(Stream stream) {
        this.mStream = stream;
    }
}
