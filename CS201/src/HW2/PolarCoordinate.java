package HW2;

import HW2.PolarCoordinate;

public class PolarCoordinate extends Coordinate 
{
	public PolarCoordinate(double r, double theta)
	{
		super(r, theta);
	}

	public PolarCoordinate() 
	{
		super();
	}

	@Override
	public double getDistance(Coordinate c) 
	{
		return getDistance(c, false);
	}
	public double getDistance(Coordinate c, boolean degrees) 
	{
		double rad1 = this.getValue1();
		double rad2 = this.getValue1();
		double rad1Sq = Math.pow(rad1, 2);
		double rad2Sq = Math.pow(rad2, 2);
		double thetaDiff = this.getValue2()-c.getValue2();
		if(degrees)
		{
			thetaDiff = Math.toRadians(thetaDiff);
		}
		double cosThetaDiff = Math.cos(thetaDiff);
		double radicand = rad1Sq + rad2Sq - (2*rad1*rad2*cosThetaDiff);
		double distance = Math.sqrt(radicand);
		return distance;
	}

	@Override
	public double getSlopeOfLine(Coordinate c) 
	{
		CartesianCoordinate point1 = this.convertToCartesian();
		CartesianCoordinate point2;
		if(c instanceof PolarCoordinate)
		{
			point2 = ((PolarCoordinate) c).convertToCartesian();
		}
		else
		{
			point2 = (CartesianCoordinate)c;
		}
		double slope = point1.getSlopeOfLine(point2);
		return slope;
	}
	public CartesianCoordinate convertToCartesian()
	{
		return convertToCartesian(false);
	}
	public CartesianCoordinate convertToCartesian(boolean degrees)
	{
		double r = this.getValue1();
		double theta = this.getValue2();
		
		if(degrees)
		{
			theta = Math.toRadians(theta);
		}
		double x = r*Math.cos(theta);
		double y = r*Math.sin(theta);
		
		return new CartesianCoordinate(x,y);
	}

}
