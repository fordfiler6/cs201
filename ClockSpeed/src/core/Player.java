package core;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Player 
{
	private int cashBalance;
	private Space currentLocation;
	private ImageIcon piece;
	private int pieceId;
	private static ArrayList<ImageIcon> pieces = null;
	private int GPUs = 0;
	private ArrayList<Property> ownedProperties;
	
	public Player(Space location, Player[] players, int clientId)
	{
		if(pieces ==null)
		{
			pieces = new ArrayList<ImageIcon>();
			pieces.add(new ImageIcon("resources/blb.png"));
			pieces.add(new ImageIcon("resources/doge.png"));
			pieces.add(new ImageIcon("resources/picard.png"));
			pieces.add(new ImageIcon("resources/skeptical.png"));
			pieces.add(new ImageIcon("resources/wonka.png"));
			pieces.add(new ImageIcon("resources/potoo.png"));
			pieces.add(new ImageIcon("resources/mallard.png"));
			pieces.add(new ImageIcon("resources/oag.png"));
			pieces.add(new ImageIcon("resources/ggg.png"));
			pieces.add(new ImageIcon("resources/penguin.png"));
		}
		
		
		currentLocation = location;
		ownedProperties = new ArrayList<Property>();
		cashBalance = 6500;
		GPUs=0;
		ImageIcon icon = null;
		int playerNum = clientId;
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
				pieceId = i;
				break;
			}
		}
	}
	
	public Player(Space location, Player[] players, int clientId, int id)
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
		int playerNum = clientId;

		ImageIcon[] choices = new ImageIcon[pieces.size()];
		for(int i =0;i<pieces.size();i++)
		{
			choices[i] = pieces.get(i);
		}
		piece = choices[id];
	}
	
	public int getIconId()
	{
		return pieceId;
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
