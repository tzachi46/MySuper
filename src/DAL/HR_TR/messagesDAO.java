package DAL.HR_TR;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Message;

public class messagesDAO extends DAO {
	public messagesDAO(String url) {
		super(url);
	}
	
	protected boolean insertMessage(Message m){
		String sql = "INSERT INTO Messages(ADDRESS,DATE,ORDERNUMBER,ISHANDLED)"
			 	   + " VALUES(?,?,?,?)";
		try (Connection conn = this.connect();
		    PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    pstmt.setString(1, m.getAddress());
		    pstmt.setString(2, m.getDate());
		    pstmt.setInt(3, m.getOrderNumber());
		    pstmt.setInt(4, m.isHandled() ? 1 : 0);
		    pstmt.executeUpdate();
		    return true;
		} catch (SQLException e) {
			System.out.println(e.getStackTrace());
     		    return false;
		}
	}
	
	protected boolean updateMessage(String address,String date,int OrderNumber)
	{
		 String sql = "UPDATE Messages SET "
	                + "ISHANDLED = 1 "  
				    + "WHERE ADDRESS = ? AND DATE = ? AND ORDERNUMBER = ?";
	 
	     try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
	         // set the corresponding parameters
	    	 pstmt.setString(1, address);
			 pstmt.setString(2, date);
	         // update 
	         pstmt.executeUpdate();
	         //close resource
	         pstmt.close();
	         return true;
	     } catch (SQLException e) {
		         return false;
	     }
	}
	
	protected Vector<Pair<String, Integer>> fetchMessages(String address,boolean isHandled)
	{
		 Vector<Pair<String, Integer>> dates = new Vector<Pair<String, Integer>>();
		 String sql = "SELECT DATE,ORDERNUMBER"
			 		+ "FROM Messages " +
					  "WHERE ADDRESS = ? AND ISHANDLED = ?";
		        
	        try (Connection conn = this.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql))
	        {
	        	stmt.setString(1, address);
	        	stmt.setInt(2, isHandled ? 1 : 0);
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next());
	            	dates.add(new Pair<String, Integer>(rs.getString(1),rs.getInt(2)));
	    
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
	        }
		return dates;
	}

}
