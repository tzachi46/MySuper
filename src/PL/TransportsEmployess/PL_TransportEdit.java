package PL.TransportsEmployess;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Vector;

import BL.TransportsEmployess.*;
import SharedClasses.StorageSuppliers.Order;
import SharedClasses.StorageSuppliers.OrderProduct;
import SharedClasses.TransportsEmployess.Driver;
import SharedClasses.TransportsEmployess.Transport;
import SharedClasses.TransportsEmployess.Truck;

public class PL_TransportEdit 
{
	private BL bl;
	private Scanner scanner;
	private Validator validator;
	private PL_Shared pl_Shared;

	public PL_TransportEdit(BL bl, Scanner scanner, Validator validator, PL_Shared pl_Shared)
	{
		this.bl = bl;
		this.scanner = scanner;
		this.validator = validator;
		this.pl_Shared = pl_Shared;
	}
	

	public void workOnTransport() 
	{
		while(true)	
		{
			System.out.println("Which of the following operations you wish to do :");
			System.out.println("1) Insert new Transport.");
			System.out.println("2) Insert new site to an existing transport.");
			//System.out.println("3) Delete a site from existing transport.");
			System.out.println("3) Update existing Transport's general details.");
			//System.out.println("4) Remove transport from the database.");
			System.out.println("5) Fetch transport's details from the database.");
			System.out.println("6) Fetch transport's destinations.");
			System.out.println("7) Return to Main Menu.");
			
			String choice = scanner.nextLine();
			if(!HandeleTransportOperationChoice(choice))
	        	break;
		}	
	}
	
	private boolean HandeleTransportOperationChoice(String choice) 
	{
		switch(choice) {
			case "1":
					insertTransport(true);
					break;
			case "2":
					insertTransport(false);
					break;
		/*	case "3":
					deleteSiteFromTransport();
					break;
		*/	case "3":
					updateTransport();
					break;
		/*	case "4":
					deleteTransport();
					break;*/
			case "5":
					fetchTransport();
					break;
			case "6":											
					fetchTransportDests();					 
					break;									
			case "7":
					return false;
			default:
			{
	            System.out.println("Invalid input, try again");
	        	break;
	        }
		}
		return true;
	}
	
	 public String getDateInputFromUser()
	 {
		 System.out.println("Enter Date :");
	     String date = scanner.nextLine();
	     while (!validator.validateDate(date))
	     {
	         if (date.equals("~"))
	             return date;
	         System.out.println("date is not valid, try again: ");
	         date = scanner.nextLine();
	     }
	     return date;
	 }
	 
	 public String getTimeInputFromUser(String toPrint)
	 {
		 System.out.println("Enter " + toPrint);
	     String time = scanner.nextLine();
	     while (!validator.validateTime(time))
	     {
	         if (time.equals("~"))
	             return time;
	         System.out.println(toPrint + " is not valid, try again:");
	         time = scanner.nextLine();
	     }
	     return time;
	 }
	 public String getTimeInputInShift(String timeLess ,String toPrint)
	 {
		 System.out.println("Enter " + toPrint);
		 String time = scanner.nextLine();
	     while (!validator.validateTime(time) || !checkSameShift(timeLess,time)) 
	     {
	         if (time.equals("~"))
	             return time;
	         System.out.println(toPrint + " is not valid, try again:");
	         time = scanner.nextLine();
	     }
	     return time;
	 }
	 
	 private boolean checkSameShift(String relTime,String newTime)
	 {
		 int hour1 = Integer.parseInt(relTime.substring(0, 2));
		 int min1 = Integer.parseInt(relTime.substring(3));
		 int hour2 = Integer.parseInt(newTime.substring(0, 2));
		 int min2 = Integer.parseInt(newTime.substring(3));
		 
		 if(hour1<24 && hour1>=12)
		 {
			 if(hour2<24 && hour2>=12)
			 {
				 if(hour2 > hour1)
					 return true;
				 else
				 {
					 if(hour2 == hour1 && min2 > min1)
						 return true;
					 return false;
				 }
			 }
			 else
				 return false;
		 }
		 else
		 {
			 if(hour2<24 && hour2>=12)
				 return false;
			 else
			 {
				 if(hour2 > hour1)
					 return true;
				 else
				 {
					 if(hour2 == hour1 &&min2 > min1)
						 return true;
					 return false;
				 }
			 }
		  }
	}
	 	 
	private String getWeightFromUser(String toPrint)
	{			
		String weight; 
		System.out.println("Truck's "+ toPrint+ " (in Kg) : ");		
		weight = scanner.nextLine();
		while (!validator.validateDouble(weight)) 
		{
			if (weight.equals("~"))
				return "~";
			System.out.println(toPrint+ " is not valid, try again:");
			weight = scanner.nextLine();
		}
		return weight; 
	}
	

	private String getSourceDocNumInputFromUser(String toPrint)
	{			
		String sourceDocNum; 
		System.out.println("Enter "+ toPrint +" doc num Input : ");		
		sourceDocNum = scanner.nextLine();
		while (!validator.validateDouble(sourceDocNum)) 
		{
			if (sourceDocNum.equals("~"))
				return "~";
			System.out.println("doc num is not valid, try again:");
			sourceDocNum = scanner.nextLine();
		}
		return sourceDocNum; 
	}
	 
	 
	 
	 public int getExistTruckDrivers(String addressStore, String date, String shiftType,int lio)
	 {
		 Vector<Driver> drivers = bl.fetchAvailableDrivers(addressStore,date,shiftType);
		 for(int i =0;i< drivers.size();i++)
		 {
			 if(drivers.elementAt(i).getLno()<lio)
				 drivers.removeElementAt(i); 
		 }
		 if(drivers.size() == 0)
		 {
			 System.out.println("There are no drivers in the store");
			 return -1;
		 }
		 System.out.println("choose a avilable driver: ");
		 for(int i =0;i< drivers.size();i++)
		 {
			 Driver driver = drivers.elementAt(i);
			 System.out.println((i+1)+ ")" + driver.getFname() +" " + driver.getLname() + " " + driver.getId());
		 }
		 String option = scanner.nextLine();
		 while(!validator.validateIntInBounds(option, 0, drivers.size()))
		 {
			 if(option.equals("~"))
				 return 0;
			 System.out.println("invalid input, try again:");
			 option = scanner.nextLine();
		 } 
		 int index = Integer.parseInt(option);
		 return drivers.elementAt(index-1).getId();
	 }
	  
	private String fetchAvilableTruck(String date,String time)
	{
		String numberOfTruck;
		while(true)
		{
			numberOfTruck = pl_Shared.getExistingTruckNumber();
			if(numberOfTruck.equals("~"))
				return numberOfTruck;
			if(bl.checkIfTruckAvilable(date, time, Integer.parseInt(numberOfTruck)))
				break;
			else
				System.out.println("The truck is not avilable in this shift");		
		}
		return numberOfTruck;
		
	}
	 
	private void insertTransport(boolean toTras)
	{
		int i = 0;
        while (true)
        {
            Vector<Order> undeliveredOrders = bl.getUndeliveredOrders();
            if(undeliveredOrders == null)
            {
                System.out.println("no relevant undelivered orders");
                return;
            }
            System.out.println("choose option");
            if(toTras)
            	System.out.println("~)return, 1)left, 2)right, 3)Send Transport 4)manual");
            else
            	System.out.println("~)return, 1)left, 2)right, 3)Add order to Transport 4)manual");
            System.out.println(undeliveredOrders.elementAt(i).toString());
            String option = scanner.nextLine();
            if (!option.equals("~") && !option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4"))
                System.out.println("invalid input, try again");
            else {
                if (option.equals("~"))
                    break;
                if (option.equals("1")) {
                    if (i == 0)
                        System.out.println("earlier Orders not exist");
                    else
                        i--;
                }
                if (option.equals("2")) {
                    if (i == undeliveredOrders.size() - 1)
                        System.out.println("later shifts not exist");
                    else
                        i++;
                }
                if (option.equals("3")) 
                	if(toTras)
                		i = sentTransport(undeliveredOrders.elementAt(i),i);  
                	else
                		insertSiteToTransport(i,undeliveredOrders.elementAt(i));
                if (option.equals("4"))
                    i = pl_Shared.manualOrder(i,undeliveredOrders);
            }
        }
	}
	
	
	
	
	private int sentTransport(Order elementAt, int i) 
	{
		String date, time,  numberOfTruck = "0" ,sourceDocNum = "";
		double  weight = elementAt.getWeightOrder();
		int supplier = elementAt.getSupplierId();
		String source = elementAt.getAddres();
		int idOfDriver = -1;
		while(true)
		{
			boolean replan = true;
			System.out.println("Please insert the Transport's details : ");
			date = pl_Shared.getShiftDate();
			if(date.equals("~"))
				return i;
			time = getTimeInputFromUser("leaving time");
			if(time.equals("~"))
				return i;
			while(true)
			{
				boolean enterTruck = false;
				//CHANGE TO COMPANYID
				if(bl.cheakAvailableStoreKeepers(source, date, time))
				{
					numberOfTruck = fetchAvilableTruck(date,time);
					if(numberOfTruck.equals("~"))
						return i;
					Truck truck = bl.fetchTruck(Integer.parseInt(numberOfTruck));
					idOfDriver = getExistTruckDrivers(source,date,time,truck.getLicenceType());
					if(idOfDriver == 0)
						return i;
					if(idOfDriver > 0)
					{
						if(truck.getMaxWeight() < weight)
						{
							String choice = "2";
							System.out.println("Over Weight");
							while(choice.equals("2"))
							{
								choice = OverWeightMenu();
								if(choice.equals("4"))//return
									return i;
								if(choice.equals("2"))//return
								{
									double Mweight = minimizeWeightMenu(weight,elementAt);
									if(Mweight != -1)
									{
										weight = Mweight;
										break;
									}
								}
								if(choice.equals("1"))//replan
									replan = true;
								if(choice.equals("3"))
									enterTruck = true;
							}
						}
						else
						{
							replan = false;
							break;
						}
					}
				}
				else
					System.out.println("There is no available storekeepers in the store at this time, please try again");
				if(!enterTruck)
					break;
			}
			if(!replan)
				break;
		}	
		
		//CHANGW TO ????
		sourceDocNum = getSourceDocNumInputFromUser("supplier");
		if(sourceDocNum.equals("~"))
			return i;
		String docNum = getSourceDocNumInputFromUser("store");
		if(docNum.equals("~"))
			return i;
		String arrivaleTime = getTimeInputInShift(time, "arrival time");
		if(arrivaleTime.equals("~"))
			return i;
		boolean success = bl.createTransport(date, time,Integer.parseInt(numberOfTruck), idOfDriver, supplier, weight,Integer.parseInt(sourceDocNum),elementAt.getAddres());
		if(success)
		{
			System.out.println("Transport created successfully.");
			bl.addSiteToTransport(date, time, Integer.parseInt(numberOfTruck), Integer.parseInt(docNum),arrivaleTime);
			if(i==0)
				return i;
			return i-1;
		}
		else 
		{
			System.out.println("Unfortunately the system couldnt create the transport in the data base.");
			return i;
		}
	}

	

	private double minimizeWeightMenu(double maxWeight, Order order) 
	{
		String option;
		double weight = order.getWeightOrder();
		LinkedList<OrderProduct> orderProducts= order.getProducts();
		while(weight > maxWeight)
		{
			System.out.println("Choose option:");
			System.out.println("1)reduce product");
			System.out.println("2)return to OverWeight Menu");
			System.out.println("Current weight: " + weight + "Max weight: " + maxWeight);
			
			ListIterator<OrderProduct> listIterator = orderProducts.listIterator();
			while (listIterator.hasNext()) 
			{
				OrderProduct Current = listIterator.next();
				System.out.println(Current.getProductId() + " " + Current.getProductName()+ " " + Current.getProductWeight());
			}
			option = scanner.nextLine();
			if(option.equals(2))
				return -1;
		
			String pid,amount;
			System.out.println("Enter productId: ");
			pid = scanner.nextLine();
			System.out.println("Enter new Amount");
			amount = scanner.nextLine();
			
			listIterator = orderProducts.listIterator();
			while (listIterator.hasNext()) 
			{
				OrderProduct Current = listIterator.next();
				if(Current.getProductId() == Integer.parseInt(pid))
					Current.setAmount(Integer.parseInt(amount));
				System.out.println(Current.getProductId() + " " + Current.getProductName()+ " " + Current.getProductWeight());
			}
			weight = order.getWeightOrder();
		}	
		return weight;
	}


	private String OverWeightMenu() 
	{
		String option;
		while (true)
		{
			System.out.println("Choose option:");
			System.out.println("1)replan the transport");
			System.out.println("2)enter weight again");
			System.out.println("3)replace truck");
			System.out.println("4)return to previous menu");
				
			option = scanner.nextLine();
			if(validator.validateIntInBounds(option, 1, 4))
				break;
			System.out.println("invalid input");
		}			
		return option;
	}

		
	private int insertSiteToTransport(int i,Order o) 
	{
		double weight;
		String timeOfArrival,siteAddress;
		System.out.println("Please insert the Transport's details : ");
		Transport transport = getTransportByKey();
		if(transport == null)
			return i;
		if(!bl.cheakAvailableStoreKeepers(o.getAddres(), transport.getDateOfDep(), transport.getHourOfDep()))
		{
			System.out.println("There are no available storekeeprs at this site, try again");
			return i;
		}
		if(o.getWeightOrder() + transport.getCurrentWeight() > transport.getTruckWeight())
		{
			double min =  minimizeWeightMenu(transport.getTruckWeight(), o);
			if(min == -1)
				return i;
			weight = min;
		}
		else
			weight = o.getWeightOrder();
		
		if(bl.addreesAtTransport(o.getAddres(),transport))
		{
			timeOfArrival = bl.getArrivalTime(o.getAddres(), transport);
		}	
		else
		{
			while(true)
			{
				timeOfArrival = getTimeInputInShift(transport.getHourOfDep(), "time of arrival"); 
				if(timeOfArrival.equals("~"))
					return i;
				if(bl.getHoursOfArrival(transport).contains(timeOfArrival))
					System.out.println("At this time the truck is unavailable");
				else
					break;
			}
		}
		if(bl.addSiteToTransport(transport.getDateOfDep(), transport.getHourOfDep(),transport.getTruckNo() ,o.getOrderNumber(),timeOfArrival))
		{
			System.out.println("Successfuly Added the site to destinations.");
			transport.setWeight(weight);
			commitUpdate(transport);
			if(i != 1)
				return i-1;
			else
				return i;
		}
		else 
		{
			System.out.println("Failed to add the site (maybe the site already in the transport) sorry...");
			return i;
		}
	}
	
	/*private void deleteSiteFromTransport() 
	{
		String siteAddress;
		System.out.println("Please insert the Transport's details : ");
		Transport transport = getTransportByKey();
		while(true)
		{
			siteAddress = pl_Shared.getExistStoreAddressFromUser()getExistAddressFromUser(1);
			if(siteAddress.equals("0"))
				return;
			if(bl.fetchEmployee(Integer.toString(transport.getDriverID())).getWorkAddress().equals(siteAddress))
				System.out.println("Cant delete the Main destination site.");
			else
				break;
		}
		if(bl.deleteSiteFromTransport(transport.getDateOfDep(), transport.getHourOfDep(),transport.getTruckNo(),siteAddress))
			System.out.println("Successfuly removed site from destinations.");
		else
			System.out.println("Failed to remove the site, sorry...");
	}*/
	private void updateTransport() 
	{	
		Transport transport;
		while (true)
		{
			transport = getTransportByKey();
			if(transport == null)
				return;
			System.out.println("Choose option:");
			System.out.println("1)Updete id of driver");
			System.out.println("2)Updete sourceDoc number");
			System.out.println("3)return to previous menu");
				
			String option = scanner.nextLine();
			if(!handleTransportUpdate(option,transport))
					return;
		}			
	}
	
	private boolean handleTransportUpdate(String option, Transport transport) 
	{
		switch (option)
	    {
	        case "1":
	        		updateId(transport);
	        		break;
			case "2":
					updateSourceDoc(transport);
					break;
			case "3":
					return false;
	        default:
	        {
	            System.out.println("Invalid input, try again");
	        	break;
	        }
	    }
	 	return true;
	}


	private void updateSourceDoc(Transport transport) 
	{
		String  SourceDoc = getSourceDocNumInputFromUser("supplier");
		if(SourceDoc.equals("~"))
			return;
		transport.setSourceDoc(Integer.parseInt(SourceDoc));
		commitUpdate(transport);
		
	}


	private void updateId(Transport transport) 
	{
		Truck truck = bl.fetchTruck(transport.getTruckNo());
		int id = getExistTruckDrivers(bl.fetchEmployee(Integer.toString(transport.getDriverID())).getWorkAddress(), transport.getDateOfDep(), transport.getHourOfDep(),truck.getLicenceType());
	    if(id == 0 || id == -1)
	    	return;
	    transport.setDriverID(id);
		commitUpdate(transport);
	}

	private void commitUpdate(Transport transport)
	{
		if(bl.updateTransport(transport.getDateOfDep(), transport.getHourOfDep(), transport.getTruckNo(), transport.getDriverID(), transport.getCompanyID(), transport.getTruckWeight(), transport.getSourceDoc(), transport.getAddressOrign()))
			System.out.println("Transport updated successfully.");
		else 
			System.out.println("Unfortunately the system couldnt update the transport in the data base.");
	}
	
	/*private void deleteTransport() 
	{
		Transport transport =getTransportByKey();
		if(transport == null)
			return;
		if(bl.deleteTransport(transport.getDateOfDep(), transport.getHourOfDep(), transport.getTruckNo()))
			System.out.println("Transport deleted successfully.");
		else 
			System.out.println("Unfortunately the system couldn't deleted the Transport in the data base.");
		
	}*/
	private void fetchTransport() 
	{
		Transport transport =getTransportByKey();
		if(transport == null)
			return;
		System.out.println(transport.toString());
		System.out.println("Main destenation: "+bl.fetchEmployee(Integer.toString(transport.getDriverID())).getWorkAddress()+"\n");
	}
	private void fetchTransportDests() 
	{
		Transport transport =getTransportByKey();
		if(transport == null)
			return;
		System.out.println(((BLimp) bl).getBl_trans().getHoursOfArrival(transport));//bl.getTransportDests(transport.getDateOfDep(), transport.getHourOfDep(), transport.getTruckNo()).toString());
	}
	
	private Transport getTransportByKey() 
	{
		String date, time, numberOfTruck;
		Transport transport;
		while(true)
		{
			date = getDateInputFromUser();
			if(date.equals("~"))
				return null;
			time = getTimeInputFromUser("Leaving time");
			if(time.equals("~"))
				return null;
			numberOfTruck = pl_Shared.getExistingTruckNumber();
			if(numberOfTruck.equals("~"))
				return null;
		
			transport = bl.fetchTransport(date, time, Integer.parseInt(numberOfTruck));
			if(transport != null)
				break;
			else
				System.out.println("transport does not exist, try again: ");
		}
		return transport;
	}
}