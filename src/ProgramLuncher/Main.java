package ProgramLuncher;
import DAL.HR_TR.DALhrtrManager;
import PL.TransportsEmployess.PL;
import PL.TransportsEmployess.PLimpl;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Employee.Rank;

public class Main {

	public static void main(String[] args) throws Exception 
	{
//		DALhrtrManager dal = DALhrtrManager.getDALImp();
//		dal.insertSite(new Site("Base", "0111111111", "yoniz",  123));
//		dal.insertEmployee(new Employee(111111111, "itay", "rosh", 10000, "23/04/2016", "", "123456/leumi/123/15%/10%", Rank.logisticManager, "Base", 7));
//		
		PL pl = new PLimpl();
		pl.run();

	}
	
//	private static void testForDebug(){
//		DALhrtrManager dal = DALhrtrManager.getDALImp();
//		Vector<Pair<String,Integer>> vec = new Vector<Pair<String,Integer>>();
//		Shift sht = new Shift("24/12/2017", "morning", 3, 1, "ofir street");
//		vec.add(new Pair<String,Integer>("StoreKeeper",1));
//		vec.add(new Pair<String,Integer>("Carrier",1));
//		dal.insertShift(sht,
//				vec);
//		dal.addEmployeeShift(sht, 
//				new Employee(699999999, "regular9", "regular9", 50000, "04/19/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7)
//				, "StoreKeeper");
//		dal.addEmployeeShift(sht, 
//				new Driver(655555555, "regular5", "regular5", 50000, "04/19/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7,31)
//				, "Carrier");
//		dal.insertTransport(new Transport(611111111, 11111111, 1, "24/12/2017", "10:01", 50, 1, "itay street"));
//		
//		BLimp bl = new BLimp();
//		System.out.println(bl.checkDate("24/12/2017", "ofir street", 1, ord));
//	}

}
