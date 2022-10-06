package reserve;

public class OrderData {
	
	private int odno;
	private String mID;
	private String orderDate;
	private int peopleNum;
	private String paymentMethod;
	private int price;

	
	public OrderData(int odno, String mID, String orderDate, int peopleNum, String paymentMethod, int price) {
		super();
		this.odno = odno;
		this.mID = mID;
		this.orderDate = orderDate;
		this.peopleNum = peopleNum;
		this.paymentMethod = paymentMethod;
		this.price = price;
	}

	public OrderData(String mID, String orderDate, int peopleNum, String paymentMethod, int price) {
		super();
		this.mID = mID;
		this.orderDate = orderDate;
		this.peopleNum = peopleNum;
		this.paymentMethod = paymentMethod;
		this.price = price;
	}

	
	public int getOdno() {
		return odno;
	}

	public void setOdno(int odno) {
		this.odno = odno;
	}

	public String getmID() {
		return mID;
	}

	public void setmID(String mID) {
		this.mID = mID;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(int peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	
}
