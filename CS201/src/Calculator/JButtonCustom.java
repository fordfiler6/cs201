package Calculator;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class JButtonCustom extends JButton implements MouseListener
{

	
	
		@Override
	    public void mouseClicked(MouseEvent e)
	    {

	    }

	    @Override
	    public void mousePressed(MouseEvent e)
	    {

	    }

	    @Override
	    public void mouseReleased(MouseEvent e)
	    {

	    }

	    @Override
	    public void mouseEntered(MouseEvent e)
	    {
	    	this.setBackground(Color.black);
	    }

	    @Override
	    public void mouseExited(MouseEvent e)
	    {

	    }
}
