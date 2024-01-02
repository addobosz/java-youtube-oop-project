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
                JOptionPane.showMessageDialog(null, "The author of this program is Adam Dobosz");
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
                if (!e.getValueIsAdjusting()) { // Prevents double window on selection
                    int selectedUserIndex = listUsers.getSelectedIndex();
                    createFrame(selectedUserIndex, false);
                }
            }
        });

        listChannels.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Prevents double window on selection
                    int selectedChannelIndex = listChannels.getSelectedIndex();
                    createFrame(selectedChannelIndex, true);
                }
            }
        });
    }

    public void refreshUsersList() {
        listUsersModel.removeAllElements();
        for (UserAccount user : users) {
            listUsersModel.addElement(user.getName());
        }
    }

    public void refreshChannelsList() {
        listChannelsModel.removeAllElements();
        for (Channel channel : channels) {
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

    public void createFrame(int userIndex, boolean isChannel) { // method to create a popup window with user/channel data
        String title;
        if (isChannel) {
            title = "Channel data";
        } else {
            title = "User data";
        }
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            try
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set the look and feel to the default user's system look and feel
            } catch (Exception e) {
                e.printStackTrace();
            }

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setOpaque(true);

            JTextArea textArea = new JTextArea(7, 50);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            Font textAreaFont = new Font("Arial", Font.PLAIN, 16); // Font(name, style, size)
            textArea.setFont(textAreaFont);

            if (isChannel) {
                Channel user = channels.get(userIndex);
                textArea.append("Username: " + user.getName() + "\n");

                if (user.getStream() != null) {
                    textArea.append("Currently Streaming: " + user.getStream().getName() + "\n");
                    ImageIcon streamThumbnail = new ImageIcon(ClassLoader.getSystemResource(user.getStream().getThumbnail()));
                    JLabel imageLabel = new JLabel(streamThumbnail);
                    panel.add(imageLabel);
                }
                textArea.append("Join date: " + user.getJoinDate() + "\n");
                textArea.append("Premium: " + user.getPremium() + "\n");
                textArea.append("Subscriptions: " + user.getFollowers().size() + "\n");
                textArea.append("Number of uploaded videos: " + user.getUploadedVideos().size() + "\n");

                ImageIcon thumbnail = new ImageIcon(ClassLoader.getSystemResource(user.getThumbnail()));
                JLabel imageLabel = new JLabel(thumbnail);
                frame.setIconImage(thumbnail.getImage());
                panel.add(imageLabel);
            } else {
                UserAccount user = users.get(userIndex);
                ImageIcon thumbnail = new ImageIcon(ClassLoader.getSystemResource(user.getThumbnail()));
                JLabel imageLabel = new JLabel(thumbnail);
                panel.add(imageLabel);
                frame.setIconImage(thumbnail.getImage());
                textArea.append("Username: " + user.getName() + "\n");
                if (user.getCurrentlyViewed() != null) {
                    textArea.append("Currently Viewed: " + user.getCurrentlyViewed().getName() + " by " + user.getCurrentlyViewed().getAuthor().getName() + "\n");
                }
                textArea.append("Join date: " + user.getJoinDate() + "\n");
                textArea.append("Premium: " + user.getPremium() + "\n");
                textArea.append("Followed channels: " + user.getFollowingChannels().size() + "\n");

                textArea.append("Queue size: " + user.getQueue().size() + "\n");
            }

            JScrollPane scroller = new JScrollPane(textArea);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new FlowLayout());
            JButton videosButton = new JButton("Show videos");
            JButton streamsButton = new JButton("Show stream");
            JButton subscribersButton = new JButton("Show subscribers");
            JButton followingButton = new JButton("Show following");
            JButton likedVideosButton = new JButton("Show liked videos");

            JPanel inputpanel = new JPanel();
            inputpanel.setLayout(new FlowLayout());
            JTextField input = new JTextField(20);
            JButton button = new JButton("Enter");
            DefaultCaret caret = (DefaultCaret) textArea.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

            panel.add(scroller);
            if (isChannel) {
                optionsPanel.add(videosButton);
                optionsPanel.add(streamsButton);
                optionsPanel.add(subscribersButton);
            } else {
                optionsPanel.add(followingButton);
                optionsPanel.add(likedVideosButton);
            }

            inputpanel.add(input);
            inputpanel.add(button);
            panel.add(optionsPanel);
            panel.add(inputpanel);

            frame.getContentPane().add(BorderLayout.CENTER, panel);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
            frame.setResizable(true);
            input.requestFocus();
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
