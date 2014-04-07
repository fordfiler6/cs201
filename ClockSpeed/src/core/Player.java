package core;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Player 
{
	private int cashBalance;
	private Space currentLocation;
	private ImageIcon piece;
	private static ArrayList<ImageIcon> pieces = null;
	private int GPUs = 0;
	private ArrayList<Property> ownedProperties;
	
	public Player(Space location, Player[] players)
	{
		if(pieces ==null)
		{
			pieces = new ArrayList<ImageIcon>();
			pieces.add(new ImageIcon("icons/blb.png"));
			pieces.add(new ImageIcon("icons/doge.png"));
			pieces.add(new ImageIcon("icons/picard.png"));
			pieces.add(new ImageIcon("icons/skeptical.png"));
			pieces.add(new ImageIcon("icons/wonka.png"));
			pieces.add(new ImageIcon("icons/potoo.png"));
			pieces.add(new ImageIcon("icons/mallard.png"));
			pieces.add(new ImageIcon("icons/oag.png"));
			pieces.add(new ImageIcon("icons/ggg.png"));
			pieces.add(new ImageIcon("icons/penguin.png"));
		}
		
		
		currentLocation = location;
		ownedProperties = new ArrayList<Property>();
		cashBalance = 6500;
		GPUs=0;
		ImageIcon icon = null;
		int playerNum = 1;
		for(int i=0;i<players.length;i++)
		{
			if(players[i] == null)
			{
				playerNum = i+1;
				break;
			}
			else
			{
				players[i].getPiece();
			}
		}
		while(icon == null)
		{
			
			ImageIcon[] choices = new ImageIcon[pieces.size()];
			for(int i =0;i<pieces.size();i++)
			{
				choices[i] = pieces.get(i);
			}
			
		    icon = (ImageIcon) JOptionPane.showInputDialog(null, "Select a piece",
		        "Player "+playerNum, JOptionPane.QUESTION_MESSAGE, null, // Use
		                                                                        // default
		                                                                        // icon
		        choices, // Array of choices
		        choices[0]); // Initial choice
		}
		piece = icon;
		for(int i=0;i<pieces.size();i++)
		{
			if(pieces.get(i) == piece)
			{
				pieces.remove(i);
				break;
			}
		}
	}
	
	public Space getCurrentLocation() 
	{
		return currentLocation;
	}
	public void setCurrentLocation(Space currentLocation) 
	{
		this.currentLocation = currentLocation;
	}

	public int getBalance()
	{
		return cashBalance;
	}
	public ArrayList<Property> getOwnedProperties() 
	{
		return ownedProperties;
	}

	public void setOwnedProperties(ArrayList<Property> ownedProperties) 
	{
		this.ownedProperties = ownedProperties;
	}

	
	public boolean stillPlaying()
	{
		return cashBalance>0;
	}
	public ImageIcon getPiece() 
	{
		return piece;
	}

	public void setPiece(ImageIcon piece) 
	{
		this.piece = piece;
	}
	
	public void purchaseProperty(Property p)
	{
		ownedProperties.add(p);
		cashBalance -= p.getCost();
		if(p instanceof GPU)
		{
			GPUs++;
		}
	}
	public int getGPUs()
	{
		return GPUs;
	}
	public void lose()
	{
		cashBalance = 0;
		for(Property p : ownedProperties)
		{
			p.reset();
		}
		ownedProperties = new ArrayList<Property>();
	}

	
	public void addMoney(int amount)
	{
		cashBalance += amount;
	}
	public void deductMoney(int amount)
	{
		cashBalance -= amount;
	}
	
	public void payRent(int amount, Player p)
	{
		//System.out.println("Paying rent "+amount);
		cashBalance -= amount;
		p.addMoney(amount);
	}
}
