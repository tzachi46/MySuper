package SharedClasses.StorageSuppliers;

import BL.StorageSuppliers.BLSupplier;
import BL.StorageSuppliers.InvBLManager;
import SharedClasses.Pair;

public class OrderProduct {
	
	private int ProductId;
	private int Amount;
	private double CurrentPrice,InitialPrice;

	public OrderProduct(int ProductId){
		this.ProductId=ProductId;
		CurrentPrice=-1;
		InitialPrice=-1;
	}
	
	public OrderProduct(int ProductId,int Amount){
		this(ProductId);
		this.setAmount(Amount);
	}
	
	public String getProductName(){
		return InvBLManager.GetInvBLManager().getProduct(ProductId).getName();
	}
	public double getProductWeight(){
		return InvBLManager.GetInvBLManager().getProduct(ProductId).getWeight();
	}
	
	private void initialPrices(){
		Pair<Double,ProductFromSupplier> pair=BLSupplier.GetBLSupplier().getBestPrice(ProductId, Amount);
		CurrentPrice=pair.getKey();
		InitialPrice=pair.getValue().getInitialPrice();
	}

	public double getCurrentProductPrice(){
		if(CurrentPrice==-1)
			initialPrices();
		return CurrentPrice;
	}
	
	public double getInitialProductPrice(){
		if(InitialPrice==-1)
			initialPrices();
		return InitialPrice;
	}
	
	public int getProductId() {
		return ProductId;
	}

	public int getAmount() {
		return Amount;
	}

	public void setAmount(int amount) {
		Amount = amount;
	}

}
