package BL.TransportsEmployess;

import java.util.Vector;

public interface Validator {
	/**
	 * Check if string is a valid name(no digits)
	 * @param name to validate
	 * @return true if name contains letters only
	 */
	public boolean validateName(String name);
	/**
	 * Check if string is a double larger than 0
	 * @param s string to validate
	 * @return true if s is valid positive double(>0)
	 */
	public boolean validateDouble(String s);
	/**
     * Validates the ID which supplied
     * @param id the id to validate
     * @return true if valid(9 digits, first one not '0'). else false.
     */
    public boolean validateID(String id);
	/**
	 * Check if input is in the format of hh:mm (valid hour & minute)
	 * @param time string to validate
	 * @return true if string is a valid representation of time
	 */
	public boolean validateTime(String time);
	/**
	 * Check if string is in the format of dd/mm/yyyy (valid day, month & year)
	 * @param date string to validate
	 * @return true if valid date (& year between 1950 & 3000)
	 */
	public boolean validateDate(String date);
    /**
     * Check if string is a valid bank name ("leumi","hapoalim","discount")
     * @param bankName string to validate
     * @return true if input is one of the above
     */
    public boolean validateBankName(String bankName);
    /**
     * Check if input is a known specialty
     * @param spec the string to validate
     * @return true if valid. else false.
     */
    public boolean validateSpec(String spec);
    /**
     * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * @param size
     * @param choice
     * @param numOfEmps
     * @return
     */
    public Vector<Integer> validateEmployeeChoice(int size, String choice, int numOfEmps);
    /**
     * Check if input is an integer within given bounds
     * CAN SWITCH : validateNumOfEmp , validateManNumOfEmp , validateDay , validateBranchNumber , validateAccountNumber , validateID , validateInteger
     * @param num string to validate
     * @param lower bound
     * @param higer bound
     * @return true if stirng is integer within lower & higher bounds, else otherwise
     */
    public boolean validateIntInBounds(String num, int lower, int higer);
	public boolean validatePhoneNumber(String phoneNum);
    
}
