package Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

import Base.UserData;
import Customer.MenuList;
import Customer.OrderMenu;
import Customer.UserDAO;


public class UserListController {
	private Pane selectedPane;
	
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
    
    @FXML
    public void return_backAd(ActionEvent event) throws Exception {
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

}
