package PL.TransportsEmployess;

import java.util.Scanner;

import BL.TransportsEmployess.*;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.Site;
import SharedClasses.TransportsEmployess.Employee.Rank;

public class PL_SitesEdit 
{
	private BL bl;
	private Scanner scanner;
	private Validator validator;
	PL_Shared pl_shared;

	public PL_SitesEdit(BL bl, Scanner scanner, Validator validator,PL_Shared pl_Shared)
	{
		this.bl = bl;
		this.scanner = scanner;
		this.validator = validator;
		this.pl_shared = pl_Shared;
	}
	
	public void workOnSites() 
	{
		while(true)	
		{
			System.out.println("Which of the following operations you wish to do :");
			System.out.println("1) Insert new Site.");
			System.out.println("2) Update existing Site's details.");
			System.out.println("3) Remove site from the database.");
			System.out.println("4) Fetch site's details from the database.");
			System.out.println("5) Return to Main Menu.");
	
			String choice = scanner.nextLine();
			
			 if(!HandeleSiteOperationChoice(choice))
	                break;
		}
	}
	private boolean HandeleSiteOperationChoice(String choice)
	{
		switch(choice) 
		{
			case "1":
					insertSite();
					break;
			case "2":
					updateSite();
					break;
			case "3":
					deleteSite();
					break;
			case "4":
					fetchSite();
					break;
			case "5":
					return false;
			default:
	        {
	            System.out.println("Invalid input, try again");
	        	break;
	        }
		}
		return true;
	}
	
	
	private String getPhoneNumberFromUser()
	{
		String phoneNum;
		System.out.println("Please insert phone number: ");
		phoneNum = scanner.nextLine();
		while(!validator.validatePhoneNumber(phoneNum)) 
		{
			if(phoneNum.equals("0"))
				return phoneNum;
			System.out.println("phone number is not valid, try again");
			phoneNum = scanner.nextLine();
		}
		return phoneNum;
	}
	
	private String getNameFromUser()
	{
		String name;
		System.out.println("Please insert name: ");
		name = scanner.nextLine();
		while(!validator.validateName(name)) 
		{
			if(name.equals("0"))
				return name;
			System.out.println("name is not valid, try again");
			name = scanner.nextLine();
		}
		return name;
	}
	
	private String getAreaCodeFromUser()
	{
		String areaCode;
		System.out.println("Please insert area code: ");
		areaCode = scanner.nextLine();
		while(!validator.validateIntInBounds(areaCode,0,1000)) 
		{
			if(areaCode.equals("0"))
				return areaCode;
			System.out.println("area code is not valid, try again");
			areaCode = scanner.nextLine();
		}
		return areaCode;
	}
	
	private Employee getValidAdmin(String address)
	{
		System.out.println("At any point you can press 0 to return to previous menu");
        System.out.println("Enter the details of the human resource manger of the store:");
        String id = pl_shared.getNotExistingId();
        if(id.equals("0"))
        	return null;
        String fisrt_name = pl_shared.getName("first name");
        if(fisrt_name.equals("0"))
        	return null;
        String last_name = pl_shared.getName("last_name");
        if(last_name.equals("0"))
        	return null;	
        String salery = pl_shared.getsalary();
        if(salery.equals("0"))
        	return null;	 
        String BankAccount = pl_shared.getBankDetails();
        if(BankAccount.equals("0"))
        	return null;	 
        int day = pl_shared.GetDay("rest day");
        if(day == 0)
        	return null;
        String currentDate = pl_shared.getCurrentDate(); 
        return new Employee(Integer.parseInt(id),fisrt_name,last_name,Double.parseDouble(salery),currentDate,"",BankAccount,(Rank.storeManeger),address,day);
	}
	
//	//For site's isStore
//	private int getStoreOrSupplierFromUser()
//	{
//		String option;
//		System.out.println("choose Option:");
//		System.out.println("1)Store");
//		System.out.println("2)Supplier");
//		
//		option = scanner.nextLine(); 
//		while(!option.equals("1") && !option.equals("2"))
//		{
//			if(option.equals("0"))
//				return -1; 
//			System.out.println("area code is not valid, try again");
//			option = scanner.nextLine();
//		}
//		if(option.equals("1"))
//			return 1;
//		return 0;
//	}

	
	private void insertSite() 
	{
		Employee admin = null;
		String address, phoneNum, contact, areaCode;
		int isStore;
		System.out.println("Please insert the Site's details : ");
		address = pl_shared.getNotExistAddressFromUser();	
		if(address.equals("0"))
			return;
		phoneNum = getPhoneNumberFromUser();
		if(phoneNum.equals("0"))
			return;	
		contact = getNameFromUser();
		if(contact.equals("0"))
			return;	
		areaCode = getAreaCodeFromUser();
		if(contact.equals("0"))
			return;	
		/* Store or Supplier */
//		isStore = getStoreOrSupplierFromUser();
//		if(isStore == -1)
//			return;
//		if(isStore == 1)
//		{
			admin = getValidAdmin(address);
			if(admin == null)
				return;
//		}
		if(bl.createSite(address, phoneNum, contact,/* isStore,*/ Integer.parseInt(areaCode)))
		{
//			if(isStore == 1)
//			{
				bl.addEmployee(String.valueOf(admin.getId()), String.valueOf(admin.getSalary()), admin.getFname(), admin.getLname(), admin.getStartDate(), admin.getEndDate(), admin.getRank().toString(), admin.getBankAccount(), admin.getWorkAddress(),admin.getDayOfRest());
				pl_shared.makeDayOfWorkUnavilable(admin.getId(),admin.getDayOfRest());
//			}
			System.out.println("Site created successfully.");
		}
		else 
			System.out.println("Unfortunately the system couldnt create the site in the data base.");	
	}
	
	private void updateSite() 
	{
		String address;
		while (true)
		{
			address = pl_shared.getExistAddressFromUser(2);
			if(address.equals("0"))
				break;
			Site site = bl.fetchSite(address);
			System.out.println("Choose option:");
			System.out.println("1)Updete phone number");
			System.out.println("2)Updete contant name");
			System.out.println("3)Updete area code");
			System.out.println("4)return to previous menu");
				
			String option = scanner.nextLine();
			if(!handleSiteUpdate(option,site))
				return;
		}
	}
	
	private boolean handleSiteUpdate(String option, Site site)
	{
		switch (option)
	    {
	        case "1":
					updatePhoneNumber(site);
					break;
			case "2":
					updateContantName(site);
					break;
			case "3":
					updateAreaCode(site);
					break;
			case "4":
					return false;
	        default:
	        {
	            System.out.println("Invalid input, try again");
	        	break;
	        }
	    }
	 	return true;
	}

	private void updateAreaCode(Site site) 
	{
		String areaCode = getAreaCodeFromUser();
		if(areaCode.equals("0"))
			return;
		site.setAreaCode(Integer.parseInt(areaCode));
		commitUpdate(site);
	}

	private void updateContantName(Site site) 
	{
		String contant = getNameFromUser();
		if(contant.equals("0"))
			return;
		site.setContanName(contant);
		commitUpdate(site);	
	}

	private void updatePhoneNumber(Site site) 
	{
		String phoneNum = getPhoneNumberFromUser();
		if(phoneNum.equals("0"))
			return;
		site.setPhoneNum(phoneNum);
		commitUpdate(site);
	}

	public void commitUpdate(Site site)
	{
		if(bl.updateSite(site.getAddress(), site.getPhoneNum(), site.getContactName(), site.getAreaCode()))
			System.out.println("Site updated successfully.");
		else 
			System.out.println("Unfortunately the system couldnt create the site in the data base.");
	}
	
	
	private void deleteSite() 
	{
		String address = pl_shared.getExistAddressFromUser(2);
		if(address.equals("BasePoint"))
		{
			System.out.println("cant delete the main store!");
			return;
		}
		if(bl.deleteSite(address))
			System.out.println("Site was deleted successfully!");
		else
			System.out.println("Something went wrong during the deletion of the site, sorry...");
	}
	private void fetchSite() 
	{
		String address = pl_shared.getExistAddressFromUser(2);
		if(address.equals("0"))
			return;
		Site site = bl.fetchSite(address);
		System.out.println(site.toString());
	}	
}