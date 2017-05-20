package DAL.Suppliers.test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import DAL.DALManager;
import DAL.Suppliers.SupplierManager;
import SharedClasses.StorageSuppliers.Supplier;
import SharedClasses.StorageSuppliers.SupplierContact;

public class SupplierContactListDBTest {

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
	public void addSupplierContactTest() throws Exception {
		DB.removeSupplier(1);
		//simple
		Supplier s1=new Supplier(1,"zahi",2, 3,"Cash", 0,"ashdod");
		DB.addNewSupplier(s1);
		SupplierContact SC1= new SupplierContact(1,"0525378040","zahi","tzachi46gmail.com");
		DB.addSupplierContact(SC1);
		SupplierContact[] SC2=DB.getSupplierContactList(1);
		if(SC2.length!=1)
			fail();
		if(SC2[0].getCompanyId()!=1 | !SC2[0].getPhone().equals("0525378040")|!SC2[0].getName().equals("zahi") |!SC2[0].getEmail().equals("tzachi46gmail.com"))
			fail();
		
		// add contact of unexisting company
		SC1.setCompanyId(20);
		DB.addSupplierContact(SC1);
		SC2=DB.getSupplierContactList(1);
		if(SC2.length!=1)
			fail();
		SC2=DB.getSupplierContactList(20);
		if(SC2.length!=0)
			fail();
	}
	
	@Test
	public void getSupplierContactListTest() throws Exception {
		DB.removeSupplier(1);

		SupplierContact[] SC2=DB.getSupplierContactList(1);
		if(SC2.length!=0)
			fail();
		Supplier s1=new Supplier(1,"zahi",2, 3,"Cash", 0,"ashdod");
		DB.addNewSupplier(s1);
		SupplierContact SC0= new SupplierContact(1,"0525378041","zahi1","tzachi46gmail.com");
		SupplierContact SC1= new SupplierContact(1,"0525378040","zahi","tzachigmail.com");
		DB.addSupplierContact(SC0);
		DB.addSupplierContact(SC1);
		SC2=DB.getSupplierContactList(1);
		if(SC2.length!=2)
			fail();
	}

}
