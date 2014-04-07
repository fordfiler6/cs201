package core;

import javax.swing.JOptionPane;

public class CollectCard extends Card
{
	protected int amount;
	public CollectCard(String name, String content, int amount)
	{
		super(name, content);
		this.amount = amount;
	}
	@Override
	public void takeAction(Player p) 
	{
		p.addMoney(amount);
		JOptionPane.showMessageDialog(null, name + " - "+content);
	}

}
