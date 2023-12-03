package Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.Map;

import Base.UserData;
import Customer.UserDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class UserListController {
	    @FXML
	    private TableView<UserData> tableView;

	    @FXML
	    private TableColumn<UserData, Integer> userIdColumn;

	    @FXML
	    private TableColumn<UserData, String> userNameColumn;

	    public void initialize() {
	        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
	        userNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
	        UserDAO userDAO = new UserDAO();
	        Map<Integer, String> userMap = userDAO.getalluser();
	        displayUserMap(userMap);
	    }

	    public void displayUserMap(Map<Integer, String> userMap) {
	        ObservableList<UserData> userDataList = FXCollections.observableArrayList();
	        
	        for (Map.Entry<Integer, String> entry : userMap.entrySet()) {
	            userDataList.add(new UserData(entry.getKey(), entry.getValue()));
	        }

	        tableView.setItems(userDataList);
	    }
	    

}
