import javax.swing.*;
import java.awt.*;
import java.util.Date;
public class Main {
    public static void main(String[] args) {
        ImageIcon image = new ImageIcon("images/icon.png");

        JLabel label = new JLabel();
        label.setText("elo leo elo");
        label.setIcon(image);

        Frame frame = new Frame();
        frame.add(label);
        //frame.setContentPane(new gui().getPanel1());
        //frame.pack();
//        Video m = new Video("some thumbnail", "Medion", "description blah blah", 1000, 60, new Date(), 10000, true);
//        System.out.println(m.getDescription());
//        m.setNumberOfLikes(2000);
//        System.out.println(m.getNumberOfLikes());
//        System.out.println(m.getUploadDate());
//        m.setNumberOfViews(2000);
//        System.out.println(m.getNumberOfViews());
    }
}
