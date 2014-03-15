package Restaurant;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class GUI
{
	JFrame window;
	JTable table;
	JScrollPane tablePane;
	JPanel tablePanel;
	MainPanel container;
	Restaurant res;
	final int GUI_WIDTH = 800;
	final int GUI_HEIGHT = 600;
	GUI (Restaurant res)
	{
		this.res = res;
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(GUI_WIDTH,GUI_HEIGHT);
		container = new MainPanel(res);
		container.setSize(GUI_WIDTH,GUI_HEIGHT);
		container.setLayout(null);
		
		//Table Status
		TableStatusList tableList = res.getTableList();
		String[] columnNames = tableList.getColumnNames();

		table = new JTable(new Object[20][columnNames.length], columnNames);
		tablePane = new JScrollPane(table);
		tablePane.setPreferredSize(new Dimension(tableList.getWidth(),table.getPreferredSize().height));
		
		tablePanel = new JPanel();
	
		tablePanel.add(tablePane);
		
		tablePanel.setBounds(tableList.getLocation().getX(),tableList.getLocation().getY(), tableList.getWidth(), tablePane.getPreferredSize().height);
		
		
		container.repaint();
		container.add(tablePanel);
		window.add(container);
		window.setVisible(true);
	}
	
	
}
