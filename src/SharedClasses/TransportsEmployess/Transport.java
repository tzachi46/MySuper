package SharedClasses.TransportsEmployess;


public class Transport {
	@Override
	public String toString() {
		return "Transport's Details: \nTime of Departure : "+ dateOfDep +" "+hourOfDep + "\nTruck's Number : "+ truckNo +
				"\nDriver's ID : "+driverID+"\nSource's company ID : "+companyID+"\nTransport's Weight : "+ Weight+" kg"
				;
	}
	private int driverID;
	private int truckNo;
	private int companyID;
	private String addressOrign;
	private String dateOfDep;
	private String hourOfDep;
	private double Weight;
	
	public Transport(int driverID, int truckNo, int companyID/*String addressOrign*/,
			String dateOfDep, String hourOfDep, double Weight, String addressOrign) 
	{
		this.driverID = driverID;
		this.truckNo = truckNo;
		//this.addressOrign = addressOrign;
		this.companyID = companyID;
		this.dateOfDep = dateOfDep;
		this.hourOfDep = hourOfDep;
		this.Weight = Weight;
		this.addressOrign = addressOrign;
	}
	public int getDriverID() {
		return driverID;
	}
	public int getTruckNo() {
		return truckNo;
	}
	public String getAddressOrign() {
		return addressOrign;
	}
	public int getCompanyID() {
		return companyID;
	}
	public String getDateOfDep() {
		return dateOfDep;
	}
	public String getHourOfDep() {
		return hourOfDep;
	}
	public double getTruckWeight() 
	{
		return (DAL.HR_TR.DALhrtrManager.getDALImp().fetchTruck(truckNo)).getMaxWeight();
	}
	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}
	public void setTruckNo(int truckNo) {
		this.truckNo = truckNo;
	}
//	public void setAddressOrign(String addressOrign) {
//		this.addressOrign = addressOrign;
//	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	public void setDateOfDep(String dateOfDep) {
		this.dateOfDep = dateOfDep;
	}
	public void setHourOfDep(String hourOfDep) {
		this.hourOfDep = hourOfDep;
	}
	
	public void setWeight(double Weight) {
		this.Weight = Weight;
	}
	public double getCurrentWeight() 
	{
		return Weight;
	}
	
}
