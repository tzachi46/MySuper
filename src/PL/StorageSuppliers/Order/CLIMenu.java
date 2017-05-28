package PL.StorageSuppliers.Order;

import java.util.LinkedList;
import java.util.Scanner;

import BL.BLManager;
import BL.StorageSuppliers.BLSupplier;
import BL.StorageSuppliers.InvBLManager;
import BL.StorageSuppliers.Calculations;
import BL.StorageSuppliers.Orders;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.OrderProduct;

public class CLIMenu {


	private static CLIMenu instance;
	private Calculations calc;
	public static CLIMenu getInstance() {
		if(instance==null)
			instance=new CLIMenu();
		return instance;
	}

	private CLIMenu() {	
		calc=Calculations.GetCalculations();
	}

	public void Start(Scanner in){
		boolean EndOfPage=false;
		while(!EndOfPage){
			System.out.println("Order Menu:\n1. Get All Orders \n2. Order Arrival \n3. Update Periodic Order \n4. Insert new Periodic Order \n5. Remove periodic order\n6. Print Automatic Orders\n7. Cancel order\n~. Back to main menu. ");
			String s=in.nextLine();
			while(!cheackCLIMainInput(s)){
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			switch (s.charAt(0)){
			case ('1'):{
				getAllOrders(in);
				break;
			}
			case('2'):{
				orderArrival(in);
				break;
			}
			case('3'):{
				updatePeriodicOrder(in);
				break;
			}
			case('4'):{
				insertNewOrder(in);
				break;
			}
			case('5'):{
				removeOrder(in);
				break;
			}
			case('6'):{
				makeauto(in);
				break;
			}
			case('7'):{
				CancelOrder(in);
				break;
			}
			case('~'):{
				EndOfPage=true;
				break;
			}
			}
		}
	}
	private void makeauto(Scanner in){
		LinkedList<Order> orders=Orders.getInstance().getAutomaticOrders();
		if(orders.isEmpty()){
			System.out.println("No orders to make.");
		}else{
			System.out.println("The orders are:");
			for(Order order:orders){
				System.out.println(order);
			}
		}
		System.out.println("If you wish to return to order menu press ~");
		String s=in.nextLine();
		while(!(s.equals("~"))){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}
	}
	
	private void removeOrder(Scanner in){
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("Enter order id you wish to delete.\nIf you wish to return to the order menu press ~");
			String s=in.nextLine();
			Order order=null;
			while(!s.equals("~")){
				if(calc.checkInt(s)){
					order=Orders.getInstance().getOrder(Integer.parseInt(s));
					if(order!=null)
						if(order.getPeriodic()>0&&order.getDueDate().equals("")&&!Orders.getInstance().isLastDayOfOrder(order)&&order.getAddres().equals(BLManager.emp.getWorkAddress()))
							break;
				}
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			if(s.equals("~"))
				return;
			else{
				Orders.getInstance().removeOrder(order.getOrderNumber());
				System.out.println("Order removed successfully!");
			}
		}
	}
	private void CancelOrder(Scanner in){
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("Enter order id you wish to delete that not arrived yet.\nIf you wish to return to the order menu press ~");
			String s=in.nextLine();
			Order order=null;
			while(!s.equals("~")){
				if(calc.checkInt(s)){
					order=Orders.getInstance().getOrder(Integer.parseInt(s));
					if(order!=null)
						if(order.getDueDate().equals("")&&!Orders.getInstance().isLastDayOfOrder(order)&&order.getAddres().equals(BLManager.emp.getWorkAddress())&&!order.getISHaveTransport())
							break;
				}
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			if(s.equals("~"))
				return;
			else{
				Orders.getInstance().removeOrder(order.getOrderNumber());
				System.out.println("Order removed successfully!");
				order=Orders.getInstance().CancelOrderToNextOrder(order);
				if(order!=null)
					System.out.println(order);
			}
		}
		
	}
	
	private void insertNewOrder(Scanner in){
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("Insert order menu:\nEnter SupplierId.\nIf you wish to return to the order menu press ~ ");
			String s=in.nextLine();
			while(!s.equals("~")){
				if(checkSupplierId(s)){
					if(BLSupplier.GetBLSupplier().getDaysOfSupplier(Integer.parseInt(s)).length>0)
						break;
				}

				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			if(s.equals("~"))
				return;
			int supp=Integer.parseInt(s);
			Order order=new Order(Integer.parseInt(s));
			order.setAddres(BL.BLManager.emp.getWorkAddress());
			System.out.println("Please enter the ammount of days of the supply time of the order.\nIf you wish to return to the order menu press ~ ");
			s=in.nextLine();
			while(!s.equals("~")){
				if(calc.checkOnlyNumbers(s))
					break;
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			if(s.equals("~"))
				return;
			order.setPeriodic(Integer.parseInt(s));	
			String productID="";
			while(!endOfPage){
				System.out.println("Enter product ID to order.\nIf you finish adding press #.\nIf you wish to return to the order menu press ~ ");
				productID=in.nextLine();
				while(!productID.equals("~")&&!productID.equals("#")){
					if(calc.checkInt(productID))
						if(checkProductId(productID,supp))
							if(!checkIfIdInList(order.getProducts(),Integer.parseInt(productID)))
								break;
					System.out.println("Illegal input, please try again.");
					productID=in.nextLine();
				}
				if(productID.equals("#")){
					if(order.getProducts().size()>0)
						break;
					else{
						System.out.println("Order cannot have zero products!");
						continue;
					}
				}
				if(productID.equals("~"))
					return;
				//inv_cli.mainMenu();
				OrderProduct orderProduct=new OrderProduct(Integer.parseInt(productID));

				System.out.println("Enter Amount to order.\nIf you wish to return to the main menu press ~ ");
				String amount=in.nextLine();
				while(!(amount.equals("~"))&&!calc.checkInt(amount)){
					System.out.println("Illegal input, please try again.");
					amount=in.nextLine();
				}
				if(amount.equals("~"))
					return;
				orderProduct.setAmount(Integer.parseInt(amount));
				order.addProduct(orderProduct);
			}
			Orders.getInstance().addNewOrder(order);
			System.out.println("Order added successfully");
			System.out.println(order);
		}
	}
	public boolean checkIfIdInList(LinkedList<OrderProduct>list,int id){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getProductId()==id)
				return true;
		}
		return false;
	}
	private boolean checkSupplierId(String id){
		if(!calc.checkInt(id))
			return false;
		int temp=Integer.parseInt(id);
		if(BLSupplier.GetBLSupplier().VerifyExistingSupplierID(temp))
			return true;
		return false;
	}

	private boolean checkProductId(String id,int supp){
		if(!calc.checkInt(id))
			return false;
		int temp=Integer.parseInt(id);
		if(InvBLManager.GetInvBLManager().checkProductId(temp))
			if(BLSupplier.GetBLSupplier().getProductFromSupplier(supp, temp)!=null)
				return true;
		return false;
	}

	public void getAllOrders(Scanner in){
		LinkedList<Order> temp=Orders.getInstance().getAllOrders();
		System.out.println("All the store orders:");
		for(int i=0;i<temp.size();i++){
			/*System.out.println("----------------------------------------");
			System.out.println("Order number: "+temp.get(i).getOrderNumber()+"\nSupplier number: "+temp.get(i).getSupplierId()+
					"\nOrder date: "+temp.get(i).getDate()+"\nContact number: "+temp.get(i).getSupplierPhoneNumber());
			System.out.println("Products in order:");
			for(int j=0;j<temp.get(i).getProducts().size();j++){
				System.out.println("Product numebr: "+temp.get(i).getProducts().get(j).getProductId()+"\nProduct name:"+temp.get(i).getProducts().get(j).getProductName()
						+"\nAmount: "+temp.get(i).getProducts().get(j).getAmount()+"\nOriginal price: "+temp.get(i).getProducts().get(j).getInitialProductPrice()
						+"\nDiscount: "+(100*temp.get(i).getProducts().get(j).getCurrentProductPrice()/temp.get(i).getProducts().get(j).getInitialProductPrice()
								+"\nCurrent price "+temp.get(i).getProducts().get(j).getCurrentProductPrice()));
			}
			System.out.println("----------------------------------------");*/
			//System.out.println(temp.get(i).getProducts().get(0).getProductId());
			//System.out.println(temp.get(i).getProducts().size());
			System.out.println(temp.get(i).getOrderDetail());
		}
		System.out.println("If you wish to return to order menu press ~");
		String s=in.nextLine();
		while(!(s.equals("~"))){
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();
		}

	}
	private void orderArrival(Scanner in){
		boolean endOfPage=false; 
		while(!endOfPage){
			System.out.println("Please enter order id. If you wish to return to order menu press ~");
			String s=in.nextLine();
			while(!(s.equals("~"))){ 
				if(calc.checkInt(s)){
					Order o=Orders.getInstance().getOrder(Integer.parseInt(s));
					if((o!=null)&& o.getDueDate().equals("")&&o.getAddres().equals(BLManager.emp.getWorkAddress()))
							break;
				}
					
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			if(s.equals("~"))
				return;
			else{
				System.out.println("Order arrival manager. If you wish to go back to order menu at all time without saving please press ~.");
				Order o=Orders.getInstance().getOrder(Integer.parseInt(s)); 
				System.out.println("Order number: "+o.getOrderNumber());
				for(int i=0;i<o.getProducts().size();i++){
					System.out.println("Product number: "+o.getProducts().get(i).getProductId()+"\nProduct name: "+o.getProducts().get(i).getProductName()
							+"\nExpected ammount to arrive: "+o.getProducts().get(i).getAmount());
					System.out.println("Actual ammount to arrive: ");
					s=in.nextLine();
					while(!s.equals("~")){
						if(calc.checkInt(s))
							if(Integer.parseInt(s)>=0)
								break;
						System.out.println("Illegal input, please try again.");
						s=in.nextLine();
					}
					if(s.equals("~"))
						return;
					o.getProducts().get(i).setAmount(Integer.parseInt(s));
				}
				Order responseOrder=Orders.getInstance().updateReceivedProducts(o.getOrderNumber(),o.getProducts());
				System.out.println("Order handled succesfully!");
				if(responseOrder!=null){
					System.out.println("A new Order need to be send.");
					System.out.println(responseOrder);
				}
			}
		}
	}

	private void updatePeriodicOrder(Scanner in){
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("Please enter order id. If you wish to return to order menu press ~");
			String s=in.nextLine();
			Order o=null;
			while(!(s.equals("~"))){
				if(calc.checkInt(s)){
					o=Orders.getInstance().getOrder(Integer.parseInt(s));
					if(o!=null)
						if(o.getPeriodic()>0&&o.getDueDate().equals("")&&!Orders.getInstance().isLastDayOfOrder(o))
							break;
				}
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			if(s.equals("~"))
				return;
			else{
				int temp = Integer.parseInt(s);
				Order order=Orders.getInstance().getOrder(temp);
				updateOrder(in,order);
			}
		}
	}
	
	private void updateOrder(Scanner in,Order order){
		boolean endOfPage=false;
		while(!endOfPage){
			System.out.println("1. Add product to order.\n2. Remove product from order.\n~. Back.");
			String s1=in.nextLine();
			while(!calc.checkKeyboardMenu(s1, 1, 3) && !s1.equals("~")){
				System.out.println("Illegal input, please try again.");
				s1=in.nextLine();
			}

			switch (s1.charAt(0)){
			case('1'):{
				insertProduct(in,order);
				break;
			}
			case('2'):{
				removeProduct(in, order);
				break;
			}
			case('~'):{
				return;
			}
			}
		}
	}
	private void insertProduct(Scanner in,Order order){
		System.out.println("Please enter product id you wish to add.\nIf you wish to return to the update menu press ~");
		String s=in.nextLine();
		int tempIndex=-1;
		while(!s.equals("~")){
			if(calc.checkInt(s)){
				tempIndex=productInList(order.getProducts(),Integer.parseInt(s));
				if(tempIndex==-1){
					if(checkProductId(s,order.getSupplierId()))
						break;
					else
						System.out.println("The supplier does not supply this product.");
				}
				else
					System.out.println("This product is already in this order.");
			}
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();		
		}
		if(s.equals("~"))
			return;
		else{
			int id=(Integer.parseInt(s));
			System.out.println("Please enter the product ammount per order.\nIf you wish to return to the update menu press ~");
			s=in.nextLine();
			while(!s.equals("~"))
			{
				if(calc.checkInt(s))
					if(Integer.parseInt(s)>0)
						break;
				System.out.println("Illegal input, please try again.");
				s=in.nextLine();
			}
			int ammount=(Integer.parseInt(s));
			OrderProduct op=new OrderProduct(id,ammount);
			order.addProduct(op);
			Orders.getInstance().addProductToOrder(order.getOrderNumber(),op);
		}
	}
	private void removeProduct(Scanner in, Order order){
		System.out.println("Please enter product id you wish to remove.\nIf you wish to return to the update menu press ~");
		String s=in.nextLine();
		int tempIndex=-1;
		while(!s.equals("~")){
			if(calc.checkInt(s)){
				tempIndex=productInList(order.getProducts(),Integer.parseInt(s));
				if(tempIndex>-1)
					break;
			}
			System.out.println("Illegal input, please try again.");
			s=in.nextLine();		
		}
		if(s.equals("~"))
			return;
		else{
			int id=order.getProducts().get(tempIndex).getProductId();
			order.getProducts().remove(tempIndex);
			//BL.Orders.getInstance().updateOrder(order);
			Orders.getInstance().removeProductFromOrder(order.getOrderNumber(),id);
			System.out.println("Product removed successfully!");
		}

	}
	private int productInList(LinkedList<OrderProduct>list,int id){
		for(int i=0;i<list.size();i++)
			if(list.get(i).getProductId()==id)
				return i;
		return -1;
	}

	private boolean cheackCLIMainInput(String s){
		if(s.length()!=1)
			return false;
		if(s.charAt(0)=='~')
			return true;
		if(s.charAt(0)<'1'||s.charAt(0)>'7')
			return false;
		return true;
	}
}
