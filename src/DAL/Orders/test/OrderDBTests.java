package DAL.Orders.test;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import DAL.DALManager;
import BL.StorageSuppliers.BLSupplier;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.Supplier;


public class OrderDBTests {

/*
	DAL.Inventory.InvDALManager dalM;
	DAL.Orders.OrderManager ordersM;
	BLSupplier blSupplier;
	Order Specialorder;
	@Before
	public void setUp() throws Exception {
		DALManager.DBLocation="jdbc:sqlite:"+System.getProperty("user.dir")+"/DataBaseForTest.db";

		ordersM = DAL.Orders.OrderManager.getInstance();
		dalM=DAL.Inventory.InvDALManager.getInstance();
		blSupplier = BLSupplier.GetBLSupplier();
		
		Specialorder = new Order(1010, 0, 311498703);
		
	}
	private Supplier addSupplier() throws Exception{
		Supplier supplier = new Supplier(10101, "nivos", 12345, 1, "Cash", 1, "albert kami");
		DAL.Suppliers.SupplierManager.getInstance().removeSupplier(supplier.getCompanyId());
		DAL.Suppliers.SupplierManager.getInstance().addNewSupplier(supplier);
		DAL.Suppliers.SupplierManager.getInstance().addDayOfSupplier(supplier.getCompanyId(), 4);
		return supplier;
	}
	
	@After
	public void tearDown() throws Exception {
		String Location=""+System.getProperty("user.dir")+"/DataBaseForTest.db";
		File f= new File(Location);
		f.delete();
	}

	@Test
	public void addOrder() throws Exception {
		//ordersM.removeOrder(12333);
		Supplier supplier = addSupplier();
		Order toInsert = new Order(12333,0,supplier.getCompanyId());
		ordersM.addNewOrder(toInsert);
		Order order = ordersM.getOrder(toInsert.getOrderNumber());
		assertEquals(order.getSupplierId(), toInsert.getSupplierId());
		assertEquals(order.getOrderNumber(),toInsert.getOrderNumber());
	}
*/

}
