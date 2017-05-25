package DAL.HR_TR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import SharedClasses.TransportsEmployess.Site;;

public class SiteDAO extends DAO {

	public SiteDAO(String url) {
		super(url);
	}
	
	protected boolean insertSite(Site site){
		String sql = "INSERT INTO Sites(ADDRESS,CONTACTNAME,PHONENO,AREACODE)"
		 		+ " VALUES(?,?,?,?)";
		 
		try (Connection conn = this.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
		    pstmt.setString(1, site.getAddress());
		    pstmt.setString(2, site.getContactName());
		    pstmt.setString(3, site.getPhoneNum());
		    pstmt.setInt(4, site.getAreaCode());
		    pstmt.executeUpdate();
		    return true;
		} catch (SQLException e) 
		{
 		    return false;
		}
	}
	
	protected boolean updateSite(Site site){
		 String sql = "UPDATE Sites SET "
	                + "ADDRESS = ? ,"+ "CONTACTNAME = ? ," + 
				   " PHONENO = ?,"+ "AREACODE = ?"
				   + " WHERE ADDRESS = ? ";
	 
	     try (Connection conn = this.connect();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
	         // set the corresponding parameters
	         pstmt.setString(1, site.getAddress());
	         pstmt.setString(2, site.getContactName());
	         pstmt.setString(3, site.getPhoneNum());
	         pstmt.setInt(4,  site.getAreaCode());
	         pstmt.setString(5, site.getAddress());
	         
	
	         // update 
	         pstmt.executeUpdate();
	         //close resource
	         pstmt.close();
	         return true;
	     } catch (SQLException e) {
	         return false;
	     }
	}
	
	protected Site fetchSite(String address){
		 String sql = "SELECT ADDRESS, CONTACTNAME, PHONENO, AREACODE"
			 		+ " FROM Sites " +
					 "WHERE ADDRESS = ?";
		        
        try (Connection conn = this.connect();
        		PreparedStatement stmt = conn.prepareStatement(sql)){
        	stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();
        	if(!rs.next()){
        		return null;
        	}
        	// get the result
        	return new Site(rs.getString(1), rs.getString(3), rs.getString(2), rs.getInt(4));
           
        } catch (SQLException e) {
        }
		return null;
	}
	
	protected boolean deleteSite(String address){
		String sql = "DELETE FROM Sites WHERE ADDRESS = ?";
			 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
	        // set the corresponding parameters
	        pstmt.setString(1, address);
	        // execute the delete statement
	        pstmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
 	        return false;
	    }
	}


}
