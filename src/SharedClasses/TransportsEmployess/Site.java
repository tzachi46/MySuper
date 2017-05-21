package SharedClasses.TransportsEmployess;

public class Site {
	@Override
	public String toString() {
			return "Site's Details: \nAddress : "+ address + "\nContact's First Name : "+ contactName +
				"\nPhone Number : "+phoneNum+"\nArea Code : "+areaCode;
	}
	private String address;
	private String contactName;
	private String phoneNum;
	private int areaCode;

	public Site(String address, String phoneNum, String contactName, int areaCode) {
		super();
		this.address = address;
		this.contactName = contactName;
		this.phoneNum = phoneNum;
		this.areaCode = areaCode;
	}
	//Getters
	public int getAreaCode(){
		return areaCode;
	}
	public String getAddress() {
		return address;
	}
	public String getContactName() {
		return contactName;
	}
	public String getPhoneNum() {
		return phoneNum;
	}

	//Setters
	public void setContanName(String contactName) 
	{
		this.contactName = contactName;
		
	}

	public void setAreaCode(int areaCode) 
	{
		this.areaCode = areaCode;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
}
