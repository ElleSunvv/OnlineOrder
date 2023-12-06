package Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UserController {
	 public void return_backUser(ActionEvent event) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/LoginView.fxml"));
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
	 public void register(ActionEvent event) throws Exception {
        try {
            UserDAO userDAO = new UserDAO();
			userDAO.insertUserData(userName.getText(), passcode.getText());
			Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Approved！");
            alert.setHeaderText(null);
            alert.setContentText("Nber eats : Your Account is created.");
            alert.showAndWait();
            return_backUser(event);
			
        }catch (Exception e){
            System.out.println("Error while registering and inserting");
        }
	}
}