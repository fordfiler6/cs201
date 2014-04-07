package core;

public abstract class Card 
{
	protected String name;
	protected String content;
	public abstract void takeAction(Player p);
	
	public Card(String name, String content)
	{
		this.name = name;
		this.content = content;
	}
}
