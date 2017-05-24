package DAL;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DALManager {
	
	
	private static DALManager instance;
	public static Connection conn;
	public static String DBLocation =  "jdbc:sqlite:" + Paths.get(".").toAbsolutePath().normalize().toString()
			+ "\\DataBase.db";
	/* OLD URL :   "jdbc:sqlite:"+System.getProperty("user.dir")+"/DataBase.db";*/
		
	private DALManager(){
		connect();
		instance =this;
	}
	
	public static DALManager getInstance(){
		if(instance==null){
			instance=new DALManager();
		}
		return instance;
	}

	
	public static void connect() {
	    try {
	    	conn = DriverManager.getConnection(DALManager.DBLocation);
	    } catch (SQLException e) {
	    	////System.out.println(e.getMessage());
	    }
    }
	
	
}
