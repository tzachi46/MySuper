package PL.StorageSuppliers.Order;

import java.util.Scanner;

public class OrderCLI {
	public static void Start(Scanner in){
		CLIMenu.getInstance().Start(in);
	}
	
	public static void getAllOrders(Scanner in){
		CLIMenu.getInstance().getAllOrders(in);
	}
}
