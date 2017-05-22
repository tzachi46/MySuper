package SharedClasses.TransportsEmployess;

public class Message {
	private String date;
	private String address;
	private boolean Handled;
	private int OrderNumber;
	
	
	public int getOrderNumber() {
		return OrderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		OrderNumber = orderNumber;
	}
	public Message(String date, String address, boolean handled, int orderNumber) {
		super();
		this.date = date;
		this.address = address;
		Handled = handled;
		OrderNumber = orderNumber;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isHandled() {
		return Handled;
	}
	public void setHandled(boolean Handled) {
		this.Handled = Handled;
	}

}
