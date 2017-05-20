package DAL.Suppliers;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import DAL.DALManager;
import SharedClasses.StorageSuppliers.SupplierContact;

public class SupplierContactListDB {
	
	/**
	 * Initial the ContactList table if this table is not exist
	 */

	protected void initContactListDB(){
		String sql = "CREATE TABLE IF NOT EXISTS SuplierContactList\n"
				+ "( ID INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                + "	CompanyId Integer REFERENCES Suppliers(CompanyId) ,\n"
                + "	PhoneNumber varchar(10),\n"
                + "	Name varchar(30)NOT NULL,\n"
                + "	Email varchar(50));";
		SupplierManager.executeSQLCommand(sql);
	}
	/**
	 * @param SC Supplier Contact
	 * add this Contact to DBs
	 */
	
	protected void addSupplierContact(SupplierContact SC){
		String sql = "INSERT INTO SuplierContactList \n"
                + "	(CompanyId, PhoneNumber, Name, Email)\n"
                + "	values("+SC.getCompanyId()+",'"+SC.getPhone()+"','"+SC.getName()+"','"+SC.getEmail()+"');";	
		SupplierManager.executeSQLCommand(sql);
	}
	
	/**
	 * @param CompanyId
	 * @return All contacts of Supplier with Company Id= CompanyId
	 */
	
	protected SupplierContact[] getSupplierContactList(int CompanyId){
		String sql = "SELECT ID,CompanyId,PhoneNumber,Name,Email FROM SuplierContactList where CompanyId is "+CompanyId;
		SupplierContact[] res=null;
		LinkedList<SupplierContact> temp = new LinkedList<SupplierContact>();
		 try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 temp.add(new SupplierContact(rs.getInt("ID"),rs.getInt("CompanyId"), rs.getString("PhoneNumber"),rs.getString("Name") ,rs.getString("Email")));
			 }
			 res= new SupplierContact[temp.size()];
			 for(int i=0;i<res.length;i++){
				 res[i]=temp.pop();
			 }
		 }
		 catch (SQLException e) {
			 //System.out.println(e.getMessage());
			 return new SupplierContact[0]; 
		 }
		return res;
	}
	/**
	 * @param ID
	 * @return the supplier contact that contact.ID= ID
	 */
	
	protected SupplierContact getASupplierContact(int ID){
		String sql = "SELECT ID,CompanyId,PhoneNumber,Name,Email FROM SuplierContactList where ID is "+ID;
		SupplierContact res=null;
		 try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 	rs.next();
				 res=new SupplierContact(rs.getInt("ID"),rs.getInt("CompanyId"), rs.getString("PhoneNumber"),rs.getString("Name") ,rs.getString("Email"));
		 }
		 catch (SQLException e) {
			 //System.out.println(e.getMessage());
		 }
		return res;
	}
	/**
	 * 
	 * @return All contact's ID
	 */
	
	protected int[] getAllContactsID(){
		String sql = "SELECT ID FROM SuplierContactList";
		int[] res=null;
		LinkedList<Integer> temp = new LinkedList<Integer>();
		 try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 temp.add(rs.getInt("ID"));
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
	/**
	 * 
	 * @param ID
	 * remove Contact with this ID
	 */

	protected void removeSupplierContactList(int ID){
		String sql = "DELETE FROM SuplierContactList\n"
				+ " WHERE ID="+ID+";";	
		SupplierManager.executeSQLCommand(sql);
	}
	/**
	 * @param SC
	 * update this SupplierContact
	 */
	protected void updateSupplierContact(SupplierContact SC){
		String sql = "UPDATE SuplierContactList \n"
                + "SET CompanyId="+SC.getCompanyId()+",\n"
                + "PhoneNumber="+SC.getPhone()+",\n"
                + "Name='"+SC.getName()+"',\n"
                + "Email='"+SC.getEmail()+"'\n" 
                + "WHERE ID="+SC.getId(); 
		SupplierManager.executeSQLCommand(sql);
	}
}
