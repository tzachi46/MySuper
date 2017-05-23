package BL.StorageSuppliers;

import java.util.List;

import BL.BLManager;
import DAL.Suppliers.SupplierManager;
import SharedClasses.Pair;
import SharedClasses.StorageSuppliers.ProductFromSupplier;
import SharedClasses.StorageSuppliers.Supplier;
import SharedClasses.StorageSuppliers.SupplierContact;

public class BLSupplier {

	private static BLSupplier instance;
	private SupplierManager DB;
	
	public static BLSupplier GetBLSupplier(){
		if(instance==null)
			instance= new BLSupplier();
		return instance;
	}
	
	/**
	 * Contractor 
	 */
	private BLSupplier(){
		DB= SupplierManager.getInstance();
	}
	//days
	public int[] getDaysOfSupplier(int SupplierID){
		return DB.getDaysOfSupplier(SupplierID);
	}
	
	public void addDayOfSupplier(int supplier, int day){
		DB.addDayOfSupplier(supplier, day);
	}
	
	public void removeDayOfSupplier(int CompanyId, int day){
		DB.removeDayOfSupplier(CompanyId, day);
	}
	
	
	
	//Contact

	public SupplierContact[] getSupplierContacts(int supplierId){
		return DB.getSupplierContactList(supplierId);
	}
	
	public void addSupplierContact(SupplierContact contact){
		DB.addSupplierContact(contact);
	}
	
	public boolean verifyiedExsitingContact(int id){
		int[] AllIds= DB.getAllContactsID();
		for(int i=0;i<AllIds.length;i++){
			if(AllIds[i]==id)
				return true;
		}
		return false;
	}
	
	public void removeSupplierContact(int ID){
		DB.removeSupplierContactList(ID);
	}
	
	public SupplierContact getASupplierContact(int id){
		return DB.getASupplierContact(id);
	}
	
	public void updateSupplierContact(SupplierContact SC){
		DB.updateSupplierContact(SC);
	}
	
	//Product's supplier
	
	public void UpdatePriceOfProductFromSupplier(ProductFromSupplier product,int minimumAmount,Double price){
		DB.UpdatePriceOfProductFromSupplier(product, minimumAmount, price);
	}
	
	public boolean isCatalogIdValid(String CatalogId){
		if(!checkOnlyNumbers(CatalogId))
			return false;
		int cat=Integer.parseInt(CatalogId);
		int[] allCat=DB.getAllCatlogId();
		for(int i=0;i<allCat.length;i++){
			if(allCat[i]==cat)
				return false;
		}
		return true;
	}
	
	private boolean checkOnlyNumbers(String s){
		String temp="0123456789";
		for(int i=0;i<s.length();i++){
			if(temp.indexOf(s.charAt(i))==-1)
				return false;
		}
		return true;
	}
	
	public void addProductFromSupplier(ProductFromSupplier prod){
		DB.addNewProductToSupplier(prod);
	}
	
	public ProductFromSupplier getProductFromSupplier(int supplierId,int productId){
		return DB.getProductFromSupplier(supplierId, productId);
	}
	
	public void updateProduct(ProductFromSupplier product){
		DB.updateProduct(product);
	}

	public ProductFromSupplier[] getAllProductFromSupplier(int SupplierId){
		return DB.getAllProductFromSupplier(SupplierId);
	}
	
	public ProductFromSupplier[] getAllSuppliersOfAProduct(int ProductID){
		return DB.getAllSuppliersOfAProduct(ProductID);
	}
	
	public boolean isProductExist(int ProductID){ 
		return DAL.Inventory.InvDALManager.getInstance().getProduct(ProductID,BLManager.emp.getWorkAddress())!=null;
	}
	
	public Pair<Double,ProductFromSupplier> getBestPrice(int ProductID,int minimumQuantity){
		List<Pair<Integer,Double>> PricesOfSupplier;
		ProductFromSupplier[] AllPrices=getAllSuppliersOfAProduct(ProductID);
		Pair<Double,ProductFromSupplier> BestPrice=new Pair<Double,ProductFromSupplier> (Double.MAX_VALUE,null);
		for(int i=0;i<AllPrices.length;i++){
			PricesOfSupplier=AllPrices[i].getPrices();
			for(int j=0;j<PricesOfSupplier.size();j++){
				if((PricesOfSupplier.get(j).getKey()<=minimumQuantity 
						&& BestPrice.getKey()>PricesOfSupplier.get(j).getValue())){
					BestPrice.setKey(PricesOfSupplier.get(j).getValue());
					BestPrice.setValue(AllPrices[i]);
				}
			}
		}
		return BestPrice;
	}
	
	public void removeProductFromSupplier(int SupplierId, int ProductId){
		DB.removeProductFromSupplier(SupplierId,ProductId);
	}
	
	public void InsertNewPriceForProduct(int supplierId,int ProductId, int minimumQuantity,double Price){
		DB.InsertNewPriceForProduct(supplierId,ProductId,minimumQuantity,Price);
	}
	
	public void RemoveAPriceOfProduct(int supplierId,int ProductId, int minimumQuantity){
		DB.RemoveAPriceOfProduct(supplierId,ProductId,minimumQuantity);
	}

	//Supplier
	
	public void addSupplier(Supplier supplier){
		DB.addNewSupplier(supplier);
	}
	
	public Supplier getSupplier(int supplierId){
		return DB.getSupplier(supplierId);
	}
	
	public void updateSupplier(Supplier supplier){
		DB.updateSupplier(supplier);
	}
	
	public boolean VerifyExistingSupplierID(int ID){
		int[] SuppliersID= DB.getAllSuppliersID();
		for(int i=0;i<SuppliersID.length;i++){
			if(SuppliersID[i]==ID)
				return true;
		}
		return false;
	}
	
	public void removeSupplier(int supplierId){
		DB.removeSupplier(supplierId);
	}
	
	public Supplier[] getAllSupplier(){
		return DB.getAllSupplier();
	}

}
