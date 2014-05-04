package program;

import javax.swing.JOptionPane;

import chat.Client;
import chat.ListenServer;
import gui.*;

public class StartGame 
{
	public static void main(String[] args)
	{	
		String input = null;
		
		/*while(input == null)
		{
			String[] choices = { "2", "3", "4" };
		    input = (String) JOptionPane.showInputDialog(null, "How Many Players?",
		        "Number of Players", JOptionPane.QUESTION_MESSAGE, null, // Use
		                                                                        // default
		                                                                        // icon
		        choices, // Array of choices
		        choices[0]); // Initial choice
		}*/
		Client client = new Client();
		client.start();
		while(!client.getStartGame())
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int numPlayers = client.getNumPlayers();
		GameDisplay  game = new GameDisplay(numPlayers, client.getClientId(), client);
		client.setGameBoard(game.getGameBoard());
		game.play();
	}
}
