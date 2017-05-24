package DAL.HR_TR;

import java.nio.file.Paths;
import java.util.Vector;

import DAL.DALhrtr_Interface;
import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.TransportsEmployess.Message;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.TransportDestination;
import SharedClasses.TransportsEmployess.Truck;



public class DALhrtrManager implements DALhrtr_Interface{
	private String url = "jdbc:sqlite:" + Paths.get(".").toAbsolutePath().normalize().toString()
			+ "\\DataBase.db";
	private static DALhrtrManager singleDAL = null;
	private Repository repo;
	private DALhrtrManager(){
		repo = new Repository(url);
		repo.createDB();
	}
	
	public static DALhrtrManager getDALImp(){
		if(singleDAL == null){
			singleDAL = new DALhrtrManager();
		}
		return singleDAL;
	}


	public boolean insertTruck(Truck truck) {
		return repo.getTrucks().insertTruck(truck);
	}
	public Truck fetchTruck(int truckNumber) {
		return repo.getTrucks().fetchTruck(truckNumber);
	}
	public boolean updateTruck(Truck truck) {
		return repo.getTrucks().updateTruck(truck);
	}
	public boolean deleteTruck(int truckNumber) {
		return repo.getTrucks().deleteTruck(truckNumber);
	}

	public boolean insertDriver(Driver driver) {
		return repo.getEmployees().insertDriver(driver);
	}
	public Driver fetchDriver(int iD) {
		return repo.getEmployees().fetchDriver(iD);
	}
	public boolean updateDriver(int id, int lio) {
		return repo.employees.updateDriver(id,lio);
	}

	public boolean insertSite(Site site) {
		return repo.getSites().insertSite(site);
	}
	public Site fetchSite(String address) {
		return repo.getSites().fetchSite(address);
	}
	public boolean updateSite(Site site) {
		return repo.getSites().updateSite(site);
	}
	public boolean deleteSite(String address) {
		return repo.getSites().deleteSite(address);
	}

	public boolean insertTransport(Transport transport) {
		return repo.getTransports().insertTransport(transport);
	}
	public Transport fetchTransport(String date, String time, int numberOfTruck) {
		return repo.getTransports().fetchTransport(numberOfTruck, time, date);
	}
	public boolean checkLicenceAndWeight(int driverID, int truckNumber, double weight) {
		return repo.checkLicenceAndWeight(driverID, truckNumber, weight);
	}
	public boolean updateTransport(Transport transport) {
		return repo.getTransports().updateTransport(transport);
	}
	public boolean deleteTransport(String date, String time, int truckNumber) {
		return repo.getTransports().deleteTransport(truckNumber, time, date);
	}

	public boolean insertTransportDestination(TransportDestination transportDestination) {
		return repo.getTransDests().insertTransDest(transportDestination);
	}
	public boolean deleteTransportDestination(int truckNumber, String siteAddress, String date, String time) {
		return repo.getTransDests().deleteTransportDestination(truckNumber, siteAddress, date, time);
	}
	
	public String getTransportDests(String date, String hour, int truckNumber) {
		return repo.getTransDests().getTransportDests(date, hour, truckNumber);
	}

	@Override
	public boolean insertEmployee(Employee emp) {
		return repo.getEmployees().insertEmployee(emp);
	}
	@Override
	public Employee fetchEmployee(int id) {
		return repo.getEmployees().fetchEmployee(id);
	}
	@Override
	public boolean updateEmployee(Employee emp) {
		return repo.getEmployees().updateEmployee(emp);
	}
	@Override
	public boolean deleteEmployee(int id) {
		return repo.getEmployees().deleteEmployee(id);
	}

	@Override
	public boolean insertEmployeeSpeciality(EmployeeSpeciality emps) {
		return repo.getEmployees().insertEmployeeSpeciality(emps);
	}
	@Override
	public EmployeeSpeciality fetchEmployeeSpeciality(EmployeeSpeciality emps) {
		return repo.getEmployees().fetchEmployeeSpeciality(emps);
	}
	@Override
	public Vector<EmployeeSpeciality> fetchAllEmployeeSpeciality(int id) {
		return repo.getEmployees().fetchAllEmployeeSpecialities(id);
	}
	@Override
	public boolean deleteEmployeeSpeciality(EmployeeSpeciality emps) {
		return repo.getEmployees().deleteEmployeeSpeciality(emps);
	}

	@Override
	public boolean insertEmployeeRestriction(EmployeeRestriction empr) {
		return repo.getEmployees().insertEmployeeRestriction(empr);
	}
	@Override
	public EmployeeRestriction fetchEmployeeRestriction(EmployeeRestriction empr) {
		return repo.getEmployees().fetchEmployeeRestriction(empr);
	}
	@Override
	public Vector<EmployeeRestriction> fetchAllEmployeeRestrictions(int id) {
		return repo.getEmployees().fetchAllEmployeeRestrictions(id);
	}
	@Override
	public boolean deleteEmployeeRestriction(EmployeeRestriction empr) {
		return repo.getEmployees().deleteEmployeeRestriction(empr);
	}

	@Override
	public boolean insertShift(Shift shift, Vector<Pair<String,Integer>> accupations) {
		return repo.getShifts().insertShift(shift,accupations);
	}
	@Override
	public Shift fetchShift(String date, String type,String address) {
		return repo.getShifts().fetchShift(date, type,address);
	}
	@Override
	public boolean initShift(Shift shift) {
		return repo.getShifts().initShift(shift);
	}
	@Override
	public Vector<Shift> fetchEmptyShift(String address) {
		return repo.getShifts().fetchEmptyShifts(address);
	}
	@Override
	public boolean deleteShift(String date, String type, String address) {
		return repo.getShifts().deleteShift(date, type,address);
	}
	@Override
	public Vector<Shift> fetchInitializedShifts() {
		return repo.getShifts().fetchInitializedShifts();
	}
	

	@Override
	public boolean addEmployeeShift(Shift shift, Employee emp, String specialization) {
		return repo.getEmpShifts().addEmployeeShift(shift, emp, specialization);
	}
	@Override
	public boolean deleteEmployeeShift(String date, String type,String address, String id) {
		return repo.getEmpShifts().deleteEmployeeShift(date, type,address, id);
	}
	@Override
	public boolean ShiftWithEmployeelExist(String date, String type, int id) {
		return repo.getEmpShifts().ShiftWithEmployeelExist(date, type, id);
	}

	@Override
	public Vector<Employee> getPossibleWorkers(Shift shift, String specialization) {
		return repo.getPossibleWorkers(shift, specialization);
	}

	@Override
	public Vector<Pair<Shift,String>> fetchAllEmployeeShifts(int id) {
		return repo.fetchAllEmployeeShifts(id);
	}

	@Override
	public Vector<Driver> fetchAvailableDrivers(String addressStore, String date, String shiftType) {
		return repo.fetchAvailableDrivers(addressStore, date, shiftType);
	}

	@Override
	public Vector<Employee> fetchAvailableStoreKeepers(String addressStore, String date, String shiftType) {
		return repo.fetchAvailableStoreKeepers(addressStore, date, shiftType);
	}

	@Override
	public Vector<Pair<String, Integer>> fetchShiftAccupations(Shift shift) {
		return repo.shifts.fetchShiftAccupation(shift);
	}

	@Override
	public Vector<Pair<Employee, String>> fetchEmployeesInShift(String address, Shift shift) {
		return repo.fetchEmployeesInShift(address,shift);
	}

	@Override
	public Vector<String> getHoursOfArrival(Transport trans) {
		return repo.getTransDests().getHoursOfArrival(trans);
	}

	@Override
	public Vector<Pair<String, Integer>> fetchtrucksAndDates(String date) {
		return repo.getTransports().fetchtrucksAndDates(date);
	}

	@Override
	public boolean insertMessage(Message message) {
		return repo.getMessages().insertMessage(message);
	}

	public Vector<Pair<String, Integer>> getMessages(String workAddress, boolean b) {
		// first=date second = ordernumber
		return repo.getMessages().fetchMessages(workAddress, b);
	}
	
	@Override
	public Vector<Integer> fetchAvailableTrucks(String date, String shift){
		return this.repo.fetchAvailableTrucks(date, shift);
	}

	@Override
	public Vector<Integer> getOrdersInTransport(Transport trans) {
		return this.repo.transports.getOrdersInTransport(trans);
	}

	@Override
	public TransportDestination fetchTransportDestination(int truckNumbeer, int orderNumber, String date, String hour) {
		return this.repo.transDests.fetchTransportDestination(truckNumbeer, orderNumber, date, hour);
	}
}
