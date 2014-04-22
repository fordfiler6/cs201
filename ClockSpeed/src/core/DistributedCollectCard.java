package core;

import javax.swing.JOptionPane;

public class DistributedCollectCard extends CollectCard
{
	Player[] players;
	
	public DistributedCollectCard(String name, String content, int amount, Player[] players) 
	{
		super(name, content, amount);
		this.players = players;
	}

	public void takeAction(Player p) 
	{
		
		for(Player p2 : players)
		{
			if(p2.stillPlaying())
			{
				p2.deductMoney(amount);
				p.addMoney(amount);
			}
		}
		JOptionPane.showMessageDialog(null, name + " - "+content);
	}
}
