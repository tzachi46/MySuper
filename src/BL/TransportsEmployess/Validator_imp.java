package BL.TransportsEmployess;

import java.util.Vector;


public class Validator_imp implements Validator{

	@Override
	public boolean validateName(String name) {
		if(name.equals(""))
			return false;
		for(int i = 0; i < name.length(); i++)
		{
			if(!((name.charAt(i) >= 'a' && name.charAt(i) <= 'z') ||
					(name.charAt(i) >= 'A' && name.charAt(i) <= 'Z'))){
			
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean validateDouble(String s) {
		double lno = -1;
		try {
			lno = Double.parseDouble(s);
		} catch(Exception e){
			return false;
		}
		return lno > 0;
	}
	
	@Override
    public boolean validateID(String id){
        if(id.length() != 9)
            return false;
        if(id.charAt(0)=='0')
            return false;
        for(int i=0; i<9; i++){
            if(!(id.charAt(i)>='0' && id.charAt(i)<='9'))
                return false;
        }
        return true;
    }
	
    @Override
	public boolean validateTime(String time) 
    {
		//checks time format (hh:mm)
    	if(time.length()!=5)
    		return false;
		boolean check = time.charAt(2) == ':';
		try {
			int h = Integer.parseInt(time.substring(0, 2));
			int m = Integer.parseInt(time.substring(3));
			check = h >= 0 && h <= 23 && m <= 59 && m >= 0;
		} catch(Exception e){
			check = false;
		}
		return check;
	}
	
	@Override
	public boolean validateDate(String date){
        if(date.length()!=10)
            return false;
        if((date.charAt(2) != '/' || date.charAt(5) != '/'))
            return false;
        if(date.length() !=0){
            try
            {
                int day = Integer.parseInt(date.substring(0, 2));
                int month = Integer.parseInt(date.substring(3, 5));
                int year = Integer.parseInt(date.substring(6, 10));

                if(!((day > 0 && day <= 31) && (month > 0 && month <= 12) &&  (year > 1950 && year < 3000)))
                    return false;

            }
            catch (Exception e)
            {
                return false;
            }
        }
        return true;
    }

	@Override
    public boolean validateBankName(String bankName)
    {
        if(bankName.equals("leumi") || bankName.equals("hapoalim") || bankName.equals("discount"))
            return  true;
        return false;
    }

    @Override
    public boolean validateSpec(String spec){
        if(!(spec.equals("Cashier") || spec.equals("Carrier") || spec.equals("StoreKeeper") || spec.equals("Manager")))
            return false;
        return true;
    }
    
    @Override
    public Vector<Integer> validateEmployeeChoice(int size, String choice, int numOfEmps)
    {
        if(choice.equals("0"))
            return null;
        Vector<Integer> vec = new Vector<>();
        String[] parts = choice.split(",");
        if (parts.length != numOfEmps)
            return null;
        try
        {
            for (int i =0; i<parts.length; i++)
            {
                int j = Integer.parseInt(parts[i]);
                if(j<1 || j > size)
                    return null;
                vec.add(j);
            }
            for (int a =0; a<parts.length - 1; a++)
            {
                for (int b = a + 1; b<parts.length; b++)
                {
                    if (vec.elementAt(a) == vec .elementAt(b))
                        return null;
                }
            }
            return  vec;
        }
        catch (Exception e)
        {
            return null;
        }
    }

	@Override
	public boolean validateIntInBounds(String num, int lower, int higer) {
		int numberToCheck = lower-1;
		try{
			numberToCheck = Integer.parseInt(num);
		}catch(Exception e){
			return false;
		}
		return numberToCheck>=lower && numberToCheck<=higer;
	}

	@Override
	public boolean validatePhoneNumber(String phoneNum) {
		if(phoneNum.length() != 9 && phoneNum.length() != 10)
            return false;
        if(phoneNum.charAt(0)!='0')
            return false;
        for(int i=0; i<9; i++){
            if(!(phoneNum.charAt(i)>='0' && phoneNum.charAt(i)<='9'))
                return false;
        }
        return true;
	}
}
