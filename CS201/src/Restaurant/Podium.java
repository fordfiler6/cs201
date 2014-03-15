package Restaurant;

public class Podium 
{
	private Location location;
	private int width;
	private int height;
	
	Podium(Location location, int width, int height)
	{
		this.location = location;
		this.width = width;
		this.height = height;
	}
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
