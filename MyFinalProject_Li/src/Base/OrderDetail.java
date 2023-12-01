package Base;

public class OrderDetail {
	private String dishName;
	private int num;
	
	public OrderDetail(String name, int n) {
		this.dishName = name;
		this.num = n;
	}
	
	public String getDishName() {
		return this.dishName;
	}
	
	public int getNum() {
		return this.num;
	}
}
