package src;

import java.util.Date;

public class Video extends Media {
    private int mDuration;
    private Date mUploadDate;
    private int mNumberOfViews;
    private boolean mPremium;

    public Video(String thumbnail, String name, String description, int numberOfLikes, int duration, Date uploadDate, int numberOfViews, boolean premium) {
        super(thumbnail, name, description, numberOfLikes);
        this.mDuration = duration;
        this.mUploadDate = uploadDate;
        this.mNumberOfViews = numberOfViews;
        this.mPremium = premium;
    }

    // Getters
    public int getDuration() {
        return mDuration;
    }

    public Date getUploadDate() {
        return mUploadDate;
    }

    public int getNumberOfViews() {
        return mNumberOfViews;
    }

    public boolean getPremium() {
        return mPremium;
    }

    // Setters
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public void setUploadDate(Date uploadDate) {
        this.mUploadDate = uploadDate;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.mNumberOfViews = numberOfViews;
    }

    public void setPremium(boolean premium) {
        this.mPremium = premium;
    }
}
