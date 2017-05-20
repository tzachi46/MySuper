package PL.StorageSuppliers.SupplierPL;

import java.util.Scanner;

import BL.StorageSuppliers.BLSupplier;
import BL.StorageSuppliers.Calculations;
import SharedClasses.StorageSuppliers.SupplierContact;

public class ContactsMenuCLI {

	private static ContactsMenuCLI instance;
	private Calculations calc;
	public static ContactsMenuCLI getInstance() {
		if(instance==null)
			instance=new ContactsMenuCLI();
		return instance;
	}
	
	private ContactsMenuCLI() {
		calc=Calculations.GetCalculations();
	}
	
	public void Start(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Supplier's Contact Menu:\n1. Get All Contact Of Supplier \n2. Add A Contact \n3. Remove A Contact \n4. Update A Contact \n~. Back to Supplier Menu. ");
			String s=in.nextLine();
			while(!cheackCLIMainInput(s)){
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			switch (s.charAt(0)){
				case ('1'):{
					getContacts(in);
					break;
				}
				case('2'):{
					addNewContact();
					break;
				}
				case('3'):{
					DeleteAContact(in);
					break;
				}
				case('4'):{
					UpdateContact(in);
					break;
				}
				case('~'):{
					EndOfPage=true;
					break;
				}
			}
		}
	}
	

	private void getContacts(Scanner in){
		int supplierId = SupplierMenuCLI.receiveSupplierId(in);
		if(supplierId==-1)
			return;
		while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(supplierId)){
			System.out.println("The Supplier "+supplierId+" is not exist" );
			supplierId = SupplierMenuCLI.receiveSupplierId(in);
			if(supplierId==-1)
				return;
		}
		SupplierContact[] contacts = BLSupplier.GetBLSupplier().getSupplierContacts(supplierId);
		String contactsView="";
		if(contacts.length==0)
			System.out.println("there is no Contacts for this Supplier\n");
		for (int i=0;i<contacts.length;i++)
			if (contacts[i]!=null)
				contactsView+=contacts[i].toString()+"\n";
		System.out.print(contactsView);
	}
	
	private void addNewContact(){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Add New Contact Menu");
			Scanner in=new Scanner(System.in);
			int supplierId = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(supplierId) & supplierId!=-1){
				System.out.println("The Supplier "+supplierId+" is not exist" );
				supplierId = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(supplierId==-1)
				return;
			String contactName = GetContactName(in);
			if(contactName.equals("~"))
				return;
	
			String phoneNumber = GetPhoneNumber(in);
			if(phoneNumber.equals("~"))
				return;
			
			String email = receiveEmailAddress(in);
			if(email.equals("~"))
				return;
			
			SupplierContact SC = new SupplierContact(supplierId, phoneNumber, contactName, email);
			BLSupplier.GetBLSupplier().addSupplierContact(SC);
			System.out.println("A new contact has been added\n");
		}

	}
	
	private void DeleteAContact(Scanner in){
		int supplierId = receiveContactId(in);
		if(supplierId==-1)
			return;
		while(!BLSupplier.GetBLSupplier().verifyiedExsitingContact(supplierId)){
			System.out.println("The Contact "+supplierId+" is not exist" );
			supplierId = SupplierMenuCLI.receiveSupplierId(in);
		}
		BLSupplier.GetBLSupplier().removeSupplierContact(supplierId);
		System.out.println("The Contact has been removed successfully");
	}
	
	private void UpdateContact(Scanner in){
		System.out.println("Update Contact Menu:");
		int supplierId = receiveContactId(in);
		if(supplierId==-1)
			return;
		while(!BLSupplier.GetBLSupplier().verifyiedExsitingContact(supplierId)){
			System.out.println("The Contact "+supplierId+" is not exist" );
			supplierId = SupplierMenuCLI.receiveSupplierId(in);
		}
		SupplierContact SC= BLSupplier.GetBLSupplier().getASupplierContact(supplierId);
		EditAContact(in,SC);
		
	}
	
	private void EditAContact(Scanner in,SupplierContact SC){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("1. Update SupplierId Number.\n2. Update Phone Number.\n3. Update Name.\n4. Update Email.\n~. Back to Contacts Menu.");
			String s2=in.nextLine();
			while(!calc.checkKeyboardMenu(s2, 1, 4)&&!s2.equals("~")){
				System.out.println("Illegal input, please try again.");
				s2=in.nextLine();
			}
			switch (s2.charAt(0)){
				case ('1'):{
					int temp = SupplierMenuCLI.receiveSupplierId(in);
					while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(temp) & temp!=-1){
						System.out.println("The Supplier "+temp+" is not exist" );
						temp = SupplierMenuCLI.receiveSupplierId(in);
					}
					if(temp==-1)
						EndOfPage=true;
					else
						SC.setCompanyId(temp);
					break;
				}
				case('2'):{
					
					String phoneNumber = GetPhoneNumber(in);
					if(phoneNumber.equals("~"))
						EndOfPage=true;
					else
						SC.setPhone(phoneNumber);
					break;
				}
				case('3'):{
					String contactName = GetContactName(in);
					if(contactName.equals("~"))
						EndOfPage=true;
					else
						SC.setName(contactName);
					break;
				}
				case('4'):{
					String email = receiveEmailAddress(in);
					if(email.equals("~"))
						EndOfPage=true;
					else
						SC.setEmail(email);
					break;
				}
				case('~'):{
					EndOfPage=true;
					break;
				}
			}
			BLSupplier.GetBLSupplier().updateSupplierContact(SC);
		}
	}
	
	private boolean cheackCLIMainInput(String s){
		if(s.length()!=1)
			return false;
		if(s.charAt(0)=='~')
			return true;
		if(s.charAt(0)<'1'||s.charAt(0)>'4')
			return false;
		return true;
	}
	
	private int receiveContactId(Scanner in){
		System.out.println("enter contact's Id: \nIf you wish to return to the Contact Menu press ~");
		String s= in.nextLine();
		while((!SupplierMenuCLI.checkOnlyNumbers(s)) & !s.equals("~")){
			System.out.println("Illigal contact's Id");
			System.out.println("enter contact's Id: \nIf you wish to return to the Contact Menu press ~");
			s=in.nextLine();
		}
		if(s.charAt(0)=='~')
			return -1;
		return Integer.parseInt(s);

	}
	
	private String GetContactName(Scanner in){
		System.out.println("enter contact's name: \nIf you wish to return to the Contact Menu press ~");
		String s= in.nextLine();
		while(!receiveLegalLetterString(s) & !s.equals("~")){
			System.out.println("Illigal Name");
			System.out.println("enter contact's name: \nIf you wish to return to the Contact Menu press ~");
			s=in.nextLine();
		}
		return s;
	}
	
	private String receiveEmailAddress(Scanner in){
		System.out.println("enter contact's Email adress: \nIf you wish to return to the Contact Menu press ~");
		String s= in.nextLine();
		while(!s.contains("@") & !s.equals("~")){
			System.out.println("Illigal Email");
			System.out.println("eneter contact's Email adress: \nIf you wish to return to the Contact Menu press ~");
			s=in.nextLine();
		}
		return s;
	}
	
	
	private String GetPhoneNumber(Scanner in){
		System.out.println("enter contact's phone number: \nIf you wish to return to the Contact Menu press ~");
		String s= in.nextLine();
		while((!SupplierMenuCLI.checkOnlyNumbers(s) | s.length()!=10) & !s.equals("~")){
			System.out.println("Illigal Phone Number");
			System.out.println("enter contact's phone number: \nIf you wish to return to the Contact Menu press ~");
			s=in.nextLine();
		}
		return s;

	}
	
	static boolean receiveLegalLetterString(String s){
		if(s.equals(""))
			return false;
		for(int i=0;i<s.length();i++){
			if(s.charAt(i)==' ')
				continue;
			if((s.charAt(i)<'A'|s.charAt(i)>'z'))
				return false;
			if(s.charAt(i)>'Z'&s.charAt(i)<'a')
				return false;
		}
		return true;
	}
}
