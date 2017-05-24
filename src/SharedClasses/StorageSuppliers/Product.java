package SharedClasses.StorageSuppliers;

/**
 * An object that represent a product in the store
 *
 */
public class Product {
	private int id;
	private String name;
	private String manufacturer;
	private int storeQuantity;
	private int wareQuantity;
	private int discountFromSupp;
	private double priceToSell;
	private int discountToCus;
	private String discountFinishTime;
	private String discountStartTime;
	private String warehouseLoc;
	private String storeLoc;
	private int storeDefective;
	private int warehouseDefective;
	private int category;
	private int salesPerDay;
	private int weight;
	/**
	 * Constructor for inserting new product
	 * @param id
	 * @param name
	 * @param manufacturer
	 * @param quantity 
	 * @param priceToBuy
	 * @param priceToSell
	 */
	public Product(int id, String name,String manufacturer,int storeQuantity,int warehouseQuantity,double priceToSell){
		this.name=name;
		this.id=id;
		this.manufacturer=manufacturer;
		this.storeQuantity=storeQuantity;
		this.wareQuantity=warehouseQuantity;
		this.priceToSell=priceToSell;
		discountFromSupp=0;
		discountToCus=0;
		discountFinishTime=null;
		discountStartTime=null;
		warehouseLoc=null;
		storeLoc=null;
		storeDefective=0;
		warehouseDefective=0;
		salesPerDay=0;
	}
	
	public Product(int id, String name,String manufacturer,int storeQuantity,int warehouseQuantity,double priceToSell,int weight){
	this( id,  name, manufacturer, storeQuantity, warehouseQuantity, priceToSell);
	this.weight=weight;	
	}
	/**
	 * Constructor
	 * @param id
	 */
	public Product(int id){
		this.name=null;
		this.id=id;
		this.manufacturer=null;
		this.storeQuantity=0;
		this.wareQuantity=0;
		this.priceToSell=0;
		discountFromSupp=0;
		discountToCus=0;
		discountFinishTime=null;
		discountStartTime=null;
		storeDefective=0;
		salesPerDay=0;
		warehouseDefective=0;
	}
	/**
	 * @return id
	 */
	public int getId(){
		return id;
	}
	/**
	 * @return name 
	 */
	public String getName(){
		return name;
	}
	/**
	 * @return manufacturer
	 */
	public String getManufacturer(){
		return manufacturer;
	}
	/**
	 * @return quantity in store
	 */
	public int getStoreQuantity(){
		return storeQuantity;
	}
	/**
	 * @return warehouse quantity
	 */
	public int getWarehouseQuantity(){
		return wareQuantity;
	}

	/**
	 * return the discount from sup
	 * @return
	 */
	public int getDiscountFromSupp(){
		return discountFromSupp;
	}
	/**
	 * 
	 * @return price to sell product 
	 */
	public double getPriceToSell(){
		return priceToSell;
	}
	/**
	 * 
	 * @return Discount to customer
	 */
	public int getDiscountToCus(){
		return discountToCus;
	}
	public int getWeight(){
		return weight;
	}
	/**
	 * 
	 * @return discount finish time
	 */
	public String getDiscountFinishTime(){
		return discountFinishTime;
	}
	/**
	 * 
	 * @return get discount start time
	 */
	public String getDiscountStartTime(){
		return discountStartTime;
	}
	/**
	 * 
	 * @return get location in store
	 */
	public String getStoreLoc(){
		return storeLoc;
	}
	/**
	 * 
	 * @return get location in warehouse
	 */
	public String getWareLoc(){
		return warehouseLoc;
	}
	/**
	 * 
	 * @return get how many defective in store
	 */
	public int getStoreDefective(){
		return storeDefective;
	}
	/**
	 * 
	 * @return get how many defective in warehouse
	 */
	public int getWareDefective(){
		return warehouseDefective;
	}
	/**
	 * 
	 * @return get category of product
	 */
	public int getCategory(){
		return category;
	}
	/**
	 * 
	 * @return how many sales of specipice product are made in average
	 */
	public int getSalesPerDay(){
		return salesPerDay;
	}
	/**
	 * set the sales per day
	 * @param salesPerDay
	 */
	public void setSalesPerDay(int salesPerDay){
		this.salesPerDay=salesPerDay;
	}

	/**
	 * sets name 
	 */
	public void setName(String name){
		this.name=name;
	}
	/**
	 * sets manufacturer
	 */
	public void setManufacturer(String manufacturer){
		this.manufacturer=manufacturer;
	}
	/**
	 * sets quantity in store
	 */
	public void setStoreQuantity(int quant){
		this.storeQuantity=quant;
	}
	/**
	 * 
	 * @param quantity in warehouse
	 */
	public void setWarehouseQuantity(int quant){
		this.wareQuantity=quant;
	}
	/**
	 * set Discount buy from supllier
	 * @param discount
	 */
	public void setDiscountFromSupp(int discount){
		this.discountFromSupp=discount;
	}
	public void setWeight(int weight){
		this.weight= weight;
	}
	/**
	 * set price for costumer
	 * @param price
	 */
	public void setPriceToSell(double price){
		this.priceToSell=price;
	}
	/**
	 * set discount for costumer
	 * @param discount
	 */
	public void setDiscountToCus(int discount){
		this.discountToCus=discount;
	}
	/**
	 * Set discount finish time
	 * @param date
	 */
	public void setDiscountFinishTime(String date){
		this.discountFinishTime=date;
	}
	/**
	 * Set discount start time
	 * @param date
	 */
	public void setDiscountStartTime(String date){
		this.discountStartTime=date;
	}
	/**
	 * set location in store for the product
	 * @param loc
	 */
	public void setStoreLoc(String loc){
		this.storeLoc=loc;
	}
	/**
	 * set location in warehouse for the product
	 * @param loc
	 */
	public void setWareLoc(String loc){
		this.warehouseLoc=loc;
	}
	/**
	 * set how many defective in store
	 * @param def
	 */
	public void setStoreDefective(int def){
		storeDefective=def;
	}
	/**
	 * set how many defective in warehouse
	 * @param def
	 */
	public void setWareDefective(int def){
		warehouseDefective=def;
	}
	/**
	 * set category
	 * @param category
	 */
	public void setCategory(int category){
		this.category=category;
	}
	
	public int getWarningQuantity(){
		return (int) (salesPerDay*1.2*DAL.Suppliers.SupplierManager.getInstance().getAvarageSupplyTimeOfProduct(id));
	}

}
