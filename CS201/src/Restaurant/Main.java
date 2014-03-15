package Restaurant;

public class Main 
{
	public static void main(String[] args)
	{
		InputFile in = new InputFile("Assignment4.xml");
		Restaurant restaurant = in.readContents();
		
		GUI gui = new GUI(restaurant);
	}
}
