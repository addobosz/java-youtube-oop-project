package src;

import java.util.Date;

public class Stream extends Media {
    private Date mStartTime;
    private int mNumberOfViewers;

    public Stream(String thumbnail, String name, String description, int numberOfLikes, Date startTime, int numOfViewers) {
        super(thumbnail, name, description, numberOfLikes);
        this.mStartTime = startTime;
        this.mNumberOfViewers = numOfViewers;
    }

    // Getters
    public Date getStartTime() {
        return mStartTime;
    }

    public int getNumberOfViewers() {
        return mNumberOfViewers;
    }

    // Setters
    public void setStartTime(Date startTime) {
        this.mStartTime = startTime;
    }

    public void setNumberOfViewers(int numberOfViewers) {
        this.mNumberOfViewers = numberOfViewers;
    }


}
