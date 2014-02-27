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
		 
		NodeList topList = doc.getElementsByTagName("title");
	 
		for (int temp = 0; temp < nList.getLength(); temp++) {
	 
			Node nNode = topList.item(temp);
	 
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
	 
			
			Element curElement = (Element) nNode;
			
			String name = curElement.getElementsByTagName("name").item(0).getTextContent();
			
			NodeList locList = curElement.getElementsByTagName("title");
			 
			for (int i = 0; i < locList.getLength(); i++) {
		 
				Node nNode = nList.item(temp);

				
				
				System.out.println("Name: " + name);
	 
			}
		}
	}
}
