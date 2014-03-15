package ffiler_midterm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class GUI 
{
	private JFrame frame;
	private JMenuBar wordMenu = generateWordMenu();
	private JMenuBar spreadMenu = generateSpreadSheetMenu();
	private JPanel wordProcessor;
	private JPanel spreadSheet;
	private JRadioButton wordOption = new JRadioButton("Word Processor");
	private JRadioButton spreadOption = new JRadioButton("Spreadsheet");
	private Font font;
	private JPanel container;
	private JTextArea textArea;
	GUI()
	{
		frame = new JFrame();
		frame.setSize(700, 700);
		frame.setJMenuBar(wordMenu);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container = new JPanel();
		container.setLayout(new BorderLayout());
		
		container.add(generateRadioButtonPanel(), BorderLayout.NORTH);
		container.add(generateWordProcessor());
		spreadSheet = generateSpreadSheet();
		
		frame.add(container);
		
		frame.setVisible(true);
		
	}
	private JMenuBar generateWordMenu()
	{
		JMenuBar menu = new JMenuBar();
		JMenu change = new JMenu("Change");
		JRadioButtonMenuItem word = new JRadioButtonMenuItem("Word Processor");
		word.setSelected(true);
		change.add(word);
		
		JRadioButtonMenuItem spreadsheet = new JRadioButtonMenuItem("Spreadsheet");
		spreadsheet.addActionListener(new ChangeActionListener(this, true));
		change.add(spreadsheet);
		
		JMenu edit = new JMenu("Edit");
		JCheckBoxMenuItem bold = new JCheckBoxMenuItem("Bold");
		JCheckBoxMenuItem italic = new JCheckBoxMenuItem("Italic");
		bold.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				updateFont(Font.BOLD);
			}
		});
		
		italic.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				updateFont(Font.ITALIC);
			}
		});
		
		edit.add(bold);
		edit.add(italic);
		
	
		menu.add(change);
		menu.add(edit);
		
		return menu;
	}
	private JPanel generateRadioButtonPanel()
	{
		JPanel radioPanel = new JPanel();
		wordOption.addActionListener(new ChangeActionListener(this,false));
		spreadOption.addActionListener(new ChangeActionListener(this,true));
		ButtonGroup selector = new ButtonGroup();
		selector.add(wordOption);
		selector.add(spreadOption);
		wordOption.setSelected(true);
		
		radioPanel.add(wordOption);
		radioPanel.add(spreadOption);
		
		return radioPanel;
		
	}
	
	private JPanel generateWordProcessor()
	{
		Dimension d = new Dimension(25,55);
	 textArea = new JTextArea(d.width,d.height);
		font = new Font("Times New Roman", Font.PLAIN,16);
		textArea.setFont(font);
		textArea.setLineWrap(true);
		textArea.setPreferredSize(d);
		wordProcessor = new JPanel();
		wordProcessor.add(textArea);
		
		return wordProcessor;
	}
	private JPanel generateSpreadSheet()
	{
		String[] columnNames = {"A","B","C","D","E","F","G","H","I","J"};

		JTable table = new JTable(new Object[50][10], columnNames);
		JScrollPane pane = new JScrollPane(table);
		
		
		
		spreadSheet = new JPanel();
		spreadSheet.add(pane);
		return spreadSheet;
	}
	public void updateFont(int style)
	{
		if(style == Font.BOLD)
		{
			if(font.isBold())
			{
				if(font.isItalic())
				{
					font = new Font(font.getFamily(), Font.ITALIC, 16);
				}
				else
				{
					font = new Font(font.getFamily(), Font.PLAIN, 16);
				}
			}
			else
			{
				if(font.isItalic())
				{
					font = new Font(font.getFamily(), Font.ITALIC + Font.BOLD, 16);
				}
				else
				{
					font = new Font(font.getFamily(), Font.BOLD, 16);
				}
			}
		}
		if(style == Font.ITALIC)
		{
			if(font.isItalic())
			{
				if(font.isBold())
				{
					font = new Font(font.getFamily(), Font.BOLD, 16);
				}
				else
				{
					font = new Font(font.getFamily(), Font.PLAIN, 16);
				}
			}
			else
			{
				if(font.isBold())
				{
					font = new Font(font.getFamily(), Font.BOLD + Font.ITALIC, 16);
				}
				else
				{
					font = new Font(font.getFamily(), Font.ITALIC, 16);
				}
			}
		}
		textArea.setFont(font);
	}
	private JMenuBar generateSpreadSheetMenu()
	{
		JMenuBar menu = new JMenuBar();
		JMenu change = new JMenu("Change");
		JRadioButtonMenuItem word = new JRadioButtonMenuItem("Word Processor");
		word.addActionListener(new ChangeActionListener(this,false));
		change.add(word);
		
		JRadioButtonMenuItem spreadsheet = new JRadioButtonMenuItem("Spreadsheet");
		spreadsheet.setSelected(true);
		change.add(spreadsheet);
		
		JMenu table = new JMenu("Table");
		JMenuItem height = new JMenuItem("Row Height");
		height.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int n = JOptionPane.showOptionDialog(frame,
					    "Height ",
					    "RowHeight",
					    JOptionPane.YES_NO_CANCEL_OPTION,
					    JOptionPane.QUESTION_MESSAGE,
					    null,
					    null,
					    null);
				
			}
	
	
		}
		);
		
		table.add(height);
		
		menu.add(change);
		menu.add(table);
		
		return menu;
	}
	
	public void switchToWord()
	{
		frame.setVisible(false);
		wordOption.setSelected(true);
		wordMenu = generateWordMenu();
		container.remove(spreadSheet);
		container.add(wordProcessor);
		frame.setJMenuBar(wordMenu);
		frame.setVisible(true);
	}
	public void switchToSpreadSheet()
	{
		frame.setVisible(false);
		spreadOption.setSelected(true);
		spreadMenu = generateSpreadSheetMenu();
		container.remove(wordProcessor);
		container.add(spreadSheet);
		frame.setJMenuBar(spreadMenu);
		frame.setVisible(true);
	}
	
	
}
