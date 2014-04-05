package core;

import javax.swing.JPanel;

public class CPU extends Property
{
	public static final int BASERENT_COST_FACTOR = 15;
	private int cores, clockSpeed, threads, baseRent, oc1Rent, oc2Rent, oc3Rent;
	
	public CPU(String _name, int _coreCount, int _threadCount, int _clockSpeed, int _baseRent,int _oc1, int _oc2,int _oc3)
	{
		super(_name, _baseRent*BASERENT_COST_FACTOR);
		cores = _coreCount;
		threads = _threadCount;
		baseRent = _baseRent;
		oc1Rent = _oc1;
		oc2Rent = _oc2;
		oc3Rent = _oc3;
		
		clockSpeed = _clockSpeed;
		
	}
	
	@Override
	public int calculateRent() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public JPanel drawSpace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void landOnSpace(Player p) {
		// TODO Auto-generated method stub
		
	}

}
