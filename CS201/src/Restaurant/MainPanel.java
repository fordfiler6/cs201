package Restaurant;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MainPanel extends JPanel
{
	Restaurant res;
	final int TEXT_OFFSET_X = 3;
	final int TEXT_OFFSET_Y = -4;
	final int CHAIR_SIZE = 10;
	public MainPanel(Restaurant res, GUI gui) 
	{
		this.res = res;
		if(res!=null)
		{
			this.addMouseListener(new TableListener(res, gui));
		}
	}
	
	public void paint(Graphics g)
	{
		if(res != null)
		{
			super.paint(g);
			drawTitle(g);
			drawTables(g);
			drawWalls(g);
			drawPodium(g);
		}
	}
	public void drawTitle(Graphics g)
	{
		String title = res.getTitle().getName();
		Font font = new Font("Times", Font.BOLD, 18);
		g.setFont(font);
		g.drawString(title, res.getTitle().getLocation().getX(), res.getTitle().getLocation().getY()+18);
		
	}
	public void drawTables(Graphics g)
	{
		Font font = new Font("Times", Font.PLAIN, 12);
		g.setFont(font);
		ArrayList<Table> tables = res.getTables();
		for(Table table : tables)
		{
			int x = table.getLocation().getX();
			int y = table.getLocation().getY();
		
			if(table instanceof RoundTable)
			{
				if(table.isOccupied())
				{
					g.setColor(Color.red);
				}
				else
				{
					g.setColor(Color.green);
				}
				int radius = ((RoundTable) table).getRadius();
				g.fillOval(x, y, radius, radius);
				
				g.setColor(Color.black);
				g.drawString(table.getNumber()+"", x+radius/2 - TEXT_OFFSET_X*((table.getNumber()/10)+1), y+radius/2 - TEXT_OFFSET_Y);
				drawRoundSeats(g, (RoundTable) table);
			}
			else if(table instanceof SquareTable)
			{
				if(table.isOccupied())
				{
					g.setColor(Color.red);
				}
				else
				{
					g.setColor(Color.green);
				}
				int sLen = ((SquareTable) table).getsLen();
				
				g.fillRect(x, y, sLen, sLen);
				g.setColor(Color.black);
				g.drawString(table.getNumber()+"", x+sLen/2 - TEXT_OFFSET_X*((table.getNumber()/10)+1), y+sLen/2 - TEXT_OFFSET_Y);
				
				drawSquareSeats(g,(SquareTable)table);
			}
			else if(table instanceof RectangleTable)
			{
				if(table.isOccupied())
				{
					g.setColor(Color.red);
				}
				else
				{
					g.setColor(Color.green);
				}
				int width = ((RectangleTable) table).getWidth();
				int height = ((RectangleTable) table).getHeight();
				
				g.fillRect(x, y, width, height);
				g.setColor(Color.black);
				g.drawString(table.getNumber()+"", x+width/2 - TEXT_OFFSET_X*((table.getNumber()/10)+1), y+height/2 - TEXT_OFFSET_Y);
				
				drawRectangleSeats(g, (RectangleTable) table);
			}

		}
	}
	
	public void drawRoundSeats(Graphics g, RoundTable t)
	{
		int numSeats = t.getNumSeats();
		int radiusB = t.getRadius() + CHAIR_SIZE;
		int radiusS = t.getRadius() - CHAIR_SIZE;
		int occupiedSeats = t.getNumSittingAtTable();
		g.setColor(Color.red);
		for (int j = 0; j < numSeats; j++)
		{
			if(j>=occupiedSeats)
				g.setColor(Color.black);
		    int x = (int)(t.getLocation().getX()+(radiusS/2)+ Math.sin(j*(2*Math.PI / numSeats)) * (radiusB)/2);
		    int y = (int) (t.getLocation().getY() +(radiusS/2)+ Math.cos(j*(2*Math.PI / numSeats)) * (radiusB)/2);
		    g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
		}
	}
	
	public void drawSquareSeats(Graphics g, SquareTable t)
	{
		int numSeats = t.getNumSeats();
		int sideL = t.getsLen();
		int sideX = t.getLocation().getX();
		int sideY = t.getLocation().getY();
		int x =0;
		int y =0;
		int adjust=0;
		int occupiedSeats = t.getNumSittingAtTable();
		g.setColor(Color.red);
		for (int j = 0; j < numSeats; j++)
		{
			if(j>=occupiedSeats)
				g.setColor(Color.black);
			switch (j%4)
			{
				case 0: 
					adjust = (int) (sideL/(Math.ceil(numSeats/4.0)+1));
					x = sideX - CHAIR_SIZE -1;
					y = sideY + ((j/4)+1)*adjust - (int)(CHAIR_SIZE/2);
					break;
				case 1: 
					adjust = (int) (sideL/(Math.ceil(numSeats/4.0)+1));
					x = sideX + sideL +1;
					y = sideY + ((j/4)+1)*adjust - (int)(CHAIR_SIZE/2);
					break;
				case 2: 
					adjust = (int) (sideL/(Math.ceil(numSeats/4.0)+1));
					x = sideX + ((j/4)+1)*adjust - (int)(CHAIR_SIZE/2);
					y = sideY - CHAIR_SIZE-1;
					break;
				case 3: 
					adjust = (int) (sideL/(Math.ceil(numSeats/4.0)+1));
					x = sideX + ((j/4)+1)*adjust - (int)(CHAIR_SIZE/2);
					y = sideY + sideL + 1;
					break;
			}

		    g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
		}
	}
	
	public void drawRectangleSeats(Graphics g, RectangleTable t)
	{
		int numSeats = t.getNumSeats();
		int height = t.getHeight();
		int width = t.getWidth();
		int sideX = t.getLocation().getX();
		int sideY = t.getLocation().getY();
		boolean horizontal = (double)height/(double)width <=1 ? true : false;
		int x =0;
		int y =0;
		int adjust=0;
		int occupiedSeats = t.getNumSittingAtTable();
		g.setColor(Color.red);
		if(numSeats >=1)
		{
			if(1>occupiedSeats)
				g.setColor(Color.black);
			if(horizontal)
			{
				adjust = (int) (height/(Math.ceil(4.0/4.0)+1)); 
			}
			else
			{
				adjust = (int) (height/(Math.ceil((numSeats-2)/2.0)+1));
			}
			x = sideX - CHAIR_SIZE -1;
			y = sideY + adjust - (int)(CHAIR_SIZE/2);
			g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
		}
		if(numSeats >=2)
		{
			if(2>occupiedSeats)
				g.setColor(Color.black);
			if(horizontal)
			{
				adjust = (int) (height/(Math.ceil(4.0/4.0)+1)); 
			}
			else
			{
				adjust = (int) (height/(Math.ceil((numSeats-2)/2.0)+1));
			}
			x = sideX + width +1;
			y = sideY + adjust - (int)(CHAIR_SIZE/2);
			g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
		}
		if(numSeats >=3)
		{
			if(3>occupiedSeats)
				g.setColor(Color.black);
			if(!horizontal)
			{
				adjust = (int) (width/(Math.ceil(4.0/4.0)+1)); 
			}
			else
			{
				adjust = (int) (width/(Math.ceil((numSeats-2)/2.0)+1));
			}
			x = sideX + adjust - (int)(CHAIR_SIZE/2);
			y = sideY - CHAIR_SIZE-1;
			g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
		}
		if(numSeats >=4)
		{
			
			if(4>occupiedSeats)
				g.setColor(Color.black);
			if(!horizontal)
			{
				adjust = (int) (width/(Math.ceil(4.0/4.0)+1));
			}
			else
			{
				adjust = (int) (width/(Math.ceil((numSeats-2)/2.0)+1));
			}
			
			x = sideX + adjust - (int)(CHAIR_SIZE/2);
			y = sideY + height + 1;
			g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
		}
		for (int j = 4; j < numSeats; j++)
		{
			if(j>=occupiedSeats)
				g.setColor(Color.black);
			int oldJ = (j-4)*2 + 4;
			switch (j%2)
			{
				case 0: 
					if(!horizontal)
					{
						adjust = (int) (height/(Math.ceil((numSeats-2)/2.0)+1)); 
						x = sideX - CHAIR_SIZE -1;
						y = sideY + ((oldJ/4)+1)*adjust - (int)(CHAIR_SIZE/2);
					}
					else
					{
						adjust = (int) (width/(Math.ceil((numSeats-2)/2.0)+1));
						x = sideX + ((oldJ/4)+1)*adjust - (int)(CHAIR_SIZE/2);
						y = sideY - CHAIR_SIZE-1;
						
					}
					g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
					break;
				case 1: 
					if(!horizontal)
					{
						adjust = (int) (height/(Math.ceil((numSeats-2)/2.0)+1)); 
						x = sideX + width +1;
						y = sideY + ((oldJ/4)+1)*adjust - (int)(CHAIR_SIZE/2);
					}
					else
					{
						adjust = (int) (width/(Math.ceil((numSeats-2)/2.0)+1));
						x = sideX + ((oldJ/4)+1)*adjust - (int)(CHAIR_SIZE/2);
						y = sideY + height+1;
						
					}
					g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
					break;
			}
		}
		/*	if(horizontal && (j ==0 || j == 1))
			{
				g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
			}
			else if(horizontal && j%4 != 0 && j%4 != 2)
			{
				g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
			}
			else if(!horizontal && (j ==2 || j ==3))
			{
				g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
			}
			else if(!horizontal && j%4 != 2 && j%4 != 3)
			{
				g.fillRect(x,y,CHAIR_SIZE,CHAIR_SIZE);
			}
		*/
	}
	
	public void drawWalls(Graphics g)
	{
		ArrayList<Wall> walls = res.getWalls();
		g.setColor(Color.black);
		for(Wall wall : walls)
		{
			int startX = wall.getStart().getX();
			int startY = wall.getStart().getY();
			
			int endX = wall.getEnd().getX();
			int endY = wall.getEnd().getY();
			
			g.drawLine(startX, startY, endX, endY);
		}
	}
	
	public void drawPodium(Graphics g)
	{
		Podium podium = res.getPodium();
		g.setColor(Color.cyan);
		
		int x = podium.getLocation().getX();
		int y = podium.getLocation().getY();
		int width = podium.getWidth();
		int height = podium.getHeight();
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.black);
		g.drawString("P", x+width/2 - TEXT_OFFSET_X, y+height/2 - TEXT_OFFSET_Y);

		
	}

	
	
}
