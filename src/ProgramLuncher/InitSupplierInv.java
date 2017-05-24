package ProgramLuncher;

import DAL.Suppliers.SupplierManager;
import SharedClasses.StorageSuppliers.Supplier;
import SharedClasses.StorageSuppliers.SupplierContact;

public class InitSupplierInv {
	public static void main(String[] args) throws Exception 
	{
		DAL.DALManager.getInstance();
		InitSupplier();
		InitSupplierDays();
		InitSupplierContact();
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
