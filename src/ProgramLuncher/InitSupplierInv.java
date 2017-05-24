package ProgramLuncher;

import java.sql.SQLException;
import java.sql.Statement;

import BL.StorageSuppliers.BLSupplier;
import BL.StorageSuppliers.InvBLManager;
import BL.StorageSuppliers.Orders;
import DAL.DALManager;
import DAL.Inventory.CategoryDB;
import DAL.Inventory.ProductDB;
import DAL.Inventory.ProductSellingCostDB;
import DAL.Orders.OrderManager;
import DAL.Suppliers.SupplierManager;
import DAL.Suppliers.SupplierProductsDB;
import PL.StorageSuppliers.SupplierPL.PricesMenuCLI;
import SharedClasses.StorageSuppliers.Category;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.OrderProduct;
import SharedClasses.StorageSuppliers.Product;
import SharedClasses.StorageSuppliers.ProductFromSupplier;
import SharedClasses.StorageSuppliers.Supplier;
import SharedClasses.StorageSuppliers.SupplierContact;

public class InitSupplierInv {
	public static void init() throws Exception 
	{
		DAL.DALManager.getInstance();
		InitProducts();
		InitSupplier();
		InitSupplierDays();
		InitSupplierContact();
		InitProductPrices();
		initOrder();
		
	}
	
	private static void InitProducts(){
		String sql = "CREATE TABLE IF NOT EXISTS Product (\n"
                + "	Barcode integer PRIMARY KEY,\n"
                + "	Name VARCHAR(30) NOT NULL,\n"
                + "	Manufacturer VARCHAR(30) NOT NULL,\n"
                + " Category integer DEFAULT 0,\n"
                + " Weight integer DEFAULT 0,\n"
                + "FOREIGN KEY(Category) REFERENCES Category(CategoryID) ON UPDATE CASCADE ON DELETE CASCADE"
                + ");";
		String sql2 = "CREATE TABLE IF NOT EXISTS ProductSellingCost (\n"
                + "	Barcode integer ,\n"
                + "	Cost REAL NOT NULL,\n"
                + "	discount integer DEFAULT 0,\n"
                + "	StartDiscountDate VARCHAR(30) DEFAULT NULL,\n"
                + "	EndDiscountDate VARCHAR(30) DEFAULT NULL,\n"
                + "	FOREIGN KEY(Barcode) REFERENCES Product(Barcode) ON DELETE CASCADE ON UPDATE CASCADE\n"
                + ");";
		String sql3 = "CREATE TABLE IF NOT EXISTS ProductInStore (\n"
                + "	Barcode integer PRIMARY KEY,\n"
                + "	StoreAdd TEXT NOT NULL,\n"
                + "	QuantityShelf integer NOT NULL,\n"
                + "	QuantityWarehouse integer NOT NULL,\n"
                + "	PlaceInWarehouse VARCHAR(30) NOT NULL,\n"
                + "	PlaceInStore VARCHAR(30) NOT NULL,\n"
                + "	StoreDefective integer DEFAULT 0,\n"
                + " WareDefective integer DEFAULT 0,\n"
                + " SalesPerDay integer DEFAULT 0,\n"
                + "FOREIGN KEY(Barcode) REFERENCES Product(Barcode) ON UPDATE CASCADE ON DELETE CASCADE"
                + "FOREIGN KEY(StoreAdd) REFERENCES Sites(ADDRESS) ON UPDATE CASCADE ON DELETE CASCADE"
                +"CONSTRAINT PKProductInStore PRIMARY KEY (Barcode, StoreAdd)\n"
                + ");";
		String sql4 = "CREATE TABLE IF NOT EXISTS Category (\n"
				+ "	CategoryID integer PRIMARY KEY,\n"
				+ "	FatherCategoryID integer DEFAULT Null,\n"
				+ "	Name VARCHAR(30) NOT NULL,\n"
				+ "	Discount integer DEFAULT 0 NOT NULL,\n"
				+ "	StartDiscountDate VARCHAR(30) DEFAULT NULL,\n"
				+ "	EndDiscountDate VARCHAR(30) DEFAULT NULL,\n"
				+ "	FOREIGN KEY(FatherCategoryID) REFERENCES Category(CategoryID)\n"
				+ ");";
		String storeCat="INSERT INTO Category(CategoryID,Name,FatherCategoryID,Discount,StartDiscountDate,EndDiscountDate)"
						+" SELECT 0, 'ALL STORE',-1,0,NULL,NULL"
						+" WHERE NOT EXISTS(SELECT CategoryID FROM Category WHERE CategoryID = 0);";
		
		SupplierManager.executeSQLCommand(sql4);
		SupplierManager.executeSQLCommand(storeCat);
		
		SupplierManager.executeSQLCommand(sql);
		SupplierManager.executeSQLCommand(sql2);
		SupplierManager.executeSQLCommand(sql3);
		
		
		
		
		Category cat=new Category(1);
		cat.setFatherId(0);
		cat.setName("CANDIES");
		CategoryDB.getCategoryDB().addNewCategory(cat);
		
		Category cat2=new Category(2);
		cat2.setFatherId(1);
		cat2.setName("M&M CANDIES");
		cat2.setDiscount(10);
		cat2.setDiscountStartTime("10.10.2016");
		cat2.setDiscountFinishTime("10.10.2017");
		CategoryDB.getCategoryDB().addNewCategory(cat2);
		
		Category cat3=new Category(3);
		cat3.setFatherId(1);
		cat3.setName("WAFFLES");
		CategoryDB.getCategoryDB().addNewCategory(cat3);
		
		Category cat4=new Category(4);
		cat4.setFatherId(0);
		cat4.setName("SOFT DRINKS");
		CategoryDB.getCategoryDB().addNewCategory(cat4);
		
		Category cat5=new Category(5);
		cat5.setFatherId(0);
		cat5.setName("Milk");
		CategoryDB.getCategoryDB().addNewCategory(cat5);
		
		Category cat6=new Category(6);
		cat6.setFatherId(5);
		cat6.setName("Milk 3%");
		CategoryDB.getCategoryDB().addNewCategory(cat6);
		
		Category cat7=new Category(7);
		cat7.setFatherId(0);
		cat7.setName("Meat");
		CategoryDB.getCategoryDB().addNewCategory(cat7);
		
		Category cat8=new Category(8);
		cat7.setFatherId(7);
		cat7.setName("CHICKEN");
		CategoryDB.getCategoryDB().addNewCategory(cat8);
		
		Category cat9=new Category(9);
		cat9.setFatherId(0);
		cat9.setName("BEEF");
		CategoryDB.getCategoryDB().addNewCategory(cat9);
		
		
		Product p1=new Product(1, "cola","cola",20,30,20,2);
		p1.setCategory(4);
		p1.setSalesPerDay(5);
		p1.setWareDefective(3);
		p1.setStoreDefective(3);
		p1.setStoreLoc("2");
		p1.setWareLoc("3");
		
		ProductDB.getProductDB().addNewProduct(p1, "Base");
		
		
		Product p2=new Product(2, "sprite","cola",3,5,21,2);
		p2.setCategory(4);
		p2.setSalesPerDay(5);
		p2.setWareDefective(0);
		p2.setStoreDefective(1);
		p2.setStoreLoc("2");
		p2.setWareLoc("3");
		
		ProductDB.getProductDB().addNewProduct(p2, "Base");
		
		Product p3=new Product(3, "soda","osem",2,0,21,1);
		p3.setCategory(4);
		p3.setSalesPerDay(10);
		p3.setWareDefective(0);
		p3.setStoreDefective(0);
		p3.setStoreLoc("1");
		p3.setWareLoc("4");
		
		ProductDB.getProductDB().addNewProduct(p3, "ofir street");
		
		Product p4=new Product(4, "M&M blue","M&M",20,20,20,2);
		p4.setCategory(2);
		p4.setSalesPerDay(5);
		p4.setWareDefective(0);
		p4.setStoreDefective(0);
		p4.setStoreLoc("ROW B");
		p4.setWareLoc("SHELF 4");
		
		ProductDB.getProductDB().addNewProduct(p4, "ofir street");
		
		Product p5=new Product(5, "M&M red","M&M",20,10,25,2);
		p5.setCategory(2);
		p5.setSalesPerDay(5);
		p5.setWareDefective(0);
		p5.setStoreDefective(0);
		p5.setStoreLoc("ROW B");
		p5.setWareLoc("SHELF 4");
		
		ProductDB.getProductDB().addNewProduct(p5, "ofir street");
		
		Product p6=new Product(6, "Milk 3% Tnuva","Tnuva",100,1000,5,6);
		p6.setCategory(6);
		p6.setSalesPerDay(300);
		p6.setWareDefective(0);
		p6.setStoreDefective(0);
		p6.setStoreLoc("ROW T");
		p6.setWareLoc("SHELF A");
		
		ProductDB.getProductDB().addNewProduct(p6, "dov street");
		
		
		Product p7=new Product(7, "Chicken breast","Oftov",29,200,50,1);
		p7.setCategory(8);
		p7.setSalesPerDay(30);
		p7.setWareDefective(0);
		p7.setStoreDefective(0);
		p7.setStoreLoc("ROW A");
		p7.setWareLoc("SHELF 4");
		
		ProductDB.getProductDB().addNewProduct(p7, "dov street");
		
		Product p8=new Product(8, "HAMBURGER","ADOM ADOM",79,99,10,1);
		p8.setCategory(9);
		p8.setSalesPerDay(40);
		p8.setWareDefective(7);
		p8.setStoreDefective(5);
		p8.setStoreLoc("ROW I");
		p8.setWareLoc("SHELF G");
		
		ProductDB.getProductDB().addNewProduct(p8, "dov street");
		
		
		
		
		
		
	}
	
	private static void InitSupplier() throws Exception{
		String sql[]=new String[5];
		sql[0] = "CREATE TABLE IF NOT EXISTS TermsOfPayment (\n"
				+ "	Id Integer PRIMARY KEY,\n"
				+ "	text varchar(30) unique);\n";
		sql[1] ="INSERT OR IGNORE INTO TermsOfPayment \n"
                + "	(ID, text)\n"
                + "	values(1,'Cash');\n";
		sql[2] ="INSERT OR IGNORE INTO TermsOfPayment \n"
                + "	(ID, text)\n"
                + "	values(2,'Credit Card');\n";
		sql[3] ="INSERT OR IGNORE INTO TermsOfPayment \n"
                + "	(ID, text)\n"
                + "	values(3,'Checks');\n";
		sql[4] = "CREATE TABLE IF NOT EXISTS Supplier (\n"
                + "	CompanyId Integer PRIMARY KEY,\n"
                + "	Name varchar(30) NOT NULL,\n"
                + "	BankAccountNumber Integer NOT NULL,\n"
                + "	BankBranchId Integer NOT NULL,\n"
                + "	TermsOfPayment varchar(30) REFERENCES TermsOfPayment(text) NOT NULL,\n"
                + "	Delivery Integer not null CHECK(Delivery is 0 OR Delivery is 1 ),\n"
                + "	Address varchar(30) not null \n"
                + ");";
		SupplierManager.executeSQLCommand(sql);
		SupplierManager.getInstance().addNewSupplier(new Supplier(1,"zahi",212,321,"Cash",1,"dgania"));
		SupplierManager.getInstance().addNewSupplier(new Supplier(2,"shay",21,31,"Credit Card",0,"shauli"));
		SupplierManager.getInstance().addNewSupplier(new Supplier(3,"aviad",213,321,"Checks",0,"dsa"));
		SupplierManager.getInstance().addNewSupplier(new Supplier(4,"niv",213,321,"Cash",1,"rager"));
		SupplierManager.getInstance().addNewSupplier(new Supplier(5,"abo brothers",1023882,17882,"Checks",1,"Dgania 99 Ashdod"));
	}
	
	private static void InitSupplierDays(){
		String sql = "CREATE TABLE IF NOT EXISTS SupplierDays (\n"
                + "	CompanyId Integer REFERENCES Suppliers(CompanyId) ,\n"
                + "	Day Integer CHECK(Day > 0 AND Day < 8 ) NOT NULL,\n"
                +"CONSTRAINT PKSupplierDays PRIMARY KEY (CompanyId, Day)\n"
                + ");";
		SupplierManager.executeSQLCommand(sql);
		SupplierManager.getInstance().addDayOfSupplier(1, 1);
		SupplierManager.getInstance().addDayOfSupplier(1, 3);
		SupplierManager.getInstance().addDayOfSupplier(5, 5);
	}
	
	private static void InitSupplierContact(){
		String sql = "CREATE TABLE IF NOT EXISTS SuplierContactList\n"
				+ "( ID INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                + "	CompanyId Integer REFERENCES Suppliers(CompanyId) ,\n"
                + "	PhoneNumber varchar(10),\n"
                + "	Name varchar(30)NOT NULL,\n"
                + "	Email varchar(50));";
		SupplierManager.executeSQLCommand(sql);
		SupplierManager.getInstance().addSupplierContact(new SupplierContact(1,"0525378040","zahi","tzachi@gmail.com"));
		SupplierManager.getInstance().addSupplierContact(new SupplierContact(2,"0502332281","shay","shay@gmail.com"));
		SupplierManager.getInstance().addSupplierContact(new SupplierContact(3,"0513430193","aviad","aviad@gmail.com"));
		SupplierManager.getInstance().addSupplierContact(new SupplierContact(4,"0545382939","niv","niv@gmail.com"));
		SupplierManager.getInstance().addSupplierContact(new SupplierContact(5,"0526633454","Afek","Afekabo@abo.com"));

	}
	private static void InitProductPrices(){
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
		


		Product p=InvBLManager.GetInvBLManager().getProduct(1);
		String CatalogID= "32";
		int AvarageDeleveryTime = 10;
		ProductFromSupplier PFS= new ProductFromSupplier(1,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		String price= "6";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 300, 3.5);
		
		p=InvBLManager.GetInvBLManager().getProduct(1);
		CatalogID= "312";
		AvarageDeleveryTime = 9;
		PFS= new ProductFromSupplier(2,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "7";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 100, 5);
		
		p=InvBLManager.GetInvBLManager().getProduct(1);
		CatalogID= "21";
		AvarageDeleveryTime = 9;
		PFS= new ProductFromSupplier(3,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "4";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		
		p=InvBLManager.GetInvBLManager().getProduct(2);
		CatalogID= "31";
		AvarageDeleveryTime = 21;
		PFS= new ProductFromSupplier(2,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "8";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));

		p=InvBLManager.GetInvBLManager().getProduct(3);
		CatalogID= "244";
		AvarageDeleveryTime = 8;
		PFS= new ProductFromSupplier(3,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "7";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 33, 3.9);
		
		p=InvBLManager.GetInvBLManager().getProduct(3);
		CatalogID= "214";
		AvarageDeleveryTime = 8;
		PFS= new ProductFromSupplier(4,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "4";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		
		p=InvBLManager.GetInvBLManager().getProduct(4);
		CatalogID= "55";
		AvarageDeleveryTime = 6;
		PFS= new ProductFromSupplier(5,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "10";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		
		p=InvBLManager.GetInvBLManager().getProduct(5);
		CatalogID= "100";
		AvarageDeleveryTime = 4;
		PFS= new ProductFromSupplier(5,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "24";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		
		p=InvBLManager.GetInvBLManager().getProduct(6);
		CatalogID= "2003";
		AvarageDeleveryTime = 3;
		PFS= new ProductFromSupplier(5,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "11";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		
		p=InvBLManager.GetInvBLManager().getProduct(7);
		CatalogID= "220";
		AvarageDeleveryTime = 12;
		PFS= new ProductFromSupplier(5,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "33";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		
		p=InvBLManager.GetInvBLManager().getProduct(8);
		CatalogID= "1222";
		AvarageDeleveryTime = 7;
		PFS= new ProductFromSupplier(5,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "40";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 60, 22.0);
		
		p=InvBLManager.GetInvBLManager().getProduct(8);
		CatalogID= "122";
		AvarageDeleveryTime = 1;
		PFS= new ProductFromSupplier(1,p,Integer.parseInt(CatalogID),AvarageDeleveryTime);
		price= "24";
		BLSupplier.GetBLSupplier().addProductFromSupplier(PFS);
		BLSupplier.GetBLSupplier().InsertNewPriceForProduct(PFS.getSupplierId(), PFS.getProduct().getId(), 0, Double.valueOf(price));


	}
	
	private static void initOrder(){
		
		String sql = "CREATE TABLE IF NOT EXISTS Orders (\n"
				+ "	OrderNumber Integer PRIMARY KEY AUTOINCREMENT,\n"
				+ "	SupplierId Integer REFERENCES Supplier(CompanyId),\n"
				+ "	OpenDate varchar(30) NOT NULL,\n"
				+ "	DueDate varchar(30) DEFAULT '',\n"
				+ "	StoreAddress varchar(30) NOT NULL,\n"
				+ "	HaveTransport Integer NOT NULL,\n"
				+ "	isPeriodic Integer NOT NULL);\n";
		OrderManager.executeSQLCommand(sql);
		
		String sql2 = "CREATE TABLE IF NOT EXISTS OrderProduct (\n"
				+ "	OrderNumber Integer REFERENCES Orders(OrderNumber),\n"
				+ "	ProductId Integer REFERENCES Product(Barcode),\n"
				+ "	Amount Integer NOT NULL,\n"
				+ "CONSTRAINT PK_ProductsPrices PRIMARY KEY (OrderNumber,ProductId));\n";
		OrderManager.executeSQLCommand(sql2);
		
		
		Order order=new Order(1);
		order.setPeriodic(10);
		OrderProduct orderProduct=new OrderProduct(1);
		orderProduct.setAmount(10);
		order.addProduct(orderProduct);
		Orders.getInstance().addNewOrder(order);
		
		Order order2=new Order(2);
		order2.setPeriodic(10);
		orderProduct=new OrderProduct(6);
		orderProduct.setAmount(10);
		order2.addProduct(orderProduct);
		orderProduct=new OrderProduct(7);
		orderProduct.setAmount(5);
		order2.addProduct(orderProduct);
		
		Orders.getInstance().addNewOrder(order2);
		
		
	}
	
	
}
