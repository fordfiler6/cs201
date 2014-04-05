package gui;

import javax.swing.JFrame;

public class GameDisplay 
{
	//Constants
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 768;
	
	//Member Variables
	JFrame container;
	
	public GameDisplay()
	{
		container = new JFrame();
		container.setSize(WIDTH,HEIGHT);
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void play()
	{
		container.setVisible(true);
	}
}
