package PL.StorageSuppliers.Inventory;
/**
 * Insert cli is responsible to insert a new product
 */
import java.util.LinkedList;
import java.util.Scanner;

import BL.StorageSuppliers.Calculations;
import BL.StorageSuppliers.InvBLManager;
import SharedClasses.StorageSuppliers.Category;
import SharedClasses.StorageSuppliers.Product;

public class InsertCLI {
	private static InsertCLI instance;
	private Calculations calc;
	private InvBLManager BL;
	private InventoryCLI inv_cli;
	private CategoryCLI cat_cli;
	/**
	 * Insert cli Constructor 
	 */
	private InsertCLI(){
		BL=InvBLManager.GetInvBLManager();
		cat_cli=CategoryCLI.GetCategoryCLI();
		calc=Calculations.GetCalculations();
	}
	/**
	 * 
	 * @return the singleton instance
	 */
	public static InsertCLI GetInsertCLI(){
		if(instance==null)
			instance= new InsertCLI();
		return instance;
	}
	/**
	 * Responsible for product insertion
	 */
	void insertProductMenu(Scanner in){
		inv_cli=InventoryCLI.GetInvCLIManager();
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("Insert product menu:\nEnter product ID.\nIf you wish to return to the main menu press ~ ");
			String s=in.nextLine();
			while(!s.equals("~")){
				if(calc.checkInt(s)&&!inv_cli.checkProductId(s))
					break;
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			if(s.equals("~"))
				break;
				//inv_cli.mainMenu();
			else{
				Product product=new Product(Integer.parseInt(s));

				System.out.println("Enter product name\nIf you wish to return to the main menu press ~");
				String name=in.nextLine();
				while(!name.equals("~"))
				{
					if(name.length()>0)
						break;
					else
						System.out.println("Illegal input, please try again.");
					name=in.nextLine();
				}
				if(name.equals("~"))
					break;
					//inv_cli.mainMenu();
				else{
					product.setName(name);
				}

				System.out.println("Enter product manufacturer\nIf you wish to return to the main menu press ~");
				String manufacturer=in.nextLine();
				while(!manufacturer.equals("~")){
					if(manufacturer.length()>0)
						break;
					else
						System.out.println("Illegal input, please try again.");
					manufacturer=in.nextLine();
				}
				if(manufacturer.equals("~"))
					break;
					//inv_cli.mainMenu();
				else{
					product.setManufacturer(manufacturer);
				}

				System.out.println("Enter product quantity in store\nIf you wish to return to the main menu press ~");
				String quan=in.nextLine();
				while(!(quan.equals("~"))&&!calc.checkInt(quan)){
					System.out.println("Illegal input, please try again.");
					quan=in.nextLine();
				}
				if(quan.equals("~"))
					break;
					//break;
					//inv_cli.mainMenu();
				else{
					product.setStoreQuantity(Integer.parseInt(quan));
				}


				System.out.println("Enter product quantity in warehouse\nIf you wish to return to the main menu press ~");
				String quan2=in.nextLine();
				while(!(quan2.equals("~"))&&!calc.checkInt(quan2)){
					System.out.println("Illegal input, please try again.");
					quan2=in.nextLine();
				}
				if(quan2.equals("~"))
					break;
					//break;
					//inv_cli.mainMenu();
				else{
					product.setWarehouseQuantity(Integer.parseInt(quan2));
				}

				System.out.println("Enter average product sells for a day\nIf you wish to return to the main menu press ~");
				String avrgS=in.nextLine();
				while(!(avrgS.equals("~"))&&!calc.checkInt(avrgS)){
					System.out.println("Illegal input, please try again.");
					avrgS=in.nextLine();
				}
				if(avrgS.equals("~"))
					break;
					//inv_cli.mainMenu();
				else{
					product.setSalesPerDay(Integer.parseInt(avrgS));
				}

				System.out.println("Enter product category name. If you wish to see all categories please enter 'all'.\nIf you wish to return to the main Menu press ~");
				String cat=in.nextLine();
				while(!cat.equals("~")){
					if(BL.checkCategoryName(cat))
						break;
					if(cat.equals("all")){
						Category[]category=BL.getAllCategories();
						LinkedList<Category>c=new LinkedList<>();
						for(int i=0;i<category.length;i++)
							c.add(category[i]);
						cat_cli.printCategory(c,-1,0);
					}
					else{
						System.out.println("Illegal input, please try again.");
					}
					cat=in.nextLine();
				}
				if(cat.equals("~"))
					break;
					//inv_cli.mainMenu();
				else{
					product.setCategory(BL.getCategoryByName(cat).getId());

				}

				System.out.println("Enter the product selling cost to customer\nIf you wish to return to the main menu press ~");
				String sell=in.nextLine();
				while(!(sell.equals("~"))&&!calc.checkDouble(sell)){
					System.out.println("Illegal input, please try again.");
					sell=in.nextLine();
				}
				if(sell.equals("~"))
					break;
					//inv_cli.mainMenu();
				else{
					product.setPriceToSell(Double.parseDouble(sell));
				}

				System.out.println("Enter product location in store\nIf you wish to return to the main menu press ~");
				String store=in.nextLine();
				while(!store.equals("~")){
					if(store.length()>0)
						break;
					System.out.println("Illegal input, please try again.");
					store=in.nextLine();
				}
				if(store.equals("~"))
					break;
					//inv_cli.mainMenu();
				else{
					product.setStoreLoc(store);
				}

				System.out.println("Enter product location in warehouse\nIf you wish to return to the main menu press ~");
				String ware=in.nextLine();
				while(!ware.equals("~")){
					if(ware.length()>0)
						break;
					System.out.println("Illegal input, please try again.");
					ware=in.nextLine();
				}
				if(ware.equals("~"))
					break;
					//inv_cli.mainMenu();
				else{
					product.setWareLoc(ware);
				}
				System.out.println("Enter the weight of the product\nIf you wish to return to the main menu press ~");
				String weight=in.nextLine();
				while(!(weight.equals("~"))&&!calc.checkDouble(weight)){
					System.out.println("Illegal input, please try again.");
					weight=in.nextLine();
				}
				if(weight.equals("~"))
					break;
					//inv_cli.mainMenu();
				else{
					product.setWeight(Double.parseDouble(weight));
				}
				BL.addNewProduct(product);
				System.out.println("Product added successfully");
				//insertProductMenu();
			}
		}
	}
}
