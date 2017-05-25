package ProgramLuncher;

import DAL.HR_TR.DALhrtrManager;
import SharedClasses.TransportsEmployess.Transport;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		
	///	init.forItay();
	///	init.initHRTR();
		///InitSupplierInv.init();
	
		
		
	//	PL pl = new PLimpl();
	//	pl.run();
		testForDebug();
	}
	
	private static void testForDebug(){
		DALhrtrManager dal = DALhrtrManager.getDALImp();
		dal.insertTransport(new Transport(611111111, 11111111, 1, "24/12/2017", "10:01", 50, 1, "itay street"));
	}

}
