package BL.TransportsEmployess;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import DAL.DALhrtr_Interface;
import DAL.HR_TR.DALhrtrManager;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Employee.Rank;

/**
 * Created by Yoni Kazarski on 24/03/2017.
 */
public class BLEmployees_imp
{
    private DALhrtr_Interface dal;


    public BLEmployees_imp()
    {
        dal = DALhrtrManager.getDALImp();
    }


    public Vector<Shift> getUptoDate(Vector<Shift> vec)
    {
        return vec;
    }

    public boolean addEmployee(String id,String salary, String private_name, String last_name, String start_of_employment_date, String end_of_employment_date ,String rank, String bankAccount,String workAddress, int day) {
        Employee newEmp = new Employee(Integer.parseInt(id), private_name, last_name,Double.parseDouble(salary), start_of_employment_date, end_of_employment_date,bankAccount, Rank.valueOf(rank),workAddress,day);
        boolean inserted = dal.insertEmployee(newEmp);
        if(!inserted)
            return false;
        return true;
    }


    public Shift fetchShift(String type, String date, String address)
    {
        return  dal.fetchShift(type, date, address);
    }
    
   
    public boolean updateEmployee(String id, String private_name, String last_name, String salary, String start_of_employment_date, String end_of_employment_date,  String bankAccount, String rank ,String address,int day) 
    {
        Employee emp = dal.fetchEmployee(Integer.parseInt(id));
        emp.setFname(private_name);
        emp.setId(Integer.parseInt(id));
        emp.setLname(last_name);
        emp.setEndOfEmploymentDate(end_of_employment_date);
        emp.setStartDate(start_of_employment_date);
        emp.setSalary(Double.parseDouble(salary));
        emp.setRank(Employee.Rank.valueOf(rank));
        emp.setBankAccount(bankAccount);
        emp.setDayOfRest(day);

        return dal.updateEmployee(emp);
    }

  
    public boolean deleteEmployee(String id) {
        return dal.deleteEmployee(Integer.parseInt(id));
    }

  
    public Employee fetchEmployee(String id)
    {
        Employee emp = dal.fetchEmployee(Integer.parseInt(id));
        return emp;
    }



    public boolean initShift(Shift shift)
    {
        return  dal.initShift(shift);
    }

  
    public boolean addShift(String date, String type, int manager,int cashier,int storekeeper ,int carrier, String address)
    {
        int day = 0;
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date date2 = format.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date2);
            day = cal.get(Calendar.DAY_OF_WEEK);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        Shift shift = new Shift(date, type, day, 0,address);
        Vector<Pair<String,Integer>> vec = new Vector<Pair<String,Integer>>();
        vec.addElement(new Pair<String,Integer>("Cashier", cashier));
        vec.addElement(new Pair<String,Integer>("ShiftManager", manager));
        vec.addElement(new Pair<String,Integer>("TruckDriver", carrier));
        vec.addElement(new Pair<String,Integer>("StoreKeeper", storekeeper));
        return dal.insertShift(shift, vec);
    }

   
    public boolean deleteShift(String date, String type,String address)
    {
        return dal.deleteShift(date, type, address);
    }

    public Vector<EmployeeRestriction> fetchAllEmployeeRestrictions(int id)
    {
        return  dal.fetchAllEmployeeRestrictions(id);
    }


    //region Specializations
   
    public boolean addEmployeeSpeciality(int id, String specialization)
    {
    	EmployeeSpeciality employeeSpecialization = new EmployeeSpeciality(id,specialization);
        return dal.insertEmployeeSpeciality(employeeSpecialization);
    }
 
    public boolean deleteEmployeeSpeciality(int id, String specialization)
    {
    	EmployeeSpeciality employeeSpeciality = new EmployeeSpeciality(id,specialization);
    	if(dal.fetchEmployeeSpeciality(employeeSpeciality)== null)
    		return false;
        return dal.deleteEmployeeSpeciality(employeeSpeciality); 
    }
  
  
    public EmployeeSpeciality[] getSpecsOf(int id) 
    {
        Vector<EmployeeSpeciality>  vecOfSpec = dal.fetchAllEmployeeSpeciality(id);
        if(vecOfSpec != null){
        	EmployeeSpeciality[] empSpec = new EmployeeSpeciality[vecOfSpec.size()];
            for(int i=0; i<vecOfSpec.size(); i++){
                empSpec[i] = vecOfSpec.elementAt(i);
            }
            return empSpec;
        }
        return new EmployeeSpeciality[0];
    }

    //region Constraints
 
    public boolean insertEmployeeRestriction(int id, int day, String type) {
        EmployeeRestriction empr = new EmployeeRestriction(id, day, type);
        return dal.insertEmployeeRestriction(empr); 
    }


    public boolean deleteEmployeeRestriction(int id, int day, String type)
    {
        EmployeeRestriction empRest = new EmployeeRestriction(id, day, type);
        return dal.deleteEmployeeRestriction(empRest);
    }
    //end regions

  
    public EmployeeRestriction fetchEmployeeRestriction(int id, int day, String type)
    {
    	EmployeeRestriction empr = new EmployeeRestriction(id, day, type);
        return dal.fetchEmployeeRestriction(empr);
    }

 
    public Vector<Shift> getEmptyShifts(String address)
    {
        return dal.fetchEmptyShift(address);
    }


    public void sortDates(Vector<Shift> notSorted)
    {
        notSorted.sort(new Comparator<Shift>() {
            @Override
            public int compare(Shift o1, Shift o2)
            {
                int day1 = Integer.parseInt(o1.getDate().substring(0, 2));
                int month1 = Integer.parseInt(o1.getDate().substring(3, 5));
                int year1 = Integer.parseInt(o1.getDate().substring(6));
                int day2 = Integer.parseInt(o2.getDate().substring(0, 2));
                int month2 = Integer.parseInt(o2.getDate().substring(3, 5));
                int year2 = Integer.parseInt(o2.getDate().substring(6));
                if(year1 > year2)
                    return 1;
                if(year1 < year2)
                    return -1;
                if(month1 > month2)
                    return 1;
                if(month1 < month2)
                    return -1;
                if(day1 > day2)
                    return 1;
                if(day1 < day2)
                    return -1;
                if (o1.getType().equals("morning"))
                    return 1;
                return 0;
            }
        });
    }

   
    public void sortDatesPair(Vector<Pair<Shift,String>> vec) {

        vec.sort(new Comparator<Pair<Shift,String>>() {
            @Override
            public int compare(Pair<Shift,String> o1, Pair<Shift,String> o2)
            {
                int day1 = Integer.parseInt(((Shift)o1.getKey()).getDate().substring(0, 2));
                int month1 = Integer.parseInt(((Shift)o1.getKey()).getDate().substring(3, 5));
                int year1 = Integer.parseInt(((Shift)o1.getKey()).getDate().substring(6, 10));
                int day2 = Integer.parseInt(((Shift)o2.getKey()).getDate().substring(0, 2));
                int month2 = Integer.parseInt(((Shift)o2.getKey()).getDate().substring(3, 5));
                int year2 = Integer.parseInt(((Shift)o2.getKey()).getDate().substring(6, 10));
                if(year1 > year2)
                    return 1;
                if(year1 < year2)
                    return -1;
                if(month1 > month2)
                    return 1;
                if(month1 < month2)
                    return -1;
                if(day1 > day2)
                    return 1;
                if(day1 < day2)
                    return -1;
                if (((Shift)o1.getKey()).getType().equals("morning"))
                    return 1;
                return 0;
            }
        });
    }

 
    public Vector<Employee> getPossibleWorkers(Shift shift, String specialization)
    {
        return dal.getPossibleWorkers(shift, specialization);
        /**
         * 
         * itay if you dont undatrstand what this function do look at my code in the drive
         */
    }

   
    public boolean addEmployeeShift(Shift shift, Employee emp, String specialization)
    {
        return  dal.addEmployeeShift(shift, emp,specialization);
    }

   
    public void reduce(Vector<Integer> vecOpt, Vector<Employee> vecMan, Vector<Employee> vec)
    {
        for (int i = 0; i< vecOpt.size(); i++)
        {
            int id = vecMan.elementAt(vecOpt.elementAt(i) -1).getId();
            for (int j = 0; j< vec.size(); j++)
            {
                if(vec.elementAt(j).getId()  == id)
                    vec.remove(j);
            }
        }
    }

    
    public Vector<Pair<Shift,String>> fetchAllEmployeeShifts(int id)
    {
        return  dal.fetchAllEmployeeShifts(id);
    }


	public Vector<Pair<String, Integer>> fetchShiftAccupations(Shift shift) {
		return dal.fetchShiftAccupations(shift);
	}


	public Vector<Shift> fetchInitializedShifts(String address) 
	{
		return dal.fetchInitializedShifts(address);
	}


	public Vector<Pair<Employee, String>> fetchEmployeesInShift(String address, Shift shift) {
		return dal.fetchEmployeesInShift(address,shift);
	}

	
	public boolean cheakAvailableStoreKeepers(String addressStore, String date, String shiftType) 
	{
		return dal.fetchAvailableStoreKeepers(addressStore, date, shiftType).size() != 0;
	}    
}