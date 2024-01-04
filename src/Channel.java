import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Channel extends UserAccount implements Runnable {
    private volatile ArrayList<UserAccount> mFollowers;
    private volatile ArrayList<Video> mUploadedVideos;
    private volatile Stream mStream;
    private final Semaphore semStream = new Semaphore(1);

    public Channel(String thumbnail, String name, Date joinDate, ArrayList<Channel> followingChannels, boolean premium, Media currentlyViewed, ArrayList<Media> queue, ArrayList<UserAccount> followers, ArrayList<Video> uploadedVideos, Stream stream) {
        super(thumbnail, name, joinDate, followingChannels, premium, currentlyViewed, queue);
        this.mFollowers = followers;
        this.mUploadedVideos = uploadedVideos;
        this.mStream = stream;
        simulationManager.getInstance().addChannel(this);
    }

    public synchronized void addVideo(Video video) {
        this.mUploadedVideos.add(video);
    }

    public synchronized void deleteVideo(Video video) {
        this.mUploadedVideos.remove(video);
        simulationManager.getInstance().getAllVideos().remove(video);
    }

    public synchronized void startStream(Stream stream) {
        this.stopStream();
        // check is the author still exists (if not, the stream is cancelled)
        if (!simulationManager.getInstance().getAllChannels().contains(stream.getAuthor())) {
            return;
        }
        this.setStream(stream);
        System.out.println(stream.getAuthor().getName() + "'s stream '" + stream.getName()+"' has just started.");
    }

    public synchronized void stopStream() {
        simulationManager.getInstance().getAllStreams().remove(this.getStream());
        this.setStream(null);
    }

    @Override
    public void run() {
        // Channels have to act as both users and channels.
        while (simulationManager.isRunning) {
            // channel tasks
            try {
                semStream.acquire();
                if (this.getStream() != null) {
                    if (UserAccountFactory.random.nextInt(100) < 3) { // 3% chance of stopping a stream every second
                        this.stopStream();
                    }
                } else if (UserAccountFactory.random.nextInt(100) < 1) { // 1% chance of starting a stream every second. Scheduled streams cancel randomly instantiated streams.
                    Stream stream = new Stream(RandomThumbnailPicker.getRandomVideoThumbnail(), UserAccountFactory.randomVideoTitlePicker.getRandomLine(), UserAccountFactory.randomDescriptionPicker.getRandomLine(), 0, 0, 0, this);
                    this.startStream(stream);
                }
                if (UserAccountFactory.random.nextInt(100) < 1) { // 1% chance of uploading a video every second
                    int numberOfLikes = UserAccountFactory.random.nextInt(1001); // 0-1000 likes (both inclusive)
                    int duration = UserAccountFactory.random.nextInt(60)+1; // 1-60 seconds of length (both inclusive)
                    int numberOfViews = UserAccountFactory.random.nextInt(2001) + numberOfLikes; // at least as many views as likes
                    boolean isPremium = UserAccountFactory.random.nextBoolean();
                    this.addVideo(new Video(RandomThumbnailPicker.getRandomVideoThumbnail(), UserAccountFactory.randomVideoTitlePicker.getRandomLine(), UserAccountFactory.randomDescriptionPicker.getRandomLine(), numberOfLikes, duration, RandomDatePicker.getInstance().getRandomDate(), numberOfViews, isPremium, this));
                    System.out.println(this.getName()+" has uploaded a new video.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                semStream.release();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted. Exiting.");
                return;
            }
            // user tasks
            try {
                semWatch.acquire();
                Media curr = this.getCurrentlyViewed(); // create local variable to prevent errors due to the video being set to null in the middle of the execution
                if (curr != null) {
                    if (curr instanceof Video) {
                        if (UserAccountFactory.random.nextInt(1000) < 17) { // 1.7% chance of liking a video every second
                            this.likeVideo();
                        }
                        if (this.mVideoStartTime + ((Video) curr).getDuration() * 1000L < System.currentTimeMillis()) { // if the video has ended
                            this.setCurrentlyViewed(null);
                        }
                    }
                    if (UserAccountFactory.random.nextInt(1000) < 17) {
                        try {
                            this.subscribe(curr.getAuthor()); // 1.7% chance of subscribing to the author of the video or stream every second
                        } catch (Exception e) {
                            System.out.println(curr);
                            e.printStackTrace();
                        }
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semWatch.release();
            }
            try {
                semQueue.acquire();

                if (this.getQueue().size() < 10) { // if the queue is not full, add a random video or stream to the queue
                    if (UserAccountFactory.random.nextBoolean()) { // 50% chance of adding a video
                        Video video = simulationManager.getInstance().getAllVideos().get(UserAccountFactory.random.nextInt(simulationManager.getInstance().getAllVideos().size()));
                        if (video != this.getCurrentlyViewed()) { // if the video is not the one that the user is currently watching
                            this.getQueue().add(video);
                        }
                    } else { // 50% chance of adding a stream
                        if (!simulationManager.getInstance().getAllStreams().isEmpty()) {
                            Stream stream = simulationManager.getInstance().getAllStreams().get(UserAccountFactory.random.nextInt(simulationManager.getInstance().getAllStreams().size()));
                            if (stream != this.getCurrentlyViewed()) { // if the stream is not the one that the user is currently watching
                                this.getQueue().add(stream);
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semQueue.release();
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
