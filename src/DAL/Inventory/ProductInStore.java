package DAL.Inventory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import DAL.DALManager;
import SharedClasses.StorageSuppliers.Product;

public class ProductInStore {
	
	/*
	 * may need to add as pre condition creation of site table and Product Table
	 */
	protected void InitTable(){
		String sql = "CREATE TABLE IF NOT EXISTS ProductInStore (\n"
                + "	Barcode integer PRIMARY KEY,\n"
                + "	StoreAdd TEXT NOT NULL,\n"
                + "	QuantityShelf integer NOT NULL,\n"
                + "	QuantityWarehouse integer NOT NULL,\n"
                + "	PlaceInWarehouse VARCHAR(30) NOT NULL,\n"
                + "	PlaceInStore VARCHAR(30) NOT NULL,\n"
                + "	StoreDefective integer DEFAULT 0,\n"
                + " WareDefective integer DEFAULT 0,\n"
                + " SalesPerDay integer DEFAULT 0,\n"
                + "FOREIGN KEY(Barcode) REFERENCES Product(Barcode) ON UPDATE CASCADE ON DELETE CASCADE"
                + "FOREIGN KEY(StoreAdd) REFERENCES Sites(ADDRESS) ON UPDATE CASCADE ON DELETE CASCADE"
                +"CONSTRAINT PKProductInStore PRIMARY KEY (Barcode, StoreAdd)\n"
                + ");";
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {  }
	}
	
	protected void addNewProductInStore(SharedClasses.StorageSuppliers.ProductInStore productInStore){
		String sql = "INSERT OR IGNORE INTO ProductInStore \n"
                + "	(Barcode, StoreAdd, QuantityShelf, QuantityWarehouse, PlaceInWarehouse, PlaceInStore, StoreDefective, WareDefective, SalesPerDay)\n"
                + "	values("+productInStore.getProductid()
                +","+"'"+productInStore.getStoreAddress()+"'"
                +","+productInStore.getStoreQuantity()
                +","+productInStore.getWareQuantity()
                +","+"'"+productInStore.getWarehouseLoc()+"'"
                +","+"'"+productInStore.getStoreLoc()+"'"
                +","+productInStore.getStoreDefective()
                +","+productInStore.getWarehouseDefective()
                +","+productInStore.getSalesPerDay()+");";	
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {  }
	}
	
	
	protected void UpdateProductInStore(SharedClasses.StorageSuppliers.ProductInStore productInStore){
		String sql = "UPDATE ProductInStore \n"
                + "SETStoreAdd='"+productInStore.getStoreAddress()+"',\n"
                + "QuantityShelf="+productInStore.getStoreQuantity()+",\n"
                + "QuantityWarehouse="+productInStore.getWareQuantity()+",\n"
                + "PlaceInWarehouse='"+productInStore.getWarehouseLoc()+"',\n" 
                + "PlaceInStore='"+productInStore.getStoreLoc()+"',\n" 
                + "StoreDefective="+productInStore.getStoreDefective()+",\n" 
                + "WareDefective="+productInStore.getWarehouseDefective()+",\n"
                + "SalesPerDay="+productInStore.getSalesPerDay()+"\n"
                + "WHERE Barcode="+productInStore.getProductid();     
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {    }
	}
	
	protected void removeProduct(int ProductId){
		String sql= "DELETE FROM ProductInStore\n"
					+ " WHERE Barcode="+ProductId+"\n";
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {  }	
	}
	
	protected void removeSite(String StoreAdd){
		String sql= "DELETE FROM ProductInStore\n"
				+ " WHERE StoreAdd is "+StoreAdd+"\n";
	try (Statement stmt = DALManager.conn.createStatement()) {
        stmt.execute(sql);
    	} catch (SQLException e) {  }	
	}
	
	protected SharedClasses.StorageSuppliers.ProductInStore getProductInStore(int ProductId,String StoreAddress){
		String sql = "Select Barcode, StoreAdd, QuantityShelf, QuantityWarehouse, PlaceInWarehouse, PlaceInStore, StoreDefective, WareDefective, SalesPerDay\n"
				+"FROM ProductInStore WHERE Barcode = "+ProductId +" AND StoreAdd is "+StoreAddress;
		SharedClasses.StorageSuppliers.ProductInStore res=null;
	    try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	//rs.getString("DepartureDate");
	    	res= new SharedClasses.StorageSuppliers.ProductInStore(rs.getString("StoreAdd"),
	    			rs.getInt("Barcode"),rs.getInt("QuantityShelf"),rs.getInt("QuantityWarehouse"),
	    			rs.getString("PlaceInWarehouse"),rs.getString("PlaceInStore"),
	    			rs.getInt("StoreDefective"),rs.getInt("WareDefective"),
	    			rs.getInt("SalesPerDay")
	    			);

	        } catch (SQLException e) {
	            return null; 
	        }
		return res;
	}
	// add a ProductInStore foreach store in the system 
	protected void InitialNewProduct(Product p, String StoreAddress){
		SharedClasses.StorageSuppliers.ProductInStore PIS;
		String sql = "Select ADDRESS\n"
				+"FROM Sites";
		  try ( Statement stmt  = DALManager.conn.createStatement();
		             ResultSet rs    = stmt.executeQuery(sql)){
			  while(rs.next()){
				   PIS= new SharedClasses.StorageSuppliers.ProductInStore(rs.getString("ADDRESS"),p.getId());
				   addNewProductInStore(PIS);
			  }
			  PIS=new SharedClasses.StorageSuppliers.ProductInStore(StoreAddress,p.getId(),
					  p.getStoreQuantity(),p.getWarehouseQuantity(),
					  p.getWareLoc(),p.getStoreLoc(),
					  p.getStoreDefective(),p.getWareDefective(),p.getSalesPerDay());
			  UpdateProductInStore(PIS);
			  
		  } catch (SQLException e) { }	
	}
	
}
