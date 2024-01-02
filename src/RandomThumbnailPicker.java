import java.io.File;

public class RandomThumbnailPicker {
    private static final File dirUserPictures = new File("src/images/user-pictures/");
    private static final File dirVideoThumbnails = new File("src/images/video-thumbnails/");
    private static final File[] userPictures = dirUserPictures.listFiles();
    private static final File[] videoThumbnails = dirVideoThumbnails.listFiles();

    private RandomThumbnailPicker() {
        // Private constructor to prevent instantiation
    }

    public static String getRandomUserPicture() {
        File file = userPictures[UserAccountFactory.random.nextInt(userPictures.length)];
        return "images/user-pictures/"+file.getName();
    }

    public static String getRandomVideoThumbnail() {
        File file = videoThumbnails[UserAccountFactory.random.nextInt(videoThumbnails.length)];
        return "images/video-thumbnails/"+file.getName();
    }
}
