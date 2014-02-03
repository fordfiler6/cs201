package HW2;

public abstract class Coordinate 
{
	private double value1, value2; 
	public Coordinate(double val1, double val2)
	{
		value1 = val1;
		value2 = val2;
	}
	public Coordinate() 
	{
		
	}
	public abstract double getDistance(Coordinate c); 
	public abstract double getSlopeOfLine(Coordinate c); 
	protected double getValue1()
	{
		return value1;
	}
	protected double getValue2()
	{
		return value2;
	}
	protected void setValue1(double newValue1)
	{
		value1 = newValue1;
	}
	protected void setValue2(double newValue2)
	{
		value2 = newValue2;
	}
	public String toString()
	{
		return "("+value1+","+value2+")";
	}

}
