package SharedClasses.TransportsEmployess;

public class TransportDestination {
	
	private int truckNo;
	private String dateOfDep;
	private String hourOfDep;
	private String destination;
	private int docCode;
	private String hourOfArr;
	public TransportDestination(int truckNo, String dateOfDep, String hourOfDep, String destination, int docCode, String hourOfArr) {
		super();
		this.truckNo = truckNo;
		this.dateOfDep = dateOfDep;
		this.hourOfDep = hourOfDep;
		this.destination = destination;
		this.docCode = docCode;
		this.hourOfArr = hourOfArr;
	}
	public int getTruckNo() {
		return truckNo;
	}
	public String getDestination() {
		return destination;
	}
	public int getDocCode() {
		return docCode;
	}
	public String getDateOfDep() {
		return dateOfDep;
	}
	public String getHourOfDep() {
		return hourOfDep;
	}
	public String getHourOfArr() {
		return hourOfArr;
	}
	
	@Override
	public String toString() {
		return "Transport's Destination's Details: \n Truck's number : "+ truckNo + "\n Time of Departure : "+ dateOfDep + " "+ hourOfDep +
				"\n Transport document's code : "+docCode;
	}
}
