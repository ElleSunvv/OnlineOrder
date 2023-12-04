package application;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CreateAc1 {
	    @FXML private TextField userName;
	    @FXML private TextField passcode;
@FXML	   public void return_backUserC(ActionEvent event) throws Exception{
	        try {
	            Parent root = FXMLLoader.load(getClass().getResource("UserControl.fxml"));
	            Scene AdUc = new Scene(root);
	            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
	            window.setScene(AdUc);
	            window.show();

	        }catch (Exception e){
	            System.out.println("Error occured while opening Log page");
	            e.printStackTrace();
	            throw e;
	        }
	    }

	   
@FXML 
public TextField txtuserName;
public TextField txtpasscode;
public void registerC(ActionEvent event) throws Exception{
       try {
	        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Approved！");
            alert.setHeaderText(null);
            alert.setContentText("Nber eats : New Account is created.");
            alert.showAndWait();
            UserDAO userDAO = new UserDAO();
			userDAO.insertUserData(txtuserName.getText(), txtpasscode.getText());
			Parent root = FXMLLoader.load(getClass().getResource("UserControl.fxml"));
           Scene bbb = new Scene(root);
           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
           window.setScene(bbb);
           window.show();
			//此处为跳转回控制用户
       }catch (Exception e){
           System.out.println("Error while registering and inserting");
       }
   }
}
