package Base;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
public class Order {
	private String orderId;
	private String userId;
	private int status;		//0:in process, 1: finished, 2: canceled
	private String time;
	private HashMap<String, Integer> dishes; 	//key: dish name, value: dish count
	private double totalCost;
	
	public String getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getStatus() {
		if (this.status == 0) {
			return "In process";
		}
		else if (this.status == 1) {
			return "Finished";
		}
		else {
			return "Canceled";
		}
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getOrderTime() {
		return this.time;
	}
	
	public void setOrderTime(Timestamp time) {
		this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
	}
	
	public String getDishes() {
		StringBuffer buffer = new StringBuffer();
		Iterator<HashMap.Entry<String, Integer>> iterator = this.dishes.entrySet().iterator();
		while (iterator.hasNext()) {
			HashMap.Entry<String, Integer> entry = iterator.next();
			buffer.append(entry.getKey() + "*");
			buffer.append(entry.getValue());
			if (iterator.hasNext()) {
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
	
	public HashMap<String, Integer> getDishesMap() {
		return this.dishes;
	}
	
	public void setDishes(String dishes) {
		String[] strs = dishes.split(",");
		HashMap<String, Integer> map = new HashMap<>();
		for (String s : strs) {
			String key = s.split("\\*")[0];
			Integer value = Integer.valueOf(s.split("\\*")[1]);
			map.put(key, value);
		}
		this.dishes = map;
	}
	
	public void changeDishes(HashMap<String, Integer> dishes, double cost) {
		this.dishes = dishes;
		this.totalCost = cost;
	}
	
	public double getTotalCost() {
		return this.totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
}
