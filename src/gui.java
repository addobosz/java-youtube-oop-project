import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui {

    private JButton clickMeButton;
    private JPanel panel1;
    public gui() {
        JPanel panel1 = new JPanel();

        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "bla bla");
            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
