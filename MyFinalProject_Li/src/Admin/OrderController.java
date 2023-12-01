package Admin;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Base.Order;
import Base.OrderDetail;

public class OrderController {
	@FXML private Label totalNum;
	@FXML private Label totalAmount;
	
	@FXML private TableColumn<Order, String> orderID;
	@FXML private TableColumn<Order, String> status;
	@FXML private TableColumn<Order, String> time;
	@FXML private TableColumn<Order, String> details;
	@FXML private TableColumn<Order, String> amount;
	
	@FXML private TableView<Order> orderTable;
	
	@FXML private javafx.scene.control.TextArea resultConsole;
	
	@FXML
	private void initialize() throws Exception {
		DBUtil.DBUtil.dbConnect();
		
		totalNum.setText(Integer.toString(OrderDAO.getTotalOrderNum()));
		totalAmount.setText("$" + Double.toString(OrderDAO.getTotalOrderAmount()));
		
		orderID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getOrderId()));
		status.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStatus()));
		time.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getOrderTime()));
		details.setCellFactory(col -> {
			Button btn = new Button("Show");
			
			TableCell<Order, String> cell = new TableCell<Order, String>() {
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
                        setText(null);
                        setGraphic(null);
                    } 
					else {
						this.setGraphic(btn);
						
						ObservableList<OrderDetail> detailMap = generateDetailData(getTableRow().getItem().getDishesMap());
						
						btn.setOnMouseClicked(e -> {
							VBox dialog = new VBox();
							dialog.setAlignment(Pos.CENTER);
							Stage dialogStage = new Stage();
							dialogStage.setTitle("Order Details");
							
							
							TableView<OrderDetail> detailTable = new TableView<>();
							
							detailTable.setItems(detailMap);
							
							TableColumn<OrderDetail, String> dishName = new TableColumn<>("Item");
							detailTable.getColumns().add(dishName);
							dishName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDishName()));
							dishName.prefWidthProperty().bind(detailTable.widthProperty().divide(2));
							TableColumn<OrderDetail, Integer> quantity = new TableColumn<>("Quantity");
							detailTable.getColumns().add(quantity);
							quantity.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNum()));
							quantity.prefWidthProperty().bind(detailTable.widthProperty().divide(2));
							
							dialog.getChildren().add(detailTable);
							
							Button finishBtn = new Button("Finish");
							finishBtn.setOnMouseClicked(ev ->  {
								try {
									updateOrderStatus(getTableRow().getItem().getOrderId());
									dialogStage.close();
								} catch (ClassNotFoundException | SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							});
							if (getTableRow().getItem().getStatus().equals("In process")) {
								dialog.getChildren().add(finishBtn);
							}
							
							VBox.setMargin(finishBtn, new Insets(10, 0, 10, 0));
							
							Scene dialogScene = new Scene(dialog, 300, 300);
							dialogStage.setScene(dialogScene);
							dialogStage.show();
						});
                    }
				}
			};
			
			cell.setAlignment(Pos.CENTER);
			
			return cell;
		});
		amount.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("$" + cellData.getValue().getTotalCost()));
		
		ObservableList<Order> orderList = OrderDAO.getOrders();
		populateOrderTable(orderList);
	}
	
	private void populateOrderTable(ObservableList<Order> orderList) {
		orderTable.setItems(orderList);
	}
	
	private ObservableList<OrderDetail> generateDetailData(HashMap<String, Integer> map) {
		ObservableList<OrderDetail> alldata = FXCollections.observableArrayList();
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			OrderDetail dataRow = new OrderDetail(entry.getKey(), entry.getValue());
            alldata.add(dataRow);
        }
        return alldata;
	}
	
	private void updateOrderStatus(String orderId) throws ClassNotFoundException, SQLException {
		try {
			OrderDAO.updateOrderStatus(orderId, 1);
//			resultConsole.setText("Data was updated");
			ObservableList<Order> orderList = OrderDAO.getOrders();
			populateOrderTable(orderList);
		}
		catch (SQLException e) {
			System.out.println("Error while updating");
            e.printStackTrace();
            throw e;
		}
	}
}
