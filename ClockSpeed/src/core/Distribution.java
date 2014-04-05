package core;

import javax.swing.JPanel;

public class Distribution extends Space
{

	private int amount;
	
	
	public Distribution(String name, int amount)
	{
		super(name);
		this.amount = amount;
	}
	@Override
	public JPanel drawSpace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void landOnSpace(Player p) {
		p.addMoney(amount);
		
	}

}
