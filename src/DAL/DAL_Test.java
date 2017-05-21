package DAL;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.TransportDestination;
import SharedClasses.TransportsEmployess.Truck;
import SharedClasses.TransportsEmployess.Employee.Rank;

public class DAL_Test {
	
	//Fields
	private DAL_imp _dal;
	private int testInd;
	
	
	@Before
	public void setUp() throws Exception {
		_dal = DAL_imp.getDALImp();
	}

	@After
	public void tearDown() throws Exception {
		_dal.deleteEmployee(1);
		switch(testInd){
		case 2:
			_dal.deleteTruck(111);
			break;
		case 4:
			_dal.deleteEmployee(1234);
			break;
		case 6:
			_dal.deleteSite("hhhh");
			break;
		case 7:
			_dal.deleteEmployee(1234);
			_dal.deleteSite("hhhh");
			_dal.deleteSite("aaaa");
			_dal.deleteTruck(111);
			_dal.deleteTransport("01/02/2011", "10:12", 111);
			break;
		case 9:
			_dal.deleteEmployee(1234);
			_dal.deleteSite("hhhh");
			_dal.deleteSite("aaaa");
			_dal.deleteTruck(111);
			_dal.deleteTransportDestination(111, "aaaa", "01/02/2011", "10:12");
			_dal.deleteTransport("01/02/2011", "10:12", 111);
			break;
		case 10:
			_dal.deleteEmployee(1234);
			_dal.deleteSite("hhhh");
			_dal.deleteSite("aaaa");
			_dal.deleteTruck(111);
			_dal.deleteTransportDestination(111, "aaaa", "01/02/2011", "10:12");
			_dal.deleteTransport("01/02/2011", "10:12", 111);
			break;
		default:
			
		}

	}

	@Test
	public void testEmp() {
		//Insert
		Employee emp1 = new Employee(1, "test1", "test1", 10.5, "01/01/2017", null, 
				"123456/leumi/123/15%/10%", Rank.regular, "ofir street", 3);
		Employee emp2 = new Employee(2, "test2", "test2", 10.5, "01/01/2017", "02/01/2017", 
				"123456/leumi/123/15%/10%", Rank.regular, "ofir street", 3);
		//Without leaving date
		assertTrue(_dal.insertEmployee(emp1));
		//with leaving date
		assertTrue(_dal.insertEmployee(emp2));
		
		//Fetch
		assertEquals(_dal.fetchEmployee(1).toString(), emp1.toString());
		assertEquals(_dal.fetchEmployee(2).toString(), emp2.toString());
		
		//Update
		emp1.setSalary(777);
		assertTrue(_dal.updateEmployee(emp1));
		emp2.setRank(Rank.shiftManager);
		assertTrue(_dal.updateEmployee(emp2));
		
		//Fetch Again
		assertEquals(_dal.fetchEmployee(1).toString(), emp1.toString());
		assertEquals(_dal.fetchEmployee(2).toString(), emp2.toString());
		
		//Delete
		assertTrue(_dal.deleteEmployee(2));
		assertTrue(_dal.deleteEmployee(3));
		
		//fetch Again
		assertEquals(_dal.fetchEmployee(2), null);
		
		//Insert Specialty
		assertTrue(_dal.insertEmployeeSpeciality(new EmployeeSpeciality(1, "Carrier")));
		assertTrue(_dal.insertEmployeeSpeciality(new EmployeeSpeciality(1, "StoreKeeper")));
		
		//Fetch Specialty
		Vector<EmployeeSpeciality> vec = new Vector<EmployeeSpeciality>();
		vec.addElement(new EmployeeSpeciality(1, "Carrier"));
		vec.addElement(new EmployeeSpeciality(1, "StoreKeeper"));
		assertEquals(vec.elementAt(0).getSpecialization(), 
				_dal.fetchAllEmployeeSpeciality(1).elementAt(0).getSpecialization());
		assertEquals(vec.elementAt(0).getId(), 
				_dal.fetchAllEmployeeSpeciality(1).elementAt(0).getId());
		assertEquals(vec.elementAt(1).getSpecialization(),
				_dal.fetchAllEmployeeSpeciality(1).elementAt(1).getSpecialization());
		assertEquals(vec.elementAt(0).getId(), 
				_dal.fetchAllEmployeeSpeciality(1).elementAt(0).getId());
		assertEquals(vec.size(), _dal.fetchAllEmployeeSpeciality(1).size());
		
		//Delete Specialty
		assertTrue(_dal.deleteEmployeeSpeciality(vec.elementAt(0)));
		assertTrue(_dal.deleteEmployeeSpeciality(vec.elementAt(1)));
		
		//Fetch Specialty Again
		assertEquals(null, _dal.fetchAllEmployeeSpeciality(1));
		
		//Insert Specialty Again
		assertTrue(_dal.insertEmployeeSpeciality(new EmployeeSpeciality(1, "Carrier")));
		
		//Shifts
		assertFalse(_dal.getPossibleWorkers(new Shift
				("01/01/2017", "morning", 3, 1, "ofir street"), "Carrier").contains(emp1));
		
	}
	
	@Test
	public void testTruckInsertDelete() {
		testInd = 1;
		Truck trk1 = new Truck(111, "i3", 1000, 1200, 3);
		boolean result = _dal.insertTruck(trk1);
		assertTrue("insertion of truck was unsuccessful.", result);
		Truck trk2 = new Truck(111, "i8", 1020, 1000, 5);
		result = _dal.insertTruck(trk2);
		assertFalse("truck insertion was successful but shouldnt (primary key)", result);
		result = _dal.deleteTruck(trk1.getTruckNo());
		assertTrue("truck deletion was unsuccessful", result);
	}

	@Test
	public void testTruckFetchUpdate() {
		testInd = 2;
		Truck trk1 = new Truck(111, "i3", 1000, 1200, 3);
		_dal.insertTruck(trk1);
		boolean result = trk1.toString().equals(_dal.fetchTruck(111).toString());
		assertTrue("truck fetching of truck was unsuccessful.", result);
		result = (_dal.fetchTruck(112) == null);
		assertTrue("truck fetching was successful but shouldnt (primary key)", result);
		Truck trk2 = new Truck(111, "i5", 1000, 1200, 3);
		result = _dal.updateTruck(trk2);
		assertTrue("truck single update was unsuccessful", result);
		Truck trk3 = new Truck(111, "i5", 1200, 1000, 4);
		result = _dal.updateTruck(trk3);
		assertTrue("truck multiple update was unsuccessful", result);
		
	}

	@Test
	public void testDriverInsertDelete() {
		testInd = 3;
		Driver drv2 = new Driver(1234, "dani", "dani", 10000, "01/01/2017", null,
				"123456/leumi/123/15%/10%", Rank.regular, "rosh street",7, 3);
		boolean result = _dal.insertDriver(drv2);
		assertFalse("driver insertion was successful but shouldnt (primary key)", result);
		result = _dal.deleteEmployee(drv2.getId());
		assertTrue("driver deletion was unsuccessful", result);
	}
	
	@Test
	public void testDriverFetchUpdate() {
		testInd = 4;
		Driver drv1 = new Driver(1234, "itay", "rosh", 10000, "01/01/2017", null,
				"123456/leumi/123/15%/10%", Rank.regular, "rosh street",7, 2);
		_dal.insertEmployee(drv1);
		_dal.insertDriver(drv1);
		Driver d = _dal.fetchDriver(1234);
		boolean result = drv1.toString().equals(d.toString());
		assertTrue("fetching of driver was unsuccessful.", result);
		result = (_dal.fetchDriver(112) == null);
		assertTrue("driver fetching was successful but shouldnt (primary key)", result);
		Driver drv2 = new Driver(1234, "itay", "dani", 10000, "01/01/2017", null,
				"123456/leumi/123/15%/10%", Rank.regular, "rosh street",7, 2);
		result = _dal.updateEmployee(drv2);
		assertTrue("driver single update was unsuccessful", result);
		Driver drv3 = new Driver(1234, "dani", "danidani", 10000, "01/01/2017", null,
				"123456/leumi/123/15%/10%", Rank.regular, "rosh street",7, 3);
		result = _dal.updateEmployee(drv3);
		assertTrue("driver multiple update was unsuccessful", result);
	}
	
	@Test
	public void testSiteInsertDelete() {
		testInd = 5;
		Site st1 = new Site("hhhh","itay","0545492167",0);
		boolean result = _dal.insertSite(st1);
		assertTrue("insertion of site was unsuccessful.", result);
		Site st2 = new Site("hhhh","ofik","0545492167",0);
		result = _dal.insertSite(st2);
		assertFalse("site insertion was successful but shouldnt (primary key)", result);
		result = _dal.deleteSite("hhhh");
		assertTrue("site deletion was unsuccessful", result);
	}
	
	@Test
	public void testSiteFetchUpdate() {
		testInd = 6;
		Site st1 = new Site("hhhh","itay","0545492167",0);
		_dal.insertSite(st1);
		Site s = _dal.fetchSite("hhhh");
		boolean result = st1.toString().equals(s.toString());
		assertTrue("fetching of site was unsuccessful.", result);
		result = (_dal.fetchSite("hh") == null);
		assertTrue("site fetching was successful but shouldnt (primary key)", result);
		Site st2 = new Site("hhhh","ofik","0545492167",0);
		result = _dal.updateSite(st2);
		assertTrue("site single update was unsuccessful", result);
		Site st3 = new Site("hhhh","ofik","ofik",0);
		result = _dal.updateSite(st3);
		assertTrue("site multiple update was unsuccessful", result);
	}
	
	@Test
	public void testTransportInsert() {
		testInd = 7;
		Site st1 = new Site("hhhh","itay","0545492167",0);
		Site st2 = new Site("aaaa","ofik","0545492168",1);
		Driver drv1 = new Driver(1234, "itay", "rosh", 50000, "04/19/2017", "", "123456/leumi/123/15%/10%", Rank.shiftManager, "rosh street", 7,4);
		Truck trk1 = new Truck(111, "i3", 1000, 1200, 3);
		_dal.insertEmployee(drv1);
		_dal.insertDriver(drv1);
		_dal.insertSite(st1);
		_dal.insertSite(st2);
		_dal.insertTruck(trk1);
		Transport trp1 = new Transport(1234, 111, 1234, "01/02/2011", "10:12", 900,1);
		boolean result = _dal.insertTransport(trp1);
		assertTrue("insertion of transport was unsuccessful.", result);
		Transport trp3 = new Transport(1234, 111, 1234, "01/02/2011", "10:12", 950,2);
		result = _dal.insertTransport(trp3);
		assertFalse("insertion of transport was successful but shoudnt (PK)", result);
		TransportDestination td = new TransportDestination(111, "01/02/2011", "10:12", "aaaa", 123, "10:15");
		result = _dal.insertTransportDestination(td);
		assertTrue("transport destinstion insertion was unsuccessful", result);
	}
	
	@Test
	public void testTransportDelete() {
		testInd = 8;
		boolean result;
		result = _dal.deleteTransportDestination(111, "aaaa", "01/02/2011", "10:12");
		assertTrue("transport destinstion deletion was unsuccessful", result);
		result = _dal.deleteTransport("01/02/2011", "10:12", 111);
		assertTrue("deletion of transport was unsuccessful.", result);
	}
	
	@Test
	public void testTransportFetch() {
		testInd = 9;
		Site st1 = new Site("hhhh","itay","0545492167",0);
		Site st2 = new Site("aaaa","ofik","0545492168",1);
		Driver drv1 = new Driver(1234, "itay", "rosh", 10000, "01/01/2017", null,
				"123456/leumi/123/15%/10%", Rank.regular, "rosh street",7, 4);
		Truck trk1 = new Truck(111, "i3", 1000, 1200, 3);
		_dal.insertEmployee(drv1);
		_dal.insertDriver(drv1);
		_dal.insertSite(st1);
		_dal.insertSite(st2);
		_dal.insertTruck(trk1);
		
		Transport trp1 = new Transport(1234, 111, 1234, "01/02/2011", "10:12", 900,1);
		_dal.insertTransport(trp1);

		boolean result = (_dal.fetchTransport("01/02/2011", "10:12", 111).toString()).equals(trp1.toString());
		assertTrue("fetching of transport was unsuccessful.", result);
		result = _dal.fetchTransport("01/02/2015", "10:17", 111) == null;
		assertTrue("fetching of transport was successful but shoudnt (PK)", result);
	}
	
	@Test
	public void testTransportUpdate() {
		testInd = 10;
		Site st1 = new Site("hhhh","itay","0545492167",0);
		Site st2 = new Site("aaaa","ofik","0545492168",1);
		Driver drv1 = new Driver(1234, "itay", "rosh", 10000, "01/01/2017", null,
				"123456/leumi/123/15%/10%", Rank.regular, "rosh street",7, 4);
		Truck trk1 = new Truck(111, "i3", 1000, 1200, 3);
		_dal.insertDriver(drv1);
		_dal.insertSite(st1);
		_dal.insertSite(st2);
		_dal.insertTruck(trk1);
		
		Transport trp1 = new Transport(1234, 111, 1234, "01/02/2011", "10:12", 900,1);
		_dal.insertTransport(trp1);
		Transport trp2 = new Transport(1234, 111, 1234, "01/02/2011", "10:12", 978.9,1);
		boolean result = _dal.updateTransport(trp2);
		assertTrue("updating of transport was unsuccessful.", result);
	}
	
}
