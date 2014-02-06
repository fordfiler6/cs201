package HW2;

public class DegreePolarCoordinate extends PolarCoordinate {

	public DegreePolarCoordinate(double r, double theta) 
	{
		super(r, theta);
	}
	public DegreePolarCoordinate() 
	{
		super();
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
	
	@Override
	public String getEquationOfLine(Coordinate c) 
	{
		Coordinate cart1 = this.convertToCartesian();
		Coordinate cart2 = ((DegreePolarCoordinate)c).convertToCartesian();
		
		double slope = cart1.getSlopeOfLine(cart2);
		double intercept = (slope*-1)*cart1.getValue1() + cart1.getValue2();
		
		return "y = "+df.format(slope)+"x + "+df.format(intercept);

	}
}
