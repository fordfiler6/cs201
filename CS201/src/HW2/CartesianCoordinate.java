package HW2;

public class CartesianCoordinate extends Coordinate 
{

	public CartesianCoordinate(double x, double y)
	{
		super(x, y);
	}

	@Override
	public double getDistance(Coordinate c) 
	{
		double deltaX = this.getValue1()-c.getValue1();
		double deltaY = this.getValue2()-c.getValue2();
		double deltaXSq = Math.pow(deltaX, 2);
		double deltaYSq = Math.pow(deltaY, 2);
		double radicand = deltaXSq+deltaYSq;
		double distance = Math.sqrt(radicand);
		return distance;
	}

	@Override
	public double getSlopeOfLine(Coordinate c) 
	{
		double deltaX = this.getValue1()-c.getValue1();
		double deltaY = this.getValue2()-c.getValue2();
		double slope = deltaY/deltaX;
		return slope;
	}
	
	public PolarCoordinate convertToPolar()
	{
		CartesianCoordinate origin = new CartesianCoordinate(0,0);
		double r = this.getDistance(origin);
		double theta = Math.atan2(this.getValue2(), this.getValue1());
		theta = Math.toDegrees(theta);
		
		PolarCoordinate polarVersion = new PolarCoordinate(r,theta);
			
		return polarVersion;
		
		
	}

	@Override
	public String getEquationOfLine(Coordinate c) 
	{
		double slope = this.getSlopeOfLine(c);
		double intercept = (slope*-1)*this.getValue1() + this.getValue2();
		
		return "y = "+df.format(slope)+"x + "+df.format(intercept);

	}

}
