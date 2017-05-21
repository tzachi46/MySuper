package PL.TransportsEmployess;

import java.util.Scanner;
import java.util.Vector;

import BL.TransportsEmployess.*;
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
			System.out.println("3) Delete a site from existing transport.");
			System.out.println("4) Update existing Transport's general details.");
			System.out.println("5) Remove transport from the database.");
			System.out.println("6) Fetch transport's details from the database.");
			System.out.println("7) Fetch transport's destinations.");
			System.out.println("8) Return to Main Menu.");
			
			String choice = scanner.nextLine();
			if(!HandeleTransportOperationChoice(choice))
	        	break;
		}	
	}
	
	private boolean HandeleTransportOperationChoice(String choice) 
	{
		switch(choice) {
			case "1":
					insertTransport();
					break;
			case "2":
					insertSiteToTransport();
					break;
			case "3":
					deleteSiteFromTransport();
					break;
			case "4":
					updateTransport();
					break;
			case "5":
					deleteTransport();
					break;
			case "6":
					fetchTransport();
					break;
			case "7":											
					fetchTransportDests();					 
					break;									
			case "8":
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
	         if (date.equals("0"))
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
	         if (time.equals("0"))
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
	         if (time.equals("0"))
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
			if (weight.equals("0"))
				return "0";
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
			if (sourceDocNum.equals("0"))
				return "0";
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
			 if(option.equals("0"))
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
			if(numberOfTruck.equals("0"))
				return numberOfTruck;
			if(bl.checkIfTruckAvilable(date, time, Integer.parseInt(numberOfTruck)))
				break;
			else
				System.out.println("The truck is not avilable in this shift");		
		}
		return numberOfTruck;
		
	}
	 
	private void insertTransport() 
	{
		//source-First store, where driver is coming from
		String date, time, source, supplier,  numberOfTruck,sourceDocNum, weight;
		int idOfDriver;
		while(true)
		{
			boolean replan = false;
			System.out.println("Please insert the Transport's details : ");
			System.out.println("*****************************************");
			supplier  = pl_Shared.getExistStoreAddressFromUser()/*getExistAddressFromUser(0)*/;
			if(supplier .equals("0"))
				return;
			date = pl_Shared.getShiftDate();
			if(date.equals("0"))
				return;
			time = getTimeInputFromUser("leaving time");
			if(time.equals("0"))
				return;
			while(true)
			{
				boolean enterSiteAndTruck = false;
				//CHANGE TO COMPANYID
				source = pl_Shared.getExistStoreAddressFromUser()/*getExistAddressFromUser(1)*/;
				if(source.equals("0"))
					return;
				if(bl.cheakAvailableStoreKeepers(source, date, time))
				{
					numberOfTruck = fetchAvilableTruck(date,time);
					if(numberOfTruck.equals("0"))
						return;
					Truck truck = bl.fetchTruck(Integer.parseInt(numberOfTruck));
					idOfDriver = getExistTruckDrivers(source,date,time,truck.getLicenceType());
					if(idOfDriver == 0)
						return;
					if(idOfDriver > 0)
					{
						while(true)
						{
							weight = getWeightFromUser("Weight");
							if(weight.equals("0"))
								return;
							if(truck.getMaxWeight() < Double.parseDouble(weight))
							{
								System.out.println("Over Weight");
								String choice = OverWeightMenu();
								if(choice.equals("4"))//return
									return;
								if(choice.equals("1"))//replan
								{
									replan = true;
									break;
								}
								if(choice.equals("3"))
								{
									enterSiteAndTruck = true;
									break;
								}
							}
							else
								break;
						}
						if(!enterSiteAndTruck)
							break;
					}
				}
				else
					System.out.println("There is no available storekeepers in the store at this time, please try again");
			}
			if(!replan)
				break;
		}
		//CHANGW TO ????
		sourceDocNum = getSourceDocNumInputFromUser("supplier");
		if(sourceDocNum.equals("0"))
			return;
		String docNum = getSourceDocNumInputFromUser("store");
		if(docNum.equals("0"))
			return;
		String arrivaleTime = getTimeInputInShift(time, "arrival time");
		if(arrivaleTime.equals("0"))
			return;
		boolean success = bl.createTransport(date, time,Integer.parseInt(numberOfTruck), idOfDriver, supplier, Double.parseDouble(weight),Integer.parseInt(sourceDocNum));
		if(success)
		{
			System.out.println("Transport created successfully.");
			bl.addSiteToTransport(date, time, Integer.parseInt(numberOfTruck), source, Integer.parseInt(docNum),arrivaleTime);
		}
		else 
			System.out.println("Unfortunately the system couldnt create the transport in the data base.");
	}
	

	private String OverWeightMenu() 
	{
		String option;
		while (true)
		{
			System.out.println("Choose option:");
			System.out.println("1)replan the transport");
			System.out.println("2)enter weight again");
			System.out.println("3)replace site and truck");
			System.out.println("4)return to previous menu");
				
			option = scanner.nextLine();
			if(validator.validateIntInBounds(option, 1, 4))
				break;
			System.out.println("invalid input");
		}			
		return option;
	}

		
	private void insertSiteToTransport() 
	{
		String timeOfArrival,siteAddress;
		System.out.println("Please insert the Transport's details : ");
		Transport transport = getTransportByKey();
		if(transport == null)
			return;
		while(true)
		{
			siteAddress =  pl_Shared.getExistStoreAddressFromUser()/*getExistAddressFromUser(1)*/;
			if(siteAddress.equals("0"))
				return;
			if(bl.fetchSite(siteAddress).getAreaCode()==bl.fetchSite(transport.getAddressOrign()).getAreaCode())
			{
				if(bl.cheakAvailableStoreKeepers(siteAddress, transport.getDateOfDep(), transport.getHourOfDep()))
					break;
				else
					System.out.println("There are no available storekeeprs at this site, try again");
			}
			else
				System.out.println("Address not in the same area of the Transport, try again");
		}
		String docNum = getSourceDocNumInputFromUser("store");
		if(docNum.equals("0"))
			return;
		while(true)
		{
			timeOfArrival = getTimeInputInShift(transport.getHourOfDep(), "time of arrival"); 
			if(timeOfArrival.equals("0"))
				return;
			if(bl.getHoursOfArrival(transport).contains(timeOfArrival))
				System.out.println("At this time the truck is unavailable");
			else
				break;
		}
		if(bl.addSiteToTransport(transport.getDateOfDep(), transport.getHourOfDep(),transport.getTruckNo(),siteAddress, Integer.parseInt(docNum),timeOfArrival))
			System.out.println("Successfuly Added the site to destinations.");
		else 
			System.out.println("Failed to add the site (maybe the site already in the transport) sorry...");
	}
	private void deleteSiteFromTransport() 
	{
		String siteAddress;
		System.out.println("Please insert the Transport's details : ");
		Transport transport = getTransportByKey();
		while(true)
		{
			siteAddress = pl_Shared.getExistStoreAddressFromUser()/*getExistAddressFromUser(1)*/;
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
	}
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
			System.out.println("2)Updete weight");
			System.out.println("3)Updete sourceDoc number");
			System.out.println("4)return to previous menu");
				
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
					updateWeight(transport);
					break;
			case "3":
					updateSourceDoc(transport);
					break;
			case "4":
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
		if(SourceDoc.equals("0"))
			return;
		transport.setSourceDoc(Integer.parseInt(SourceDoc));
		commitUpdate(transport);
		
	}


	private void updateWeight(Transport transport) 
	{
		String  weight;
		Truck truck = bl.fetchTruck(transport.getTruckNo());
		while(true)
		{
			weight = getWeightFromUser("Weight");
			if(weight.equals("0"))
				return;
			if(Double.parseDouble(weight)<=truck.getMaxWeight())
				break;
			else
				System.out.println("the Weight entered is bigger then the max Weight which is " + truck.getMaxWeight() + " , try again");
				
		}
		transport.setTruckWeight(Double.parseDouble(weight));
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
		if(bl.updateTransport(transport.getDateOfDep(), transport.getHourOfDep(), transport.getTruckNo()
				, transport.getDriverID(), transport.getAddressOrign(), transport.getTruckWeight(), transport.getSourceDoc()))
			System.out.println("Transport updated successfully.");
		else 
			System.out.println("Unfortunately the system couldnt update the transport in the data base.");
	}
	private void deleteTransport() 
	{
		Transport transport =getTransportByKey();
		if(transport == null)
			return;
		if(bl.deleteTransport(transport.getDateOfDep(), transport.getHourOfDep(), transport.getTruckNo()))
			System.out.println("Transport deleted successfully.");
		else 
			System.out.println("Unfortunately the system couldn't deleted the Transport in the data base.");
		
	}
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
		System.out.println(bl.getTransportDests(transport.getDateOfDep(), transport.getHourOfDep(), transport.getTruckNo()).toString());
	}
	
	private Transport getTransportByKey() 
	{
		String date, time, numberOfTruck;
		Transport transport;
		while(true)
		{
			date = getDateInputFromUser();
			if(date.equals("0"))
				return null;
			time = getTimeInputFromUser("Leaving time");
			if(time.equals("0"))
				return null;
			numberOfTruck = pl_Shared.getExistingTruckNumber();
			if(numberOfTruck.equals("0"))
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
