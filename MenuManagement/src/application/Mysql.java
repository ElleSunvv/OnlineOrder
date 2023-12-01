package application;

import java.sql.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Mysql {

	public Mysql() {
		try 
        {  
             Class.forName("com.mysql.cj.jdbc.Driver");     //加载MYSQL JDBC驱动程序      
             System.out.println("Success loading Mysql Driver!");  
         }  
         catch (Exception e) 
        {  
              System.out.print("Error loading Mysql Driver!");  
              e.printStackTrace();  
        }  
	}
       
	public static Connection getConnection() {
		try {
			System.out.println("start connecting");
			return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mySql","root","54sl54SL!!");
		} catch(SQLException e) {
			e.printStackTrace();  
			throw new RuntimeException("Success connect Mysql server!");
		}
	}
	
	public static ArrayList<Category> getCategoryItemsFromDatabase() {
		ArrayList<Category> categoryItems = new ArrayList<>();
		try (Connection connect = Mysql.getConnection();
    		Statement stmt = connect.createStatement();  
    		ResultSet rs = stmt.executeQuery("select * from onlineOrderDatabase.category");){
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
    	try (Connection connect = Mysql.getConnection();
    		Statement stmt = connect.createStatement();  
    		ResultSet rs = stmt.executeQuery(
    				"select * from onlineOrderDatabase.dishItem join onlineOrderDatabase.category "
    				+ "on onlineOrderDatabase.dishItem.categoryID = onlineOrderDatabase.category.categoryID");
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

