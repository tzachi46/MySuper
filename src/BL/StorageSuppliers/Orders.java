package BL.StorageSuppliers;

import java.time.ZonedDateTime;
import java.util.LinkedList;

import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.OrderProduct;
import SharedClasses.Pair;
import SharedClasses.StorageSuppliers.Product;
import SharedClasses.StorageSuppliers.ProductFromSupplier;
import BL.BLManager;
import BL.StorageSuppliers.BLSupplier;
import BL.StorageSuppliers.InvBLManager;

public class Orders {
	
	private static Orders instance;
	
	/**
	 * @return singelton instance
	 */
	public static Orders getInstance(){
		if(instance==null)
			instance= new Orders();
		return instance;
	}
	
	/**
	 * constructor
	 */
	private Orders(){}
	
	/**
	 * @return get all orders ever created
	 */
	public LinkedList<Order> getAllOrders(){
		return DAL.Orders.OrderManager.getInstance().getAllOrders(BLManager.emp.getWorkAddress());
	}
	
	/**
	 * @param orderId
	 * @return returns the requested Order including all his products
	 * returns null in case no familiar order id exists in the data base.
	 */
	public Order getOrder(int orderId){//return null if not exist
		return DAL.Orders.OrderManager.getInstance().getOrder(orderId); 
	}
	
	//assumptions: order exists, supplier supply this product
	/**
	 * @param orderId
	 * @param prod - includes the quantity and the product
	 * @return true if insert succeeded, else returns false
	 */
	public boolean addProductToOrder(int orderId, OrderProduct prod){
		Order order = DAL.Orders.OrderManager.getInstance().getOrder(orderId);
		if (BLSupplier.GetBLSupplier().getProductFromSupplier(order.getSupplierId(), prod.getProductId())==null)
			return false;
		if (!order.canOrder())
			return false;
		DAL.Orders.OrderManager.getInstance().addProductToOrder(orderId,prod); 
		return true;
	}
	
	public boolean isLastDayOfOrder(Order o){
		int supplyTime= getSupplyTimeOfOrder(o);
		ZonedDateTime ArrivalDate = ZonedDateTime.parse(o.getDate()).plusDays(supplyTime);
		ZonedDateTime nowTime = ZonedDateTime.now();
		return nowTime.plusDays(1).isAfter(ArrivalDate);
	}
	
	/**
	 * @param order 
	 * also adding the order's products
	 */
	public void addNewOrder(Order order){
		DAL.Orders.OrderManager.getInstance().addNewOrder(order);
		for (OrderProduct op: order.getProducts()){
			addProductToOrder(order.getOrderNumber(),op);
		}
	}
	
	/**
	 * @param orderId
	 * @param prodId - the product's id
	 */
	public void removeProductFromOrder(int orderId, int prodId){
		DAL.Orders.OrderManager.getInstance().removeOrdersProduct(orderId,prodId);
	}
	
	/**
	 * @param orderId
	 */
	public void removeOrder(int orderId){
		DAL.Orders.OrderManager.getInstance().removeOrder(orderId); 
	}
	
	
	
	
	//check if a sooner order is already exists
	//if yes, add to that order the needed product
	//if no, create 1
	/**
	 * @return returns a list of orders which holds an order for the corresponding missing products
	 * with the best offer from the suppliers
	 */
	public LinkedList<Order> getAutomaticOrders(){
		LinkedList<Order> orders = new LinkedList<Order>();
		LinkedList<Product> missingItems= InvBLManager.GetInvBLManager().getMissingItems();		
		for(int i=0;i< missingItems.size();i++){
			int orderQuantity = getOrderQuantity(missingItems.get(i));
			if(orderQuantity>0){
				addProductToUnknownOrder(missingItems.get(i).getId(),orderQuantity,orders);
			}
		}
		for(int i=0;i<orders.size();i++){
			DAL.Orders.OrderManager.getInstance().addNewOrder(orders.get(i));
			DAL.Orders.OrderManager.getInstance().addProductsToOrder(orders.get(i));
		}
		return orders;
	}
	
	
	private void addProductToUnknownOrder(int productId,int Quantity,LinkedList<Order> orders){
		Pair<Double,ProductFromSupplier> res = BLSupplier.GetBLSupplier().getBestPrice(productId, Quantity);
		if (res.getValue()==null)
			return;
		for(int i=0;i<orders.size();i++){
			if(orders.get(i).getSupplierId()==res.getValue().getSupplierId()){
				orders.get(i).addProduct(new OrderProduct(productId,Quantity));
				return;
			}
		}
		Order order = new Order(res.getValue().getSupplierId(),0,BL.BLManager.emp.getWorkAddress());
		order.addProduct(new OrderProduct(productId,Quantity));
		orders.add(order);		
	}
	/**
	 * @param list
	 * @param toAdd
	 * adds an order to the list, replacing duplicate in case of existence
	 */
	/*
	private void addOrderToList(LinkedList<Order> list, Order toAdd){
		if (toAdd==null)
			return;
		for (int i=0;i<list.size();i++)
			if (list.get(i).getSupplierId() == toAdd.getSupplierId())
				list.remove(i);
		list.add(toAdd);
		
	}*/
	
	
	/**
	 * @param orderId
	 * @param arrivedProducts - updates the arrived products, in case of missing products,
	 * automatically creates an order for the missing products.
	 * in case of periodic order, creates the next order.
	 */
	public Order updateReceivedProducts(int orderId, LinkedList<OrderProduct> arrivedProducts){
		Order order = DAL.Orders.OrderManager.getInstance().getOrder(orderId);
		order.setDueDate(ZonedDateTime.now().toString());
		updateOrder(order);
		order.setProducts(DAL.Orders.OrderManager.getInstance().getProductOfOrder(order));
		
		for(int i=0;i< order.getProducts().size();i++){
			Product prod = InvBLManager.GetInvBLManager().getProduct(order.getProducts().get(i).getProductId());
			int currItems = prod.getWarehouseQuantity();
			for (int j=0;j<arrivedProducts.size();j++)
				if (arrivedProducts.get(j).getProductId() == prod.getId())
					currItems+= arrivedProducts.get(j).getAmount();
			
			prod.setWarehouseQuantity(currItems);
			InvBLManager.GetInvBLManager().updateProduct(prod);
		}
		if (order.getIsPeriodic()){
			order.setOpenDate(ZonedDateTime.parse(order.getDate()).plusDays(order.getPeriodic()-getSupplyTimeOfOrder(order)).toString()); 
			order.setDueDate("");
			addNewOrder(order);
			return order;
		}
		return null;
	}
	
	private int getSupplyTimeOfOrder(Order o){
		int res=0;
		LinkedList<OrderProduct> Products=o.getProducts();
		for(int i=0;i<Products.size();i++){
			int avarage=DAL.Suppliers.SupplierManager.getInstance().getAvarageSupplyTimeOfProduct(Products.get(i).getProductId());
			if(avarage>res)
				res=avarage;
		}
		return res;
	}
	
	/**
	 * @param orderId
	 * @param product
	 * updates an existing order's Product
	 */
	public void updateOrderProduct(int orderId, OrderProduct product){
		DAL.Orders.OrderManager.getInstance().updateOrderProduct(orderId,product); 
	}
	
	/**
	 * @param order
	 * updates order's information EXCEPT PRODUCTS AND ORDERID!
	 */
	public void updateOrder(Order order){
		DAL.Orders.OrderManager.getInstance().updateOrder(order);
	}
	
	

	/**
	 * @param curProdId
	 * @param orderQuantity
	 * @return the order which the new product was added to
	 * adds product + quantity to the supplier with the best offer
	 */
	/*private Order addProductToUnknownOrder(int curProdId, int orderQuantity){
			Pair<Double,ProductFromSupplier> res = BL.BLSupplier.GetBLSupplier().getBestPrice(curProdId, orderQuantity);
			if (res.getValue()==null)
				return null;
			Supplier supplier = DAL.Suppliers.SupplierManager.getInstance().getSupplier(res.getValue().getSupplierId());
			Order order = checkExistingOrderFromSupplier(supplier);
			OrderProduct newOP;
			if (order == null){ // may be false if an automatic order with this product with closer day is already exists.
				order = new Order(res.getValue().getSupplierId(), 0);
				newOP = new OrderProduct(curProdId,orderQuantity);
				order.addProduct(newOP);
				addNewOrder(order);
			}
			else {
				int orderedAmount = 0;
				for (OrderProduct p : order.getProducts()){
					if (p.getProductId() == curProdId) 
						orderedAmount = p.getAmount();
				}
				newOP = new OrderProduct(curProdId,orderQuantity+orderedAmount);
				//order.addProduct(newOP);
				if (orderedAmount == 0)
					addProductToOrder(order.getOrderNumber(), newOP);
				else updateOrderProduct(order.getOrderNumber(),newOP);
			}
			return order;
	}*/
	
	/**
	 * @param p
	 * @return the quantity of the missing products after taking care of the already ordered product in other orders.
	 */
	private int getOrderQuantity(Product p){
		int orderedAmount = DAL.Orders.OrderManager.getInstance().getAmountOfProductInOpenOrderes(p.getId(),BLManager.emp.getWorkAddress());
		int MyAmount=p.getStoreQuantity()+p.getWarehouseQuantity();
		int min=p.getWarningQuantity();
		int res = min-(MyAmount+orderedAmount); 
		if (res>0)
			return res;
		return 0;
	}

	
	
	/**
	 * @param supplier
	 * @return an automatic order of a supplier if exists
	 * returns null if no such order exists
	 *//*
	private Order checkExistingOrderFromSupplier(Supplier supplier){
		LinkedList<Order> ordersOfSupplier = DAL.Orders.OrderManager.getInstance().getOpenOrdersOfSupplier(supplier);
		for (Order order: ordersOfSupplier)
			if (order.canOrder() && !order.getIsPeriodic())
				return order;
		return null;
	}*/
}
