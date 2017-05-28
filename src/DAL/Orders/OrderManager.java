package DAL.Orders;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import org.sqlite.SQLiteConfig;

import DAL.DALManager;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.OrderProduct;
import SharedClasses.StorageSuppliers.Product;
import SharedClasses.StorageSuppliers.Supplier;

public class OrderManager {

	private OrderDB orderDB;
	private OrderProductDB orderProductDB;
	private static OrderManager instance;
	
	public static OrderManager getInstance(){
		if(instance==null){
			instance=new OrderManager();
		}
		return instance;
	}
	
	private OrderManager(){
		DALManager.getInstance();
		orderDB = new OrderDB();
		orderProductDB = new OrderProductDB();
		
	}
	
	public LinkedList<Order> getAllOrders(String StoreAddress){
		orderDB.initTable();
		return orderDB.getAllOrders(StoreAddress);
	}
	
	public LinkedList<Order> getUndeliverdOrders(){
		orderDB.initTable();
		return orderDB.getUndeliverdOrders();
	}
	
	public LinkedList<OrderProduct> getProductOfOrder(Order Order){
		orderProductDB.initTable();
		return orderProductDB.getProductOfOrder(Order);
	}
	
	public Order getOrder(int orderId){
		orderDB.initTable();
		Order res=orderDB.getOrder(orderId);
		if(res!=null)
			res.setProducts(this.getProductOfOrder(res));
		return res;
	}
	
	public void addProductToOrder(int orderId, OrderProduct prod){
		orderDB.initTable();
		orderProductDB.initTable();
		orderProductDB.addProductToOrder(orderId,prod);
	}
	
	public void addProductsToOrder(Order order){
		orderDB.initTable();
		orderProductDB.initTable();
		LinkedList<OrderProduct> temp = order.getProducts();
		if(temp==null)
			return;
		for(int i=0;i<temp.size();i++){
			this.addProductToOrder(order.getOrderNumber(), temp.get(i));
		}
	}
	
	public void addNewOrder(Order order){
		orderDB.initTable();
		orderDB.addNewOrder(order);
		/*LinkedList<OrderProduct> temp = order.getProducts();
		if(temp==null)
			return;
		for(int i=0;i<temp.size();i++){
			this.addProductToOrder(order.getOrderNumber(), temp.get(i));
		}*/
	}
	
	public void removeOrdersProduct(int orderId, int prodId){
		orderDB.initTable();
		orderProductDB.initTable();
		orderProductDB.removeOrdersProduct(orderId,prodId);
	}
	
	public void removeOrder(int orderId){
		orderDB.initTable();
		orderProductDB.initTable();
		orderProductDB.removeOrder(orderId);
		orderDB.removeOrder(orderId);	
	}
	
	
	public void updateOrderProduct(int orderId, OrderProduct product){
		orderDB.initTable();
		orderProductDB.initTable();
		orderProductDB.updateOrderProduct(orderId,product);
	}
	
	public void updateOrder(Order order) {
		orderDB.initTable();
		orderProductDB.initTable();
		LinkedList<OrderProduct> Products=order.getProducts();
		for(int i=0;i<Products.size();i++)
			updateOrderProduct(order.getOrderNumber(),Products.get(i));
		orderDB.updateOrder(order);
	}
	
	public void removeProduct(Product p){
		orderDB.initTable();
		orderProductDB.initTable();
		orderProductDB.removeProduct(p);
	}
	
	public LinkedList<Order> getOpenOrdersOfSupplier(Supplier supplier){
		orderDB.initTable();
		return orderDB.getOpenOrdersOfSupplier(supplier);
	}
	
	public int getAmountOfProductInOpenOrderes(int ProductId,String StoreAddress){
		orderDB.initTable();
		orderProductDB.initTable();
		return orderProductDB.getAmountOfProductInOpenOrderes(ProductId,StoreAddress);
	}
	
	
	//help functions
	protected static boolean executeSQLCommand(String[] SQL) {
		
		try (Statement stmt = DALManager.conn.createStatement()) {
			for(int i=0;i<SQL.length;i++)
				stmt.execute(SQL[i]);
			
        } catch (SQLException e) {
        	////System.out.println(e.getMessage());
            return false;
        }
		return true;
    }
	
	public static boolean executeSQLCommand(String SQL) {
		String[] sqlCommand=new String[1];
		sqlCommand[0]=SQL;
		return executeSQLCommand(sqlCommand);
    }
}
