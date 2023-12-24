import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame() {
        setTitle("Youtube App");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        setIconImage(icon.getImage());
        getContentPane().setBackground(Color.BLUE);

        // Initialize the gui class and set its panel as content pane
        setContentPane(new gui().getPanel1());
    }
}
