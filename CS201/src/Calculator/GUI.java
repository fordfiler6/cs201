package Calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
	private static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int SCREEN_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	private static final int APP_WIDTH = (int) (SCREEN_WIDTH * (422.0/1920.0));
	private static final int APP_HEIGHT = (int) (SCREEN_HEIGHT * (324.0/1080.0));
	private static final int X_PADDING = (int) (SCREEN_WIDTH * (10.0/1920.0));
	private static final int Y_PADDING = (int) (SCREEN_HEIGHT * (10.0/1080.0));
	private static final int INNER_PANEL_WIDTH = APP_WIDTH-(X_PADDING*3);
	private static final int TEXT_PANEL_HEIGHT = APP_HEIGHT/5;
	private static final int INPUT_TEXT_HEIGHT = (int)(TEXT_PANEL_HEIGHT*(2.0/3.0));
	private static final int EQUATION_HEIGHT = TEXT_PANEL_HEIGHT - INPUT_TEXT_HEIGHT;
	private static final int BUTTON_WIDTH = (int) (SCREEN_WIDTH * (35.0/1920.0));
	private static final int BUTTON_HEIGHT = (int) (SCREEN_HEIGHT * (27.0/1080.0));
	private static final int BUTTON_MARGIN = (int) (SCREEN_WIDTH * (6.0/1920.0))/2;
	
	private JLabel equationDisplay;
	private JLabel textInput;
	
	private static final int CONTROL_PANEL_HEIGHT = APP_HEIGHT-(Y_PADDING*3)-TEXT_PANEL_HEIGHT;

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
		main.add(generateControlPanel());
		
		
		return main;
	}
	
	private CalculatorButton[][] generateButtonArray()
	{
		CalculatorButton[][] buttons = 
			{
				{INV,LN,LP,RP,BKSP,CE,C,SIGN,SQRT},
				{SINH,SINH,XSQ,FACT,N7,N8,N9,FWDS,PERC },
				{COSH,COS,EXP,NROOT,N4,N5,N6,MULT,OVERX},
				{TANH,TAN,CUBE,ROOT3,N1,N2,N3,SUB,EQ},
				{PI,MOD,LOG,TENX,N0,null,DEC,PLUS,null}
			};
		return buttons;
	}
	
	private JPanel generateTextDisplay()
	{
		JPanel textDisplay = new JPanel();
		textDisplay.setSize(INNER_PANEL_WIDTH, TEXT_PANEL_HEIGHT);
		textDisplay.setLocation(X_PADDING, Y_PADDING);
		textDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
		
		textDisplay.setLayout(new BorderLayout());
		
		textInput = new JLabel();
		textInput.setSize(INNER_PANEL_WIDTH, INPUT_TEXT_HEIGHT);
		textInput.setHorizontalAlignment(SwingConstants.RIGHT);
		textInput.setFont(new Font(textInput.getFont().getName(), Font.PLAIN, INPUT_TEXT_HEIGHT));
		
		textDisplay.add(textInput, BorderLayout.PAGE_END);
		textInput.setText("Inputs");
	
		equationDisplay = new JLabel();
		equationDisplay.setSize(INNER_PANEL_WIDTH, EQUATION_HEIGHT);
		equationDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
		
		equationDisplay.setFont(new Font(equationDisplay.getFont().getName(), Font.PLAIN, EQUATION_HEIGHT));

		equationDisplay.setText("Equation");
		textDisplay.add(equationDisplay, BorderLayout.PAGE_START);
		
		
		return textDisplay;
	}
	
	private JPanel generateControlPanel()
	{
		JPanel controlPanel = new JPanel();
		controlPanel.setSize(INNER_PANEL_WIDTH,CONTROL_PANEL_HEIGHT);
		controlPanel.setLocation(X_PADDING, Y_PADDING+TEXT_PANEL_HEIGHT);
		controlPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		CalculatorButton[][] buttons = generateButtonArray();
		JButton toAdd;
		
		for(int k = 0;k<buttons[0].length;k++)
		{
			for(int i =0;i<buttons.length;i++)
			{
				if(buttons[i][k] != null)
				{
					if(isNum(buttons[i][k]))
					{
						toAdd = new JButtonCustomLight();
						toAdd.addActionListener(new NumberKeyActionListener(buttons[i][k].display,textInput, equationDisplay));
					}
					else
					{
						toAdd = new JButtonCustomDark();
						toAdd.addActionListener(new FunctionKeyActionListener(buttons[i][k],textInput, equationDisplay));
					}
				
					if(buttons[i][k] == EQ)
					{
						c.gridheight = 2;
						toAdd.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT*2+BUTTON_MARGIN*2));
					}
					else if(buttons[i][k] == N0)
					{
						c.gridwidth = 2;
						toAdd.setPreferredSize(new Dimension(BUTTON_WIDTH*2+BUTTON_MARGIN*2,BUTTON_HEIGHT));
					}
					else
					{
						c.gridheight = 1;
						c.gridwidth = 1;
						toAdd.setPreferredSize(new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT));
					}
					c.gridx = k;
					c.gridy = i;
					
					c.insets = new Insets(BUTTON_MARGIN,BUTTON_MARGIN,BUTTON_MARGIN,BUTTON_MARGIN);
	
					toAdd.setMargin(new Insets(0,0,0,0));
					toAdd.setText(buttons[i][k].display);
					controlPanel.add(toAdd,c);
				}
			}
		}
		
		return controlPanel;
		
	}
	
	private boolean isNum(CalculatorButton test)
	{
		if(test == N0
			|| test == N1
			|| test == N2
			|| test == N3
			|| test == N4
			|| test == N5
			|| test == N6
			|| test == N7
			|| test == N8
			|| test == N9)
		{
			return true;
		}
		return false;
			
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
	
	
	//Button Defs
	
	public static CalculatorButton INV = new CalculatorButton("Inv");
	public static CalculatorButton LN = new CalculatorButton("ln");
	public static CalculatorButton RP = new CalculatorButton("(");
	public static CalculatorButton LP = new CalculatorButton(")");
	public static CalculatorButton BKSP = new CalculatorButton("\u2190");
	public static CalculatorButton CE = new CalculatorButton("CE");
	public static CalculatorButton C = new CalculatorButton("C");
	public static CalculatorButton SIGN = new CalculatorButton("\u00b1");
	public static CalculatorButton SQRT = new CalculatorButton("\u221a");
	public static CalculatorButton SINH = new CalculatorButton("sinh");
	public static CalculatorButton SIN = new CalculatorButton("sin");
	public static CalculatorButton XSQ = new CalculatorButton("x\u00B2");
	public static CalculatorButton FACT = new CalculatorButton("n!");
	public static CalculatorButton N7 = new CalculatorButton("7");
	public static CalculatorButton N8 = new CalculatorButton("8");
	public static CalculatorButton N9 = new CalculatorButton("9");
	public static CalculatorButton FWDS = new CalculatorButton("/");
	public static CalculatorButton PERC = new CalculatorButton("%");
	public static CalculatorButton COSH = new CalculatorButton("cosh");
	public static CalculatorButton COS = new CalculatorButton("cos");
	public static CalculatorButton EXP = new CalculatorButton("");
	public static CalculatorButton NROOT = new CalculatorButton("");
	public static CalculatorButton N4 = new CalculatorButton("4");
	public static CalculatorButton N5 = new CalculatorButton("5");
	public static CalculatorButton N6 = new CalculatorButton("6");
	public static CalculatorButton MULT = new CalculatorButton("*");
	public static CalculatorButton OVERX = new CalculatorButton("1/x");
	public static CalculatorButton TANH = new CalculatorButton("tanh");
	public static CalculatorButton TAN = new CalculatorButton("tan");
	public static CalculatorButton CUBE = new CalculatorButton("x\u00B3");
	public static CalculatorButton ROOT3 = new CalculatorButton("");
	public static CalculatorButton N1 = new CalculatorButton("1");
	public static CalculatorButton N2 = new CalculatorButton("2");
	public static CalculatorButton N3 = new CalculatorButton("3");
	public static CalculatorButton SUB = new CalculatorButton("-");
	public static CalculatorButton EQ = new CalculatorButton("=");
	public static CalculatorButton PI = new CalculatorButton("\u03C0");
	public static CalculatorButton MOD = new CalculatorButton("Mod");
	public static CalculatorButton LOG = new CalculatorButton("log");
	public static CalculatorButton TENX = new CalculatorButton("");
	public static CalculatorButton N0 = new CalculatorButton("0");
	public static CalculatorButton DEC = new CalculatorButton(".");
	public static CalculatorButton PLUS = new CalculatorButton("+");

}
