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
    protected ArrayList<Media> mQueue;
    protected ArrayList<Video> mLikedVideos;

    public UserAccount(String thumbnail, String name, Date joinDate, ArrayList<Channel> followingChannels, boolean premium, Media currentlyViewed, ArrayList<Media> queue) {
        this.mThumbnail = thumbnail;
        this.mName = name;
        this.mJoinDate = joinDate;
        this.mFollowingChannels = followingChannels;
        this.mPremium = premium;
        this.mCurrentlyViewed = currentlyViewed;
        this.mQueue = queue;
        this.mVideoStartTime = 0;
        this.mLikedVideos = new ArrayList<>();
        simulationManager.getInstance().addUser(this);
    }

    // Special methods
    @Override
    public void subscribe(Channel channel) {
        if (!this.mFollowingChannels.contains(channel)) {
            channel.addFollower(this);
            this.mFollowingChannels.add(channel);
        }
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
            System.out.println(this.getName()+" is watching "+video.getName()+".");
        }
    }
    @Override
    public void watchStream(Stream stream) {
        this.setCurrentlyViewed(stream);
        stream.setNumberOfViewers(stream.getNumberOfViewers() + 1);
        System.out.println(this.getName()+" is watching "+stream.getName()+".");
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
                if (this.getCurrentlyViewed() instanceof Video) {
                    if (UserAccountFactory.random.nextInt(1000) < 17*30) { // 1.7% chance of liking a video every second
                        this.likeVideo();
                    }
                    if (this.mVideoStartTime + ((Video) this.getCurrentlyViewed()).getDuration() * 1000L < System.currentTimeMillis()) { // if the video has ended
                        this.setCurrentlyViewed(null);
                    }
                }
                if (UserAccountFactory.random.nextInt(1000) < 17*30) {
                    this.subscribe(this.getCurrentlyViewed().getAuthor()); // 1.7% chance of subscribing to the author of the video or stream every second
                }
            } else { // if the user is not watching anything, watch the next item in the queue
                if (!this.getQueue().isEmpty()) { // if the queue is not empty, watch the next item (either video or stream)
                    Media next = this.getQueue().getFirst();
                    this.getQueue().removeFirst();
                    if (next instanceof Video) {
                        this.watchVideo((Video) next);
                    } else if (next instanceof Stream) {
                        this.watchStream((Stream) next);
                    }
                }
            }
            if (this.getQueue().size() < 10) { // if the queue is not full, add a random video or stream to the queue
                if (UserAccountFactory.random.nextBoolean()) { // 50% chance of adding a video
                    Video video = simulationManager.getInstance().getAllVideos().get(UserAccountFactory.random.nextInt(simulationManager.getInstance().getAllVideos().size()));
                    if (video != this.getCurrentlyViewed()) { // if the video is not the one that the user is currently watching
                        this.getQueue().add(video);
                    }
                } else { // 50% chance of adding a stream
                    Stream stream = simulationManager.getInstance().getAllStreams().get(UserAccountFactory.random.nextInt(simulationManager.getInstance().getAllStreams().size()));
                    if (stream != this.getCurrentlyViewed()) { // if the stream is not the one that the user is currently watching
                        this.getQueue().add(stream);
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
    public ArrayList<Media> getQueue() {
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
    public void setQueue(ArrayList<Media> queue) {
        this.mQueue = queue;
    }
    @Override
    public void setVideoStartTime(long videoStartTime) {
        this.mVideoStartTime = videoStartTime;
    }
}
