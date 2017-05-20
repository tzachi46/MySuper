package DAL.Inventory;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import DAL.DALManager;
import DAL.Orders.OrderManager;
import DAL.Suppliers.SupplierManager;
import SharedClasses.StorageSuppliers.Quartet;
import SharedClasses.StorageSuppliers.Category;
import SharedClasses.StorageSuppliers.Product;
public class InvDALManager {

	private static InvDALManager instance;
	private ProductDB productDb;
	private CategoryDB categoryDb;
	/**
	 * Singleton constructor
	 */
	private InvDALManager(){
		DALManager.getInstance();
		productDb= ProductDB.getProductDB();
		categoryDb=CategoryDB.getCategoryDB();
	}
	public static InvDALManager getInstance(){
		if(instance==null){
			instance=new InvDALManager();
		}
		return instance;
	}
	/**
	 * add product to the DB
	 * @param product
	 */
	public void addNewProduct(Product product){
		productDb.initProductInfoTable();
		productDb.addNewProduct(product);
	}
	/**
	 * add new category to the DB
	 * @param category
	 */
	public void addNewCategory(Category category){
		categoryDb.initCategoryTable();
		categoryDb.addNewCategory(category);
	}
	/**
	 * update Category that already exist in the DB
	 * @param category
	 */
	public void updateCategory(Category category){
		categoryDb.initCategoryTable();
		if(category.getId()==0)
			categoryDb.updateAllStore(category);
		else
			categoryDb.updateCategory(category);
	}
	/**
	 * update Product that already exist in the DB
	 * @param product
	 */
	public void updateProduct(Product product){
		productDb.initProductInfoTable();
		productDb.updateProduct(product);
	}
	/**
	 * delete p from the DB
	 * @param p
	 */
	public void deleteProduct(Product p){
		productDb.initProductInfoTable();
		OrderManager.getInstance().removeProduct(p);
		SupplierManager.getInstance().removeProduct(p);
		productDb.deleteProduct(p);
		ProductSellingCostDB.getProductBuyingCostDB().deleteProduct(p);
	}
	/**
	 * delete c from the DB
	 * @param c
	 */
	public void deleteCategory(Category c){
		categoryDb.initCategoryTable();
		categoryDb.deleteCategory(c);
	}
	/**
	 * get category by ID compare 
	 * @param ID
	 * @return
	 */
	public Category getCategory(int ID){
		categoryDb.initCategoryTable();
		return categoryDb.getCategory(ID);
	}
	/**+
	 * get category by name compare 
	 * @param name
	 * @return
	 */
	public Category getCategoryByName(String name){
		categoryDb.initCategoryTable();
		return categoryDb.getCategoryByName(name);
	}
	/**
	 * get product by ID compare 
	 * @param ID
	 * @return
	 */
	public Product getProduct(int ID){
		productDb.initProductInfoTable();
		return productDb.getProduct(ID);
	}
	/**
	 * get all Categories as array category 
	 * @return
	 */
	public Category[] getAllCategory(){
		categoryDb.initCategoryTable();
		return categoryDb.getAllCategory();
	}
	/**
	 * get all categories ID's as int array
	 * @return
	 */
	public int[] getAllCategoryID(){
		categoryDb.initCategoryTable();
		return categoryDb.getAllID();
	}
	/**
	 *get all products ID's as int array
	 * @return
	 */
	public int[] getAllProductID(){
		productDb.initProductInfoTable();
		return productDb.getAllID();
	}
	/**
	 *get all products ID's as int array that are in stock 
	 * @return
	 */
	public int[] getItemsInInventory(){
		productDb.initProductInfoTable();
		return productDb.getItemsInInventory();
	}
	/**
	 * 
	 * @return
	 */
	public LinkedList<Quartet<Integer,String,Integer,Integer>> getDefectItems(){
		productDb.initProductInfoTable();
		return productDb.getDefectItems();
	}
	/**
	 * List of items that are going out of stock
	 * @return
	 */
	public LinkedList<Product>getMissingItems(){
		productDb.initProductInfoTable();
		return productDb.getMissingItems();
	}
	
	
	protected static boolean executeSQLCommand(String[] SQL) {
		try (Statement stmt = DALManager.conn.createStatement()) {
			for(int i=0;i<SQL.length;i++)
				stmt.execute(SQL[i]);
			
        } catch (SQLException e) {
            return false;
        }
		return true;
    }
	
	protected static boolean executeSQLCommand(String SQL) {
		String[] sqlCommand=new String[1];
		sqlCommand[0]=SQL;
		return executeSQLCommand(sqlCommand);
    }
}

