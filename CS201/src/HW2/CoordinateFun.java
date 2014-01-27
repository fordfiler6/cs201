package HW2;

import java.util.Scanner;



public class CoordinateFun 
{
	static Scanner scan = new Scanner(System.in);
	public static void main(String[] args)
	{
		
	}
	private static void menu()
	{
		MenuOption selection = mainMenu();
		switch(selection)
		{
			case POLAR:
				break;
			case CARTESIAN:
				break;
			case FILE:
				break;
			case EXIT:
				break;
		}
	}
	private static MenuOption mainMenu()
	{
		MenuOption selectedOption = MenuOption.EXIT;
		
		System.out.println("[polar] Polar Coordinates (r, theta)");
		System.out.println("[cartesian] Cartesian Coordinates (x, y)");
		System.out.println("[file] File Input");
		System.out.println("[exit] Exit program");

		boolean valid = false;
		while(!valid)
		{
			System.out.print("What type of coordinates? ");
			String userInput = scan.nextLine();
			if(userInput.equalsIgnoreCase("polar"))
			{
				selectedOption = MenuOption.POLAR;
				valid = true;
			}
			else if(userInput.equalsIgnoreCase("cartesian"))
			{
				selectedOption = MenuOption.CARTESIAN;
				valid = true;
			}
			else if(userInput.equalsIgnoreCase("file"))
			{
				selectedOption = MenuOption.FILE;
				valid = true;
			}
			else if(userInput.equalsIgnoreCase("exit"))
			{
				selectedOption = MenuOption.EXIT;
				valid = true;
			}
			if(!valid)
			{
				System.out.println("Invalid Input");
			}
				
		}
		return selectedOption;
		
	}
}
