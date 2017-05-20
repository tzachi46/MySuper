package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.sqlite.SQLiteConfig;

import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Shift;

public class ShiftDAO extends DAO {

	public ShiftDAO(String url) {
		super(url);
	}
	
	public boolean insertShift(Shift shift, Vector<Pair<String,Integer>> accups)
    {

        String sql = "INSERT INTO shifts(Date, Type, Day, Init, STOREADDRESS)"
                + " VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, shift.getDate());
            pstmt.setString(2, shift.getType());
            pstmt.setInt(3, shift.getDay());
            pstmt.setInt(4, shift.getInit());
            pstmt.setString(5, shift.getStoreAddress());
            pstmt.executeUpdate();
            boolean ans = true;
            for(Pair<String,Integer> p : accups){
            	ans = ans && this.insertAccupation(shift, p.getKey(), p.getValue());
            }
            return ans;
        }
        catch (SQLException e)
        {
            return false;
        }

    }

    private boolean insertAccupation(Shift shift, String accup, int count) {
    	String sql = "INSERT INTO accupations(DATE,TYPE,STOREADDRESS,ACCUPATION,NUMOFEMPS)"
                + " VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, shift.getDate());
            pstmt.setString(2, shift.getType());
            pstmt.setString(3, shift.getStoreAddress());
            pstmt.setString(4, accup);
            pstmt.setInt(5, count);
            pstmt.executeUpdate();
            return true;
        }
        catch (SQLException e)
        {
             return false;
        }
	}

	public boolean deleteShift(String date, String type, String address)
    {
        String sql = "DELETE FROM shifts WHERE Date = ? AND Type = ? AND STOREADDRESS = ?";

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, date);
            stmt.setString(2, type);
            stmt.setString(3, address);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
             return  false;
        }
        return  true;
    }

    public boolean initShift(Shift shift)
    {
        String sql = "UPDATE shifts SET "
                + "Init = 1 "
                + "WHERE Date = ? AND Type = ? AND STOREADDRESS = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding parameters
            pstmt.setString(1, shift.getDate());
            pstmt.setString(2, shift.getType());
            pstmt.setString(3, shift.getStoreAddress());
            // update
            pstmt.executeUpdate();
            //close resource
            pstmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public Shift fetchShift(String date, String type, String address)
    {
        String sql = "SELECT Date, Type, Day, Init, STOREADDRESS"
                + " FROM shifts " +
                "WHERE Date = ? AND Type = ? AND STOREADDRESS = ?" ;

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, date);
            stmt.setString(2, type);
            stmt.setString(3, address);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next())
                return null;

            // get the result
            return new Shift(date, type, rs.getInt(3), rs.getInt(4), rs.getString(5));
        }
        catch (SQLException e)
        {
        }
        return null;
    }

    public Vector<Shift> fetchEmptyShifts(String address)
    {
        Vector<Shift> vec = new Vector<>();
        String sql = "SELECT Date, Type, Day, Init, STOREADDRESS"
                + " FROM shifts " +
                  "WHERE Init = 0 AND STOREADDRESS = '" + address +"'" ;

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                vec.add(new Shift(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
            if (vec.size() == 0)
                return null;
            else return vec;
        }
        catch (SQLException e)
        {
         }
        return null;
    }
	
    public Vector<Shift> fetchInitializedShifts()
    {
        Vector<Shift> vec = new Vector<>();
        String sql = "SELECT Date, Type, Day, Init, STOREADDRESS"
                + " FROM shifts " +
                  "WHERE Init = 1" ;

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                vec.add(new Shift(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)));
            if (vec.size() == 0)
                return null;
            else return vec;
        }
        catch (SQLException e)
        {
        }
        return null;
    }

	public void createSupplements(String[] commds) {
		SQLiteConfig config = new SQLiteConfig();  
        config.enforceForeignKeys(true); 
        for(int i = 0; i < commds.length; i++){
			try (Connection conn = DriverManager.getConnection(getUrl(), config.toProperties()))
	        {
	            if (conn != null)
	            {
	                Statement stmt = null;
	                // create by the actual DB
	                stmt = conn.createStatement();
	                stmt.executeUpdate(commds[i]);
	                stmt.close();
	            }
	
	        }
	        catch (SQLException e)
	        {
	        }
        }		
	}
	
	public Vector<Pair<String,Integer>> fetchShiftAccupation(Shift shift){
		Vector<Pair<String,Integer>> vec = new Vector<Pair<String,Integer>>();
        String sql = "SELECT ACCUPATION, NUMOFEMPS"
                + " FROM accupations " +
                  "WHERE accupations.DATE = ? AND accupations.TYPE = ? AND accupations.STOREADDRESS = ?" ;

        try (Connection conn = this.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
        	stmt.setString(1, shift.getDate());
        	stmt.setString(2, shift.getType());
        	stmt.setString(3, shift.getStoreAddress());
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                vec.add(new Pair<String,Integer>(rs.getString(1), rs.getInt(2)));
            if (vec.size() == 0)
                return null;
            else return vec;
        }
        catch (SQLException e)
        {
        }
        return null;
	}

}
