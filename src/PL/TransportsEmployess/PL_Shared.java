package PL.TransportsEmployess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Vector;

import BL.StorageSuppliers.Calculations;
import BL.TransportsEmployess.*;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.Shift;

public class PL_Shared 
{
	
	private BL bl;
    private Scanner scanner;
    Validator validator;
    
	public PL_Shared(BL bl, Scanner scanner, Validator validator)
	{
		 this.bl = bl;
	     this.scanner = scanner;
	     this.validator = validator;
	}
	
	
	protected String getShiftDate()
    {
    	 System.out.println("Enter Date :");
         String date = scanner.nextLine();
         while(!validator.validateDate(date))
         {
             if (date.equals("~"))
                 return date;
             System.out.println("date is not valid, try again: ");
             date = scanner.nextLine();
         }
         return date;
    }
	
	protected String getShiftType()
    {
    	System.out.println("choose shift :");
        System.out.println("1) morning");
        System.out.println("2) evening");
        String Type = scanner.nextLine();
        while(!Type.equals("1") && !Type.equals("2"))
        {
            if (Type.equals("~"))
                return Type;
            System.out.println("type is not valid, try again:");
            Type = scanner.nextLine();
        }
        if (Type.equals("1"))
            return "morning";
        else
            return "evening";
    }
	
	protected int GetDay(String toPrint)
	{
	    System.out.println("Enter " +toPrint +" : ");
	    System.out.println("1-sunday, 2- monday, 3- tuesday, 4- wednesday 5 - thursday 6- friday 7 - saturday:");
	    String day = scanner.nextLine();
	    while(!validator.validateIntInBounds(day, 1, 7))
	    {
	        if (day.equals("~"))
	            return 0;
	        System.out.println(toPrint + " is not valid, try again:");
	        day = scanner.nextLine();
	    }
	    return Integer.parseInt(day);
	 }
	 
	 
	private String getValidId()
    {
    	System.out.println("Enter Employee id :");
        String id = scanner.nextLine();
        while (!validator.validateID(id)) 
        {
            if (id.equals("~"))
                return "~";
            System.out.println("id is not valid, try again:");
            id = scanner.nextLine();
        }
        return id;
    }
	    
	protected String getExistingId()
    {
		while (true)
        {
	        String id =  getValidId();
	        if(id.equals("~"))
	        	return id;
	        Employee emp = bl.fetchEmployee(id);
	        if (emp != null)
	        	return id;
	        else
	        	System.out.println("employee does not exist");
        }
    }  
	protected String getNotExistingId()
    {
    	while (true)
        {
	        String id =  getValidId();
	        if(id.equals("~"))
	        	return id;
	        Employee emp = bl.fetchEmployee(id);
	        if (emp == null)
	        	return id;
	        else
	        	System.out.println("employee with this id already exist");
        }
    }
	
	 
	protected String getCurrentDate()
    {
    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	 LocalDateTime  localDate = LocalDateTime.now();
         return dtf.format(localDate);
    }
    
    protected String getName(String toPrint)
    {
    	System.out.println("Enter "+toPrint+" :");
        String name = scanner.nextLine();
        while(!validator.validateName(name))
        {
            if (name.equals("~"))
                return name;
            System.out.println(toPrint+" is not valid, try again:");
            name = scanner.nextLine();
        }
        return name;
    }
    
    protected String getsalary()
    {
    	System.out.println("Enter salary :");
        String salary = scanner.nextLine();
        while(!validator.validateDouble(salary))
        {
            if (salary.equals("~"))
                return salary;
            System.out.println("salary is not valid, try again: ");
            salary = scanner.nextLine();
        } 	
        return salary;
    }
    
    protected String getBankDetails()
    {
    	 System.out.println("Enter account number :");
         String accNum = scanner.nextLine();
         while(!validator.validateIntInBounds(accNum, 100000, 999999))
         {
             if (accNum.equals("~"))
                 return accNum;
             System.out.println("account number is not valid, try again: ");
             accNum = scanner.nextLine();
         }
         System.out.println("Enter bank name :");
         String bankName = scanner.nextLine();
         while(!validator.validateBankName(bankName))
         {
             if (bankName.equals("~"))
                 return bankName;
             System.out.println("bank name is not valid, try again :");
             bankName = scanner.nextLine();
         }
         System.out.println("Enter branch number :");
         String branchNumber = scanner.nextLine();
         while(!validator.validateIntInBounds(branchNumber, 100, 999))
         {
             if (branchNumber.equals("~"))
                 return branchNumber;
             System.out.println("invalid branch number, try again: :");
             branchNumber = scanner.nextLine();
         }
         return accNum + "/" + bankName  + "/" + branchNumber + "/" + "15%"  + "/"+ "10%";
    }
    
    protected void makeDayOfWorkUnavilable(int id,int day)
    {
    	 bl.insertEmployeeRestriction(id,day,"morning");
         bl.insertEmployeeRestriction(id,day,"evening");
    }
    
    protected int manual(int i,Vector<Shift> vec1)
    {
        System.out.println("At any point you can press ~ to return to previous menu");
       
        String date = getShiftDate();
        if (date.equals("~"))
            return i;
        String type  = getShiftType();
        if (type.equals("~"))
            return i;
        
        for (int j=0; j <vec1.size() ; j++)
        {
            if (vec1.elementAt(j).getDate().equals(date) && vec1.elementAt(j).getType().equals(type))
                return j;
        }
        System.out.println("shift not exist");
        return i;
    }
    
    
    protected String getValidTruckNumber()
	{
		String truckNo; 
		System.out.println("Truck No. : ");
		truckNo = scanner.nextLine();
		while (!validator.validateIntInBounds(truckNo, 10000000, 99999999)) 
		{
			if (truckNo.equals("~"))
				return "~";
		    System.out.println("Truck No is not valid, try again:");
		    truckNo = scanner.nextLine();
		}
		return truckNo;
		
	}
	
    protected String getNotExistingTruckNumber()
	{		
		String truckNo;
		while(true)
		{
			truckNo = getValidTruckNumber();
			if(truckNo.equals("~"))
				return truckNo;
			if(bl.fetchTruck(Integer.parseInt(truckNo)) == null)
				break;
			else
				System.out.println("Truck No already exist:");	
		}
	    return truckNo; 
	}
	
    protected String getExistingTruckNumber()
	{		
		String truckNo;
		while(true)
		{
			truckNo = getValidTruckNumber();
			if(truckNo.equals("~"))
				return truckNo;
			if(bl.fetchTruck(Integer.parseInt(truckNo)) != null)
				break;
			else
				System.out.println("Truck not exist in the system! ");	
		}
	    return truckNo; 
	}
    
    protected String getExistStoreAddressFromUser()/*getExistAddressFromUser(int isStore)*/
	{
		String address;
		System.out.println("Please insert the stores's address : ");
		address = scanner.nextLine();
		while(bl.fetchSite(address) == null) 
		{
			if(address.equals("~"))
				return address;
			System.out.println("address not exist, try again: ");
			address = scanner.nextLine();
		}
		return address;
	}
	
    protected String getNotExistAddressFromUser()
	{
		String address;
		//System.out.println("Please insert the site's address : ");
		System.out.println("address :");
		address = scanner.nextLine();
		while(bl.fetchSite(address) != null || address.equals("")) 
		{
			if(address.equals("~"))
				return address;
			if(!address.equals(""))
				System.out.println("address does exist, try again");
			else 
				System.out.println("invalid input please try again");
			address = scanner.nextLine();
		}
		return address;
	}

    public String getNotEmptyStringFromUser(String toPrint){
    	String ret="";
    	System.out.print(toPrint+" :");
    	while(true){
			ret = scanner.nextLine();
			if (ret.equals("~"))
				return ret;
			if (!ret.equals(""))
				return ret;
			System.out.println(toPrint+ " can't be empty, please enter again");
		}
    }

	public int manualOrder(int i, Vector<Order> undeliveredOrders) 
	{
		System.out.println("At any point you can press ~ to return to previous menu");
		System.out.println("Enter order id: ");
		Calculations calc = Calculations.GetCalculations();
        String orderNumber = scanner.nextLine();
        if(!calc.checkInt(orderNumber))
        {
        	if(orderNumber.equals("~"))
        		return i;
        	System.out.println("invalid order number, try again");
        }
		
        for (int j=0; j <undeliveredOrders.size() ; j++)
        {
            if (undeliveredOrders.elementAt(j).getOrderNumber()== Integer.parseInt(orderNumber))
                return j;
        }
        System.out.println("Order not exist or already delivered");
        return i;
	}
}