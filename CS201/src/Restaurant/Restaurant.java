package Restaurant;

import java.util.ArrayList;

public class Restaurant 
{
	Title title;
	TableStatusList tableList;
	ArrayList<Table> tables;
	Restaurant ()
	{
		title = null;
		tableList = new TableStatusList();
		tables = new ArrayList<Table>();
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
	
}
