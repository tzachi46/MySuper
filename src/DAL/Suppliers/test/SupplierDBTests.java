package DAL.Suppliers.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import DAL.DALManager;
import DAL.Suppliers.SupplierManager;
import SharedClasses.StorageSuppliers.Supplier;

public class SupplierDBTests {

	private static SupplierManager DB;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DB= SupplierManager.getInstance();
		DALManager.DBLocation="jdbc:sqlite:"+System.getProperty("user.dir")+"\\DataBaseForTest.db";
	}
	
	
	@After
	public void tearDown() throws Exception {

	}


	@Test
	public void AddNewSupplierTest() throws Exception {
		DB.removeSupplier(102344);
		//simple test
		Supplier s1=new Supplier(102344,"zahi",3555, 355,"Cash", 1, "Ashdod");
		DB.addNewSupplier(s1);
		Supplier s2=DB.getSupplier(s1.getCompanyId());
		if(s2==null)
			fail();
		if(s2.getCompanyId()!=102344 | s2.getBankAccountNumber()!=3555 |!s2.getAddress().equals("Ashdod")| s2.getBankBranchId()!= 355 |s2.getDelivery()!=1 | !s2.getTermsOfPayment().equals("Cash"))
			fail();		
		
		//illegal termOfPayment
		s1=new Supplier(102344,"zahi",3, 3,"Cash", 0,"ashdod");
		DB.addNewSupplier(s1);
		s2=DB.getSupplier(102344);
		if(s2==null)
			fail();
		if(s2.getCompanyId()!=102344 | s2.getBankAccountNumber()!=3555 |!s2.getAddress().equals("Ashdod")| s2.getBankBranchId()!= 355 |s2.getDelivery()!=1 | !s2.getTermsOfPayment().equals("Cash"))
			fail();		
		DB.removeSupplier(s1.getCompanyId());
		assertTrue(true);
		
	}
	
	@Test
	public void getSupplierTest() throws Exception {
		DB.removeSupplier(1);
		//simple test
		Supplier s1=new Supplier(1,"zahi",2, 3,"Cash", 1,"ashdod");
		DB.addNewSupplier(s1);
		Supplier s2=DB.getSupplier(1);
		if(s2.getCompanyId()!=1 | s2.getBankAccountNumber()!=2 | s2.getBankBranchId()!= 3 |s2.getDelivery()!=1 | !s2.getTermsOfPayment().equals("Cash"))
			fail();		
		
		// non existence supplier
		s2=DB.getSupplier(333933);
		if(s2!=null)
			fail();
		assertTrue(true);
	}
	
	@Test
	public void updateSupplier() throws Exception {
		DB.removeSupplier(1);
		//simple test
		Supplier s1=new Supplier(1,"zahi",2, 3,"Cash", 1,"ashdod");
		DB.addNewSupplier(s1);
		s1.setBankAccountNumber(5);
		DB.updateSupplier(s1);
		Supplier s2=DB.getSupplier(1);
		if(s2.getCompanyId()!=1 | s2.getBankAccountNumber()!=5 | s2.getBankBranchId()!= 3 |s2.getDelivery()!=1 | !s2.getTermsOfPayment().equals("Cash"))
			fail();		
		
		// change delivery
		s1.setDelivery(0);
		DB.updateSupplier(s1);
		s2=DB.getSupplier(1);
		if(s2.getCompanyId()!=1 | s2.getBankAccountNumber()!=5 | s2.getBankBranchId()!= 3 |s2.getDelivery()!=0 | !s2.getTermsOfPayment().equals("Cash"))
			fail();
		
		// change non existence Supplier
		s1=new Supplier(32333123,"zahi",2, 3,"Cash", 1,"ashdod");
		DB.updateSupplier(s1);
		s2=DB.getSupplier(32333123);
		if(s2!=null)
			fail();
		assertTrue(true);
	}
	
	


}
