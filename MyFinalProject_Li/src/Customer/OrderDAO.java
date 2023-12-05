package Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import Base.Order;
import DBUtil.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderDAO {
	public static ObservableList<Order> getOrders(String userId)  throws ClassNotFoundException, SQLException {
		String sql = "select * from test.order where user_id='" + userId + "' order by status asc, time desc";
		
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
                order.setOrderId(rsSet.getString("order_id"));System.out.println(order.getOrderId());
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
	
	public static void insertOrder(String orderId, String userId, int status, String dishes, String time, double totalCost) throws SQLException, ClassNotFoundException {
		String sql = "insert into test.order (order_id, user_id, status, dishes, time, total_cost) values('" + orderId + "', '" + userId + "', " + status + ", '" + dishes + "', '" + time + "', " + totalCost + ")";
		
		try {
			DBUtil.dbExecuteQuery(sql);
		}
		catch (SQLException e) {
			System.out.println("Exception occured while inserting data");
		}
	}
	
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
