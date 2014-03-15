package ffiler_midterm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeActionListener implements ActionListener
{
	private GUI gui;
	private boolean isWord;
	ChangeActionListener(GUI gui, boolean isWord)
	{
		this.gui = gui;
		this.isWord = isWord;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(isWord)
			gui.switchToSpreadSheet();
		else
			gui.switchToWord();
	}

}
