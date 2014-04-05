package core;

import java.util.ArrayList;
import java.util.Scanner;

public class GameBoard 
{
	public static final int NUM_SPACES = 10 * 4; //MUST BE DIVISIBLE BY 4 or board will be wierd
	private ArrayList<Space> boardSpaces;
	
	public GameBoard(String boardDescFile)
	{
		Scanner scan = new Scanner(boardDescFile);
		scan.useDelimiter(",");
		while(scan.hasNext())
		{
			String type = scan.next();
			Space temp = new Safe();
			if(type.equalsIgnoreCase("DIST"))
			{
				String name = scan.next();
				int amount = scan.nextInt();
				temp = new Distribution(name,amount);
			}
			else if(type.equalsIgnoreCase("CPU"))
			{
				String name = scan.next();
				int coreCount = scan.nextInt();
				int threadCount = scan.nextInt();
				int clockSpeed = scan.nextInt();
				int baseRent = scan.nextInt();
				int oc1 = scan.nextInt();
				int oc2 = scan.nextInt();
				int oc3 = scan.nextInt();
				temp = new CPU(name,coreCount,threadCount,clockSpeed,baseRent,oc1,oc2,oc3);
			}
			else if(type.equalsIgnoreCase("GPU"))
			{
				
			}
			else if(type.equalsIgnoreCase("MINE"))
			{
				
			}
			else if(type.equalsIgnoreCase("VIRUS"))
			{
				
			}
			else if(type.equalsIgnoreCase("SAFE"))
			{
				
			}
			boardSpaces.add(temp);
		}
	}
}
