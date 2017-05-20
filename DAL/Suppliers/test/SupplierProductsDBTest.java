package DAL.Suppliers.test;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import DAL.DALManager;
import DAL.Suppliers.SupplierManager;
import SharedClasses.StorageSuppliers.Supplier;

public class SupplierProductsDBTest {

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

	/*
	@Test
	public void AddNewProductTest() throws Exception {
		//simple test
		Supplier s1=new Supplier(1,2, 3,"Cash", 0);
		DB.addNewSupplier(s1);
		Product p= new Product(123,"pepsi");
		DB.AddProdact(p);
		Supplier s2=DB.getSupplier(1);
		if(s2.getCompanyId()!=1 | s2.getBankAccountNumber()!=2 | s2.getBankBranchId()!= 3 |s2.getDelivery()!=0 | !s2.getTermsOfPayment().equals("Cash"))
			fail();		
		
		LinkedList<Pair<Integer, Double>> prices = new LinkedList<Pair<Integer, Double>>();
		prices.add(new Pair<Integer,Double>(100,120.0));
		prices.add(new Pair<Integer,Double>(200,110.0));
		ProductFromSupplier product = new ProductFromSupplier(1, p, 12345, prices);
		DB.addNewProductToSupplier(product);
		ProductFromSupplier product2 = DB.getProductFromSupplier(1, 123);
		if(product2==null)
			fail();
		if(product.getSupplierId()!=product2.getSupplierId())
			fail();
		assertTrue(true);
	}
	*/
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
		s2=DB.getSupplier(323132133);
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
		s1=new Supplier(323123332,"zahi",2, 3,"Cash", 1,"ashdod");
		DB.updateSupplier(s1);
		s2=DB.getSupplier(323123332);
		if(s2!=null)
			fail();
		assertTrue(true);
	}
	
	


}
