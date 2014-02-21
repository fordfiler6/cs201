package Calculator;

public class CalculatorButton 
{
	public int colSpan,rowSpan;
	public String display;
	
	CalculatorButton()
	{
		
	}
	CalculatorButton(int colSpan,int rowSpan, String display)
	{
		this.colSpan = colSpan;
		this.rowSpan = rowSpan;
		this.display = display;
	}
	CalculatorButton(String display)
	{
		this.display = display;
	}
	
	public boolean is4Function()
	{
		return (this == GUI.PLUS || this == GUI.SUB || this == GUI.FWDS || this == GUI.MULT);
	}
}
