package core;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

public class Virus extends Space
{
	private ArrayList<Card> cards;
	private int curCard = 0;
	public Virus(Player[] players)
	{
		super("Virus Scan");
		cards = new ArrayList<Card>();
		cards.add(new DeductCard("Adware: order shoes you never recieve","Pay $200", 200));
		cards.add(new CollectCard("Clean Bill of Health","Collect $500", 500));
		cards.add(new DistributedDeductCard("Trojan Horse","Pay $50 to each player", 50, players));
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
		}
		else
		{
			curCard++;
		}
		
		
	}

}
