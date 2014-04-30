package program;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import chat.Client;
import chat.ListenServer;

public class StartChatClient 
{
	static JFrame clientWindow;
	static JPanel container;
	static JTextArea messageWindow;
	static JTextArea inputBox;
	public static void main(String[] args)
	{
		Client client = new Client();
		client.start();
		clientWindow = new JFrame();
		clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientWindow.setSize(500,550);
		container = new JPanel();
		container.setSize(500,500);
		clientWindow.add(container);
		
		messageWindow = new JTextArea();
		messageWindow.setEditable(false);
		messageWindow.setPreferredSize(new Dimension(450,300));
		container.add(new JScrollPane(messageWindow), BorderLayout.NORTH);
		
		
		JPanel sendContainer = new JPanel();
		sendContainer.setSize(450, 125);
		sendContainer.setLayout(new FlowLayout());

		inputBox = new JTextArea();
		inputBox.setPreferredSize(new Dimension(250,125));
		inputBox.setWrapStyleWord(true);
		sendContainer.add(new JScrollPane(inputBox));
		
		
		JButton sendAll = new JButton("Send To All");
		JButton sendOne = new JButton("Send To One");
		sendContainer.add(sendOne);
		sendContainer.add(sendAll);
		container.add(sendContainer, BorderLayout.SOUTH);
		

		clientWindow.setVisible(true);
		
		
	}
}
