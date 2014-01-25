package Lecture;

import java.util.Random;
import java.util.Scanner;

public class Dice 
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		
		int numOfDie;
		int numOfRolls;
		System.out.print("Num of die: ");
		numOfDie = scan.nextInt();
		System.out.print("Num of Rolls: ");
		numOfRolls = scan.nextInt();
		int rollVal;
		Random rand = new Random(System.currentTimeMillis());
		int[] resultCounts = new int[numOfDie*6+1];
		for(int i = 0;i<numOfRolls;i++)
		{
			rollVal = 0;
			for(int k = 0;k<numOfDie;k++)
			{
				rollVal += rand.nextInt(6)+1;
			}
			resultCounts[rollVal]++;
		}
		for(int i = 0; i<resultCounts.length;i++)
		{
			System.out.println("Number of "+(i)+"'s = "+resultCounts[i]+" ("+((double)resultCounts[i]/(double)numOfRolls)*100+")");
		}
		
		
	}
}
