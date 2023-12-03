package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;

import javafx.scene.control.Button;



public class UserControl {
@FXML private AnchorPane expansionPanel;
@FXML private TextField username;
@FXML private TextField password;
@FXML private TextArea resultConcole;
@FXML private Button adduser;
@FXML private Button userlist;


@FXML public  void addUserAc(ActionEvent event) throws Exception {
	        try {
	            Parent root = FXMLLoader.load(getClass().getResource("adduserAc.fxml"));
	            Scene create = new Scene(root);
	            //gets stage information
	            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	            window.setScene(create);
	            window.show();

	        }catch (Exception e){
	            System.out.println("Error occured while opening Creating Account");
	            e.printStackTrace();
	            throw e;
	        }

}
@FXML public void userList(ActionEvent event) throws Exception {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("UserList.fxml"));
        Scene userlist = new Scene(root);
        //gets stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(userlist);
        window.show();

    }catch (Exception e){
        System.out.println("Error occured while opening User List");
        e.printStackTrace();
        throw e;
    }

}
@FXML public  void backtoAdminPage(ActionEvent event) throws Exception {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("AdminControlmain.fxml"));
        Scene create = new Scene(root);
        //gets stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(create);
        window.show();

    }catch (Exception e){
        System.out.println("Error occured ");
        e.printStackTrace();
        throw e;
    }
}
}
