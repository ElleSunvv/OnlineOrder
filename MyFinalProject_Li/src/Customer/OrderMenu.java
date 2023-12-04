package Customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Base.Category;
import Base.DishItem;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class OrderMenu extends BorderPane {
	private ScrollPane scrollPane = new ScrollPane();
	private TabPane tabPane = new TabPane();
	private ArrayList<Category> categoryItems = MenuDAO.getCategoryItemsFromDatabase();
	private ArrayList<DishItem> dishItems = MenuDAO.getDishItemsFromDatabase();
	private HBox goToPay = new HBox();
	private static Button continueButton = new Button("Continue");
	private static Integer totalNum = 0;
	private static Double totalPrice = 0.0;
	private static Text showTotalNum = new Text("0");
	private static Text showTotalPrice = new Text("0.0");
	private static HashMap<DishItem, Integer> addedDishItems = new HashMap<DishItem, Integer>();

	public OrderMenu() {
		for (Category category: categoryItems) {
			Tab tab = new Tab(category.getCategoryName());
			tabPane.getTabs().add(tab);
			tab.setClosable(false);
			
			VBox dishPaneForCategory = new VBox();
			for (DishItem dishItem: dishItems) {
				if (dishItem.getCategoryName().equals(category.getCategoryName())) {
					DishItemPane dishItemPane = new DishItemPane(dishItem);
					dishPaneForCategory.getChildren().add(dishItemPane);
					VBox.setMargin(dishItemPane, new Insets(5));
				}
			}
			tab.setContent(dishPaneForCategory);
		}

		scrollPane.setContent(tabPane);
		SimpleDoubleProperty scrollValue = new SimpleDoubleProperty();
        scrollValue.bindBidirectional(scrollPane.vvalueProperty());
		
		goToPay.getChildren().addAll(showTotalNum, new Label(" items: $ "), showTotalPrice, continueButton);
		
		showTotalNum.setFont(new Font(20));
		showTotalPrice.setFont(new Font(20));
		showTotalNum.setFill(Color.RED);
		showTotalPrice.setFill(Color.RED);
		
//		go to order cart page TBD
//		continueButton.setOnAction(e -> {
//			setIsContinued(true);
////			for(DishItem dishItem: OrderMenu.getAddedDishItems().keySet()) {
////				System.out.println("dishItem: " + dishItem.getDishName() + 
////						", unit price is: " + dishItem.getUnitPrice() + 
////						", quantity is: " + OrderMenu.getAddedDishItems().get(dishItem));
////			}
//		});
//		
		setCenter(scrollPane);
		setBottom(goToPay);
		setMargin(goToPay, new Insets(10));
	}
	
	public static void setShowTotalNum(Integer num) {
		showTotalNum.setText(num.toString());
	}
	
	public static void setShowTotalPrice(Double price) {
		showTotalPrice.setText(totalPrice.toString());
	}
	
	public static int getTotalNum() {
		totalNum = 0;
		for (Map.Entry<DishItem, Integer> entry : addedDishItems.entrySet()) {
			totalNum += entry.getValue();
		}
		return totalNum;
	}
	
	public static Double getTotalPrice() {
		totalPrice = 0.0;
		for (Map.Entry<DishItem, Integer> entry : addedDishItems.entrySet()) {
			totalPrice += entry.getValue() * entry.getKey().getUnitPrice();
		}
		return totalPrice;
	}
	
	public static void setAddedDishItems(DishItem dishItem) {
		DishItem d = null;
		for (Map.Entry<DishItem, Integer> entry : addedDishItems.entrySet()) {
			if (entry.getKey().getDishId() == dishItem.getDishId()) {
				d = entry.getKey();
			}
		}
		if (d != null) {
			int currentQuantity = addedDishItems.get(d);
			addedDishItems.put(d, currentQuantity + 1);
		} else {
			addedDishItems.put(dishItem, 1);
		}	
	}
	
	public static HashMap<DishItem, Integer> getAddedDishItems(){
		return addedDishItems;
	}

	public static Button getContinuedButton() {
		return continueButton;
	}
	
	public static void updateDishItems(HashMap<DishItem, Integer> dishMap) {
		addedDishItems = dishMap;
		setShowTotalNum(OrderMenu.getTotalNum());
		setShowTotalPrice(OrderMenu.getTotalPrice());
	}
}

class DishItemPane extends GridPane{
	private ImageView imageView = new ImageView();
	private Text dishItemNameText = new Text();
	private Text unitPriceText = new Text();
	private Button addDishButton = new Button("+");
	private ColumnConstraints column1 = new ColumnConstraints();
	private ColumnConstraints column2 = new ColumnConstraints();
	private ColumnConstraints column3 = new ColumnConstraints();
	
	public DishItemPane(DishItem dishItem) {
		for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Region cell = new Region();
                cell.setStyle("-fx-background-color: lightgray;");
                cell.setMinSize(10, 10); 
                add(cell, i, j);
            }
        }
		imageView.setImage(new Image(dishItem.getImageUri()));
		imageView.setFitHeight(60);
		imageView.setFitWidth(60);
		setRowSpan(imageView, 3);
		setColumnSpan(imageView, 3);  
		
		dishItemNameText.setText(dishItem.getDishName());
		
		unitPriceText.setText("$" + dishItem.getUnitPrice().toString());
		
		column1.setPercentWidth(21);
		column2.setPercentWidth(72);
		column3.setPercentWidth(7);
		getColumnConstraints().addAll(column1, column2, column3);
		
		add(imageView, 0, 0);
		add(dishItemNameText, 1, 0);
		add(unitPriceText, 1, 2);
		add(addDishButton, 2, 0);
		
		addDishButton.setOnAction(e -> {
			OrderMenu.setAddedDishItems(dishItem);
			OrderMenu.setShowTotalNum(OrderMenu.getTotalNum());
			OrderMenu.setShowTotalPrice(OrderMenu.getTotalPrice());
		});
	}
}