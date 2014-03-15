package Restaurant;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class InputFile 
{
	Document doc = null;
	Restaurant xmlRes;
	InputFile(String fileName)
	{
		try
		{
			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.normalize();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	void readTitleInfo()
	{
		//get title element
		Element curElement = (Element)doc.getElementsByTagName("title").item(0);
		
		//get name
		String name = curElement.getElementsByTagName("name").item(0).getTextContent();

		//get location element
		curElement = (Element)curElement.getElementsByTagName("location").item(0);
		
		//get title x
		String x = curElement.getElementsByTagName("x").item(0).getTextContent();
		
		//get title y
		String y = curElement.getElementsByTagName("y").item(0).getTextContent();
		
		Title resTitle = new Title(name, new Location(Integer.parseInt(x),Integer.parseInt(y)));
		
		xmlRes.setTitle(resTitle);
	}
	void readTableList()
	{
		TableStatusList tableList = new TableStatusList();
		
		//get table status element
		Element curElement = (Element)doc.getElementsByTagName("tablestatus").item(0);
		
		//get location element
		Element locElement = (Element)curElement.getElementsByTagName("location").item(0);
		
		
		
		NodeList cols = curElement.getElementsByTagName("column");
		
		
		for(int i = 0; i<cols.getLength();i++)
		{
			Element col = (Element)cols.item(i);

			//get Column Name
			String colName = col.getAttribute("name");
			
			
			//get location element
			locElement = (Element)col.getElementsByTagName("location").item(0);
			
			//get title x
			String colX = locElement.getElementsByTagName("x").item(0).getTextContent();
			
			//get title y
			String colY = locElement.getElementsByTagName("y").item(0).getTextContent();
			
			Location colLoc = new Location(Integer.parseInt(colX),Integer.parseInt(colY));
			
			
			Element fontElement = (Element)col.getElementsByTagName("font").item(0);
			
			FontStyle font = new FontStyle();
			
			font.setFace(fontElement.getAttribute("name"));
			
			if((fontElement.getAttribute("type").equalsIgnoreCase("plain")))
				font.setWeight(FontWeight.PLAIN);
			else if (fontElement.getAttribute("type").equalsIgnoreCase("bold"))
				font.setWeight(FontWeight.BOLD);
			else
			{
				//error case
			}
			
			font.setSize(Integer.parseInt(fontElement.getAttribute("size")));
			
			tableList.addColumn(new TableColumn(colName,colLoc,font));
			
			
			
		}
		
		xmlRes.setTableList(tableList);
	}
	void readTables()
	{
		Element curElement = (Element)doc.getElementsByTagName("tables").item(0);
		NodeList tables = curElement.getElementsByTagName("table");
		
		for(int i = 0; i<tables.getLength();i++)
		{
			Table toAdd = new Table();
			Element table = (Element)tables.item(i);
			

			
			String shape = table.getElementsByTagName("shape").item(0).getTextContent();
			if(shape.equalsIgnoreCase("round"))
			{
				toAdd = new RoundTable();
				int radius = Integer.parseInt(table.getElementsByTagName("size").item(0).getTextContent());
				
				((RoundTable) toAdd).setRadius(radius);
			}
			else if(shape.equalsIgnoreCase("square"))
			{
				toAdd = new SquareTable();
				int sLen = Integer.parseInt(table.getElementsByTagName("size").item(0).getTextContent());
				
				((SquareTable) toAdd).setsLen(sLen);
			}
			else if(shape.equalsIgnoreCase("rectangle"))
			{
				toAdd = new RectangleTable();
				Element locElement = (Element) table.getElementsByTagName("size").item(0);
				int width = Integer.parseInt(locElement.getElementsByTagName("width").item(0).getTextContent());
				int height = Integer.parseInt(locElement.getElementsByTagName("height").item(0).getTextContent());
			
				((RectangleTable) toAdd).setWidth(width);
				((RectangleTable) toAdd).setHeight(height);
			}
			else
			{
				//error case
			}
			
			int tableNum = Integer.parseInt(table.getAttribute("number"));
			toAdd.setNumber(tableNum);
			
			Element locElement = (Element) table.getElementsByTagName("location").item(0);
			int x = Integer.parseInt(locElement.getElementsByTagName("x").item(0).getTextContent());
			int y = Integer.parseInt(locElement.getElementsByTagName("y").item(0).getTextContent());
			
			toAdd.setLocation(new Location(x,y));
			
			String status = table.getElementsByTagName("status").item(0).getTextContent();
			
			if(status.equalsIgnoreCase("open"))
			{
				toAdd.setOccupied(false);
			}
			else if(status.equalsIgnoreCase("occupied"))
			{
				toAdd.setOccupied(true);
			}
			else
			{
				//error case
			}
			
			int numSeats = Integer.parseInt(table.getElementsByTagName("numseats").item(0).getTextContent());
			toAdd.setNumber(numSeats);
			
			xmlRes.addTable(toAdd);
		
			
				
		}
		
	}
	void readWalls()
	{
		Element curElement = (Element)doc.getElementsByTagName("walls").item(0);
		NodeList walls = curElement.getElementsByTagName("wall");
		
		for(int i = 0; i<walls.getLength();i++)
		{
			Element wall = (Element)walls.item(0);
			Wall toAdd = new Wall();
			
		}
	
	}
	Restaurant readContents()
	{
		xmlRes = new Restaurant();
		readTitleInfo();
		readTableList();
		readTables();
		
		
		return xmlRes;

	}
}
