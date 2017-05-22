package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Transport;

public class TransportDAO extends DAO {

	public TransportDAO(String url) {
		super(url);
	}
	
	protected boolean insertTransport(Transport trs){
		if(!this.checkValidDriver(trs))
			return false;
		String sql = "INSERT INTO Transports(DRIVERID,LICENCETRUCK,COMPANYID,"
				+ "DATE,HOUR,TRUCKWEIGHT,SOURCEDOC)"
			 		+ " VALUES(?,?,?,?,?,?,?)";
			 
		try (Connection conn = this.connect();
		    PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    pstmt.setInt(1, trs.getDriverID());
		    pstmt.setInt(2, trs.getTruckNo());
		    pstmt.setInt(3, trs.getCompanyID());
		    pstmt.setString(4, trs.getDateOfDep());
		    pstmt.setString(5, trs.getHourOfDep());
		    pstmt.setDouble(6, trs.getTruckWeight());
		    pstmt.setInt(7, trs.getSourceDoc());
	
		    pstmt.executeUpdate();
		    return true;
		} catch (SQLException e) {
		    return false;
		}
	}

	private boolean checkValidDriver(Transport trs) {
		 String sql = "SELECT DRIVERID"
			 		+ " FROM Transports " +
					 "WHERE HOUR = ? AND DATE = ?";
		        
		        try (Connection conn = this.connect();
		             PreparedStatement stmt = conn.prepareStatement(sql)){
				    stmt.setString(1, trs.getHourOfDep());
				    stmt.setString(2, trs.getDateOfDep());
				    
		            ResultSet rs = stmt.executeQuery();
		        	if(!rs.next()){
		        		return true;
		        	}
		        	// get the result
		        	return rs.getInt(1) != trs.getDriverID();
		           
		        } catch (SQLException e) {
		        }
			return false;
	}

	protected boolean updateTransport(Transport trs){
		 String sql = "UPDATE Transports SET "
	                + "DRIVERID = ? ,"+ " COMPANYID = ? ," +
				  " TRUCKWEIGHT = ? ," +"SOURCEDOC = ?"+
	                " WHERE LICENCETRUCK = ? AND HOUR = ? AND DATE = ?";
	 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding parameters
        	pstmt.setInt(1, trs.getDriverID());
		    pstmt.setInt(5, trs.getTruckNo());
		    pstmt.setInt(2, trs.getCompanyID());
		    pstmt.setString(7, trs.getDateOfDep());
		    pstmt.setString(6, trs.getHourOfDep());
		    pstmt.setDouble(3, trs.getTruckWeight());
		    pstmt.setInt(4, trs.getSourceDoc());
            // update 
            pstmt.executeUpdate();
            //close resource
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
	}
	
	protected Transport fetchTransport(int licenceNo, String hour, String date){

		 String sql = "SELECT DRIVERID, LICENCETRUCK, COMPANYID, DATE, HOUR,"
		 		+ " TRUCKWEIGHT, SOURCEDOC"
		 		+ " FROM Transports " +
				 "WHERE LICENCETRUCK = ? AND HOUR = ? AND DATE = ?";
	        
	        try (Connection conn = this.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)){
	        	stmt.setInt(1, licenceNo);
			    stmt.setString(2, hour);
			    stmt.setString(3, date);
			    
	            ResultSet rs = stmt.executeQuery();
	        	if(!rs.next()){
	        		return null;
	        	}
	        	// get the result
	        	return new Transport(rs.getInt(1), rs.getInt(2), 
	        			rs.getInt(3), rs.getString(4), 
	        			rs.getString(5), rs.getDouble(6),rs.getInt(7));
	           
	        } catch (SQLException e) {
	        }
		return null;
	}
	
	protected boolean deleteTransport(int licenceNo, String hour, String date){
		 String sql = "DELETE FROM Transports WHERE "
		 		+ "LICENCETRUCK = ? AND HOUR = ? AND DATE = ?";
		 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
            // set the corresponding parameters
        	pstmt.setInt(1, licenceNo);
		    pstmt.setString(2, hour);
		    pstmt.setString(3, date);
            // execute the delete statement
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            return false;
        }
	}
	
	public Vector<Pair<String, Integer>> fetchtrucksAndDates(String date){
		Vector<Pair<String,Integer>> vec = new Vector<Pair<String,Integer>>();
		String sql = "SELECT HOUR, LICENCETRUCK "
			 		+ " FROM Transports " +
					 "WHERE DATE = ?";
		 
		try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	 
	            // set the corresponding parameters
	        	pstmt.setString(1, date);
	            // execute the delete statement
	            //pstmt.executeUpdate();
	        	ResultSet rs = pstmt.executeQuery();
	        	while (rs.next())
	            {// get the result
	                vec.add(new Pair<String,Integer>(rs.getString(1),rs.getInt(2)));
	            }
	            if (vec.size() > 0)
	                return vec;
	        } catch (SQLException e) {
	            return vec;
	        }
		return vec;
	}


}
