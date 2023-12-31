import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//public class gui {
//
//    private JButton clickMeButton;
//    private JPanel panel1;
//    private JLabel img_label;
//    private JLabel title;
//    private JList list1;
//
//    public gui() {
//        JPanel panel1 = new JPanel();
//        String
//        clickMeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, "bla bla");
//            }
//        });
//    }
//
//    public JPanel getPanel1() {
//        return panel1;
//    }
//}
public class gui {

    private JButton clickMeButton;
    private JPanel panel1;
    private JLabel img_label;
    private JLabel title;
    private JList listUsers;
    private DefaultListModel listUsersModel;
    private ArrayList<UserAccount> users;

    public gui() {
        JPanel panel1 = new JPanel();
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "bla bla");
            }
        });

        // Create a sample list of Channel objects
        users = new ArrayList<UserAccount>();
        listUsersModel = new DefaultListModel<>();
        listUsers.setModel(listUsersModel);

        users.add(simulationManager.getInstance().getAllChannels().get(0));
        users.add(simulationManager.getInstance().getAllChannels().get(1));
        users.add(simulationManager.getInstance().getAllChannels().get(2));

        listUsers.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });

        for (UserAccount user : new ArrayList<>(users)) {
            System.out.println("adding channel to list "+user.getName());
            this.addUserToList(user);
        }
        panel1.add(listUsers);
    }

    public void refreshUsersList() {
        listUsersModel.removeAllElements();
        for (UserAccount user : users) {
            System.out.println("adding channel to list "+user.getName());
            listUsersModel.addElement(user.getName());
        }
    }

    public void addUserToList(UserAccount user) {
        users.add(user);
        refreshUsersList();
    }
    public JPanel getPanel1() {
        return panel1;
    }


}
