package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


//Basic GUI from http://codereview.stackexchange.com/questions/25461/code-review-for-my-incredibly-simple-chat-room-swing-gui
public class ChatGUI
{

    String      appName     = "ClockSpeed Chat";
    ChatGUI     mainGUI;
    JFrame      newFrame    = new JFrame(appName);
    JButton     sendMessageOne;
    JButton     sendMessageAll;
    JTextField  messageBox;
    JTextArea   chatBox;
    Client cli;

    
    public ChatGUI(Client client)
    {
    	cli = client;
        try 
        {
            UIManager.setLookAndFeel(UIManager
                    .getSystemLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        display();
    }


    public void addMsg(String msg, String from)
    {
    	chatBox.append("Player " + from + " to all:  " + msg
                + "\n");
    }
    public void addMsgPrivate(String msg, String from, String to)
    {
    	chatBox.append("Player " + from + " to Player "+to+": " + msg
                + "\n");
    }


    public void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessageAll = new JButton("Send to All");
        sendMessageAll.addActionListener(new sendMessageAllButtonListener());
        
        sendMessageOne = new JButton("Send to One");
        sendMessageOne.addActionListener(new sendMessageOneButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessageAll, right);
        southPanel.add(sendMessageOne, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        newFrame.add(mainPanel);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(800, 500);
        newFrame.setVisible(true);
    }
    
    class sendMessageAllButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().length() < 1) 
            {
                // do nothing
            }
            else {
            	addMsg(messageBox.getText(), cli.clientId+"");
                cli.sendMessage(messageBox.getText());
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }
    
    class sendMessageOneButtonListener implements ActionListener {
    	String username = "username";
        public void actionPerformed(ActionEvent event) {
        	String[] choices = { "Player 1", "Player 2", "Player 3", "Player 4" };
		    String input = (String) JOptionPane.showInputDialog(null, "Send To",
		        "Select Recipient", JOptionPane.QUESTION_MESSAGE, null, // Use
		                                                                        // default
		                                                                        // icon
		        choices, // Array of choices
		        choices[0]); // Initial choice
		    if(input ==null)
		    	return;
            if (messageBox.getText().length() < 1) 
            {
                // do nothing
            }
            else {
                int to = Integer.parseInt(input.substring(6));
                addMsgPrivate(messageBox.getText(), cli.clientId+"", to+"");
                cli.sendMessageTo(messageBox.getText(), to);
                messageBox.setText("");
            }
            messageBox.requestFocusInWindow();
        }
    }

}
