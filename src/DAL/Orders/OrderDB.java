package DAL.Orders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import DAL.DALManager;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.Supplier;


public class OrderDB {

	
	protected void initTable(){
		String sql = "CREATE TABLE IF NOT EXISTS Orders (\n"
				+ "	OrderNumber Integer PRIMARY KEY AUTOINCREMENT,\n"
				+ "	SupplierId Integer REFERENCES Supplier(CompanyId),\n"
				+ "	OpenDate varchar(30) NOT NULL,\n"
				+ "	DueDate varchar(30) DEFAULT '',\n"
				+ "	StoreAddress varchar(30) NOT NULL,\n"
				+ "	HaveTransport Integer NOT NULL,\n"
				+ "	isPeriodic Integer NOT NULL);\n";
		OrderManager.executeSQLCommand(sql);
	}
	
	protected LinkedList<Order> getAllOrders(){
		LinkedList<Order> res= new LinkedList<Order>();
		String sql="select OrderNumber,SupplierId,OpenDate,isPeriodic,DueDate,StoreAddress,HaveTransport from Orders";
		try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 res.add(new Order(rs.getInt("OrderNumber"),rs.getInt("SupplierId"),rs.getString("OpenDate"),rs.getInt("isPeriodic"),rs.getString("DueDate"),rs.getString("StoreAddress"),rs.getInt("HaveTransport")));
			 }
			 for(int i=0;i<res.size();i++){
				 res.get(i).setProducts(OrderManager.getInstance().getProductOfOrder(res.get(i)));
			 }
		 }
		 catch (SQLException e) {
			 ////System.out.println(e.getMessage());
		 }
		return res;
	}
	
	protected Order getOrder(int orderId){
		Order res=null;
		String sql="select OrderNumber,SupplierId,OpenDate,isPeriodic,DueDate,StoreAddress,HaveTransport from Orders where OrderNumber ="+orderId;
		try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			res= new Order(rs.getInt("OrderNumber"),rs.getInt("SupplierId"),rs.getString("OpenDate"), rs.getInt("isPeriodic"),rs.getString("DueDate"),rs.getString("StoreAddress"),rs.getInt("HaveTransport"));
			res.setProducts(OrderManager.getInstance().getProductOfOrder(res));
		 }
		 catch (SQLException e) {
			 ////System.out.println(e.getMessage());
		 }
		return res;
	}
	
	
	protected void addNewOrder(Order order){	
		
		String sql = "INSERT INTO Orders \n"
                + "	(SupplierId ,OpenDate,isPeriodic,DueDate,HaveTransport,StoreAddress)\n"
                + "	values("+order.getSupplierId()+" ,'"
                			+order.getDate()+"',"+order.getPeriodic()+",'"+order.getDueDate()+"',"+order.getHaveTransport()+", '"+order.getAddres()+"');";	
		try (Statement stmt = DALManager.conn.createStatement()) {
				stmt.execute(sql);
			
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        	return;
        }
		sql="select last_insert_rowid() as a;";
		try (    Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 rs.next();
			 int a=rs.getInt("a");
			 order.setOrderNumber(a);
		 }
		 catch (SQLException e) {
			////System.out.println(e.getMessage());
		 }
		 
	}
	
	protected void removeOrder(int orderId){
		String sql = "DELETE FROM Orders\n"
				+ " WHERE OrderNumber= "+orderId+";";	
		OrderManager.executeSQLCommand(sql);
	}
	
	protected void updateOrder(Order order) {
		String sql = "UPDATE Orders \n"
                + "SET SupplierId="+order.getSupplierId()+",\n"
                + "OpenDate='"+order.getDate()+"',\n"
                + "isPeriodic="+order.getPeriodic()+",\n"
                + "DueDate='"+order.getDueDate()+"'\n"
                + "HaveTransport='"+order.getHaveTransport()+"'\n"
                + "StoreAddress='"+order.getAddres()+"'\n"
                + "WHERE OrderNumber="+order.getOrderNumber()+";"; 
		OrderManager.executeSQLCommand(sql);
	}
	
	protected LinkedList<Order> getOpenOrdersOfSupplier(Supplier supplier){
		LinkedList<Order> res=new LinkedList<Order>();
		String sql="select OrderNumber,SupplierId,OpenDate,isPeriodic,HaveTransport,StoreAddress from Orders where DueDate='' AND SupplierId = "+supplier.getCompanyId();
		try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 res.add(new Order(rs.getInt("OrderNumber"),rs.getInt("SupplierId"),rs.getString("OpenDate"),rs.getInt("isPeriodic"),"",rs.getString("StoreAddress"),rs.getInt("HaveTransport")));
			 }
			 for(int i=0;i<res.size();i++){
				 res.get(i).setProducts(OrderManager.getInstance().getProductOfOrder(res.get(i)));
			 }
		 }
		 catch (SQLException e) {
			////System.out.println(e.getMessage());
		 }
		return res;
	}
	
	protected LinkedList<Order> getUndeliverdOrders()
	{
		LinkedList<Order> res=new LinkedList<Order>();
		String sql="select OrderNumber,SupplierId,OpenDate,isPeriodic,HaveTransport,StoreAddress from Orders where DueDate=''";
		try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
			 while(rs.next()){
				 res.add(new Order(rs.getInt("OrderNumber"),rs.getInt("SupplierId"),rs.getString("OpenDate"),rs.getInt("isPeriodic"),"",rs.getString("StoreAddress"),rs.getInt("HaveTransport")));
			 }
			 for(int i=0;i<res.size();i++){
				 res.get(i).setProducts(OrderManager.getInstance().getProductOfOrder(res.get(i)));
			 }
		 }
		 catch (SQLException e) {
			////System.out.println(e.getMessage());
		 }
		return res;
	}
}
