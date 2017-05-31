package DAL.HR_TR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.sqlite.SQLiteConfig;

import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.TransportsEmployess.Employee.Rank;

public class EmployeeDAO extends DAO {

	public EmployeeDAO(String url) {
		super(url);
	}
	
	protected void createSupplements(String[] cmds){
		SQLiteConfig config = new SQLiteConfig();  
        config.enforceForeignKeys(true); 
        for(int i = 0; i < cmds.length; i++){
			try (Connection conn = DriverManager.getConnection(getUrl(), config.toProperties()))
	        {
	            if (conn != null)
	            {
	                Statement stmt = null;
	                // create by the actual DB
	                stmt = conn.createStatement();
	                stmt.executeUpdate(cmds[i]);
	                stmt.close();
	            }
	
	        }
	        catch (SQLException e)
	        {
	        }
        }
	}
	
	/***************************************************************************************************************/
	protected boolean insertEmployee(Employee emp){
		String sql = "INSERT INTO employees(ID,FIRSTNAME,LASTNAME,SALARY,STARTOFEMPLOYMENTDATE,"+
				 	 "ENDOFEMPLOYMENTDATE,BANKACCOUNT,RANK,STOREADDRESS,RESTDAY)"
				 	 + " VALUES(?,?,?,?,?,?,?,?,?,?)";
		 
		try (Connection conn = this.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
	         pstmt.setInt(1,  emp.getId());
			 pstmt.setString(2, emp.getFname());
	         pstmt.setString(3, emp.getLname());
	         pstmt.setDouble(4, emp.getSalary());
	         pstmt.setString(5,  emp.getStartDate());
	         pstmt.setString(6, emp.getEndDate());
	         pstmt.setString(7, emp.getBankAccount());
	         pstmt.setString(8, emp.getRank().toString());
	         pstmt.setString(9, emp.getWorkAddress());
	         pstmt.setInt(10,  emp.getDayOfRest());

		    pstmt.executeUpdate();
		    return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		    return false;
		}
	}
	
	protected boolean updateEmployee(Employee emp){
		 String sql = "UPDATE employees SET " +
				"FIRSTNAME=?,LASTNAME=?,SALARY=?,STARTOFEMPLOYMENTDATE=?,"+
			 	 "ENDOFEMPLOYMENTDATE=?,BANKACCOUNT =?,RANK = ?, STOREADDRESS = ?, RESTDAY = ?" 
				   + " WHERE ID = ? ";
	 
	     try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
	         // set the corresponding parameters
	         pstmt.setString(1, emp.getFname());
	         pstmt.setString(2, emp.getLname());
	         pstmt.setDouble(3, emp.getSalary());
	         pstmt.setString(4,  emp.getStartDate());
	         pstmt.setString(5, emp.getEndDate());
	         pstmt.setString(6, emp.getBankAccount());
	         pstmt.setString(7, emp.getRank().toString());
	         pstmt.setString(8, emp.getWorkAddress());
	         pstmt.setInt(9,  emp.getDayOfRest());
	         pstmt.setInt(10,  emp.getId());
	
	         // update 
	         pstmt.executeUpdate();
	         //close resource
	         pstmt.close();
	         return true;
	     } catch (SQLException e) {
	         return false;
	     }
	}
	
	protected Employee fetchEmployee(int id){
		 String sql = "SELECT ID,FIRSTNAME,LASTNAME,SALARY,STARTOFEMPLOYMENTDATE,"+
				 	 "ENDOFEMPLOYMENTDATE,BANKACCOUNT,RANK,STOREADDRESS,RESTDAY"
			 		+ " FROM employees " +
					 "WHERE ID = ?";
        try (Connection conn = this.connect();
        		PreparedStatement stmt = conn.prepareStatement(sql)){
        		stmt.setInt(1, id);
        		ResultSet rs = stmt.executeQuery();
        		if(!rs.next()){
        			return null;
        		}
        	// get the result
        	return new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
        			rs.getString(5), rs.getString(6), rs.getString(7), 
        			Rank.valueOf(rs.getString(8)),rs.getString(9), rs.getInt(10));
           
        } catch (SQLException e) {
        }
		return null;
	}
	
	protected boolean deleteEmployee(int id){
		String sql = "DELETE FROM employees WHERE ID = ?";
			 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
	        // set the corresponding parameters
	        pstmt.setInt(1, id);
	        // execute the delete statement
	        pstmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        return false;
	    }
	}
	/***************************************************************************************************************/
	protected boolean insertEmployeeSpeciality(EmployeeSpeciality emps){
		String sql = "INSERT INTO employeeSpecialization(ID,SPECIALITY)"
				 	 + " VALUES(?,?)";
		 
		try (Connection conn = this.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
	         pstmt.setInt(1,  emps.getId());
			 pstmt.setString(2, emps.getSpecialization());
	        
		    pstmt.executeUpdate();
		    return true;
		} catch (SQLException e) {
 		    return false;
		}
	}
	
	protected EmployeeSpeciality fetchEmployeeSpeciality(EmployeeSpeciality emps){
		 String sql = "SELECT ID,SPECIALITY"
			 		+ " FROM employeeSpecialization " +
					 "WHERE ID = ? AND SPECIALITY = ?";
       try (Connection conn = this.connect();
       		PreparedStatement stmt = conn.prepareStatement(sql)){
       		stmt.setInt(1, emps.getId());
       		stmt.setString(2, emps.getSpecialization());
       		ResultSet rs = stmt.executeQuery();
       		if(!rs.next()){
       			return null;
       		}
       	// get the result
       		return new EmployeeSpeciality(rs.getInt(1), rs.getString(2));
          
       } catch (SQLException e) {
    	 }
		return null;
	}
	
	protected Vector<EmployeeSpeciality> fetchAllEmployeeSpecialities(int id){
		 String sql = "SELECT ID,SPECIALITY"
			 		+ " FROM employeeSpecialization " +
					 "WHERE ID = ?";
        try (Connection conn = this.connect();
        		PreparedStatement stmt = conn.prepareStatement(sql)){
        		stmt.setInt(1, id);
        		ResultSet rs = stmt.executeQuery();
        		
    		    Vector<EmployeeSpeciality> vec = new Vector<EmployeeSpeciality>();
        		while(rs.next())
                {
        			// get the result
        			vec.add(new EmployeeSpeciality(id, rs.getString(2)));
                }
        		if (vec.size()  > 0 )
        			return vec;           
        } catch (SQLException e) {
          }
		return null;
	}
	
	protected boolean deleteEmployeeSpeciality(EmployeeSpeciality emps){
		String sql = "DELETE FROM employeeSpecialization WHERE ID = ? AND SPECIALITY = ?";
			 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
	        // set the corresponding parameters
	        pstmt.setInt(1, emps.getId());
	        pstmt.setString(2, emps.getSpecialization());
	        // execute the delete statement
	        pstmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        return false;
	    }
	}
	/***************************************************************************************************************/
	protected boolean insertEmployeeRestriction(EmployeeRestriction empr){
		String sql = "INSERT INTO employeeRestriction(ID,DAY,TYPE)"
				 	 + " VALUES(?,?,?)";
		 
		try (Connection conn = this.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
	         pstmt.setInt(1,  empr.getId());
			 pstmt.setInt(2, empr.getDay());
			 pstmt.setString(3, empr.getType());
	        
		    pstmt.executeUpdate();
		    return true;
		} catch (SQLException e) {
		    return false;
		}
	}
	
	protected EmployeeRestriction fetchEmployeeRestriction(EmployeeRestriction empr){
		 String sql = "SELECT ID,DAY,TYPE"
			 		+ " FROM employeeRestriction " +
					 "WHERE ID = ? AND DAY = ? AND TYPE = ?";
      try (Connection conn = this.connect();
      		PreparedStatement stmt = conn.prepareStatement(sql)){
      		stmt.setInt(1, empr.getId());
      		stmt.setInt(2, empr.getDay());
      		stmt.setString(3, empr.getType());

      		ResultSet rs = stmt.executeQuery();
      		if(!rs.next()){
      			return null;
      		}
      	// get the result
      	return new EmployeeRestriction(rs.getInt(1), rs.getInt(2), rs.getString(3));
         
      } catch (SQLException e) {
      }
		return null;
	}
	
	protected Vector<EmployeeRestriction> fetchAllEmployeeRestrictions(int id){
		 String sql = "SELECT ID,DAY,TYPE"
			 		+ " FROM employeeRestriction " +
					 "WHERE ID = ?";
        try (Connection conn = this.connect();
        		PreparedStatement stmt = conn.prepareStatement(sql)){
        		stmt.setInt(1, id);
        		ResultSet rs = stmt.executeQuery();
        		
    		    Vector<EmployeeRestriction> vec = new Vector<EmployeeRestriction>();
        		while(rs.next())
                {
        			// get the result
        			vec.add(new EmployeeRestriction(id, rs.getInt(2), rs.getString(3)));
                }
        		if (vec.size()  > 0 )
        			return vec;           
        } catch (SQLException e) {
          }
		return null;
	}
	
	protected boolean deleteEmployeeRestriction(EmployeeRestriction empr){
		String sql = "DELETE FROM employeeRestriction WHERE ID = ? AND DAY = ? AND TYPE = ?";
			 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
	        // set the corresponding parameters
	        pstmt.setInt(1, empr.getId());
	        pstmt.setInt(2, empr.getDay());
	        pstmt.setString(3, empr.getType());
	        // execute the delete statement
	        pstmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        return false;
	    }
	}
	/***************************************************************************************************************/
	
	protected Driver fetchDriver(int id){
		 String sql = "SELECT employees.ID,FIRSTNAME,LASTNAME,SALARY,STARTOFEMPLOYMENTDATE,"+
			 	 "ENDOFEMPLOYMENTDATE,BANKACCOUNT,RANK,STOREADDRESS,RESTDAY, drivers.LICENCENUM"
		 		+ " FROM employees JOIN drivers ON employees.ID  = drivers.ID " +
				 "WHERE drivers.ID = ?";
	    try (Connection conn = this.connect();
	    		PreparedStatement stmt = conn.prepareStatement(sql)){
	    		stmt.setInt(1, id);
	    		ResultSet rs = stmt.executeQuery();
	    		if(!rs.next()){
	    			return null;
	    		}
	    	// get the result
	    	return new Driver(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
	    			rs.getString(5), rs.getString(6), rs.getString(7), Rank.valueOf(rs.getString(8)),rs.getString(9), rs.getInt(10),rs.getInt(11));
	       
	    } catch (SQLException e) {
	    	System.out.println(e.getMessage());
    	    }
	return null;
	}
	
	protected boolean insertDriver(Driver driver){
		String sql = "INSERT INTO drivers(ID,LICENCENUM)"
			 	 + " VALUES(?,?)";
	 
		try (Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1,  driver.getId());
			pstmt.setInt(2, driver.getLno());
	       
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
	    return false;
		}
	}
	
	protected boolean updateDriver(int id, int lio){
		 String sql = "UPDATE drivers SET " +
					"LICENCENUM=?" 
					   + " WHERE ID = ? ";
		 
		     try (Connection conn = this.connect();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {
		
		         // set the corresponding parameters
		         pstmt.setInt(1, lio);
		         pstmt.setInt(2, id);
	
		
		         // update 
		         pstmt.executeUpdate();
		         //close resource
		         pstmt.close();
		         return true;
		     } catch (SQLException e) {
		         return false;
		     }
	}

	public boolean deleteOnlyDriver(int id) {
		String sql = "DELETE FROM drivers WHERE ID = ?";
		 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
	        // set the corresponding parameters
	        pstmt.setInt(1, id);
	        // execute the delete statement
	        pstmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        return false;
	    }
	}
}
