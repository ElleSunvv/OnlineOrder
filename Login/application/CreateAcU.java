package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAcU {
	 public void return_backUser(ActionEvent event) throws Exception{
	        try {
	            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
	            Scene log = new Scene(root);
	            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	            window.setScene(log);
	            window.show();

	        }catch (Exception e){
	            System.out.println("Error occured while opening Log page");
	            e.printStackTrace();
	            throw e;
	        }
	    }
	 @FXML 
	 public TextField userName;
	 public TextField passcode;
	 public void register(ActionEvent event) throws Exception{
	        try {
	            UserDAO userDAO = new UserDAO();
				userDAO.insertUserData(userName.getText(), passcode.getText());
				Parent root = FXMLLoader.load(getClass().getResource("usermainpage.fxml"));
	            Scene bbb = new Scene(root);
	            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	            window.setScene(bbb);
	            window.show();
				//此处为跳转到下一个fxml
	        }catch (Exception e){
	            System.out.println("Error while registering and inserting");
	        }
	    }


}
