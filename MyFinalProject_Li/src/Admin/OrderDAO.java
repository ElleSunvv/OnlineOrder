package Admin;

import javafx.collections.ObservableList;
import DBUtil.DBUtil;
import Base.Order;
import javafx.collections.FXCollections;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {
	public static ObservableList<Order> getOrders()  throws ClassNotFoundException, SQLException {
		String sql = "select * from test.order order by status asc";
		
		try {
            ResultSet rsSet = DBUtil.dbExecute(sql);
            ObservableList<Order> orderList =  getOrderObjects(rsSet);
            return orderList;
        }
		catch (SQLException e){
            System.out.println("Error occured while fetching the data");
            e.printStackTrace();
            throw e;
        }
	}
	
	private static ObservableList<Order> getOrderObjects(ResultSet rsSet) throws SQLException, ClassNotFoundException {

        try {
            ObservableList<Order> orderList = FXCollections.observableArrayList();

            while (rsSet.next()){
                Order order = new Order();
                order.setOrderId(rsSet.getString("order_id"));
                order.setUserId(rsSet.getString("user_id"));
                order.setStatus(rsSet.getInt("status"));
                order.setOrderTime(rsSet.getTimestamp("time"));
                order.setDishes(rsSet.getString("dishes"));
                order.setTotalCost(rsSet.getDouble("total_cost"));
                orderList.add(order);
            }
            return orderList;
        }
        catch (SQLException e){
            System.out.println("Error occured while fetching data");
            e.printStackTrace();
            throw e;
        }
    }
	
	public static int getTotalOrderNum() throws ClassNotFoundException, SQLException {
		String sql = "select count(*) from test.order where status < 2";
		int num = 0;
		
		try {
			ResultSet rsSet = DBUtil.dbExecute(sql);
			while (rsSet.next()) {
				num = rsSet.getInt(1);
			}
            
            return num;
        }
		catch (SQLException e){
            System.out.println("Error occured while fetching the data");
            e.printStackTrace();
            throw e;
        }
	}
	
	public static double getTotalOrderAmount() throws ClassNotFoundException, SQLException {
		String sql = "select sum(total_cost) from test.order where status < 2";
		double amount = 0;
		
		try {
            ResultSet rsSet = DBUtil.dbExecute(sql);
            while (rsSet.next()) {
            	amount = rsSet.getDouble(1);
            }
            return amount;
        }
		catch (SQLException e){
            System.out.println("Error occured while fetching the data");
            e.printStackTrace();
            throw e;
        }
	}
	
//	public static void insertOrder(String orderId, String userId, int status, String dishes, String time, double totalCost) throws SQLException, ClassNotFoundException {
//		String sql = "insert into order values(" + orderId + ", '" + userId + ", '" + status + ", '" + dishes + ", '" + time + ", '" + totalCost +")";
//		
//		try {
//			DBUtil.dbExecuteQuery(sql);
//		}
//		catch (SQLException e) {
//			System.out.println("Exception occured while inserting data");
//		}
//	}
	
	public static void updateOrderStatus(String orderId, int status) throws ClassNotFoundException, SQLException {
		String sql = "update test.order set status = " + status + " where order_id = '" + orderId + "'";
		
		try{
            DBUtil.dbExecuteQuery(sql);

        }catch (SQLException e){
            System.out.println("Error occured while updating the record");
            e.printStackTrace();
            throw e;
        }
	}
}
