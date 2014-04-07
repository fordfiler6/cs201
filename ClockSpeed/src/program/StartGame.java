package program;

import javax.swing.JOptionPane;

import gui.*;

public class StartGame 
{
	public static void main(String[] args)
	{	
		String input = null;
		while(input == null)
		{
			String[] choices = { "2", "3", "4" };
		    input = (String) JOptionPane.showInputDialog(null, "How Many Players?",
		        "Number of Players", JOptionPane.QUESTION_MESSAGE, null, // Use
		                                                                        // default
		                                                                        // icon
		        choices, // Array of choices
		        choices[0]); // Initial choice
		}
		GameDisplay  game = new GameDisplay(Integer.parseInt(input));
		game.play();
	}
}
