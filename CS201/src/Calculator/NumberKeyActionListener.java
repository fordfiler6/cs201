package Calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

public class NumberKeyActionListener implements ActionListener
{
	String buttonText;
	JLabel equationLabel;
	JLabel inputLabel;
	public static boolean clear = false;
	
	NumberKeyActionListener()
	{
		buttonText = "";
		equationLabel = null;
	
	}
	
	NumberKeyActionListener(String button, JLabel input, JLabel equation)
	{
		buttonText = button;
		equationLabel = equation;
		inputLabel = input;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(FunctionKeyActionListener.error){}
		else
		{
			if(FunctionKeyActionListener.lastPressed == GUI.NROOT)
			{
				double root = (1.0/Double.parseDouble(buttonText));
				FunctionKeyActionListener.simpleEquation = FunctionKeyActionListener.simpleEquation + root;
			}
			if(clear)
			{
				inputLabel.setText(buttonText);
				clear = false;
			}

			else
			{
				String labelVal = inputLabel.getText();
				if(labelVal == "0")
				{
					inputLabel.setText(buttonText);
					
				}
				else
					inputLabel.setText(labelVal+buttonText);
			}
			
		}
	}

}
