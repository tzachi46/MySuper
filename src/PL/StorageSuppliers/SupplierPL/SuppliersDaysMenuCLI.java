package PL.StorageSuppliers.SupplierPL;

import java.util.Scanner;

import BL.StorageSuppliers.BLSupplier;
import SharedClasses.StorageSuppliers.Supplier;

public class SuppliersDaysMenuCLI {
	
	private static SuppliersDaysMenuCLI instance;
	
	public static SuppliersDaysMenuCLI getInstance() {
		if(instance==null)
			instance=new SuppliersDaysMenuCLI();
		return instance;
	}
	
	private SuppliersDaysMenuCLI() {	}
	
	public void Start(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Supplier's Days Menu:\n1. Get all arrival days of a Supplier \n2. Add arrival day of a Supplier \n3. delete arrival day of a Supplier \n~. Back to Supplier Menu. ");
			String s=in.nextLine();
			while(!cheackCLIMainInput(s)){
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			switch (s.charAt(0)){
				case ('1'):{
					getArrivalDaysOfASupplier(in);
					break;
				}
				case('2'):{
					addArrivalDaysOfASupplier(in);
					break;
				}
				case('3'):{
					removeArrivalDaysOfASupplier(in);
					break;
				}
				case('~'):{
					EndOfPage=true;
					break;
				}
			}
		}
	}
	
	private void getArrivalDaysOfASupplier(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Get arrival days of a Supplier menu:");
			int supplierId = SupplierMenuCLI.receiveSupplierId(in);
			if(supplierId==-1)
				return;
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(supplierId)){
				System.out.println("The Supplier "+supplierId+" is not exist" );
				supplierId = SupplierMenuCLI.receiveSupplierId(in);
				if(supplierId==-1)
					return;
			}
			int[] days =BLSupplier.GetBLSupplier().getDaysOfSupplier(supplierId);
			String temp="";
			String[] DaysSamples={"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

			for(int i=0;i<days.length;i++){
				temp+=DaysSamples[days[i]-1]+ " ";
			}
			if(days.length>0)	
				System.out.println("The days are: "+temp);
			else{
				System.out.println("There are no arrival days for this supplier\n");
			}
		}
	}
	
	private void addArrivalDaysOfASupplier(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Add arrival days of a Supplier menu:");
			int supplierId = SupplierMenuCLI.receiveSupplierId(in);
			if(supplierId==-1)
				return;
			Supplier s =BLSupplier.GetBLSupplier().getSupplier(supplierId);
			while(s==null||s.getDelivery()==0){
				if(s==null)
					System.out.println("The Supplier "+supplierId+" is not exist" );
				else{
					System.out.println("The Supplier "+supplierId+" has no Delivery agreement");
				}
				supplierId = SupplierMenuCLI.receiveSupplierId(in);
				if(supplierId==-1)
					return;
				s =BLSupplier.GetBLSupplier().getSupplier(supplierId);
			}
			String day=getDay(in);
			if(day.equals("~"))
				return;
			BLSupplier.GetBLSupplier().addDayOfSupplier(supplierId, Integer.parseInt(day));
			System.out.println("Supplier's arival Days list has been updated\n");
		}
	}
	
	private void removeArrivalDaysOfASupplier(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Delete arrival days of a Supplier menu:");
			int supplierId = SupplierMenuCLI.receiveSupplierId(in);
			if(supplierId==-1)
				return;
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(supplierId)){
				System.out.println("The Supplier "+supplierId+" is not exist" );
				supplierId = SupplierMenuCLI.receiveSupplierId(in);
				if(supplierId==-1)
					return;
			}
			String day=getDay(in);
			if(day.equals("~"))
				return;
			BLSupplier.GetBLSupplier().removeDayOfSupplier(supplierId, Integer.parseInt(day));
			System.out.println("Supplier's arival Days list has been updated");
		}
	}
	
	private String getDay(Scanner in){
		System.out.println("Enter a delivery day(a number between 1 to 7)\nIf you wish to return to the Supplier's Days Menu press ~");
		String s=in.nextLine();
		while((s.length()!=1 || s.charAt(0)>'7'||s.charAt(0)<'1')&&!s.equals("~")){
			System.out.println("Illigal day");
			System.out.println("Enter a delivery day(a number between 1 to 7)\nIf you wish to return to the Supplier's Days Menu press ~");
			s=in.nextLine();
		}
		return s;
	}
	
	private boolean cheackCLIMainInput(String s){
		if(s.length()!=1)
			return false;
		if(s.charAt(0)=='~')
			return true;
		if(s.charAt(0)<'1'||s.charAt(0)>'3')
			return false;
		return true;
	}

}
