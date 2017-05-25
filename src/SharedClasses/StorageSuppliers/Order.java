package SharedClasses.StorageSuppliers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import BL.StorageSuppliers.BLSupplier;

public class Order {
	
	private LinkedList<OrderProduct> products;
	private int OrderNumber;
	private int SupplierId;
	private String openDate;
	private int Periodic;
	private String DueDate;
	private int HaveTransport;
	private String StoreAddress;
	
	public Order(int SupplierId){
		this.SupplierId=SupplierId;
		this.openDate=ZonedDateTime.now().toString();
		this.setDueDate("");
		products = new LinkedList<OrderProduct>();
		this.HaveTransport=DAL.Suppliers.SupplierManager.getInstance().getSupplier(SupplierId).getDelivery();
		this.StoreAddress="";
	}
	
	public Order(int SupplierId, int isPeriodic, String StoreAddress){
		this(SupplierId);
		this.Periodic=isPeriodic;
		this.StoreAddress = StoreAddress;
	}
	
	public Order(int OrderNumber,int isPeriodic,int SupplierId, String StoreAddress){
		this(SupplierId,isPeriodic,StoreAddress);
		this.OrderNumber=OrderNumber;
	}
	
	public Order(int OrderNumber,int SupplierId,String openDate, int isPeriodic,String DueDate, String StoreAddress){
		this(OrderNumber,isPeriodic, SupplierId, StoreAddress);
		this.openDate=openDate;
		this.DueDate=DueDate;
	}
	
	public Order(int OrderNumber,int SupplierId,String openDate, int isPeriodic,String DueDate,String StoreAddress,int HaveTransport){
		this(OrderNumber,SupplierId,openDate,isPeriodic,DueDate,StoreAddress);
		this.HaveTransport=HaveTransport;
		
	}
	
	public void setOrderNumber(int OrderNumber){
		this.OrderNumber=OrderNumber;
	}
	
	public void setProducts(LinkedList<OrderProduct> products){
		this.products=products;
	}
	
	public Boolean getIsPeriodic() {
		return Periodic!=0;
	}
	public Boolean getISHaveTransport() {
		return HaveTransport!=0;
	}
	public String getAddres() {
		return StoreAddress;
	}
	public int getPeriodic(){
		return Periodic;
	}
	public int getHaveTransport(){
		return HaveTransport;
	}
	public void setPeriodic(int Periodic) {
		this.Periodic = Periodic;
	}
	public void setAddres(String Addres) {
		this.StoreAddress=Addres;
	}
	public void setHaveTransport(int HaveTransport) {
		this.HaveTransport=HaveTransport;
	}
	
	public int getSupplierId() {
		return SupplierId;
	}
	public int getOrderNumber() {
		return OrderNumber;
	}

	public String getDate() {
		return openDate;
	}

	public boolean isOpen() {
		return DueDate.equals("");
	}

	public void setDueDate(String DueDate) {
		this.DueDate = DueDate;
	}
	
	public String getDueDate(){
		return DueDate;
	}

	public LinkedList<OrderProduct> getProducts() {
		return products;
	}

	
	public boolean canOrder(){
		if (!DueDate.equals(""))
			return false;
		if(products.size()==0)
			return true;
		/*ZonedDateTime ArrivalDate = ZonedDateTime.parse(//TODO:: niv your func is not working
				DAL.Suppliers.SupplierManager.getInstance().getSupplier(SupplierId).
				getNearestDelivery(ZonedDateTime.parse(openDate).plusDays(getMaxProductPrepTime())));
		*/
		ZonedDateTime ArrivalDate = ZonedDateTime.parse(openDate).plusDays(getMaxProductPrepTime());		
		ZonedDateTime nowTime = ZonedDateTime.now();
		if (nowTime.plusDays(1).isAfter(ArrivalDate))
			return false;
		return true;
	}
	
	private int getMaxProductPrepTime(){
		int maxTime = 0;
		for (OrderProduct prod: products){
			int avgTime = DAL.Suppliers.SupplierManager.getInstance().getProductFromSupplier(SupplierId, prod.getProductId()).getAvarageDeleveryTime();
			if (avgTime > maxTime)
				maxTime = avgTime;
		}
		return maxTime;
	}
	
	public boolean addProduct(OrderProduct p) {
		if (!canOrder())
			return false;
		for (OrderProduct prod : products)
			if (p.getProductId() == prod.getProductId()){  //TODO: add Date Query
				prod.setAmount(prod.getAmount()+p.getAmount());
				return true;
			}
		products.add(p);
		return true;
	}
	
	public boolean removeProduct(OrderProduct p) {
		return products.remove(p);
	}
	
	public String getSupplierPhoneNumber(){
		SupplierContact[] SC=BLSupplier.GetBLSupplier().getSupplierContacts(SupplierId);
		if(SC.length>0)
			return SC[0].getPhone();
		return "there is no contact info for this Supplier";
		
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
		
	}
	
	public String toString(){
		ZonedDateTime t = ZonedDateTime.parse(getDate());
		String Date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(t);
		Supplier sup = DAL.Suppliers.SupplierManager.getInstance().getSupplier(SupplierId);
		String s="";
		s=s+"----------------------------------------\n";
		s=s+"Order number: "+getOrderNumber()+"\nSupplier number: "+getSupplierId()+
				"\nSupplier Name: "+sup.getName()+"\nAddress: "+sup.getAddress()+
				"\nOrder date: "+Date+"\nContact number: "+getSupplierPhoneNumber()+"\n";
		s=s+"------------------\nProducts in order:\n------------------";
		for(int j=0;j<getProducts().size();j++){ //change to cataloge number: BL.StorageSuppliers.BLSupplier.GetBLSupplier().getProductFromSupplier(getSupplierId(),getProducts().get(j).getProductId())
			s=s+"\nProduct numebr: "+getProducts().get(j).getProductId()+"\nProduct name:"+getProducts().get(j).getProductName()
					+"\nAmount: "+getProducts().get(j).getAmount()+"\nOriginal price: "+getProducts().get(j).getInitialProductPrice()
					+"\nDiscount: "+((100-(100*getProducts().get(j).getCurrentProductPrice()/getProducts().get(j).getInitialProductPrice()))
							+"\nCurrent price "+getProducts().get(j).getCurrentProductPrice())+"\n";
		}
		s=s+"----------------------------------------\n";
		return s;
	}
	public String getOrderDetail(){
		ZonedDateTime t = ZonedDateTime.parse(getDate());
		String Date = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(t);
		ZonedDateTime t2;
		String Date2="Not arrived yet";
		if(!getDueDate().equals("")){
			t2= ZonedDateTime.parse(getDueDate());
			Date2= DateTimeFormatter.ofPattern("dd/MM/yyyy").format(t2);
			
		}
		Supplier sup = DAL.Suppliers.SupplierManager.getInstance().getSupplier(SupplierId);
		String s="";
		s=s+"----------------------------------------\n";
		s=s+"Order number: "+getOrderNumber()+"\nSupplier number: "+getSupplierId()+
				"\nSupplier Name: "+sup.getName()+"\nAddress: "+sup.getAddress()+
				"\nOrder date: "+Date+"\nOrder arrival date: "+Date2+"\nContact number: "+getSupplierPhoneNumber()+"\n";
		if(getIsPeriodic()){
			s=s+"Order type: Periodic order\n";
		}
		else{
			s=s+"Order type: Order due to absence\n";
		}
		s=s+"------------------\nProducts in order:\n------------------";
		for(int j=0;j<getProducts().size();j++){
			s=s+"\nProduct numebr: "+BL.StorageSuppliers.BLSupplier.GetBLSupplier().getProductFromSupplier(getSupplierId(),getProducts().get(j).getProductId())
					+"\nProduct name:"+getProducts().get(j).getProductName()
					+"\nAmount: "+getProducts().get(j).getAmount()+"\nOriginal price: "+getProducts().get(j).getInitialProductPrice()
					+"\nDiscount: "+((100-(100*getProducts().get(j).getCurrentProductPrice()/getProducts().get(j).getInitialProductPrice()))
							+"\nCurrent price "+getProducts().get(j).getCurrentProductPrice())
							+"\n";
		}
		s=s+"----------------------------------------\n";
		return s;
	}
	
	public double getWeightOrder(){
		double sum=0;
		for(OrderProduct p: products){
			int amount=p.getAmount();
			Product p1=BL.StorageSuppliers.InvBLManager.GetInvBLManager().getProduct(p.getProductId());
			sum+=amount*p1.getWeight();
		}
		return sum;
	}
	
}
