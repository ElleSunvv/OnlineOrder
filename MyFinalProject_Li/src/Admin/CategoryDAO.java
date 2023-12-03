package Admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Base.Category;
import DBUtil.DBUtil;

public class CategoryDAO {
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
	
	public static boolean isCategoryExisted(Category category) {
    	boolean isCategoryExisted = false;
    	for (Category existedCategory: getCategoryItemsFromDatabase()) {
    		if (category.getCategoryName().equals(existedCategory.getCategoryName())) {
    			isCategoryExisted = true;
    			break;
    		}
    	}
    	return isCategoryExisted;
    }
}
