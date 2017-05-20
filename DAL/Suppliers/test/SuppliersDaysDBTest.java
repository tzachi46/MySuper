package DAL.Suppliers.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import DAL.DALManager;
import DAL.Suppliers.SupplierManager;
import SharedClasses.StorageSuppliers.Supplier;

public class SuppliersDaysDBTest {
	
	private static SupplierManager DB;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DB= SupplierManager.getInstance();
		DALManager.DBLocation="jdbc:sqlite:"+System.getProperty("user.dir")+"/DataBaseForTest.db";
	}
	
	
	@After
	public void tearDown() throws Exception {
		String Location=""+System.getProperty("user.dir")+"/DataBaseForTest.db";
		File f= new File(Location);
		f.delete();
	}

	
	@Test
	public void addDayOfSupplierTest() throws Exception {
		DB.removeSupplier(1);
		Supplier s1=new Supplier(1,"zahi",2, 3,"Cash", 0,"ashdod");
		DB.addNewSupplier(s1);
		DB.addDayOfSupplier(s1.getCompanyId(), 6);
		int[] days=DB.getDaysOfSupplier(1);
		if(days.length!=0)
			fail();
		s1.setDelivery(1);
		DB.updateSupplier(s1);
		DB.addDayOfSupplier(s1.getCompanyId(), 6);
		days=DB.getDaysOfSupplier(1);
		if(days.length!=1)
			fail();
		assertEquals(days[0], 6);
		DB.addDayOfSupplier(12, 6);
		days=DB.getDaysOfSupplier(12);
		if(days.length!=0)
			fail();
		
	}
	
	@Test
	public void removeDayOfSupplierTest() throws Exception {
		DB.removeSupplier(1);
		Supplier s1=new Supplier(1,"zahi",2, 3,"Cash", 1,"ashdod");
		DB.addNewSupplier(s1);
		DB.addDayOfSupplier(s1.getCompanyId(), 1);
		DB.addDayOfSupplier(s1.getCompanyId(), 6);
		int[] days=DB.getDaysOfSupplier(1);
		if(days.length!=2)
			fail();
		DB.removeDayOfSupplier(1, 1);
		days=DB.getDaysOfSupplier(1);
		if(days.length!=1)
			fail();
		DB.removeDayOfSupplier(1, 3);
		days=DB.getDaysOfSupplier(1);
		if(days.length!=1)
			fail();
	}

}
