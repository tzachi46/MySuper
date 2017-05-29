package PL.StorageSuppliers.Inventory;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

import BL.StorageSuppliers.Calculations;
import BL.StorageSuppliers.InvBLManager;
import SharedClasses.StorageSuppliers.Category;
import SharedClasses.StorageSuppliers.Product;

public class CategoryCLI {
	/**
	 * Category CLI is responsible for the category interface
	 * This class is a singleton
	 */
	private static CategoryCLI instance;

	private InvBLManager BL;
	private InventoryCLI inv_cli;
	private Calculations calc;
	/**
	 * constructor
	 */
	private CategoryCLI(){
		BL=InvBLManager.GetInvBLManager();
		calc=Calculations.GetCalculations();
	}
	/**
	 * Singleton creation if needed
	 * @return singleton instance
	 */
	public static CategoryCLI GetCategoryCLI(){
		if(instance==null)
			instance= new CategoryCLI();
		return instance;
	}
	/**
	 * category manager menu
	 */
	public void categoryManager(Scanner in){
		inv_cli=InventoryCLI.GetInvCLIManager();
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("Category manager menu:\n1. Get category list.\n2. Add new category.\n3. Edit category.\n4. Delete category.\n5. Category inventory report."
					+ "\n6. Category Details.\n~. Back to inventory menu");
			String input=in.nextLine();
			Category[]category=BL.getAllCategories();
			LinkedList<Category>c=new LinkedList<>();
			for(int i=0;i<category.length;i++)
				c.add(category[i]);
			while(!calc.checkKeyboardMenu(input,1,6)&&!input.equals("~"))
			{
				System.out.println("Illegal input, please try again.");
				input=in.nextLine();
			}
			switch(input.charAt(0)){
			case('1'):{
				System.out.println("Store categories list:");
				printCategory(c,-1,0);
				System.out.println("\nIf you wish to return to the category menu press ~");
				String s=in.nextLine();
				while(!s.equals("~")){
					System.out.println("Illegal input, please try again.");
					s=in.nextLine();
				}
				continue;
				//break;
			}
			case('2'):{
				insertCategory(category,in);
				continue;
				//break;
			}
			case('3'):{
				System.out.println("Please enter the category name you wish to edit.\nIf you wish to return to category manager menu press ~");
				String cat=in.nextLine();
				while(!BL.checkCategoryName(cat)&&!(cat.equals("~")))
				{
					System.out.println("This category does not exists, please enter category name again.\nTo go back to category manager menu press '~'.");
					cat=in.nextLine();
				}
				if(cat.equals("~"))
					continue;
				Category cat1=BL.getCategoryByName(cat);
				updateCategory(cat1,in);
				continue;
				//break;
			}
			case('4'):{
				System.out.println("Please enter the category name you wish to remove.\nIf you wish to return to category manager menu press ~");
				String cat=in.nextLine();
				while(!(cat.equals("~")))
				{
					if(BL.checkCategoryName(cat)&&!cat.equals("ALL STORE"))
						break;
					if(cat.equals("ALL STORE")){
						System.out.println("CANNOT DELETE ALL STORE CATEGORY");
					}
					else{
						System.out.println("This category does not exists, please enter category name again.\nTo go back to category manager menu press '~'.");	
					}
					cat=in.nextLine();
				}
				if(cat.equals("~"))
					continue;
				Category cat1=BL.getCategoryByName(cat);
				for(int i=0;i<category.length;i++){
					if(category[i].getFatherId()==cat1.getId()){
						category[i].setFatherId(cat1.getFatherId());
						BL.updateCategory(category[i]);
					}
				}
				BL.removeCategory(cat1);
				System.out.println("Category removed successfully.\n");
				categoryManager(in);
				break;
			}
			case('5'):{
				System.out.println("Category inventory report:\nEnter category name.\nIf you wish to see all categories please enter 'all'."
						+ "\nIf you wish to return to category manager menu press ~");
				String cat=in.nextLine();
				while(!cat.equals("~")){
					if(BL.checkCategoryName(cat))
						break;
					if(cat.equals("all")){
						printCategory(c,-1,0);
						System.out.println("Enter category name.\nIf you wish to see all categories please enter 'all'."
								+ "\nIf you wish to return to category manager menu press ~");
					}
					else{
						System.out.println("Illegal input, please try again.");
					}
					cat=in.nextLine();
				}
				if(cat.equals("~"))
					continue;
				else{
					LinkedList<Category>catToCheck=new LinkedList<>();
					catToCheck.add(BL.getCategoryByName(cat));
					System.out.println("Category added successfully!");
					while(!cat.equals("#")){
						System.out.println("If you wish to add one more category enter it's name.\nIf you are done please press '#'."
								+ "\nIf you wish to see all categories please enter 'all'."
								+ "\nIf you wish to return to category manager menu press ~");
						cat=in.nextLine();
						if(cat.equals("~"))
							break;
						if(cat.equals("all")){
							printCategory(c,-1,0);
							continue;
						}
						if(BL.checkCategoryName(cat)){
							boolean inserted=false;
							for(int a=0;a<catToCheck.size();a++)
								if(catToCheck.get(a).getName().equals(cat))
									inserted=true;
							if(inserted==false){
								catToCheck.add(BL.getCategoryByName(cat));
								System.out.println("Category added successfully!");
							}
							else
								System.out.println("Category already inserted!");
						}
						else {
							if(!cat.equals("#"))
								System.out.println("Illegal input, please try again.");
						}
					}
				if(!cat.equals("~")){
					for(int k=0;k<catToCheck.size();k++){
						for(int j=0;j<category.length;j++){
							boolean inserted=false;
							if(category[j].getFatherId()==catToCheck.get(k).getId()){
								for(int p=0;p<catToCheck.size();p++){
									if(category[j].getId()==catToCheck.get(p).getId())
										inserted=true;
								}
								if(!inserted)
									catToCheck.add(category[j]);
							}
						}
					}
					System.out.print("Category report for categories: ");
					for(int l=0;l<catToCheck.size();l++){
						if(l<catToCheck.size()-1)
							System.out.print(catToCheck.get(l).getName()+", ");
						else
							System.out.print(catToCheck.get(l).getName());
					}
					System.out.println("\n----------------------------------------");
					LinkedList<Product>p=BL.getProductInInventoryList();
					for(int i=0;i<catToCheck.size();i++){
						printProductInCat(catToCheck.get(i),p);
					}
				System.out.println("\nIf you wish to return to the category manager menu press ~");
				String s2=in.nextLine();
				while(!s2.equals("~")){
					System.out.println("Illegal input, please try again.");
					s2=in.nextLine();
				}
				}
				continue;
				//break;
				}
			}
			case('6'):{
				System.out.println("Please enter the category name.\nIf you wish to return to category manager menu press ~");
				String cat=in.nextLine();
				while(!BL.checkCategoryName(cat)&&!(cat.equals("~")))
				{
					System.out.println("This category does not exists, please enter category name again.\nTo go back to category manager menu press '~'.");
					cat=in.nextLine();
				}
				if(cat.equals("~"))
					continue;
				Category cat1=BL.getCategoryByName(cat);
				String parentName;
				if(cat1.getFatherId()!=-1)
					parentName=BL.getCategoryById(cat1.getFatherId()).getName();
				else
					parentName="None";
				System.out.println("Category Details:\nCategory id: "+cat1.getId()+"\nCategory name: "+cat1.getName()+"\nParent category: "+parentName+
						"\nDiscount: "+cat1.getDiscount()+"\nDiscount start date: "+cat1.getDiscountStartTime()+"\nDiscount finish time: "+cat1.getDiscountFinishTime()+"\n");
				System.out.println("If you wish to return to category manager menu press ~");
				cat=in.nextLine();
				while(!cat.equals("~"))
				{
					System.out.println("Illegal input, please try again.");
					cat=in.nextLine();
				}
				continue;
				//break;
			}
			case('~'):{
				endOfPage=true;
				break;
			}
			}
		}
	}
	/**
	 * prints the categories
	 * @param c
	 * @param p
	 */
	private void printProductInCat(Category c,LinkedList<Product>p){
		for(int i=0;i<p.size();i++){
			if(p.get(i).getCategory()==c.getId())
			{
				System.out.println("ID: "+p.get(i).getId()+"\nName: "+p.get(i).getName()+
						"\nStore quantity: "+p.get(i).getStoreQuantity()+"\nWarehouse quantity:"+p.get(i).getWarehouseQuantity()
						+"\nAmmount of expired/defective in store: "+p.get(i).getStoreDefective()
						+"\nAmmount of expired/defective in warehouse: "+p.get(i).getWareDefective()+"\nMinimum recommanded quantity "
						+ "in inventory of the product: "+p.get(i).getWarningQuantity()+"\n----------------------------------------");
			}
		}
	}
	/**
	 * This function is responsible for the category update
	 * @param cat
	 */
	private void updateCategory(Category cat,Scanner in){
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("Category update menu:\n1. Update name.\n2. Update discount.\n3. Update discount start time.\n4. Update discount finish time."
					+ "\n5. Update parent category.\n"
					+"~. Back to category manager.");
			String s2=in.nextLine();
			while(!calc.checkKeyboardMenu(s2, 1, 5)&&!s2.equals("~")){
				System.out.println("Illegal input, please try again.");
				s2=in.nextLine();
			}
			switch(s2.charAt(0)){
			case('1'):{
				System.out.println("Enter name.\nIf you wish to return to the update menu press ~");
				String s3=in.nextLine();
				while(!s3.equals("~"))
				{
					if(s3.length()>0)
						if(!cat.getName().equals("ALL STORE")&&!BL.checkCategoryName(s3))
							break;
					if(cat.getName().equals("ALL STORE"))
					{
						System.out.println("CANNOT UPDATE ALL STORE NAME.");
						continue;
						//updateCategory(cat);
					}
					System.out.println("Illegal input, please try again.");
					s3=in.nextLine();
				}
				if(s3.equals("~"))
					continue;
					//updateCategory(cat);
				else{
					cat.setName(s3);
					BL.updateCategory(cat);
				}
				break;
			}
			case('2'):{
				System.out.println("Enter discount to the customer.\nTo cancel previous discounts press -1.\nIf you wish to return to the update menu press ~");
				String s3=in.nextLine();
				while(!s3.equals("~")&&!s3.equals("-1")&&!calc.checkOnlyNumbers(s3)){
					System.out.println("Illegal input, please try again.");
					s3=in.nextLine();
				}
				if(s3.equals("~"))
					continue;
					//updateCategory(cat);
				else{
					if(Integer.parseInt(s3)==-1){
						cat.setDiscount(0);
						cat.setDiscountStartTime(null);
						cat.setDiscountFinishTime(null);
						BL.updateCategory(cat);
					}
					else{
						System.out.println("Enter the start date of the discount with format dd.MM.yyyy.\nIf you wish to return to the update menu press ~");
						String s5=in.nextLine();
						while(!(s5.length()==1&&s5.charAt(0)=='~')&&!calc.checkDate(s5)){
							System.out.println("Illegal input, please try again.");
							s5=in.nextLine();
						}
						if(s5.equals("~"))
							continue;
							//updateCategory(cat);
						else{
							System.out.println("Enter the end date of the discount with format dd.MM.yyyy.\nIf you wish to return to the update menu press ~");
							String s6=in.nextLine();
							boolean set=false;
							while(!s6.equals("~")&&!set){
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
								//updateCategory(cat);
							else{
								cat.setDiscount(Integer.parseInt(s3));
								cat.setDiscountStartTime(s5);
								cat.setDiscountFinishTime(s6);
								BL.updateCategory(cat);
							}
						}
					}
				}
				break;
			}
			case('3'):{
				System.out.println("Enter the start date of the discount with format dd.MM.yyyy.\nIf you wish to return to the update menu press ~");
				String s5=in.nextLine();
				boolean set=false;
				while(!(s5.equals("~"))&&!set){
					if(calc.checkDate(s5))
						if(inv_cli.ComperDates(s5, cat.getDiscountFinishTime()))
							set=true;
					if(!set){
						System.out.println("Illegal input, please try again.");
						s5=in.nextLine();
					}
				}
				if(s5.equals("~"))
					continue;
					//updateCategory(cat);
				cat.setDiscountStartTime(s5);
				BL.updateCategory(cat);
				break;
			}
			case('4'):{
				System.out.println("Enter the end date of the discount with format dd.MM.yyyy.\nIf you wish to return to the update menu press ~");
				String s6=in.nextLine();
				boolean set=false;
				while(!s6.equals("~")&&!set){
					if(calc.checkDate(s6))
						if(inv_cli.ComperDates(cat.getDiscountStartTime(), s6))
							set=true;
					if(!set){
						System.out.println("Illegal input, please try again.");
						s6=in.nextLine();
					}
				}
				if(s6.equals("~"))
					continue;
					//updateCategory(cat);
				cat.setDiscountFinishTime(s6);
				BL.updateCategory(cat);
				break;
			}
			case('5'):{
				System.out.println("Enter parent category.\nIf you wish to return to the update menu press ~");
				String s6=in.nextLine();
				while(!s6.equals("~")){
					if(!BL.checkCategoryName(s6))
						System.out.println("This category does not exists.\nTo go back to category manager menu press '~'.");
					else{
						if(isCircularCategory(cat,BL.getCategoryByName(s6)))
							System.out.println("Your sub-category cannot be your parent category.\nTo go back to category manager menu press '~'.");
						else
							break;
					}
					System.out.println("Illegal input, please try again.");
					s6=in.nextLine();

				}
				if(s6.equals("~"))
					continue;
					//updateCategory(cat);
				Category temp=BL.getCategoryByName(s6);
				cat.setFatherId(temp.getId());
				BL.updateCategory(cat);
				break;
			}
			case('~'):{
				//categoryManager();
				return;
				//break;
			}
			}
			System.out.println("Category updated successfully");
			//updateCategory(cat);
		}
	}
	/**
	 * checks if the category is a parent category and also a child category
	 * @param child
	 * @param parent
	 * @return
	 */
	private boolean isCircularCategory(Category child,Category parent){
		//Category[]category=BL.getAllCategories();
		while(parent.getId()!=0){
			if(parent.getId()==child.getId())
				return true;
			parent=BL.getCategoryById(parent.getFatherId());
		}
		return false;

	}
	/**
	 * Prints all the categories
	 * @param c
	 * @param fatherId
	 * @param tabs
	 */
	void printCategory(LinkedList<Category>c,int fatherId,int tabs){
		boolean finished=false;
		int i=0;
		while(!finished&&c.size()>0){
			if(c.get(i).getFatherId()==fatherId)
			{
				for(int k=0;k<tabs;k++)
					System.out.print("\t");
				System.out.println("Category Id: "+c.get(i).getId());
				for(int k=0;k<tabs;k++)
					System.out.print("\t");
				System.out.println("Category Name: "+c.get(i).getName());
				int tempFatherId=c.get(i).getId();
				//c.remove(i);
				int tab=tabs+1;
				printCategory(c,tempFatherId,tab);
			}
			i=i+1;
			if(i==c.size())
				finished=true;
		}
	}
	/**
	 * Insert category menu
	 * @param category
	 */
	private void insertCategory(Category[]category,Scanner in){
		System.out.println("Add new category:\nPlease enter the category name.\nTo go back to category manager menu press '~'.");
		String cat=in.nextLine();
		while(!(cat.equals("~")))
		{
			if(cat.length()>0&&!BL.checkCategoryName(cat))
				break;
			System.out.println("This category already exists, please enter category name again.\nTo go back to category manager menu press '~'.");
			cat=in.nextLine();
		}
		if(cat.equals("~"))
			return;
		//categoryManager();
		boolean inserted=false;
		int id=1;
		for(int j=0;j<category.length;j++){
			if(category[j].getId()==id){
				id++;
				j=0;
			}
		}
		Category catToWork=new Category(id);
		catToWork.setName(cat);
		while(!inserted){
			int catNum=0;
			String catName="ALL STORE";
			for(int i=0;i<category.length;i++){
				if(catToWork.getFatherId()==category[i].getId()){
					catName=category[i].getName();
					catNum=category[i].getId();
				}
			}
			Vector<Integer>vec=new Vector<>();
			System.out.println("Category name: "+catName+"\nSub-categories in this category:");
			for(int j=0;j<category.length;j++){
				if(category[j].getFatherId()==catNum){
					vec.add(j);
					System.out.println("Category id: "+category[j].getId()+". Category name: "+category[j].getName());
				}
			}
			System.out.println("\nTo enter any sub-category please enter it's id.\nTo insert your category here enter '#'.\nTo go back to menu press '~'.");
			cat=in.nextLine();
			while(!cat.equals("~")&&!cat.equals("#")){
				if(calc.checkInt(cat)&&vec.contains(Integer.parseInt(cat))){
					break;
				}
				System.out.println("Illegal input, please try again.");
				cat=in.nextLine();
			}
			if(calc.checkInt(cat))
				catToWork.setFatherId(Integer.parseInt(cat));
			else if(cat.equals("~"))
				return;
			else 
				inserted=true;

		}
		BL.addNewCategory(catToWork);
		System.out.println("Category added successfully!\n");
	}
}
