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
	void printContents()
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
		
		
		//get table status element
		curElement = (Element)doc.getElementsByTagName("tablestatus").item(0);
		
		NodeList cols = curElement.getElementsByTagName("column");
		
		
		
		for(int i = 0; i<cols.getLength();i++)
		{
			Element col = (Element)cols.item(i);
			
		}
		
		
		System.out.println(name + " "+x+" "+y);

	}
}
