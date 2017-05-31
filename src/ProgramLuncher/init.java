package ProgramLuncher;

import java.io.File;
import java.util.Vector;

import BL.BLManager;
import BL.TransportsEmployess.BL;
import BL.TransportsEmployess.BLimp;
import DAL.DALhrtr_Interface;
import DAL.HR_TR.DALhrtrManager;
import DAL.Suppliers.SupplierManager;
import SharedClasses.Pair;

import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.TransportDestination;
import SharedClasses.TransportsEmployess.Truck;
import SharedClasses.TransportsEmployess.Employee.Rank;

public class init 
{
	public static void main(String[] args) throws Exception {
		String Location=""+System.getProperty("user.dir")+"/DataBase.db";
		File f= new File(Location);
		f.delete();
		
		DAL.DALManager.getInstance();
		initHRTR();
		
		InitSupplierInv.init();
	//	forItay();
	//	initItay();
		String s[]=new String[2];
		s[0]="insert into SupplierProducts (SupplierId, ProductId, SupplierCatalogId, AvarageDeleveryTime) VALUES (1,2,123,21);";
		s[1]="INSERT INTO ProductsPrices (SupplierId, ProductId, MinimumQuantity, Price) VALUES (1,2,0,10);";
		SupplierManager.executeSQLCommand(s);
		System.out.println("DB has been ReInitialized");
	}

	public static void initHRTR() 
	{
		DALhrtr_Interface dal_imp = DALhrtrManager.getDALImp();
		//4 stores
		dal_imp.insertSite(new Site("Base", "0111111111", "yoniz",  123));
		dal_imp.insertSite(new Site("ofir street", "0111111111", "ofir",  123));
		dal_imp.insertSite(new Site("dov street", "0111111111", "dov",  123));
		dal_imp.insertSite(new Site("rosh street", "0111111111", "rosh", 123));
		
		//logistic manager
		dal_imp.insertEmployee(new Employee(111111111, "ceo1", "ceo1", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.logisticManager, "Base", 7));
		
		//4 store manegers
		dal_imp.insertEmployee(new Employee(944444444, "admin4", "admin4", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.storeManager, "Base", 7));
		dal_imp.insertEmployee(new Employee(911111111, "admin1", "admin1", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.storeManager, "ofir street", 7));
		dal_imp.insertEmployee(new Employee(922222222, "admin2", "admin2", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.storeManager, "dov street", 7));
		dal_imp.insertEmployee(new Employee(933333333, "admin3", "admin3", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.storeManager, "rosh street", 7));
		// 4 HR .
		dal_imp.insertEmployee(new Employee(244444444, "maneger4", "maneger4", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.humenResourceManager, "Base", 7));
		dal_imp.insertEmployee(new Employee(211111111, "maneger1", "maneger1", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.humenResourceManager, "ofir street", 7));
		dal_imp.insertEmployee(new Employee(222222222, "maneger2", "maneger2", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.humenResourceManager, "dov street", 7));
		dal_imp.insertEmployee(new Employee(233333333, "maneger3", "maneger3", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.humenResourceManager, "rosh street", 7));
		// 24 regular employees 6 per Store
		dal_imp.insertEmployee(new Employee(611111111, "regular1", "regular1", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7));
		dal_imp.insertEmployee(new Employee(622222222, "regular2", "regular2", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7));
		dal_imp.insertEmployee(new Employee(633333333, "regular3", "regular3", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7));
		dal_imp.insertEmployee(new Employee(644444444, "regular4", "regular4", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7));
		dal_imp.insertEmployee(new Employee(655555555, "regular5", "regular5", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7));
		dal_imp.insertEmployee(new Employee(666666666, "regular6", "regular6", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7));
		
		dal_imp.insertEmployee(new Employee(677777777, "regular7", "regular7", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7));
		dal_imp.insertEmployee(new Employee(688888888, "regular8", "regular8", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7));
		dal_imp.insertEmployee(new Employee(699999999, "regular9", "regular9", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7));
		dal_imp.insertEmployee(new Employee(711111111, "regular10", "regular10", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7));
		dal_imp.insertEmployee(new Employee(722222222, "regular11", "regular11", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7));
		dal_imp.insertEmployee(new Employee(733333333, "regular12", "regular12", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7));
				
		dal_imp.insertEmployee(new Employee(744444444, "regular13", "regular13", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7));
		dal_imp.insertEmployee(new Employee(755555555, "regular14", "regular14", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7));
		dal_imp.insertEmployee(new Employee(766666666, "regular15", "regular15", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7));
		dal_imp.insertEmployee(new Employee(777777777, "regular16", "regular16", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7));
		dal_imp.insertEmployee(new Employee(788888888, "regular17", "regular17", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7));
		dal_imp.insertEmployee(new Employee(799999999, "regular18", "regular18", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7));
		
		dal_imp.insertEmployee(new Employee(811111111, "regular19", "regular19", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "Base", 7));
		dal_imp.insertEmployee(new Employee(822222222, "regular20", "regular20", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "Base", 7));
		dal_imp.insertEmployee(new Employee(833333333, "regular21", "regular21", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "Base", 7));
		dal_imp.insertEmployee(new Employee(844444444, "regular22", "regular22", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "Base", 7));
		dal_imp.insertEmployee(new Employee(855555555, "regular23", "regular23", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "Base", 7));
		dal_imp.insertEmployee(new Employee(866666666, "regular24", "regular23", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "Base", 7));
		
		// reuglar emps speaciality
		//ofir
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(611111111, "ShiftManager"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(622222222, "Carrier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(633333333, "Carrier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(644444444, "Cashier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(655555555, "StoreKeeper"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(666666666, "StoreKeeper"));
		
		///itay
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(677777777, "ShiftManager"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(688888888, "Carrier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(699999999, "Carrier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(711111111, "Cashier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(722222222, "StoreKeeper"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(733333333, "StoreKeeper"));
		//dov
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(744444444, "ShiftManager"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(755555555, "Carrier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(766666666, "Carrier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(777777777, "Cashier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(788888888, "StoreKeeper"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(799999999, "StoreKeeper"));
		//base
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(811111111, "ShiftManager"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(822222222, "Carrier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(833333333, "Carrier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(844444444, "Cashier"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(855555555, "StoreKeeper"));
		dal_imp.insertEmployeeSpeciality(new EmployeeSpeciality(866666666, "StoreKeeper"));
		
		
		///  cant work in day of work
		//storeManager
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(211111111, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(211111111, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(222222222, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(222222222, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(233333333, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(233333333, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(244444444, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(244444444, 7, "evening"));
		//HR
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(911111111, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(911111111, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(922222222, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(922222222, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(933333333, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(933333333, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(944444444, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(944444444, 7, "evening"));
		//regular
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(611111111, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(611111111, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(622222222, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(622222222, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(633333333, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(633333333, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(644444444, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(644444444, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(655555555, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(655555555, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(666666666, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(666666666, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(677777777, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(677777777, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(688888888, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(688888888, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(699999999, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(699999999, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(711111111, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(711111111, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(722222222, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(722222222, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(733333333, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(733333333, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(744444444, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(744444444, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(755555555, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(755555555, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(766666666, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(766666666, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(777777777, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(777777777, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(788888888, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(788888888, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(799999999, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(799999999, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(811111111, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(811111111, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(822222222, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(822222222, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(833333333, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(833333333, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(844444444, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(844444444, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(855555555, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(855555555, 7, "morning"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(866666666, 7, "evening"));
		dal_imp.insertEmployeeRestriction(new EmployeeRestriction(866666666, 7, "morning"));


		dal_imp.insertDriver(new Driver(622222222, "regular2", "regular2", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7,21));
		dal_imp.insertDriver(new Driver(633333333, "regular3", "regular3", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7,21));
		dal_imp.insertDriver(new Driver(688888888, "regular8", "regular8", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7,21));
		dal_imp.insertDriver(new Driver(699999999, "regular9", "regular9", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7,21));
		dal_imp.insertDriver(new Driver(755555555, "regular14", "regular14", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7,21));
		dal_imp.insertDriver(new Driver(766666666, "regular15", "regular15", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7,21));
		dal_imp.insertDriver(new Driver(822222222, "regular20", "regular20", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7,21));
		dal_imp.insertDriver(new Driver(833333333, "regular21", "regular21", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7,21));
		
		//insert trucks
		dal_imp.insertTruck(new Truck(11111111, "subaro", 1000, 1000, 10));
		dal_imp.insertTruck(new Truck(22222222, "subaro", 1000, 1200, 15));
		dal_imp.insertTruck(new Truck(33333333, "subaro", 1000, 1200, 20));
		dal_imp.insertTruck(new Truck(44444444, "subaro", 1000, 1300, 25));
		dal_imp.insertTruck(new Truck(55555555, "subaro", 1000, 2000, 30));
		dal_imp.insertTruck(new Truck(66666666, "subaro", 1000, 2500, 45));
		
		//insert Shifts
		Vector<Pair<String,Integer>> shiftAcc1 = new Vector<Pair<String,Integer>>();
		Shift sht = new Shift("15/06/2017", "morning", 5, 1, "ofir street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
		
		
		dal_imp.addEmployeeShift(sht, new Employee(611111111, "regular1", "regular1", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(622222222, "regular2", "regular2", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(633333333, "regular3", "regular3", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(644444444, "regular4", "regular4", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(655555555, "regular5", "regular5", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "StoreKeeper");

		
		/***
		 * itay's shit
		 */
		Vector<Pair<String,Integer>> shiftAcc5 = new Vector<Pair<String,Integer>>();
		sht = new Shift("10/06/2017", "morning", 5, 0, "ofir street");
		shiftAcc5.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc5.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc5.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc5.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc5);
		
				
	    shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("29/05/2017", "morning", 5, 1, "ofir street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
		
		dal_imp.addEmployeeShift(sht, new Employee(611111111, "regular1", "regular1", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(622222222, "regular2", "regular2", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(633333333, "regular3", "regular3", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(644444444, "regular4", "regular4", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(655555555, "regular5", "regular5", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "StoreKeeper");
	
		
		
	    shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("20/06/2017", "morning", 5, 1, "ofir street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
		
		dal_imp.addEmployeeShift(sht, new Employee(611111111, "regular1", "regular1", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(622222222, "regular2", "regular2", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(633333333, "regular3", "regular3", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(644444444, "regular4", "regular4", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(655555555, "regular5", "regular5", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "StoreKeeper");
		
		shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("11/06/2017", "evening", 6, 0, "ofir street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
			
		shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("14/06/2017", "morning", 7, 0, "ofir street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
		
		dal_imp.addEmployeeShift(sht, new Employee(611111111, "regular1", "regular1", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(622222222, "regular2", "regular2", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(633333333, "regular3", "regular3", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(644444444, "regular4", "regular4", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(655555555, "regular5", "regular5", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "ofir street", 7), "StoreKeeper");
		
		shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("20/06/2017", "morning", 5, 1, "dov street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
				
		dal_imp.addEmployeeShift(sht, new Employee(677777777, "regular7", "regular7", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(688888888, "regular8", "regular8", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(699999999, "regular9", "regular9", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(711111111, "regular10", "regular10", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(722222222, "regular11", "regular11", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "StoreKeeper");
				
		shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("20/06/2017", "morning", 5, 1, "rosh street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
		
		dal_imp.addEmployeeShift(sht, new Employee(744444444, "regular13", "regular13", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(755555555, "regular14", "regular14", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(766666666, "regular15", "regular15", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(777777777, "regular16", "regular16", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(788888888, "regular17", "regular17", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "StoreKeeper");

				
		shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("20/06/2017", "morning", 5, 1, "Base");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
		
		dal_imp.addEmployeeShift(sht, new Employee(811111111, "regular19", "regular19", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(822222222, "regular20", "regular20", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(833333333, "regular21", "regular21", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(844444444, "regular22", "regular22", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(855555555, "regular23", "regular23", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "StoreKeeper");
	
	
		
		
		//// for boris
		
		
		
		
	    shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("30/06/2017", "morning", 5, 1, "ofir street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
		
		shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("30/06/2017", "morning", 5, 1, "dov street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
				
		dal_imp.addEmployeeShift(sht, new Employee(677777777, "regular7", "regular7", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(688888888, "regular8", "regular8", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(699999999, "regular9", "regular9", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(711111111, "regular10", "regular10", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(722222222, "regular11", "regular11", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "rosh street", 7), "StoreKeeper");
				
		shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("30/06/2017", "morning", 5, 1, "rosh street");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
		
		dal_imp.addEmployeeShift(sht, new Employee(744444444, "regular13", "regular13", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(755555555, "regular14", "regular14", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(766666666, "regular15", "regular15", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(777777777, "regular16", "regular16", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(788888888, "regular17", "regular17", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "StoreKeeper");

				
		shiftAcc1 = new Vector<Pair<String,Integer>>();
		sht = new Shift("30/06/2017", "morning", 5, 1, "Base");
		shiftAcc1.add(new Pair<String,Integer>("ShiftManager",1));
		shiftAcc1.add(new Pair<String,Integer>("StoreKeeper",1));
		shiftAcc1.add(new Pair<String,Integer>("Cashier",1));
		shiftAcc1.add(new Pair<String,Integer>("Carrier",2));
		dal_imp.insertShift(sht,shiftAcc1);
		
		dal_imp.addEmployeeShift(sht, new Employee(811111111, "regular19", "regular19", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "ShiftManager");
		dal_imp.addEmployeeShift(sht, new Employee(822222222, "regular20", "regular20", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Carrier");		
		dal_imp.addEmployeeShift(sht, new Employee(833333333, "regular21", "regular21", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Carrier");
		dal_imp.addEmployeeShift(sht, new Employee(844444444, "regular22", "regular22", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "Cashier");		
		dal_imp.addEmployeeShift(sht, new Employee(855555555, "regular23", "regular23", 50000, "04/04/2017", "", "123456/leumi/123/15%/10%", Rank.regular, "dov street", 7), "StoreKeeper");
	
	
		
		
		
		
		
	
	}
}
