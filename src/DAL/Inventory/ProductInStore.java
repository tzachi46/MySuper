package DAL.Inventory;

import java.sql.SQLException;
import java.sql.Statement;
import DAL.DALManager;

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
	
	public void addNewProductInStore(SharedClasses.StorageSuppliers.ProductInStore productInStore){
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
	
	
	public void UpdateProductInStore(SharedClasses.StorageSuppliers.ProductInStore productInStore){
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
	
	public void removeProduct(int ProductId){
		String sql= "DELETE FROM ProductInStore\n"
					+ " WHERE Barcode="+ProductId+"\n";
		try (Statement stmt = DALManager.conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {  }	
	}
	
	public void removeSite(String StoreAdd){
		String sql= "DELETE FROM ProductInStore\n"
				+ " WHERE StoreAdd is "+StoreAdd+"\n";
	try (Statement stmt = DALManager.conn.createStatement()) {
        stmt.execute(sql);
    } catch (SQLException e) {  }	
	}
		
	
}
