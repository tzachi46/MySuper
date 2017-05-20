package SharedClasses.TransportsEmployess;

public class EmployeeRestriction {
	private  int id;
	private int day;
	private String type;
	
	public EmployeeRestriction(int id, int day, String type)
	{
		this.id = id;
		this.day = day;
		this.type = type;
	}
	
	public int getId() {

		return id;
	}
	public int getDay() {
		return day;
	}
	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "EmployeeRestriction [id=" + id + ", day=" + day + ", type=" + type + "]";
	}
	
}
