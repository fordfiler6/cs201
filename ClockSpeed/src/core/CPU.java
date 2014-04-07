package core;

import javax.swing.JPanel;

public class CPU extends Property
{
	public static final int BASERENT_COST_FACTOR = 15;
	private int cores, clockSpeed, threads, baseRent, oc1Rent, oc2Rent, oc3Rent, level;
	
	private static final double UPGRADE_FACTOR = 0.5;

	public CPU(String _name, int _coreCount, int _threadCount, int _clockSpeed, int _baseRent,int _oc1, int _oc2,int _oc3)
	{
		super(_name, _baseRent*BASERENT_COST_FACTOR);
		cores = _coreCount;
		threads = _threadCount;
		baseRent = _baseRent;
		oc1Rent = _oc1;
		oc2Rent = _oc2;
		oc3Rent = _oc3;
		level = 0;
		clockSpeed = _clockSpeed;
		
	}
	
	@Override
	public int calculateRent() {
		switch(level)
		{
			case 0: return baseRent;
			case 1: return oc1Rent;
			case 2: return oc2Rent;
			case 3: return oc3Rent;
		
		}
		return 0;
	}

	public String getUpgradeString()
	{
		switch(level)
		{
		case 0: return "base";
		case 1: return "oc1";
		case 2: return "oc2";
		case 3: return "oc3";
		default: return "base";
		}
	}
	
	public int getUpgradeCost()
	{
		return (int) (cost*UPGRADE_FACTOR);
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
	
	public boolean canUpgrade(Player p)
	{
		if(p.getBalance() > getUpgradeCost())
		{
			if(level < 4)
				return true;
		}
		return false;
	}
	
	public void upgrade(Player p)
	{
		level++;
		p.deductMoney((int)(getUpgradeCost()));
	}
	
	public void reset()
	{
		owner = null;
		level = 0;
		
	}

}
