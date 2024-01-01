import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class gui {
    private JButton clickMeButton;
    private JPanel panel1;
    private JLabel img_label;
    private JLabel title;
    private JList listUsers;
    private JList listChannels;
    private JLabel listUserLabel;
    private JLabel listChannelsLabel;
    private DefaultListModel listUsersModel;
    private DefaultListModel listChannelsModel;
    private ArrayList<UserAccount> users;
    private ArrayList<Channel> channels;

    public gui() {
        JPanel panel1 = new JPanel();
        clickMeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "bla bla");
            }
        });

        users = new ArrayList<UserAccount>();
        listUsersModel = new DefaultListModel<>();
        listUsers.setModel(listUsersModel);

        channels = new ArrayList<Channel>();
        listChannelsModel = new DefaultListModel<>();
        listChannels.setModel(listChannelsModel);

        listUsers.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    createFrame();
                }
            }
        });

        listChannels.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
    }

    public void refreshUsersList() {
        listUsersModel.removeAllElements();
        for (UserAccount user : users) {
            System.out.println("adding user to list "+user.getName());
            listUsersModel.addElement(user.getName());
        }
    }

    public void refreshChannelsList() {
        listChannelsModel.removeAllElements();
        for (Channel channel : channels) {
            System.out.println("adding channel to list "+channel.getName());
            listChannelsModel.addElement(channel.getName());
        }
    }

    public void addUserToList(UserAccount user) {
        users.add(user);
        refreshUsersList();
    }

    public void addChannelToList(Channel channel) {
        channels.add(channel);
        refreshChannelsList();
    }

    public JPanel getPanel1() {
        return panel1;
    }
//    private boolean isFrameOpen() {
//        java.awt.Frame[] frames = JFrame.getFrames();
//        for (java.awt.Frame frame : frames) {
//            if (frame.getTitle().equals("Test")) {
//                return true;
//            }
//        }
//        return false;
//    }
    public static void createFrame()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                JTextArea textArea = new JTextArea(15, 50);
                textArea.setWrapStyleWord(true);
                textArea.setEditable(false);
                textArea.setFont(Font.getFont(Font.SANS_SERIF));
                JScrollPane scroller = new JScrollPane(textArea);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new FlowLayout());
                JTextField input = new JTextField(20);
                JButton button = new JButton("Enter");
                DefaultCaret caret = (DefaultCaret) textArea.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                panel.add(scroller);
                inputpanel.add(input);
                inputpanel.add(button);
                panel.add(inputpanel);
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
                input.requestFocus();
            }
        });
    }
}