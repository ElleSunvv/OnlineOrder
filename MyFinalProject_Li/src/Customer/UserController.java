package Customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserController {
	private Pane selectedPane;
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
			Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
			Button menuListForCustomer = new Button("Order Menu");
			Button orderList = new Button("Order List");
			HBox customerMenu = new HBox();
			Pane customerOrderListPane = FXMLLoader.load(getClass().getResource("/Customer/OrderListView.fxml"));
			customerMenu.setSpacing(10);
			customerMenu.getChildren().addAll(menuListForCustomer, orderList);
			selectedPane = new OrderMenu();
			showCustomerScene(primaryStage, setMainPane(new Pane(), selectedPane, customerMenu), "Order List");
			menuListForCustomer.setOnAction(e -> {
				selectedPane = new OrderMenu();
				showCustomerScene(primaryStage, setMainPane(new Pane(), selectedPane, customerMenu), "Order Menu");
			});
			OrderMenu.getContinuedButton().setOnAction(e -> {
				showSceneBuilder(primaryStage, "/Customer/OrderCartView.fxml", "Cart", 400, 600);
			});
			
			orderList.setOnAction(e -> {
				selectedPane = customerOrderListPane;
				showCustomerScene(primaryStage, setMainPane(new Pane(), selectedPane, customerMenu), "Order List");
			});
			
        }catch (Exception e){
            System.out.println("Error while registering and inserting");
        }
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