package Calculator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GUI extends JFrame 
{
	JLabel debug;
	GUI()
	{
		setSize(515,391);
		setResizable(false);
		JMenuBar menu = generateMenuBar();
		this.setJMenuBar(menu);
		this.setVisible(true);
		
		
		JPanel parentContainer = generateMainPanel();
		debug = new JLabel("debug");
	
		parentContainer.add(debug);
		
		this.add(parentContainer);
		
	}
	private void setDebugText(String debugMessage)
	{
		debug.setText(debugMessage);
	}
	private JPanel generateMainPanel()
	{
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		return main;
	}
	private JMenuBar generateMenuBar()
	{
		JMenuBar menu = new JMenuBar();
		JMenu edit = new JMenu("Edit");
		JMenuItem copy = new JMenuItem("Copy");
		copy.setAccelerator(KeyStroke.getKeyStroke(
							java.awt.event.KeyEvent.VK_C, 
							java.awt.Event.CTRL_MASK));
		copy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) 
			{
				setDebugText("Copy");
			}
			
		});
		
		JMenuItem paste = new JMenuItem("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke(
							java.awt.event.KeyEvent.VK_V, 
							java.awt.Event.CTRL_MASK));
		paste.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) 
			{
				setDebugText("Paste");
			}
			
		});
		
		edit.add(copy);
		edit.add(paste);
		
		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) 
			{
				setDebugText("Help");
			}
		});
		
		help.add(about);
		
		menu.add(edit);
		menu.add(help);
		
		menu.setSize(menu.getPreferredSize());
		return menu;
	}
}
