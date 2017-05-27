package SharedClasses.TransportsEmployess;

public class Employee 
{
	//public enum Rank {logisticManeger,humenResourceManeger, shiftManager, regular, storeManeger}
	public enum Rank {logisticManager,humenResourceManager, regular, storeManager}
	
	private int id;
	private String fname;
	private String lname;
	private double salary;
	private String startDate;
	private String endDate;
	private String bankAccount;
	private Rank rank;
	private String workAddress;
	private int dayOfRest;
	
	public Employee(int id, String fname, String lname, double salary, String startDate, String endDate,
			String bankAccount, Rank rank, String workAddress,int dayOfRest) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.salary = salary;
		this.startDate = startDate;
		this.endDate = endDate;
		this.bankAccount = bankAccount;
		this.rank = rank;
		this.workAddress = workAddress;
		this.dayOfRest = dayOfRest;
	}

	public int getDayOfRest() {
		return dayOfRest;
	}

	public void setDayOfRest(int dayOfRest) {
		this.dayOfRest = dayOfRest;
	}

	public String getWorkAddress(){
		return workAddress;
	}
	public int getId() {
		return id;
	}
	public String getFname() {
		return fname;
	}
	public String getLname() {
		return lname;
	}
	public double getSalary() {
		return salary;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public Rank getRank() {
		return rank;
	}
	
	

	@Override
	public String toString() {
		return "Employee [id=" + id + ", fname=" + fname + ", lname=" + lname + ", salary=" + salary + ", startDate="
				+ startDate + ", endDate=" + endDate + ", bankAccount=" + bankAccount + ", rank=" + rank + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}


	public void setSalary(double salary) 
	{
		this.salary = salary;
	}

	public void setBankAccount(String bankAccount)
	{
		this.bankAccount = bankAccount;	
	}

	public void setEndOfEmploymentDate(String endDate) 
	{
		this.endDate = endDate;
	}
}
