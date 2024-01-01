import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frame extends JFrame {
    public Frame() {
        setTitle("Youtube App");
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        setIconImage(icon.getImage());
        getContentPane().setBackground(Color.BLUE);
        gui gui = new gui();
        for (UserAccount user : simulationManager.getInstance().getAllUsers()) {
            gui.addUserToList(user);
        }

        // Initialize the gui class and set its panel as content pane
        setContentPane(gui.getPanel1());
    }
}
