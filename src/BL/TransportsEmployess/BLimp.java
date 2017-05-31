package BL.TransportsEmployess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

import DAL.Orders.OrderManager;
import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.TransportsEmployess.Message;
import SharedClasses.Pair;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.OrderProduct;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.Truck;

public class BLimp implements BL {

	public BLTransports_imp getBl_trans() {
		return bl_trans;
	}

	public void setBl_trans(BLTransports_imp bl_trans) {
		this.bl_trans = bl_trans;
	}

	public BLEmployees_imp getBl_emp() {
		return bl_emp;
	}

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
	public boolean deleteOnlyDriver(int id){
		return bl_trans.deleteOnlyDriver(id);
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
			double weight, int sourceDoc,String address)
	{
		return bl_trans.createTransport(date, time, truckNumber, driverID, companyID/*source*/, weight, sourceDoc, address);
	}

	@Override
	public Transport fetchTransport(String date, String time, int numberOfTruck) {
		return bl_trans.fetchTransport(date, time, numberOfTruck);
	}

	@Override
	public boolean updateTransport(String date, String time, int truckNumber, int driverID, int companyID /*String source*/,
			double weight,String address) {
		return bl_trans.updateTransport(date, time, truckNumber, driverID, companyID /*source*/, weight,address);
	}

	/*@Override
	public boolean deleteTransport(String date, String time, int truckNumber) {
		return bl_trans.deleteTransport(date, time, truckNumber);
	}*/

	@Override
	public boolean addSiteToTransport(String date, String time, int truckNumber, int docCode,
			String hourOfArr){
		return bl_trans.addSiteToTransport(date, time, truckNumber, docCode, hourOfArr);
	}

	/*@Override
	public boolean deleteSiteFromTransport(String date, String time, int truckNumber, String siteAddress) {
		return bl_trans.deleteSiteFromTransport(date, time, truckNumber, siteAddress);
	}*/

	@Override
	public Vector<Driver> fetchAvailableDrivers(String addressStore, String date, String time) {
		return bl_trans.fetchAvailableDrivers(addressStore, date, time);
	}

	@Override
	public Vector<Pair<String, Integer>> fetchShiftAccupations(Shift shift) {
		return bl_emp.fetchShiftAccupations(shift);
	}

	@Override
	public Vector<Shift> fetchInitializedShifts(String address) 
	{
		return bl_emp.fetchInitializedShifts(address);
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

	/*@Override
	public Vector<Pair<String, String>> getTransportDests(String date, String hour, int truckNumber) {
		return bl_trans.getTransportDests(date, hour, truckNumber);
	}*/

	@Override
	public Vector<Pair<String, String>> getHoursOfArrival(Transport trans)
	{
		Vector<Pair<String, String>> vec = bl_trans.getHoursOfArrival(trans);
		for(int i =0; i < vec.size()-1;i++)
		{
			for(int j = i + 1; j < vec.size();j++)
			{
				if(vec.get(i).getKey().equals(vec.get(j).getKey()) && vec.get(i).getValue().equals(vec.get(j).getValue()))
					vec.remove(j);
			}
		}
		return vec;
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
	
	private Pair<Driver, Truck> checkAvailabilityOfDriversToTrucks(Vector<Integer> truckNums, String date, String shift, String store, double orderWeight){
		Vector<Driver> drivers = bl_trans.fetchAvailableDrivers(store, date, shift);
		double weight = orderWeight;
		for(Driver d : drivers){
			for(Integer truckNo : truckNums){
				if((d.getLno() >= bl_trans.fetchTruck(truckNo).getLicenceType()) && 
						(bl_trans.fetchTruck(truckNo).getMaxWeight() >= weight)){
					return new Pair<Driver,Truck>(d, bl_trans.fetchTruck(truckNo));
				}
			}
		}
		return null;
	}
	
	public boolean checkTransportToOrder(Order order){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime localDate = LocalDateTime.now();
        String date; 
        localDate=localDate.plusDays(order.getMaxProductPrepTime());
        for(int i = 0; i < WEEK; i++){
			localDate=localDate.plusDays(1);
			date = localDate.format(dtf);
			if(checkDate(date, order.getAddres(), order.getSupplierId(), order) == true){/* after big merge */
				//order.setDueDate(order.getDate());
				order.setHaveTransport(1);
				DAL.Orders.OrderManager.getInstance().updateOrder(order);
			    localDate = LocalDateTime.now();
				date = localDate.format(dtf);
				updateMessgae(new Message(date, order.getAddres(), true, order.getOrderNumber()));
				return true;
			}
        }
        localDate = LocalDateTime.now();
		date = localDate.format(dtf);
        bl_trans.insertMessage(new Message(date, order.getAddres(), false, order.getOrderNumber())); ///
        return false;
   }
	
	@Override
	public void updateMessgae(Message msg){
		bl_trans.updateMessage(msg);
	}
	
	public boolean checkDate(String date, String store, int supplierId, Order order){
//		
//		Vector<Transport> transports =  bl_trans.getTransportsInDate(date);
//			if(!transports.isEmpty()){
//				Transport tran = transports.firstElement();
//				int hour = Integer.parseInt(tran.getHourOfDep().substring(0, 2));
//				if(hour >= 12)
//					return bl_trans.addSiteToTransport(tran.getDateOfDep(), tran.getHourOfDep(),
//							tran.getTruckNo(), order.getOrderNumber(), "23:59");
//				else 
//					return bl_trans.addSiteToTransport(tran.getDateOfDep(), tran.getHourOfDep(),
//							tran.getTruckNo(), order.getOrderNumber(), "11:59");
//			}
		/**
		 * if there is'nt a transport to attach the order
		 */
		if(bl_emp.cheakAvailableStoreKeepers(store, date, "morning")){
			/* get the optional trucks in morning */
			Vector<Integer> trucksNumsM = this.bl_trans.fetchAvailableTrucks(date, "morning");
			/* choose the proper driver to the truck in morning */
			Pair<Driver,Truck> driverNtruckM = this.checkAvailabilityOfDriversToTrucks(trucksNumsM, date, "morning", store,order.getOrderNumber());
			if(driverNtruckM != null){ /* not M */
				return ((bl_trans.createTransport(date, "00:01", driverNtruckM.getValue().getTruckNo(),
						driverNtruckM.getKey().getId(), supplierId, order.getWeightOrder(), order.getSupplierId(), order.getAddres()))
						&& (bl_trans.addSiteToTransport(date, "00:01", driverNtruckM.getValue().getTruckNo(),
								order.getOrderNumber(), "11:59")));
			}
		} 
		if(bl_emp.cheakAvailableStoreKeepers(store, date, "evening")){
			/* get the optional trucks in evening */
			Vector<Integer> trucksNumsE = this.bl_trans.fetchAvailableTrucks(date, "evening");
			/* choose the proper driver to the truck in evening */
			Pair<Driver,Truck> driverNtruckE = this.checkAvailabilityOfDriversToTrucks(trucksNumsE, date, "evening", store, order.getOrderNumber());
			 if(driverNtruckE != null) {/* E */
				return ((bl_trans.createTransport(date, "12:01", driverNtruckE.getValue().getTruckNo(),
						driverNtruckE.getKey().getId(), supplierId, order.getWeightOrder(), order.getSupplierId(), order.getAddres()))
						&& (bl_trans.addSiteToTransport(date, "12:01", driverNtruckE.getValue().getTruckNo(),
								order.getOrderNumber(), "23:59")));
			 }
		} 
		return false;
		
	}
	@Override
	public Vector<Order> getUndeliveredOrders()
	{
		Vector<Order> vec = new Vector<Order>();
		LinkedList<Order> l = OrderManager.getInstance().getUndeliverdOrders();
		ListIterator<Order> listIterator = l.listIterator();
		while (listIterator.hasNext()) {
			vec.add(listIterator.next());
		}
		return vec;
	}

	@Override
	public boolean addreesAtTransport(String address, Transport transport) {
		Vector<Integer> vec  = bl_trans.getOrdersInTransport(transport);
		if(vec==null)
			return false;
		for(Integer orderNo : vec){
			if(DAL.Orders.OrderManager.getInstance().getOrder(orderNo).getAddres().equals(address))
				return true;
		}
		return false;
		
	}

	@Override
	public String getArrivalTime(String address, Transport transport) 
	{
		Vector<Integer> vec = bl_trans.getOrdersInTransport(transport);
		
		for(Integer orderNo : vec)
		{
			Order ord = DAL.Orders.OrderManager.getInstance().getOrder(orderNo);
			if(ord.getAddres().equals(address))
				return bl_trans.getArrivalTime(ord.getOrderNumber(), transport);
		}
		return null;
	}

	/*
     * NEW (edited 26.5 by Ofir): get transports whose driver's id = id
     */
	@Override
	public Vector<Transport> getRelevantTransports(int id) {
		return bl_trans.getRelevantTransports(id);
	}
	
	/*
     * NEW (edited 26.5 by Ofir): Sort vector of transports by date
     */
	@Override
	public void sortTrans(Vector<Transport> vec) {
		bl_trans.sortTrans(vec);
	}
	
	/*
     * NEW (edited 26.5 by Ofir): Sort vector of Pair<time, address> by date
     */
	@Override
	public void sortDests(Vector<Pair<String,String>> vec) {
		bl_trans.sortDests(vec);
	}
	/*
	 * NEW (28.5): check if pid exist in list of products
	 */
	@Override
	public boolean checkPidInList(LinkedList<OrderProduct> list, String pid){
		int id=-1;
		try{
			id=Integer.parseInt(pid);
		}catch(Exception e){
			//Should not be here, pid tested before
			return false;
		}
		ListIterator<OrderProduct> iter = list.listIterator();
		while (iter.hasNext()) 
		{
			OrderProduct curr = iter.next();
			if(curr.getProductId() == id)
				return true;
		}
		return false;
	}

	@Override
	public boolean checkReplacement(Truck truck) {
		return bl_trans.checkReplacement(truck);
	}

	public Vector<Integer> getTransportOrders(String date, String hour, int truckNo)
	{
		return bl_trans.getTransportOrders(date, hour, truckNo);
	}
	@Override
	public int compareDates(String d1, String d2){
		return bl_trans.compare(d1, d2);
	}
}
