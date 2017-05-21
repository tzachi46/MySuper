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
	private ProductInStore productInStore;
	private ProductSellingCostDB pscDB;
	/**
	 * Singleton constructor 
	 */
	private ProductDB(){
		pscDB=ProductSellingCostDB.getProductBuyingCostDB();
		productInStore = new ProductInStore();
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
		String sql = "Select Barcode, Name, QuantityShelf, QuantityWarehouse, Manufacturer, PlaceInWarehouse, PlaceInStore, StoreDefective, WareDefective, SalesPerDay, Category\n"
				+"FROM Product WHERE Barcode="+ID;
		Product res=null;
	    try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	//rs.getString("DepartureDate");
	    	res= new Product(rs.getInt("Barcode"));
	    	res.setName(rs.getString("Name"));
	    	res.setStoreQuantity(rs.getInt("QuantityShelf"));
	    	res.setWarehouseQuantity(rs.getInt("QuantityWarehouse"));
	    	res.setManufacturer(rs.getString("Manufacturer"));
	    	res.setStoreLoc(rs.getString("PlaceInStore"));
	    	res.setWareLoc(rs.getString("PlaceInWarehouse"));
	    	res.setStoreDefective(rs.getInt("StoreDefective"));
	    	res.setWareDefective(rs.getInt("WareDefective"));
	    	res.setSalesPerDay(rs.getInt("SalesPerDay"));
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
	/**
	 * 
	 * @return
	 */
	public LinkedList<Quartet<Integer,String,Integer,Integer>> getDefectItems(){
		String sql="SELECT Barcode,Name,storeDefective,wareDefective FROM Product WHERE StoreDefective>0 OR WareDefective>0 ";
		LinkedList<Quartet<Integer,String,Integer,Integer>> temp = new LinkedList<>();
		 try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 temp.add(new Quartet<Integer,String,Integer,Integer>(rs.getInt("Barcode"),rs.getString("Name"),rs.getInt("StoreDefective"),rs.getInt("WareDefective")));
			 }
		 }
		 catch (SQLException e) {
			 return null; 
		 }
		return temp;
	}
	/**
	 * get all the items that are about to run out of stock
	 * @return List of products 
	 */
	public LinkedList<Product> getMissingItems(){
		int[] ProductsIds= this.getAllID();
		LinkedList<Product> temp = new LinkedList<>();
		for(int i=0;i<ProductsIds.length;i++){
			int DeliveryTime=SupplierManager.getInstance().getAvarageSupplyTimeOfProduct(ProductsIds[i]);
			String sql="SELECT Barcode FROM Product WHERE (QuantityShelf+QuantityWarehouse)<=(SalesPerDay*"+DeliveryTime+"*1.2) AND Barcode is "+ ProductsIds[i];
			try ( Statement stmt  = DALManager.conn.createStatement();
		             ResultSet rs    = stmt.executeQuery(sql)){
				 while(rs.next()){
					 temp.add(getProduct(rs.getInt("Barcode")));
				 }
			 }
			 catch (SQLException e) {}
			
		}		
		return temp;
	}
	/**
	 * return array of brocade of products that have any product in stock
	 * @return array of barricades
	 */
	public int[] getItemsInInventory(){
		String sql="SELECT Barcode FROM Product WHERE QuantityShelf>=0 OR QuantityWarehouse>=0 ";
		int[] res=null;
		LinkedList<Integer> temp = new LinkedList<Integer>();
		 try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 temp.add(rs.getInt("Barcode"));
			 }
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
