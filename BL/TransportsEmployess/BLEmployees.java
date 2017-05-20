package BL.TransportsEmployess;
import java.util.Vector;

import SharedClasses.TransportsEmployess.Employee;
import SharedClasses.TransportsEmployess.EmployeeRestriction;
import SharedClasses.TransportsEmployess.EmployeeSpeciality;
import SharedClasses.Pair;
import SharedClasses.TransportsEmployess.Shift;

public interface BLEmployees
{
    Vector<Pair<Shift,String>> fetchAllEmployeeShifts(int id);

    void sortDatesPair(Vector<Pair<Shift,String>> vec);

    public enum checkedStat
    {
        checked_in,
        already_checked,
        not_checked_in,
    }

    //employeeTable
    boolean addEmployee(String id, String salary, String private_name, String last_name, String start_of_employment_date, String end_of_employment_date, String rank , String bankAccount,String address,int day);
    boolean updateEmployee(String id, String salary, String private_name, String last_name, String start_of_employment_date, String end_of_employment_date, String rank , String bankAccount,String address,int day);
    boolean deleteEmployee(String id);
    Employee fetchEmployee(String id);
    boolean addShift(String date, String type,int manager,int cashier,int storekeeper ,int carrier,String address);
    boolean initShift(Shift shift);
    boolean deleteShift(String type, String date, String address);
    Shift fetchShift(String type, String date, String address);
    Vector<Shift> getEmptyShifts(String address);
    public Vector<Shift> fetchInitializedShifts();
    Vector<Shift> getUptoDate(Vector<Shift> vec);
    checkedStat checkIfEmployeeArrivedToShift(int id);
    boolean addEmployeeSpeciality(int id, String specialization);
    boolean deleteEmployeeSpeciality(int id, String specialization);
    boolean insertEmployeeRestriction(int id, int day, String type);
    boolean deleteEmployeeRestriction(int id, int day, String type);
    EmployeeRestriction fetchEmployeeRestriction(int id, int day, String type);
    EmployeeSpeciality[] getSpecsOf(int id);
    void sortDates(Vector<Shift> notSorted);
    boolean addEmployeeShift(Shift shift,Employee emp, String specialization);

    Vector<EmployeeRestriction> fetchAllEmployeeRestrictions(int id);
    Vector<Employee> getPossibleWorkers (Shift shift, String specialization);

    void reduce(Vector<Integer> vecOpt1, Vector<Employee> vecMan, Vector<Employee> vecCash);
    
    Vector<Pair<String,Integer>> fetchShiftAccupations(Shift shift);
    public Vector<Pair<Employee,String>> fetchEmployeesInShift(String address, Shift shift);
    public boolean cheakAvailableStoreKeepers(String addressStore, String date, String shiftType);
}
