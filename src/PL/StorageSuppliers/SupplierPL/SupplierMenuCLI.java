package PL.StorageSuppliers.SupplierPL;

import java.util.Scanner;

import BL.StorageSuppliers.BLSupplier;
import BL.StorageSuppliers.Calculations;
import SharedClasses.StorageSuppliers.Supplier;

public class SupplierMenuCLI {

	private static SupplierMenuCLI instance;
	private Calculations calc;
	public static SupplierMenuCLI getInstance() {
		if(instance==null)
			instance=new SupplierMenuCLI();
		return instance;
	}
	
	private SupplierMenuCLI() {	
		calc=Calculations.GetCalculations();
	}
	
	public void Start(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Supplier Menu:\n1. Add New Supplier \n2. Edit Supplier \n3. Remove a Supplier\n4. Get Supplier Details \n5. Get all suppliers\n~. Back to Supplier Menu. ");
			String s=in.nextLine();
			while(!cheackCLIMainInput(s) & !s.equals("~")){
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			switch (s.charAt(0)){
				case ('1'):{
					insertSupplierMenu();
					break;
				}
				case('2'):{
					updateSupplierMenu();
					break;
				}
				case('3'):{
					removeSupplier(in);
					break;
				}
				case('4'):{
					getSupplierMenu();
					break;
				}
				case('5'):{
					GetAllSupplier(in);
					break;
				}
				case('~'):{
					EndOfPage=true;
					break;
				}
			}
		}
	}
	
	private void GetAllSupplier(Scanner in){
		Supplier[] suppliers=BLSupplier.GetBLSupplier().getAllSupplier();
		if(suppliers.length==0)
			System.out.println("there is no supplier in the System");
		for(int i=0;i<suppliers.length;i++){
			System.out.println(suppliers[i]+"----------------------------------------");
		}
	}
	
	private void removeSupplier(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Remove a supplier menu:");
			int supplierId = receiveSupplierId(in);
			if(supplierId==-1)
				return;
			Supplier supplier=BLSupplier.GetBLSupplier().getSupplier(supplierId);
			if (supplier == null)
				System.out.println("no such supplier exists\n");
			else{
				BLSupplier.GetBLSupplier().removeSupplier(supplier.getCompanyId());
				System.out.println("The supplier has been removed\n");
			}
		}
	}
	
	private void getSupplierMenu(){
		Scanner in=new Scanner(System.in);
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Get Supplier details menu:");
			int supplierId = receiveSupplierId(in);
			if(supplierId==-1)
				return;
			Supplier supplier=BLSupplier.GetBLSupplier().getSupplier(supplierId);
			if (supplier == null)
				System.out.println("no such supplier exists");
			else
				System.out.println(supplier);
		}
	}

	private void updateSupplierMenu(){
		Scanner in=new Scanner(System.in);
		System.out.println("Update Supplier menu:");
		int supplierId = receiveSupplierId(in);
		if(supplierId==-1)
			return;
		Supplier supplier=BLSupplier.GetBLSupplier().getSupplier(supplierId);
		if (supplier == null)
		{
			System.out.println("was unable to get the wanted supplier");
			return;
		}
		updateSupplier(supplier);
	}

	
	private void insertSupplierMenu(){
		System.out.println("Insert supplier menu:");
		Scanner in=new Scanner(System.in);
		int supplierId = receiveSupplierId(in);
		if(supplierId==-1)
			return;
		
		while(BLSupplier.GetBLSupplier().getSupplier(supplierId)!=null){
			System.out.println("Illigal input : supplier "+supplierId+" allready exist\n");
			supplierId = receiveSupplierId(in);
			if(supplierId==-1)
				return;
		}
		String name= this.reciveSupplierName(in);
		if(name.equals("~"))
			return;
		
		int bankAccountNummber=receiveBankAccountNumber(in);
		if(bankAccountNummber==-1)
			return;
		int bankBranchNumber=receiveBankBranchNumber(in);
		if(bankBranchNumber==-1)
			return;
		String termsOfPayment =  receiveTermsOfPayment(in);
		if(termsOfPayment.equals("~"))
			return;
		int delivery=receiveDeliveryServices(in);
		if(delivery==-1)
			return;
		String Address = getSupplierAddress(in);
		if(Address.equals("~"))
			return;
				
		Supplier supplier = null;
		try {
			supplier = new Supplier(supplierId, name, bankAccountNummber, bankBranchNumber, termsOfPayment, delivery,Address);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BLSupplier.GetBLSupplier().addSupplier(supplier);
		System.out.println("A new Supplier has been saved");
	}
	
	private String getSupplierAddress(Scanner in){
		System.out.println("Enter supplier Address.\nIf you wish to return to the Supplier Menu press ~ ");
		String s=in.nextLine();
		while(s.equals("")){
			System.out.println("Illigal input");
			System.out.println("Enter supplier Address.\nIf you wish to return to the Supplier Menu press ~ ");
			s=in.nextLine();
		}
		return s;
	}
	
	private void updateSupplier(Supplier supplier){
		Scanner in=new Scanner(System.in);
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("1. Update Bank Account Number.\n2. Update Supplier's Name\n3. Update Bank Branch Number.\n4. Update Terms Of Payment.\n5. Update Delivery Services.\n6. Update Address\n~. Back to Supplier Menu.");
			String s2=in.nextLine();
			while(!calc.checkKeyboardMenu(s2, 1, 6)&&!s2.equals("~")){
				System.out.println("Illegal input, please try again.");
				s2=in.nextLine();
			}
			switch (s2.charAt(0)){
				case ('1'):{
					int temp=receiveBankAccountNumber(in);
					if(temp==-1)
						EndOfPage=true;
					else
						supplier.setBankAccountNumber(temp);
					break;
				}
				case('2'):{
					String temp=reciveSupplierName(in);
					if(temp.equals("~"))
						EndOfPage=true;
					else
						supplier.setName(temp);	
					break;
				}
				case('3'):{
					int temp=receiveBankBranchNumber(in);
					if(temp==-1)
						EndOfPage=true;
					else
						supplier.setBankBranchId(temp);	
					break;
				}
				case('4'):{
					String temp=receiveTermsOfPayment(in);
					if(temp.equals("~"))
						EndOfPage=true;
					else
						supplier.setTermsOfPayment(temp);
					break;
				}
				case('5'):{
					int temp=receiveDeliveryServices(in);
					if(temp==-1)
						EndOfPage=true;
					else
						supplier.setDelivery(temp);
					break;
				}
				case('6'):{
					String temp=getSupplierAddress(in);
					if(temp.equals("~"))
						EndOfPage=true;
					else
						supplier.setAddress(temp);
					break;
				}
				case('~'):{
					EndOfPage=true;
					break;
				}
			}
			BLSupplier.GetBLSupplier().updateSupplier(supplier);
		}
		
	}
	
	private String reciveSupplierName(Scanner in){
		String s;
		System.out.println("Enter supplier Name.\nIf you wish to return to the Supplier Menu press ~ ");
		s=in.nextLine();
		while(!ContactsMenuCLI.receiveLegalLetterString(s) && !s.equals("~")){
			System.out.println("Illigal supplier Name.");
			System.out.println("Enter supplier Name.\nIf you wish to return to the Supplier Menu press ~ ");
			s=in.nextLine();
		}
		return s;
	}
	
	static int receiveSupplierId(Scanner in){
		String s;
		System.out.println("Enter supplier ID.\nIf you wish to return to the Supplier Menu press ~ ");
		s=in.nextLine();
		while(!checkIDInput(s) && !s.equals("~")){
			System.out.println("Illigal ID");
			System.out.println("Enter supplier ID.\nIf you wish to return to the Supplier Menu press ~ ");
			s=in.nextLine();
		}	
		if(s.charAt(0)=='~')
			return -1;
		return Integer.parseInt(s);
	}
	
	private int receiveBankAccountNumber(Scanner in){
		String s;
		System.out.println("Enter Bank Account Number\nIf you wish to return to the Supplier Menu press ~");
		s=in.nextLine();
		while(!checkOnlyNumbers(s) & !s.equals("~")){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}
		if(s.equals("~"))
			return -1;
		return Integer.parseInt(s);
	}
	
	private int receiveBankBranchNumber(Scanner in){
		String s;
		System.out.println("Enter Bank Branch Number\nIf you wish to return to the Supplier Menu press ~");
		s=in.nextLine();
		while(!checkOnlyNumbers(s) & !s.equals("~")){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}
		if(s.equals("~"))
			return -1;
		return Integer.parseInt(s);
	}
	
	private String receiveTermsOfPayment(Scanner in){
		String s;
		System.out.println("Enter Terms Of Payment: Cash / Credit Card / Checks\nIf you wish to return to the Supplier Menu press ~");
		s=in.nextLine();
		while(!s.equals("Cash")&!s.equals("Credit Card")&!s.equals("Checks") & !s.equals("~")){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}
		return s;
	}
	
	private int receiveDeliveryServices(Scanner in){
		String s;
		System.out.println("Does the supplier has delivery services, enter yes/no\nIf you wish to return to the Supplier Menu press ~");
		s=in.nextLine();
		while(!s.equals("yes")& !s.equals("no") & !s.equals("~")){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}
		if(s.equals("~"))
			return -1;
		if(s.equals("no"))
			return 0;
		return 1;
	}
	
	
	
	//help functions
	
	static boolean checkOnlyNumbers(String s){
		if(s.equals(""))
			return false;
		String temp="0123456789";
		for(int i=0;i<s.length();i++){
			if(temp.indexOf(s.charAt(i))==-1)
				return false;
		}
		return true;
	}
	
	private boolean cheackCLIMainInput(String s){
		
		if(s.length()!=1)
			return false;
		if(s.charAt(0)=='~')
			return true;
		if(s.charAt(0)<'1'||s.charAt(0)>'5')
			return false;
		return true;
	}

	
	private static boolean checkIDInput(String s){
		if(s.equals(""))
			return false;
		if(checkOnlyNumbers(s))
			return true;
		return false;
	}

	
}
