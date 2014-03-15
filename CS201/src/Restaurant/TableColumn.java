package Restaurant;

public class TableColumn 
{
	private String name;
	private Location location;
	private FontStyle font;
	
	TableColumn (String _name, Location _location, FontStyle _font)
	{
		name = _name;
		location = _location;
		font = _font;
	}

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

	public FontStyle getFont() {
		return font;
	}

	public void setFont(FontStyle font) {
		this.font = font;
	}
	
	
}
