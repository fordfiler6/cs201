package core;

import javax.swing.JPanel;

public abstract class Space 
{
	private String name;
	public abstract JPanel drawSpace();
	public abstract void landOnSpace(Player p);
	
	public Space(String name)
	{
		this.name = name;
	}
	public Space()
	{
		
	}
	
	public String getName()
	{
		return name;
	}
}
