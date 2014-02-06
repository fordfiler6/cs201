package Lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;



public class Buffered1337er extends BufferedReader implements Neat1337Stuff
{
	public Buffered1337er(Reader arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	public int l337Read() throws IOException
	{
		int returnVal = read();
		String forConvert = returnVal+"";
		forConvert = forConvert.toLowerCase();
		returnVal = forConvert.charAt(0);
		String l33ted = l33ctionary.get(returnVal);
		if(l33ted != null)
		{
			returnVal = l33ted.charAt(0);
		}
		return returnVal;
	}
	public String l337readLine() throws IOException
	{
		String input = readLine();
		String output = "";
		for(int i = 0;i<input.length();i++)
		{
			String l33ted = l33ctionary.get(input.charAt(i));
			if(l33ted != null)
			{
				output += l33ted;
			}
			else
			{
				output += input.charAt(i);
			}
				
		}
		return output;
	}
	@Override
	public char l337ify(char c) {
		// TODO Auto-generated method stub
		return 0;
	}
}
