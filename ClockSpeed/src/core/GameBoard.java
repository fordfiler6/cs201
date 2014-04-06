package core;

import gui.GameDisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameBoard extends JPanel
{
	public static final int NUM_SPACES = 10 * 4; //MUST BE DIVISIBLE BY 4 or board will be wierd
	public static final int SPACE_WIDTH = GameDisplay.WIDTH/(NUM_SPACES/4);
	public static final int SPACE_HEIGHT = GameDisplay.HEIGHT/(NUM_SPACES/4);
	private ArrayList<Space> boardSpaces;
	
	public GameBoard(String boardDescFile)
	{
		boardSpaces = new ArrayList<Space>();
		Scanner scan = new Scanner("File Not Found");
		try {
			scan = new Scanner(new File(boardDescFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan.useDelimiter(",|\\n");
		while(scan.hasNext())
		{
			String type = scan.next();
			Space temp = new Safe();
			if(type.equalsIgnoreCase("DIST"))
			{
				String name = scan.next();
				int amount = scan.nextInt();
				temp = new Distribution(name,amount);
			}
			else if(type.equalsIgnoreCase("CPU"))
			{
				String name = scan.next();
				int coreCount = Integer.parseInt(scan.next());
				int threadCount = Integer.parseInt(scan.next());
				int clockSpeed = Integer.parseInt(scan.next());
				int baseRent = Integer.parseInt(scan.next());
				int oc1 = Integer.parseInt(scan.next());
				int oc2 = Integer.parseInt(scan.next());
				int oc3 = Integer.parseInt(scan.next().trim());
				temp = new CPU(name,coreCount,threadCount,clockSpeed,baseRent,oc1,oc2,oc3);
			}
			else if(type.equalsIgnoreCase("GPU"))
			{
				String name = scan.next();
				int cost = scan.nextInt();
				temp = new GPU(name,cost);
			}
			else if(type.equalsIgnoreCase("MINE"))
			{
				temp = new Mine();
			}
			else if(type.equalsIgnoreCase("VIRUS"))
			{
				temp = new Virus();
			}
			else if(type.equalsIgnoreCase("SAFE"))
			{
				temp = new Safe();
			}
			boardSpaces.add(temp);
			scan.nextLine();
		}
	}
	@Override
	public void paint(Graphics g)
	{	
		String display = "<html><body style='width:"+(SPACE_WIDTH-20)+" px; padding: 5px; text-align:center;'>"
                + boardSpaces.get(0).getName();
	
		JLabel textLabel = new JLabel(display);
	    textLabel.setSize(textLabel.getPreferredSize());
	    BufferedImage bi = new BufferedImage(SPACE_WIDTH,SPACE_HEIGHT,BufferedImage.TYPE_INT_ARGB);
	    Graphics g2 = bi.createGraphics();
	  
	    textLabel.paint(g2);
	    g.drawImage(bi, GameDisplay.WIDTH_BOARD-SPACE_WIDTH, GameDisplay.HEIGHT_BOARD-SPACE_HEIGHT, null);
		
		
		//bottom
		g.drawLine(SPACE_WIDTH, GameDisplay.HEIGHT_BOARD-SPACE_HEIGHT, GameDisplay.WIDTH_BOARD-SPACE_WIDTH, GameDisplay.HEIGHT_BOARD-SPACE_HEIGHT);
		for(int i =1;i<NUM_SPACES/4;i++)
		{
			int width = SPACE_WIDTH*i-((i-1)*3);
			if(i==NUM_SPACES/4-1)
				width = width-2;
			g.drawLine(width, GameDisplay.HEIGHT_BOARD-SPACE_HEIGHT, width, GameDisplay.HEIGHT_BOARD+10);

			display = "<html><body style='width:"+(SPACE_WIDTH-20)+" px; padding: 5px; text-align:center;'>"
		                + boardSpaces.get(i).getName();
			if(boardSpaces.get(i) instanceof Property)
			{
				display += "<br>$"+((Property)boardSpaces.get(i)).getCost();
			}
			
			textLabel = new JLabel(display);
            textLabel.setSize(textLabel.getPreferredSize());
            bi = new BufferedImage(SPACE_WIDTH,SPACE_HEIGHT,BufferedImage.TYPE_INT_ARGB);
            g2 = bi.createGraphics();
          
            textLabel.paint(g2);
            g.drawImage(bi, GameDisplay.WIDTH-SPACE_WIDTH*(i+1)-(((NUM_SPACES/4)-i)*3), GameDisplay.HEIGHT_BOARD-SPACE_HEIGHT, null);


		}
		
		
		//TOP
		g.drawLine(SPACE_WIDTH, SPACE_HEIGHT, GameDisplay.WIDTH_BOARD-SPACE_WIDTH, SPACE_HEIGHT);
		for(int i =1;i<NUM_SPACES/4;i++)
		{
			int width = SPACE_WIDTH*i-((i-1)*3);
			if(i==NUM_SPACES/4-1)
				width = width-2;
			g.drawLine(width, 0, width, SPACE_HEIGHT);
		}
		
		//left
		g.drawLine(SPACE_WIDTH, GameDisplay.HEIGHT_BOARD-SPACE_HEIGHT, SPACE_WIDTH, SPACE_HEIGHT);
		for(int i =1;i<NUM_SPACES/4;i++)
		{
			int width = SPACE_HEIGHT*i-((i-1)*9);
			g.drawLine(0, width, SPACE_WIDTH, width);
		}
		
		//right
		g.drawLine(GameDisplay.WIDTH_BOARD-SPACE_WIDTH, GameDisplay.HEIGHT_BOARD-SPACE_HEIGHT, GameDisplay.WIDTH_BOARD-SPACE_WIDTH, SPACE_HEIGHT);
		for(int i =1;i<NUM_SPACES/4;i++)
		{
			int width = SPACE_HEIGHT*i-((i-1)*9);
			g.drawLine(GameDisplay.WIDTH_BOARD-SPACE_WIDTH, width, GameDisplay.WIDTH_BOARD, width);
		}
	
	}
}
