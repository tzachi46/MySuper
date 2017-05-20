package SharedClasses.StorageSuppliers;

import java.util.LinkedList;
import java.util.List;

public class Supplier {
	
	private int CompanyId;
	private String name;
	private int BankAccountNumber;
	private int BankBranchId;
	private String TermsOfPayment;
	private int Delivery;
	private List<Integer> deliveryDays; 
	private String Address;
		
	public Supplier(int CompanyId,String name,int BankAccountNumber, int BankBranchId,String TermsOfPayment, int Delivery, String Address) throws Exception{
		if(!(checkTermsOfPayment(TermsOfPayment)) | Delivery>1 | Delivery<0)
			throw new Exception("arguments are illegal");
		this.setCompanyId(CompanyId);
		this.setBankAccountNumber(BankAccountNumber);
		this.setBankBranchId(BankBranchId);
		this.setTermsOfPayment(TermsOfPayment);
		this.setDelivery(Delivery);
		this.deliveryDays= new LinkedList<Integer>();
		this.name=name;
		this.setAddress(Address);
	}
	
	private boolean checkTermsOfPayment(String TermsOfPayment){
		if(TermsOfPayment.equals("Cash")|TermsOfPayment.equals("Credit Card")|TermsOfPayment.equals("Checks"))
			return true;
		return false;
	}
	
	public int getCompanyId() {
		return CompanyId;
	}

	public boolean setCompanyId(int companyId) {
		this.CompanyId = companyId;
		return true;
	}

	public int getBankAccountNumber() {
		return BankAccountNumber;
	}

	public void setBankAccountNumber(int bankAccountNumber) {
		BankAccountNumber = bankAccountNumber;
	}

	public int getBankBranchId() {
		return BankBranchId;
	}

	public void setBankBranchId(int bankBranchId) {
		BankBranchId = bankBranchId;
	}

	public String getTermsOfPayment() {
		return TermsOfPayment;
	}

	public boolean setTermsOfPayment(String termsOfPayment) {
		if(!checkTermsOfPayment(termsOfPayment))
			return false;
		TermsOfPayment = termsOfPayment;
		return true;
	}

	public int getDelivery() {
		return Delivery;
	}

	public void setDelivery(int delivery) {
		if(delivery==1 || delivery==0)
			this.Delivery = delivery;
	}


	public List<Integer> getDeliveryDays() {
		return deliveryDays;
	}


	public void setDeliveryDays(List<Integer> deliveryDays) {
		this.deliveryDays = deliveryDays;
	}
	
	public String toString(){
		String delivery = "PickUp";
		if (getDelivery()==1)
			delivery = "Has deliveries";
		return "Supplier details:\nSupplier ID: "+getCompanyId()+"\nSupplier Name:"+getName()+"\nAddress: "+Address+"\nBank Account Number: "+getBankAccountNumber()+"\nBank Branch Number: "+getBankBranchId()+"\nTerms Of Payment: "+getTermsOfPayment()+"\nDelivery services: "+delivery+"\n";
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}
}
