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
public class PL_man {
	
    private BL bl;
    private Scanner scanner;
    private Validator validator;
    private PL_Shared pl_shared;

    public PL_man(BL bl, Scanner scanner, Validator validator, PL_Shared pl_shared) 
    {
        this.bl = bl;
        this.scanner = scanner;
        this.validator = validator;
        this.pl_shared = pl_shared;
    }


    public void updateShift(Employee emp) 
    {
    	 emptyShifts(emp.getWorkAddress());
    }
    

    private void emptyShifts(String address) 
    {

        int i = 0;
        while (true)
        {
            Vector<Shift> emptyShifts = bl.getEmptyShifts(address);
            if(emptyShifts == null){
                System.out.println("no relevant empty shifts");
                return;
            }
            bl.sortDates(emptyShifts);
            emptyShifts = bl.getUptoDate(emptyShifts);
            if (emptyShifts ==null)
            {
                System.out.println("no relevant empty shifts");
                return;
            }
            Vector<Pair<String,Integer>> accups = bl.fetchShiftAccupations(emptyShifts.elementAt(i));
            System.out.println("choose option");
            System.out.println("0)return, 1)left, 2)right, 3)update 4)manual");
            System.out.println("***********************************");
            System.out.println("Date: " + emptyShifts.elementAt(i).getDate());
            System.out.println("Type: " + emptyShifts.elementAt(i).getType());
            System.out.println("Needed employees: ");
            System.out.println("Shift managers: " + Shift.getNumOfAccup("ShiftManager",accups));//emptyShifts.elementAt(i).getShift_manger());
            System.out.println("Cashier: " + Shift.getNumOfAccup("Cashier",accups));//emptyShifts.elementAt(i).getCashier());
            System.out.println("Store keepers: " + Shift.getNumOfAccup("StoreKeeper",accups));//emptyShifts.elementAt(i).getStorekeeper());
            System.out.println("Truck Drivers: " + Shift.getNumOfAccup("TruckDriver",accups));//emptyShifts.elementAt(i).getTruck_driver());
            System.out.println("***********************************");

            String option = scanner.nextLine();
            if (!option.equals("0") && !option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4"))
                System.out.println("invalid input, try again");
            else {
                if (option.equals("0")||option.equals("~"))
                    break;
                if (option.equals("1")) {
                    if (i == 0)
                        System.out.println("earlier shifts not exist");
                    else
                        i--;
                }
                if (option.equals("2")) {
                    if (i == emptyShifts.size() - 1)
                        System.out.println("later shifts not exist");
                    else
                        i++;
                }
                if (option.equals("3")) 
                    i = EmployeeToShift(emptyShifts.elementAt(i),i);   
                if (option.equals("4"))
                    i = pl_shared.manual(i,emptyShifts);
            }
        }
    }

    private int EmployeeToShift(Shift shift,int index)
    {
        String choice;
        int i;
        Vector<Employee> vecMan = bl.getPossibleWorkers(shift,"ShiftManager");
        Vector<Employee> vecCash = bl.getPossibleWorkers(shift,"Cashier");
        Vector<Employee> vecSK = bl.getPossibleWorkers(shift,"StoreKeeper");
        Vector<Employee> vecCA = bl.getPossibleWorkers(shift,"Carrier");

        Vector<Integer> vecOpt1 = new Vector<Integer>();
        Vector<Integer> vecOpt2 = new Vector<Integer>();
        Vector<Integer> vecOpt3 = new Vector<Integer>();
        Vector<Integer> vecOpt4 = new Vector<Integer>();

        Vector<Pair<String,Integer>> accups = bl.fetchShiftAccupations(shift);
        if (vecMan.size()< Shift.getNumOfAccup("ShiftManager", accups)/*shift.getShift_manger()*/ || 
        		vecCash.size()< Shift.getNumOfAccup("Cashier", accups)/*shift.getCashier()*/  ||
        		vecSK.size()<Shift.getNumOfAccup("StoreKeeper", accups)/*shift.getStorekeeper()*/ ||
        		vecCA.size() < Shift.getNumOfAccup("TruckDriver", accups)/*shift.getTruck_driver()*/)
        {
            System.out.println("Not enough employees for shift tell the HR manager to hire some");
            return index;
        }
        System.out.println("Choose " + Shift.getNumOfAccup("ShiftManager", accups)/*shift.getShift_manger()*/ + " shiftManegers");
        for (i = 0; i < vecMan.size() ; i++)
        {
            System.out.println((i+1) + ")" + vecMan.elementAt(i).getFname() +" " + vecMan.elementAt(i).getLname() + " " + vecMan.elementAt(i).getId());
        }
        System.out.println("Write your options with ',' between them");
        choice = scanner.nextLine();
        while ((vecOpt1 = validator.validateEmployeeChoice(vecMan.size(),choice, Shift.getNumOfAccup("ShiftManager", accups)/*shift.getShift_manger()*/)) == null)
        {
            if (choice.equals("0")||choice.equals("0"))
                return index;
            System.out.println("choice is not valid, try again:");
            choice = scanner.nextLine();
        }
        bl.reduce(vecOpt1,vecMan,vecCash);
        bl.reduce(vecOpt1,vecMan,vecSK);
        bl.reduce(vecOpt1,vecMan,vecCA);
        if(Shift.getNumOfAccup("Cashier", accups)/*shift.getCashier()*/ > 0) 
        {
        	if(vecCash.size() == 0)
        	{
        		 System.out.println("Not enough employees for shift tell the HR manager to hire some");
        		 return index;
        	}
            System.out.println("Choose " + Shift.getNumOfAccup("Cashier", accups)/*shift.getCashier()*/ + " Cashiers");
            for (i = 0; i < vecCash.size(); i++) {
                System.out.println((i + 1) + ")" + vecCash.elementAt(i).getFname() + " " + vecCash.elementAt(i).getLname() + " "+ vecCash.elementAt(i).getId());
            }
            System.out.println("Write your options with ',' between them");
            choice = scanner.nextLine();
            while ((vecOpt2 = validator.validateEmployeeChoice(vecCash.size(), choice, Shift.getNumOfAccup("Cashier", accups)/*shift.getCashier()*/)) == null) {
                if (choice.equals("~"))
                    return index;
                System.out.println("choice is not valid, try again:");
                choice = scanner.nextLine();
            }
        }
        bl.reduce(vecOpt2,vecCash,vecSK);
        bl.reduce(vecOpt2,vecCash,vecCA);
        if(Shift.getNumOfAccup("StoreKeeper", accups)/*shift.getStorekeeper()*/ > 0) 
        {
        	if(vecSK.size() == 0)
        	{
        		 System.out.println("Not enough employees for shift tell the HR manager to hire some");
        		 return index;
        	}
            System.out.println("Choose " + Shift.getNumOfAccup("StoreKeeper", accups)/*shift.getStorekeeper()*/ + " storeKeepers");
            for (i = 0; i < vecSK.size(); i++) {
                System.out.println((i + 1) + ")" + vecSK.elementAt(i).getFname() + " " + vecSK.elementAt(i).getLname() + " " + vecSK.elementAt(i).getId());
            }
            System.out.println("Write your options with ',' between them");
            choice = scanner.nextLine();
            while ((vecOpt3 = validator.validateEmployeeChoice(vecSK.size(), choice, Shift.getNumOfAccup("StoreKeeper", accups)/*shift.getStorekeeper()*/)) == null) {
                if (choice.equals("~"))
                    return index;
                System.out.println("choice is not valid, try again:");
                choice = scanner.nextLine();
            }
        }
        bl.reduce(vecOpt3,vecSK,vecCA);
        if(Shift.getNumOfAccup("TruckDriver", accups)/*shift.getTruck_driver()*/ > 0) 
        {
        	if(vecCA.size() == 0)
        	{
        		 System.out.println("Not enough employees for shift tell the HR manager to hire some");
        		 return index;
        	}
            System.out.println("Choose " + Shift.getNumOfAccup("TruckDriver", accups)/*shift.getTruck_driver()*/ + " truck drivers");
            for (i = 0; i < vecCA.size(); i++) {
                System.out.println((i + 1) + ")" + vecCA.elementAt(i).getFname() + " " + vecCA.elementAt(i).getLname() + " " + vecCA.elementAt(i).getId());
            }
            System.out.println("Write your options with ',' between them");
            choice = scanner.nextLine();

            while ((vecOpt4 = validator.validateEmployeeChoice(vecCA.size(), choice, Shift.getNumOfAccup("TruckDriver", accups)/*shift.getTruck_driver()*/)) == null) {
                if (choice.equals("~"))
                    return index;
                System.out.println("choice is not valid, try again:");
                choice = scanner.nextLine();
            }
        }

        for (int k = 0; k < vecOpt1.size() ; k++)
            bl.addEmployeeShift(shift,vecMan.elementAt(vecOpt1.elementAt(k) -1),"Manager");
        for (int k = 0; k < vecOpt2.size() ; k++)
            bl.addEmployeeShift(shift,vecCash.elementAt(vecOpt2.elementAt(k) -1),"Cashier");
        for (int k = 0; k < vecOpt3.size() ; k++)
            bl.addEmployeeShift(shift,vecSK.elementAt(vecOpt3.elementAt(k) -1),"StoreKeeper");
        for (int k = 0; k < vecOpt4.size() ; k++)
            bl.addEmployeeShift(shift,vecCA.elementAt(vecOpt4.elementAt(k) -1),"Carrier");
        bl.initShift(shift);
        System.out.println("Employees were succefully added to the shift!");
        return 0;
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
        if(emp.getRank() == Rank.storeManeger || emp.getRank() == Rank.logisticManeger || emp.getRank() == Rank.humenResourceManeger)
        {
        	System.out.println("you dont have permission to update the specializations of store maneger, logistic manager or HR manager.");
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
    
    private void addSpecializiation(Employee emp)
    {
        String spec = getSpecialization();
        if(spec.equals("~"))
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
        if(spec.equals("~"))
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
              if (spec.equals("~"))
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
            case "~":
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
