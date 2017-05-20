package DAL.Suppliers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import DAL.DALManager;

public class SuppliersDaysDB {
	
	protected void initWorkerInfoTable(){
		String sql = "CREATE TABLE IF NOT EXISTS SupplierDays (\n"
                + "	CompanyId Integer REFERENCES Suppliers(CompanyId) ,\n"
                + "	Day Integer CHECK(Day > 0 AND Day < 8 ) NOT NULL,\n"
                +"CONSTRAINT PKSupplierDays PRIMARY KEY (CompanyId, Day)\n"
                + ");";
		SupplierManager.executeSQLCommand(sql);
	}
	
	protected int[] getDaysOfSupplier(int CompanyId){
		String sql = "SELECT Day FROM SupplierDays where CompanyId is "+CompanyId;
		int[] res=null;
		LinkedList<Integer> temp = new LinkedList<Integer>();
		 try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 temp.add(rs.getInt("Day"));
			 }
			 res= new int[temp.size()];
			 for(int i=0;i<res.length;i++){
				 res[i]=temp.pop();
			 }
		 }
		 catch (SQLException e) {
			 //System.out.println(e.getMessage());
			 return new int[0]; 
		 }
		return res;
	}
	
	protected void addDayOfSupplier(int CompanyId, int day){
		String sql = "INSERT INTO SupplierDays \n"
                + "	(CompanyId, Day)\n"
                + "	select "+CompanyId+","+day+"\n"
                +"WHERE EXISTS(select * from Supplier\n"
                +"where CompanyId="+CompanyId+" AND Delivery=1)" ;
		SupplierManager.executeSQLCommand(sql);
	}
	
	protected void removeDayOfSupplier(int CompanyId, int day){
		String sql = "DELETE FROM SupplierDays\n"
				+ " WHERE CompanyId="+CompanyId+" AND Day="+day+";\n";	
		SupplierManager.executeSQLCommand(sql);
	}
}
