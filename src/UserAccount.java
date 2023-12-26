import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserAccount implements UserInterface{
    protected String mThumbnail;
    protected String mName;
    protected Date mJoinDate;
    protected ArrayList<Channel> mFollowingChannels;
    protected boolean mPremium;
    protected Media mCurrentlyViewed;
    protected ArrayList<Video> mQueue;

    public UserAccount(String thumbnail, String name, Date joinDate, ArrayList<Channel> followingChannels, boolean premium, Media currentlyViewed, ArrayList<Video> queue) {
        this.mThumbnail = thumbnail;
        this.mName = name;
        this.mJoinDate = joinDate;
        this.mFollowingChannels = followingChannels;
        this.mPremium = premium;
        this.mCurrentlyViewed = currentlyViewed;
        this.mQueue = queue;
    }

    // Special methods
    @Override
    public void subscribe(Channel channel) {
        channel.addFollower(this);
        this.mFollowingChannels.add(channel);
    }
    @Override
    public void unsubscribe(Channel channel) {
        channel.removeFollower(this);
        this.mFollowingChannels.remove(channel);
    }
    @Override
    public void watchVideo(Video video) {
        this.setCurrentlyViewed(video);
        video.setNumberOfViews(video.getNumberOfViews() + 1);
    }
    @Override
    public void watchStream(Stream stream) {
        this.setCurrentlyViewed(stream);
        stream.setNumberOfViewers(stream.getNumberOfViewers() + 1);
    }
    @Override
    public void stopWatchingStream() {
        Stream stream = (Stream) this.getCurrentlyViewed();
        stream.setNumberOfViewers(stream.getNumberOfViewers() - 1);
        this.setCurrentlyViewed(null);
    }
    @Override
    public List<Channel> search(String name) {
        List<Channel> resultChannels = new ArrayList<>();
        for (Channel channel : ChannelsManager.getInstance().getAllChannels()) {
            if (channel.getName().contains(name)) {
                resultChannels.add(channel);
            }
        }
        return resultChannels;
    }

    // Getters
    @Override
    public String getThumbnail() {
        return mThumbnail;
    }
    @Override
    public String getName() {
        return mName;
    }
    @Override
    public Date getJoinDate() {
        return mJoinDate;
    }
    @Override
    public ArrayList<Channel> getFollowingChannels() {
        return mFollowingChannels;
    }
    @Override
    public boolean getPremium() {
        return mPremium;
    }
    @Override
    public Media getCurrentlyViewed() {
        return mCurrentlyViewed;
    }
    @Override
    public ArrayList<Video> getQueue() {
        return mQueue;
    }

    // Setters
    @Override
    public void setThumbnail(String thumbnail) {
        this.mThumbnail = thumbnail;
    }
    @Override
    public void setName(String name) {
        this.mName = name;
    }
    @Override
    public void setJoinDate(Date joinDate) {
        this.mJoinDate = joinDate;
    }
    @Override
    public void setFollowingChannels(ArrayList<Channel> followingChannels) {
        this.mFollowingChannels = followingChannels;
    }
    @Override
    public void setPremium(boolean premium) {
        this.mPremium = premium;
    }
    @Override
    public void setCurrentlyViewed(Media currentlyViewed) {
        this.mCurrentlyViewed = currentlyViewed;
    }
    @Override
    public void setQueue(ArrayList<Video> queue) {
        this.mQueue = queue;
    }
}
