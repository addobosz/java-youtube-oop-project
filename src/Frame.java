import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    Frame() {
        JFrame frame = new JFrame("Youtube App");
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        frame.setIconImage(icon.getImage());
        frame.getContentPane().setBackground(Color.BLUE);
        frame.setVisible(true);
    }
}
