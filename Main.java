package application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import java.util.Map;
import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene userlist = new Scene(root);
            primaryStage.setTitle("Nber Eats");
            primaryStage.setScene(userlist);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Error occurred");
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
    	 UserDAO user = new UserDAO();
    	 user.testConnection();
        launch(args);
        /*UserDAO userDAO = new UserDAO();
         
        Map<Integer, String> userMap = userDAO.getalluser();

        // Print the content of userMap
        for (Map.Entry<Integer, String> entry : userMap.entrySet()) {
            System.out.println("User ID: " + entry.getKey() + ", User Name: " + entry.getValue());
        }
       */
     
       /* UserDAO userDAO = new UserDAO();
        
        int a = userDAO.findUserId("Admin","aaa");
        System.out.println(a); */      
   
}
}