package HW2;

public class DegreePolarCoordinate extends PolarCoordinate {

	public DegreePolarCoordinate(double r, double theta) 
	{
		super(r, theta);
	}
	@Override
	public double getDistance(Coordinate c)
	{
		return super.getDistance(c, true);
	}
	@Override
	public CartesianCoordinate convertToCartesian()
	{
		return super.convertToCartesian(true);
	}
}
