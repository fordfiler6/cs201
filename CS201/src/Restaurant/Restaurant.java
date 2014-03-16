package Restaurant;

import java.util.ArrayList;

public class Restaurant 
{
	private Title title;
	private TableStatusList tableList;
	private ArrayList<Table> tables;
	private ArrayList<Wall> walls;
	private Podium podium;
	
	Restaurant ()
	{
		title = null;
		tableList = new TableStatusList();
		tables = new ArrayList<Table>();
		walls = new ArrayList<Wall>();
		podium = null;
	}
	
	public Object[][] getTableData()
	{
		Object[][] tableData = new Object[tables.size()][2];
		for(int i=0;i<tables.size();i++)
		{
			tableData[i][0] = tables.get(i).getNumber();
			tableData[i][1] = tables.get(i).getNumSeats();
		}
		
		return tableData;
	}
	
	public Title getTitle() {
		return title;
	}
	public void setTitle(Title title) {
		this.title = title;
	}
	public TableStatusList getTableList() {
		return tableList;
	}
	public void setTableList(TableStatusList tableList) {
		this.tableList = tableList;
	}
	public void addTable(Table table)
	{
		tables.add(table);
	}
	public ArrayList<Table> getTables()
	{
		return tables;
	}
	
	public void addWall(Wall wall)
	{
		walls.add(wall);
	}
	public ArrayList<Wall> getWalls()
	{
		return walls;
	}
	
	public void setPodium(Podium podium)
	{
		this.podium = podium;
	}
	
	public Podium getPodium()
	{
		return podium;
	}
}
