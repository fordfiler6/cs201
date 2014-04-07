package gui;

import java.awt.Dimension;

import javax.swing.JFrame;

import core.GameBoard;

public class GameDisplay 
{
	//Constants
	public static final int WIDTH_BOARD = 1024;
	public static final int HORIZ_PADDING = 30;
	public static final int WIDTH = WIDTH_BOARD + HORIZ_PADDING;
	public static final int HEIGHT_BOARD = 768;
	public static final int VERT_PADDING = 75;
	public static final int HEIGHT = HEIGHT_BOARD + VERT_PADDING;
	
	//Member Variables
	JFrame container;
	GameBoard board;

	private int numPlayers;
	
	public GameDisplay(int input)
	{
		numPlayers = input;
		container = new JFrame();
		container.setSize(WIDTH,HEIGHT);
		container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board = new GameBoard("CPUMonopoly.csv", numPlayers);
		board.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		container.add(board);
	}
	
	public void play()
	{
		container.setVisible(true);
		board.play();
	}
}
