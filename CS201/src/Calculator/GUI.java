package Calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class GUI extends JFrame implements KeyListener
{
	private static final int SCREEN_WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private static final int SCREEN_HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	private static final int APP_WIDTH = (int) (SCREEN_WIDTH * (489.0/1920.0));
	private static final int APP_HEIGHT = (int) (SCREEN_HEIGHT * (400.0/1080.0));
	private static final int X_PADDING = (int) (SCREEN_WIDTH * (10.0/1920.0));
	private static final int Y_PADDING = (int) (SCREEN_HEIGHT * (10.0/1080.0));
	private static final int INNER_PANEL_WIDTH = APP_WIDTH-(X_PADDING*3);
	private static final int TEXT_PANEL_HEIGHT = APP_HEIGHT/6;
	private static final int INPUT_TEXT_HEIGHT = (int)(TEXT_PANEL_HEIGHT*(1.5/3.0));
	private static final int EQUATION_HEIGHT = (int)(TEXT_PANEL_HEIGHT*(.9/3.0));
	private static final int BUTTON_WIDTH = (int) (SCREEN_WIDTH * (45.0/1920.0));
	private static final int BUTTON_HEIGHT = (int) (SCREEN_HEIGHT * (35.0/1080.0));
	private static final int BUTTON_MARGIN = (int) (SCREEN_WIDTH * (7.0/1920.0))/2;
	private static final int RADIO_PANEL_WIDTH = (int) (SCREEN_WIDTH * (150.0/1920.0));
	private static final int RADIO_PANEL_HEIGHT = (int) (SCREEN_HEIGHT * (32.0/1080.0));
	private static final int CONTROL_PANEL_HEIGHT = APP_HEIGHT-(Y_PADDING*8)-TEXT_PANEL_HEIGHT-RADIO_PANEL_HEIGHT;
	
	
	private static JFrame aboutWindow;
	
	
	private float[] bgcolor = Color.RGBtoHSB(221, 232, 243, null);
	private float[] bg2color = Color.RGBtoHSB(239, 244, 252, null);
	private float[] bordercolor = Color.RGBtoHSB(142, 156, 173, null);
	
	private JPanel nonInv;
	private JPanel inv;
	
	private JLabel equationDisplay;
	private JLabel textInput;
	
	public ArrayList<JButton> keyLinkedButtons = new ArrayList<JButton>();

	JLabel debug;
	private String copiedText ="";
	GUI()
	{
		Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize(APP_WIDTH,APP_HEIGHT);
		setResizable(false);
		JMenuBar menu = generateMenuBar();
		this.setJMenuBar(menu);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setTitle("Calculator");
		
		JPanel parentContainer = generateMainPanel();
		

		parentContainer.setBackground(Color.getHSBColor(bgcolor[0], bgcolor[1],bgcolor[2]));
		
		this.add(parentContainer);
		this.addKeyListener(this);
		
		for(int i=0;i<keyLinkedButtons.size();i++)
		{
			if(keyLinkedButtons.get(i).getText().charAt(0) == 'C')
			{
				//System.out.println("Button clicked = " +)
				keyLinkedButtons.get(i).doClick();
			}
			if(keyLinkedButtons.get(i).getText().charAt(0) == '%')
			{

				keyLinkedButtons.get(i).setEnabled(false);
			}
		}
		
	}

	private JPanel generateMainPanel()
	{
		JPanel main = new JPanel();
		main.setLayout(null);
		

		main.add(generateTextDisplay());
		nonInv = generateControlPanel(false);
		inv = generateControlPanel(true);
		inv.setVisible(false);
		inv.setBackground(Color.getHSBColor(bgcolor[0], bgcolor[1],bgcolor[2]));
		nonInv.setBackground(Color.getHSBColor(bgcolor[0], bgcolor[1],bgcolor[2]));
		main.add(nonInv);
		main.add(inv);
		
		
		
		main.add(generateAngleTypePanel());
		
		
		return main;
	}
	
	private CalculatorButton[][] generateButtonArray()
	{
		CalculatorButton[][] buttons = 
			{
				{INV,LN,LP,RP,BKSP,CE,C,SIGN,SQRT},
				{SINH,SIN,XSQ,FACT,N7,N8,N9,FWDS,PERC },
				{COSH,COS,EXP,NROOT,N4,N5,N6,MULT,OVERX},
				{TANH,TAN,CUBE,ROOT3,N1,N2,N3,SUB,EQ},
				{PI,MOD,LOG,TENX,N0,null,DEC,PLUS,null}
			};
		return buttons;
	}
	private CalculatorButton[][] generateInvButtonArray()
	{
		CalculatorButton[][] buttons = 
			{
				{INV,EX,LP,RP,BKSP,CE,C,SIGN,SQRT},
				{INVSH,INVS,XSQ,FACT,N7,N8,N9,FWDS,PERC },
				{INVCH,INVC,EXP,NROOT,N4,N5,N6,MULT,OVERX},
				{INVTH,INVT,CUBE,ROOT3,N1,N2,N3,SUB,EQ},
				{TWOPI,MOD,LOG,TENX,N0,null,DEC,PLUS,null}
			};
		return buttons;
	}
	
	private JPanel generateTextDisplay()
	{
		JPanel textDisplay = new JPanel();
		textDisplay.addKeyListener(this);
		textDisplay.setSize(INNER_PANEL_WIDTH, TEXT_PANEL_HEIGHT);
		textDisplay.setLocation(X_PADDING, Y_PADDING);
		textDisplay.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(bordercolor[0], bordercolor[1], bordercolor[1])));
		
		textDisplay.setLayout(new BorderLayout());
		
		textInput = new JLabel();
		textInput.addKeyListener(this);
		textInput.setSize(INNER_PANEL_WIDTH-X_PADDING, INPUT_TEXT_HEIGHT);
		textInput.setHorizontalAlignment(SwingConstants.RIGHT);
		textInput.setFont(new Font(textInput.getFont().getName(), Font.PLAIN, INPUT_TEXT_HEIGHT));
		
		textDisplay.add(textInput, BorderLayout.PAGE_END);
		textInput.setText("0");
	
		equationDisplay = new JLabel();
		equationDisplay.addKeyListener(this);
		equationDisplay.setSize(INNER_PANEL_WIDTH-X_PADDING, EQUATION_HEIGHT);
		equationDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
		
		equationDisplay.setFont(new Font(equationDisplay.getFont().getName(), Font.PLAIN, EQUATION_HEIGHT));

		equationDisplay.setText("");
		textDisplay.add(equationDisplay, BorderLayout.PAGE_START);
		
		equationDisplay.setBackground(Color.getHSBColor(bg2color[0], bg2color[1],bg2color[2]));
		textInput.setBackground(Color.getHSBColor(bg2color[0], bg2color[1],bg2color[2]));
		
		textDisplay.setBackground(Color.getHSBColor(bg2color[0], bg2color[1],bg2color[2]));
		
		
		return textDisplay;
	}
	
	private JPanel generateAngleTypePanel()
	{
		JRadioButton degrees = new JRadioButton("Degrees");
		degrees.setActionCommand("degrees");
		degrees.setMargin(new Insets(0,0,0,0));
		degrees.setSelected(true);
		degrees.addActionListener(new RadioButtonListener());
		
		degrees.addKeyListener(this);
		
		JRadioButton radians = new JRadioButton("Radians");
		radians.setActionCommand("radians");
		radians.setMargin(new Insets(0,0,0,0));
		radians.addActionListener(new RadioButtonListener());
		
		
		radians.addKeyListener(this);
		
		JPanel angleTypePanel = new JPanel();
		angleTypePanel.setLayout(new FlowLayout());
		angleTypePanel.setLocation(X_PADDING, Y_PADDING*2+TEXT_PANEL_HEIGHT);
		angleTypePanel.setSize(RADIO_PANEL_WIDTH,RADIO_PANEL_HEIGHT);
		angleTypePanel.setBorder(BorderFactory.createLineBorder(Color.getHSBColor(bordercolor[0], bordercolor[1], bordercolor[1])));

		ButtonGroup buttons = new ButtonGroup();
		buttons.add(degrees);
		buttons.add(radians);
		
		angleTypePanel.add(degrees);
		angleTypePanel.add(radians);
		
		
		degrees.setBackground(Color.getHSBColor(bgcolor[0], bgcolor[1],bgcolor[2]));
		radians.setBackground(Color.getHSBColor(bgcolor[0], bgcolor[1],bgcolor[2]));
		angleTypePanel.setBackground(Color.getHSBColor(bgcolor[0], bgcolor[1],bgcolor[2]));
		
		return angleTypePanel;
		
	}
	
	private JPanel generateControlPanel(boolean inv)
	{
		JPanel controlPanel = new JPanel();
		controlPanel.setSize(INNER_PANEL_WIDTH,CONTROL_PANEL_HEIGHT);
		controlPanel.setLocation(X_PADDING, (Y_PADDING*2)+TEXT_PANEL_HEIGHT+RADIO_PANEL_HEIGHT);
		controlPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		CalculatorButton[][] buttons = null;
		if(inv)
		{
			buttons = generateInvButtonArray();
		}
		else
		{
			buttons = generateButtonArray();
		}
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
					else if(buttons[i][k] == PERC)
					{
						toAdd = new GrayedButton();
					}
					else
					{
						toAdd = new JButtonCustomDark();
						toAdd.addActionListener(new FunctionKeyActionListener(buttons[i][k],textInput, equationDisplay, this));
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
					if(buttons[i][k] != NROOT)
					{
						toAdd.setText(buttons[i][k].display);
					}
					else
					{
						toAdd.setIcon(new ImageIcon("yroot.png"));
					}
					if(toAdd.getText().length() >0)
						keyLinkedButtons.add(toAdd);
					toAdd.addKeyListener(this);
					controlPanel.add(toAdd,c);
				}
			}
		}

		return controlPanel;
		
	}
	
	public void toInverse()
	{
		if(nonInv.isVisible())
		{
			nonInv.setVisible(false);
			inv.setVisible(true);
		}
		else
		{
			nonInv.setVisible(true);
			inv.setVisible(false);
		}
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
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection cbContents = new StringSelection(textInput.getText());
				copiedText  = textInput.getText();
				clipboard.setContents(cbContents, null);
			}
			
		});
		
		JMenuItem paste = new JMenuItem("Paste");
		paste.setAccelerator(KeyStroke.getKeyStroke(
							java.awt.event.KeyEvent.VK_V, 
							java.awt.Event.CTRL_MASK));
		paste.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) 
			{
				textInput.setText(copiedText);
			}
			
		});
		
		edit.add(copy);
		edit.add(paste);
		
		JMenu help = new JMenu("Help");
		JMenuItem about = new JMenuItem("About Calculator");

		aboutWindow = new JFrame();
		
		aboutWindow.setSize(500,500);
		BufferedImage me = null;
		 try {                
	          me = ImageIO.read(new File("me.jpg"));
	       } catch (IOException ex) {
	            System.out.println("Image could not be loaded");
	       }
		final BufferedImage meFinal = me;
		aboutWindow.getContentPane().add(new JPanel(){
			protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawImage(meFinal, 75, 0, null); // see javadoc for more info on the parameters            
		    }
		});
		
		JPanel info = new JPanel();
		info.add(new JLabel("Ford Filer - CS201 - Spring 2014"));

		
		aboutWindow.add(info,BorderLayout.SOUTH);
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) 
			{
				aboutWindow.setVisible(true);
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
	public static CalculatorButton EXP = new CalculatorButton("<html>x<sup>y</sup></html>");
	public static CalculatorButton NROOT = new CalculatorButton("");
	public static CalculatorButton N4 = new CalculatorButton("4");
	public static CalculatorButton N5 = new CalculatorButton("5");
	public static CalculatorButton N6 = new CalculatorButton("6");
	public static CalculatorButton MULT = new CalculatorButton("*");
	public static CalculatorButton OVERX = new CalculatorButton("1/x");
	public static CalculatorButton TANH = new CalculatorButton("tanh");
	public static CalculatorButton TAN = new CalculatorButton("tan");
	public static CalculatorButton CUBE = new CalculatorButton("x\u00B3");
	public static CalculatorButton ROOT3 = new CalculatorButton("\u221B"+"x");
	public static CalculatorButton N1 = new CalculatorButton("1");
	public static CalculatorButton N2 = new CalculatorButton("2");
	public static CalculatorButton N3 = new CalculatorButton("3");
	public static CalculatorButton SUB = new CalculatorButton("-");
	public static CalculatorButton EQ = new CalculatorButton("=");
	public static CalculatorButton PI = new CalculatorButton("\u03C0");
	public static CalculatorButton TWOPI = new CalculatorButton("2*\u03C0");
	public static CalculatorButton MOD = new CalculatorButton("Mod");
	public static CalculatorButton LOG = new CalculatorButton("log");
	public static CalculatorButton TENX = new CalculatorButton("<html>10<sup>x</sup></html>");
	public static CalculatorButton N0 = new CalculatorButton("0");
	public static CalculatorButton DEC = new CalculatorButton(".");
	public static CalculatorButton PLUS = new CalculatorButton("+");
	
	public static CalculatorButton INVS = new CalculatorButton("<html>sin\u207B<sup>1</sup></html>");
	public static CalculatorButton INVSH = new CalculatorButton("<html>sinh\u207B<sup>1</sup></html>");
	public static CalculatorButton INVC = new CalculatorButton("<html>cos\u207B<sup>1</sup></html>");
	public static CalculatorButton INVCH = new CalculatorButton("<html>cos\u207B<sup>1</sup></html>");
	public static CalculatorButton INVT = new CalculatorButton("<html>tan\u207B<sup>1</sup></html>");
	public static CalculatorButton INVTH = new CalculatorButton("<html>tanh\u207B<sup>1</sup></html>");
	public static CalculatorButton EX = new CalculatorButton("<html>e<sup>x</sup></html>");
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) 
	{
		char typed = arg0.getKeyChar();
		System.out.println(typed);
		for(int i=0;i<keyLinkedButtons.size();i++)
		{
			if(keyLinkedButtons.get(i).getText().charAt(0) == typed)
			{
				//System.out.println("Button clicked = " +)
				keyLinkedButtons.get(i).doClick();
			}
		}
		
	}

}
