import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Initialize the simulation
        simulationManager.getInstance().initializeSimulation(10,10);
        // Start the simulation
        simulationManager.getInstance().startSimulation();

        // Display the GUI
        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame();
            frame.pack();
            frame.setVisible(true);
        });
    }
}
