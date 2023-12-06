package Admin;

import java.io.IOException;

import Customer.MenuList;
import Customer.UserDAO;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AddUserController {
	private Pane selectedPane;
	
	@FXML
	public void return_backUserC(ActionEvent event) throws Exception {
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Button menuListForAdmin = new Button("Menu List");
		Button categoryList = new Button("Category List");
		Button userControl = new Button("User Control");
		Button workStation = new Button("Work Station");
		
		HBox adminMenu = new HBox();
		HBox customerMenu = new HBox();
		
		adminMenu.setSpacing(10);
		adminMenu.getChildren().addAll(
			workStation, menuListForAdmin, categoryList, userControl
		);
		
		
		selectedPane = FXMLLoader.load(getClass().getResource("/Admin/UserControlView.fxml"));
		showAdminScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "User Control");
		
		menuListForAdmin.setOnAction(e -> {
			selectedPane = new MenuList();
			showAdminScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Menu List");
		});
		
		categoryList.setOnAction(e -> {
			selectedPane = new CategoryList();
			showAdminScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Category List");
		});
		
		workStation.setOnAction(e -> {
			try {
				selectedPane = FXMLLoader.load(getClass().getResource("/Admin/AdminOrderView.fxml"));
				showAdminScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Work Station");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		userControl.setOnAction(e -> {
			try {
				selectedPane = FXMLLoader.load(getClass().getResource("/Admin/UserControlView.fxml"));
				showAdminScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "User Control");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
	}
    
    public Pane setMainPane(Pane adminMenu, Pane selectedPane, Pane customerMenu ) {
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(adminMenu);
		mainPane.setCenter(selectedPane);
		mainPane.setBottom(customerMenu);
		BorderPane.setMargin(adminMenu, new Insets(10));
		BorderPane.setMargin(customerMenu, new Insets(10));
		return mainPane;
	}
	
	public void showAdminScene(Stage stage, Pane pane, String title) {
		Scene scene = new Scene(pane, 600, 500);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}

	@FXML public TextField txtuserName;
	@FXML public TextField txtpasscode;
	
	@FXML
	public void registerC(ActionEvent event) throws Exception{
       try {
    	   UserDAO userDAO = new UserDAO();
			userDAO.insertUserData(txtuserName.getText(), txtpasscode.getText());
	        Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Approved！");
            alert.setHeaderText(null);
            alert.setContentText("Nber eats : New Account is created.");
            alert.showAndWait();
            
			return_backUserC(event);
       }catch (Exception e){e.getStackTrace();
           System.out.println("Error while registering and inserting");
       }
   }
}