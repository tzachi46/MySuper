package DAL.HR_TR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.Message;
import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Shift;

public class Repository {
	TruckDAO trucks;
	SiteDAO sites;
	EmployeeDAO employees;
	ShiftDAO shifts;
	EmployeeShiftsDAO empShifts;
	TransportDAO transports;
	TransportDestinationsDAO transDests;
	messagesDAO messages;
	public Repository(String url){
		trucks = new TruckDAO(url);
		sites = new SiteDAO(url);
		employees = new EmployeeDAO(url);
		shifts = new ShiftDAO(url);
		empShifts = new EmployeeShiftsDAO(url);
		transports = new TransportDAO(url);
		transDests = new TransportDestinationsDAO(url);
		messages = new messagesDAO(url);
	}
	
	protected void createDB(){
		trucks.createTable("CREATE TABLE IF NOT EXISTS Trucks "
				        + "(TRUCKNO INT PRIMARY KEY NOT NULL,"
				         + "MODEL TEXT NOT NULL, "
				         + "WEIGHT REAL	NOT NULL, "
				         + "MAXWEIGHT REAL NOT NULL, "
				         + "LICENCETYPE	INT NOT NULL)");
		
		sites.createTable("CREATE TABLE IF NOT EXISTS Sites "
					   + "(ADDRESS TEXT PRIMARY KEY NOT NULL, "
						+ "CONTACTNAME	TEXT	NOT NULL, "
						+ "PHONENO   TEXT	NOT NULL, "
						+ "AREACODE INT NOT NULL)");
	
		employees.createTable("CREATE TABLE IF NOT EXISTS employees " 
	            		   + "(ID INT PRIMARY KEY NOT NULL, " 
	            			+ "FIRSTNAME TEXT NOT NULL, " 
	            		    + "LASTNAME TEXT NOT NULL, " 
	            			+ "SALARY REAL NOT NULL, " 
	            			+ "STARTOFEMPLOYMENTDATE TEXT NOT NULL, " 
	            			+ "ENDOFEMPLOYMENTDATE TEXT, " 
	            			+ "BANKACCOUNT TEXT NOT NULL, " 
	            			+ "RANK TEXT NOT NULL,"
	            			+ "STOREADDRESS TEXT NOT NULL,"
	            			+ "RESTDAY INT NOT NULL,"
	            			+ "FOREIGN KEY (STOREADDRESS) REFERENCES sites (ADDRESS) ON DELETE CASCADE)");
		
		String[] cmds ={"CREATE TABLE IF NOT EXISTS employeeSpecialization " 
	                 + "(ID INTEGER NOT NULL ," 
	                  + "SPECIALITY TEXT NOT NULL, " 
	                  + "PRIMARY KEY (ID,SPECIALITY), " 
	                  + "FOREIGN KEY (ID) REFERENCES employees (ID) ON DELETE CASCADE)",
	                    "CREATE TABLE IF NOT EXISTS employeeRestriction " +
	                   "(ID	INTEGER	NOT NULL, " +
	                    "DAY INTEGER NOT NULL, " +
	                    "TYPE TEXT NOT NULL, " +
	                    "PRIMARY KEY (ID,DAY,TYPE)" +
	                    "FOREIGN KEY (ID) REFERENCES employees (ID)ON DELETE CASCADE)",
	                    "CREATE TABLE IF NOT EXISTS drivers"
	                  + "(ID INTEGER NOT NULL, "
	                  + "LICENCENUM INTEGER NOT NULL, "
	                  + "PRIMARY KEY(ID), "
	                  + "FOREIGN KEY (ID) REFERENCES employees (ID) ON DELETE CASCADE)"};
		employees.createSupplements(cmds);
		
		shifts.createTable("CREATE TABLE IF NOT EXISTS shifts " +
                "(Date TEXT NOT NULL, " +
                 "Type TEXT NOT NULL, " +
                 "Day INTEGER NOT NULL, " +
                 "Init INTEGER NOT NULL, " +
                 "STOREADDRESS TEXT NOT NULL," +
                 "FOREIGN KEY (STOREADDRESS) REFERENCES sites (ADDRESS) ON DELETE CASCADE," +
                 "PRIMARY KEY (Date,Type,STOREADDRESS))");
		String[] commds = {"CREATE TABLE IF NOT EXISTS accupations"
				+ "(DATE TEXT NOT NULL, " +
                 "TYPE TEXT NOT NULL, " +
				   "STOREADDRESS TEXT NOT NULL, "
				 + "ACCUPATION TEXT NOT NULL,"
				 + "NUMOFEMPS INTEGER NOT NULL,"
				 + "PRIMARY KEY(DATE,TYPE,STOREADDRESS,ACCUPATION),"
				 + "FOREIGN KEY(DATE,TYPE,STOREADDRESS) REFERENCES shifts(Date,Type,STOREADDRESS) ON DELETE CASCADE)"};
		shifts.createSupplements(commds);
		
		empShifts.createTable("CREATE TABLE employeeShifts " +
							 "(Date	TEXT	NOT NULL, " +
							  "Type	TEXT	NOT NULL, " +
							  "ID	INTEGER	NOT NULL," +
							  "STOREADDRESS TEXT NOT NULL, "+
							  "Specialization	TEXT   NOT NULL, " +
							  "PRIMARY KEY(Date,Type,STOREADDRESS,ID), " +
							  "FOREIGN KEY (Date,Type,STOREADDRESS) REFERENCES shifts (DATE,TYPE,STOREADDRESS) ON DELETE CASCADE," +
							  "FOREIGN KEY (ID) REFERENCES employees (ID) ON DELETE CASCADE)");
		
		transports.createTable("CREATE TABLE IF NOT EXISTS Transports " +
                			"(DRIVERID	INT		NOT		NULL, " +
                			"	LICENCETRUCK	INT		NOT NULL, " + 
                			"	COMPANYID   INT	NOT NULL, " + 
                			"	DATE   TEXT	NOT NULL, " + 
                			"	HOUR   TEXT	NOT NULL, " + 
                			"SOURCEDOC 	INT 	NOT 	NULL, "
                			+ "	TRUCKWEIGHT		REAL	NOT NULL," 
                			+ " PRIMARY KEY (LICENCETRUCK,DATE,HOUR), "
                			+ " FOREIGN KEY (DRIVERID) REFERENCES Drivers(ID) ON DELETE CASCADE ON UPDATE CASCADE, "
                			+ " FOREIGN KEY (LICENCETRUCK) REFERENCES Trucks(TRUCKNO) ON DELETE CASCADE ON UPDATE CASCADE)");
                			
		
		transDests.createTable("CREATE TABLE IF NOT EXISTS TransportDestinations " +
                			"( LICENCETRUCK		INT		NOT NULL, " + 
                			"	DATE   		TEXT	NOT NULL, " + 
                			"	HOUR   		TEXT	NOT NULL, " + 
                			"	DOCCODE		INT		NOT NULL, " +
                			"   HOUROFARR TEXT NOT NULL, " 
                			+" PRIMARY KEY(LICENCETRUCK, DATE, HOUR, DOCCODE),"
                			+ "FOREIGN KEY(LICENCETRUCK,DATE,HOUR) REFERENCES Transports(LICENCETRUCK,DATE,HOUR) ON DELETE CASCADE,"
                			+ "FOREIGN KEY(DOCCODE) REFERENCES Orders(OrderNumber) ON DELETE CASCADE)");
	
		messages.createTable("CREATE TABLE IF NOT EXISTS Messages " +
                			"( 	ADDRESS   	TEXT	NOT NULL, " + 
                			"	DATE		TEXT	NOT NULL, " + 
                			"	ORDERNUMBER INT		NOT NULL, " + 
                			"	ISHANDLED 	INT		NOT NULL DEFAULT 0, "
                			+" PRIMARY KEY(DATE, ADDRESS, ORDERNUMBER),"
                			+ "FOREIGN KEY(ADDRESS) REFERENCES Sites(ADDRESS) ON DELETE CASCADE,"
                			+ "FOREIGN KEY(ORDERNUMBER) REFERENCES Orders(OrderNumber) ON DELETE CASCADE)");
	}

	public TransportDAO getTransports() {
		return transports;
	}

	public TransportDestinationsDAO getTransDests() {
		return transDests;
	}

	public TruckDAO getTrucks() {
		return trucks;
	}

	public SiteDAO getSites() {
		return sites;
	}

	public EmployeeDAO getEmployees() {
		return employees;
	}

	public ShiftDAO getShifts() {
		return shifts;
	}

	public EmployeeShiftsDAO getEmpShifts() {
		return empShifts;
	}
	
	public messagesDAO getMessages() {
		return messages;
	}

	public boolean checkLicenceAndWeight(int driverID, int truckNumber, double weight) {
		 String sql = "SELECT Drivers.LICENCENUM, Trucks.LICENCETYPE, Trucks.MAXWEIGHT"
			 		+ " FROM (Trucks JOIN Transports ON Trucks.LICENCETYPE = Transports.LICENCETRUCK) "
			 		+ "JOIN Drivers ON Transports.DRIVERID = Drivers.ID " +
					 "WHERE Drivers.ID = ? AND Trucks.LICENCETYPE = ?";
		        
		        try (Connection conn = this.employees.connect();
		             PreparedStatement stmt = conn.prepareStatement(sql)){
		        	stmt.setInt(1, driverID);
		        	stmt.setInt(2, truckNumber);
		            ResultSet rs = stmt.executeQuery();
		        	if(!rs.next()){
		        		return false;
		        	}
		        	// drivers licence >= trucks licence
		        	return (rs.getInt(3) >= weight && rs.getInt(1) >= rs.getInt(2));
		           
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		return false;
	}
	
	  public Vector<Employee> getPossibleWorkers(Shift shift, String specialization)
	    {
	        Vector<Employee> vec = new Vector<Employee>();
	        String sql = "SELECT employees.ID, employees.FIRSTNAME, employees.LASTNAME, employees.SALARY, employees.STARTOFEMPLOYMENTDATE, employees.ENDOFEMPLOYMENTDATE,"
	        		+ " employees.RANK , employees.BANKACCOUNT, employees.STOREADDRESS, employees.RESTDAY"
	                + " FROM employees INNER JOIN employeeSpecialization ON employeeSpecialization.ID = employees.ID"
	                + " WHERE employeeSpecialization.SPECIALITY = ? AND employees.STOREADDRESS = ?"
	                + " AND employees.ID NOT IN (SELECT employeeRestriction.ID FROM employeeRestriction WHERE  employeeRestriction.DAY = ? AND employeeRestriction.TYPE = ?)";

	   
	        try (Connection conn = this.shifts.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, specialization);
	            stmt.setString(2, shift.getStoreAddress());
	            stmt.setInt(3, shift.getDay());
	            stmt.setString(4, shift.getType());
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next())
	            {
	                // get the result
	                vec.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6),
	                		rs.getString(8), Employee.Rank.valueOf(rs.getString(7)), rs.getString(9),rs.getInt(10)));
	            }
	            if (vec.size() > 0)
	                return vec;
	        } catch (SQLException e)
	        {
	        }
	        return vec;
	    }
	  
	  public Vector<Pair<Shift,String>> fetchAllEmployeeShifts(int id)
	    {
	        Vector<Pair<Shift,String>> vec = new Vector<Pair<Shift,String>>();
	        String sql = "SELECT shifts.Date, shifts.Type, shifts.Day, shifts.Init, "
	        		+ "shifts.STOREADDRESS, employeeShifts.Specialization"
	                + " FROM shifts, employeeShifts"
	                + " WHERE shifts.Date = employeeShifts.Date AND shifts.Type = employeeShifts.Type AND employeeShifts.ID = ?";

	        try (Connection conn = this.employees.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, id);
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next())
	            {// get the result
	                vec.add(new Pair<Shift,String>(new Shift( rs.getString(1),  rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)),rs.getString(6)));
	            }
	            if (vec.size() > 0)
	                return vec;
	        } catch (SQLException e)
	        {
	        }
	        return vec;
	    }
	  
	  public Vector<Driver> fetchAvailableDrivers(String addressStore, String date, String shiftType){
		  Vector<Driver> vec = new Vector<Driver>();
	        String sql = "SELECT employees.ID, employees.FIRSTNAME, employees.LASTNAME, employees.SALARY, employees.STARTOFEMPLOYMENTDATE, employees.ENDOFEMPLOYMENTDATE,"
	        		+ " employees.BANKACCOUNT, employees.RANK, employees.STOREADDRESS,employees.RESTDAY, drivers.LICENCENUM"
	                + " FROM (employees JOIN drivers ON employees.ID = drivers.ID) JOIN employeeShifts ON employees.ID = employeeShifts.ID"
	                + " WHERE employees.STOREADDRESS = ? AND employeeShifts.Date = ? AND employeeShifts.specialization = ? AND employeeShifts.Type = ? AND "
	                + "employees.ID NOT IN(SELECT Transports.DRIVERID FROM Transports WHERE Transports.DATE = ?)";

	        try (Connection conn = this.employees.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, addressStore);
	            stmt.setString(2, date);
	            stmt.setString(3, "Carrier");
	            stmt.setString(4, shiftType);
	            stmt.setString(5, date);
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next())
	            {// get the result
	                vec.add(new Driver( rs.getInt(1),  rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getString(6) ,rs.getString(7) , Employee.Rank.valueOf(rs.getString(8)), rs.getString(9),rs.getInt(10), rs.getInt(11)));
	            }
	            if (vec.size() > 0)
	                return vec;
	        } catch (SQLException e)
	        {
	        }
	        return vec;
	  }
	  
	  public Vector<Employee> fetchAvailableStoreKeepers(String addressStore, String date, String shiftType){
		  Vector<Employee> vec = new Vector<Employee>();
	        String sql = "SELECT employees.ID, employees.FIRSTNAME, employees.LASTNAME, employees.SALARY, employees.STARTOFEMPLOYMENTDATE, employees.ENDOFEMPLOYMENTDATE,"
	        		+ " employees.BANKACCOUNT, employees.RANK , employees.STOREADDRESS, employees.RESTDAY "
	                + " FROM (employees JOIN employeeShifts ON employees.ID = employeeShifts.ID)"
	                + " WHERE employees.STOREADDRESS = ? AND employeeShifts.Date = ?  AND employeeShifts.Type = ? ";
/*AND employeeShifts.specialization = ?*/
	        try (Connection conn = this.employees.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, addressStore);
	            stmt.setString(2, date);
	           // stmt.setString(3, "StoreKeeper");
	            stmt.setString(3/*4*/, shiftType);
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next())
	            {// get the result
	                vec.add(new Employee( rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5),
	                		rs.getString(6) ,rs.getString(7) , Employee.Rank.valueOf(rs.getString(8)), rs.getString(9), rs.getInt(10)));
	            }
	            if (vec.size() > 0)
	                return vec;
	        } catch (SQLException e)
	        {
	        	System.out.println("error");
	        }
	        return vec;
	  }

	public Vector<Pair<Employee, String>> fetchEmployeesInShift(String address, Shift shift) {
		 Vector<Pair<Employee,String>> vec = new Vector<Pair<Employee,String>>();
	        String sql = "SELECT employees.ID, employees.FIRSTNAME, employees.LASTNAME, employees.SALARY, employees.STARTOFEMPLOYMENTDATE, employees.ENDOFEMPLOYMENTDATE,"
	        		+ " employees.BANKACCOUNT, employees.RANK , employees.STOREADDRESS, employees.RESTDAY, employeeShifts.Specialization"
	                + " FROM employees JOIN employeeShifts ON employees.ID = employeeShifts.ID "
	                + " WHERE employees.STOREADDRESS = ? AND employeeShifts.Date = ? AND employeeShifts.Type = ?";
	        // + " AND NOT EXIST (SELECT * FROM employeeConstraints WHERE employeeConstraints.ID = employees.ID AND Day = ? AND Type = ?)";

	        try (Connection conn = this.employees.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, address);
	            stmt.setString(2, shift.getDate());
	            stmt.setString(3, shift.getType());
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next())
	            {// get the result
	                vec.add(new Pair<Employee,String>(new Employee( rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5),
	                		rs.getString(6) ,rs.getString(7) , Employee.Rank.valueOf(rs.getString(8)), rs.getString(9), rs.getInt(10)),rs.getString(11)));
	            }
	            if (vec.size() > 0)
	                return vec;
	        } catch (SQLException e)
	        {
	        }
	        return vec;
	}
	  
	public Vector<Integer> fetchAvailableTrucks(String date, String shift){
    	Vector<Integer> truckNums = new Vector<Integer>(); 
    	String sql = "SELECT Trucks.TRUCKNO"
			 		+ " FROM Trucks "
					+ "WHERE Trucks.TRUCKNO NOT IN (SELECT Trucks.TRUCKNO"
												+ " FROM Trucks JOIN Transports ON "
												+ "Trucks.TRUCKNO = Transports.LICENCETRUCK"
												+ " WHERE Transports.DATE = ?)";
		        
		        try (Connection conn = this.transports.connect();
		             PreparedStatement stmt = conn.prepareStatement(sql)){
		        	stmt.setString(1, date);
		            ResultSet rs = stmt.executeQuery();
		            while (rs.next())
		            {// get the result
		            	truckNums.add(rs.getInt(1));
		            }
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		Vector<Pair<Integer, String>> trucksAndTheirSchedual = new Vector<Pair<Integer,String>>();
        sql =	 "SELECT Trucks.TRUCKNO, Transports.HOUR"
				+ " FROM Trucks JOIN Transports ON "
				+ "Trucks.TRUCKNO = Transports.LICENCETRUCK"
				+ " WHERE Transports.DATE = ?";

	        try (Connection conn = this.transports.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)){
	        	stmt.setString(1, date);

	            ResultSet rs = stmt.executeQuery();
	            while (rs.next())
	            {// get the result
	            	trucksAndTheirSchedual.add(new Pair<Integer,String>(rs.getInt(1),rs.getString(2)));
	            }
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
	        }
	    for(Pair<Integer, String> pair : trucksAndTheirSchedual){
	    	String type="morning";
			int hour = Integer.parseInt(pair.getValue().substring(0, 2));
			if(hour<24 && hour>=12)
				type="evening";
	    	if(!type.equals(shift)){
	    		truckNums.add(pair.getKey());
	    	}
	    }
	    for(Integer trk : truckNums){
	    	System.out.println("truck no. " + trk);
	    }
	    
	    return truckNums;
    }
}
