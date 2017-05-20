package SharedClasses.StorageSuppliers;


public class SupplierContact {
	private int id;
	private int CompanyId;
	private String phone;
	private String name;
	private String Email;
	
	public SupplierContact(int CompanyId, String phone,String name ,String Email){
		this.setCompanyId(CompanyId);
		this.setPhone(phone);
		this.setName(name);
		this.setEmail(Email);
		this.id=-1;
	}
	

	public SupplierContact(int id,int CompanyId, String phone,String name ,String Email){
		this(CompanyId, phone, name , Email);
		this.id=id;
	}

	public int getCompanyId() {
		return CompanyId;
	}

	public void setCompanyId(int companyId) {
		CompanyId = companyId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) { //TODO: check validity of phone number
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return Email;
	}

	public boolean setEmail(String email) {//TODO: check input
		this.Email = email;
		return true;
	}


	public int getId() {
		return id;
	}
	
	public String toString(){
		String res = "";
		res+="ID = "+id+"\n";
		res+= "Supplier ID = "+ CompanyId+"\n";
		res+="PhoneNumber = "+ phone+"\n";
		res+="Name = "+name+"\n";
		res+="Email = "+Email+"\n";
		return res;
	}

}
