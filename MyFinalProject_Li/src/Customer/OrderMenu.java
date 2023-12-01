package Customer;

import java.util.ArrayList;
import java.util.HashMap;

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
	private ArrayList<Category> categoryItems = Mysql.getCategoryItemsFromDatabase();
	private ArrayList<DishItem> dishItems = Mysql.getDishItemsFromDatabase();
	private HBox goToPay = new HBox();
	private Button payButton = new Button("Pay");
	private static Integer totalNum = 0;
	private static Double totalPrice = 0.0;
	private static Text showTotalNum = new Text("0");
	private static Text showTotalPrice = new Text("0.0");
	private static HashMap<DishItem, Integer> addedDishItems = new HashMap<>();

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
		
		goToPay.getChildren().addAll(showTotalNum, new Label(" items: $ "), showTotalPrice, payButton);
		
		showTotalNum.setFont(new Font(20));
		showTotalPrice.setFont(new Font(20));
		showTotalNum.setFill(Color.RED);
		showTotalPrice.setFill(Color.RED);
		
//		go to order cart page TBD
		payButton.setOnAction(e -> {
			for(DishItem dishItem: OrderMenu.getAddedDishItems().keySet()) {
				System.out.println("dishItem: " + dishItem.getDishName() + ", unit price is: " + dishItem.getUnitPrice());
			}
		});

		setCenter(scrollPane);
		setBottom(goToPay);
		setMargin(goToPay, new Insets(10));
		

	}
	
	public static void addTotalNum() {
		totalNum++;
	}
	
	public static void addTotalPrice(Double price) {
		totalPrice += price;
	}
	
	public static void setShowTotalNum(Integer num) {
		showTotalNum.setText(num.toString());
	}
	
	public static void setShowTotalPrice(Double price) {
		showTotalPrice.setText(totalPrice.toString());
	}
	
	public static int getTotalNum() {
		return totalNum;
	}
	
	public static Double getTotalPrice() {
		return totalPrice;
	}
	
	public static void setAddedDishItems(DishItem dishItem) {
		if (addedDishItems.containsKey(dishItem)) {
			int currentQuantity = addedDishItems.get(dishItem);
			addedDishItems.put(dishItem,currentQuantity + 1);
		} else {
			addedDishItems.put(dishItem, 1);
		}
	}
	
	public static HashMap<DishItem, Integer> getAddedDishItems(){
		return addedDishItems;
	}
}

class DishItemPane extends GridPane{
	private ImageView imageView = new ImageView();
	private Text dishItemNameText = new Text();
	private Text unitPriceText = new Text();
	private Button addDishButton = new Button("+");
	
	public DishItemPane(DishItem dishItem) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                Region cell = new Region();
                cell.setStyle("-fx-background-color: lightgray;");
                cell.setMinSize(10, 10); 
                add(cell, i, j);
            }
        }
		this.imageView.setImage(new Image(dishItem.getImageUri()));
		this.dishItemNameText.setText(dishItem.getDishName());
		this.unitPriceText.setText(dishItem.getUnitPrice().toString());
		
		imageView.setFitHeight(30);
		imageView.setFitWidth(30);
		
		setRowSpan(imageView, 3);
		setColumnSpan(imageView, 3);                               
		
		add(imageView, 0, 0);
		add(dishItemNameText, 3, 0);
		add(unitPriceText, 3, 1);
		add(addDishButton, 5, 0);
		
		addDishButton.setOnAction(e -> {
			OrderMenu.addTotalNum();
			OrderMenu.addTotalPrice(dishItem.getUnitPrice());
			OrderMenu.setShowTotalNum(OrderMenu.getTotalNum());
			OrderMenu.setShowTotalPrice(OrderMenu.getTotalPrice());
//			DishItems will be passed to order cart
			OrderMenu.setAddedDishItems(dishItem);
			
		});
	}
}