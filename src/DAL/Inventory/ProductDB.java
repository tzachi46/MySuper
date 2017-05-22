package DAL.Inventory;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import DAL.DALManager;
import DAL.Suppliers.SupplierManager;
import SharedClasses.Quartet;
import SharedClasses.StorageSuppliers.Product;
/**
 * Manage the Product table in the DB 
 * 
 *
 */
public class ProductDB {
	private static ProductDB instance;
	private ProductInStoreDB productInStore;
	private ProductSellingCostDB pscDB;
	/**
	 * Singleton constructor 
	 */
	private ProductDB(){
		pscDB=ProductSellingCostDB.getProductBuyingCostDB();
		productInStore = ProductInStoreDB.getInstance();
	}
	static ProductDB getProductDB(){
		if(instance==null)
			instance= new ProductDB();
		return instance;
	}
	/**
	 * initialize the Product's  table
	 */
	void initProductInfoTable(){
		String sql = "CREATE TABLE IF NOT EXISTS Product (\n"
                + "	Barcode integer PRIMARY KEY,\n"
                + "	Name VARCHAR(30) NOT NULL,\n"
                + "	Manufacturer VARCHAR(30) NOT NULL,\n"
                + " Category integer DEFAULT 0,\n"
                + "FOREIGN KEY(Category) REFERENCES Category(CategoryID) ON UPDATE CASCADE ON DELETE CASCADE"
                + ");";
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {        }
		pscDB.initProductSellingCostTable();
	}
	
	/**
	 * add a new Product to the database
	 * @param Product
	 */
	public void addNewProduct(Product product, String StoreAddress){
		String sql = "INSERT OR IGNORE INTO Product \n"
                + "	(Barcode, Name, Manufacturer,Category)\n"
                + "	values("+product.getId()
                +","+"'"+product.getName()+"'"
                +","+"'"+product.getManufacturer()+"'"
                +","+product.getCategory()+");";// add to Product	
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
            productInStore.InitialNewProduct(product,StoreAddress);
            
        } catch (SQLException e) {

        }
		pscDB.addProductSellingCost(product);
	}
	/**
	 * updates the given Product's information
	 * @param Product
	 */
	public void updateProduct(Product product, String StoreAddress){
		SharedClasses.StorageSuppliers.ProductInStore res=null;
		String sql = "UPDATE Product \n"
                + "SET Barcode="+product.getId()+",\n"
                + "Name='"+product.getName()+"',\n"
                + "Manufacturer='"+product.getManufacturer()+"',\n" 
                + "Category="+product.getCategory()+"\n"
                + "WHERE Barcode="+product.getId();     
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
            res=new SharedClasses.StorageSuppliers.ProductInStore(StoreAddress,product.getId(),
            		product.getStoreQuantity(),product.getWarehouseQuantity(),
            		product.getWareLoc(),product.getStoreLoc(),
            		product.getStoreDefective(),product.getWareDefective(),product.getSalesPerDay());
            productInStore.UpdateProductInStore(res);
        } catch (SQLException e) {   }
		pscDB.updateProductSellingCost(product);
	}
	
	/**
	 * Delete the product from the DB
	 * @param product
	 */
	public void deleteProduct(Product product){
		String sql = "Delete FROM Product \n"
                + "WHERE Barcode="+product.getId();     
		try ( Statement stmt = DALManager.conn.createStatement()) {
			//conn.createStatement().execute("PRAGMA foreign_keys = ON;");
            stmt.execute(sql);
        } catch (SQLException e) {
           // //System.out.println(e.getMessage());
        	////System.out.println("ERROR");
        }
	}
	/**
	 * @param ID
	 * @return return Product's information with the corresponding ID
	 */
	public Product getProduct(int ID){
		String sql = "Select Barcode, Name, Manufacturer, Category\n"
				+"FROM Product WHERE Barcode="+ID;
		Product res=null;
	    try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	//rs.getString("DepartureDate");
	    	res= new Product(rs.getInt("Barcode"));
	    	res.setName(rs.getString("Name"));
	    	//res.setStoreQuantity(rs.getInt("QuantityShelf"));
	    	//res.setWarehouseQuantity(rs.getInt("QuantityWarehouse"));
	    	res.setManufacturer(rs.getString("Manufacturer"));
	    	/*
	    	res.setStoreLoc(rs.getString("PlaceInStore"));
	    	res.setWareLoc(rs.getString("PlaceInWarehouse"));
	    	res.setStoreDefective(rs.getInt("StoreDefective"));
	    	res.setWareDefective(rs.getInt("WareDefective"));
	    	res.setSalesPerDay(rs.getInt("SalesPerDay"));
	    	*/
	    	res.setCategory(rs.getInt("Category"));
	        } catch (SQLException e) {
	            return null; 
	        }
	    res=pscDB.getProductSellingCost(res);
		return res;
	}
	
	/**
	 * @return an array with the id of each Product
	 */
	public int[] getAllID(){
		String sql = "SELECT Barcode FROM Product";
		int[] res=null;
		LinkedList<Integer> temp = new LinkedList<Integer>();
		 try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 temp.add(rs.getInt("Barcode"));
			 }
			 ///temp.pop();//TODO:: fix for shay
			 res= new int[temp.size()];
			 for(int i=0;i<res.length;i++){
				 res[i]=temp.pop();
			 }
		 }
		 catch (SQLException e) {
			 return new int[0]; 
		 }
		return res;
	}



	
}
