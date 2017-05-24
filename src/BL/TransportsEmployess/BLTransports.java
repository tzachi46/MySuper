package BL.TransportsEmployess;

import java.util.Vector;

import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.Truck;

public interface BLTransports {

	//*****	Trucks	*****//
	public boolean createTruck(int truckNumber, String model, double weight, double maxWeight, int requiredLicenseType);
	public boolean checkTruckExist(int truckNumber);	//Was checkTruckLicenceExist
	public boolean updateTruck(int truckNumber, String model, double weight, double maxWeight, int requiredLicenseType);
	public Truck fetchTruck(int truckNumber);
	public boolean deleteTruck(int truckNumber);
	//*******************//
	
	//***** Drivers *****//
	public boolean insertDriver(Driver driver);
	public boolean checkDriverExist(int ID);		//Was checkDriverIdExist
	//public boolean updateDriver(int ID, String firstName, String lastName, int licenseType);
	public Driver fetchDriver(int ID);
	public boolean deleteDriver(int ID);
	//*******************//
	
	//***** Sites *****//
	public boolean createSite(String address, String phoneNumber, String contactName, int areaCode);		
	public boolean updateSite(String address, String phoneNumber, String contactName, int areaCode);
	public Site fetchSite(String address);
	public boolean deleteSite(String address);
	//*****************//
	
	//***** Transports *****//
	public boolean createTransport(String date, String time, int truckNumber, int driverID, /*String source*/int companyID,
							double weight, int sourceDoc,  String address);
	public Transport fetchTransport(String date, String time, int numberOfTruck);
	public boolean updateTransport(String date, String time, int truckNumber, int driverID, /*String source*/int companyID,
							double weight, int sourceDoc, String address);
/*	public boolean deleteTransport(String date, String time, int truckNumber);*/
	public boolean addSiteToTransport(String date, String time, int truckNumber, int docCode, String hourOfArr) throws Exception;
	/*public boolean deleteSiteFromTransport(String date, String time, int truckNumber, String siteAddress);
*/	public Vector<Pair<String, String>> getTransportDests(String date, String hour, int truckNumber);
	//**********************//
	
	public Vector<Driver> fetchAvailableDrivers(String addressStore, String date, String time);
	public Vector<Pair<String,String>> getHoursOfArrival(Transport trans);
	public boolean checkIfTruckAvilable(String date,String time,int truckNumber);
	public boolean checkLicenceAndWeight(int driverId, int truckNo, double weight);
	
	
	/***/
	public Vector<Integer> getOrdersInTransport(Transport trans);
	public String getArrivalTime(int orderNumber, Transport transport);
}
