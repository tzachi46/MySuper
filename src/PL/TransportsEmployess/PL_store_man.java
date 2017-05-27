package PL.TransportsEmployess;


import java.util.Scanner;
import java.util.Vector;

import BL.TransportsEmployess.*;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Employee.Rank;

/**
 * Created by kazarski on 3/26/17.
 */
public class PL_store_man {
	
    private BL bl;
    private Scanner scanner;
    private Validator validator;
    private PL_Shared pl_shared;

    public PL_store_man(BL bl, Scanner scanner, Validator validator, PL_Shared pl_shared) 
    {
        this.bl = bl;
        this.scanner = scanner;
        this.validator = validator;
        this.pl_shared = pl_shared;
    }


    

    
   	public void showInitializedShifts(String address)
   	{
   	  	int i = 0;
   		Vector<Shift> initializedShifts = bl.fetchInitializedShifts(address);
   		if(initializedShifts == null)
   		{
                System.out.println("no relevant shifts");
                return;
           }
   		bl.sortDates(initializedShifts);
   		
           while (true)
           {    
           	Vector<Pair<Employee,String>> wrokersInShift = bl.fetchEmployeesInShift(address, initializedShifts.elementAt(i));
               System.out.println("choose option");
               System.out.println("0)return, 1)left, 2)right,3)manual");
               System.out.println("***********************************");
               System.out.println("Date: " + initializedShifts.elementAt(i).getDate());
               System.out.println("Type: " + initializedShifts.elementAt(i).getType());
               System.out.println("The workers of the shift are: ");
               for(int j = 0;j<wrokersInShift.size();j++)
               {
               	Pair<Employee,String> pair = wrokersInShift.elementAt(j);
               	System.out.println(pair.getKey().getFname()+ " " + pair.getKey().getLname() + " " + pair.getKey().getId() + " work as " +pair.getValue());
               }
               System.out.println("***********************************");

               String option = scanner.nextLine();
               if (!validator.validateIntInBounds(option, 0, 3))
                   System.out.println("invalid input, try again");
               else 
               {
                   if (option.equals("0"))
                       break;
                   if (option.equals("1")) {
                       if (i == 0)
                           System.out.println("earlier shifts not exist");
                       else
                           i--;
                   }
                   if (option.equals("2")) {
                       if (i == initializedShifts.size() - 1)
                           System.out.println("later shifts not exist");
                       else
                           i++;
                   }
                   if (option.equals("3"))
                       i = pl_shared.manual(i,initializedShifts);
               }
           }
       }	

    
    private void printCurrentSpecs(Employee emp)
    {
    	EmployeeSpeciality[] specs = bl.getSpecsOf(emp.getId());
        System.out.println("Current Specializiaions of "+emp.getFname()+" "+emp.getLname()+" are:");
        if(specs.length == 0)
            System.out.println("None.");
        for(int i= 0; i<specs.length; i++)
            System.out.println(specs[i].getSpecialization());
    }

    public void updateSpec(Employee manEmp)
    {
        System.out.println("At any point you can press ~ to return to previous menu");
        String id = pl_shared.getExistingId();
        if(id.equals("~"))
        	return;
        Employee emp = bl.fetchEmployee(id);
        if(!manEmp.getWorkAddress().equals(emp.getWorkAddress()))
       	{
       		System.out.println("user not exist in this store");
       		return;
       	}
        if(emp.getRank() == Rank.storeManager || emp.getRank() == Rank.logisticManager || emp.getRank() == Rank.storeManager)
        {
        	System.out.println("you dont have permission to update the specializations of maneger,admin,ceo");
        	return;
        }
        while (true)
        {
        	printCurrentSpecs(emp);
            System.out.println("Choose Option: ");
            System.out.println("1) add Specializiation");
            System.out.println("2) remove Specializiation");
            System.out.println("3) return to previous menu");
          
            String choice = scanner.nextLine();
            if(!handelUpdateSpecChoice(emp, choice))
                break;
        }
    }

    public void export_reports()
    {
    	// Boris implementation
    }
    
    private void addSpecializiation(Employee emp)
    {
        String spec = getSpecialization();
        if(spec.equals("0"))
        	return;
        if(spec.equals("Carrier"))
        {
        	if(bl.fetchDriver(emp.getId()) == null)
        	{
        		 System.out.println("employee dont have licence");
        		 return;
        	}
        }
        boolean updateded = bl.addEmployeeSpeciality(emp.getId(), spec);
        if (updateded)
            System.out.println(emp.getFname() + " " + emp.getLname() + " Specializiations was updedated with: " + spec);
        else
            System.out.println("problem happened");
    }
    
    
    private void removeSpecializiation(Employee emp)
    {
    	String spec = getSpecialization();
        if(spec.equals("0"))
        	return;
         boolean updateded = bl.deleteEmployeeSpeciality(emp.getId(), spec);
         if (updateded)
             System.out.println(emp.getFname() + " " + emp.getLname() + "'s Specializiation of " + spec + " was deleted.");
         else
             System.out.println("problem happened");
    }
    
    private String getSpecialization()
    {
    	  System.out.println("Enter specialization :");
          String spec = scanner.nextLine();
          while (!validator.validateSpec(spec)) 
          {
              if (spec.equals("0"))
                  return spec;
              System.out.println("Specialization is not valid, try again:");
              spec = scanner.nextLine();
          }
          return spec;  
    }
   

    public boolean handelUpdateSpecChoice(Employee emp, String choice) 
    {
        switch (choice) 
        {
            case "1": 
            {
            	addSpecializiation(emp);
                break;
            }
            case "2": 
            {
            	removeSpecializiation(emp);
                break;
            }
            case "3": 
            {
                return false;
            }
            default: 
            {
                System.out.println("invalid input, try again");
                break;
            }
        }
        return true;
    }
}