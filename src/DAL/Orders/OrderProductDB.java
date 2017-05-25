package DAL.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import DAL.DALManager;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.OrderProduct;
import SharedClasses.StorageSuppliers.Product;

public class OrderProductDB {
	
	protected void initTable(){
		String sql = "CREATE TABLE IF NOT EXISTS OrderProduct (\n"
				+ "	OrderNumber Integer REFERENCES Orders(OrderNumber),\n"
				+ "	ProductId Integer REFERENCES Product(Barcode),\n"
				+ "	Amount Integer NOT NULL,\n"
				+ "CONSTRAINT PK_ProductsPrices PRIMARY KEY (OrderNumber,ProductId));\n";
		OrderManager.executeSQLCommand(sql);
	}
	
	protected void addProductToOrder(int orderId, OrderProduct prod){
		String sql = "INSERT INTO OrderProduct \n"
                + "	(OrderNumber,ProductId , Amount)\n"
                + "	values("+orderId+","+prod.getProductId()+","+prod.getAmount()+");";	
		OrderManager.executeSQLCommand(sql);
	}

	
	protected LinkedList<OrderProduct> getProductOfOrder(Order Order){
		LinkedList<OrderProduct> res= new LinkedList<OrderProduct>();
		String sql="select ProductId,Amount from OrderProduct where OrderNumber is "+Order.getOrderNumber();
		try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 res.add(new OrderProduct(rs.getInt("ProductId"),rs.getInt("Amount")));
			 }
		 }
		 catch (SQLException e) {
			////System.out.println(e.getMessage());
		 }
		return res;
	}
	
	protected int getAmountOfProductInOpenOrderes(int ProductId, String storeAddress){
		int count =0;
		String sql="select Amount from OrderProduct join Orders ON OrderProduct.OrderNumber=Orders.OrderNumber\n"
		+"where Orders.DueDate is '' AND Orders.StoreAddress is '"+storeAddress+"' AND ProductId is "+ProductId;
		try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 count+=rs.getInt("Amount");
			 }
		 }
		 catch (SQLException e) {
			 ////System.out.println(e.getMessage());
		 }
		return count;
	}
	
	protected void removeOrdersProduct(int orderId, int prodId){
		String sql = "DELETE FROM OrderProduct\n"
				+ " WHERE OrderNumber="+orderId+" AND ProductId="+prodId+";";	
		OrderManager.executeSQLCommand(sql);
	}
	
	protected void removeOrder(int orderId){
		String sql = "DELETE FROM OrderProduct\n"
				+ " WHERE OrderNumber="+orderId+";";	
		OrderManager.executeSQLCommand(sql);
	}
	
	public void removeProduct(Product p){
		String sql = "DELETE FROM OrderProduct\n"
				+ " WHERE ProductId="+p.getId()+";";	
		OrderManager.executeSQLCommand(sql);
	}
	
	protected void updateOrderProduct(int orderId, OrderProduct product){
		String sql = "UPDATE OrderProduct \n"
                + "SET Amount="+product.getAmount()+"\n"
                + "WHERE OrderNumber="+orderId+" AND ProductId= "+product.getProductId()+";"; 
		OrderManager.executeSQLCommand(sql);
	}
}
