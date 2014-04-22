package core;

import javax.swing.JOptionPane;

public class DistributedDeductCard extends CollectCard
{
	Player[] players;
	
	public DistributedDeductCard(String name, String content, int amount, Player[] players) 
	{
		super(name, content, amount);
		this.players = players;
	}

	public void takeAction(Player p) 
	{
		p.deductMoney(amount*players.length);
		for(Player p2 : players)
		{
			if(p2.stillPlaying())
			{
				p2.addMoney(amount);
				p.deductMoney(amount);
			}
		}
		JOptionPane.showMessageDialog(null, name + " - "+content);
	}
}
