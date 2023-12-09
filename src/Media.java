package src;

public class Media {
    protected String mThumbnail;
    protected String mName;
    protected String mDescription;
    protected int mNumberOfLikes;

    public Media(String thumbnail, String name, String description, int numberOfLikes) {
        this.mThumbnail = thumbnail;
        this.mName = name;
        this.mDescription = description;
        this.mNumberOfLikes = numberOfLikes;
    }

    // Getters
    public String getThumbnail() {
        return mThumbnail;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getNumberOfLikes() {
        return mNumberOfLikes;
    }

    // Setters
    public void setThumbnail(String thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.mNumberOfLikes = numberOfLikes;
    }

}
