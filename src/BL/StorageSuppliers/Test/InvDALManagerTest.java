package BL.StorageSuppliers.Test;

import static org.junit.Assert.*;

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import DAL.DALManager;
import SharedClasses.StorageSuppliers.Category;
import SharedClasses.StorageSuppliers.Product;

public class InvDALManagerTest {
	DAL.Inventory.InvDALManager dalM;
	Product p;
	Category c;
	@Before
	public void setUp() throws Exception {
		DALManager.DBLocation="jdbc:sqlite:"+System.getProperty("user.dir")+"/DataBaseForTest.db";
		dalM=DAL.Inventory.InvDALManager.getInstance();
		p=new Product(123, "food", "osem", 10, 20, 50);
		p.setCategory(0);
		p.setDiscountStartTime("01.01.2001");
		p.setDiscountFinishTime("01.01.2018");
		p.setDiscountFromSupp(15);
		p.setDiscountToCus(10);
		p.setSalesPerDay(5);
		p.setStoreDefective(2);
		p.setStoreLoc("left side");
		p.setWareDefective(0);
		p.setWareLoc("somewhere");
		c=new Category(6545, 0, "stack", 3, "01.01.2018", "01.01.2013");
	}

	@After
	public void tearDown() throws Exception {
		dalM.deleteProduct(p);
		dalM.deleteCategory(c);
		p=null;
		c=null;
		dalM=null;
		String Location=""+System.getProperty("user.dir")+"/DataBaseForTest.db";
		File f= new File(Location);
		f.delete();
	}

	@Test
	public void testAddNewCategory() {
		dalM.addNewCategory(c);
		Category c2=dalM.getCategory(6545);
		assertEquals(c.getId(), c2.getId());
		dalM.deleteCategory(c);
	}


	@Test
	public void testGetAllCategory() {
		Category cArr[]=dalM.getAllCategory();
		dalM.addNewCategory(c);
		Category cArr2[]=dalM.getAllCategory();
		assertEquals(cArr.length+1,cArr2.length);
		
		dalM.deleteCategory(c);
	}


	
}
