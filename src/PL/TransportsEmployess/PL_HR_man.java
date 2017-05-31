package PL.TransportsEmployess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import BL.TransportsEmployess.*;
import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.Pair;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.Employee.Rank;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;

/**
 * Created by kazarski on 3/26/17.
 */
public class PL_HR_man
{
	private BL bl;
	private Scanner scanner;
	private Validator validator;
	private PL_Shared pl_shared;

	public PL_HR_man(BL bl, Scanner scanner, Validator validator,PL_Shared pl_shared)
	{
		this.bl = bl;
		this.scanner = scanner;
		this.validator = validator;
		this.pl_shared = pl_shared;
	}


	private String getLicenceDriver(boolean isManger)
	{
		if(isManger)
			System.out.println("Enter driving licence: ");
		else
		{
			System.out.println("Does the Employee has driving licence?");
			System.out.println("~-return, 1-no and continue,else enter the driving licence");
		}
		String lio = scanner.nextLine();
		while(!validator.validateIntInBounds(lio, 2, 99999999))
		{
			if (lio.equals("~"))
				return lio;
			if(!isManger && lio.equals("1"))
				return "";
			System.out.println("licence number is not valid, try again:");
			lio = scanner.nextLine();
		}
		return lio;
	}

	public void reEmploy(String address)
	{
		System.out.println("At any point you can press ~ to return to previous menu");
		String id = pl_shared.getExistingId();
		if(id.equals("~"))
			return;
		Employee emp = bl.fetchEmployee(id);
		if(!address.equals(emp.getWorkAddress()))
		{
			System.out.println("user wasn't in this store");
			return;
		}
		if(!emp.getEndDate().equals(""))
		{
			String currentDate = pl_shared.getCurrentDate();
			boolean updateded = bl.updateEmployee(Integer.toString(emp.getId()), emp.getFname(), emp.getLname(),Double.toString(emp.getSalary()), currentDate, "", emp.getBankAccount(), emp.getRank().toString(),address,emp.getDayOfRest());
			if (updateded)
				System.out.println(emp.getFname() + " " + emp.getLname() + " was reEmployed");
			else
				System.out.println("problem happened");
		}
		else
			System.out.println("employee is still working");
	}

	/** until here we are all clear **/
	/**
	 * 
	 * TO DO: remove add all speciality and the default storekeeper speciality!!
	 * q: how to support the insertion of speciality to new employee?
	 */

	public void addEmployee(String address) 
	{
		System.out.println("At any point you can press ~ to return to previous menu");
		System.out.println("Enter new Employee details :");
		String id = pl_shared.getNotExistingId();
		if(id.equals("~"))
			return;
		String fisrt_name = pl_shared.getName("first name");
		if(fisrt_name.equals("~"))
			return;
		String last_name = pl_shared.getName("last name");
		if(last_name.equals("~"))
			return;	
		String salery = pl_shared.getsalary();
		if(salery.equals("~"))
			return;	 
		Employee.Rank emp_rank = Rank.regular; /*getRank();
        if(emp_rank == null)
        	return;     */
		String BankAccount = pl_shared.getBankDetails();
		if(BankAccount.equals("~"))
			return;	 
		int day = pl_shared.GetDay("rest day");
		if(day == 0)
			return;
		String currentDate = pl_shared.getCurrentDate();
		String lio = getLicenceDriver(false/*emp_rank == Rank.storeManeger : it has no meaning cause you only insert regular */);
		if(lio.equals("~"))
			return;

		boolean added = bl.addEmployee(id,salery,fisrt_name,last_name,currentDate,"",emp_rank.toString(),BankAccount,address,day);
		if (added)
		{
			pl_shared.makeDayOfWorkUnavilable(Integer.parseInt(id),day);
			System.out.println(fisrt_name + " " + last_name + "  was added to the system");
			if(!lio.equals(""))
				bl.insertDriver(new Driver(Integer.parseInt(id), fisrt_name, last_name,Double.parseDouble(salery), currentDate, "", BankAccount, emp_rank, address,day,Integer.parseInt(lio)));
			/*  if (emp_rank.toString().equals(Employee.Rank.storeManeger.toString()))
            	addAllSpeciality(Integer.parseInt(id));
            else
                bl.addEmployeeSpeciality(Integer.parseInt(id), "StoreKeeper"); */
		}
		else
			System.out.println("problem happend");
	}
	/**
	 * ask boris about the line 175
	 * 
	 * */
	public void UpdateEmployee(Employee adminEmp)
	{
		System.out.println("At any point you can press ~ to return to previous menu");
		System.out.println("Enter id of Employee to update:");
		String id = pl_shared.getExistingId();
		if(id.equals("~"))
			return;
		Employee emp = bl.fetchEmployee(id);
		if(!adminEmp.getWorkAddress().equals(emp.getWorkAddress()))
		{
			System.out.println("user not exist in this store");
			return;
		}
		if(emp.getRank() == Rank.logisticManager)
		{
			System.out.println("you dont have the permission to update a logistic manager:");
			return;
		}
		while (true)
		{
			System.out.println("Choose Option: ");
			System.out.println("1) update salery");
			System.out.println("2) update bank Account");
			System.out.println("3) Fire employee");
			System.out.println("4) Update licence type");
			System.out.println("~) return to previous menu");

			String choice = scanner.nextLine();
			if(!handelUpdateChoice(emp, choice))
				break;
		}
	}

	private void commitUpdate(Employee employee)
	{
		boolean updateded = bl.updateEmployee(Integer.toString(employee.getId()),employee.getFname(),employee.getLname(),Double.toString(employee.getSalary()),employee.getStartDate(),employee.getEndDate(),employee.getBankAccount(),employee.getRank().toString(),employee.getWorkAddress(),employee.getDayOfRest());
		if(updateded)
			System.out.println(employee.getFname() + " " + employee.getLname()  + " was updated");
		else
			System.out.println("problem happened");
	}

	public boolean handelUpdateChoice(Employee emp, String choice)
	{
		switch (choice)
		{
		case "1":
		{
			updateSalery(emp);
			break;
		}
		case "2":
		{
			updateBankAccount(emp);
			break;     
		}
		case "3":
		{
			fireEmployee(emp);
			break;
		}
		case "4":
		{
			updateLicenceType(emp);
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
		return  true;
	}

	private void updateLicenceType(Employee emp) 
	{
		/*String lio = getLicenceDriver(false);
		if(lio.equals("~"))
			return;
		Vector<Transport>vec=bl.getRelevantTransports(emp.getId());
		if(vec!=null){
			for(int i=0;i<vec.size();i++){   
				if(!ComperDates(vec.get(i).getDateOfDep())){
					System.out.println("Driver has an open transportation!");
					return;
				}
			}
		}
           if(lio.equals("")&&bl.fetchDriver(emp.getId())!=null){
        	   //Remove from drivers
        	   bl.deleteDriver(emp.getId());
        	   EmployeeSpeciality[] specs = bl.getSpecsOf(emp.getId());
        	   boolean found = false;
        	   for(int i=0; i<specs.length; i++){
        		   if(specs[i].getSpecialization().equals("Carrier")){
        			   found = true;
        		   }
        	   }
        	   if(found){
        		   bl.deleteEmployeeSpeciality(emp.getId(), "Carrier");
        	   }
           }
           else if(bl.fetchDriver(emp.getId())==null)
			bl.insertDriver(new Driver(emp.getId(), emp.getFname(), emp.getLname(),emp.getSalary(), emp.getStartDate(), emp.getEndDate(), emp.getBankAccount(), emp.getRank(), emp.getWorkAddress(),emp.getDayOfRest(),Integer.parseInt(lio)));
		else
			bl.updateDriver(emp.getId(),Integer.parseInt(lio));  */
		
			String lio = getLicenceDriver(false);
			if(lio.equals("~"))
				return;
			Vector<Transport>vec=bl.getRelevantTransports(emp.getId());
			if(vec!=null){
				for(int i=0;i<vec.size();i++){   
					if(!ComperDates(vec.get(i).getDateOfDep())){
						System.out.println("Driver has an open transportation!");
						return;
					}
				}
			}
			boolean wasDriver = bl.fetchDriver(emp.getId())!=null;
			boolean isDriverNow = !lio.equals("");
			if(!isDriverNow && wasDriver){
	    	   //Remove from drivers
	    	   bl.deleteDriver(emp.getId());
	    	   EmployeeSpeciality[] specs = bl.getSpecsOf(emp.getId());
	    	   boolean found = false;
	    	   for(int i=0; i<specs.length; i++){
	    		   if(specs[i].getSpecialization().equals("Carrier")){
	    			   found = true;
	    		   }
	    	   }
	    	   if(found){
	    		   bl.deleteEmployeeSpeciality(emp.getId(), "Carrier");
	    	   }
	       }
	       else if(!wasDriver && isDriverNow)
	    	   bl.insertDriver(new Driver(emp.getId(), emp.getFname(), emp.getLname(),emp.getSalary(), emp.getStartDate(), emp.getEndDate(), emp.getBankAccount(), emp.getRank(), emp.getWorkAddress(),emp.getDayOfRest(),Integer.parseInt(lio)));
	       else if(wasDriver && isDriverNow)
				bl.updateDriver(emp.getId(),Integer.parseInt(lio));    
	}

	/**
	 * return true if start < finish
	 * @param start
	 * @param finish
	 * @return
	 */
	boolean ComperDates(String start){
		int startInt=GetDayAsInt2(start);
		int finishInt=getTodayDateAsInt();
		if(startInt<=finishInt)
			return true;
		return false;
	}
	/**
	 * @return the day in int format
	 */
	private int getTodayDateAsInt(){
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		String today=dateFormat.format(date).toString();
		return GetDayAsInt(today);
	}
	private int GetDayAsInt2(String s){
		String[] parts = s.split("\\/");
		String ans="";
		for(int i=parts.length-1;i>=0;i--)
			ans+=parts[i];
		return Integer.parseInt(ans);
	}
	/**
	 * reverses the date in order to get it's decimal representation 
	 * @param s
	 * @return date as int
	 */
	private int GetDayAsInt(String s){
		String[] parts = s.split("\\.");
		String ans="";
		for(int i=parts.length-1;i>=0;i--)
			ans+=parts[i];
		return Integer.parseInt(ans);
	}
	private void updateSalery(Employee emp)
	{
		String salary = pl_shared.getsalary();
		if(salary.equals("~"))
			return;
		emp.setSalary(Double.parseDouble(salary));
		commitUpdate(emp);
	}

	private void updateBankAccount(Employee emp)
	{
		String BankAccount = pl_shared.getBankDetails();
		if(BankAccount.equals("~"))
			return;
		emp.setBankAccount(BankAccount);
		commitUpdate(emp);   
	}

	private void fireEmployee(Employee emp)
	{
		String currentDate = pl_shared.getCurrentDate();
		Vector<Transport> trans = bl.getRelevantTransports(emp.getId());
		boolean futureTransport = false;
		ListIterator<Transport> listIterator = trans.listIterator();
		while (listIterator.hasNext()) {
			Transport curr = listIterator.next();
			if(bl.compareDates(currentDate, curr.getDateOfDep())==-1){
				//If current date<transport's date = future transport -> can't delete
				futureTransport=true;
			}
		}
		if(futureTransport){
			System.out.println("Can't fire a driver of a future transport.");
			return;
		}
		emp.setEndOfEmploymentDate(currentDate);
		commitUpdate(emp);
	}

	private void printEmpDetails(Employee emp)
	{
		System.out.println("id: " + emp.getId());
		System.out.println("name: " + emp.getFname() + " " + emp.getLname());
		System.out.println("Rank: " + emp.getRank().toString());
		System.out.println("Salary: " + emp.getSalary());
		System.out.println("Start Date: " + emp.getStartDate());
		System.out.println("leaving date: " + emp.getEndDate());
		System.out.println("store address: " + emp.getWorkAddress());
		System.out.println("Bank Account and employment details: " + emp.getBankAccount());
		Driver driver = bl.fetchDriver(emp.getId());
		if(driver != null)
			System.out.println("driving licence: " + driver.getLno());  
	}

	public void showEmployeeDetails(Employee adminEmp)
	{
		System.out.println("At any point you can press ~ to return to previous menu");
		String id = pl_shared.getExistingId();
		if (id.equals("~"))
			return;
		Employee emp = bl.fetchEmployee(id);
		if(!adminEmp.getWorkAddress().equals(emp.getWorkAddress()))
		{
			System.out.println("user not exist in this store");
			return;
		}
		if(emp.getRank() == Rank.logisticManager || emp.getRank() == Rank.humenResourceManager)
		{
			System.out.println("you dont have the permission to view logistic manager's or HR manager's details.");
			return;
		}
		printEmpDetails(emp);
	}


	///////////////SHIFT//////////////////////////////////////////////////

	private int getNumOfEmployees(String toPrint, int min,int max)
	{
    	 System.out.println("Enter number of " + toPrint + ", should be at least " + min +", press ~ to Return");
         String num = scanner.nextLine();
         while(!validator.validateIntInBounds(num, min, max))
         {
             if (num.equals("~"))
                 return -1;
             System.out.println("this number is not valid, try again:");
             num = scanner.nextLine();
         }
         return Integer.parseInt(num);
    }
    
    public  void addShift(String address)
    {
    	String date,type;
        int man,cash,storeKeep,carrier;
        System.out.println("At any point you can press ~ to return to previous menu");
        while(true)
        {
	        date = pl_shared.getShiftDate();
	        if(date.equals("~"))
	        	return;
	        type = pl_shared.getShiftType();
	        if(type.equals("~"))
	        	return;
	        if(!checkIfShiftExist(date,type,address))
	        	break;
	        else
	        	System.out.println("shift already exist in this stote, try again");
        } 
        man = getNumOfEmployees("shift managers", 1, 100);
        if(man == -1)
        	return;
        cash = getNumOfEmployees("Cashiers", 0, 100);
        if(cash == -1)
        	return;
        storeKeep = getNumOfEmployees("store keepers", 0, 100);
        if(storeKeep == -1)
        	return;
        carrier = getNumOfEmployees("truck drivers", 0, 100);
        if(carrier == -1)
        	return;
        boolean added = bl.addShift(date,type,man,cash,storeKeep,carrier,address);
        if(added)
            System.out.println(type + " shift was added at " + date );
        else
            System.out.println("problem happend");
    }

	private boolean checkIfShiftExist(String date, String type, String workAddress) 
	{
		return bl.fetchShift(date, type, workAddress) != null;
	}

	public void deleteShift(String address)
    {
        while (true)
        {
       
            String date = pl_shared.getShiftDate();
            if (date.equals("~"))
                return;
            String type = pl_shared.getShiftType();
            if (type.equals("~"))
                return;   
            if (checkIfShiftExist(date, type, address))
            {
                if (bl.deleteShift(date, type, address))
                {
                    System.out.println(type + " shift at " + date +" was deleted");
                    break;
                }
                else
                    System.out.println("Problem happened");

            }
            else
            	System.out.println("Shift not exist, try again");
        }
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
            System.out.println("~)return, 1)left, 2)right,3)manual");
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
            if (!validator.validateIntInBounds(option, 0, 3) && !option.equals("~"))
                System.out.println("invalid input, try again");
            else 
            {
                if (option.equals("~"))
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


	public void viewMessages(String workAddress) {
		while (true)
	    {
	         System.out.println("Choose Option: ");
	         System.out.println("1) view not handled messages");
	         System.out.println("2) view handled messages");
	         System.out.println("~) go back to previous menu");
	         
	         String choice = scanner.nextLine();
	         if(!handleViewMessagesChoice(workAddress, choice))
	        	 break;
	    }
	}


	private boolean handleViewMessagesChoice(String workAddress, String choice) {
		switch (choice)
        {
			case "~":
			{
				return false;
			}
            case "1":
            {
            	Vector<String> newMessages = bl.getNotHandledMessages(workAddress);
        		for(int i=0; i<newMessages.size(); i++){
        			System.out.println(newMessages.elementAt(i));
        		}
        		break;
            }
            case "2":
            {
            	Vector<String> oldMessages = bl.getHandledMessages(workAddress);
            	for(int i=0; i<oldMessages.size(); i++){
        			System.out.println(oldMessages.elementAt(i));
        		}
            	break;
            }
            default:
            {
                System.out.println("invalid input, try again");
                break;
            }
        }
        return  true;
	}	
}