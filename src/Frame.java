import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    public Frame(gui existingGui, boolean displaySetupDialog) {
        setTitle("Good Tube App");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/icon.png"));
        setIconImage(icon.getImage());
        getContentPane().setBackground(Color.BLUE);

        setContentPane(existingGui.getPanel1());

        if (displaySetupDialog) {
            displaySetupDialog(); // if the user wants to load a simulation from a file, don't display the setup dialog
        }

        for (UserAccount user : simulationManager.getInstance().getAllUsers()) {
            existingGui.addUserToList(user);
        }
        for (Channel channel : simulationManager.getInstance().getAllChannels()) {
            existingGui.addChannelToList(channel);
        }
    }

    private void displaySetupDialog() {
        // Create a dialog for setting up the simulation
        JDialog setupDialog = new JDialog(this, "Simulation Setup", true);
        setupDialog.setLayout(new FlowLayout());
        setupDialog.setSize(300, 150);

        JTextField usersField = new JTextField(10);
        JTextField channelsField = new JTextField(10);
        JButton createButton = new JButton("Create");

        setupDialog.add(new JLabel("Number of Users:"));
        setupDialog.add(usersField);
        setupDialog.add(new JLabel("Number of Channels:"));
        setupDialog.add(channelsField);
        setupDialog.add(createButton);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numberOfUsers = Integer.parseInt(usersField.getText());
                    int numberOfChannels = Integer.parseInt(channelsField.getText());

                    // Initialize the simulation with user-provided values
                    simulationManager.initializeSimulation(numberOfUsers, numberOfChannels);

                    // Start the simulation
                    simulationManager.startSimulation();

                    // Close the setup dialog
                    setupDialog.dispose();

                    // Create and show the main frame
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(setupDialog, "Invalid input. Please enter valid numbers.");
                }
            }
        });

        // Center the dialog on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - setupDialog.getWidth()) / 2;
        int y = (screenSize.height - setupDialog.getHeight()) / 2;
        setupDialog.setLocation(x, y);

        // Make the dialog visible
        setupDialog.setVisible(true);
    }
}
