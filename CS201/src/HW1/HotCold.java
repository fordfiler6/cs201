package HW1;

import java.util.Scanner;

public class HotCold 
{
	static CoordPair hidingPlace;
	static Scanner scan;
	static boolean admin = false;
	static CoordPair currentGuess = null;
	static CoordPair lastGuess = null;
	public static void main(String[] args) 
	{
		scan = new Scanner(System.in);
		

		
		System.out.println("Welcome to the hotter/colder game");
		
		createGrid();
		
		hidingPlace = new CoordPair();
		
		System.out.print("Do you have anything else to tell me? ");
		if(scan.next().equalsIgnoreCase("a")) 
			admin = true;
			
		if(admin)
		{
			System.out.println("Ahh you're an administrator. The random location is "+hidingPlace.getRow()+", "+hidingPlace.getCol());

		}
		
		
		
	}

	private static void createGrid()
	{
		System.out.print("How many rows are in the grid? ");
		int numRows = scan.nextInt();
		System.out.print("How many columns are in the grid? ");
		int numCols = scan.nextInt();
		
		CoordPair.setGridSize(numRows, numCols);
		
	}
	
	private static void getGuess()
	{

		if(lastGuess == null)
		{
			System.out.print("What is your first guess?");
			int row = scan.nextInt();
			int col = scan.nextInt();
			currentGuess = new CoordPair(row,col);
			hotterOrColder();
		}
		lastGuess = currentGuess;
		
		System.out.print("What is your next guess?");
		int row = scan.nextInt();
		int col = scan.nextInt();
		currentGuess = new CoordPair(row,col);
		
		
	}

	private static void hotterOrColder()
	{
		if(admin)
		{
			System.out.println("Distance = " + hidingPlace.getDistanceTo(lastGuess));
		}
		if(lastGuess != null)
		{
			
		}
	}
	
	

	

}
