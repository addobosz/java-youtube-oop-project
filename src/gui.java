import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class gui {
    private JButton clickMeButton;
    private JPanel panel1;
    private JLabel img_label;
    private JLabel title;
    private JList listUsers;
    private JList listChannels;
    private JLabel listUserLabel;
    private JLabel listChannelsLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton saveButton;
    private JButton loadButton;
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

        searchButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String searchQuery = searchTextField.getText();
               if (searchQuery.isEmpty()) {
                   JOptionPane.showMessageDialog(null, "Please enter a search query!");
               } else {
                   EventQueue.invokeLater(() -> {
                       JFrame frame = new JFrame("Search results");
                       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                       try {
                           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set the look and feel to the default user's system look and feel
                       } catch (Exception exception) {
                           exception.printStackTrace();
                       }

                       JPanel panel = new JPanel();
                       panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                       panel.setOpaque(true);

                       for (UserAccount user : simulationManager.getInstance().search(searchQuery)) {
                           JLabel userLabel = new JLabel(user.getName());
                           JLabel joinDateLabel = new JLabel("Join date: " + user.getJoinDate());
                           JLabel premiumLabel = new JLabel("Premium: " + user.getPremium());
                           ImageIcon userThumbnail = new ImageIcon(ClassLoader.getSystemResource(user.getThumbnail()));
                           userLabel.setIcon(userThumbnail);
                           panel.add(userLabel);
                           panel.add(joinDateLabel);
                            panel.add(premiumLabel);
                       }

                       JScrollPane scroller = new JScrollPane(panel);
                       scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                       scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                       frame.getContentPane().add(BorderLayout.CENTER, scroller);
                       panel.setVisible(true);
                       frame.pack();
                       frame.setLocationByPlatform(true);
                       frame.setVisible(true);
                   });
               }
           }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationManager.saveState();
                System.out.println("\nState saved!\n");
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationManager.stopSimulation();
                JFrame oldFrame = (JFrame) SwingUtilities.getWindowAncestor(getPanel1());
                try {
                    // Wait for the simulation to stop
                    for (Thread thread : simulationManager.agentThreads) {
                        thread.join();
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                simulationManager.loadState();
                System.out.println("\nState loaded!\n");
                SwingUtilities.invokeLater(() -> {
                    Frame frame = new Frame(new gui(), false);
                    frame.pack();
                    frame.setVisible(true);
                });
                if (oldFrame != null) {
                    oldFrame.dispose();
                }
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
                    ImageIcon streamThumbnail = new ImageIcon(ClassLoader.getSystemResource("images/alert.gif"));
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
                textArea.append("Liked videos: " + user.getLikedVideos().size() + "\n");
                textArea.append("Queue size: " + user.getQueue().size() + "\n");
            }

            JScrollPane scroller = new JScrollPane(textArea);
            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            frame.getContentPane().add(BorderLayout.CENTER, scroller);

            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new FlowLayout());

            JButton videosButton = new JButton("Show videos");
            JButton streamsButton = new JButton("Show stream");
            JButton subscribersButton = new JButton("Show subscribers");
            JButton followingButton = new JButton("Show following");
            JButton likedVideosButton = new JButton("Show liked videos");

            videosButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Channel user = channels.get(userIndex);
                    EventQueue.invokeLater(() -> {
                        JFrame buttonFrame = new JFrame(user.getName()+ "'s videos");
                        ImageIcon userThumbnail = new ImageIcon(ClassLoader.getSystemResource(user.getThumbnail()));
                        buttonFrame.setIconImage(userThumbnail.getImage());
                        buttonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set the look and feel to the default user's system look and feel
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        JPanel panelButton = new JPanel();
                        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
                        panelButton.setOpaque(true);

                        for (Video video : user.getUploadedVideos()) {
                            JLabel videoLabel = new JLabel(video.getName());
                            JLabel descriptionLabel = new JLabel("Description: " + video.getDescription());
                            JLabel likesLabel = new JLabel("Likes: " + video.getNumberOfLikes());
                            JLabel viewsLabel = new JLabel("Views: " + video.getNumberOfViews());
                            JLabel durationLabel = new JLabel("Duration: " + video.getDuration() + " seconds");
                            JLabel dateLabel = new JLabel("Date: " + video.getUploadDate());
                            JLabel premiumLabel = new JLabel("Premium: " + video.getPremium());
                            JLabel authorLabel = new JLabel("Author: " + video.getAuthor().getName());

                            ImageIcon videoThumbnail = new ImageIcon(ClassLoader.getSystemResource(video.getThumbnail()));
                            videoLabel.setIcon(videoThumbnail);

                            panelButton.add(videoLabel);
                            panelButton.add(descriptionLabel);
                            panelButton.add(likesLabel);
                            panelButton.add(viewsLabel);
                            panelButton.add(durationLabel);
                            panelButton.add(dateLabel);
                            panelButton.add(premiumLabel);
                            panelButton.add(authorLabel);
                        }

                        JScrollPane scroller = new JScrollPane(panelButton);
                        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                        buttonFrame.getContentPane().add(BorderLayout.CENTER, scroller);
                        buttonFrame.pack();
                        buttonFrame.setLocationByPlatform(true);
                        buttonFrame.setVisible(true);
                        buttonFrame.setResizable(true);
                    });
                }
            });

            streamsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Channel user = channels.get(userIndex);
                    EventQueue.invokeLater(() -> {
                        JFrame buttonFrame = new JFrame(user.getName()+ "'s stream");
                        ImageIcon userThumbnail = new ImageIcon(ClassLoader.getSystemResource(user.getThumbnail()));
                        buttonFrame.setIconImage(userThumbnail.getImage());
                        buttonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set the look and feel to the default user's system look and feel
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        JPanel panelButton = new JPanel();
                        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
                        panelButton.setOpaque(true);

                        if (user.getStream() != null) {
                            Stream stream = user.getStream();
                            JLabel streamLabel = new JLabel(stream.getName());
                            JLabel descriptionLabel = new JLabel("Description: " + stream.getDescription());
                            JLabel likesLabel = new JLabel("Likes: " + stream.getNumberOfLikes());
                            JLabel viewsLabel = new JLabel("Number of viewers: " + stream.getNumberOfViewers());
                            JLabel dateLabel = new JLabel("Date: " + stream.getStartTime());
                            JLabel premiumLabel = new JLabel("Premium: " + stream.getNumberOfLikes());
                            JLabel authorLabel = new JLabel("Author: " + stream.getAuthor().getName());

                            ImageIcon streamThumbnail = new ImageIcon(ClassLoader.getSystemResource(stream.getThumbnail()));
                            streamLabel.setIcon(streamThumbnail);

                            panelButton.add(streamLabel);
                            panelButton.add(descriptionLabel);
                            panelButton.add(likesLabel);
                            panelButton.add(viewsLabel);
                            panelButton.add(dateLabel);
                            panelButton.add(premiumLabel);
                            panelButton.add(authorLabel);
                            JScrollPane scroller = new JScrollPane(panelButton);
                            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                            buttonFrame.getContentPane().add(BorderLayout.CENTER, scroller);
                            buttonFrame.pack();
                            buttonFrame.setLocationByPlatform(true);
                            buttonFrame.setVisible(true);
                            buttonFrame.setResizable(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "This channel is currently offline!");
                        }
                    });
                }
            });

            subscribersButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Channel user = channels.get(userIndex);
                    EventQueue.invokeLater(() -> {
                        JFrame buttonFrame = new JFrame(user.getName()+ "'s subscribers");
                        ImageIcon userThumbnail = new ImageIcon(ClassLoader.getSystemResource(user.getThumbnail()));
                        buttonFrame.setIconImage(userThumbnail.getImage());
                        buttonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set the look and feel to the default user's system look and feel
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        JPanel panelButton = new JPanel();
                        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
                        panelButton.setOpaque(true);
                        if (user.getFollowers().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "This channel has no subscribers!");
                        } else {
                            for (UserAccount subscriber : user.getFollowers()) {
                                JLabel subscriberLabel = new JLabel(subscriber.getName());
                                JLabel joinDateLabel = new JLabel("Join date: " + subscriber.getJoinDate());
                                JLabel premiumLabel = new JLabel("Premium: " + subscriber.getPremium());

                                ImageIcon subscriberThumbnail = new ImageIcon(ClassLoader.getSystemResource(subscriber.getThumbnail()));
                                subscriberLabel.setIcon(subscriberThumbnail);

                                panelButton.add(subscriberLabel);
                                panelButton.add(joinDateLabel);
                                panelButton.add(premiumLabel);
                            }
                            JScrollPane scroller = new JScrollPane(panelButton);
                            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                            buttonFrame.getContentPane().add(BorderLayout.CENTER, scroller);
                            buttonFrame.pack();
                            buttonFrame.setLocationByPlatform(true);
                            buttonFrame.setVisible(true);
                            buttonFrame.setResizable(true);
                        }
                    });
                }
            });

            likedVideosButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UserAccount user = users.get(userIndex);
                    EventQueue.invokeLater(() -> {
                        JFrame buttonFrame = new JFrame(user.getName()+ "'s liked videos");
                        ImageIcon userThumbnail = new ImageIcon(ClassLoader.getSystemResource(user.getThumbnail()));
                        buttonFrame.setIconImage(userThumbnail.getImage());
                        buttonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set the look and feel to the default user's system look and feel
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        JPanel panelButton = new JPanel();
                        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
                        panelButton.setOpaque(true);
                        if (user.getLikedVideos().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "This user has no liked videos!");
                        } else {
                            for (Video video : user.getLikedVideos()) {
                                JLabel videoLabel = new JLabel(video.getName());
                                JLabel descriptionLabel = new JLabel("Description: " + video.getDescription());
                                JLabel likesLabel = new JLabel("Likes: " + video.getNumberOfLikes());
                                JLabel viewsLabel = new JLabel("Views: " + video.getNumberOfViews());
                                JLabel durationLabel = new JLabel("Duration: " + video.getDuration() + " seconds");
                                JLabel dateLabel = new JLabel("Date: " + video.getUploadDate());
                                JLabel premiumLabel = new JLabel("Premium: " + video.getPremium());
                                JLabel authorLabel = new JLabel("Author: " + video.getAuthor().getName());

                                ImageIcon videoThumbnail = new ImageIcon(ClassLoader.getSystemResource(video.getThumbnail()));
                                videoLabel.setIcon(videoThumbnail);

                                panelButton.add(videoLabel);
                                panelButton.add(descriptionLabel);
                                panelButton.add(likesLabel);
                                panelButton.add(viewsLabel);
                                panelButton.add(durationLabel);
                                panelButton.add(dateLabel);
                                panelButton.add(premiumLabel);
                                panelButton.add(authorLabel);
                            }
                            JScrollPane scroller = new JScrollPane(panelButton);
                            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                            buttonFrame.getContentPane().add(BorderLayout.CENTER, scroller);
                            buttonFrame.pack();
                            buttonFrame.setLocationByPlatform(true);
                            buttonFrame.setVisible(true);
                            buttonFrame.setResizable(true);
                        }
                    });
                }
            });

            followingButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UserAccount user = users.get(userIndex);
                    EventQueue.invokeLater(() -> {
                        JFrame buttonFrame = new JFrame(user.getName()+ "'s following");
                        ImageIcon userThumbnail = new ImageIcon(ClassLoader.getSystemResource(user.getThumbnail()));
                        buttonFrame.setIconImage(userThumbnail.getImage());
                        buttonFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Set the look and feel to the default user's system look and feel
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        JPanel panelButton = new JPanel();
                        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
                        panelButton.setOpaque(true);
                        if (user.getFollowingChannels().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "This user is not following any channels!");
                        } else {
                            for (Channel channel : user.getFollowingChannels()) {
                                JLabel channelLabel = new JLabel(channel.getName());
                                JLabel subscriptionNumber = new JLabel("Subscriptions: " + channel.getFollowers().size());
                                JLabel joinDateLabel = new JLabel("Join date: " + channel.getJoinDate());
                                JLabel premiumLabel = new JLabel("Premium: " + channel.getPremium());

                                ImageIcon channelThumbnail = new ImageIcon(ClassLoader.getSystemResource(channel.getThumbnail()));
                                channelLabel.setIcon(channelThumbnail);

                                panelButton.add(channelLabel);
                                panelButton.add(subscriptionNumber);
                                panelButton.add(joinDateLabel);
                                panelButton.add(premiumLabel);
                            }
                            JScrollPane scroller = new JScrollPane(panelButton);
                            scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                            buttonFrame.getContentPane().add(BorderLayout.CENTER, scroller);
                            buttonFrame.pack();
                            buttonFrame.setLocationByPlatform(true);
                            buttonFrame.setVisible(true);
                            buttonFrame.setResizable(true);
                        }
                    });
                }
            });

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

            panel.add(optionsPanel);
            frame.getContentPane().add(BorderLayout.CENTER, panel);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);
            frame.setResizable(true);
        });

    }

    public JPanel getPanel1() {
        return panel1;
    }
}
