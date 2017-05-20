package DAL.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DAL.DALManager;
import SharedClasses.StorageSuppliers.Product;

/**
 * Manage the Product Selling Cost table in the DB
 */
public class ProductSellingCostDB {
	private static ProductSellingCostDB instance;
	/**
	 * Singleton constructor
	 */
	private ProductSellingCostDB(){}
	
	static ProductSellingCostDB getProductBuyingCostDB(){
		if(instance==null)
			instance= new ProductSellingCostDB();
		return instance;
	}
	
	/**
	 * initialize the ProductSellingCost's info table
	 */
	void initProductSellingCostTable(){
		String sql = "CREATE TABLE IF NOT EXISTS ProductSellingCost (\n"
                + "	Barcode integer ,\n"
                + "	Cost REAL NOT NULL,\n"
                + "	discount integer DEFAULT 0,\n"
                + "	StartDiscountDate VARCHAR(30) DEFAULT NULL,\n"
                + "	EndDiscountDate VARCHAR(30) DEFAULT NULL,\n"
                + "	FOREIGN KEY(Barcode) REFERENCES Product(Barcode) ON DELETE CASCADE ON UPDATE CASCADE\n"
                + ");";
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }		 
	}
	/**
	 * add a new ProductSellingCost to the database
	 * @param product
	 */
	public void addProductSellingCost(Product product){
		String sql = "INSERT INTO ProductSellingCost \n"
                + "	(Barcode, Cost, discount, StartDiscountDate, EndDiscountDate)\n"
                + "	values("+product.getId()
                +","+product.getPriceToSell()
                +",0"
                +",NULL"
                +",NULL"
                +");";	
		
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
	}
	/**
	 * updates the given ProductSellingCost's information 
	 * @param product
	 */
	public void updateProductSellingCost(Product product){
		String sql = "UPDATE ProductSellingCost \n"
                + "SET Cost="+product.getPriceToSell()+",\n"
                + "discount="+product.getDiscountToCus()+",\n"
                + "StartDiscountDate='"+product.getDiscountStartTime()+"',\n"
                + "EndDiscountDate='"+product.getDiscountFinishTime()+"'\n"
                + "WHERE Barcode="+product.getId();     
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            //System.out.println(e.getMessage());
        }
	}
	/**
	 * get product info
	 * @param product
	 * @return
	 */
	public Product getProductSellingCost(Product product){
		String sql = "SELECT Barcode, Cost, discount, StartDiscountDate, EndDiscountDate\n"
				+"FROM ProductSellingCost where Barcode="+product.getId();
		
	    try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	product.setPriceToSell(rs.getInt("Cost"));
	    	product.setDiscountToCus(rs.getInt("discount"));
	    	product.setDiscountStartTime(rs.getString("StartDiscountDate"));
	    	product.setDiscountFinishTime(rs.getString("EndDiscountDate"));
	        } catch (SQLException e) {
	            //System.out.println(e.getMessage());
	        }
	    return product;
	}
	public void deleteProduct(Product product){
		String sql = "Delete FROM ProductSellingCost \n"
                + "WHERE Barcode="+product.getId();     
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
        	
        }
	}
}
