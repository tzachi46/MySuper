package DAL.Suppliers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import DAL.DALManager;
import SharedClasses.StorageSuppliers.Supplier;

public class SupplierDB {
	
	protected void initWorkerInfoTable(){
		String sql[]=new String[5];
		sql[0] = "CREATE TABLE IF NOT EXISTS TermsOfPayment (\n"
				+ "	Id Integer PRIMARY KEY,\n"
				+ "	text varchar(30) unique);\n";
		sql[1] ="INSERT OR IGNORE INTO TermsOfPayment \n"
                + "	(ID, text)\n"
                + "	values(1,'Cash');\n";
		sql[2] ="INSERT OR IGNORE INTO TermsOfPayment \n"
                + "	(ID, text)\n"
                + "	values(2,'Credit Card');\n";
		sql[3] ="INSERT OR IGNORE INTO TermsOfPayment \n"
                + "	(ID, text)\n"
                + "	values(3,'Checks');\n";
		sql[4] = "CREATE TABLE IF NOT EXISTS Supplier (\n"
                + "	CompanyId Integer PRIMARY KEY,\n"
                + "	Name varchar(30) NOT NULL,\n"
                + "	BankAccountNumber Integer NOT NULL,\n"
                + "	BankBranchId Integer NOT NULL,\n"
                + "	TermsOfPayment varchar(30) REFERENCES TermsOfPayment(text) NOT NULL,\n"
                + "	Delivery Integer not null CHECK(Delivery is 0 OR Delivery is 1 ),\n"
                + "	Address varchar(30) not null \n"
                + ");";
		SupplierManager.executeSQLCommand(sql);
	}

	
	protected boolean addNewSupplier(Supplier supplier){
		String sql = "INSERT INTO Supplier \n"
                + "	(CompanyId,Name , BankAccountNumber, BankBranchId, TermsOfPayment,Delivery,Address)\n"
                + "	values("+supplier.getCompanyId()+",'"+supplier.getName()+"',"+supplier.getBankAccountNumber()+","
                			+supplier.getBankBranchId()+","+"'"+supplier.getTermsOfPayment()+"'"+","+supplier.getDelivery()+",'"+supplier.getAddress()+"');";	
		return SupplierManager.executeSQLCommand(sql);
	}
	
	protected void updateSupplier(Supplier supplier){
		String sql[]= new String[1];
		if(supplier.getDelivery()==0){
			sql= new String[2];
			sql[1]="DELETE FROM SupplierDays\n"
					+ " WHERE CompanyId="+supplier.getCompanyId()+"\n";
		}
		sql[0] = "UPDATE Supplier \n"
                + "SET BankAccountNumber="+supplier.getBankAccountNumber()+",\n"
                + "BankBranchId="+supplier.getBankBranchId()+",\n"
                + "Name='"+supplier.getName()+"',\n"
                + "TermsOfPayment='"+supplier.getTermsOfPayment()+"',\n"
                + "Delivery="+supplier.getDelivery()+",\n" 
                + "Address='"+supplier.getAddress()+"'\n" 
                + "WHERE CompanyId="+supplier.getCompanyId(); 
		SupplierManager.executeSQLCommand(sql);
	}
	
	protected void removeSupplier(int supplierId){
		String[] sql= new String[5];
		sql[0]="DELETE FROM SupplierDays\n"
					+ " WHERE CompanyId="+supplierId+"\n";
		sql[1]="DELETE FROM SuplierContactList\n"
				+ " WHERE CompanyId="+supplierId+"\n";
		sql[2]="DELETE FROM ProductsPrices\n"
				+ " WHERE SupplierId="+supplierId+"\n";
		sql[3]="DELETE FROM SupplierProducts\n"
				+ " WHERE SupplierId="+supplierId+"\n";
		sql[4]="DELETE FROM Supplier\n"
				+ " WHERE CompanyId="+supplierId+"\n";
		SupplierManager.executeSQLCommand(sql);	
	}
	
	protected Supplier getSupplier(int CompanyId){
		String sql = "SELECT CompanyId,Name , BankAccountNumber, BankBranchId,TermsOfPayment,Delivery,Address\n"
				+"FROM Supplier where CompanyId="+CompanyId;
		Supplier res=null;
	    try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	res= new Supplier(rs.getInt("CompanyId"),rs.getString("Name"),rs.getInt("BankAccountNumber"),rs.getInt("BankBranchId"),rs.getString("TermsOfPayment"),rs.getInt("Delivery"),rs.getString("Address"));
	        } catch (Exception e) {
	            return null; 
	        }
		return res;
	}
	
	protected Supplier[] getAllSupplier(){
		String sql = "SELECT CompanyId,Name , BankAccountNumber, BankBranchId,TermsOfPayment,Delivery,Address\n"
				+"FROM Supplier;";
		Supplier[] res=null;
		LinkedList<Supplier> temp = new LinkedList<Supplier>();
	    try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	 while(rs.next()){
	    		 temp.add(new Supplier(rs.getInt("CompanyId"),rs.getString("Name"),rs.getInt("BankAccountNumber"),rs.getInt("BankBranchId"),rs.getString("TermsOfPayment"),rs.getInt("Delivery"),rs.getString("Address")));
	    	 }
	    	 res= new Supplier[temp.size()];
	    	 for(int i=0;i<res.length;i++){
				 res[i]=temp.pop();
			 }
	        } catch (Exception e) {
	            return new Supplier[0]; 
	        }
		return res;
	}
	
	protected int[] getAllSuppliersID(){
		String sql = "SELECT CompanyId FROM Supplier";
		int[] res=null;
		LinkedList<Integer> temp = new LinkedList<Integer>();
		 try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 temp.add(rs.getInt("CompanyId"));
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
	
	
}
