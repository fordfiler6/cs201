package Lab1;

import java.util.ArrayList;

public class Main 
{

	public static void main(String[] args) 
	{
		ArrayList<Integer> empty = new ArrayList<Integer>();
		
		for(int i = 0; i<3; i++)
		{
			empty.add(0);
		}

		for(int i = 0; i<empty.size();i++)
		{
			System.out.println(empty.get(i));
		}
	}

}
