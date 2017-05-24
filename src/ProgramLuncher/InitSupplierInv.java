package ProgramLuncher;


import DAL.Inventory.ProductDB;
import DAL.Suppliers.SupplierManager;
import SharedClasses.StorageSuppliers.Product;
import SharedClasses.StorageSuppliers.Supplier;
import SharedClasses.StorageSuppliers.SupplierContact;

/***
 * things that i changed that might not be correct : 
 * 
 * executeSQLCommand ->  changed to public 
 * getProductDB -> changed to public 
 * changed url of DB -> to ours (HR-TR)
 * @author itay
 *
 */


public class InitSupplierInv {
	public static void main(String[] args) throws Exception 
	{
		DAL.DALManager.getInstance();
		InitSupplier();
		InitSupplierDays();
		InitSupplierContact();
		InitProducts();
		init.initHRTR();
		
		init.forItay();
		
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
		SupplierManager.executeSQLCommand(sql);
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
	
}