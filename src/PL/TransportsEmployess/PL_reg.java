package PL.TransportsEmployess;

import java.util.Scanner;
import java.util.Vector;

import BL.TransportsEmployess.*;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Transport;

/**
 * Created by kazarski on 3/26/17.
 */
public class PL_reg
{
    private BL bl;
    Validator validator;
    private PL_Shared pl_shared;
    private Scanner scanner;

    
    public PL_reg(BL bl, Validator validator,PL_Shared pl_shared ) {

        this.bl = bl;
        this.validator = validator;
        this.pl_shared = pl_shared;
        scanner = new Scanner(System.in);
    }

    
    private String getDayAsString(int day)
    {
    	if (day == 1)
            return "sunday";
        if (day == 2)
            return "monday";
        if (day == 3)
            return "tuesday";
        if (day == 4)
            return "wednesday";
        if (day == 5)
        	return "thursday";
        if (day == 6)
        	return "friday";
        return "saturday";
    }
    
    public void addConstraint(int id)
    {
        System.out.println("At any point you can press 0 to return to previous menu");
        int day = pl_shared.GetDay("day");
        if(day == 0)
        	return;	
        String type = pl_shared.getShiftType();
        if (type.equals("~"))
        	return;
        String sday = getDayAsString(day);
      
        boolean added = bl.insertEmployeeRestriction(id, day, type);
        if(added)
            System.out.println(id + "'s constraint at "+ type + " shift in  " + sday+ " was added." );
        else
            System.out.println("problem happend");
    }

    private boolean cheackIfRestrictionExist(int id, int day ,String type)
    {
    	return bl.fetchEmployeeRestriction(id,day,type) != null;
    }
    
    public void removeConstraint(Employee emp)
    {
        while (true) 
        {
        	System.out.println("At any point you can press 0 to return to previous menu");
        	int day = pl_shared.GetDay("day");
        	if(day == 0)
        		return;	
        	if(day != emp.getDayOfRest())
        	{
	        	String type = pl_shared.getShiftType();
	            if (type.equals("~"))
	            	return;
	            String sday = getDayAsString(day);
	               
	            if (cheackIfRestrictionExist(emp.getId(),day,type))
	            {
	                boolean removed = bl.deleteEmployeeRestriction(emp.getId(), day, type);
	                if (removed)
	                {
	                    System.out.println(emp.getId() + "'s constraint at " + type + " shift in day " + sday + " was removed.");
	                    break;
	                }
	                else
	                    System.out.println("problem happend, or Constraint does not exist in the system");
	            }
	            else
	                System.out.println("Constraint not exist, try again");
	        }
        	else
        		System.out.println("cant work on rest day.");
        }
    }

    public void showAllConstraints(int id)
    {
        Vector<EmployeeRestriction> vec = bl.fetchAllEmployeeRestrictions(id);
        if (vec == null)
            System.out.println("you dont have any constraints");
        else
        {
            for (int i =0; i< vec.size();i++)
            {
                int day = vec.elementAt(i).getDay();
                String sday = getDayAsString(day);
                System.out.println("can't work at " + sday + " in " + vec.elementAt(i).getType() + " shift");
            }
        }
    }

    public void showAllShifts(int id)
    {
        Vector<Pair<Shift,String>> vec = bl.fetchAllEmployeeShifts(id);
        bl.sortDatesPair(vec);
        if (vec.size()==0)
            System.out.println("you dont have any shifts");
        else
        {
            for (int i =0; i< vec.size();i++)
                System.out.println((((Shift)(vec.elementAt(i)).getKey()).getType()) + " shift at " + ((Shift)vec.elementAt(i).getKey()).getDate() + " : " + ((String)vec.elementAt(i).getValue()));
        }
    }
    
    private boolean handleRestrictionChoice(String choice, Employee emp){
    	switch(choice)
    	{
	    	case "1":
	        {
	            addConstraint(emp.getId());
	            break;
	        }
	        case "2":
	        {
	            removeConstraint(emp);
	            break;
	        }
	        case "3":
	        {
	            showAllConstraints(emp.getId());
	            break;
	        }
	        case "~":
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


	public boolean restrictionMenu(Employee emp) {
		while (true)
        {
            System.out.println("Please choose an option:");
            System.out.println("1) Add restriction");
            System.out.println("2) Remove restriction");
            System.out.println("3) show current restrictions");
            System.out.println("~) go to previous menu");
            String choice = scanner.nextLine();

            if(!handleRestrictionChoice(choice,emp))
                break;
        }

        return true;
	}

	/*
     * NEW (edited 26.5 by Ofir)
     */
	public void showRelevantTransports(int id) {
		Vector<Transport> transports = getRelevantTransports(id);
		bl.sortTrans(transports);
		int i=0;
		if(transports.size()==0){ //Shouldn't be true , checked before. Just in case...
			System.out.println("You have no transports to drive");
			return;
		}
		while(true){
			System.out.println("choose option");
            System.out.println("~)return, 1)left, 2)right, 3)show destinations");
            System.out.println("***********************************");
            System.out.println(transports.elementAt(i).toString());
            System.out.println("***********************************");
            String option = scanner.nextLine();
            if (!validator.validateIntInBounds(option, 0, 3)&&!option.equals("~"))
                System.out.println("invalid input, try again");
            else 
            {
                if (option.equals("~"))
                    break;
                if (option.equals("1")) {
                    if (i == 0)
                        System.out.println("earlier transports not exist");
                    else
                        i--;
                }
                if (option.equals("2")) {
                    if (i == transports.size() - 1)
                        System.out.println("later transports not exist");
                    else
                        i++;
                }
                if (option.equals("3")){
                	System.out.println("Transport's destinations:");
                	Vector<Pair<String,String>> dests = bl.getHoursOfArrival(transports.elementAt(i));
                	bl.sortDests(dests);
                	for(int j=0; j<dests.size(); j++){
                		System.out.println("Hour of arrival: "+ dests.elementAt(j).getKey());
                		System.out.println("Store's address: "+ dests.elementAt(j).getValue());
                		System.out.println();
                	}
                }
            }
		}
	}

	/*
     * NEW (edited 26.5 by Ofir): get transports whose driver's id = id
     */
	protected Vector<Transport> getRelevantTransports(int id) {
		return bl.getRelevantTransports(id);
	}
    

}
