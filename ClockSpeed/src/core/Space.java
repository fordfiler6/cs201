package core;

import javax.swing.JPanel;

public abstract class Space 
{
	private String name;
	public abstract JPanel drawSpace();
	public abstract void landOnSpace(Player p);
	private int boardLocation;
	private int x,y;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Space(String name)
	{
		this.name = name;
	}
	public Space()
	{
		
	}
	public void setBoardLocation(int loc)
	{
		boardLocation = loc;
	}
	public int getBoardLocation()
	{
		return boardLocation;
	}
	
	public String getName()
	{
		return name;
	}

}
