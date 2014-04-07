package core;

import javax.swing.JPanel;

public class GPU extends Property 
{
	GPU(String name, int cost)
	{
		super(name, cost);
	}

	@Override
	public int calculateRent() {
		return 50*owner.getGPUs();
	}
	
	public String getUpgradeString()
	{
		switch(owner.getGPUs())
		{
		case 1: return "1";
		case 2: return "2";
		case 3: return "3";
		case 4: return "4";
		default: return "0";
		}
	}

	@Override
	public JPanel drawSpace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void landOnSpace(Player p) 
	{
		if(owner == null)
		{
			if(p.getBalance() > cost)
			{
				owner = p;
				p.purchaseProperty(this);
			}
			else
			{
				p.lose();
			}
		}
		else
		{
			if(p.getBalance() >calculateRent())
			{
				p.payRent(calculateRent(), owner);
			}
			else
			{
				p.payRent(p.getBalance(), owner);
			}
		}
		
	}

	@Override
	public void reset() 
	{
		owner = null;
	}

}
