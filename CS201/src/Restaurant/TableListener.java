package Restaurant;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class TableListener extends MouseAdapter
{
	Restaurant res;
	GUI gui;
	TableListener(Restaurant res, GUI gui)
	{
		super();
		this.res = res;
		this.gui = gui;
	}
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		int mouseX = arg0.getPoint().x;
		int mouseY = arg0.getPoint().y;
		int tableId = -1;
		ArrayList<Table> tables = res.getTables();
		
		for(Table table : tables)
		{
			if(table instanceof RoundTable)
			{
				int tableCX = table.getLocation().getX() + ((RoundTable) table).getRadius()/2;
				int tableCY = table.getLocation().getY() + ((RoundTable) table).getRadius()/2;
				
				int xDiff = mouseX-tableCX;
				int yDiff = mouseY-tableCY;
				double sumSquares = Math.pow(xDiff, 2) + Math.pow(yDiff, 2);
				double dist = Math.sqrt(sumSquares);
				if(dist < ((RoundTable) table).getRadius()/2)
				{
					tableId = table.getNumber();
		
				}
			}
			else if(table instanceof SquareTable)
			{
				int tableXMin = table.getLocation().getX();
				int tableYMin = table.getLocation().getY();
				
				int tableXMax = table.getLocation().getX() + ((SquareTable) table).getsLen();
				int tableYMax = table.getLocation().getY() + ((SquareTable) table).getsLen();

				if(mouseX < tableXMax && mouseX > tableXMin)
				{
					if(mouseY < tableYMax && mouseY > tableYMin)
					{
						tableId = table.getNumber();
		
					}
				}
			}
			else if(table instanceof RectangleTable)
			{
				int tableXMin = table.getLocation().getX();
				int tableYMin = table.getLocation().getY();
				
				int tableXMax = table.getLocation().getX() + ((RectangleTable) table).getWidth();
				int tableYMax = table.getLocation().getY() + ((RectangleTable) table).getHeight();

				if(mouseX < tableXMax && mouseX > tableXMin)
				{
					if(mouseY < tableYMax && mouseY > tableYMin)
					{
						tableId = table.getNumber();

					}
				}
			}
			if(tableId !=-1)
			{
				if(table.isOccupied())
				{
					table.setOccupied(false);
				}
				else
				{
					String numOccupiedStr;
					Integer numOccupied;
					do
					{
						numOccupiedStr = JOptionPane.showInputDialog("How many members in this party? (Must be less than "+table.getNumSeats()+")");
						numOccupied = Utils.safeParseInt(numOccupiedStr);
						if(numOccupiedStr == null)
							break;
					}while (numOccupied == null || numOccupied > table.getNumSeats() || numOccupied <= 0);
					if(numOccupiedStr != null)
					{
						table.setOccupied(true);
						table.setNumSittingAtTable(numOccupied);
					}
				}
				break;
			}
		}
		
		
		
		gui.repaint();
		
	}


}
