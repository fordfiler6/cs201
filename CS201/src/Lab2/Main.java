package Lab2;

import java.util.Random;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		Random rand = new Random(System.currentTimeMillis());
		int n = scan.nextInt();
		int[] haystack = new int[n];
		int numOfTrials = 1000;
		int sum = 0;
		for(int trials = 0;trials<numOfTrials;trials++)
		{
			for(int i =0;i<n;i++)
			{
				haystack[i]=0;
			}
			haystack[rand.nextInt(n)] = 1;
			
			for(int i =0;i<n;i++)
			{
				if(haystack[i] == 1)
				{
					sum+=i;
					break;
				}
				
			}
		}
		System.out.println("avg: "+(sum/numOfTrials));
	}
}
