package BL.StorageSuppliers.Test;


import java.io.File;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import DAL.DALManager;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.Supplier;
import BL.StorageSuppliers.BLSupplier;

public class ordersBLTest {

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

		
	}
	
	@After
	public void tearDown() throws Exception {
		String Location=""+System.getProperty("user.dir")+"/DataBaseForTest.db";
		File f= new File(Location);
		f.delete();
	}
	
	

	
	@Test
	public void IllegalAddProductOrder() throws Exception{
		Supplier supplier = new Supplier(101012, "nivos", 12345, 1, "Cash", 1, "albert kami");
		DAL.Suppliers.SupplierManager.getInstance().addNewSupplier(supplier);
		
		Order order = new Order(supplier.getCompanyId());
		Assert.assertEquals(order.canOrder(),true);
		
	}
	
}
