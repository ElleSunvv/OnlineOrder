package DBUtil;

import java.sql.*;

import javafx.stage.Stage;

public class DBUtil {
	private static Stage stage;
    private static Connection connection = null;
    private static final String connStr = "jdbc:mysql://127.0.0.1:3306/mySql?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    private static final String userName = "root";
    private static final String passWord = "password";
    
    //database connection method
    public static void dbConnect() throws SQLException, ClassNotFoundException{
    	try 
        {  
             Class.forName("com.mysql.jdbc.Driver");
             System.out.println("Success loading Mysql Driver!");  
         }  
         catch (Exception e) 
        {  
              System.out.print("Error loading Mysql Driver!");  
              e.printStackTrace();  
        }  
    	
        try {
            connection = DriverManager.getConnection(connStr, userName, passWord);
            System.out.println("Success connect Mysql server!");  
        }
        catch (SQLException e){
            System.out.println("Connection failed");
            throw e;
        }
    }
    
    //disconnecting database method
    public static void dbDisconnect() throws SQLException{
        try {
            if(connection!=null&&!connection.isClosed()){
                connection.close();
            }
        }catch (Exception e){
            throw e;
        }
    }
    
    //for insert update delete from db
    public static void dbExecuteQuery(String sqlStmt) throws SQLException, ClassNotFoundException{
        Statement stmt = null;
        try{
//            dbConnect();
            stmt = connection.createStatement();
            stmt.executeUpdate(sqlStmt);
        }catch (SQLException e){
            System.out.println("Problem occured at dbExecuteQuery "+e);
            e.printStackTrace();
            throw e;

        }finally {
            if(stmt!=null){
                stmt.close();
            }
//            dbDisconnect();
        }
    }
    
  //for getting data from database(selects)
    public static ResultSet dbExecute(String sqlQuery) throws ClassNotFoundException, SQLException {
        Statement stmt = null;
        ResultSet rs = null;
//        CachedRowSetImpl crs = null;	//缓存

        try {
//            dbConnect();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sqlQuery);
//            crs = new CachedRowSetImpl();
//            crs.populate(rs);

        } catch (SQLException e) {
            System.out.println("Problem occured in dbExecute " + e);
            throw e;
        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//            if (stmt != null) {
//                stmt.close();
//            }
//            dbDisconnect();
        }
        return rs;
    }
    
    public static String getDBConnStr() {
    	return connStr;
    }
    
    public static String getDBUserName() {
    	return userName;
    }
    
    public static String getDBPassWord() {
    	return passWord;
    }
    
    public static void setStage(Stage stage) {
        DBUtil.stage = stage;
    }

    public static Stage getStage() {
        return stage;
    }
}
