package PL.TransportsEmployess;

import java.util.Scanner;

import BL.BLManager;
import BL.TransportsEmployess.*;
import PL.StorageSuppliers.SupplierPL.CLIMenu;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;

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
	        case logisticManeger:
	        {
	            return HandleLogedCeo(emp);
	        }
            case humenResourceManeger:
            {
                return HandleLogedAdmin(emp);
            }
            case storeManeger:
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
             System.out.println("1) site edit menu");
             System.out.println("2) truck edit menu"); 
             System.out.println("3) Transport edit menu"); 
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
            /*System.out.println("1) add employee");
            System.out.println("2) update employee"); // salery , leaving date, Rank.
            System.out.println("3) Remploy employee"); // salery , leaving date, Rank.
            System.out.println("4) add shift");
            System.out.println("5) delete shift");
            System.out.println("6) show Employee details");
            System.out.println("7) show Initialized shifts");
            System.out.println("8) View Messages");
            System.out.println("9) go back to Previous menu");*/
            
            System.out.println("1) Employees management");
            System.out.println("2) Shifts management");
            System.out.println("3) Update employee's specializations");
            System.out.println("4) Go back to Previous menu");
            
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
            System.out.println("1) Add restriction");
            System.out.println("2) Remove restriction");
            System.out.println("3) show current restrrictions");
            System.out.println("4) show shifts");
            System.out.println("5) update employees of shift");
            System.out.println("6) update specialization of employee");
            System.out.println("7) go back to Previous menu");

            String choice = scanner.nextLine();

            if(!HandeleManagerChoice(choice,emp))
                break;
        }
        return  true;

    }

    private boolean HandleLogedRegular(Employee emp)
    {
    	boolean isStoreKeeper = isStoreKeeper(emp.getId());
        while (true)
        {
            System.out.println("Hello " + emp.getFname());
            System.out.println("1) Restriction menu");
            System.out.println("2) show shifts");
            if(!isStoreKeeper)
            	System.out.println("3) go back to Previous menu");
            else
            {
            	System.out.println("3) supplier manegment");
            	System.out.println("4) go back to Previous menu");
            }
            /*System.out.println("1) Add restriction");
            System.out.println("2) Remove restriction");
            System.out.println("3) show current restrictions");
            System.out.println("4) show shifts");
            if(!isStoreKeeper)
            	System.out.println("5) go back to Previous menu");
            else
            {
            	System.out.println("5) supplier manegment");
            	System.out.println("6) go back to Previous menu");
            }
			*/
            String choice = scanner.nextLine();

            if(!HandeleRegularrChoice(choice,emp,isStoreKeeper))
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
            System.out.println("5) Return to previous menu");
            String choice = scanner.nextLine();
            if(!HandleEmployeeManagementMenuChoice(choice,emp))
                break;
        }

        return true;
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
            /*case "8":
            {
            	pl_admin.viewMessages(emp.getWorkAddress());
            }*/
            case "3":
            {
            	pl_man.updateSpec(emp);
                break;
            }
            case "4":
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
            System.out.println("4) Return to previous menu");
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
            case "5":
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
                pl_reg.addConstraint(emp.getId());
                break;
            }
            case "2":
            {
                pl_reg.removeConstraint(emp);
                break;
            }
            case "3":
            {
                pl_reg.showAllConstraints(emp.getId());
                break;
            }
            case "4":
            {
                pl_reg.showAllShifts(emp.getId());
                break;
            }
            case "5":
            {
                pl_man.updateShift(emp);
                break;
            }
            case "6":
            {
                pl_man.updateSpec(emp);
                break;
            }
            case "7":
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
    
    
    private boolean HandeleRegularrChoice(String choice, Employee emp, boolean isStoreKeeper)
    {
        switch (choice)
        {
            case "1":
            {
                pl_reg.restrictionMenu(emp);
                break;
            }
            /*case "2":
            {
                pl_reg.removeConstraint(emp);
                break;
            }
            case "3":
            {
                pl_reg.showAllConstraints(emp.getId());
                break;
            }*/
            case "2":
            {
                pl_reg.showAllShifts(emp.getId());
                break;
            }
            case "3":
            {
            	if(!isStoreKeeper)
            		return false;
            	else
            	{
            		CLIMenu.getInstance().Start(scanner);
            	}
            }
            default:
            {
            	if(!isStoreKeeper && choice.equals("4"))
            		return false;
            	else
            		System.out.println("Invalid input, try again");
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
		    default:
		    {
		        System.out.println("Invalid input, try again");
			}
		}
		return true;
	}
}


/*
package PL.TransportsEmployess;

import java.util.Scanner;

import BL.BLManager;
import BL.TransportsEmployess.*;
import PL.StorageSuppliers.SupplierPL.CLIMenu;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;

public class PLimpl implements PL
{
  
	/// new fields *********************************
	
    private BL bl;
    private PL_reg pl_reg;
    private PL_store_man pl_store_man;
    private PL_HR_man pl_HR_man;
    private PL_SitesEdit pl_SitesEdit;
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
        pl_store_man = new PL_store_man(bl,scanner, validator,pl_Shared);
        pl_HR_man = new PL_HR_man(bl,scanner, validator,pl_Shared);
        pl_SitesEdit = new PL_SitesEdit(bl, scanner, validator,pl_Shared);
        pl_TruckEdit = new PL_TruckEdit(bl, scanner, validator,pl_Shared);
        pl_TransportEdit = new PL_TransportEdit(bl, scanner, validator,pl_Shared);
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
//    ******* yoni edit 21/05   *********
    private boolean showLoginMenu() {
        Employee emp;
        while (true) {
            System.out.println("Enter your id or press ~ to return: ");
            String id = scanner.nextLine();
            while (!validator.validateID(id)) {
                if (id.equals("0"))
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
	        case logisticManeger:
	        {
	            return HandleLogedCeo(emp);
	        }
            case storeManeger:
            {
                return HandleLogedStoremanager(emp);
            }
            case humenResourceManeger:
            {
                return HandleLogedHRmanager(emp);
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
             System.out.println("1) site edit menu");
             System.out.println("2) truck edit menu"); 
             System.out.println("3) Transport edit menu"); 
             System.out.println("4) go back to Previous menu");

             String choice = scanner.nextLine();

             if(!HandeleCeoChoice(choice,emp))
                 break;
         }
         return  true;
	}

 // ***************   NOT  RELEVANT IN HW3   ************************************************
	private boolean HandleLogedAdmin(Employee emp)
    {
        while (true)
        {
            System.out.println("Hello " + emp.getFname());
            System.out.println("1) add employee");
            System.out.println("2) update employee"); // salery , leaving date, Rank.
            System.out.println("3) Remploy employee"); // salery , leaving date, Rank.
            System.out.println("4) add shift");
            System.out.println("5) delete shift");
            System.out.println("6) show Employee details");
            System.out.println("7) show Initialized shifts");
            System.out.println("8) go back to Previous menu");

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
            System.out.println("1) Add restriction");
            System.out.println("2) Remove restriction");
            System.out.println("3) show current restrrictions");
            System.out.println("4) show shifts");
            System.out.println("5) update employees of shift");
            System.out.println("6) update specialization of employee");
            System.out.println("7) go back to Previous menu");

            String choice = scanner.nextLine();

            if(!HandeleManagerChoice(choice,emp))
                break;
        }
        return  true;

    }
 
    private boolean HandeleAdminChoice(String choice, Employee emp)
    {
        switch (choice)
        {
            case "1":
            {
          //      pl_admin.addEmployee(emp.getWorkAddress());
                break;
            }
            case "2":
            {
            //    pl_admin.UpdateEmployee(emp);
                break;
            }
            case "3":
            {
           //     pl_admin.reEmploy(emp.getWorkAddress());
                break;
            }
            case "4":
            {
           //     pl_admin.addShift(emp.getWorkAddress());
                break;
            }
            case "5":
            {
           //     pl_admin.deleteShift(emp.getWorkAddress());
                break;
            }
            case "6":
            {
           //     pl_admin.showEmployeeDetails(emp);
                break;
            }
            case "7":
            {
           //     pl_admin.showInitializedShifts(emp.getWorkAddress());
                break;
            }
            case "8":
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
             //   pl_reg.addConstraint(emp.getId());
                break;
            }
            case "2":
            {
           //     pl_reg.removeConstraint(emp);
                break;
            }
            case "3":
            {
            //    pl_reg.showAllConstraints(emp.getId());
                break;
            }
            case "4":
            {
            //    pl_reg.showAllShifts(emp.getId());
                break;
            }
            case "5":
            {
             //   pl_man.updateShift(emp);
                break;
            }
            case "6":
            {
              //  pl_man.updateSpec(emp);
                break;
            }
            case "7":
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
    // ***************   NOT  RELEVANT IN HW3   ************************************************
    
    private boolean HandleLogedRegular(Employee emp)
    {
    	boolean isStoreKeeper = isStoreKeeper(emp.getId());
        while (true)
        {
            System.out.println("Hello " + emp.getFname());
            System.out.println("1) Add restriction");
            System.out.println("2) Remove restriction");
            System.out.println("3) show current restrictions");
            System.out.println("4) show shifts");
            if(!isStoreKeeper)
            	System.out.println("5) go back to Previous menu");
            else
            {
            	System.out.println("5) supplier manegment");
            	System.out.println("6) go back to Previous menu");
            }

            String choice = scanner.nextLine();

            if(!HandeleRegularrChoice(choice,emp,isStoreKeeper))
                break;
        }

        return true;
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

    
    
    private boolean HandeleRegularrChoice(String choice, Employee emp, boolean isStoreKeeper)
    {
        switch (choice)
        {
            case "1":
            {
                pl_reg.addConstraint(emp.getId());
                break;
            }
            case "2":
            {
                pl_reg.removeConstraint(emp);
                break;
            }
            case "3":
            {
                pl_reg.showAllConstraints(emp.getId());
                break;
            }
            case "4":
            {
                pl_reg.showAllShifts(emp.getId());
                break;
            }
            case "5":
            {
            	if(!isStoreKeeper)
            		return false;
            	else
            	{
            		CLIMenu.getInstance().Start(scanner);
            	}
            }
            default:
            {
            	if(!isStoreKeeper && choice.equals("6"))
            		return false;
            	else
            		System.out.println("Invalid input, try again");
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
		    default:
		    {
		        System.out.println("Invalid input, try again");
			}
		}
		return true;
	}

	// *****  NEW  ******************************** yoni
	private boolean HandleLogedHRmanager(Employee emp)
	{
		while (true)
		{
			System.out.println("Hello " + emp.getFname());
		    System.out.println("1) Manage Employees");
		    System.out.println("2) Manage Shifts"); 
			
		    String choice = scanner.nextLine();
		    
		    if(!HandleHRmanagerChoice(choice,emp))
		        break;
		}
		return  true;
	}

	private boolean HandleLogedStoremanager(Employee emp)
	{
		while (true)
		{
			System.out.println("Hello " + emp.getFname());
		    System.out.println("1) show Initialized shifts");
		    System.out.println("2) update specialization of employee"); 
		    System.out.println("3) Export reports"); 
		    
		    String choice = scanner.nextLine();
		    
		    if(!HandleStoremanagerChoice(choice,emp))
		        break;
		}
		return  true;
	}

	private boolean HandleHRmanagerChoice(String choice, Employee emp)
	{
	    switch (choice)
	    {
	        case "1":
	        {
	        	//Manage Employees
	        	pl_HR_man.manage_Employees(emp);
	            break;
	        }
	        case "2":
	        {
	        	//Manage Shifts
	        	pl_HR_man.manage_Shifts(emp);
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

	private boolean HandleStoremanagerChoice(String choice, Employee emp)
	{
	    switch (choice)
	    {
	        case "1":
	        {
	        	//Manage Employees
	            pl_store_man.showInitializedShifts(emp.getWorkAddress());
	            break;
	        }
	        case "2":
	        {
	        	//Manage Shifts
	        	pl_store_man.updateSpec(emp);
	            break;
	        }
	        case "3":
	        {
	        	//Export reports
	        	pl_store_man.export_reports();
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
	// *****  NEW  ******************************** yoni
}


*/