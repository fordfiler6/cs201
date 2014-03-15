package Restaurant;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MainPanel extends JPanel
{
	Restaurant res;
	public MainPanel(Restaurant res) 
	{
		this.res = res;
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		drawTables(g);
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
				int radius = ((RoundTable) table).getRadius();
				
				g.fillOval(x, y, radius, radius);
			}
			else if(table instanceof SquareTable)
			{
				int sLen = ((SquareTable) table).getsLen();
				
				g.fillRect(x, y, sLen, sLen);
			}
			else if(table instanceof RectangleTable)
			{
				int width = ((RectangleTable) table).getWidth();
				int height = ((RectangleTable) table).getHeight();
				
				g.fillRect(x, y, width, height);
			}
		}
	}

	
	
}
