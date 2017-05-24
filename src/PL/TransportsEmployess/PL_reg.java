package PL.TransportsEmployess;

import java.util.Vector;

import BL.TransportsEmployess.*;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Shift;

/**
 * Created by kazarski on 3/26/17.
 */
public class PL_reg
{
    private BL bl;
    Validator validator;
    private PL_Shared pl_shared;
    
    public PL_reg(BL bl, Validator validator,PL_Shared pl_shared ) {

        this.bl = bl;
        this.validator = validator;
        this.pl_shared = pl_shared;
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
}
