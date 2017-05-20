package SharedClasses.TransportsEmployess;

import java.util.Vector;

import SharedClasses.Pair;

public class Shift {
	private String type;
	private String date;
	private int day;
	private int init;
	private String storeAddress; 

    public Shift(String date, String type, int day, int init, String storeAddress) {

        this.type = type;
        this.date = date;
        this.day = day;
        this.init = init;
        this.storeAddress = storeAddress;
    }
    
    public String getStoreAddress(){
    	return storeAddress;
    }
    public String getType() {
        return type;
    }
    public String getDate() {
        return date;
    }
    public int getDay() {
        return day;
    }
    public int getInit() {
        return init;
    }
	@Override
	public String toString() {
		return "Shift [type=" + type + ", date=" + date + ", day=" + day + ", init=" + init + ", store address="
				+ this.storeAddress + "]";
	}

	public static Integer getNumOfAccup(String accup, Vector<Pair<String,Integer>> accups) {
		for(Pair<String,Integer> p : accups){
			if(p.getKey().equals(accup)){
				return p.getValue();
			}
		}
		return 0;
	}
   

}
