package PL.TransportsEmployess;

import java.util.Scanner;

import BL.BLManager;
import BL.TransportsEmployess.*;
import PL.StorageSuppliers.Inventory.InventoryCLI;
import PL.StorageSuppliers.Order.OrderCLI;
import PL.StorageSuppliers.SupplierPL.CLIMenu;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.TransportsEmployess.Transport;

/**
 * Created by Yoni Kazarski on 24/03/2017.
 */
public class PLimpl implements PL
{
  
    private BL bl;
    private PL_reg pl_reg;
    private PL_man pl_man;
    private PL_HR_man pl_admin;
    private PL_SitesEdit pl_SitesEdit;
    private PL_Shared pl_shared;
    private PL_TransportEdit pl_TransportEdit;
    private PL_TruckEdit pl_TruckEdit;
    private Scanner scanner;
    private Validator validator;

    public PLimpl()
    {
        scanner = new Scanner(System.in);
        bl = new BLimp();
        validator =  new Validator_imp();
        PL_Shared pl_Shared = new PL_Shared(bl, scanner, validator);
        pl_reg = new PL_reg(bl, validator,pl_Shared);
        pl_man = new PL_man(bl,scanner, validator,pl_Shared);
        pl_admin = new PL_HR_man(bl,scanner, validator,pl_Shared);
        pl_SitesEdit = new PL_SitesEdit(bl, scanner, validator,pl_Shared);
        pl_TruckEdit = new PL_TruckEdit(bl, scanner, validator,pl_Shared);
        pl_TransportEdit = new PL_TransportEdit(bl, scanner, validator,pl_Shared);
        pl_shared = new PL_Shared(bl, scanner, validator);
    }

    @Override
    public void run()
    {
        boolean exiting;
        while (true)
        {
            exiting = showStartMenu();
            if(!exiting)
            {
            	goodBye();
                break;
            }
        }

    }
    private void goodBye() 
    {
		System.out.println("***** Thank you and Goodbye! *****");
	}
    
    

    private void openingFrame(){
		System.out.println("*********************");
		System.out.println("* Hello and Welcome *");
		System.out.println("* to the Transport  *");
		System.out.println("* and Employment    *");
		System.out.println("* Managment System! *");
		System.out.println("*********************");
	}
    
    private boolean showStartMenu()
    {
    	openingFrame();

        System.out.println("1) log in");
        System.out.println("2) Exit");

        String option = scanner.nextLine();

        while (!option.equals("1") && !option.equals("2"))
        {
            System.out.println("ivalid input, try again");
            option = scanner.nextLine();
        }

        if (option.equals("1"))
            return showLoginMenu();
        else
            return false;

    }

    private boolean showLoginMenu() {
        Employee emp;
        while (true) {
            System.out.println("Enter your id or press ~ to return: ");
            String id = scanner.nextLine();
            while (!validator.validateID(id)) {
                if (id.equals("~"))
                    return true;
                System.out.println("id is not valid, try again:");
                id = scanner.nextLine();
            }
            emp = bl.fetchEmployee(id);
            if (emp != null)
                break;
            System.out.println("The id you entered does not exist in the system.");
        }
        if (!emp.getEndDate().equals(""))
        {
            System.out.println("You are not working anymore, you dont have access to the system.");
            return  true;
        }
        BLManager.emp = emp;
        switch (emp.getRank())
        {
	        case logisticManager:
	        {
	            return HandleLogedCeo(emp);
	        }
            case humenResourceManager:
            {
                return HandleLogedAdmin(emp);
            }
            case storeManager:
            {
                return HandleLogedManager(emp);
            }
            default:
            {
                return HandleLogedRegular(emp);
            }
        }
    }

    private boolean HandleLogedCeo(Employee emp)
    {
    	 System.out.println("Hello " + emp.getFname());
         while (true)
         {
             System.out.println("1) Store Manegment");
             System.out.println("2) Truck Manegment"); 
             System.out.println("3) Transport Manegment"); 
             System.out.println("4) go back to Previous menu");

             String choice = scanner.nextLine();

             if(!HandeleCeoChoice(choice,emp))
                 break;
         }
         return  true;
	}


	private boolean HandleLogedAdmin(Employee emp)
    {
        while (true)
        {
            System.out.println("Hello " + emp.getFname());
            System.out.println("1) Employees management");
            System.out.println("2) Shifts management");
            System.out.println("3) Employee's specializations management");
            System.out.println("~) Go back to Previous menu");
            
            String choice = scanner.nextLine();

            if(!HandeleAdminChoice(choice,emp))
                break;
        }
        return  true;

    }

    private boolean HandleLogedManager(Employee emp)
    {
        while (true)
        {
            System.out.println("Hello " + emp.getFname());
            System.out.println("1) Show messages");
            System.out.println("2) Employee reports management");
            System.out.println("3) Inventory reports management");
            System.out.println("4) Get all orders");
            System.out.println("5) Supplier management");
            System.out.println("~) Go back to Previous menu");
            String choice = scanner.nextLine();

            if(!HandeleManagerChoice(choice,emp))
                break;
        }
        return  true;

    }
    
    /*
     * NEW (edited 26.5 by Ofir)
     */
    private boolean HandleLogedRegular(Employee emp)
    {
    	boolean isStoreKeeper = isStoreKeeper(emp.getId());
    	boolean isDriving = isDriving(emp.getId());
        while (true)
        {
            System.out.println("Hello " + emp.getFname());
            System.out.println("1) Restriction menu");
            System.out.println("2) show shifts");
            if(!isStoreKeeper){
            	if(isDriving){
            		System.out.println("3) View transports details"); //Present transports whose driver is emp
            	}
            	System.out.println("~) go back to Previous menu");
            }
            else
            {
            	System.out.println("3) supplier manegment screen");
            	System.out.println("4) Inventory manegment screen");
            	System.out.println("5) Order manegment screen");
            	if(isDriving){
            		System.out.println("6) View transports details"); //Present transports whose driver is emp
            	}
            	System.out.println("~) go back to Previous menu");

            }
            
            String choice = scanner.nextLine();

            if(!HandeleRegularrChoice(choice,emp,isStoreKeeper, isDriving))
                break;
        }

        return true;
    }
    
    private boolean HandleEmployeeManagementMenu(Employee emp)
    {
        while (true)
        {
        	System.out.println("Choose one of the followings: ");
            System.out.println("1) Add new employee");
            System.out.println("2) Update employee");
            System.out.println("3) Remploye employee");
            System.out.println("4) View employee's details");
            System.out.println("~) Return to previous menu");
            String choice = scanner.nextLine();
            if(!HandleEmployeeManagementMenuChoice(choice,emp))
                break;
        }

        return true;
    }
    
    /*
     * NEW (edited 26.5 by Ofir)
     */
    private boolean isDriving(int id){
    	return pl_reg.getRelevantTransports(id)!=null;
    }

    private boolean isStoreKeeper(int id) 
    {
		EmployeeSpeciality[] specs = bl.getSpecsOf(id);
		for (int i = 0; i < specs.length; i++)
		{
			if(specs[i].getSpecialization().equals("StoreKeeper"))
				return true;
		}
		return false;
	}

	private boolean HandeleAdminChoice(String choice, Employee emp)
    {
        switch (choice)
        {
            case "1":
            {
            	HandleEmployeeManagementMenu(emp);
                break;
            }
            case "2":
            {
            	HandleShiftManagementMenu(emp);
                break;
            }
            case "3":
            {
            	pl_man.updateSpec(emp);
                break;
            }
            case "~":
            {
                return false;
            }
            default:
            {
                System.out.println("Invalid input, try again");
                break;
            }
        }
        return true;
    }
	
	private boolean HandleShiftManagementMenu(Employee emp) {
		while (true)
        {
        	System.out.println("Choose one of the followings: ");
            System.out.println("1) Add new shift");
            System.out.println("2) Delete shift");
            System.out.println("3) Show initialized shifts");
            System.out.println("4) Add employee to shift");
            System.out.println("~) Return to previous menu");
            String choice = scanner.nextLine();
            if(!HandleShiftManagementMenuHandle(choice,emp))
                break;
        }

        return true;
		
	}
	
	private boolean HandleShiftManagementMenuHandle(String choice, Employee emp){
		switch(choice)
		{
			case "1":
		    {
		        pl_admin.addShift(emp.getWorkAddress());
		        break;
		    }
		    case "2":
		    {
		        pl_admin.deleteShift(emp.getWorkAddress());
		        break;
		    }
		    case "3":
		    {
		        pl_admin.showInitializedShifts(emp.getWorkAddress());
		        break;
		    }
		    case "4":
		    {
		        pl_man.updateShift(emp);
		        break;
		    }
		    case "~":
		    {
		    	return false;
		    }
		    default:
            {
                System.out.println("Invalid input, try again");
                break;
            }
		}
		return true;
	}

	private boolean HandleEmployeeManagementMenuChoice(String choice, Employee emp)
    {
        switch (choice)
        {
            case "1":
            {
                pl_admin.addEmployee(emp.getWorkAddress());
                break;
            }
            case "2":
            {
                pl_admin.UpdateEmployee(emp);
                break;
            }
            case "3":
            {
            	pl_admin.reEmploy(emp.getWorkAddress());
                break;
            }
            case "4":
            {
            	pl_admin.showEmployeeDetails(emp);
                break;
            }
            case "~":
            {
                return false;
            }
            default:
            {
                System.out.println("Invalid input, try again");
                break;
            }
        }
        return true;
    }


    private boolean HandeleManagerChoice(String choice, Employee emp)
    {
        switch (choice)
        {
            case "1":
            {
                pl_admin.viewMessages(emp.getWorkAddress());
                break;
            }
            case "2":
            {
            	HandleEmployeeReportManagementMenu(emp);
            	
                break;
            }
            case "3":
            {
            	HandleInventoryReportManagementMenu(emp);
                break;
            }
            case "4":
            {
            	OrderCLI.getAllOrders(scanner);
            	break;
            }
            case "5":
            {//supplier management
            	CLIMenu.getInstance().Start(scanner);
            	break;
            }
            case "~":
            {//ret
                return false;
            }
            default:
            {
                System.out.println("Invalid input, try again");
                break;
            }
        }
        return true;
    }
    
	private boolean HandleEmployeeReportManagementMenu(Employee emp) 
	{
		while (true)
        {
        	System.out.println("Choose one of the followings: ");
        	System.out.println("1) Show employee's details");
            System.out.println("2) Show employee's shifts");
            System.out.println("3) Show initialized shifts");
            System.out.println("4) Show uninitialized shifts");
            /*HandleEmployeeShiftReportManagementMenu(emp)*/
            System.out.println("~) Return to previous menu");
            String choice = scanner.nextLine();
            if(!HandleEmployeeReportManagementMenuChoice(choice,emp))
                break;
        }

        return true;	
	}
	
	private boolean HandleEmployeeReportManagementMenuChoice(String choice, Employee emp) {
		switch (choice)
        {
            case "1":
            {
            	pl_admin.showEmployeeDetails(emp);
                break;
            }
            case "2":
            {//emp shifts
            	String id=pl_shared.getExistingId();
            	if(id.equals("~"))
            		break;
                pl_reg.showAllShifts(Integer.parseInt(id));
                break;
            }
            case "3":
            {
                pl_admin.showInitializedShifts(emp.getWorkAddress());
                break;
            }
            case "4":
            {
                System.out.println("currently unsported will be avilable soon");
                break;
            }
            case "~":
            {//ret
                return false;
            }
            default:
            {
                System.out.println("Invalid input, try again");
                break;
            }
        }
        return true;
	}
/*
	private boolean HandleEmployeeShiftReportManagementMenu(Employee emp) 
	{
		while (true)
        {
        	System.out.println("Choose one of the followings: ");
            System.out.println("1) Show initialized shifts");
            System.out.println("2) Show uninitialized shifts");
            System.out.println("~) Return to previous menu");
            String choice = scanner.nextLine();
            if(!HandleEmployeeShiftReportManagementMenuChoice(choice,emp))
                break;
        }
        return true;	
	}
    
    
    private boolean HandleEmployeeShiftReportManagementMenuChoice(String choice, Employee emp) 
    {
    	switch (choice)
        {
            case "1":
            {
                pl_admin.showInitializedShifts(emp.getWorkAddress());
                break;
            }
            case "2":
            {
                System.out.println("currently unsported will be avilable soon");
                break;
            }
            case "~":
            {//ret
                return false;
            }
            default:
            {
                System.out.println("Invalid input, try again");
                break;
            }
        }
        return true;
	}
*/
    /*
     * NEW (edited 26.5 by Ofir)
     */
	private boolean HandeleRegularrChoice(String choice, Employee emp, boolean isStoreKeeper, boolean isDriving)
    {
        switch (choice)
        {
            case "1":
            {
                pl_reg.restrictionMenu(emp);
                break;
            }
            case "2":
            {
                pl_reg.showAllShifts(emp.getId());
                break;
            }
            case "3":
            {
            	if(!isStoreKeeper&& !isDriving){
            		System.out.println("Invalid input, try again");
            	}
            	else if(!isStoreKeeper && isDriving){
            		pl_reg.showRelevantTransports(emp.getId());
            	}
            	else if(isStoreKeeper)
            	{
            		CLIMenu.getInstance().Start(scanner);
            	}
            	break;
            }
            case "4":
            {
            	if(!isStoreKeeper)
            		System.out.println("Invalid input, try again");
            	else
            	{
            		InventoryCLI.GetInvCLIManager().Start();
            	}
            	break;
            }
            case "5":
            {
            	if(!isStoreKeeper)
            		System.out.println("Invalid input, try again");
            	else
            	{
            		OrderCLI.Start(scanner);
            	}
            	break;
            }
            case "6":
            {
            	if(isStoreKeeper && isDriving){
            		pl_reg.showRelevantTransports(emp.getId());
            	}
            	else{
            		System.out.println("Invalid input, try again");
            	}
            }
            case "~":
            {
            	return false;
            }
            default:
            {
            		System.out.println("Invalid input, try again");
            		break;
            }
        }
        return true;
    }
    
	private boolean HandeleCeoChoice(String choice, Employee emp) 
	{
		switch(choice)
		{
		    case "1":
		    {
		        pl_SitesEdit.workOnSites();
		        break;
		    }
		    case "2":
		    {
		        pl_TruckEdit.workOnTrucks();
		        break;
		    }
		    case "3":
		    {
		        pl_TransportEdit.workOnTransport();
		        break;
		    }
		    case "4":
		    {
		        return false;
		    }
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
	
	private boolean HandleInventoryReportManagementMenu(Employee emp) 
	{
		while (true)
        {
        	System.out.println("Choose one of the followings: ");
            System.out.println("1) Get products in inventory.");
            System.out.println("2) Get products that are running out.");
            System.out.println("3) Get defective products.");
            System.out.println("4) Get product details.");
            System.out.println("~) Return to previous menu");
            String choice = scanner.nextLine();
            if(!HandleInventoryReportManagementMenuChoice(choice,emp))
                break;
        }
        return true;	
	}
    
    
    private boolean HandleInventoryReportManagementMenuChoice(String choice, Employee emp) 
    {
    	InventoryCLI ICLI =InventoryCLI.GetInvCLIManager();
    	switch (choice)
        {
            case "1":
            {
            	ICLI.getProductsMenu(scanner);
                break;
            }
            case "2":
            {
            	ICLI.runningOutProducts(scanner);
                break;
            }
            case "3":
            {
            	ICLI.defectiveProducts(scanner);
                break;
            }
            case "4":
            {
            	ICLI.getProductMenu(scanner);
                break;
            }
            case "~":
            {
                return false;
            }
            default:
            {
                System.out.println("Invalid input, try again");
                break;
            }
        }
        return true;
	}
}