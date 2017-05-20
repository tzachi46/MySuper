package SharedClasses.TransportsEmployess;

public class Truck {
	private int truckNo;
	private String model;
	private double weight;
	private double maxWeight;
	private int licenceType;
	public Truck(int truckNo, String model, double weight, double maxWeight, int licenceType) {
		super();
		this.truckNo = truckNo;
		this.model = model;
		this.weight = weight;
		this.maxWeight = maxWeight;
		this.licenceType = licenceType;
	}
	
	//Getters
	public int getTruckNo() {
		return truckNo;
	}
	public int getLicenceType() {
		return licenceType;
	}
	public String getModel() {
		return model;
	}
	public double getWeight() {
		return weight;
	}
	public double getMaxWeight() {
		return maxWeight;
	}
	
	//Setters
	
	public void setModel(String model)
	{
		this.model = model;
	}
	
	public void setWeight(double weight)
	{
		this.weight = weight;
	}
	
	public void setMaxWeight(double maxWeight)
	{
		this.maxWeight = maxWeight;
	}
	
	public void setLicenceType(int licenceType)
	{
		this.licenceType = licenceType;
	}
	
	@Override
	public String toString() {
		return "Truck's Details: \nTruck's Number : "+ truckNo + "\nModel : "+ model +
				"\nTruck's Weight : "+weight+" kg \nTruck's Maximum Weight : "+maxWeight+
				" kg \nLicence Type : "+licenceType;
	}
	
	
}
