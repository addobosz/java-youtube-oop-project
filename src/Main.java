import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Display the GUI
        SwingUtilities.invokeLater(() -> {
            gui initialGui = new gui();
            Frame frame = new Frame(initialGui, true);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
