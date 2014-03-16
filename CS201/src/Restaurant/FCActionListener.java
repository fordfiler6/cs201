package Restaurant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FCActionListener implements ActionListener
{
	GUI parentFrame;
	FCActionListener(GUI parentFrame)
	{
		this.parentFrame = parentFrame;
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		JFileChooser fc = new JFileChooser();
		int fcReturn = fc.showOpenDialog(parentFrame.getParentFrame());
		if(fcReturn == JFileChooser.APPROVE_OPTION)
		{
			parentFrame.readNewFile(fc.getSelectedFile().getAbsolutePath());
			parentFrame.repaint();
		}
		
		
	}
}
