package ProgramLuncher;

import DAL.DALhrtr_Interface;
import PL.TransportsEmployess.PL;
import PL.TransportsEmployess.PLimpl;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Employee.Rank;

public class Main {

	public static void main(String[] args) throws Exception 
	{
		
	///	init.forItay();
	///	init.initHRTR();
		///InitSupplierInv.init();
		DALhrtr_Interface dal = DAL.HR_TR.DALhrtrManager.getDALImp();
		dal.insertSite(new Site("Base", "0111111111", "yoniz",  123));
		dal.insertEmployee(new Employee(111111111, "ceo1", "ceo1", 50000, "04/19/2017", "", "123456/leumi/123/15%/10%", Rank.logisticManeger, "Base", 7));
		PL pl = new PLimpl();
		pl.run();
		
	}

}
