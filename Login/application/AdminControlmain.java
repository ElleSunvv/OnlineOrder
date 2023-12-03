package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminControlmain {
	@FXML	   public void skipusercontrol(ActionEvent event) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("UserControl.fxml"));
            Scene AdUc = new Scene(root);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(AdUc);
            window.show();

        }catch (Exception e){
            System.out.println("Error occured");
            e.printStackTrace();
            throw e;
        }
    }
	@FXML	   public void skiporder(ActionEvent event) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Ordercontrol.fxml"));//orderlist.fxml
            Scene AdUc = new Scene(root);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(AdUc);
            window.show();

        }catch (Exception e){
            System.out.println("Error occured ");
            e.printStackTrace();
            throw e;
        }
    }
	@FXML	   public void skipdishcontrol(ActionEvent event) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("XXXXXXXX.fxml"));//dish.fxml
            Scene AdUc = new Scene(root);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(AdUc);
            window.show();

        }catch (Exception e){
            System.out.println("Error occured ");
            e.printStackTrace();
            throw e;
        }
    }

}
