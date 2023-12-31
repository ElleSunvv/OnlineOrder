package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	private Pane selectedPane = new MenuList();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button menuListForAdmin = new Button("Menu List");
		Button categoryList = new Button("Category List");
		Button userList = new Button("User List");
		Button workStation = new Button("Work Station");
		
		Button menuListForCustomer = new Button("Order Menu");
		Button orderList = new Button("Order List");
//		To add all menu buttons
		
		HBox adminMenu = new HBox();
		HBox customerMenu = new HBox();
		
//	-----	isCustomer and isAdmin value TBD;
		boolean isCustomer = true;
		boolean isAdmin = true;
		
		menuListForAdmin.setVisible(isAdmin);
		categoryList.setVisible(isAdmin);
		userList.setVisible(isAdmin);
		workStation.setVisible(isAdmin);
		
		menuListForCustomer.setVisible(isCustomer);
		orderList.setVisible(isCustomer);
		
		adminMenu.setSpacing(10);
		adminMenu.getChildren().addAll(
				workStation, menuListForAdmin, categoryList, userList
				);
		
		customerMenu.setSpacing(10);
		customerMenu.getChildren().addAll(menuListForCustomer, orderList);
		
		showAdminScene(primaryStage, setMainPane(adminMenu, selectedPane, customerMenu), "Menu List");
		
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
			showSceneBuilder(primaryStage, "Main.fxml", "Work Station");
		});
		
		userList.setOnAction(e -> {
			showSceneBuilder(primaryStage, "Main.fxml", "User List");
		});
		
		OrderMenu.getContinuedButton().setOnAction(e -> {
			showSceneBuilder(primaryStage, "Main.fxml", "User List");
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
		Scene scene = new Scene(pane, 300, 500);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showSceneBuilder(Stage stage, String fxmlLink, String title) {
		try {
			Parent root = 
					FXMLLoader.load(getClass().getResource(fxmlLink));
			Scene scene = new Scene(root, 600, 500);
			stage.setTitle(title);
			stage.setScene(scene);
			stage.show();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}






 


