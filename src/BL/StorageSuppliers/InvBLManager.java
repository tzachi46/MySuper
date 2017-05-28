package BL.StorageSuppliers;
/**
 * InvBLManager is responsible for the communication between the PL and the DAL
 * This class is a singelton
 */
import java.util.LinkedList;

import BL.BLManager;
import DAL.Inventory.InvDALManager;
import DAL.Orders.OrderManager;
import SharedClasses.Quartet;
import SharedClasses.StorageSuppliers.Category;
import SharedClasses.StorageSuppliers.Product;


public class InvBLManager {
		private static InvBLManager instance;
		private InvDALManager DB;
		
		public static InvBLManager GetInvBLManager(){
			if(instance==null)
				instance= new InvBLManager();
			return instance;
		}
		
		/**
		 * Constructor 
		 */
		private InvBLManager(){
			DB= InvDALManager.getInstance();
		}
		/**
		 * 
		 * @return Quartet of the defect items
		 */
		public LinkedList<Quartet<Integer,String,Integer,Integer>>getDefectItems(){
			return DB.getDefectItems(BLManager.emp.getWorkAddress());
		}
		/**
		 * 
		 * @return linked list of missing items
		 */
		public LinkedList<Product>getMissingItems(){
			return DB.getMissingItems(BLManager.emp.getWorkAddress());
		}
		/**
		 * 
		 * @return all products in inventory
		 */
		public LinkedList<Product> getProductInInventoryList(){
			int[]id=DB.getItemsInInventory(BLManager.emp.getWorkAddress());
			LinkedList<Product>p=new LinkedList<>();
			for(int i=0;i<id.length;i++)
				p.add(DB.getProduct(id[i],BLManager.emp.getWorkAddress()));
			return p;
		}
		/**
		 * 
		 * @return the products in the inventory
		 */
		public Product[]getProductInInventory(){
			int[]id=DB.getItemsInInventory(BLManager.emp.getWorkAddress());
			Product[]p=new Product[id.length];
			for(int i=0;i<id.length;i++)
				p[i]=DB.getProduct(id[i],BLManager.emp.getWorkAddress());
			return p;
		}
		/**
		 * removes a category
		 * @param c
		 */
		public void removeCategory(Category c){
			DB.deleteCategory(c);
			Product[]p=getProductInInventory();
			for(int i=0;i<p.length;i++){
				if(p[i].getCategory()==c.getId()){
					p[i].setCategory(c.getFatherId());
					updateProduct(p[i]);
				}
			}
		}
		/**
		 * removes a product
		 * @param product
		 */
		public void removeProdut(Product product){
			// remove from time order 
			DB.deleteProduct(product);
		}
		/**
		 * updates a product
		 * @param product
		 */
		public void updateProduct(Product product){
			// may couse adding order
			DB.updateProduct(product,BLManager.emp.getWorkAddress());
		}
		/**
		 * updates a category
		 * @param c
		 */
		public void updateCategory(Category c){
			DB.updateCategory(c);
		}
		/**
		 * check if products exits by id
		 * @param ID
		 * @return
		 */
		public boolean checkProductId(int ID){
			int [] productsID=DB.getAllProductID(BLManager.emp.getWorkAddress());
			for(int i=0;i<productsID.length;i++)
				if(ID==productsID[i])
					return true;
			return false;
		}
		/**
		 * checks if product exists by name
		 * @param name
		 * @return
		 */
		public boolean checkCategoryName(String name){
			Category[]cat=DB.getAllCategory();
			for(int i=0;i<cat.length;i++)
				if(name.equals(cat[i].getName()))
					return true;
			return false;
		}
		/**
		 * 
		 * @return all the categories
		 */
		public Category[]getAllCategories(){
			return DB.getAllCategory();
		}
		/**
		 * 
		 * @param name
		 * @return category by name
		 */
		public Category getCategoryByName(String name){
			return DB.getCategoryByName(name);
		}
		/**
		 * 
		 * @param id
		 * @return category by id
		 */
		public Category getCategoryById(int id){
			return DB.getCategory(id);
		}
		/**
		 * add new product to the DB
		 * @param worker
		 */
		public void addNewProduct(Product product){
			//may couse adding order
			DB.addNewProduct(product,BLManager.emp.getWorkAddress());
		}
		/**
		 * add new category
		 * @param category
		 */
		public void addNewCategory(Category category){
			DB.addNewCategory(category);
		}
		/**
		 * return product by it's ID
		 * @param ID
		 * @return Product.
		 */
		public Product getProduct(int ID){
			return DB.getProduct(ID,BLManager.emp.getWorkAddress());
		}
		
		public LinkedList<String> getStoresOfferingProduct(int productId){
			return DB.getStoresOfferingProduct(productId);
		}
		
		public boolean isProductExistInAOrder(int productId){
			return OrderManager.getInstance().isProductExistInAOrder(productId);
		}
	}
