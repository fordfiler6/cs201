package Restaurant;

public class Utils
{
	public static Integer safeParseInt(String in)
	{
		try
		{
			return Integer.parseInt(in);
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
