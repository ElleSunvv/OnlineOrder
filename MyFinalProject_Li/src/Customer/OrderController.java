package Customer;

import Customer.OrderDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Base.Order;
import Base.OrderDetail;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class OrderController {
	@FXML private ImageView user_img;
	@FXML private ListView<Order> order_list = new ListView<>();
	private String user_id = "1ee8464a-8bc9-11ee-9b12-778b54051971";
	
	@FXML
	private void initialize() throws Exception {
		DBUtil.DBUtil.dbConnect();
		
		refreshData();
	}
	
	private void refreshData() throws Exception {
		ObservableList<Order> dataList = OrderDAO.getOrders(user_id);
		order_list.setItems(dataList);
		
		order_list.setCellFactory(new Callback<ListView<Order>, ListCell<Order>>() {
			@Override
			public ListCell<Order> call(ListView<Order> order) {
				ListCell<Order> cell = new ListCell<Order>() {
					@Override
					public void updateItem(Order item, boolean empty) {
						super.updateItem(item, empty);
						
						if (empty == false) {
							GridPane pane = new GridPane();
							pane.setPadding(new Insets(10));
							pane.setHgap(5);
							pane.setVgap(5);
							
							Label order_id = new Label("#" + item.getOrderId());
							pane.add(order_id, 0, 0);
							
							Button statusBtn = new Button(item.getStatus());
							statusBtn.setOnMouseClicked(e -> {
								if (item.getStatus() == "In process") {
									try {
										setOrderCanceled(item);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							});
							pane.add(statusBtn, 1, 0);
							
							TableView<OrderDetail> dishes_table = new TableView<>();
							ObservableList<OrderDetail> detailMap = generateDetailData(item.getDishesMap());
							dishes_table.setItems(detailMap);
							dishes_table.setMinWidth(300);
							dishes_table.setMaxWidth(300);
							
							TableColumn<OrderDetail, String> dishName = new TableColumn<>("Item");
							dishes_table.getColumns().add(dishName);
							dishName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDishName()));
							dishName.prefWidthProperty().bind(dishes_table.widthProperty().divide(2));
							TableColumn<OrderDetail, Integer> quantity = new TableColumn<>("Quantity");
							dishes_table.getColumns().add(quantity);
							quantity.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNum()));
							quantity.prefWidthProperty().bind(dishes_table.widthProperty().divide(2));
							
							pane.add(dishes_table, 0, 1);
							
							Label total_cost = new Label("Total: $" + item.getTotalCost());
							pane.add(total_cost, 0, 2);
							
							Label time = new Label("Order Placed: " + item.getOrderTime());
							pane.add(time, 0, 3);
							
							this.setGraphic(pane);
						}
					}
				};
				return cell;
			}
		});
	}
	
	private void setOrderCanceled(Order item) throws Exception {
		try {
			Customer.OrderDAO.updateOrderStatus(item.getOrderId(), 2);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			refreshData();
		}
	}
	
	private ObservableList<OrderDetail> generateDetailData(HashMap<String, Integer> map) {
		ObservableList<OrderDetail> alldata = FXCollections.observableArrayList();
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			OrderDetail dataRow = new OrderDetail(entry.getKey(), entry.getValue());
            alldata.add(dataRow);
        }
        return alldata;
	}
}
