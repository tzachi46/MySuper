package SharedClasses.StorageSuppliers;
/**
 * 
 * An object that represent a category of products in the store
 *
 */
public class Category {
	private int id;
	private int fatherId;
	private String name;
	private int discount;
	private String discountFinishTime;
	private String discountStartTime;
	/**
	 * constructor 
	 * @param id
	 */
	public Category(int id){
		this.name=null;
		this.id=id;
		this.fatherId=0;
		this.discount=0;
		this.discountFinishTime=null;
		this.discountStartTime=null;
	}
	/**
	 * constructor
	 * @param id
	 * @param fatherId
	 * @param name
	 */
	public Category(int id, int fatherId,String name){
		this.name=name;
		this.id=id;
		this.fatherId=fatherId;
		discount=0;
		discountFinishTime=null;
		discountStartTime=null;	
	}
	/**
	 * constructor
	 * @param id
	 * @param fatherId
	 * @param name
	 * @param discount
	 * @param discountFinishTime
	 * @param discountStartTime
	 */
	public Category(int id, int fatherId,String name,int discount,String discountStartTime,String discountFinishTime ){
		this.name=name;
		this.id=id;
		this.fatherId=fatherId;
		this.discount=discount;
		this.discountFinishTime=discountFinishTime;
		this.discountStartTime=discountStartTime;
	}

	
	/**
	 * get id of the category
	 * @return id
	 */
	public int getId(){
		return id;
	}
	/**
	 * get parent category id
	 * @return
	 */
	public int getFatherId() {
		return fatherId;
	}
	/**
	 * get the name of the category 
	 * @return name 
	 */
	public String getName(){
		return name;
	}
	/**
	 * get discount on the category
	 * @return
	 */
	public int getDiscount(){
		return discount;
	}
	/**
	 * get when is the discount is finish on the category
	 * @return
	 */
	public String getDiscountFinishTime(){
		return discountFinishTime;
	}
	/**
	 * get when is the discount is starting on the category
	 * @return
	 */
	public String getDiscountStartTime(){
		return discountStartTime;
	}
	
	
	/**
	 * sets id
	 */
	public void setId(int id){
		this.id=id;
	}
	/**
	*set fatherId
	*/
	public void setFatherId(int fatherId) {
		this.fatherId=fatherId;
	}
	/**
	 * sets name 
	 */
	public void setName(String name){
		this.name=name;
	}
	/**
	 * set the discount
	 * @param discount
	 */
	public void setDiscount(int discount){
		this.discount=discount;
	}
	/**
	 * set when is the discount is starting on the category
	 * @param discountStartTime
	 */
	public void setDiscountStartTime(String discountStartTime){
		this.discountStartTime=discountStartTime;
	}
	/**
	 * set when is the discount is finish on the category
	 * @param discountFinishTime
	 */
	public void setDiscountFinishTime(String discountFinishTime){
		this.discountFinishTime=discountFinishTime;
	}
	
	
}
