package Calculator;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;

import javax.swing.BorderFactory;
import javax.swing.JButton;


public class JButtonCustomLight extends JButton implements MouseListener
{
	private boolean mouseOver = false;
	private boolean mouseDown = false;
	public JButtonCustomLight()
	{
		addMouseListener(this);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(true);
		float[] textColor = Color.RGBtoHSB(30, 57, 91,null);
		float[] borderColor = Color.RGBtoHSB(135, 151, 170,null);
		setBorder(BorderFactory.createLineBorder(Color.getHSBColor(borderColor[0], borderColor[1], borderColor[2])));
		setForeground(Color.getHSBColor(textColor[0], textColor[1], textColor[2]));
		repaint();
	 	
	}
	
	 protected void paintComponent(Graphics g)
	    {
		 	float[] color1;
		 	float[] color2;
	        if(mouseOver)
	        {
	        	if(mouseDown)
	        	{
	        		color1 = Color.RGBtoHSB(244, 203, 131,null);
				 	color2 = Color.RGBtoHSB(255, 228, 98,null);
	        	}
	        	else
	        	{
				 	color1 = Color.RGBtoHSB(255, 240, 223,null);
				 	color2 = Color.RGBtoHSB(255, 228, 121,null);
	        	}
	        }
	        else
	        {
	        	color1 = Color.RGBtoHSB(247, 251, 255,null);
			 	color2 = Color.RGBtoHSB(242, 251, 255,null);
	        }
				 Graphics2D g2 = (Graphics2D)g.create();
		         g2.setPaint(new GradientPaint(
		                 new Point(0, 0), 
		                 Color.getHSBColor(color1[0],color1[1],color1[2]),
		                 new Point(0, getHeight()), 
		                 Color.getHSBColor(color2[0],color2[1],color2[2])));
		         g2.fillRect(0, 0, getWidth(), getHeight());
		         g2.dispose();
		
		         super.paintComponent(g);
	        
        
	  
	    }


		@Override
	    public void mouseClicked(MouseEvent e)
	    {
			
	    }

	    @Override
	    public void mousePressed(MouseEvent e)
	    {
	    	float[] borderColor = Color.RGBtoHSB(227, 193, 133,null);
			setBorder(BorderFactory.createLineBorder(Color.getHSBColor(borderColor[0], borderColor[1], borderColor[2])));
	    	mouseDown = true;
	    	repaint();
	    }

	    @Override
	    public void mouseReleased(MouseEvent e)
	    {
	    	float[] borderColor = Color.RGBtoHSB(255, 219, 0,null);
			setBorder(BorderFactory.createLineBorder(Color.getHSBColor(borderColor[0], borderColor[1], borderColor[2])));
	    	mouseDown = false;
	    	repaint();
	    }

	    @Override
	    public void mouseEntered(MouseEvent e)
	    {
	    	float[] borderColor = Color.RGBtoHSB(255, 219, 0,null);
			setBorder(BorderFactory.createLineBorder(Color.getHSBColor(borderColor[0], borderColor[1], borderColor[2])));
	    	mouseOver = true;
	    	repaint();
	    }

	    @Override
	    public void mouseExited(MouseEvent e)
	    {
	    	float[] borderColor = Color.RGBtoHSB(135, 151, 170,null);
			setBorder(BorderFactory.createLineBorder(Color.getHSBColor(borderColor[0], borderColor[1], borderColor[2])));
	    	mouseOver = false;
	    	repaint();
	    }
}
