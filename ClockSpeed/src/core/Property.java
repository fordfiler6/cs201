package core;

public abstract class Property extends Space
{
	protected Player owner;
	protected int cost;
	public abstract int calculateRent();
	public abstract String getUpgradeString();
	public abstract void reset();

	public Property(String name, int cost)
	{
		super(name);
		this.cost = cost;
		owner = null;
	}
	
	public int getCost()
	{
		return cost;
	}
	

}
