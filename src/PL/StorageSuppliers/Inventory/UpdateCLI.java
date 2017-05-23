package PL.StorageSuppliers.Inventory;
/**
 * Update cli is responsible for updating aproduct
 */
import java.util.LinkedList;
import java.util.Scanner;

import BL.StorageSuppliers.Calculations;
import BL.StorageSuppliers.InvBLManager;
import SharedClasses.StorageSuppliers.Category;
import SharedClasses.StorageSuppliers.Product;

public class UpdateCLI {
	private static UpdateCLI instance;

	private InvBLManager BL;
	private InventoryCLI inv_cli;
	private CategoryCLI cat_cli;
	private Calculations calc;
	/**
	 * Constructor
	 */
	private UpdateCLI(){
		BL=InvBLManager.GetInvBLManager();
		cat_cli=CategoryCLI.GetCategoryCLI();
		calc=Calculations.GetCalculations();
	}
	/**
	 * 
	 * @return the singleton instance
	 */
	public static UpdateCLI GetUpdateCLI(){
		if(instance==null)
			instance= new UpdateCLI();
		return instance;
	}
	/**
	 * Update menu
	 */
	void updateProductMenu(Scanner in){
		inv_cli=InventoryCLI.GetInvCLIManager();
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("Update product menu:\nEnter product ID.\nIf you wish to return to the inventory menu press ~ ");
			String s=in.nextLine();
			while(!s.equals("~")&&!inv_cli.checkProductId(s)){
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			if(s.equals("~"))
				break;
			//inv_cli.mainMenu();
			else{
				int temp = Integer.parseInt(s);
				Product product = BL.getProduct(temp);
				updateProduct(product,in);
			}
		}
	}
	/**
	 * update product menu
	 * @param Product
	 */
	/**
	 * Handles an update on a product
	 * @param product
	 */
	private void updateProduct(Product product,Scanner in){
		inv_cli=InventoryCLI.GetInvCLIManager();
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("1. Update name.\n2. Update warehouse,store or sales per day quantity.\n3. Update manufacturer."
					+ "\n4. Update product prices and discounts\n"
					+"5. Update category.\n6. Update product location."
					+ "\n7. Report if product is expired or has a defect\n8. Update weight.\n~. Back.");
			String s2=in.nextLine();
			while(!calc.checkKeyboardMenu(s2, 1, 8)&&!s2.equals("~")){
				System.out.println("Illegal input, please try again.");
				s2=in.nextLine();
			}
			if(s2.equals("~"))
				return;
			switch (Integer.parseInt(s2)){
			case(1):{
				System.out.println("Enter name.\nIf you wish to return to the update menu press ~");
				String s3=in.nextLine();
				while(!(s3.equals("~")))
				{
					if(s3.length()>0)
						break;
					else{
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
				}
				if(s3.length()==1&&s3.charAt(0)=='~')
					continue;
				//updateProduct(product);
				else{
					product.setName(s3);
					BL.updateProduct(product);
				}
				break;
			}
			case(2):{
				System.out.println("Quantity menu.\n1. Increase/decrease store quantity.\n2. Set store quantity.\n3. Increase/decrease warehouse quantity.\n4. Set warehouse quantity.\n5. Update the average sales of this product per day."
						+ "\n~. Return to update menu.");
				String s4=in.nextLine();
				while(!calc.checkKeyboardMenu(s4, 1, 5)&&!s4.equals("~")){
					System.out.println("Illegal input, please try again.");
					s4=in.nextLine();
				}
				if(s4.equals("~"))
					continue;
				switch (s4.charAt(0)){
				case('1'):{
					System.out.println("Enter the number you wish to increase/decrease to store quantity.\nIf you wish to decrease the amount of products please enter negative numbers."
							+ "\nIf you wish to return to the update menu press ~");
					String s3=in.nextLine();
					while(!s3.equals("~"))
					{
						if(calc.checkInt(s3))
							if(Integer.parseInt(s3)+product.getStoreQuantity()>=0)
								break;
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
					if(s3.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setStoreQuantity(Integer.parseInt(s3)+product.getStoreQuantity());
						BL.updateProduct(product);
					}
					break;
				}

				case('2'):{
					System.out.println("Enter the number you wish to set the store quantity."
							+ "\nIf you wish to return to the update menu press ~");
					String s3=in.nextLine();
					while(!s3.equals("~")&&!calc.checkOnlyNumbers(s3))
					{
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
					if(s3.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setStoreQuantity(Integer.parseInt(s3));
						BL.updateProduct(product);
					}
					break;
				}

				case('3'):{
					System.out.println("Enter the number you wish to increase/decrease to warehouse quantity.\nIf you wish to decrease the amount of products please enter negative numbers."
							+ "\nIf you wish to return to the update menu press ~");
					String s3=in.nextLine();
					while(!s3.equals("~"))
					{
						if(calc.checkInt(s3))
							if(Integer.parseInt(s3)+product.getWarehouseQuantity()>=0)
								break;
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
					if(s3.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setWarehouseQuantity(Integer.parseInt(s3)+product.getWarehouseQuantity());
						BL.updateProduct(product);
					}
					break;
				}

				case('4'):{
					System.out.println("Enter the number you wish to set the warehouse quantity."
							+ "\nIf you wish to return to the update menu press ~");
					String s3=in.nextLine();
					while(!(s3.equals("~"))&&!calc.checkOnlyNumbers(s3))
					{
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
					if(s3.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setWarehouseQuantity(Integer.parseInt(s3));
						BL.updateProduct(product);
					}
					break;
				}

				case('5'):{
					System.out.println("Enter the average sales of this product per day."
							+ "\nIf you wish to return to the update menu press ~");
					String s3=in.nextLine();
					while(!s3.equals("~")&&!calc.checkOnlyNumbers(s3))
					{
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
					if(s3.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setSalesPerDay(Integer.parseInt(s3));
						BL.updateProduct(product);
					}
					break;
				}		
				}
				break;
			}
			case(3):{
				System.out.println("Enter manufacturer.\nIf you wish to return to the update menu press ~");
				String s3=in.nextLine();
				while(!s3.equals("~")){
					if(s3.length()>0)
						break;
					else
						System.out.println("Illegal input, please try again.");
					s3=in.nextLine();
				}
				if(s3.equals("~"))
					continue;
				//updateProduct(product);
				else{
					product.setManufacturer(s3);
					BL.updateProduct(product);
				}
				break;
			}
			case(4):{
				System.out.println("Prices and discounts menu.\n1. Update selling price to customer."
						+ "\n2. Update discount to costumer.\n~. Return to update menu.");
				String s4=in.nextLine();
				while(!calc.checkKeyboardMenu(s4, 1, 2)&&!s4.equals("~")){
					System.out.println("Illegal input, please try again.");
					s4=in.nextLine();
				}
				if(s4.equals("~"))
					continue;
				switch (s4.charAt(0)){
				case('1'):{
					System.out.println("Enter the selling price to the customer.\nIf you wish to return to the update menu press ~");
					String s3=in.nextLine();
					while(!s3.equals("~")&&!calc.checkDouble(s3)){
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
					if(s3.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setPriceToSell(Double.parseDouble(s3));
						BL.updateProduct(product);
					}
					break;
				}
				case('2'):{
					System.out.println("Enter discount to the customer.\nTo cancel previous discounts press -1.\nIf you wish to return to the update menu press ~");
					String s3=in.nextLine();
					while(!(s3.equals("~"))&&!(s3.equals("-1"))&&!calc.checkOnlyNumbers(s3)){
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
					if(s3.equals("~"))
						continue;
					//updateProduct(product);
					else{
						if(Integer.parseInt(s3)==-1){
							product.setDiscountToCus(0);
							product.setDiscountStartTime(null);
							product.setDiscountFinishTime(null);
							BL.updateProduct(product);
						}
						else{
							System.out.println("Enter the start date of the discount with format dd.MM.yyyy.\nIf you wish to return to the update menu press ~");
							String s5=in.nextLine();
							while(!(s5.equals("~"))&&!calc.checkDate(s5)){
								System.out.println("Illegal input, please try again.");
								s5=in.nextLine();
							}
							if(s5.equals("~"))
								continue;
							//updateProduct(product);
							else{
								System.out.println("Enter the end date of the discount with format dd.MM.yyyy.\nIf you wish to return to the update menu press ~");
								String s6=in.nextLine();
								boolean set=false;
								while(!(s6.equals("~"))&&!set){
									if(calc.checkDate(s6))
										if(inv_cli.ComperDates(s5, s6))
											set=true;
									if(!set){
										System.out.println("Illegal input, please try again.");
										s6=in.nextLine();
									}
								}
								if(s6.equals("~"))
									continue;
								//updateProduct(product);
								else{
									product.setDiscountToCus(Integer.parseInt(s3));
									product.setDiscountStartTime(s5);
									product.setDiscountFinishTime(s6);
									BL.updateProduct(product);
								}
							}
						}
					}
					break;
				}
				}
				break;
			}
			case(5):{
				System.out.println("Enter product category name. If you wish to see all categories please enter 'all'.\nIf you wish to return to the update menu press ~");
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
					continue;
				//updateProduct(product);
				else{
					product.setCategory(BL.getCategoryByName(cat).getId());
					BL.updateProduct(product);
				}
				break;
			}

			case(6):{
				System.out.println("Product location menu.\n1. Update product store location.\n2. Update product warehouse location."
						+ "\n~. Return to update menu.");
				String s4=in.nextLine();
				while(!calc.checkKeyboardMenu(s2, 1, 2)&&!s4.equals("~")){
					System.out.println("Illegal input, please try again.");
					s4=in.nextLine();
				}
				if(s4.equals("~"))
					continue;
				switch (s4.charAt(0)){
				case('1'):{
					System.out.println("Enter store location.\nIf you wish to return to the update menu press ~");
					String s3=in.nextLine();
					while(!s3.equals("~"))
					{
						if(s3.length()>0)
							break;
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
					if(s3.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setStoreLoc(s3);
						BL.updateProduct(product);
					}
					break;
				}

				case('2'):{
					System.out.println("Enter warehouse location.\nIf you wish to return to the update menu press ~");
					String s3=in.nextLine();
					while(!s3.equals("~"))
					{
						if(s3.length()>0)
							break;
						System.out.println("Illegal input, please try again.");
						s3=in.nextLine();
					}
					if(s3.equals("~"))
						updateProduct(product,in);
					else{
						product.setWareLoc(s3);
						BL.updateProduct(product);
					}
					break;
				}		
				}
				break;
			}
			case(7):{
				System.out.println("Expired or defective products menu:"
						+ "\n1. Increase/Decrease defective products in store."
						+ "\n2. Set amount of products that are defective in the store."
						+ "\n3. Increase/Decrease defective products in warehouse."
						+ "\n4. Set amount of products that are defective in the warehouse."
						+ "\n~.Return to update menu.");
				String s4=in.nextLine();
				while(!calc.checkKeyboardMenu(s4, 1, 4)&&!s4.equals("~")){
					System.out.println("Illegal input, please try again.");
					s4=in.nextLine();
				}
				if(s4.equals("~"))
					continue;
				switch (s4.charAt(0)){
				case('1'):{
					System.out.println("Increase/decrease the amount of products that are expired or have a defect in store. "
							+ "\nIf you wish to decrease the defective amount of expired product please enter negative numbers."
							+ "\nIf you wish to return to the update menu press ~");
					String exp=in.nextLine();
					boolean inserted=false;
					while(!exp.equals("~")&&!inserted){
						if(calc.checkInt(exp)){
							if((product.getStoreDefective()+Integer.parseInt(exp))>=0&&
									(product.getStoreQuantity()>=(Integer.parseInt(exp)+product.getStoreDefective())))
								inserted=true;
							if(product.getStoreDefective()+Integer.parseInt(exp)<0)
								System.out.println("Cannot have negative number of defective or expired products");
						}
						if(!inserted){
							System.out.println("Illegal input, please try again.");
							exp=in.nextLine();
						}
					}
					if(exp.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setStoreDefective(Integer.parseInt(exp)+product.getStoreDefective());
						BL.updateProduct(product);
					}
					break;
				}
				case('2'):{
					System.out.println("Set the amount of products that are expired or have a defect in store. "
							+ "\nIf you wish to return to the update menu press ~");
					String exp=in.nextLine();
					boolean inserted=false;
					while(!exp.equals("~")&&!inserted){
						if(calc.checkInt(exp)){
							if(Integer.parseInt(exp)>=0&&(product.getStoreQuantity()>=Integer.parseInt(exp)))
								inserted=true;
						}
						if(!inserted){
							System.out.println("Illegal input, please try again.");
							exp=in.nextLine();
						}
					}
					if(exp.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setStoreDefective(Integer.parseInt(exp));
						BL.updateProduct(product);
					}
					break;
				}
				case('3'):{
					System.out.println("Enter the amount of products that are expired or have a defect in warehouse. "
							+ "\nIf you wish to decrease the defective amount of expired product please enter negative numbers."
							+ "\nIf you wish to return to the update menu press ~");
					String exp=in.nextLine();
					boolean inserted=false;
					while(!exp.equals("~")&&!inserted){
						if(calc.checkInt(exp)){
							if((product.getWareDefective()+Integer.parseInt(exp))>=0&&
									(product.getWarehouseQuantity()>=(Integer.parseInt(exp)+product.getWareDefective())))
								inserted=true;
							if(product.getWareDefective()+Integer.parseInt(exp)<0)
								System.out.println("Cannot have negative number of defective or expired products");
						}
						if(!inserted){
							System.out.println("Illegal input, please try again.");
							exp=in.nextLine();
						}
					}
					if(exp.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setWareDefective(Integer.parseInt(exp)+product.getWareDefective());
						BL.updateProduct(product);
					}
					break;
				}
				case('4'):{
					System.out.println("Set the amount of products that are expired or have a defect in warehouse. "
							+ "\nIf you wish to return to the update menu press ~");
					String exp=in.nextLine();
					boolean inserted=false;
					while(!exp.equals("~")&&!inserted){
						if(calc.checkInt(exp)){
							if(Integer.parseInt(exp)>=0&&(product.getWarehouseQuantity()>=Integer.parseInt(exp)))
								inserted=true;
						}
						if(!inserted){
							System.out.println("Illegal input, please try again.");
							exp=in.nextLine();
						}
					}
					if(exp.equals("~"))
						continue;
					//updateProduct(product);
					else{
						product.setWareDefective(Integer.parseInt(exp));
						BL.updateProduct(product);
					}
					break;
				}
				}
				break;
			}		
			case(8):{
				//inv_cli.mainMenu();
				System.out.println("Enter the weight\nIf you wish to return to the main menu press ~");
				String weight=in.nextLine();
				while(!(weight.equals("~"))&&!calc.checkInt(weight)){
					System.out.println("Illegal input, please try again.");
					weight=in.nextLine();
				}
				if(weight.equals("~"))
					break;
					//inv_cli.mainMenu();
				else{
					product.setWeight(Integer.parseInt(weight));
				}
				BL.updateProduct(product);
				break;
				}
			}
			System.out.println("Product updated successfully");
			//updateProduct(product);
		}
	}
}
