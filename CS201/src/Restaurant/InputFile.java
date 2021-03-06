package Restaurant;

import java.io.File;

import javax.swing.JOptionPane;
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
			JOptionPane.showMessageDialog(null,
				    "There was a problem reading your XML file, please try a new one",
				    "XML Parse Error",
				    JOptionPane.ERROR_MESSAGE);
			System.out.println("Faled to read XML");
			xmlRes = null;
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
		
		
		Element rowElement = (Element)curElement.getElementsByTagName("rows").item(0);
		
		tableList.numRows = Utils.safeParseInt(rowElement.getAttribute("number"));
		
		tableList.rowHeight = Utils.safeParseInt(rowElement.getAttribute("height"));
		
		Element fontElement = (Element)rowElement.getElementsByTagName("font").item(0);
		
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
		
		tableList.font = font;
		
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
			
			
			fontElement = (Element)col.getElementsByTagName("font").item(0);
			
			font = new FontStyle();
			
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
			toAdd.setNumSeats(numSeats);
			
			xmlRes.addTable(toAdd);
		
			
				
		}
		
	}

	void readWalls()
	{
		Element curElement = (Element)doc.getElementsByTagName("walls").item(0);
		NodeList walls = curElement.getElementsByTagName("wall");
		
		for(int i = 0; i<walls.getLength();i++)
		{
			Element wall = (Element)walls.item(i);
			Wall toAdd = new Wall();
			
			Element startEle = (Element) wall.getElementsByTagName("startlocation").item(0);
			Element endEle = (Element) wall.getElementsByTagName("endlocation").item(0);
			
			int startX = Integer.parseInt(startEle.getElementsByTagName("x").item(0).getTextContent());
			int startY = Integer.parseInt(startEle.getElementsByTagName("y").item(0).getTextContent());
			
			int endX = Integer.parseInt(endEle.getElementsByTagName("x").item(0).getTextContent());
			int endY = Integer.parseInt(endEle.getElementsByTagName("y").item(0).getTextContent());
			
			toAdd.setStart(new Location(startX,startY));
			toAdd.setEnd(new Location(endX, endY));
			
			xmlRes.addWall(toAdd);
		}
	
	}
	void readPodium()
	{
		//get podium element
		Element curElement = (Element)doc.getElementsByTagName("podium").item(0);
		

		//get location element
		Element locElement = (Element)curElement.getElementsByTagName("location").item(0);
		
		//get title x
		String x = locElement.getElementsByTagName("x").item(0).getTextContent();
		
		//get title y
		String y = locElement.getElementsByTagName("y").item(0).getTextContent();
		
		//get size
		Element sizeElement = (Element)curElement.getElementsByTagName("size").item(0);
		
		//get title x
		String width = sizeElement.getElementsByTagName("width").item(0).getTextContent();
		
		//get title y
		String height = sizeElement.getElementsByTagName("height").item(0).getTextContent();
		
		Podium resPodium = new Podium(new Location(Integer.parseInt(x),Integer.parseInt(y)), Integer.parseInt(width), Integer.parseInt(height));
		
		xmlRes.setPodium(resPodium);
	}
	Restaurant readContents()
	{
		xmlRes = new Restaurant();
		readTitleInfo();
		readTableList();
		readTables();
		readWalls();
		readPodium();

		
		return xmlRes;

	}
}
