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
	public static void main(String[] args)
	{
		Client client = new Client();
		client.start();
	}
}
