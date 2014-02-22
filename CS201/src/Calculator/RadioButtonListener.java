package Calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RadioButtonListener implements ActionListener
{
	public static boolean radians = false;
	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getActionCommand() == "radians")
		{
			radians = true;
		}
		else
		{
			radians = false;
		}
	}

}
