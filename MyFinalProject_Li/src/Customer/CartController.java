package Customer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Base.DishItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CartController {
	@FXML private ListView<DishDetail> dish_list;
	@FXML private Label total_price;
	private String user_id = String.valueOf(UserDAO.userId);
	private HashMap<DishItem, Integer> dish_map = new HashMap<>();
	private ObservableList<DishDetail> data_list;
	
	@FXML
	private void initialize() throws Exception {
		DBUtil.DBUtil.dbConnect();
		
		dish_map = OrderMenu.getAddedDishItems();
		refreshData();
	}
	
	private void refreshData() {
		total_price.setText("$" + String.valueOf(getTotalCost()));
		
		data_list = generateData(dish_map);
		dish_list.setItems(data_list);
		
		dish_list.setCellFactory(new Callback<ListView<DishDetail>, ListCell<DishDetail>>() {
			@Override
			public ListCell<DishDetail> call(ListView<DishDetail> dish) {
				ListCell<DishDetail> cell = new ListCell<DishDetail>() {
					@Override
					public void updateItem(DishDetail item, boolean empty) {
						super.updateItem(item, empty);
						
						if (empty == false) {
							GridPane pane = new GridPane();
							pane.setPadding(new Insets(10));
							pane.setVgap(5);
							pane.setHgap(5);
							
							ImageView pic = new ImageView(item.getItem().getImageUri());
							pic.setFitHeight(50);
							pic.setFitWidth(50);
							pane.add(pic, 0, 0);
							
							Label name = new Label(item.getItem().getDishName());
							pane.add(name, 1, 0);
							
							Label price  = new Label("$" + item.getItem().getUnitPrice());
							pane.add(price, 1, 1);
							
							Label num = new Label(String.valueOf(item.getNum()));
							
							Button minus = new Button("-");
							minus.setOnMouseClicked(e -> {
								updateDishMap(item, item.getNum() - 1);
							});
							
							Button plus = new Button("+");
							plus.setOnMouseClicked(e -> {
								updateDishMap(item, item.getNum() + 1);
							});
							
							pane.add(minus, 2, 0);
							pane.add(num, 3, 0);
							pane.add(plus, 4, 0);
							
							this.setGraphic(pane);
						}
					}
				};
				return cell;
			}
		});
	}
	
	private double getTotalCost() {
		double total_cost = 0;
		for (Map.Entry<DishItem, Integer> entry : dish_map.entrySet()) {
			total_cost += entry.getKey().getUnitPrice() * entry.getValue();
		}
		return total_cost;
	}
	
	private void updateDishMap(DishDetail d, int n) {
		if (n == 0) {
			dish_map.remove(d.getItem());
		}
		else {
			dish_map.put(d.getItem(), n);
		}
		OrderMenu.updateDishItems(dish_map);
		refreshData();
	}
	
	private void showCustomerScene(Stage stage, Pane pane, String title) {
		Scene scene = new Scene(pane, 400, 600);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}
	
	private Pane setMainPane(Pane adminMenu, Pane selectedPane, Pane customerMenu ) {
		BorderPane mainPane = new BorderPane();
		mainPane.setTop(adminMenu);
		mainPane.setCenter(selectedPane);
		mainPane.setBottom(customerMenu);
		BorderPane.setMargin(adminMenu, new Insets(10));
		BorderPane.setMargin(customerMenu, new Insets(10));
		return mainPane;
	}
	
	@FXML
	private void backToMenu(ActionEvent event) throws Exception {
		try {
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			HBox customerMenu = new HBox();
			Button menuListForCustomer = new Button("Order Menu");
			Button orderList = new Button("Order List");
			Pane customerOrderListPane = FXMLLoader.load(getClass().getResource("/Customer/OrderListView.fxml"));
			orderList.setOnAction(e -> {
				showCustomerScene(window, setMainPane(new Pane(), customerOrderListPane, customerMenu), "Order List");
			});
			menuListForCustomer.setOnAction(e -> {
				showCustomerScene(window, setMainPane(new Pane(), new OrderMenu(), customerMenu), "Order Menu");
			});
			customerMenu.setSpacing(10);
			customerMenu.getChildren().addAll(menuListForCustomer, orderList);
			showCustomerScene(window, setMainPane(new Pane(), new OrderMenu(), customerMenu), "Order Menu");
        }
		catch (Exception e){
            System.out.println("Error occured while opening Menu page");
            e.printStackTrace();
            throw e;
        }
	}
	
	@FXML
	private void goToOrderList(ActionEvent event) throws Exception {
		try {
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			HBox customerMenu = new HBox();
			Button menuListForCustomer = new Button("Order Menu");
			Button orderList = new Button("Order List");
			Pane customerOrderListPane = FXMLLoader.load(getClass().getResource("/Customer/OrderListView.fxml"));
			orderList.setOnAction(e -> {
				showCustomerScene(window, setMainPane(new Pane(), customerOrderListPane, customerMenu), "Order List");
			});
			menuListForCustomer.setOnAction(e -> {
				showCustomerScene(window, setMainPane(new Pane(), new OrderMenu(), customerMenu), "Order Menu");
			});
			customerMenu.setSpacing(10);
			customerMenu.getChildren().addAll(menuListForCustomer, orderList);
			showCustomerScene(window, setMainPane(new Pane(), customerOrderListPane, customerMenu), "Order List");
        }
		catch (Exception e){
            System.out.println("Error occured while opening OrderList page");
            e.printStackTrace();
            throw e;
        }
	}
	
	private ObservableList<DishDetail> generateData(HashMap<DishItem, Integer> map) {
		ObservableList<DishDetail> alldata = FXCollections.observableArrayList();
		
		for (Map.Entry<DishItem, Integer> entry : map.entrySet()) {System.out.println(entry.getKey().toString());
			DishDetail dataRow = new DishDetail(entry.getKey(), entry.getValue());
            alldata.add(dataRow);
        }
        return alldata;
	}
	
	@FXML
	private void addOrder(ActionEvent event) throws Exception {
		StringBuffer dishes = new StringBuffer();
		for (Map.Entry<DishItem, Integer> entry : dish_map.entrySet()) {
			if (dishes.length() > 0) {
				dishes.append(",");
			}
			dishes.append(entry.getKey().getDishName() + "*" + entry.getValue());
		}
		
		Date now = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String time = formatter.format(now);
	    
	    double total_cost = getTotalCost();
	    
		try {
			Customer.OrderDAO.insertOrder(UUID.randomUUID().toString(), user_id, 0, dishes.toString(), time, total_cost);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			goToOrderList(event);
		}
	}
	
	private class DishDetail {
		private DishItem item;
		private int num;
		
		DishDetail(DishItem item, int num) {
			this.item = item;
			this.num = num;
		}
		
		DishItem getItem() {
			return this.item;
		}
		
		int getNum() {
			return this.num;
		}
	}
}
