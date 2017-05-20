package DAL.Suppliers;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import DAL.DALManager;
import DAL.Orders.OrderManager;
import SharedClasses.StorageSuppliers.Product;
import SharedClasses.StorageSuppliers.ProductFromSupplier;
import SharedClasses.StorageSuppliers.Supplier;
import SharedClasses.StorageSuppliers.SupplierContact;

public class SupplierManager {

	private static SupplierManager instance;
	private SupplierDB supplierDB;
	private SuppliersDaysDB suppliersDaysDB;
	private SupplierContactListDB ContactListDB;
	private SupplierProductsDB supplierProductsDB;
	
	public static SupplierManager getInstance(){
		if(instance==null){
			instance=new SupplierManager();
		}
		return instance;
	}
	
	private SupplierManager(){
		DALManager.getInstance();
		supplierDB=new SupplierDB();
		ContactListDB= new SupplierContactListDB();
		suppliersDaysDB= new SuppliersDaysDB();
		supplierProductsDB= new SupplierProductsDB();
	}
	
	//supplier Table
	/**
	 * @preCondition s.CompanyID is not inserted in DB
	 * @param supplier
	 * @return false if didn't succeed to insert the supplier to the DB
	 */
	public boolean addNewSupplier(Supplier s){
		supplierDB.initWorkerInfoTable();
		return supplierDB.addNewSupplier(s);
	}
	/**
	 * @preCondition supplier is save in DB
	 * @param supplier
	 */
	public void updateSupplier(Supplier supplier){
		supplierDB.initWorkerInfoTable();
		suppliersDaysDB.initWorkerInfoTable();
		supplierDB.updateSupplier(supplier);
	}
	
	public void UpdatePriceOfProductFromSupplier(ProductFromSupplier product,int minimumAmount,Double price){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		supplierProductsDB.UpdatePriceOfProductFromSupplier(product, minimumAmount, price);
	}
	
	public Supplier getSupplier(int CompanyId){ // return null if there is no supplier
		supplierDB.initWorkerInfoTable();
		Supplier res= supplierDB.getSupplier(CompanyId);
		if(res!=null){
			int[] tempDays =getDaysOfSupplier(CompanyId);
			List<Integer> days = new LinkedList<Integer>();
			for(int i=0;i<tempDays.length;i++){
				days.add(tempDays[i]);
			}
			res.setDeliveryDays(days);
		}
		return res;
	}
	
	public int[] getAllSuppliersID(){
		supplierDB.initWorkerInfoTable();
		return supplierDB.getAllSuppliersID();
	}
	
	public void removeSupplier(int supplierId){
		suppliersDaysDB.initWorkerInfoTable();
		ContactListDB.initContactListDB();
		supplierProductsDB.initTable();
		supplierDB.initWorkerInfoTable();
		supplierDB.removeSupplier(supplierId);
	}
	
	public Supplier[] getAllSupplier(){
		supplierDB.initWorkerInfoTable();
		return supplierDB.getAllSupplier();
	}

	
	//contactList Table
	
	public void addSupplierContact(SupplierContact SC){
		ContactListDB.initContactListDB();
		if(getSupplier(SC.getCompanyId())!=null)
			ContactListDB.addSupplierContact(SC);
	}
	
	public void removeSupplierContactList(int ID){
		ContactListDB.initContactListDB();
		ContactListDB.removeSupplierContactList(ID);
	}
	
	public SupplierContact[] getSupplierContactList(int CompanyId){
		ContactListDB.initContactListDB();
		return ContactListDB.getSupplierContactList(CompanyId);
	}

	public int[] getAllContactsID(){
		ContactListDB.initContactListDB();
		return ContactListDB.getAllContactsID();
	}
	
	public SupplierContact getASupplierContact(int ID){
		ContactListDB.initContactListDB();
		return ContactListDB.getASupplierContact(ID);
	}
	
	public void updateSupplierContact(SupplierContact SC){
		ContactListDB.initContactListDB();
		ContactListDB.updateSupplierContact(SC);
	}


	// SupplierProductsDB
	
	public int[] getAllCatlogId(){
		supplierProductsDB.initTable();
		return supplierProductsDB.getAllCatlogId();
	}
	
	/**
	 * @preCondition ProductFromSupplier is not save in DB
	 * @param supplier
	 */
	public boolean addNewProductToSupplier(ProductFromSupplier product){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		return supplierProductsDB.addNewProduct(product);
	}
	
	public int getAvarageSupplyTimeOfProduct(int ProductId){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		return supplierProductsDB.getAvarageSupplyTimeOfProduct(ProductId);
	}
 
	public void updateProduct(ProductFromSupplier product){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		supplierProductsDB.updateProduct(product);
	}
	
	public ProductFromSupplier getProductFromSupplier(int SupplierId, int ProductId){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		return supplierProductsDB.getProductFromSupplier(SupplierId, ProductId);
	}
	
	public void removeProduct(Product p){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		OrderManager.getInstance().removeProduct(p);
		supplierProductsDB.removeProduct(p);
	}
	
	public ProductFromSupplier[] getAllProductFromSupplier(int SupplierId){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		return supplierProductsDB.getAllProductFromSupplier(SupplierId);
	}
	
	public ProductFromSupplier[] getAllSuppliersOfAProduct(int ProductID){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		return supplierProductsDB.getAllSuppliersOfAProduct(ProductID);
	}
	
	public void removeProductFromSupplier(int SupplierId, int ProductId){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		supplierProductsDB.removeProductFromSupplier(SupplierId,ProductId);
	}
	
	public void InsertNewPriceForProduct(int supplierId,int ProductId, int minimumQuantity,double Price){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		supplierProductsDB.InsertNewPriceForProduct(supplierId,ProductId,minimumQuantity,Price);
	}
	
	public void RemoveAPriceOfProduct(int supplierId,int ProductId, int minimumQuantity){
		supplierDB.initWorkerInfoTable();
		supplierProductsDB.initTable();
		supplierProductsDB.RemoveAPriceOfProduct(supplierId,ProductId,minimumQuantity);
	}

	//suppliersDays Table
	
	public int[] getDaysOfSupplier(int SupplierID){
		suppliersDaysDB.initWorkerInfoTable();
		return suppliersDaysDB.getDaysOfSupplier(SupplierID);
	}
	
	/**
	 * @preCondition CompanyId.Delivery is 1
	 */
	public void addDayOfSupplier(int supplier, int day){
		suppliersDaysDB.initWorkerInfoTable();
		suppliersDaysDB.addDayOfSupplier(supplier, day);
	}
	
	public void removeDayOfSupplier(int CompanyId, int day){
		suppliersDaysDB.initWorkerInfoTable();
		suppliersDaysDB.removeDayOfSupplier(CompanyId, day);
	}
	
	
	// in house use functions

	
	protected static boolean executeSQLCommand(String[] SQL) {
		try (Statement stmt = DALManager.conn.createStatement()) {
			for(int i=0;i<SQL.length;i++)
				stmt.execute(SQL[i]);
			
        } catch (SQLException e) {
            return false;
        }
		return true;
    }
	
	protected static boolean executeSQLCommand(String SQL) {
		String[] sqlCommand=new String[1];
		sqlCommand[0]=SQL;
		return executeSQLCommand(sqlCommand);
    }
	


}
