package Restaurant;

public class FontStyle 
{
	
	private String face;
	private FontWeight weight;
	private int size;
	
	
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public FontWeight getWeight() {
		return weight;
	}
	public void setWeight(FontWeight weight) {
		this.weight = weight;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public String toString()
	{
		return face +" "+weight+" "+size;
	}
	
}
