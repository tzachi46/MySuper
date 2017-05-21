package DAL.Suppliers;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import BL.BLManager;
import DAL.DALManager;
import SharedClasses.Pair;
import SharedClasses.StorageSuppliers.Product;
import SharedClasses.StorageSuppliers.ProductFromSupplier;

public class SupplierProductsDB {

	
	protected void initTable(){
		String sql[]=new String[2];
		sql[0] = "CREATE TABLE IF NOT EXISTS SupplierProducts(\n"
				+ "	SupplierId Integer REFERENCES Supplier(CompanyId),\n"
				+ "	ProductId Integer REFERENCES Product(ProductId),\n"
				+ "	SupplierCatalogId Integer NOT NULL UNIQUE,\n"
				+ "	AvarageDeleveryTime Integer NOT NULL,\n" //in days
				+ "CONSTRAINT PKSupplierProducts PRIMARY KEY (SupplierId,ProductId));\n";
		sql[1] = "CREATE TABLE IF NOT EXISTS ProductsPrices(\n"
				+ "	SupplierId Integer REFERENCES Supplier(CompanyId),\n"
				+ "	ProductId Integer REFERENCES Product(ProductId),\n"
				+ "	MinimumQuantity Integer CHECK(MinimumQuantity > -1),\n"
				+ "	Price REAL NOT NULL CHECK(Price > 0),\n"
				+ "CONSTRAINT PK_ProductsPrices PRIMARY KEY (SupplierId,ProductId,MinimumQuantity));\n";
		SupplierManager.executeSQLCommand(sql);
	}
	
	
	
	protected void RemoveAPriceOfProduct(int supplierId,int ProductId, int minimumQuantity){
		String sql = "DELETE FROM ProductsPrices\n"
				+ " WHERE SupplierId="+supplierId+" AND ProductId="+ProductId+" AND MinimumQuantity="+minimumQuantity+";\n";	
		SupplierManager.executeSQLCommand(sql);
	}
	
	protected void UpdatePriceOfProductFromSupplier(ProductFromSupplier product,int minimumAmount,Double price){
		String sql="UPDATE ProductsPrices \n"
                + "SET Price="+price+"\n"
                + "WHERE SupplierId= "+product.getSupplierId()+" AND"
                +" ProductId= "+product.getProduct().getId()+" AND "+"MinimumQuantity= "+minimumAmount+";";
		SupplierManager.executeSQLCommand(sql);
	}
	
	protected void InsertNewPriceForProduct(int supplierId,int ProductId, int minimumQuantity,double Price){
		String sql="insert into ProductsPrices (SupplierId, ProductId, MinimumQuantity, Price)\n"
				+"VALUES ("+supplierId+","+ProductId+","+minimumQuantity+","+Price+")";
		SupplierManager.executeSQLCommand(sql);
	}
	protected boolean addNewProduct(ProductFromSupplier product){
		String sql[]=new String[2];
		sql[0] = "INSERT INTO SupplierProducts \n"
                + "	(SupplierId, ProductId, SupplierCatalogId,AvarageDeleveryTime )\n"
                + "	values("+product.getSupplierId()+","+product.getProduct().getId()+","+product.getSupplierCatalogId() +","+product.getAvarageDeleveryTime()+");";	
		List<Pair<Integer, Double>> prices = product.getPrices();
		for (int i=0;i<prices.size();i++){
			sql[1] = "INSERT INTO ProductsPrices \n"
	                + "	(SupplierId, ProductId, MinimumQuantity, Price )\n"
	                + "	values("+product.getSupplierId()+","+product.getProduct().getId()+","+prices.get(i).getKey()+","+prices.get(i).getValue()+");";	
		}
		if(prices.size()==0)
			return SupplierManager.executeSQLCommand(sql[0]);
		return SupplierManager.executeSQLCommand(sql);
	}
	
	protected void updateProduct(ProductFromSupplier product){
		String sql[]=new String[2];
		sql[0] = "UPDATE SupplierProducts  \n"
                + "SET SupplierCatalogId ="+product.getSupplierCatalogId() +"\n"
                + "SET AvarageDeleveryTime ="+product.getAvarageDeleveryTime() +"\n" 
                + "WHERE SupplierId="+product.getSupplierId()+" AND ProductId="+product.getProduct().getId();
		List<Pair<Integer, Double>> prices = product.getPrices();
		sql[1]="";
		for (int i=0;i<prices.size();i++){
			sql[1] += "UPDATE ProductsPrices  \n"
	                + "SET Price="+prices.get(i).getValue()+"\n"
	                + "WHERE SupplierId="+product.getSupplierId()+" AND ProductId="+product.getProduct().getId()+" AND MinimumQuantity="+prices.get(i).getKey()+";\n";
		}
		SupplierManager.executeSQLCommand(sql);
	}
	
	protected void removeProductFromSupplier(int SupplierId, int ProductId){
		String[] sql= new String[2];
		sql[0] = "DELETE FROM ProductsPrices\n"
				+ " WHERE SupplierId="+SupplierId+" AND ProductId = "+ProductId+";";
		sql[1]= "DELETE FROM SupplierProducts\n"
				+ " WHERE SupplierId="+SupplierId+" AND ProductId = "+ProductId+";";
		SupplierManager.executeSQLCommand(sql);
	}
	
	protected ProductFromSupplier getProductFromSupplier(int SupplierId, int ProductId){
		String sql = "SELECT MinimumQuantity, Price \n"
				+"FROM ProductsPrices where SupplierId="+SupplierId+" AND ProductId="+ProductId;
		List<Pair<Integer, Double>> prices = new LinkedList<Pair<Integer, Double>>();
	    try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	while(rs.next())
	    		prices.add(new Pair<Integer,Double>(rs.getInt("MinimumQuantity"),rs.getDouble("Price")));
	        } catch (Exception e) {
	            //System.out.println(e.getMessage());
	            return null; 
	        }
		sql = "SELECT SupplierId, ProductId,SupplierCatalogId,AvarageDeleveryTime \n"
				+"FROM SupplierProducts where SupplierId="+SupplierId+" AND ProductId="+ProductId;
		ProductFromSupplier res=null;
	    try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	Product p= DAL.Inventory.InvDALManager.getInstance().getProduct(ProductId,BLManager.emp.getWorkAddress());
	    	res= new ProductFromSupplier(rs.getInt("SupplierId"),p,rs.getInt("SupplierCatalogId"),rs.getInt("AvarageDeleveryTime"),prices);
	        } catch (Exception e) {
	            return null; 
	        }
		return res;
	}
	
	protected ProductFromSupplier[] getAllProductFromSupplier(int SupplierId){
		String sql = "SELECT Distinct ProductId\n"
				+"FROM SupplierProducts where SupplierId="+SupplierId;
		List<Integer> ProductId = new LinkedList<Integer>();
		try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	while(rs.next())
	    		ProductId.add(rs.getInt("ProductId"));
	        }
		catch (Exception e) {
	            return null; 
	    }
		ProductFromSupplier[] res= new ProductFromSupplier[ProductId.size()];
		for(int i=0;i<ProductId.size();i++){
			res[i]=getProductFromSupplier(SupplierId,ProductId.get(i));
		}
		return res;	
	}
	
	protected int getAvarageSupplyTimeOfProduct(int ProductId){
		int sum=0,count=0;
		
		String sql = "SELECT AvarageDeleveryTime FROM SupplierProducts where ProductId is "+ProductId;
		try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	while(rs.next()){
	    		sum+=rs.getInt("AvarageDeleveryTime");
	    		count++;
	    	}
	        }
		catch (Exception e) {
	            return -1; 
	    }
		if(count==0)
			return Integer.MAX_VALUE;
		return sum/count;
	}
	
	
	protected int[] getAllCatlogId(){
		String sql = "SELECT Distinct SupplierCatalogId\n"
				+"FROM SupplierProducts;";
		List<Integer> CatalogId = new LinkedList<Integer>();
		int[] res;
		try (Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	while(rs.next())
	    		CatalogId.add(rs.getInt("SupplierCatalogId"));
	        }
		catch (Exception e) {
	            return new int[0]; 
	    }
		res= new int[CatalogId.size()];
		for(int i=0;i<CatalogId.size();i++){
			res[i]=CatalogId.get(i);
		}
		return res;	
	}
	
	protected ProductFromSupplier[] getAllSuppliersOfAProduct(int ProductID){
		String sql = "SELECT Distinct SupplierId\n"
				+"FROM SupplierProducts where ProductId="+ProductID;
		List<Integer> SupplierID = new LinkedList<Integer>();
		try ( Statement stmt  = DALManager.conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	    	while(rs.next())
	    		SupplierID.add(rs.getInt("SupplierId"));
	        }
		catch (Exception e) {
	            //System.out.println(e.getMessage());
	            return null; 
	    }
		ProductFromSupplier[] res= new ProductFromSupplier[SupplierID.size()];
		for(int i=0;i<SupplierID.size();i++){
			res[i]=getProductFromSupplier(SupplierID.get(i),ProductID);
		}
		return res;	
	}
	
	protected void removeProduct(Product p){
		String[] sql= new String[2];
		sql[0] = "DELETE FROM ProductsPrices\n"
				+ " WHERE ProductId = "+p.getId()+";";
		sql[1]= "DELETE FROM SupplierProducts\n"
				+ " WHERE ProductId = "+p.getId()+";";
		SupplierManager.executeSQLCommand(sql);
	}
	
}
