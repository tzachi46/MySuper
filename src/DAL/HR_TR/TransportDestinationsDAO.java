package DAL.HR_TR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.TransportDestination;

public class TransportDestinationsDAO extends DAO {

	public TransportDestinationsDAO(String url) {
		super(url);
	}
	
	protected boolean insertTransDest(TransportDestination trds){
		String sql = "INSERT INTO TransportDestinations"
				+ "(LICENCETRUCK,DATE,HOUR,DOCCODE,HOUROFARR)"
			 		+ " VALUES(?,?,?,?,?)";
			 
		try (Connection conn = this.connect();
		    PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    pstmt.setInt(1, trds.getTruckNo());
		    pstmt.setString(2, trds.getDateOfDep());
		    pstmt.setString(3, trds.getHourOfDep());
		    pstmt.setInt(4, trds.getDocCode());
		    pstmt.setString(5, trds.getHourOfArr());
		    pstmt.executeUpdate();
		    return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
      		    return false;
		}
	}
/*
	public boolean updateTransDest(TransportDestination trds){
		//ITAYYY - Add hourOfArr
		 String sql = "UPDATE TransportDestinations SET "
	                + "DOCCODE = ?"+ 
	                " WHERE LICENCETRUCK = ? AND HOUR = ? AND DATE = ?"
	                + "AND ADDRESSDEST = ? AND HOUROFARR = ? ";
	 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding parameters
            pstmt.setInt(1, trds.getDocCode());
            pstmt.setInt(2, trds.getTruckNo());
            pstmt.setString(3, trds.getHourOfDep());
            pstmt.setString(4, trds.getDateOfDep());
            pstmt.setString(5, trds.getDestination());
		    pstmt.setString(6, trds.getHourOfArr());

            // update 
            pstmt.executeUpdate();
            //close resource
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
	}
*/
	protected TransportDestination fetchTransportDestination(int truckNumbeer, int orderNumber,
			String date, String hour){

		 String sql = "SELECT LICENCETRUCK,DATE,HOUR,DOCCODE,HOUROFARR"
		 		+ " FROM TransportDestinations" +
		 		" WHERE LICENCETRUCK = ? AND HOUR = ? AND DATE = ?"
                + " AND DOCCODE = ?";
	        
	        try (Connection conn = this.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)){
	        	  stmt.setInt(1, truckNumbeer);
	              stmt.setInt(4, orderNumber);
	              stmt.setString(3, date);
	              stmt.setString(2, hour);
	            ResultSet rs = stmt.executeQuery();
	        	if(!rs.next()){
	        		return null;
	        	}
	        	// get the result
	        	return new TransportDestination(rs.getInt(1), rs.getString(2), rs.getString(3),
	        			rs.getInt(5), rs.getString(6));	//ITAYYYYY - Change "" to hourOfArr
	           
	        } catch (SQLException e) {
	        	System.out.println(e.getMessage());
	        }
		return null;
	}
	
	/*protected boolean deleteTransportDestination(int truckNumber, String addressDest,
			String date, String hour){
		 String sql = "DELETE FROM TransportDestinations "
		 		+ "WHERE LICENCETRUCK = ? AND HOUR = ? AND DATE = ?"
                + "AND ADDRESSDEST = ?";
		 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        	 
            // set the corresponding parameters
        	pstmt.setInt(1, truckNumber);
            pstmt.setString(4, addressDest);
            pstmt.setString(3, date);
            pstmt.setString(2, hour);
            // execute the delete statement
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
               return false;
        }
	}*/
	protected Vector<Integer> getTransportOrders(String date, String hour, int truckNo) 
	{
		//ITAYYYY - Add hourOfArr
		 Vector<Integer> orderNumber = new Vector<Integer>();
		 String sql = "SELECT DOCCODE"
			 		+ " FROM TransportDestinations" +
			 		" WHERE LICENCETRUCK = ? AND HOUR = ? AND DATE = ?";
		   try (Connection conn = this.connect();
		             PreparedStatement stmt = conn.prepareStatement(sql)){
		        	  stmt.setInt(1, truckNo);
		              stmt.setString(3, date);
		              stmt.setString(2, hour);
		              //ITAYYY - Add here hourOfArr as part of the string
		            ResultSet rs = stmt.executeQuery();
		        	// get the result
		            while(rs.next())
		            {
		            	orderNumber.addElement(rs.getInt(1));
		        	}
		        	if(orderNumber.size() > 0)
		        		return orderNumber;
		           
		        } catch (SQLException e) {
		 		       System.out.println(e.getMessage());
		        }
			return orderNumber;
	}
	
	public Vector<Pair<String,String>> getHoursOfArrival(Transport trans) {
		 Vector<Pair<String, String>> vec = new Vector<Pair<String,String>>();
	        String sql = "SELECT TransportDestinations.HOUROFARR, TransportDestinations.DOCCODE "
	                + " FROM TransportDestinations "
	                + " WHERE TransportDestinations.DATE = ? AND TransportDestinations.HOUR = ? ";

	   
	        try (Connection conn = this.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, trans.getDateOfDep());
	            stmt.setString(2, trans.getHourOfDep());
	            
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next())
	            {
	                // get the result
	                vec.add(new Pair<String,String>(rs.getString(1),
	                		DAL.Orders.OrderManager.getInstance().getOrder(rs.getInt(2)).getAddres()));
	            }
	            if (vec.size() > 0)
	                return vec;
	        } catch (SQLException e)
	        {
	        	System.out.println(e.getMessage());
	         }
	        return vec;
	}
}
