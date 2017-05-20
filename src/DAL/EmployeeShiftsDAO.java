package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.Shift;
import SharedClasses.TransportsEmployess.Employee.Rank;

public class EmployeeShiftsDAO extends DAO {

	public EmployeeShiftsDAO(String url) {
		super(url);
	}
	 public boolean addEmployeeShift(Shift shift,Employee emp,String specialization)
	    {
	        String sql = "INSERT INTO employeeShifts(Date, Type,STOREADDRESS, ID, Specialization) "
	                   + "VALUES(?,?,?,?,?)";

	        try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(sql))
	        {
	            pstmt.setString(1, shift.getDate());
	            pstmt.setString(2, shift.getType());
	            pstmt.setString(3, shift.getStoreAddress());
	            pstmt.setInt(4, emp.getId());
	            pstmt.setString(5, specialization);
	            pstmt.executeUpdate();
	            return true;
	        }
	        catch (SQLException e)
	        {
	        	return false;
	        }

	    }

	    public boolean deleteEmployeeShift(String date, String type, String address , String id)
	    {
	        String sql = "DELETE FROM employeeShifts WHERE Date = ? AND Type = ? AND STOREADDRESS = ? AND ID = ?";

	        try (Connection conn = this.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql))
	        {
	            stmt.setString(1, date);
	            stmt.setString(2, type);
	            stmt.setString(3, address);
	            stmt.setString(4, id);
	            stmt.executeQuery();
	        }
	        catch (SQLException e)
	        {
  	            return  false;
	        }
	        return  true;
	    }


	    public boolean ShiftWithEmployeelExist(String date, String type , int id)
	    {
	        String sql = "SELECT *"
	                  + " FROM employeeShifts " +
	                     "WHERE Date = ?  AND Type = ? AND ID = ?" ;

	        try (Connection conn = this.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql))
	        {
	            stmt.setString(1, date);
	            stmt.setString(2, type);
	            stmt.setInt(3, id);
	            ResultSet rs = stmt.executeQuery();
	            if(!rs.next())
	                return false;
	            return true;
	        }
	        catch (SQLException e)
	        {
	        }
	        return false;
	    }

	    public boolean deleteEmployeeShifts(String date, String type)
	    {
	        String sql = "DELETE from employeeShifts where Date = ? AND Type = ?";

	        try (Connection conn = this.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql))
	        {
	            stmt.setString(1, date);
	            stmt.setString(2, type);
	            stmt.executeUpdate();
	        }
	        catch (SQLException e)
	        {
	            return  false;
	        }
	        return  true;
	    }
	    
	   public Vector<Employee> getEmployeesInShift(Shift shift){
		   String sql = "SELECT employeeShifts.ID,FIRSTNAME,LASTNAME,SALARY,STARTOFEMPLOYMENTDATE,"+
				 	 "ENDOFEMPLOYMENTDATE,BANKACCOUNT,RANK,STOREADDRESS,RESTDAY"
	                  + " FROM employeeShifts JOIN employees ON employeeShifts.ID = employee.ID " +
	                     "WHERE Date = ?  AND Type = ?" ;
	
	        try (Connection conn = this.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, shift.getDate());
	            stmt.setString(2, shift.getType());
	            ResultSet rs = stmt.executeQuery();
	            Vector<Employee> emps = new Vector<Employee>();
	            while(rs.next())
                {
        			// get the result
        			emps.add(new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
                			rs.getString(5), rs.getString(6), rs.getString(7), 
                			Rank.valueOf(rs.getString(8)),rs.getString(9),rs.getInt(10)));
                }
        		if (emps.size()  > 0 )
        			return emps;  
	            
	        }
	        catch (SQLException e){
	        }
	        return null;
	   }

}
