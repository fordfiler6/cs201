package HW2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.StringTokenizer;



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
					fileMenu();
					break;
				case EXIT:
					break;
			}
		}
	}
	private static void fileMenu()
	{
		File in = null;
		File out;
		while(in == null)
		{
			System.out.print("Enter the input filename: ");
			String inFileName = scan.next();
			in = new File(inFileName);
			if(!in.exists())
			{
				System.out.println("Sorry that file does not exist");
				in = null;
			}
		}
		System.out.print("Enter the output filename: ");
		String outFileName = scan.next();
		out = new File(outFileName);
		System.out.println();
		if(processFile(in,out))
		{
			System.out.println("File was parsed and output generated.");
			System.out.println();
			selection = mainMenu();
		}
	}
	private static boolean processFile(File in, File out)
	{
		Scanner inFile;
		try 
		{
			inFile = new Scanner(in);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("File Not Found");
			return false;
		}
		MenuOption fileCoordinateType;
		CoordinatePair fileCoordinates = new CoordinatePair();
		BufferedWriter writer;
		try 
		{
			writer = new BufferedWriter(new PrintWriter(out));
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("We're having trouble writing to the file.\n");
			return false;

		}
		
		
		
		while(inFile.hasNext())
		{
			String readIn = inFile.next();
			if(readIn.equalsIgnoreCase("Polar,"))
			{
				readIn = inFile.next();
				if(readIn.equalsIgnoreCase("Degrees"))
				{
					fileCoordinateType = MenuOption.DEGREES;
					fileCoordinates.coord1 = new DegreePolarCoordinate();
					fileCoordinates.coord2 = new DegreePolarCoordinate();
				}
				else if(readIn.equalsIgnoreCase("Radians"))
				{
					fileCoordinateType = MenuOption.RADIANS;
					fileCoordinates.coord1 = new PolarCoordinate();
					fileCoordinates.coord2 = new PolarCoordinate();
				}
				else
				{
					System.out.println("Trouble with file input: " + readIn+"\n");
					return false;
				}
			}
			else if (readIn.equalsIgnoreCase("Cartesian"))
			{
				fileCoordinateType = MenuOption.CARTESIAN;
				fileCoordinates.coord1 = new CartesianCoordinate();
				fileCoordinates.coord2 = new CartesianCoordinate();
			}
			else
			{
				System.out.println("Trouble with file input: " + readIn+"\n");
				return false;
			}
			if(!gatherCoordinates(fileCoordinates.coord1,inFile))
			{
				return false;
			}

			if(!gatherCoordinates(fileCoordinates.coord2,inFile))
			{
				return false;
			}

			if(!writeFile(fileCoordinates, writer, fileCoordinateType))
			{
				return false;
			}
			
		}
		try 
		{
			writer.close();
		} 
		catch (IOException e) 
		{
			System.out.println("File did not close correctly, your output file may or may not be correct");
		}
		return true;
		
	}
	private static boolean gatherCoordinates(Coordinate coord, Scanner inFile)
	{
		String line = inFile.nextLine();
		while(line.replaceAll("\\s+","").equals(""))
		{
			line=inFile.nextLine();
		}
		
		double num1 =0,num2=0;
		StringTokenizer tokenizer = new StringTokenizer(line,", ");
		if(tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			if(scan.tryParseDouble(token))
				num1 = Double.parseDouble(token);
			else
			{
				System.out.println("Failed to parse: "+token);
				return false;
			}
				
		}
		
		if(tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			if(scan.tryParseDouble(token))
				num2 = Double.parseDouble(token);
			else
			{
				System.out.println("Failed to parse: "+token);
				return false;
			}
		}
		
		coord.setValue1(num1);
		coord.setValue2(num2);
		return true;
	}
	private static boolean writeFile(CoordinatePair coords, BufferedWriter writer, MenuOption coordType)
	{
		String convertTo = "";
		String type = "";
		Coordinate coord1 = coords.coord1;
		Coordinate coord2 = coords.coord2;
		Coordinate converted1 = coord1;
		Coordinate converted2 = coord2;

		if(coordType == MenuOption.CARTESIAN)
		{
			convertTo="Polar";
			type="Cartesian";
		}
		else if(coordType == MenuOption.RADIANS || coordType == MenuOption.DEGREES)
		{
			convertTo = "Cartesian";
			type="Polar";
		}
		if(coordType == MenuOption.DEGREES)
		{
			converted1 = ((DegreePolarCoordinate) coord1).convertToCartesian();
			converted2 = ((DegreePolarCoordinate) coord2).convertToCartesian();

		}
		else if(coordType == MenuOption.RADIANS)
		{
			converted1 = ((PolarCoordinate) coord1).convertToCartesian();
			converted2 = ((PolarCoordinate) coord2).convertToCartesian();

		}
		else if(coordType == MenuOption.CARTESIAN)
		{
			converted1 = ((CartesianCoordinate) coord1).convertToPolar();
			converted2 = ((CartesianCoordinate) coord2).convertToPolar();
		}
		try 
		{
			writer.write("The "+convertTo+" coordinate for "+coord1+" is "+converted1+"\n");
			writer.write("The "+convertTo+" coordinate for "+coord2+" is "+converted2+"\n");
			writer.write("\n");
			writer.write("The distance between the "+type+" coordinates "+coord1+" and "+coord2+" is "+df.format(coord1.getDistance(coord2))+"\n");
			writer.write("\n");
			writer.write("The slope of the line between the "+type+" coordinates "+coord1+" and "+coord2+" is "+df.format(coord1.getSlopeOfLine(coord2))+"\n");
			writer.write("\n");
			writer.write("The equation of the line between the points is "+coord1.getEquationOfLine(coord2)+"\n");
			writer.write("\n");
			writer.write("---\n");
			writer.write("\n");
			return true;

			
		}
		catch (IOException e) 
		{
			System.out.println("We're having trouble writing to the file.\n");
			return false;
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
				Coordinate converted1 = coord1;
				Coordinate converted2 = coord2;
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
				if(!scan.nextLine().equals(""))
				{
					userInput="invalid";
				}
				System.out.println();
				if(userInput.equalsIgnoreCase("convert"))
				{
					valid = true;
					if(selection == MenuOption.DEGREES)
					{
						converted1 = ((DegreePolarCoordinate) coord1).convertToCartesian();
						converted2 = ((DegreePolarCoordinate) coord2).convertToCartesian();

						//selection = MenuOption.CARTESIAN;
					}
					else if(selection == MenuOption.RADIANS)
					{
						converted1 = ((PolarCoordinate) coord1).convertToCartesian();
						converted2 = ((PolarCoordinate) coord2).convertToCartesian();

						//selection = MenuOption.CARTESIAN;
					}
					else if(selection == MenuOption.CARTESIAN)
					{
						converted1 = ((CartesianCoordinate) coord1).convertToPolar();
						converted2 = ((CartesianCoordinate) coord2).convertToPolar();

						//selection = MenuOption.DEGREES;
					}

					System.out.println("The "+convertTo+" coordinate for "+coordinates.coord1+" is "+converted1);
					System.out.println("The "+convertTo+" coordinate for "+coordinates.coord2+" is "+converted2);
					System.out.println();
	
					
					
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
			
					if(Double.isNaN(slope) || Double.isInfinite(slope))
					{
						System.out.println("The slope of the line between the "+type+" coordinates "+coord1+" and "+coord2+" is undefined");
					}
					else
					{
						System.out.println("The slope of the line between the "+type+" coordinates "+coord1+" and "+coord2+" is "+df.format(slope));
					}
					System.out.println();
					

				}
				else if(userInput.equalsIgnoreCase("equation"))
				{
					valid = true;
					String equation = coord1.getEquationOfLine(coord2);
					double slope = coord1.getSlopeOfLine(coord2);
					if(Double.isNaN(slope) || Double.isInfinite(slope))
					{
						if(Double.isInfinite(slope))
						{
							equation = "x = "+coord1.getValue1();
						}
						else
						{
							equation = "undefined";
						}
					}
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
			System.out.print("Coordinate 2 – Please enter the radius: ");
			inputCoords.coord2.setValue1(scan.nextDouble());
			System.out.print("Coordinate 2 – Please enter theta ("+format+"): ");
			inputCoords.coord2.setValue2(scan.nextDouble());
		}
		else if(coordinateStyle == MenuOption.CARTESIAN)
		{
			inputCoords.coord1 = new CartesianCoordinate();
			inputCoords.coord2 = new CartesianCoordinate();
			
			System.out.print("Coordinate 1 - Please enter x: ");
			inputCoords.coord1.setValue1(scan.nextDouble());
			System.out.print("Coordinate 1 - Please enter y: ");
			inputCoords.coord1.setValue2(scan.nextDouble());
			System.out.print("Coordinate 2 – Please enter x: ");
			inputCoords.coord2.setValue1(scan.nextDouble());
			System.out.print("Coordinate 2 – Please enter y: ");
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
			if(!scan.nextLine().equals(""))
			{
				userInput="invalid";
			}
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
				scan.nextLine();
			}
				
		}
		return selectedOption;
		
	}
}
