package SharedClasses.StorageSuppliers;

import java.util.LinkedList;
import java.util.List;

import BL.StorageSuppliers.BLSupplier;
import SharedClasses.Pair;
public class ProductFromSupplier {

	private int SupplierId;
	private Product product;
	private int SupplierCatalogId;
	private int AvarageDeleveryTime;
	private List<Pair<Integer, Double>> prices; //key - minimumQuantity, value - price
	
	public ProductFromSupplier(int SupplierId, Product product, int SupplierCatalogId, int AvarageDeleveryTime){
		this.setSupplierId(SupplierId);
		this.setProduct(product);
		this.setSupplierCatalogId(SupplierCatalogId);
		this.setAvarageDeleveryTime(AvarageDeleveryTime);
		prices= new LinkedList<Pair<Integer, Double>>();
	}
	
	public ProductFromSupplier(int SupplierId, Product ProductId, int SupplierCatalogId,int AvarageDeleveryTime, List<Pair<Integer, Double>> prices) {
		this(SupplierId,ProductId, SupplierCatalogId,AvarageDeleveryTime);
		this.setPrices(prices);
	}
	public int getSupplierId() {
		return SupplierId;
	}
	public void setSupplierId(int supplierId) {
		SupplierId = supplierId;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getSupplierCatalogId() {
		return SupplierCatalogId;
	}
	public void setSupplierCatalogId(int supplierCatalogId) {
		SupplierCatalogId = supplierCatalogId;
	}
	
	public boolean isPriceQuantityExist(int MinimumQuantity){
		for(int i=0;i<prices.size();i++){
			if(prices.get(i).getKey()==MinimumQuantity)
				return true;
		}
		return false;
	}
	
	public double getInitialPrice(){
		if (prices.size()==0)
			return Double.MAX_VALUE;
		Pair<Integer,Double> p = prices.get(0);
		for(int i=1;i<prices.size();i++){
			if(prices.get(i).getKey()<p.getKey())
				p=prices.get(i);
		}
		return p.getValue();
	}
	
	public boolean addNewPrice(int MinimumQuantity,Double price){
		for(int i=0;i<prices.size();i++){
			if(prices.get(i).getKey()==MinimumQuantity)
				return false;
		}
		prices.add(new Pair<Integer,Double>(MinimumQuantity,price));
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(SupplierId,product.getId(),MinimumQuantity,price);
		return true;
	}
	
	public List<Pair<Integer, Double>> getPrices() {
		return prices;
	}
	public void setPrices(List<Pair<Integer, Double>> prices) {
		this.prices = prices;
	}
	
	public String toString(){
		String res="";
		res+="ProductId = "+product.getId()+"\n";
		res+="Product Name = "+product.getName()+"\n";
		res+="SupplierId = "+ SupplierId+"\n";
		res+="SupplierCatalogId = "+SupplierCatalogId+"\n";
		res+="AvarageDeliveryTime = "+this.AvarageDeleveryTime+"\n";
		return res;
	}

	public int getAvarageDeleveryTime() {
		return AvarageDeleveryTime;
	}

	public void setAvarageDeleveryTime(int avarageDeleveryTime) {
		AvarageDeleveryTime = avarageDeleveryTime;
	}


}
