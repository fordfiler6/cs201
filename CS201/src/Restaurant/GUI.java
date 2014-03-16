package Restaurant;

import java.awt.Dimension;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

public class GUI
{
	JFrame window;
	JTable table;
	JScrollPane tablePane;
	JPanel tablePanel;
	MainPanel container;
	Restaurant res;
	JMenuBar menu;
	final int GUI_WIDTH = 800;
	final int GUI_HEIGHT = 600;
	GUI (Restaurant res)
	{
		this.res = res;
		repaint();
		
	}
	void readNewFile(String file)
	{
		InputFile in = new InputFile(file);
		res = in.readContents();
	}
	
	void generateMenuBar()
	{
		menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		FCActionListener fcAL = new FCActionListener(this);
		open.addActionListener(fcAL);
		file.add(open);
		
		menu.add(file);
	}
	
	public void repaint()
	{
		if(window != null)
			window.dispose();
		window = new JFrame();
		window.setVisible(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(GUI_WIDTH,GUI_HEIGHT);
		container = new MainPanel(res);
		container.setSize(GUI_WIDTH,GUI_HEIGHT);
		container.setLayout(null);
		
		if(res != null)
		{
			//Table Status
			TableStatusList tableList = res.getTableList();
			String[] columnNames = tableList.getColumnNames();
		
			
			table = new JTable(res.getTableData(), columnNames);
			table.setTableHeader(null);
			tablePane = new JScrollPane(table);
			tablePane.setPreferredSize(new Dimension(tableList.getWidth(),400));
			
			tablePanel = new JPanel();
		
			tablePanel.add(tablePane);
			
			tablePanel.setBounds(tableList.getLocation().getX(),tableList.getLocation().getY(), tableList.getWidth(), GUI_HEIGHT);
			container.add(tablePanel);
		}
		
		generateMenuBar();
		window.setJMenuBar(menu);
		
		container.repaint();
		
		window.add(container);
		window.setVisible(true);
	}
	public JFrame getParentFrame()
	{
		return window;
	}
	
}
