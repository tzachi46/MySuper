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
			System.out.println("1) Insert new store.");
			System.out.println("2) Update existing store's details.");
			System.out.println("3) Remove store from the database.");
			System.out.println("4) Fetch store's details from the database.");
			System.out.println("~) Return to Main Menu.");
	
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
			case "~":
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
			if(phoneNum.equals("~"))
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
			if(name.equals("~"))
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
			if(areaCode.equals("~"))
				return areaCode;
			System.out.println("area code is not valid, try again");
			areaCode = scanner.nextLine();
		}
		return areaCode;
	}
	
	private Employee getValidAdmin(String address,String job,String idPrev)
	{
		String id;
		System.out.println("At any point you can press ~ to return to previous menu");
        System.out.println("Enter the details of the "+ job + " of the store:");
        while (true)
        {
	        id = pl_shared.getNotExistingId();
	        if(id.equals("~"))
	        	return null;
	        if(!id.equals(idPrev))
	        	break;
	        else
	        	System.out.println("The id is equal to the id of the store maneger, try again.");
	    }
        String fisrt_name = pl_shared.getName("first name");
        if(fisrt_name.equals("~"))
        	return null;
        String last_name = pl_shared.getName("last name");
        if(last_name.equals("~"))
        	return null;	
        String salery = pl_shared.getsalary();
        if(salery.equals("~"))
        	return null;	 
        String BankAccount = pl_shared.getBankDetails();
        if(BankAccount.equals("~"))
        	return null;	 
        int day = pl_shared.GetDay("rest day");
        if(day == 0)
        	return null;
        String currentDate = pl_shared.getCurrentDate(); 
        return new Employee(Integer.parseInt(id),fisrt_name,last_name,Double.parseDouble(salery),currentDate,"",BankAccount,Rank.valueOf(job),address,day);
	}
	
	
	private void insertSite() 
	{
		Employee HR = null;
		Employee StoreManger = null;
		String address, phoneNum, contact, areaCode;
		System.out.println("Please insert the store's details : ");
		address = pl_shared.getNotExistAddressFromUser();	
		if(address.equals("~"))
			return;
		phoneNum = getPhoneNumberFromUser();
		if(phoneNum.equals("~"))
			return;	
		contact = getNameFromUser();
		if(contact.equals("~"))
			return;	
		areaCode = getAreaCodeFromUser();
		if(contact.equals("~"))
			return;	
		HR = getValidAdmin(address,"humenResourceManager","-1");
		if(HR == null)
			return;                          
		StoreManger = getValidAdmin(address,"storeManager",Integer.toString(HR.getId()));
		if(StoreManger == null)
			return;
		if(bl.createSite(address, phoneNum, contact, Integer.parseInt(areaCode)))
		{
			bl.addEmployee(String.valueOf(HR.getId()), String.valueOf(HR.getSalary()), HR.getFname(), HR.getLname(), HR.getStartDate(), HR.getEndDate(), HR.getRank().toString(), HR.getBankAccount(), HR.getWorkAddress(),HR.getDayOfRest());
			pl_shared.makeDayOfWorkUnavilable(HR.getId(),HR.getDayOfRest());
			bl.addEmployee(String.valueOf(StoreManger.getId()), String.valueOf(StoreManger.getSalary()), StoreManger.getFname(), StoreManger.getLname(), StoreManger.getStartDate(), StoreManger.getEndDate(), StoreManger.getRank().toString(), StoreManger.getBankAccount(), StoreManger.getWorkAddress(),StoreManger.getDayOfRest());
			pl_shared.makeDayOfWorkUnavilable(StoreManger.getId(),StoreManger.getDayOfRest());
			System.out.println("Store created successfully.");
		}
		else 
			System.out.println("Unfortunately the system couldn't create the store in the data base.");	
	}
	
	private void updateSite() 
	{
		String address;
		address = pl_shared.getExistStoreAddressFromUser();
		if(address.equals("~"))
			return;
	
		while (true)
		{	
			Site site = bl.fetchSite(address);
			System.out.println("Choose option:");
			System.out.println("1)Update phone number");
			System.out.println("2)Update contant name");
			System.out.println("3)Update area code");
			System.out.println("~)return to previous menu");
				
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
			case "~":
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
		if(areaCode.equals("~"))
			return;
		site.setAreaCode(Integer.parseInt(areaCode));
		commitUpdate(site);
	}

	private void updateContantName(Site site) 
	{
		String contant = getNameFromUser();
		if(contant.equals("~"))
			return;
		site.setContanName(contant);
		commitUpdate(site);	
	}

	private void updatePhoneNumber(Site site) 
	{
		String phoneNum = getPhoneNumberFromUser();
		if(phoneNum.equals("~"))
			return;
		site.setPhoneNum(phoneNum);
		commitUpdate(site);
	}

	public void commitUpdate(Site site)
	{
		if(bl.updateSite(site.getAddress(), site.getPhoneNum(), site.getContactName(), site.getAreaCode()))
			System.out.println("Store updated successfully.");
		else 
			System.out.println("Unfortunately the system couldnt create the store in the data base.");
	}
	
	
	private void deleteSite() 
	{
		String address = pl_shared.getExistStoreAddressFromUser()/*getExistAddressFromUser(2)*/;
		if(address.equals("BasePoint"))
		{
			System.out.println("cant delete the main store!");
			return;
		}
		if(bl.deleteSite(address))
			System.out.println("Store was deleted successfully!");
		else
			System.out.println("Something went wrong during the deletion of the store, sorry...");
	}
	private void fetchSite() 
	{
		String address = pl_shared.getExistStoreAddressFromUser()/*getExistAddressFromUser(2)*/;
		if(address.equals("~"))
			return;
		Site site = bl.fetchSite(address);
		System.out.println(site.toString());
	}	
}