package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	private Pane selectedPane = new MenuList();
	private String title = "Menu List";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Button menuListForAdmin = new Button("Menu List");
		Button categoryList = new Button("Category List");
		Button menuListForCustomer = new Button("Order Menu");
		Button orderList = new Button("Order List");
		
		VBox navMenu = new VBox();
		
		navMenu.setSpacing(10);
		navMenu.getChildren().addAll(
				menuListForAdmin, categoryList, menuListForCustomer, orderList
				);
		
		showAdminScene(primaryStage, setMainPane(navMenu, selectedPane), title);
		
		menuListForAdmin.setOnAction(e -> {
			title = "Menu List";
			selectedPane = new MenuList();
			showAdminScene(primaryStage, setMainPane(navMenu, selectedPane), title);
		});
		
		categoryList.setOnAction(e -> {
			title = "Category List";
			selectedPane = new CategoryList();
			showAdminScene(primaryStage, setMainPane(navMenu, selectedPane), title);
		});
		
		menuListForCustomer.setOnAction(e -> {
			title = "Order Menu";
			selectedPane = new OrderMenu();
			showCustomerScene(primaryStage, setMainPane(navMenu, selectedPane), title);
		});
		
		orderList.setOnAction(e -> {
			try {
				Parent root = 
						FXMLLoader.load(getClass().getResource("Main.fxml"));
				Scene scene = new Scene(root, 400, 400);
				primaryStage.setTitle("Order List");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			
		});
	}
	
	public Pane setMainPane(Pane navMenu, Pane selectedPane) {
		HBox mainPane = new HBox();
		mainPane.getChildren().addAll(navMenu, selectedPane);
		HBox.setMargin(navMenu, new Insets(10));
		return mainPane;
	}
	
	public void showAdminScene(Stage stage, Pane pane, String title) {
		Scene scene = new Scene(pane, 600, 500);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showCustomerScene(Stage stage, Pane pane, String title) {
		Scene scene = new Scene(pane, 400, 500);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public void showSceneBuilder(Stage stage, Parent root, String title) {
		Scene scene = new Scene(root, 400, 500);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}






 


