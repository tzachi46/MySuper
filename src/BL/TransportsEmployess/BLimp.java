package BL.TransportsEmployess;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.Pair;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.Truck;

public class BLimp implements BL {

	//Fields
	private BLEmployees_imp bl_emp;
    private BLTransports_imp bl_trans;
    public static final int WEEK = 7;
    
    //Constructor
    public BLimp(){
    	bl_emp = new BLEmployees_imp();
    	bl_trans = new BLTransports_imp();
    }
    
	@Override
	public Vector<Pair<Shift, String>> fetchAllEmployeeShifts(int id) {
		return bl_emp.fetchAllEmployeeShifts(id);
	}

	@Override
	public void sortDatesPair(Vector<Pair<Shift, String>> vec) {
		bl_emp.sortDatesPair(vec);	
	}

	@Override
	public boolean addEmployee(String id, String salary, String private_name, String last_name,
			String start_of_employment_date, String end_of_employment_date, String rank, String bankAccount,
			String address,int day) {
		return bl_emp.addEmployee(id, salary, private_name, last_name, start_of_employment_date, end_of_employment_date, rank, bankAccount, address, day);
	}

	@Override
	public boolean updateEmployee(String id, String salary, String private_name, String last_name,
			String start_of_employment_date, String end_of_employment_date, String rank, String bankAccount,
			String address,int day) {
		return bl_emp.updateEmployee(id, salary, private_name, last_name, start_of_employment_date, end_of_employment_date, rank, bankAccount, address,day);
	}

	@Override
	public boolean deleteEmployee(String id) {
		return bl_emp.deleteEmployee(id);
	}

	@Override
	public Employee fetchEmployee(String id) {
		return bl_emp.fetchEmployee(id);
	}

	@Override
	public boolean addShift(String date, String type, int manager, int cashier, int storekeeper, int carrier,
			String address) {
		return bl_emp.addShift(date, type, manager, cashier, storekeeper, carrier, address);
	}

	@Override
	public boolean initShift(Shift shift) {
		return bl_emp.initShift(shift);
	}

	@Override
	public boolean deleteShift(String type, String date, String address) 
	{
		return bl_emp.deleteShift(type,date,address);
	}

	@Override
	public Shift fetchShift(String type, String date, String address) {
		return bl_emp.fetchShift(type, date, address);
	}

	@Override
	public Vector<Shift> getEmptyShifts(String address) {
		return bl_emp.getEmptyShifts(address);
	}

	@Override
	public Vector<Shift> getUptoDate(Vector<Shift> vec) {
		return bl_emp.getUptoDate(vec);
	}

	@Override
	public boolean addEmployeeSpeciality(int id, String specialization) {
		return bl_emp.addEmployeeSpeciality(id, specialization);
	}

	@Override
	public boolean deleteEmployeeSpeciality(int id, String specialization) {
		return bl_emp.deleteEmployeeSpeciality(id, specialization);
	}

	@Override
	public boolean insertEmployeeRestriction(int id, int day, String type) {
		return bl_emp.insertEmployeeRestriction(id, day, type);
	}

	@Override
	public boolean deleteEmployeeRestriction(int id, int day, String type) {
		return bl_emp.deleteEmployeeRestriction(id, day, type);
	}

	@Override
	public EmployeeRestriction fetchEmployeeRestriction(int id, int day, String type) {
		return bl_emp.fetchEmployeeRestriction(id, day, type);
	}

	@Override
	public EmployeeSpeciality[] getSpecsOf(int id) {
		return bl_emp.getSpecsOf(id);
	}

	@Override
	public void sortDates(Vector<Shift> notSorted) {
		bl_emp.sortDates(notSorted);
	}

	@Override
	public boolean addEmployeeShift(Shift shift, Employee emp, String specialization) {
		return bl_emp.addEmployeeShift(shift, emp, specialization);
	}

	@Override
	public Vector<EmployeeRestriction> fetchAllEmployeeRestrictions(int id) {
		return bl_emp.fetchAllEmployeeRestrictions(id);
	}

	@Override
	public Vector<Employee> getPossibleWorkers(Shift shift, String specialization) {
		return bl_emp.getPossibleWorkers(shift, specialization);
	}

	@Override
	public void reduce(Vector<Integer> vecOpt1, Vector<Employee> vecMan, Vector<Employee> vecCash) {
		bl_emp.reduce(vecOpt1, vecMan, vecCash);
	}

	@Override
	public boolean createTruck(int truckNumber, String model, double weight, double maxWeight,
			int requiredLicenseType) {
		return bl_trans.createTruck(truckNumber, model, weight, maxWeight, requiredLicenseType);
	}

	@Override
	public boolean checkTruckExist(int truckNumber) {
		return bl_trans.checkTruckExist(truckNumber);
	}

	@Override
	public boolean updateTruck(int truckNumber, String model, double weight, double maxWeight,
			int requiredLicenseType) {
		return bl_trans.updateTruck(truckNumber, model, weight, maxWeight, requiredLicenseType);
	}

	@Override
	public Truck fetchTruck(int truckNumber) {
		return bl_trans.fetchTruck(truckNumber);
	}

	@Override
	public boolean deleteTruck(int truckNumber) {
		return bl_trans.deleteTruck(truckNumber);
	}

	@Override
	public boolean insertDriver(Driver driver) {
		return bl_trans.insertDriver(driver);
	}

	@Override
	public boolean checkDriverExist(int ID) {
		return bl_trans.checkDriverExist(ID);
	}

	@Override
	public Driver fetchDriver(int ID) {
		return bl_trans.fetchDriver(ID);
	}

	@Override
	public boolean deleteDriver(int ID) {
		return bl_trans.deleteDriver(ID);
	}

	@Override
	public boolean createSite(String address, String phoneNumber, String contactName/*, int isStore*/, int areaCode) {
		return bl_trans.createSite(address, phoneNumber, contactName, /*isStore,*/ areaCode);
	}

	@Override
	public boolean updateSite(String address, String phoneNumber, String contactName, int areaCode) {
		return bl_trans.updateSite(address, phoneNumber, contactName, areaCode);
	}

	@Override
	public Site fetchSite(String address) {
		return bl_trans.fetchSite(address);
	}

	@Override
	public boolean deleteSite(String address) {
		DAL.Inventory.InvDALManager.getInstance().removeSiteProducts(address);
		return bl_trans.deleteSite(address);
	}

	@Override
	public boolean createTransport(String date, String time, int truckNumber, int driverID, int companyID/*String source*/,
			double weight, int sourceDoc)
	{
		return bl_trans.createTransport(date, time, truckNumber, driverID, companyID/*source*/, weight, sourceDoc);
	}

	@Override
	public Transport fetchTransport(String date, String time, int numberOfTruck) {
		return bl_trans.fetchTransport(date, time, numberOfTruck);
	}

	@Override
	public boolean updateTransport(String date, String time, int truckNumber, int driverID, int companyID /*String source*/,
			double weight, int sourceDoc) {
		return bl_trans.updateTransport(date, time, truckNumber, driverID, companyID /*source*/, weight, sourceDoc);
	}

	@Override
	public boolean deleteTransport(String date, String time, int truckNumber) {
		return bl_trans.deleteTransport(date, time, truckNumber);
	}

	@Override
	public boolean addSiteToTransport(String date, String time, int truckNumber, String siteAddress, int docCode,
			String hourOfArr){
		return bl_trans.addSiteToTransport(date, time, truckNumber, siteAddress, docCode, hourOfArr);
	}

	@Override
	public boolean deleteSiteFromTransport(String date, String time, int truckNumber, String siteAddress) {
		return bl_trans.deleteSiteFromTransport(date, time, truckNumber, siteAddress);
	}

	@Override
	public Vector<Driver> fetchAvailableDrivers(String addressStore, String date, String time) {
		return bl_trans.fetchAvailableDrivers(addressStore, date, time);
	}

	@Override
	public Vector<Pair<String, Integer>> fetchShiftAccupations(Shift shift) {
		return bl_emp.fetchShiftAccupations(shift);
	}

	@Override
	public Vector<Shift> fetchInitializedShifts() 
	{
		return bl_emp.fetchInitializedShifts();
	}

	@Override
	public Vector<Pair<Employee, String>> fetchEmployeesInShift(String address, Shift shift) {
		return bl_emp.fetchEmployeesInShift(address, shift);
	}

	@Override
	public boolean cheakAvailableStoreKeepers(String addressStore, String date, String time) 
	{
		//Check shift type
		String type="morning";
		int hour = Integer.parseInt(time.substring(0, 2));
		if(hour<24 && hour>=12)
			type="evening";
		return bl_emp.cheakAvailableStoreKeepers(addressStore, date, type);
	}

	@Override
	public boolean updateDriver(int id, int parseInt) {
		return bl_trans.updateDriver(id,parseInt);	
	}

	@Override
	public String getTransportDests(String date, String hour, int truckNumber) {
		return bl_trans.getTransportDests(date, hour, truckNumber);
	}

	@Override
	public Vector<String> getHoursOfArrival(Transport trans)
	{
		return bl_trans.getHoursOfArrival(trans);
	}

	@Override
	public boolean checkIfTruckAvilable(String date,String time,int truckNumber) 
	{
		return bl_trans.checkIfTruckAvilable(date, time, truckNumber);
	}

	@Override
	public Vector<String> getNotHandledMessages(String workAddress) {
		return bl_trans.getNotHandledMessages(workAddress);
	}

	@Override
	public Vector<String> getHandledMessages(String workAddress) {
		return bl_trans.getHandledMessages(workAddress);
	}
	
	private Pair<Driver, Truck> checkAvailabilityOfDriversToTrucks(Vector<Integer> truckNums, String date, String shift, String store){
		Vector<Driver> drivers = bl_trans.fetchAvailableDrivers(store, date, shift);
		double weight = getWeightOfOrder();
		for(Driver d : drivers){
			for(Integer truckNo : truckNums){
				if(bl_trans.checkLicenceAndWeight(d.getId(), truckNo, weight)){
					return new Pair<Driver,Truck>(d, bl_trans.fetchTruck(truckNo));
				}
			}
		}
		return null;
	}
	
	private double getWeightOfOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	public boolean checkTransportToOrder(Order order){
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/mm/yyyy");
        LocalDate localDate = LocalDate.now();
        String date; 
        for(int i = 0; i < WEEK; i++){
       	 localDate.plusDays(i);
       	 date = dtf.format(localDate);
       	 if(checkDate(date, order.getStore()) == true)/* after big merge */
       			 return true;
        }
        return false;
   }
	
	
	private boolean checkDate(String date, String store, int supplierId){
		/* store keepers */
		if(!(bl_emp.cheakAvailableStoreKeepers(store, date, "morning")) &&
				!(bl_emp.cheakAvailableStoreKeepers(store, date, "morning"))){
			return false;
		} else {
			/* get the optional trucks in morning */
			Vector<Integer> trucksNumsM = this.bl_trans.fetchAvailableTrucks(date, "morning");
			/* choose the proper driver to the truck in morning */
			Pair<Driver,Truck> driverNtruckM = this.checkAvailabilityOfDriversToTrucks(trucksNumsM, date, "morning", store);
			if(driverNtruckM == null){ /* not M */
				/* get the optional trucks in evening */
				Vector<Integer> trucksNumsE = this.bl_trans.fetchAvailableTrucks(date, "evening");
				/* choose the proper driver to the truck in evening */
				Pair<Driver,Truck> driverNtruckE = this.checkAvailabilityOfDriversToTrucks(trucksNumsE, date, "evening", store);
				if(driverNtruckE == null){/* not M  & not E */
					return false;
				} else {/* E */
					return (bl_trans.createTransport(date, "12:01", driverNtruckE.getValue().getTruckNo(),
							driverNtruckE.getKey().getId(), supplierId, getWeightOfOrder(), getSourceDoc()));
				}
			} else { /* M */
				return (bl_trans.createTransport(date, "00:01", driverNtruckM.getValue().getTruckNo(),
						driverNtruckM.getKey().getId(), supplierId, getWeightOfOrder(), getSourceDoc()));
			}
		}
	}
	
	private int getSourceDoc() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Vector<Order> getUndeliveredOrders() {
		// TODO Auto-generated method stub
		//return null;
		throw new UnsupportedOperationException(); 
	}

	@Override
	public boolean addreesAtTransport(String address, Transport transport) {
		Vector<Integer> vec  = bl_trans.getOrdersInTransport(transport);
		for(Integer orderNo : vec){
			if(DAL.Orders.OrderManager.getInstance().getOrder(orderNo).getAddres().equals(address))
				return true;
		}
		return false;
		
	}

	@Override
	public String getArrivalTime(String address, Transport transport) {
		Vector<Integer> vec = bl_trans.getOrdersInTransport(transport);
		for(Integer orderNo : vec){
			Order ord = DAL.Orders.OrderManager.getInstance().getOrder(orderNo);
			if(ord.getAddres().equals(address)){
				return bl_trans.getArrivalTime(ord.getOrderNumber(), transport);
			}
		}
		return null;
	}
}
