import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame();
            frame.pack();
            frame.setVisible(true);
        });

        // Initialize the simulation
        simulationManager.getInstance().initializeSimulation(1,1);
        // Start the simulation
        simulationManager.getInstance().startSimulation();
    }
}
