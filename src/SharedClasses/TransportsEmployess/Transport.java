package SharedClasses.TransportsEmployess;

public class Transport {
	@Override
	public String toString() {
		return "Transport's Details: \nTime of Departure : "+ dateOfDep +" "+hourOfDep + "\nTruck's Number : "+ truckNo +
				"\nDriver's ID : "+driverID+"\nSource's address : "+addressOrign+"\nTransport's Weight : "+ truckWeight+" kg\n"
				+ "sourceDoc: " + sourceDoc;
	}
	private int driverID;
	private int truckNo;
	private String addressOrign;
	private String dateOfDep;
	private String hourOfDep;
	private int sourceDoc;
	private double truckWeight;
	
	public Transport(int driverID, int truckNo, String addressOrign,
			String dateOfDep, String hourOfDep, double truckWeight, int sourceDoc) {
		super();
		this.driverID = driverID;
		this.truckNo = truckNo;
		this.addressOrign = addressOrign;
		this.dateOfDep = dateOfDep;
		this.hourOfDep = hourOfDep;
		this.sourceDoc = sourceDoc;
		this.truckWeight = truckWeight;
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
	public String getAddressOrign() {
		return addressOrign;
	}
	public String getDateOfDep() {
		return dateOfDep;
	}
	public String getHourOfDep() {
		return hourOfDep;
	}
	public double getTruckWeight() {
		return truckWeight;
	}
	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}
	public void setTruckNo(int truckNo) {
		this.truckNo = truckNo;
	}
	public void setAddressOrign(String addressOrign) {
		this.addressOrign = addressOrign;
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
	
}
