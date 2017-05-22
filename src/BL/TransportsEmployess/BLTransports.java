package BL.TransportsEmployess;

import java.util.Vector;

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
	public boolean createSite(String address, String phoneNumber, String contactName, int isStore, int areaCode);		
	public boolean updateSite(String address, String phoneNumber, String contactName, int areaCode);
	public Site fetchSite(String address);
	public boolean deleteSite(String address);
	//*****************//
	
	//***** Transports *****//
	public boolean createTransport(String date, String time, int truckNumber, int driverID, String source,
							double weight, int sourceDoc);
	public Transport fetchTransport(String date, String time, int numberOfTruck);
	public boolean updateTransport(String date, String time, int truckNumber, int driverID, String source,
							double weight, int sourceDoc);
	public boolean deleteTransport(String date, String time, int truckNumber);
	public boolean addSiteToTransport(String date, String time, int truckNumber, String siteAddress, int docCode, String hourOfArr) throws Exception;
	public boolean deleteSiteFromTransport(String date, String time, int truckNumber, String siteAddress);
	public String getTransportDests(String date, String hour, int truckNumber);
	//**********************//
	
	public Vector<Driver> fetchAvailableDrivers(String addressStore, String date, String time);
	public Vector<String> getHoursOfArrival(Transport trans);
	public boolean checkIfTruckAvilable(String date,String time,int truckNumber);
	public boolean checkLicenceAndWeight(int driverId, int truckNo, double weight);
}
