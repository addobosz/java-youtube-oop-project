import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
public class Main {
    public static void main(String[] args) {

        // Initialize the simulation
        simulationManager.getInstance().initializeSimulation(10,10);
        // Start the simulation
        //simulationManager.getInstance().startSimulation();
        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame();
            frame.pack();
            frame.setVisible(true);
        });
    }
}
