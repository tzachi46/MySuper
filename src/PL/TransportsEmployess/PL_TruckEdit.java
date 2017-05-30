package PL.TransportsEmployess;

import java.util.Scanner;

import BL.TransportsEmployess.*;
import SharedClasses.TransportsEmployess.Truck;

public class PL_TruckEdit
{
	private BL bl;
	private Scanner scanner;
	private Validator validator;
	PL_Shared pl_Shared;

	public PL_TruckEdit(BL bl, Scanner scanner, Validator validator,PL_Shared pl_Shared)
	{
		this.bl = bl;
		this.scanner = scanner;
		this.validator = validator;
		this.pl_Shared = pl_Shared;
	}
	
	public void workOnTrucks() 
	{
		while(true)	
		{
			System.out.println("Which of the following operations you wish to do :");
			System.out.println("1) Insert new Truck.");
			System.out.println("2) Update existing Truck's details.");
			System.out.println("3) Remove Truck from the company.");
			System.out.println("4) Fetch Truck from the company.");
			System.out.println("~) Return to Previous Menu.");
			
			String choice = scanner.nextLine();
			if(!HandeleTruckOperationChoice(choice))
	        	break;
		}
	}
	
	 private boolean HandeleTruckOperationChoice(String choice)
	 {
	 	switch (choice)
	    {
	        case "1":
					insertTruck();
					break;
			case "2":
					updateTruck();
					break;
			case "3":
					deleteTruck();
					break;
			case "4":
					fetchTruck();
					break;
			case "~":
					return false;
	        default:
	        {
	            System.out.println("Invalid input, try again");
	        	break;
	        }
	    }
	 	return true;
	 }
	

	private String getWeightFromUser(String toPrint)
	{			
		String weight; 
		System.out.println("Truck's "+ toPrint+ " (in Kg) : ");		
		weight = scanner.nextLine();
		while (!validator.validateDouble(weight) || (Double.parseDouble(weight) < 1000)) 
		{
			if (weight.equals("~"))
				return "~";
			System.out.println(toPrint+ " is not valid, try again (must be at least 1000 kg):");
			weight = scanner.nextLine();
		}
		return weight; 
	}
	
	
	
	private String getLicenceTypeFromUser()
	{			
		String licenceType; 
		System.out.println("Required licence type : ");
		licenceType = scanner.nextLine();
		while (!validator.validateIntInBounds(licenceType, 0, Integer.MAX_VALUE)) 
		{
			if (licenceType.equals("~"))
				return "~";
		    System.out.println("LicenceType is not valid, try again:");
		    licenceType = scanner.nextLine();
		}
	    return licenceType; 
	 }
	 
	
	 
	
	private void insertTruck() 
	{
		String truckNo;
		String weight, maxWeight,licenseType;
		String model = "";
		System.out.println("Please insert the Truck details : ");
		truckNo = pl_Shared.getNotExistingTruckNumber();
		if (truckNo.equals("~"))
        	return;
		model = pl_Shared.getNotEmptyStringFromUser("Model");
		if(model.equals("~")){
			return;
		}
		weight = getWeightFromUser("Weight");
		if(weight.equals("~"))
			return;
		maxWeight = getWeightFromUser("max Weight");
		if(maxWeight.equals("~"))
			return;

		licenseType = getLicenceTypeFromUser();
		if(licenseType.equals("~"))
			return;
		
		System.out.println();
		if(bl.createTruck(Integer.parseInt(truckNo),model,Double.parseDouble(weight),Double.parseDouble(maxWeight),Integer.parseInt(licenseType)))
			System.out.println("Truck created successfully.");
		else 
			System.out.println("Unfortunately the system couldnt create the truck in the data base.");
	}
	//TODO - UPDATE TO BE LIKE updateDriver
	private void updateTruck()
	{
		String truckNo= pl_Shared.getExistingTruckNumber();
		while (true)
		{
			//truckNo = pl_Shared.getExistingTruckNumber();
			if(truckNo.equals("~"))
				return;	
			Truck truck = bl.fetchTruck(Integer.parseInt(truckNo));
			if(truck!=null)
			{
				System.out.println("Choose option:");
				System.out.println("1)model");
				System.out.println("2)weight");
				System.out.println("3)maximum weight");
				System.out.println("4)license Type");
				System.out.println("~)return to previous menu");
				
				String option = scanner.nextLine();
				if(!handleTruckUpdate(option,truck))
					return;
			}
			else
				System.out.println("Truck does not exist in the system, try again:");
		}
	}
	
	public void commitUpdate(Truck truck)
	{
		if(bl.updateTruck(truck.getTruckNo(),truck.getModel(), truck.getWeight(), truck.getMaxWeight(), truck.getLicenceType()))
			System.out.println("Successfuly updated the Truck's details.");
		else 
			System.out.println("Unfortunately, something went wrong and the update wasn't complete, sorry...");
	}
	
	private boolean handleTruckUpdate(String option, Truck truck) 
	{
		switch (option)
	    {
	        case "1":
					updateModel(truck);
					break;
			case "2":
					updateWeight(truck);
					break;
			case "3":
					updateMaxWeight(truck);
					break;
			case "4":
					updateLicenseType(truck);
					break;
			case "~":
					return false;
	        default:
	        {
	            System.out.println("Invalid input, try again");
	        	break;
	        }
	    }
	 	return true;
	 }	

	private void updateLicenseType(Truck truck) 
	{
		String licenceType = getLicenceTypeFromUser();
		if(licenceType.equals("~"))
			return;
		if(Integer.parseInt(licenceType) < truck.getLicenceType()){
			truck.setLicenceType(Integer.parseInt(licenceType));
			commitUpdate(truck);
			return;
		}
		if(bl.checkReplacement(truck)){
			truck.setLicenceType(Integer.parseInt(licenceType));
			commitUpdate(truck);
			return;
		} else {
			System.out.println("truck's license type cannot be changed in the moment due to pending transports.");
			return;
		}
	}

	private void updateMaxWeight(Truck truck) 
	{
		String maxWeight = getWeightFromUser("max Weight");
		if(maxWeight.equals("~"))
			return;
		if(Double.parseDouble(maxWeight) >= truck.getMaxWeight()){
			truck.setMaxWeight(Double.parseDouble(maxWeight));
			commitUpdate(truck);
			return;
		}
		else {
			if(bl.checkReplacement(truck)){
				truck.setMaxWeight(Double.parseDouble(maxWeight));
				commitUpdate(truck);
				return;
			} else {
				System.out.println("truck's max weight cannot be changed in the moment due to pending transports.");
				return;
				
			}
		}
	}

	private void updateWeight(Truck truck) 
	{
		String weight = getWeightFromUser("Weight");
		if(weight.equals("~"))
			return;
		truck.setWeight(Double.parseDouble(weight));
		commitUpdate(truck);
		
	}

	private void updateModel(Truck truck) 
	{
		String model = pl_Shared.getNotEmptyStringFromUser("model");
		if(model.equals("~")){
			return;
		}
		truck.setModel(model);
		commitUpdate(truck);
	}

	private void deleteTruck() 
	{
		while(true)
		{
			String truckNo = pl_Shared.getExistingTruckNumber();
			if (truckNo.equals("~"))
	        	return;
			int numericTruckNo = Integer.parseInt(truckNo);
			if(bl.fetchTruck(numericTruckNo) != null)
			{
				if(bl.deleteTruck(numericTruckNo))
				{
					System.out.println("Truck was deleted successfully!");
					break;
				}
				else
					System.out.println("Something went wrong during the deletion of the truck, sorry...");
			}
			else
				System.out.println("Can't delete a truck that is not exist in the system, try again.");
		}
	}
	
	private void fetchTruck()
	{
			String truckNo = pl_Shared.getExistingTruckNumber();
			if (truckNo.equals("~"))
	        	return;
			System.out.println(bl.fetchTruck(Integer.parseInt(truckNo)).toString());
			System.out.println("---------------------------");
	}
}
