import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserAccount implements UserInterface, Runnable {
    protected String mThumbnail;
    protected String mName;
    protected Date mJoinDate;
    protected ArrayList<Channel> mFollowingChannels;
    protected boolean mPremium;
    protected Media mCurrentlyViewed;
    protected long mVideoStartTime;
    protected ArrayList<Video> mQueue;
    protected ArrayList<Video> mLikedVideos;

    public UserAccount(String thumbnail, String name, Date joinDate, ArrayList<Channel> followingChannels, boolean premium, Media currentlyViewed, ArrayList<Video> queue) {
        this.mThumbnail = thumbnail;
        this.mName = name;
        this.mJoinDate = joinDate;
        this.mFollowingChannels = followingChannels;
        this.mPremium = premium;
        this.mCurrentlyViewed = currentlyViewed;
        this.mQueue = queue;
        this.mVideoStartTime = 0;
        simulationManager.getInstance().addUser(this);
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
        if (video.getPremium() && !this.getPremium()) {
            System.out.println("You need to be a premium user to watch this video.");
        } else {
            this.setCurrentlyViewed(video);
            this.setVideoStartTime(System.currentTimeMillis());
            video.setNumberOfViews(video.getNumberOfViews() + 1);
        }
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
    public void likeVideo() {
        Video video = (Video) this.getCurrentlyViewed();
        if (!this.mLikedVideos.contains(video)) { // only works if the video is not already liked
            video.setNumberOfLikes(video.getNumberOfLikes() + 1);
            this.mLikedVideos.add(video);
        }
    }
    @Override
    public List<Channel> search(String name) {
        List<Channel> resultChannels = new ArrayList<>();
        for (Channel channel : simulationManager.getInstance().getAllChannels()) {
            if (channel.getName().contains(name)) {
                resultChannels.add(channel);
            }
        }
        return resultChannels;
    }
    @Override
    public void run() {
        while (true) {
            if (this.getCurrentlyViewed() != null) {
                if (UserAccountFactory.random.nextInt(1000) < 17) { // 1.7% chance of liking a video every second
                    this.likeVideo();
                }
                if (this.mVideoStartTime + ((Video) this.getCurrentlyViewed()).getDuration() * 1000L < System.currentTimeMillis()) { // if the video has ended
                    this.setCurrentlyViewed(null);
                }
            } else {

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if
        }
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
    @Override
    public long getVideoStartTime() {
        return mVideoStartTime;
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
    @Override
    public void setVideoStartTime(long videoStartTime) {
        this.mVideoStartTime = videoStartTime;
    }
}
