package Restaurant;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JCheckBox;
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
	final int MENU_HEIGHT = 25;
	final int GUI_HEIGHT = 600 + MENU_HEIGHT;
	GUI (Restaurant res)
	{
		this.res = res;
		repaint();
		
	}
	void readNewFile(String file)
	{
		try
		{
			InputFile in = new InputFile(file);
			res = in.readContents();
		}
		catch(Exception e)
		{
			System.out.println("Oh No! Wild Exception found");
		}
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
		double locX = 0;
		double locY = 0;
		if(window != null)
		{
			locX = window.getLocation().getX();
			locY = window.getLocation().getY();	
			window.dispose();
		}
		window = new JFrame();
		window.setVisible(false);
		window.setLocation((int)locX, (int)locY);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(GUI_WIDTH,GUI_HEIGHT);
		window.setLayout(null);
		container = new MainPanel(res, this);
		container.setSize(GUI_WIDTH,GUI_HEIGHT);
		container.setLayout(null);
		
		if(res != null)
		{
			//Table Status
			TableStatusList tableList = res.getTableList();
			String[] columnNames = tableList.getColumnNames();
		
			Object[][] tableData = new Object[tableList.numRows][2];
			Object[][] xmlData = res.getTableData();
			
			
			if(tableData.length > xmlData.length)
			{
				for(int i =0;i<xmlData.length;i++)
				{
					tableData[i][0] = xmlData[i][0];
					tableData[i][1] = xmlData[i][1];
				}
				for(int i =xmlData.length;i<tableData.length;i++)
				{
					tableData[i][0] ="";
					tableData[i][1] ="";
				}
			}
			else
			{
				//error Case
			}
			table = new JTable(tableData, columnNames);
			table.setTableHeader(null);
			table.setRowHeight(tableList.rowHeight);
			table.setFont(new Font(tableList.font.getFace(), tableList.font.getWeight() == FontWeight.BOLD ? Font.BOLD : Font.PLAIN, tableList.font.getSize()));
			tablePane = new JScrollPane(table);
			tablePane.setPreferredSize(new Dimension(tableList.getWidth(),403));
			
			tablePanel = new JPanel();
		
			tablePanel.add(tablePane);
			
			ArrayList<TableColumn> cols = tableList.columns;
			for(TableColumn col : cols)
			{
				JLabel temp = new JLabel(col.getName());
				temp.setFont(new Font(col.getFont().getFace(), col.getFont().getWeight() == FontWeight.BOLD ? Font.BOLD : Font.PLAIN, col.getFont().getSize()));
				temp.setLocation(col.getLocation().getX(), col.getLocation().getY());
				temp.setSize(temp.getPreferredSize());
				temp.setBounds(col.getLocation().getX(), col.getLocation().getY() - col.getFont().getSize(), tableList.getWidth()/2, col.getFont().getSize());
				container.add(temp);
			}
			
			
			tablePanel.setBounds(tableList.getLocation().getX(),tableList.getLocation().getY(), tableList.getWidth(), GUI_HEIGHT);
			
			
			
			JPanel checkboxes =  new JPanel();
			checkboxes.setLayout(new GridLayout(0,1));
			JCheckBox viewOccupied = new JCheckBox("Occupied Tables");
			JCheckBox viewOpen = new JCheckBox("Open Tables");
			if(TableStatusList.showOccupied)
			{
				viewOccupied.setSelected(true);
			}
			if(TableStatusList.showFree)
			{
				viewOpen.setSelected(true);
			}
			viewOccupied.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					TableStatusList.showOccupied = ((JCheckBox)arg0.getSource()).isSelected();
					repaint();
				}
			
			});
			viewOpen.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					TableStatusList.showFree = ((JCheckBox)arg0.getSource()).isSelected();
					repaint();
				}
			
			});
			checkboxes.add(viewOpen);
			checkboxes.add(viewOccupied);
			checkboxes.setBounds(tableList.getLocation().getX(), tableList.getLocation().getY()+410, tableList.getWidth(), 50);
			container.add(checkboxes);
			
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
