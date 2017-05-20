package BL.StorageSuppliers;


public class Calculations {
	private static Calculations instance;
	
	public static Calculations GetCalculations(){
		if(instance==null)
			instance= new Calculations();
		return instance;
	}
	
	/**
	 * Constructor 
	 */
	private Calculations(){} 
	/**
	 * check if s contains only numbers and not empty
	 * @param s
	 * @return true if contains only numbers else false
	 */
	public boolean checkDouble(String s){
		try
		{
			Double.parseDouble(s);
			for(int i=0;i<s.length();i++)
				if(s.charAt(i)=='f')
					return false;

			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
	/**
	 * 
	 * @param s
	 * @return true if s is an int
	 */
	public boolean checkInt(String s){
		try{
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
	/**
	 * 
	 * @param s
	 * @return true if s contains only numbers
	 */
	public boolean checkOnlyNumbers(String s){
		if(s.length()==0)
			return false;

		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i)))
				return false;
		}
		return true;
	}
	/**
	 * check if s contains only letters and not empty
	 * @param s
	 * @return true if contains only letters else false
	 */
	public boolean checkOnlyLetters(String s){
		if(s.length()==0)
			return false;

		char[] chars = s.toCharArray();

		for (char c : chars) {
			if(!Character.isLetter(c) && c!='-' && c!=' ') {
				return false;
			}
		}
		return true;
	}
	/**
	 * check if s is valid date(dd.mm.yyyy and not null) 
	 * @param s
	 * @return true if valid date else false
	 */
	public boolean checkDate(String s){
		if(s.length()!=10)
			return false;
		for(int i=0;i<s.length();i++){
			if(i==2||i==5){
				if(s.charAt(i)!='.')
					return false;
			}
			else{
				if(s.charAt(i)<48&&s.charAt(i)>57)
					return false;
			}
		}
		String temp1=s.substring(0, 2);
		if(Integer.parseInt(temp1)>31||Integer.parseInt(temp1)<0)
			return false;
		String temp2=s.substring(3,5);
		if(Integer.parseInt(temp2)>12||Integer.parseInt(temp2)<0)
			return false;
		String temp3=s.substring(6);
		if(Integer.parseInt(temp3)<0)
			return false;
		return true;
	}
	/**
	 * 
	 * @param input
	 * @param first
	 * @param last
	 * @return true if last>=input>=first
	 */
	 public boolean checkKeyboardMenu(String input,int first,int last){
		try{
			int temp=0;
			if(!checkInt(input))
				return false;
			temp=Integer.parseInt(input);
			if(temp>=first&&temp<=last)
				return true;
			return false;
		}
		catch(NumberFormatException e){
			return false;
		}
	}
}
