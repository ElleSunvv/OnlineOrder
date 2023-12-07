package application;

import java.io.IOException;

import Admin.CategoryList;
import Customer.MenuList;
import Customer.OrderMenu;
import Customer.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {
	private Pane selectedPane;
	private boolean isCustomer = false;
	private boolean isAdmin = false;

	@FXML 
	public  void addUserA(ActionEvent event) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Customer/AddUserView.fxml"));
            Scene create = new Scene(root);
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
					isAdmin = true;
				}
				else {
					isCustomer = true;
					UserDAO.userId = userDAO.findUserId(txtuserName.getText(), txtpasscode.getText());
		        }
				loadMainPage(event);
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

	public void loadMainPage(ActionEvent event) throws Exception {
		Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		Button menuListForAdmin = new Button("Menu List");
		Button categoryList = new Button("Category List");
		Button userControl = new Button("User Control");
		Button workStation = new Button("Work Station");
		
		Button menuListForCustomer = new Button("Order Menu");
		Button orderList = new Button("Order List");
		
		HBox adminMenu = new HBox();
		HBox customerMenu = new HBox();
				
		menuListForAdmin.setVisible(isAdmin);
		categoryList.setVisible(isAdmin);
		userControl.setVisible(isAdmin);
		workStation.setVisible(isAdmin);
		
		menuListForCustomer.setVisible(isCustomer);
		orderList.setVisible(isCustomer);
		
		adminMenu.setSpacing(10);
		adminMenu.getChildren().addAll(
			workStation, menuListForAdmin, categoryList, userControl
		);
		
		customerMenu.setSpacing(10);
		customerMenu.getChildren().addAll(menuListForCustomer, orderList);
		
		if (isAdmin) {
			try {
				selectedPane = FXMLLoader.load(getClass().getResource("/Admin/AdminOrderView.fxml"));
				showAdminScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Work Station");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else {
			selectedPane = new OrderMenu();
			showCustomerScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Order List");
		}
		
		menuListForAdmin.setOnAction(e -> {
			selectedPane = new MenuList();
			showAdminScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Menu List");
		});
		
		categoryList.setOnAction(e -> {
			selectedPane = new CategoryList();
			showAdminScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Category List");
		});
		
		menuListForCustomer.setOnAction(e -> {
			selectedPane = new OrderMenu();
			showCustomerScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Order Menu");
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
		
		OrderMenu.getContinuedButton().setOnAction(e -> {
			showSceneBuilder(primaryStage, "/Customer/OrderCartView.fxml", "Cart", 400, 600);
		});
		
		orderList.setOnAction(e -> {
			try {
				selectedPane = FXMLLoader.load(getClass().getResource("/Customer/OrderListView.fxml"));
				showCustomerScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Order List");
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
	
	public void showCustomerScene(Stage stage, Pane pane, String title) {
		Scene scene = new Scene(pane, 400, 600);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showSceneBuilder(Stage stage, String fxmlLink, String title, int width, int height) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxmlLink));
			Scene scene = new Scene(root, width, height);
			stage.setTitle(title);
			stage.setScene(scene);
			stage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}

