package application;

import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserDAO {
	private final static String url = "jdbc:mysql://localhost:3306/database?serverTimezone=UTC";

	private final static String username = "root";
	private final static String passcode = "1111";
	Connection connection = null;
	public static int userId = 3;

	 public UserDAO() {
	        establishConnection();
	    }
	 private void establishConnection() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            connection = DriverManager.getConnection(url, username, passcode);
	            System.out.println("Connected to the database");
	        } catch (Exception e) {
	            System.err.println("Error during database connection: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	  public void testConnection() {
	        if (connection != null) {
	            System.out.println("Connection test successful!");
	        } else {
	            System.out.println("Connection test failed!");
	        }
	    }
	  public void insertUserData(String userName, String password) {
	        try {
	        	userId = this.getfinalUserId();

	            String sql = "INSERT INTO db (userId,userName, password) VALUES (?, ?, ?)";
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                statement.setInt(1, userId);
	                statement.setString(2, userName);
	                statement.setString(3, password);
	                
	                int rowsInserted = statement.executeUpdate();
	                if (rowsInserted > 0) {
	                    System.out.println("Data inserted successfully!");
	                } else {
	                    System.out.println("Failed to insert data!");
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Error during data insertion: " + e.getMessage());
	            e.printStackTrace();
	        }
	    }
	  public int getfinalUserId() throws SQLException {
	        int nextUserId = 0;
	        String query = "SELECT MAX(userId) FROM db";
	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(query)) {

	            if (resultSet.next()) {
	                nextUserId = resultSet.getInt(1) + 1;
	            }
	        }
	        return nextUserId;
	    }
	  public boolean findUser(String userName, String password) {
	        try {
	            String sql = "SELECT * FROM db WHERE userName = ? AND password = ?";
	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                statement.setString(1, userName);
	                statement.setString(2, password);

	                try (ResultSet resultSet = statement.executeQuery()) {
	                    return resultSet.next(); // If a row is found, authentication is successful
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("no user " );
	            e.printStackTrace();
	            return false;
	        }
	    }
	  
	  
	  public Map<Integer, String> getalluser() {
	        Map<Integer, String> userMap = new HashMap<>();

	        try {
	            String sql = "SELECT userId, userName FROM db";
	            try (PreparedStatement statement = connection.prepareStatement(sql);
	                 ResultSet resultSet = statement.executeQuery()) {

	                while (resultSet.next()) {
	                    int userId = resultSet.getInt("userId");
	                    String userName = resultSet.getString("userName");
	                    userMap.put(userId, userName);
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Error retrieving user data: " + e.getMessage());
	            e.printStackTrace();
	        }

	        return userMap;
	    }
	  /*public static void main(String[] args) {
	        UserDAO userDAO = new UserDAO();
	        Map<Integer, String> userMap = userDAO.getalluser();

	        // Print the content of userMap
	        for (Map.Entry<Integer, String> entry : userMap.entrySet()) {
	            System.out.println("User ID: " + entry.getKey() + ", User Name: " + entry.getValue());
	        }
	    }*/
	  public int findUserId(String userName, String password) {
		    try {
		        String sql = "SELECT userId FROM db WHERE userName = ? AND password = ?";
		        try (PreparedStatement statement = connection.prepareStatement(sql)) {
		            statement.setString(1, userName);
		            statement.setString(2, password);

		            try (ResultSet resultSet = statement.executeQuery()) {
		                if (resultSet.next()) {
		                    return resultSet.getInt("userId"); 
		                }
                        else {
		                    return -1;
		                }
		            }
		        }
		    } catch (SQLException e) {
		        System.err.println("Error during authentication: " + e.getMessage());
		        e.printStackTrace();
		        
		        
		return -1; 
		    }
		}
	}

