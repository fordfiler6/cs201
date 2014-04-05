package core;

import java.util.ArrayList;

public class Player 
{
	private int cashBalance;
	private ArrayList<Property> ownedProperties;
	private Space currentLocation;
	
	
	public void addMoney(int amount)
	{
		cashBalance += amount;
	}
}
