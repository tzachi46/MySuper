package PL.StorageSuppliers.SupplierPL;

import java.util.List;
import java.util.Scanner;

import BL.StorageSuppliers.BLSupplier;
import BL.StorageSuppliers.Calculations;
import SharedClasses.Pair;
import SharedClasses.StorageSuppliers.ProductFromSupplier;

public class PricesMenuCLI {

private static PricesMenuCLI instance;
	
	public static PricesMenuCLI getInstance() {
		if(instance==null)
			instance=new PricesMenuCLI();
		return instance;
	}
	
	private PricesMenuCLI() {	}
	
	public void Start(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Product's Prices Menu:\n1. Get All Prices from supllier for a product \n2. Get minimum price for a product \n3. Add A price for product from a supplier \n4. Delete A price for product from a supplier\n5. Update price for product from supplier\n~. Back to Supplier Menu. ");
			String s=in.nextLine();
			while(!cheackCLIMainInput(s)){
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			switch (s.charAt(0)){
				case ('1'):{
					getPricesOfProductFromSupplier(in);
					break;
				}
				case('2'):{
					getTheBestPricesOfProduct(in);
					break;
				}
				case('3'):{
					AddAPriceForProduct(in);
					break;
				}
				case('4'):{
					removePriceForProduct(in);
					break;
				}
				case('5'):{
					updatePriceForProduct(in);
					break;
				}
				case('~'):{
					EndOfPage=true;
					break;
				}
			}
		}
	}
	
	private void updatePriceForProduct(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Update Price of a product from a supllier Menu:");

			int supplierId = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(supplierId) & supplierId!=-1){
				System.out.println("The Supplier "+supplierId+" is not exist" );
				supplierId = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(supplierId==-1)
				return;
			
			String ProductId= ProductsMenuCLI.getProductId(in);
			if(ProductId.equals("~"))
				return;
			ProductFromSupplier Product=BLSupplier.GetBLSupplier().getProductFromSupplier(supplierId, Integer.parseInt(ProductId));
			while(Product==null){
				System.out.println("the supplier "+supplierId+" dont supply the product "+ProductId);
				ProductId= ProductsMenuCLI.getProductId(in);
				if(ProductId.equals("~"))
					return;
				Product=BLSupplier.GetBLSupplier().getProductFromSupplier(supplierId, Integer.parseInt(ProductId));
			}
			
			System.out.println("enter the minimum quantity of products for this price\nIf you wish to return to the Prices Menu press ~");
			String MinimumQuantity= in.nextLine();
			if(MinimumQuantity.equals("~"))
				return;
			while(((!verifiedMinimumQuantity(MinimumQuantity))||!Product.isPriceQuantityExist(Integer.parseInt(MinimumQuantity)) )& !MinimumQuantity.equals("~")){
				System.out.println("Illigal input or the price for this minimum quantity is not exist");
				System.out.println("enter the minimum quantity of products for this price\nIf you wish to return to the Prices Menu press ~");
				MinimumQuantity=in.nextLine();
			}
			if(MinimumQuantity.equals("~"))
				return;
			
			
			System.out.println("enter the new price for the product for this minimum amount\nIf you wish to return to the Prices Menu press ~");
			String price =in.nextLine();
			while( !Calculations.GetCalculations().checkDouble(price) && !price.equals("~")){
				System.out.println("Illigal input");
				System.out.println("enter the new price for the product for this minimum amount\nIf you wish to return to the Prices Menu press ~");
				price=in.nextLine();
			}
			if(price.equals("~"))
				return;
			
			BLSupplier.GetBLSupplier().UpdatePriceOfProductFromSupplier(Product, Integer.parseInt(MinimumQuantity), Double.parseDouble(price));
		}
	}
	
	private void removePriceForProduct(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Remove Price of a product from a supllier Menu:");
			
			int supplierId = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(supplierId) & supplierId!=-1){
				System.out.println("The Supplier "+supplierId+" is not exist" );
				supplierId = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(supplierId==-1)
				return;
			
			
			String ProductId= ProductsMenuCLI.getProductId(in);
			if(ProductId.equals("~"))
				return;
			ProductFromSupplier Product=BLSupplier.GetBLSupplier().getProductFromSupplier(supplierId, Integer.parseInt(ProductId));
			while(Product==null){
				System.out.println("the supplier "+supplierId+" dont supply the product "+ProductId);
				ProductId= ProductsMenuCLI.getProductId(in);
				if(ProductId.equals("~"))
					return;
				Product=BLSupplier.GetBLSupplier().getProductFromSupplier(supplierId, Integer.parseInt(ProductId));
			}
			
			
			System.out.println("enter the minimum quantity of products for this price\nIf you wish to return to the Prices Menu press ~");
			String MinimumQuantity= in.nextLine();
			if(MinimumQuantity.equals("~"))
				return;
			while(((!verifiedMinimumQuantity(MinimumQuantity))||!Product.isPriceQuantityExist(Integer.parseInt(MinimumQuantity))||Integer.parseInt(MinimumQuantity)<1 )& !MinimumQuantity.equals("~")){
				System.out.println("Illigal input or the price for this minimum quantity is not exist");
				System.out.println("enter the minimum quantity of products for this price\nIf you wish to return to the Prices Menu press ~");
				MinimumQuantity=in.nextLine();
			}
			if(MinimumQuantity.equals("~"))
				return;
			
			BLSupplier.GetBLSupplier().RemoveAPriceOfProduct(supplierId, Integer.parseInt(ProductId), Integer.parseInt(MinimumQuantity));
			System.out.println("The price has been removed");
		}
	}
	
	private boolean verifiedMinimumQuantity(String MinimumQuantity){
		if(!SupplierMenuCLI.checkOnlyNumbers(MinimumQuantity))
			return false;
		return Integer.parseInt(MinimumQuantity)>=0;
		
	}
	
	private void AddAPriceForProduct(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Add Price of a product from a supllier Menu:");
			
			int supplierId = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(supplierId) & supplierId!=-1){
				System.out.println("The Supplier "+supplierId+" is not exist" );
				supplierId = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(supplierId==-1)
				return;
			
			String ProductId= ProductsMenuCLI.getProductId(in);
			if(ProductId.equals("~"))
				return;
			ProductFromSupplier Product=BLSupplier.GetBLSupplier().getProductFromSupplier(supplierId, Integer.parseInt(ProductId));
			while(Product==null){
				System.out.println("the supplier "+supplierId+" dont supply the product "+ProductId+"\n");
				ProductId= ProductsMenuCLI.getProductId(in);
				if(ProductId.equals("~"))
					return;
				Product=BLSupplier.GetBLSupplier().getProductFromSupplier(supplierId, Integer.parseInt(ProductId));
			}
			
			System.out.println("enter the minimum quantity of products for this price\nIf you wish to return to the Prices Menu press ~");
			String MinimumQuantity= in.nextLine();
			if(MinimumQuantity.equals("~"))
				return;
			while((!SupplierMenuCLI.checkOnlyNumbers(MinimumQuantity)) & !MinimumQuantity.equals("~")){
				System.out.println("Illigal input");
				System.out.println("enter the minimum quantity of products for this price\nIf you wish to return to the Prices Menu press ~");
				MinimumQuantity=in.nextLine();
			}
			if(MinimumQuantity.equals("~"))
				return;
			
			
			System.out.println("enter the Price for one product for this offer\nIf you wish to return to the Prices Menu press ~");
			String price= in.nextLine();
			if(price.equals("~"))
				return;
			while((!checkIfDouble(price)) & !price.equals("~")){
				System.out.println("Illigal input");
				System.out.println("enter the Price for one product for this offer\nIf you wish to return to the Prices Menu press ~");
				price=in.nextLine();
			}
			if(price.equals("~"))
				return;
			
			if(Product.addNewPrice(Integer.parseInt(MinimumQuantity), Double.valueOf(price)))
				System.out.println("The price has been added");
			else{
				System.out.println("there is allready a price for this amount");
			}
			
		}
	}
	
	static boolean checkIfDouble(String s){
		try{
			Double.valueOf(s);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	
	private void getPricesOfProductFromSupplier(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Get Prices of a product from a supllier");
			int supplierId = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(supplierId) & supplierId!=-1){
				System.out.println("The Supplier "+supplierId+" is not exist" );
				supplierId = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(supplierId==-1)
				return;
			
			String ProductId= ProductsMenuCLI.getProductId(in);
			if(ProductId.equals("~"))
				return;
			ProductFromSupplier Product=BLSupplier.GetBLSupplier().getProductFromSupplier(supplierId, Integer.parseInt(ProductId));
			while(Product==null){
				System.out.println("the supplier "+supplierId+" dont supply the product "+ProductId);
				ProductId= ProductsMenuCLI.getProductId(in);
				if(ProductId.equals("~"))
					return;
				Product=BLSupplier.GetBLSupplier().getProductFromSupplier(supplierId, Integer.parseInt(ProductId));
			}
			
			
			List<Pair<Integer, Double>> prices=Product.getPrices();
			
			if(prices.size()==0){
				System.out.println("there is no Price saved for this product from the supplier "+supplierId);
			}
			
			for(int i=0;i<prices.size();i++){
				System.out.println("Minimum Quantity :"+ prices.get(i).getKey());
				System.out.println("Price :"+ prices.get(i).getValue()+"\n");
			}
			
			
		}
	}
	
	private void getTheBestPricesOfProduct(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Get The Best Price of a product Menu:");
			
			String ProductId= ProductsMenuCLI.getProductId(in);
			if(ProductId.equals("~"))
				return;
			while(!BLSupplier.GetBLSupplier().isProductExist(Integer.parseInt(ProductId))){
				System.out.println("The Product "+ProductId +" is not exist");
				ProductId= ProductsMenuCLI.getProductId(in);
				if(ProductId.equals("~"))
					return;
			}
			
			System.out.println("enter the amount of products you want to order\nIf you wish to return to the Prices Menu press ~");
			String amount=in.nextLine();
			while((!SupplierMenuCLI.checkOnlyNumbers(amount)) & !amount.equals("~")){
				System.out.println("Illigal input");
				System.out.println("enter the amount of products you want to order\nIf you wish to return to the Prices Menu press ~");
				amount=in.nextLine();
			}
			if(amount.equals("~"))
				return;
			
			Pair<Double,ProductFromSupplier> res =BLSupplier.GetBLSupplier().getBestPrice(Integer.parseInt(ProductId), Integer.parseInt(amount));
			if(res.getValue()!= null)
				System.out.println("The best Price for the product "+ProductId+
					" if you order "+amount+" Products \nis "
					+res.getKey()+" for a Product\nfrom supplier "+res.getValue().getSupplierId()+"\n");
			else
				System.out.println("There is no Supplier that sells this product for this amount\n");
		}
	}
	

	private boolean cheackCLIMainInput(String s){
		if(s.length()!=1)
			return false;
		if(s.charAt(0)=='~')
			return true;
		if(s.charAt(0)<'1'||s.charAt(0)>'5')
			return false;
		return true;
	}
	
	
}
