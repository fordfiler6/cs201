package HW1;

import java.util.Random;


public class CoordPair 
{
	private int row;
	private int col;
	
	private static int numRows = 1;
	private static int numCols = 1;
	
	CoordPair()
	{
		Random rand = new Random(System.currentTimeMillis());
		row = rand.nextInt(numRows)+1;
		col = rand.nextInt(numCols)+1;
	}
	
	CoordPair(int setRow, int setCol)
	{
		row = setRow;
		col = setCol;
	}
	
	public static void setGridSize(int rows, int cols)
	{
		numRows = rows;
		numCols = cols;
	}
	
	public double getDistanceTo(CoordPair dest)
	{
		//allow for diagonals to count as distance of 1
		if(isAdjacent(dest))
			return 1;
		int rowDiff = this.row - dest.getRow();
		int colDiff = this.col - dest.getCol();
		int sumSquares = (int) (Math.pow(rowDiff,2) + Math.pow(colDiff, 2));
		double distance = Math.sqrt(sumSquares);
		
		return distance;
	}
	
	public boolean isAdjacent(CoordPair other)
	{
		if(this.row+1 == other.getRow() && this.col+1 == other.getCol())
			return true;
		if(this.row+1 == other.getRow() && this.col-1 == other.getCol() )
			return true;
		if(this.row-1 == other.getRow() && this.col+1 == other.getCol() )
			return true;
		if(this.row-1 == other.getRow() && this.col-1 == other.getCol() )
			return true;
		else
			return false;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
}
