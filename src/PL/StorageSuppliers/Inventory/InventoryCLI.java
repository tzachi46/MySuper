package PL.StorageSuppliers.Inventory;
import BL.StorageSuppliers.Calculations;
/**
 * Inventory cli is responsible for the main menu and some calculation functions.
 */
import BL.StorageSuppliers.InvBLManager;
import SharedClasses.Quartet;
import SharedClasses.StorageSuppliers.Category;
import SharedClasses.StorageSuppliers.Product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class InventoryCLI {

	private static InventoryCLI instance;
	private InvBLManager BL;
	private CategoryCLI cat_cli;
	private UpdateCLI update_cli;
	private InsertCLI insert_cli;
	private Calculations calc;
	/**
	 * Constructor
	 */
	private InventoryCLI(){
		BL=InvBLManager.GetInvBLManager();
		cat_cli=CategoryCLI.GetCategoryCLI();
		update_cli=UpdateCLI.GetUpdateCLI();
		insert_cli=InsertCLI.GetInsertCLI();
		calc=Calculations.GetCalculations();
	}
	/**
	 * 
	 * @return Inventory cli instance
	 */
	public static InventoryCLI GetInvCLIManager(){
		if(instance==null)
			instance= new InventoryCLI();
		return instance;
	}
	/**
	 * The main menu
	 */
	public void Start(){
		boolean endOfPage=false;
		while(!endOfPage){
			Scanner in=new Scanner(System.in);
			System.out.println("Inventory Menu:\n1. Get products in inventory. \n2. Get products that are running out."
					+ "\n3. Insert new product\n4. Update Product.\n5. Get defective products.\n6. Remove product."
					+ "\n7. Get product details.\n8. Category manager.\n~. Back to main menu. ");
			String s=in.nextLine();
			while(!calc.checkKeyboardMenu(s, 1, 8)&&!s.equals("~")){
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			switch (s.charAt(0)){
				case ('1'):{
					getProductsMenu(in);
					break;
				}
				case('2'):{
					runningOutProducts(in);
					break;
				}
				case('3'):{
					insert_cli.insertProductMenu(in);
					break;
				}
				case('4'):{
					update_cli.updateProductMenu(in);
					break;
				}
				case('5'):{
					defectiveProducts(in);
					break;
				}
				case('6'):{
					removeProduct(in);
					break;
				}
				case('7'):{
					getProductMenu(in);
					break;
				}
				case('8'):{
					cat_cli.categoryManager(in);
					break;
				}
				case('~'):{
					endOfPage=true;
					break;
				}
			}
		}
	}
	/**
	 * Removes a product
	 */
	private void removeProduct(Scanner in){
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("Please enter the product id you wish to remove.\nIf you wish to return to the main menu press ~");
			String pro=in.nextLine();
			while(!pro.equals("~"))
			{
				if(!calc.checkInt(pro))
					System.out.println("invalid input, try again");
				else if(!BL.checkProductId(Integer.parseInt(pro)))		
					System.out.println("This product does not exists, please enter product id again.\nTo go back to category manager menu press '~'.");
				else if(BL.getStoresOfferingProduct(Integer.parseInt(pro)).size()>0)
					System.out.println("This product already exists in some stores, please remove it from the stores.\nTo go back to category manager menu press '~'.");
				else break;
				pro=in.nextLine();
			}
			if(pro.equals("~"))
				return;
				//mainMenu();
			BL.removeProdut(BL.getProduct((Integer.parseInt(pro))));
			System.out.println("Product removed successfully.\n");
			//removeProduct();
		}
	}
	/**
	 * Checks if the start date is not bigger than the finish date
	 * @param start
	 * @param finish
	 * @return
	 */
	boolean checkTodayDate(String start,String finish){
		if(start==null||finish==null)
			return false;
		int today=getTodayDateAsInt();
		int startInt=GetDayAsInt(start);
		int finishInt=GetDayAsInt(finish);
		if(today>=startInt && today<=finishInt)
			return true;
		return false;
	}
	/**
	 * return true if start < finish
	 * @param start
	 * @param finish
	 * @return
	 */
	boolean ComperDates(String start,String finish){
		int startInt=GetDayAsInt(start);
		int finishInt=GetDayAsInt(finish);
		if(startInt<=finishInt)
			return true;
		return false;
	}
	/**
	 * @return the day in int format
	 */
	private int getTodayDateAsInt(){
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date date = new Date();
		String today=dateFormat.format(date).toString();
		return GetDayAsInt(today);
	}
	/**
	 * reverses the date in order to get it's decimal representation 
	 * @param s
	 * @return date as int
	 */
	private int GetDayAsInt(String s){
		String[] parts = s.split("\\.");
		String ans="";
		for(int i=parts.length-1;i>=0;i--)
			ans+=parts[i];
		return Integer.parseInt(ans);
	}
	/**
	 * Shows all the products in the inventory
	 */
	private void getProductsMenu(Scanner in){
		System.out.println("All the products in the store and the warehouse:\n");
		Product[] p=BL.getProductInInventory();
		for(int i=0;i<p.length;i++)
		{
			System.out.println("Product id: "+p[i].getId()+"\nProduct name: "+p[i].getName()
					+"\nLocation in store: "+p[i].getStoreLoc()+"\nLocation in warehouse: "
					+p[i].getWareLoc()+"\nTotal quantity in inventory: "+(p[i].getStoreQuantity()+p[i].getWarehouseQuantity())
					+"\nQuantity in store: "+p[i].getStoreQuantity()+"\nQuantity in warehouse: "+p[i].getWarehouseQuantity()+"\n"
					+ "----------------------------------------");
		}
		System.out.println("If you wish to return to the main menu press ~");
		String s=in.nextLine();
		while(!(s.equals("~"))){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}
		//mainMenu();
	}
	/**
	 * Shows all the running out products
	 */
	private void runningOutProducts(Scanner in){
		LinkedList<Product> temp=BL.getMissingItems();
		if(temp!=null){
			System.out.println("Running out products report:\n"
					+ "----------------------------------------");
			for(int i=0;i<temp.size();i++){
				if(temp.get(i).getWarningQuantity()-temp.get(i).getStoreQuantity()-temp.get(i).getWarehouseQuantity()>2000000000)
					System.out.println("Product id: "+temp.get(i).getId()+"\nProduct name: "+temp.get(i).getName()
							+"\nTotal amount in inventory: "+(temp.get(i).getStoreQuantity()+temp.get(i).getWarehouseQuantity())
							+"\nTotal missing in inventory: No supplier to supply this item!"
							+"\nAverage sell per day: "+temp.get(i).getSalesPerDay()+"\n"
							+  "----------------------------------------");
				else
					System.out.println("Product id: "+temp.get(i).getId()+"\nProduct name: "+temp.get(i).getName()
							+"\nTotal amount in inventory: "+(temp.get(i).getStoreQuantity()+temp.get(i).getWarehouseQuantity())
							+"\nTotal missing in inventory: "+(temp.get(i).getWarningQuantity()-temp.get(i).getStoreQuantity()-temp.get(i).getWarehouseQuantity())
							+"\nAverage sell per day: "+temp.get(i).getSalesPerDay()+"\n"
							+  "----------------------------------------");
			}
		}
		System.out.println("If you wish to return to the main menu press ~");
		String s=in.nextLine();
		while(!(s.length()==1&&s.charAt(0)=='~')){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}
		//mainMenu();
	}
	/**
	 * Shows all defective or expired products
	 */
	private void defectiveProducts(Scanner in){
		LinkedList<Quartet<Integer,String,Integer,Integer>> temp=BL.getDefectItems();
		if(temp!=null){
			System.out.println("Defective or expired products: ");
			for(int i=0;i<temp.size();i++)
				System.out.println("Product id: "+temp.get(i).getKey()+"\nProduct name: "+temp.get(i).getValue1()
						+"\nAmount of defective or expired products in store: "+temp.get(i).getValue2()
						+"\nAmount of defective or expired products in warehouse: "+temp.get(i).getValue3());
		}
		System.out.println("If you wish to return to the main menu press ~");
		String s=in.nextLine();
		while(!(s.length()==1&&s.charAt(0)=='~')){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}
		//mainMenu();
	}
	/**
	 * 
	 * @param id
	 * @return checks if a product id is in inventory
	 */
	boolean checkProductId(String id){
		if(!calc.checkInt(id))
			return false;
		int temp=Integer.parseInt(id);
		if(BL.checkProductId(temp))
			return true;
		return false;
	}
	/**
	 * Shows all the products in the inventory
	 */
	private void getProductMenu(Scanner in){
		System.out.println("Get product details menu:\nEnter product ID.\nIf you wish to return to the main Menu press ~ ");
		String s=in.nextLine();
		while(!s.equals("~")&&!checkProductId(s)){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}
		if(s.equals("~"))
			return;
			//mainMenu();
		else{
			int temp = Integer.parseInt(s);
			Product p=BL.getProduct(temp);
			System.out.println("Product details:\nID: "+p.getId()+"\nName: "+p.getName()+"\nManufacturer: "+p.getManufacturer()+
					"\nStore quantity: "+p.getStoreQuantity()+"\nWarehouse quantity:"+p.getWarehouseQuantity()+"\nDiscount from supplier: "+p.getDiscountFromSupp()
					+"\nOriginal selling price to customer: "+p.getPriceToSell()+"\nDiscounts to customer: "+p.getDiscountToCus()+"\nDiscount from categories: "+getCategoryDiscount(p)+"\nSelling price after all discounts: "
					+((double)(p.getPriceToSell()-(p.getPriceToSell()*(getCategoryDiscount(p)+p.getDiscountToCus())/100)))
					+"\nCustomer discount start date: "+p.getDiscountStartTime()+"\nCustomer discount finish date: "+p.getDiscountFinishTime()+
					"\nWarehouse location: "+p.getWareLoc()+"\nStore location: "+p.getStoreLoc()+"\nAmmount of expired/defective in store: "+p.getStoreDefective()
					+"\nAmmount of expired/defective in warehouse: "+p.getWareDefective()+"\nCategory: "+BL.getCategoryById(p.getCategory()).getName()+"\nMinimum recommanded quantity "
					+ "in inventory of the product: "+p.getWarningQuantity()+"\nSales per day: "+p.getSalesPerDay());
			System.out.println("\nIf you wish to return to the main menu press ~");
			String s2=in.nextLine();
			while(!s2.equals("~")){
				System.out.println("Illegal input, please try again.");
				s2=in.nextLine();
			}
			//mainMenu();
		}
	}
	/**
	 * calculates the discount a category and it's parent categories have.
	 * @param p
	 * @return discount for a category
	 */
	public int getCategoryDiscount(Product p){
		int sum=0;
		int id=p.getCategory();
		//int parent=BL.getCategoryById(child).getFatherId();
		Category[]c=BL.getAllCategories();
		LinkedList<Category>c2=new LinkedList<>();
		for(int i=0;i<c.length;i++)
			c2.add(c[i]);
		while(id!=-1)
		{
			boolean found=false;
			for(int k=0;k<c2.size()&&!found;k++){
				if(id==c2.get(k).getId()){

					if((c2.get(k).getDiscountStartTime()!=null&&c2.get(k).getDiscountFinishTime()!=null)
							&&(!c2.get(k).getDiscountStartTime().equals("null")&&!c2.get(k).getDiscountFinishTime().equals("null")))
						if(checkTodayDate(c2.get(k).getDiscountStartTime(),c2.get(k).getDiscountFinishTime()))
							sum=sum+c2.get(k).getDiscount();
					id=c2.get(k).getFatherId();
					c2.remove(k);
					found=true;
				}
			}
		}
		return sum;
	}


}

