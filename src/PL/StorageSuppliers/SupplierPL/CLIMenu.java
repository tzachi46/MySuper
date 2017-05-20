package PL.StorageSuppliers.SupplierPL;

import java.util.Scanner;

public class CLIMenu {
	
	
	private static CLIMenu instance;
	
	public static CLIMenu getInstance() {
		if(instance==null)
			instance=new CLIMenu();
		return instance;
	}
	
	private CLIMenu() {	}
	
	public void Start(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Supplier Menu:\n1. Supplier \n2. Supplier's Contacts \n3. Supplier's arrival Days \n4. Supplier's Product \n5. Prices \n~. Back to main menu. ");
			String s=in.nextLine();
			while(!cheackCLIMainInput(s)){
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			switch (s.charAt(0)){
				case ('1'):{
					SupplierMenuCLI.getInstance().Start(in);
					break;
				}
				case('2'):{
					ContactsMenuCLI.getInstance().Start(in);
					break;
				}
				case('3'):{
					SuppliersDaysMenuCLI.getInstance().Start(in);
					break;
				}
				case('4'):{
					ProductsMenuCLI.getInstance().Start(in);
					break;
				}
				case('5'):{
					PricesMenuCLI.getInstance().Start(in);
					break;
				}
				case('~'):{
					EndOfPage=true;
					break;
				}
			}
		}
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



}
