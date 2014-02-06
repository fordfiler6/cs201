package HW2;

import java.text.DecimalFormat;
import java.util.Scanner;



public class CoordinateFun 
{
	static EnhancedScanner scan = new EnhancedScanner(System.in);
	static MenuOption selection;
	static DecimalFormat df = new DecimalFormat("#.##");
	public static void main(String[] args)
	{
		menu();
	}
	private static void menu()
	{
		selection = mainMenu();
		CoordinatePair coordinates = new CoordinatePair();
		while(selection != MenuOption.EXIT)
		{
			switch(selection)
			{
				case POLAR:
					selection = polarTypeMenu();
					break;
				case RADIANS:
				case DEGREES:
				case CARTESIAN:
					coordinates = gatherCoordinates(selection);
					actionMenu(coordinates);
					break;
				case FILE:
					break;
				case EXIT:
					break;
			}
		}
	}
	private static void actionMenu(CoordinatePair coordinates)
	{
		Coordinate coord1 = coordinates.coord1;
		Coordinate coord2 = coordinates.coord2;
		boolean stay = true;
		while(stay)
		{
			boolean valid = false;
			while(!valid)
			{
				String convertTo = "";
				String type = "";
				if(selection == MenuOption.CARTESIAN)
				{
					convertTo="Polar";
					type="Cartesian";
				}
				else if(selection == MenuOption.RADIANS || selection == MenuOption.DEGREES)
				{
					convertTo = "Cartesian";
					type="Polar";
				}
				System.out.println("[convert] Convert to "+convertTo+" coordinates");
				System.out.println("[distance] Find the distance between the two points"); 
				System.out.println("[slope] Find the slope of the line between the points"); 
				System.out.println("[equation] Find the equation of the line between the points"); 
				System.out.println("[menu] Return to main menu");
				System.out.println();
			
				System.out.print("What would you like to do?");
				String userInput = scan.next();
				System.out.println();
				if(userInput.equalsIgnoreCase("convert"))
				{
					valid = true;
					if(selection == MenuOption.DEGREES)
					{
						coord1 = ((DegreePolarCoordinate) coord1).convertToCartesian();
						coord2 = ((DegreePolarCoordinate) coord2).convertToCartesian();

						selection = MenuOption.CARTESIAN;
					}
					else if(selection == MenuOption.RADIANS)
					{
						coord1 = ((PolarCoordinate) coord1).convertToCartesian();
						coord2 = ((PolarCoordinate) coord2).convertToCartesian();

						selection = MenuOption.CARTESIAN;
					}
					else if(selection == MenuOption.CARTESIAN)
					{
						coord1 = ((CartesianCoordinate) coord1).convertToPolar();
						coord2 = ((CartesianCoordinate) coord2).convertToPolar();

						selection = MenuOption.DEGREES;
					}

					System.out.println("The "+convertTo+" coordinate for "+coordinates.coord1+" is "+coord1);
					System.out.println("The "+convertTo+" coordinate for "+coordinates.coord2+" is "+coord2);
					System.out.println();
					
					coordinates.coord1 = coord1;
					coordinates.coord2 = coord2;
					
					
				}
				else if(userInput.equalsIgnoreCase("distance"))
				{
					valid = true;
					double dist = coord1.getDistance(coord2);
					System.out.println("The distance between the "+type+" coordinates "+coord1+" and "+coord2+" is "+df.format(dist));
					System.out.println();

				}
				else if(userInput.equalsIgnoreCase("slope"))
				{
					valid = true;
					double slope = coord1.getSlopeOfLine(coord2);
					
					System.out.println("The slope of the line between the "+type+" coordinates "+coord1+" and "+coord2+" is "+df.format(slope));
					System.out.println();
					

				}
				else if(userInput.equalsIgnoreCase("equation"))
				{
					valid = true;
					String equation = coord1.getEquationOfLine(coord2);
					System.out.println("The eqation of the line between the points is "+equation);
					System.out.println();
				}
				else if(userInput.equalsIgnoreCase("menu"))
				{
					selection = mainMenu();
					valid = true;
					stay = false;
				}
				else
				{
					System.out.println("Please enter one of the options provided.\n");
				}
			}
		}

	}
	private static CoordinatePair gatherCoordinates(MenuOption coordinateStyle)
	{
		CoordinatePair inputCoords = new  CoordinatePair();
		if(coordinateStyle == MenuOption.DEGREES || coordinateStyle == MenuOption.RADIANS)
		{
			String format = "";
			if(coordinateStyle == MenuOption.DEGREES)
			{
				format = "degrees";
				inputCoords.coord1 = new DegreePolarCoordinate();
				inputCoords.coord2 = new DegreePolarCoordinate();
			}
			if(coordinateStyle == MenuOption.RADIANS)
			{
				format = "radians";
				inputCoords.coord1 = new PolarCoordinate();
				inputCoords.coord2 = new PolarCoordinate();
			}
			System.out.print("Coordinate 1 - Please enter the radius: ");
			inputCoords.coord1.setValue1(scan.nextDouble());
			System.out.print("Coordinate 1 - Please enter theta ("+format+"): ");
			inputCoords.coord1.setValue2(scan.nextDouble());
			System.out.print("Coordinate 2 � Please enter the radius: ");
			inputCoords.coord2.setValue1(scan.nextDouble());
			System.out.print("Coordinate 2 � Please enter theta ("+format+"): ");
			inputCoords.coord2.setValue2(scan.nextDouble());
		}
		else if(coordinateStyle == MenuOption.CARTESIAN)
		{
			System.out.print("Coordinate 1 - Please enter x: ");
			inputCoords.coord1.setValue1(scan.nextDouble());
			System.out.print("Coordinate 1 - Please enter y: ");
			inputCoords.coord1.setValue2(scan.nextDouble());
			System.out.print("Coordinate 2 � Please enter x: ");
			inputCoords.coord2.setValue1(scan.nextDouble());
			System.out.print("Coordinate 2 � Please enter y: ");
			inputCoords.coord2.setValue2(scan.nextDouble());
		}
		System.out.println();
		return inputCoords;
	}

	private static MenuOption polarTypeMenu()
	{
		MenuOption selectedOption = MenuOption.EXIT;
		
		boolean valid = false;
		while(!valid)
		{
			System.out.println("[degrees] Degrees");
			System.out.println("[radians] Radians");
			System.out.println("");
			System.out.print("What type of Angle? ");
			String userInput = scan.next();
			System.out.println("");
			if(userInput.equalsIgnoreCase("degrees"))
			{
				valid = true;
				selectedOption = MenuOption.DEGREES;
			}
			else if(userInput.equalsIgnoreCase("radians"))
			{
				valid = true;
				selectedOption = MenuOption.RADIANS;
				
			}
			if(!valid)
			{
				System.out.println("Please enter one of the options provided.");
			}

		}
		return selectedOption;
	}
	private static MenuOption mainMenu()
	{
		MenuOption selectedOption = MenuOption.EXIT;
		
		boolean valid = false;
		while(!valid)
		{
		
			System.out.println("[polar] Polar Coordinates (r, theta)");
			System.out.println("[cartesian] Cartesian Coordinates (x, y)");
			System.out.println("[file] File Input");
			System.out.println("[exit] Exit program\n");

		
			System.out.print("What type of coordinates? ");
			String userInput = scan.next();
			System.out.println("");

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
				System.out.println("Please enter one of the options provided.\n");
			}
				
		}
		return selectedOption;
		
	}
}
