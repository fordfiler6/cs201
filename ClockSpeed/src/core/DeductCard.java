package core;

import javax.swing.JOptionPane;

public class DeductCard extends Card
{
	protected int amount;
	public DeductCard(String name, String content, int amount)
	{
		super(name, content);
		this.amount = amount;
	}
	@Override
	public void takeAction(Player p) 
	{
		p.deductMoney(amount);
		JOptionPane.showMessageDialog(null, name + " - "+content);
	}

}
