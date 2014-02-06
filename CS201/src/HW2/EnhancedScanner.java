package HW2;
import java.io.InputStream;
import java.util.Scanner;

public class EnhancedScanner
{
	Scanner scan;
	public EnhancedScanner(InputStream in)
	{
		scan = new Scanner(in);
	}
	public String next()
	{
		return scan.next();
	}
	public String nextLine()
	{
		return scan.nextLine();
	}

	public double nextDouble()
	{
		String inStr;
		Double inDouble = null;
		while(inDouble == null)
		{
			inStr = next();
			try
			{
				inDouble = Double.parseDouble(inStr);
			}
			catch (NumberFormatException nfe)
			{
				System.out.print("Please enter a valid number: ");
			}
		}
		return (double)inDouble;
	}
	public boolean tryParseDouble(String toBeParsed)
	{
		try
		{
			Double.parseDouble(toBeParsed);
			return true;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
	}
	public boolean hasNext()
	{
		return scan.hasNext();
	}
}
