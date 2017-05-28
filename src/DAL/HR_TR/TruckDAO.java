package DAL.HR_TR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import SharedClasses.TransportsEmployess.Truck;

public class TruckDAO extends DAO {

	public TruckDAO(String url) {
		super(url);
	}
	
	protected boolean insertTruck(Truck trk){
		String sql = "INSERT INTO Trucks(TRUCKNO,MODEL,WEIGHT,MAXWEIGHT,LICENCETYPE)"
			 	   + " VALUES(?,?,?,?,?)";
			 
		try (Connection conn = this.connect();
		    PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    pstmt.setInt(1, trk.getTruckNo());
		    pstmt.setString(2, trk.getModel());
		    pstmt.setDouble(3, trk.getWeight());
		    pstmt.setDouble(4, trk.getMaxWeight());
		    pstmt.setInt(5, trk.getLicenceType());
		    pstmt.executeUpdate();
		    return true;
		} catch (SQLException e) {
     		    return false;
		}
	}
	
	protected boolean updateTruck(Truck trk){
		 String sql = "UPDATE Trucks SET "
	                + "MODEL = ? , " + "WEIGHT = ? , "  
				    + "MAXWEIGHT = ?, LICENCETYPE = ? "  
	                + "WHERE TRUCKNO = ?";
	 
	     try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
	         // set the corresponding parameters
	         pstmt.setString(1, trk.getModel());
	         pstmt.setDouble(2, trk.getWeight());
	         pstmt.setDouble(3, trk.getMaxWeight());
	         pstmt.setInt(4, trk.getLicenceType());
	         pstmt.setInt(5, trk.getTruckNo());
	
	         // update 
	         pstmt.executeUpdate();
	         //close resource
	         pstmt.close();
	         return true;
	     } catch (SQLException e) {
		         return false;
	     }
	}
	
	protected Truck fetchTruck(int truckNo){
		 String sql = "SELECT MODEL, WEIGHT, MAXWEIGHT, LICENCETYPE"
			 		+ " FROM Trucks " +
					 "WHERE TRUCKNO = ?";
		        
	        try (Connection conn = this.connect();
	             PreparedStatement stmt = conn.prepareStatement(sql)){
	        	stmt.setInt(1, truckNo);
	            ResultSet rs = stmt.executeQuery();
	        	if(!rs.next()){
	        		return null;
	        	}
	        	// get the result
	        	return new Truck(truckNo, rs.getString(1), 
	        			rs.getDouble(2), rs.getDouble(3), rs.getInt(4));
	           
	        } catch (SQLException e) {
	        	////System.out.println(e.getMessage());
	        }
		return null;
	}
	
	protected boolean deleteTruck(int truckNo){
		String sql = "DELETE FROM Trucks WHERE TRUCKNO = ?";
			 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
        // set the corresponding parameters
        pstmt.setInt(1, truckNo);
        // execute the delete statement
        pstmt.executeUpdate();
        return true;
    } catch (SQLException e) {
          return false;
    }
	}
}
