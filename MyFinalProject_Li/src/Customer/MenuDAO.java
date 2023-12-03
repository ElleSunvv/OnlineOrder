package Customer;

import java.sql.*;
import java.util.ArrayList;

import Base.Category;
import Base.DishItem;
import DBUtil.DBUtil;

public class MenuDAO {
	public static Connection getConnection() {
		try {
			System.out.println("start connecting");
			return DriverManager.getConnection(DBUtil.getDBConnStr(),DBUtil.getDBUserName(), DBUtil.getDBPassWord());
		} catch(SQLException e) {
			e.printStackTrace();  
			throw new RuntimeException("Success connect Mysql server!");
		}
	}
	
	public static ArrayList<Category> getCategoryItemsFromDatabase() {
		ArrayList<Category> categoryItems = new ArrayList<>();
		try (Connection connect = getConnection();
    		Statement stmt = connect.createStatement();  
    		ResultSet rs = stmt.executeQuery("select * from test.category");){
			while (rs.next()) {
				Integer categoryID = rs.getInt(("categoryID"));
			    String categoryName = rs.getString("categoryName");
				categoryItems.add(new Category(categoryID, categoryName));
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return categoryItems;
	}
	
	public static ArrayList<DishItem> getDishItemsFromDatabase() {
		ArrayList<DishItem> dishItems = new ArrayList<>();
    	try (Connection connect = getConnection();
    		Statement stmt = connect.createStatement();  
    		ResultSet rs = stmt.executeQuery(
    				"select * from test.dishItem join test.category "
    				+ "on test.dishItem.categoryID = test.category.categoryID");
    			){
			while (rs.next()) {
				Integer dishID = rs.getInt("dishID");
			    String dishName = rs.getString("dishName");
			    Double unitPrice = rs.getDouble("unitPrice");
			    String imageURI = rs.getString("imageURI");
			    Integer categoryID = rs.getInt("categoryID");
			    String categoryName = rs.getString("categoryName");
				dishItems.add(new DishItem(new Category(categoryID, categoryName), dishID, dishName, unitPrice, imageURI));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dishItems;
	}

	public static boolean isDishItemExisted(DishItem dishItem) {
		boolean isDishItemExisted = false;
		for (DishItem existedDishItem: getDishItemsFromDatabase()) {
			if (dishItem.getDishName().equals(existedDishItem.getDishName())) {
				isDishItemExisted = true;
				break;
			}
		}
		return isDishItemExisted;
    }
}
