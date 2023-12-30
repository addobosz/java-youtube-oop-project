public class Media {
    protected String mThumbnail;
    protected String mName;
    protected String mDescription;
    protected volatile int mNumberOfLikes;
    protected Channel mAuthor;

    public Media(String thumbnail, String name, String description, int numberOfLikes, Channel author) {
        this.mThumbnail = thumbnail;
        this.mName = name;
        this.mDescription = description;
        this.mNumberOfLikes = numberOfLikes;
        this.mAuthor = author;
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

    public Channel getAuthor() {
        return this.mAuthor;
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

    public void setAuthor(Channel author) {
        this.mAuthor = author;
    }

}
