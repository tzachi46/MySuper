package SharedClasses.TransportsEmployess;

public class Site {
	@Override
	public String toString() {
		if(isStore==1)
			return "Site's Details: \nAddress : "+ address + "\nContact's First Name : "+ contactName +
				"\nPhone Number : "+phoneNum+"\nStore or Supplier: Store \nArea Code : "+areaCode;
		else
			return "Site's Details: \nAddress : "+ address + "\nContact's First Name : "+ contactName +
					"\nPhone Number : "+phoneNum+"\nStore or Supplier: Supplier \nArea Code : "+areaCode;
	}
	private String address;
	private String contactName;
	private String phoneNum;
	private int isStore;
	private int areaCode;
	
	public Site(String address, String phoneNum, String contactName, int isStore, int areaCode) {
		super();
		this.address = address;
		this.contactName = contactName;
		this.phoneNum = phoneNum;
		this.isStore = isStore;
		this.areaCode = areaCode;
	}
	
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
	public int getIsStore(){
		return isStore;
	}

	//Setters
	public void setIsStore(int isStore) 
	{
		this.isStore = isStore;
	}

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
