package DAL;

import java.util.Vector;

import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.TransportsEmployess.Message;
import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.TransportDestination;
import SharedClasses.TransportsEmployess.Truck;

public interface DAL {
	/* Trucks */
	public boolean insertTruck(Truck truck);
	public Truck fetchTruck(int truckNumber);
	public boolean updateTruck(Truck truck);
	public boolean deleteTruck(int truckNumber);
	/* Site */
	public boolean insertSite(Site site);
	public Site fetchSite(String address);
	public boolean updateSite(Site site);
	public boolean deleteSite(String address);
	/* Transport */
	public boolean insertTransport(Transport transport);
	public Transport fetchTransport(String date, String time, int numberOfTruck);
	public boolean checkLicenceAndWeight(int driverID, int truckNumber, double weight);//////////////////////////
	public boolean updateTransport(Transport transport);
	public boolean deleteTransport(String date, String time, int truckNumber);
	/* TransportDestination */
	public boolean insertTransportDestination(TransportDestination transportDestination);
	public boolean deleteTransportDestination(int truckNumber, String siteAddress, String date, String time);
	public String getTransportDests(String date, String hour, int truckNumber);
	/* Drivers */
	public boolean insertDriver(Driver driver);
	public Driver fetchDriver(int iD);
	public boolean updateDriver(int id, int lio);
	/* Employees */
	public boolean insertEmployee(Employee emp);
	public Employee fetchEmployee(int id);
	public boolean updateEmployee(Employee emp);
	public boolean deleteEmployee(int id);
	/* Employee Speciality */
	public boolean insertEmployeeSpeciality(EmployeeSpeciality emps);
	public EmployeeSpeciality fetchEmployeeSpeciality(EmployeeSpeciality emps);
	public Vector<EmployeeSpeciality> fetchAllEmployeeSpeciality(int id);
	public boolean deleteEmployeeSpeciality(EmployeeSpeciality emps);
	/* Employee Restrictions */
	public boolean insertEmployeeRestriction(EmployeeRestriction empr);
	public EmployeeRestriction fetchEmployeeRestriction(EmployeeRestriction empr);
	public Vector<EmployeeRestriction> fetchAllEmployeeRestrictions(int id);
	public boolean deleteEmployeeRestriction(EmployeeRestriction empr);
	/* Shifts */
	public boolean insertShift(Shift shift, Vector<Pair<String,Integer>> accupations);
	public Shift fetchShift(String date, String type,String address);
	public boolean initShift(Shift shift);
	public Vector<Shift> fetchEmptyShift(String address);
	public boolean deleteShift(String date, String type,String address);
	public Vector<Shift> fetchInitializedShifts(); ////////////////
	/* Shifts n Employees */
	public boolean addEmployeeShift(Shift shift,Employee emp,String specialization);
	public boolean deleteEmployeeShift(String date, String type, String address, String id);
	public boolean ShiftWithEmployeelExist(String date, String type , int id);
	//public Vector<Employee> getEmployeesInShift(Shift shift);///////////// check if needed
	public Vector<Employee> getPossibleWorkers(Shift shift, String specialization);////////////////////// NEW
	public Vector<Pair<Shift,String>> fetchAllEmployeeShifts(int id);/////////////// NEW - check with borya 
	
	public Vector<Driver> fetchAvailableDrivers(String addressStore, String date, String shiftType);
	public Vector<Employee> fetchAvailableStoreKeepers(String addressStore, String date, String shiftType);
	public Vector<Pair<String,Integer>> fetchShiftAccupations(Shift shift);
	public Vector<Pair<Employee,String>> fetchEmployeesInShift(String address, Shift shift);
	
	public Vector<String> getHoursOfArrival(Transport trans);
	public Vector<Pair<String, Integer>> fetchtrucksAndDates(String date);
	public boolean insertMessage(Message message);
	public Vector<Integer> fetchAvailableTrucks(String date, String shift);
	
}
