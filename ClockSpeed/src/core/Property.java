package core;

public abstract class Property extends Space
{
	private Player owner;
	private int cost;
	public abstract int calculateRent();
	
	public Property(String name, int cost)
	{
		super(name);
		this.cost = cost;
	}
	
	public int getCost()
	{
		return cost;
	}
}
