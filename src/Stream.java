import java.util.Date;

public class Stream extends Media {
    private int mStartTime;
    private int mNumberOfViewers;

    public Stream(String thumbnail, String name, String description, int numberOfLikes, int startTime, int numOfViewers) {
        super(thumbnail, name, description, numberOfLikes);
        this.mStartTime = startTime;
        this.mNumberOfViewers = numOfViewers;
        simulationManager.getInstance().addStream(this);
    }

    // Getters
    public int getStartTime() {
        return mStartTime;
    }

    public int getNumberOfViewers() {
        return mNumberOfViewers;
    }

    // Setters
    public void setStartTime(int startTime) {
        this.mStartTime = startTime;
    }

    public void setNumberOfViewers(int numberOfViewers) {
        this.mNumberOfViewers = numberOfViewers;
    }


}
