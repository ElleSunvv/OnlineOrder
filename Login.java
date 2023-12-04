package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class Login {

@FXML public  void addUserA(ActionEvent event) throws Exception {
	        try {
	            Parent root = FXMLLoader.load(getClass().getResource("adduserA.fxml"));
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
@FXML 
public TextField txtuserName;
public TextField txtpasscode;
public void login(ActionEvent event) throws Exception{
       try {
           UserDAO userDAO = new UserDAO();
			if(userDAO.findUser(txtuserName.getText(), txtpasscode.getText())) {
				if("admin".equals(txtuserName.getText()) && "aaa".equals(txtpasscode.getText())) {
					//System.out.println("dogdogdog");
					Parent root = FXMLLoader.load(getClass().getResource("AdminControlmain.fxml"));
			           Scene adminCM = new Scene(root);
			           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			           window.setScene(adminCM);
			           window.show();
				}
				else {
					//System.out.println("catcatcat customer");
				    Parent root = FXMLLoader.load(getClass().getResource("usermainpage.fxml"));
		            Scene adminU = new Scene(root);
		            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		            window.setScene(adminU);
		            window.show();}
			}
			else {
				   Alert alert = new Alert(AlertType.INFORMATION);
		            alert.setTitle("Error");
		            alert.setHeaderText(null);
		            alert.setContentText("Nber eats : Invalid username or password.");
		            alert.showAndWait();
			}
		
       }catch (Exception e){
           System.out.println("Error while registering and inserting");
       }
   }


}
