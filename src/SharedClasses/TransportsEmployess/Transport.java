package SharedClasses.TransportsEmployess;

public class Transport {
	@Override
	public String toString() {
		return "Transport's Details: \nTime of Departure : "+ dateOfDep +" "+hourOfDep + "\nTruck's Number : "+ truckNo +
				"\nDriver's ID : "+driverID+"\nSource's company ID : "+companyID+"\nTransport's Weight : "+ Weight+" kg\n"
				+ "sourceDoc: " + sourceDoc;
	}
	private int driverID;
	private int truckNo;
	private int companyID;
	/*private String addressOrign;*/
	private String dateOfDep;
	private String hourOfDep;
	private int sourceDoc;
	private double Weight;
	
	public Transport(int driverID, int truckNo, int companyID/*String addressOrign*/,
			String dateOfDep, String hourOfDep, double Weight, int sourceDoc) {
		super();
		this.driverID = driverID;
		this.truckNo = truckNo;
		//this.addressOrign = addressOrign;
		this.companyID = companyID;
		this.dateOfDep = dateOfDep;
		this.hourOfDep = hourOfDep;
		this.sourceDoc = sourceDoc;
		this.Weight = Weight;
	}
	public int getSourceDoc(){
		return sourceDoc;
	}
	public int getDriverID() {
		return driverID;
	}
	public int getTruckNo() {
		return truckNo;
	}
//	public String getAddressOrign() {
//		return addressOrign;
//	}
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
		return truckWeight;
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
	public void setSourceDoc(int sourceDoc) {
		this.sourceDoc = sourceDoc;
	}
	public void setTruckWeight(double truckWeight) {
		this.truckWeight = truckWeight;
	}
	public int getCurrentWeight() 
	{
		return Weight;
	}
	
}
