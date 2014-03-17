package Restaurant;

public class Table
{
	private int number;
	private int numSeats;
	private Location location;
	private boolean occupied;
	private int numSittingAtTable;
	
	public int getNumSittingAtTable() 
	{
		if(numSittingAtTable==0 && occupied)
			return numSeats;
		return numSittingAtTable;
	}
	public void setNumSittingAtTable(int numSittingAtTable) {
		this.numSittingAtTable = numSittingAtTable;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public boolean isOccupied() {
		return occupied;
	}
	public void setOccupied(boolean occupied) {
		if(occupied == false)
			numSittingAtTable = 0;
		this.occupied = occupied;
	}
	
	
}
