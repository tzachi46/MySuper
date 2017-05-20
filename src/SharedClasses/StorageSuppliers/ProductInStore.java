package SharedClasses.StorageSuppliers;

public class ProductInStore {
	
	private String StoreAddress;
	private int Productid;
	private int storeQuantity;
	private int wareQuantity;
	private String warehouseLoc;
	private String storeLoc;
	private int storeDefective;
	private int warehouseDefective;
	private int salesPerDay;

	
	public ProductInStore(String StoreAddress,int Productid,int storeQuantity,int wareQuantity,String warehouseLoc,String storeLoc,int storeDefective,int warehouseDefective,int salesPerDay){
		this(StoreAddress,Productid);
		this.storeQuantity=storeQuantity;
		this.wareQuantity=wareQuantity;
		this.warehouseLoc=warehouseLoc;
		this.storeLoc=storeLoc;
		this.storeDefective=storeDefective;
		this.warehouseDefective=warehouseDefective;
		this.salesPerDay=salesPerDay;
	}
	
	public ProductInStore(String StoreAddress,int Productid){
		this.setStoreAddress(StoreAddress);
		this.setProductid(Productid);
		this.storeQuantity=0;
		this.wareQuantity=0;
		this.warehouseLoc="Not exist in store";
		this.storeLoc="Not exist in store";
		this.storeDefective=0;
		this.warehouseDefective=0;
		this.warehouseDefective=0;
		this.salesPerDay=0;		
	}


	public String getStoreAddress() {
		return StoreAddress;
	}


	public void setStoreAddress(String storeAddress) {
		StoreAddress = storeAddress;
	}


	public int getProductid() {
		return Productid;
	}


	public void setProductid(int productid) {
		Productid = productid;
	}


	public int getStoreQuantity() {
		return storeQuantity;
	}


	public void setStoreQuantity(int storeQuantity) {
		this.storeQuantity = storeQuantity;
	}


	public int getWareQuantity() {
		return wareQuantity;
	}


	public void setWareQuantity(int wareQuantity) {
		this.wareQuantity = wareQuantity;
	}


	public String getWarehouseLoc() {
		return warehouseLoc;
	}


	public void setWarehouseLoc(String warehouseLoc) {
		this.warehouseLoc = warehouseLoc;
	}


	public String getStoreLoc() {
		return storeLoc;
	}


	public void setStoreLoc(String storeLoc) {
		this.storeLoc = storeLoc;
	}


	public int getStoreDefective() {
		return storeDefective;
	}


	public void setStoreDefective(int storeDefective) {
		this.storeDefective = storeDefective;
	}


	public int getWarehouseDefective() {
		return warehouseDefective;
	}


	public void setWarehouseDefective(int warehouseDefective) {
		this.warehouseDefective = warehouseDefective;
	}


	public int getSalesPerDay() {
		return salesPerDay;
	}


	public void setSalesPerDay(int salesPerDay) {
		this.salesPerDay = salesPerDay;
	}
}
