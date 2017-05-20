package PL.StorageSuppliers.SupplierPL;

import java.util.Scanner;

import BL.StorageSuppliers.BLSupplier;
import BL.StorageSuppliers.InvBLManager;
import SharedClasses.StorageSuppliers.Product;
import SharedClasses.StorageSuppliers.ProductFromSupplier;

public class ProductsMenuCLI {
	
	private static ProductsMenuCLI instance;
	
	public static ProductsMenuCLI getInstance() {
		if(instance==null)
			instance=new ProductsMenuCLI();
		return instance;
	}
	
	private ProductsMenuCLI() {	}
	
	public void Start(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Supplier's Product Menu:\n1. Get All Suppliers that supply a products \n2. Get the products list of a Supplier \n3. Add A Product to a supplier \n4. Update a product catalog number \n5. Update a product Avarage Delevery Time\n6. Delete products from supplier \n~. Back to Supplier Menu. ");
			String s=in.nextLine();
			while(!cheackCLIMainInput(s)){
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			switch (s.charAt(0)){
				case ('1'):{
					getAllSuppliersOfAProduct(in);
					break;
				}
				case('2'):{
					getAllProductSupplier(in);
					break;
				}
				case('3'):{
					AddANewProduct(in);
					break;
				}
				case('4'):{
					updateAProductCatalogNumber(in);
					break;
				}
				case('5'):{
					updateAProductDeliveryTime(in);
					break;
				}
				case('6'):{
					DeleteProducts(in);
					break;
				}
				case('~'):{
					EndOfPage=true;
					break;
				}
			}
		}
	}
	
	private void DeleteProducts(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Delete a product from supplier menu:");
			int SupplierId = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(SupplierId) & SupplierId!=-1){
				System.out.println("The Supplier "+SupplierId+" is not exist" );
				SupplierId = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(SupplierId==-1)
				return;
			
			String ProductId= getProductId(in);
			if(ProductId.equals("~"))
				return;
			
			ProductFromSupplier Product=BLSupplier.GetBLSupplier().getProductFromSupplier(SupplierId, Integer.parseInt(ProductId));
			while(Product==null){
				System.out.println("The Supplier "+SupplierId+" dont supply the product number "+ProductId);
				ProductId= getProductId(in);
				if(ProductId.equals("~"))
					return;
				Product=BLSupplier.GetBLSupplier().getProductFromSupplier(SupplierId, Integer.parseInt(ProductId));
			}
			BLSupplier.GetBLSupplier().removeProductFromSupplier(SupplierId, Integer.parseInt(ProductId));
			System.out.println("The product "+ProductId+" has been removed from the product list of supplier "+SupplierId);
		}
	}
	
	private void updateAProductDeliveryTime(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Update a Product Avarage Delivey Time menu:");
			int SupplierId = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(SupplierId) & SupplierId!=-1){
				System.out.println("The Supplier "+SupplierId+" is not exist" );
				SupplierId = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(SupplierId==-1)
				return;
			
			String ProductId= getProductId(in);
			if(ProductId.equals("~"))
				return;
			ProductFromSupplier productFromSupplier=BLSupplier.GetBLSupplier().getProductFromSupplier(SupplierId, Integer.parseInt(ProductId));
			while(productFromSupplier==null){
				System.out.println("The Supplier "+SupplierId+" dont supply the product number "+ProductId);
				ProductId= getProductId(in);
				if(ProductId.equals("~"))
					return;
				productFromSupplier=BLSupplier.GetBLSupplier().getProductFromSupplier(SupplierId, Integer.parseInt(ProductId));
			}
			
			int AvarageDeleveryTime = getAvarageDeleveryTime(in);
			if(AvarageDeleveryTime==-1)
				return;
			productFromSupplier.setAvarageDeleveryTime(AvarageDeleveryTime);
			
			BLSupplier.GetBLSupplier().updateProduct(productFromSupplier);
			System.out.println("Avarage Delivery time has been updated successfully");
			
		}
	}
	
	private void updateAProductCatalogNumber(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Update a Product catalog number menu:");
			
			int SupplierId = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(SupplierId) & SupplierId!=-1){
				System.out.println("The Supplier "+SupplierId+" is not exist" );
				SupplierId = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(SupplierId==-1)
				return;
			
			String ProductId= getProductId(in);
			if(ProductId.equals("~"))
				return;
			
			ProductFromSupplier productFromSupplier=BLSupplier.GetBLSupplier().getProductFromSupplier(SupplierId, Integer.parseInt(ProductId));
			while(productFromSupplier==null){
				System.out.println("The Supplier "+SupplierId+" dont supply the product number "+ProductId);
				ProductId= getProductId(in);
				if(ProductId.equals("~"))
					return;
				productFromSupplier=BLSupplier.GetBLSupplier().getProductFromSupplier(SupplierId, Integer.parseInt(ProductId));
			}
			
			String CatalogID= getCatalogID(in);
			if(CatalogID.equals("~"))
				return;
			
			productFromSupplier.setSupplierCatalogId(Integer.parseInt(CatalogID));
			BLSupplier.GetBLSupplier().updateProduct(productFromSupplier);
			System.out.println("The Product catalog has been updated\n");
			
			
		}
	}
	
	private void AddANewProduct(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Add a Product menu:");
			int SupplierId = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(SupplierId) & SupplierId!=-1){
				System.out.println("The Supplier "+SupplierId+" is not exist" );
				SupplierId = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(SupplierId==-1)
				return;
			
			String ProductId= getProductId(in);
			if(ProductId.equals("~"))
				return;
			Product p=InvBLManager.GetInvBLManager().getProduct(Integer.parseInt(ProductId));
			while(p==null || BLSupplier.GetBLSupplier().getProductFromSupplier(SupplierId, Integer.parseInt(ProductId))!=null){
				if(p==null)
					System.out.println("the Product "+ProductId+" is not exist in the product list\n");
				else
					System.out.println("the product allready in this supplier's product list\n");
				ProductId= getProductId(in);
				if(ProductId.equals("~"))
					return;
				p=InvBLManager.GetInvBLManager().getProduct(Integer.parseInt(ProductId));
			}
			
			
			String CatalogID= getCatalogID(in);
			if(CatalogID.equals("~"))
				return;
			
			int AvarageDeleveryTime = getAvarageDeleveryTime(in);
			if(AvarageDeleveryTime==-1)
				return;
			
			
			ProductFromSupplier PFS= new ProductFromSupplier(SupplierId,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
			
			System.out.println("enter Product Price: \nIf you wish to return to the Product Menu press ~");
			String price= in.nextLine();
			while((!PricesMenuCLI.checkIfDouble(price)) & !ProductId.equals("~")){
				System.out.println("Illigal input");
				System.out.println("enter Product Price: \nIf you wish to return to the Product Menu press ~");
				price= in.nextLine();
			}
			if(price.equals("~"))
				return;
			
			
			BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
			BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
			System.out.println("The product has been added to the Supplier's product list");
		}
	}
	
	
	private void getAllSuppliersOfAProduct(Scanner in){// get all the Suppliers of a product
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Get all the Suppliers of a product menu:");
			System.out.println("enter Product Id: \nIf you wish to return to the Product Menu press ~");
			String s= in.nextLine();
			while((!SupplierMenuCLI.checkOnlyNumbers(s)|| !BLSupplier.GetBLSupplier().isProductExist(Integer.parseInt(s))) && !s.equals("~")){
				System.out.println("Illigal Product Id");
				System.out.println("enter Product Id: \nIf you wish to return to the Product Menu press ~");
				s=in.nextLine();
			}
			if(s.equals("~"))
				EndOfPage=true;
			else{
				ProductFromSupplier[] PFS=BLSupplier.GetBLSupplier().getAllSuppliersOfAProduct(Integer.parseInt(s));
				if(PFS.length==0)
					System.out.println("There is no suppliers that supply this product");
				for(int i=0;i<PFS.length;i++)
					System.out.println(PFS[i]+"----------------------------------------");
			}				
		}
	}
	
	private void getAllProductSupplier(Scanner in){// get all the Prodacts of a supplier
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Get all the product of a supplier");
			int temp = SupplierMenuCLI.receiveSupplierId(in);
			while(!BLSupplier.GetBLSupplier().VerifyExistingSupplierID(temp) & temp!=-1){
				System.out.println("The Supplier "+temp+" is not exist" );
				temp = SupplierMenuCLI.receiveSupplierId(in);
			}
			if(temp==-1)
				EndOfPage=true;
			else{
				ProductFromSupplier[] PFS=BLSupplier.GetBLSupplier().getAllProductFromSupplier(temp);
				for(int i=0;i<PFS.length;i++)
					System.out.println(PFS[i]);
				if(PFS.length==0)
					System.out.println("this supplier does not supply any product\n");
			}
				
		}
	}
	
	private int getAvarageDeleveryTime(Scanner in){
		System.out.println("enter Avarage Delevery Time in Days: \nIf you wish to return to the Product Menu press ~");
		String temp=in.nextLine();
		while((!SupplierMenuCLI.checkOnlyNumbers(temp)| temp.equals(""))& !temp.equals("~")){
			System.out.println("Illigal Input");
			System.out.println("enter Avarage Delevery Time in Days: \nIf you wish to return to the Product Menu press ~");
			temp=in.nextLine();
		}
		return Integer.parseInt(temp);
	}
	
	static String getProductId(Scanner in){
		System.out.println("enter Product Id: \nIf you wish to return to the Product Menu press ~");
		String ProductId= in.nextLine();
		while((!SupplierMenuCLI.checkOnlyNumbers(ProductId)| ProductId.equals("")) & !ProductId.equals("~")){
			System.out.println("Illigal Product Id");
			System.out.println("enter Product Id: \nIf you wish to return to the Product Menu press ~");
			ProductId=in.nextLine();
		}
		return ProductId;
	}
	
	private String getCatalogID(Scanner in){
		System.out.println("enter Catalog Id number: \nIf you wish to return to the Product Menu press ~");
		String CatalogID= in.nextLine();
		while((CatalogID.equals("")||!BLSupplier.GetBLSupplier().isCatalogIdValid(CatalogID)) & !CatalogID.equals("~")){
			System.out.println("Illigal Catalog Id number");
			System.out.println("enter Catalog Id number: \nIf you wish to return to the Product Menu press ~");
			CatalogID=in.nextLine();
		}
		return CatalogID;
	}
	
	
	private boolean cheackCLIMainInput(String s){
		if(s.length()!=1)
			return false;
		if(s.charAt(0)=='~')
			return true;
		if(s.charAt(0)<'1'||s.charAt(0)>'6')
			return false;
		return true;
	}
	
}
