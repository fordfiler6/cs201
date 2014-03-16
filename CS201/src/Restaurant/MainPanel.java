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
	public MainPanel(Restaurant res) 
	{
		this.res = res;
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
		g.drawString(title, res.getTitle().getLocation().getX(), res.getTitle().getLocation().getY());
		
	}
	public void drawTables(Graphics g)
	{
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
				g.drawString(table.getNumber()+"", x+radius/2 - TEXT_OFFSET_X, y+radius/2 - TEXT_OFFSET_Y);
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
				g.drawString(table.getNumber()+"", x+sLen/2 - TEXT_OFFSET_X, y+sLen/2 - TEXT_OFFSET_Y);

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
				g.drawString(table.getNumber()+"", x+width/2 - TEXT_OFFSET_X, y+height/2 - TEXT_OFFSET_Y);

			}
		}
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
