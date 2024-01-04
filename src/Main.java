import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Initialize the simulation
        simulationManager.initializeSimulation(1,10);

        // Start the simulation
        simulationManager.startSimulation();

        //Display the GUI
        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame();
            frame.pack();
            frame.setVisible(true);
        });
    }
}
