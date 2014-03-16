package Restaurant;

public class Title 
{
	private String name;
	private Location location;

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Location getLocation() {
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}

	Title(String _name, Location _location)
	{
		name = _name;
		location = _location;

	}
}
