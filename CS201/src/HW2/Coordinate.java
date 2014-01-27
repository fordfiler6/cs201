package HW2;

public abstract class Coordinate 
{
	private double value1, value2; 
	public Coordinate(double val1, double val2)
	{
		value1 = val1;
		value2 = val2;
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

}
