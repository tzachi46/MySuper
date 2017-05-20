package BL.StorageSuppliers.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import DAL.DALManager;
import SharedClasses.StorageSuppliers.Category;
import SharedClasses.StorageSuppliers.Product;
import SharedClasses.StorageSuppliers.Quartet;

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
	public void testAddNewProduct() {
		dalM.addNewProduct(p);
		Product p2=dalM.getProduct(123);
		assertEquals(p.getId(), p2.getId());
		dalM.deleteProduct(p);
	}

	@Test
	public void testAddNewCategory() {
		dalM.addNewCategory(c);
		Category c2=dalM.getCategory(6545);
		assertEquals(c.getId(), c2.getId());
		dalM.deleteCategory(c);
	}
	@Test
	public void testUpdateProductProductInt() {
		dalM.addNewProduct(p);
		p.setSalesPerDay(10);
		dalM.updateProduct(p);
		Product p2=dalM.getProduct(123);
		assertEquals(p.getSalesPerDay(), p2.getSalesPerDay());
		dalM.deleteProduct(p);
	}

	@Test
	public void testDeleteProduct() {
		dalM.addNewProduct(p);
		dalM.deleteProduct(p);
		assertNull(dalM.getProduct(123));
	}
	@Test
	public void testGetProduct() {
		dalM.addNewProduct(p);
		Product p2=dalM.getProduct(123);
		assertEquals(p.getId(), p2.getId());
		dalM.deleteProduct(p);
	}

	@Test
	public void testGetAllCategory() {
		Category cArr[]=dalM.getAllCategory();
		dalM.addNewCategory(c);
		Category cArr2[]=dalM.getAllCategory();
		assertEquals(cArr.length+1,cArr2.length);
		
		dalM.deleteCategory(c);
	}
	@Test
	public void testGetAllProductID() {
		int pArr[]=dalM.getAllProductID();
		dalM.addNewProduct(p);
		int pArr2[]=dalM.getAllProductID();
		assertEquals(pArr.length+1,pArr2.length);
		dalM.deleteProduct(p);
	}
	@Test
	public void testGetDefectItems() {
		//p.setStoreDefective(10);
		dalM.addNewProduct(p);
		LinkedList<Quartet<Integer,String,Integer,Integer>>defective=dalM.getDefectItems();
		boolean contains=false;
		for(int i=0;i<defective.size();i++)
			if(defective.get(i).getKey()==p.getId())
				contains=true;
		assertTrue(contains);
		dalM.deleteProduct(p);
	}
	
}
