package application;
	
import DBUtil.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
//		Parent root = FXMLLoader.load(getClass().getResource("/Admin/AdminOrderView.fxml"));
		Parent root = FXMLLoader.load(getClass().getResource("/Customer/OrderListView.fxml"));
//		Parent root = FXMLLoader.load(getClass().getResource("/Customer/OrderCartView.fxml"));
		Scene scene = new Scene(root);
		DBUtil.setStage(primaryStage);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
