import javax.swing.*;
import java.awt.*;
import java.util.Date;
public class Main {
    public static void main(String[] args) {
//        ImageIcon image = new ImageIcon("images/icon.png");
//
//        JLabel label = new JLabel();
//        label.setText("elo leo elo");
//        label.setIcon(image);
//        label.setHorizontalTextPosition(JLabel.CENTER);
//
//        Frame frame = new Frame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(500,500);
//        frame.setVisible(true);
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        frame.setIconImage(icon.getImage());
        frame.getContentPane().setBackground(Color.BLUE);
        frame.setContentPane(new gui().getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

//        Video m = new Video("some thumbnail", "Medion", "description blah blah", 1000, 60, new Date(), 10000, true);
//        System.out.println(m.getDescription());
//        m.setNumberOfLikes(2000);
//        System.out.println(m.getNumberOfLikes());
//        System.out.println(m.getUploadDate());
//        m.setNumberOfViews(2000);
//        System.out.println(m.getNumberOfViews());
    }
}
