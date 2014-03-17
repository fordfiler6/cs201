package Restaurant;

import java.util.ArrayList;

public class TableStatusList
{
	ArrayList<TableColumn> columns;
	int numRows;
	FontStyle font;
	int rowHeight;
	public static boolean showOccupied = false;
	public static boolean showFree = false;
	
	TableStatusList()
	{
		columns = new ArrayList<TableColumn>();
		font = null;
	}
	
	public void addColumn(TableColumn col)
	{
		columns.add(col);
	}
	
	public String[] getColumnNames()
	{
		String[] names = new String[columns.size()];
		for(int i=0;i<columns.size();i++)
		{
			names[i] = columns.get(i).getName();
		}
		
		return names;
	}
	public Location getLocation()
	{
		Location loc = new Location(columns.get(0).getLocation().getX(),columns.get(0).getLocation().getY());
		return loc;
	}
	public int getWidth()
	{
		return columns.size()*(columns.get(1).getLocation().getX() - columns.get(0).getLocation().getX());
	}
}
