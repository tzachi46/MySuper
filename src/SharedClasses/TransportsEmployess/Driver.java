package SharedClasses.TransportsEmployess;

public class Driver extends Employee {
	private int lno;

	public Driver(int id, String fname, String lname, double salary, String startDate, String endDate,
			String bankAccount, Rank rank, String workAddress,int dayOfRest, int lno) {
		super(id, fname, lname, salary, startDate, endDate, bankAccount, rank, workAddress,dayOfRest);
		this.lno = lno;
		
	}
	

	@Override
	public String toString() {
		return "Driver's Details: \n [id=" + this.getId() + ", fname=" + this.getFname() + ", lname=" + this.getLname() + 
				", salary=" + this.getSalary() + ", startDate=" + this.getStartDate() 
				+ ", endDate=" + this.getEndDate() + ", bankAccount=" + this.getBankAccount() 
				+ ", rank=" + this.getRank() + "licence number=" + lno + "]";
	}

	public int getLno() {
		return lno;
	}

}
