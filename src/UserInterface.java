import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public interface UserInterface {
    // Getters
    String getThumbnail();

    String getName();

    Date getJoinDate();

    ArrayList<Channel> getFollowingChannels();

    boolean getPremium();

    Media getCurrentlyViewed();

    ArrayList<Media> getQueue();

    long getVideoStartTime();

    ArrayList<Video> getLikedVideos();

    // Setters
    void setThumbnail(String thumbnail);

    void setName(String name);

    void setJoinDate(Date joinDate);

    void setFollowingChannels(ArrayList<Channel> followingChannels);

    void setPremium(boolean premium);

    void setCurrentlyViewed(Media currentlyViewed);

    void setQueue(ArrayList<Media> queue);

    void setVideoStartTime(long videoStartTime);

    void setLikedVideos(ArrayList<Video> likedVideos);

    // Additional methods
    void subscribe(Channel channel);

    void unsubscribe(Channel channel);

    void watchVideo(Video video);

    void watchStream(Stream stream);

    void stopWatchingStream();

    void likeVideo();
}
