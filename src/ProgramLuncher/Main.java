package ProgramLuncher;

import PL.TransportsEmployess.PL;
import PL.TransportsEmployess.PLimpl;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		
		/*init.forItay();
		init.initHRTR();
		InitSupplierInv.init();*/
		// TODO Auto-generated method stub
		
		PL pl = new PLimpl();
		pl.run();
		
	}

}
