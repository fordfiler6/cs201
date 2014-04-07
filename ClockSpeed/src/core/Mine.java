package core;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class Mine extends Space
{
	private ArrayList<Card> cards;
	private int curCard = 0;
	public Mine(Player[] players)
	{
		super("Mining Site");
		cards = new ArrayList<Card>();
		cards.add(new CollectCard("Find a block","Collect $500", 500));
		cards.add(new DistributedCollectCard("Your pool found a block","Collect $50 in fees from each player", 50, players));
		Collections.shuffle(cards);
	}
	@Override
	public JPanel drawSpace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void landOnSpace(Player p) 
	{
		cards.get(curCard).takeAction(p);
		
		if(curCard == cards.size()-1)
		{
			curCard =0;
			Collections.shuffle(cards);
		}
		else
		{
			curCard++;
		}
		
	}
		
}
