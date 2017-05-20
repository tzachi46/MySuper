package DAL.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import DAL.DALManager;
import SharedClasses.StorageSuppliers.Category;
/**
 * 
 * Manage the category table in the DB 
 *
 */
public class CategoryDB {
	private static CategoryDB instance;
	/**
	 * Singleton constructor
	 */
	private CategoryDB(){}

	/**
	 * Singleton constructor
	 */
	static CategoryDB getCategoryDB(){
		if(instance==null)
			instance= new CategoryDB();
		return instance;
	}


	/**
	 * initialize the Category's  table
	 */
	void initCategoryTable(){
		String sql = "CREATE TABLE IF NOT EXISTS Category (\n"
				+ "	CategoryID integer PRIMARY KEY,\n"
				+ "	FatherCategoryID integer DEFAULT Null,\n"
				+ "	Name VARCHAR(30) NOT NULL,\n"
				+ "	Discount integer DEFAULT 0 NOT NULL,\n"
				+ "	StartDiscountDate VARCHAR(30) DEFAULT NULL,\n"
				+ "	EndDiscountDate VARCHAR(30) DEFAULT NULL,\n"
				+ "	FOREIGN KEY(FatherCategoryID) REFERENCES Category(CategoryID)\n"
				+ ");";
		String storeCat="INSERT INTO Category(CategoryID,Name,FatherCategoryID,Discount,StartDiscountDate,EndDiscountDate)"
						+" SELECT 0, 'ALL STORE',-1,0,NULL,NULL"
						+" WHERE NOT EXISTS(SELECT CategoryID FROM Category WHERE CategoryID = 0);";
		try (Statement stmt = DALManager.conn.createStatement()) {
			// create a new table
			stmt.execute(sql);
			stmt.execute(storeCat);
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
		}		 
	}
	
	/**
	 * add a new Category to the database
	 * @param category
	 */
	public void addNewCategory(Category category){
		String sql = "INSERT INTO Category \n"
				+ "	(CategoryID, FatherCategoryID, Name, Discount, StartDiscountDate, EndDiscountDate)\n"
				+ "	values("+category.getId()
				+","+category.getFatherId()
				+","+"'"+category.getName()+"'"
				+","+category.getDiscount()
				+","+"'"+category.getDiscountStartTime()+"'"
				+","+"'"+category.getDiscountFinishTime()+"'"
				+");";	
		try (Statement stmt = DALManager.conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
		}
	}

	/**
	 * updates the given Category's information (id cannot be changed!)
	 * @param category
	 */
	public void updateCategory(Category category){
		String sql = "UPDATE Category \n"
				+ "SET Name='"+category.getName()+"',\n"
				+ "FatherCategoryID="+category.getFatherId()+",\n"
				+ "Discount="+category.getDiscount()+",\n"
				+ "StartDiscountDate='"+category.getDiscountStartTime()+"',\n"
				+ "EndDiscountDate='"+category.getDiscountFinishTime()+"'\n" 
				+ "WHERE CategoryID="+category.getId();     
		try (Statement stmt = DALManager.conn.createStatement()) {
			DALManager.conn.createStatement().execute("PRAGMA foreign_keys = ON;");
			stmt.execute(sql);
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
		}
	}
	public void updateAllStore(Category category){
		String sql = "UPDATE Category \n"
				+ "SET Name='"+category.getName()+"',\n"
				+ "Discount="+category.getDiscount()+",\n"
				+ "StartDiscountDate='"+category.getDiscountStartTime()+"',\n"
				+ "EndDiscountDate='"+category.getDiscountFinishTime()+"'\n" 
				+ "WHERE CategoryID="+category.getId();     
		try (Statement stmt = DALManager.conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
		}
	}

	/**
	 * @param ID
	 * @return return category's information with the corresponding ID
	 */
	public Category getCategory(int ID){
		String sql = "SELECT CategoryID, FatherCategoryID, Name ,Discount, StartDiscountDate, EndDiscountDate\n"
				+"FROM Category where CategoryID="+ID;
		Category res=null;
		try (Statement stmt  = DALManager.conn.createStatement();
				ResultSet rs    = stmt.executeQuery(sql)){
			if(rs.getInt("FatherCategoryID")==0)
				res= new Category(rs.getInt("CategoryID"),0,rs.getString("Name"),rs.getInt("Discount"),rs.getString("StartDiscountDate"),rs.getString("EndDiscountDate"));
			else
				res= new Category(rs.getInt("CategoryID"),rs.getInt("FatherCategoryID"),rs.getString("Name"),rs.getInt("Discount"),rs.getString("StartDiscountDate"),rs.getString("EndDiscountDate"));
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
			return null; //TODO:: fix
		}
		return res;
	}
	/**
	 * 
	 * @param name
	 * @return return category's information with the corresponding name
	 */
	public Category getCategoryByName(String name){
		String sql = "SELECT CategoryID, FatherCategoryID, Name ,Discount, StartDiscountDate, EndDiscountDate\n"
				+"FROM Category where Name='"+name+"'";
		Category res=null;
		try (Statement stmt  = DALManager.conn.createStatement();
				ResultSet rs    = stmt.executeQuery(sql)){
			res= new Category(rs.getInt("CategoryID"),rs.getInt("FatherCategoryID"),rs.getString("Name"),rs.getInt("Discount"),rs.getString("StartDiscountDate"),rs.getString("EndDiscountDate"));
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
			return null; //TODO:: fix
		}
		return res;
	}
	/**
	 * @return an array with the id of each category
	 */
	public int[] getAllID(){
		String sql = "SELECT ID FROM Category";
		int[] res=null;
		LinkedList<Integer> temp = new LinkedList<Integer>();
		try (Statement stmt  = DALManager.conn.createStatement();
				ResultSet rs    = stmt.executeQuery(sql)){
			while(rs.next()){
				temp.add(rs.getInt("CategoryID"));
			}
			res= new int[temp.size()];
			for(int i=0;i<res.length;i++){
				res[i]=temp.pop();
			}
		}
		catch (SQLException e) {
			//System.out.println(e.getMessage());
			return new int[0]; //TODO:: fix
		}
		return res;
	}
	/***
	 * 
	 * @return array with all  of the categories
	 */
	public Category[] getAllCategory(){
		String sql = "SELECT CategoryID, FatherCategoryID, Name ,Discount, StartDiscountDate, EndDiscountDate FROM Category";
		Category[] res=null;
		LinkedList<Category> temp = new LinkedList<Category>();
		try (Statement stmt  = DALManager.conn.createStatement();
				ResultSet rs    = stmt.executeQuery(sql)){
			while(rs.next()){
				temp.add(new Category(rs.getInt("CategoryID"),rs.getInt("FatherCategoryID"),rs.getString("Name"),rs.getInt("Discount"),rs.getString("StartDiscountDate"),rs.getString("EndDiscountDate")));
			}
			res= new Category[temp.size()];
			for(int i=0;i<res.length;i++){
				res[i]=temp.pop();
			}
		}
		catch (SQLException e) {
			//System.out.println(e.getMessage());
			return new Category[0]; //TODO:: fix
		}
		return res;
	}
	/**
	 * Delete the category c from the DB
	 * @param c
	 */
	public void deleteCategory(Category c){
		String sql = "Delete FROM Category \n"
                + "WHERE CategoryID="+c.getId();     
		try (Statement stmt = DALManager.conn.createStatement()) {
			//conn.createStatement().execute("PRAGMA foreign_keys = ON;");
            stmt.execute(sql);
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
	}
}
