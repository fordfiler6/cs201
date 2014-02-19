package Calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI extends JFrame 
{
	private static final int APP_WIDTH = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * (515.0/1920.0));;
	private static final int APP_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()* (391.0/1080.0));
	private static final int X_PADDING = 10;
	private static final int Y_PADDING = 20;
	private static final int INNER_PANEL_WIDTH = APP_WIDTH-(X_PADDING*3);
	private static final int TEXT_PANEL_HEIGHT = 150;
	private static final int INPUT_TEXT_HEIGHT = 100;
	private static final int EQUATION_HEIGHT = TEXT_PANEL_HEIGHT - INPUT_TEXT_HEIGHT;

	JLabel debug;
	GUI()
	{
		Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(APP_WIDTH,APP_HEIGHT);
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
		main.setLayout(null);
		

		
		
		
		main.add(generateTextDisplay());
		
		
		return main;
	}
	private JPanel generateTextDisplay()
	{
		JPanel textDisplay = new JPanel();
		textDisplay.setSize(INNER_PANEL_WIDTH, TEXT_PANEL_HEIGHT);
		textDisplay.setLocation(X_PADDING, Y_PADDING);
		textDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
		
		textDisplay.setLayout(new BorderLayout());
		
		JLabel textInput = new JLabel();
		textInput.setSize(INNER_PANEL_WIDTH, INPUT_TEXT_HEIGHT);
		textInput.setHorizontalAlignment(SwingConstants.RIGHT);
		textInput.setFont(new Font(textInput.getFont().getName(), Font.PLAIN, INPUT_TEXT_HEIGHT));
		
		textDisplay.add(textInput, BorderLayout.PAGE_END);
		textInput.setText("Inputs");
	
		JLabel equationDisplay = new JLabel();
		equationDisplay.setSize(INNER_PANEL_WIDTH, EQUATION_HEIGHT);
		equationDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
		
		equationDisplay.setFont(new Font(equationDisplay.getFont().getName(), Font.PLAIN, EQUATION_HEIGHT));

		equationDisplay.setText("Equation");
		textDisplay.add(equationDisplay, BorderLayout.PAGE_START);
		
		
		return textDisplay;
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
