package HW2;
import java.io.InputStream;
import java.util.Scanner;

public class EnhancedScanner
{
	Scanner scan;
	EnhancedScanner(InputStream in)
	{
		scan = new Scanner(in);
	}
	public String next()
	{
		return scan.next();
	}

	public double nextDouble()
	{
		String inStr;
		Double inDouble = null;
		while(inDouble == null)
		{
			inStr = scan.next();
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
}
