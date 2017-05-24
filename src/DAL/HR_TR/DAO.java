package DAL.HR_TR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

public abstract class DAO {
	private String url;
	public DAO(String url){
		this.url = url;
	}
	
	protected void createTable(String cmd){
		SQLiteConfig config = new SQLiteConfig();  
        config.enforceForeignKeys(true); 
		try (Connection conn = DriverManager.getConnection(url,config.toProperties())) {
            if (conn != null) {
            	Statement stmt = null;
                // create by the actual DB
                stmt = conn.createStatement();
                stmt.executeUpdate(cmd);
                stmt.close();
            }
 
        } catch (SQLException e) {
        } 
	}
	
	protected Connection connect(){
		Connection conn = null;
		 try {
			 	SQLiteConfig config = new SQLiteConfig();  
		        config.enforceForeignKeys(true); 
			 	if(url == null){
			 		throw new SQLException("no url was found");
			 	}
	            conn = DriverManager.getConnection(url,config.toProperties());
	        } catch (SQLException e) {
	        }
	        return conn;
	}
	
	protected String getUrl(){
		return url;
	}
}
