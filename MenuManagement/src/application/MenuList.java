package application;

import javafx.event.ActionEvent;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MenuList extends VBox {
   	private TextField searchField = new TextField();
   	private Button searchButton = new Button("Search");
   	private Button addButton = new Button("Add");
   	private Button editButton = new Button("Edit");
   	private Button deleteButton = new Button("Delete");
   	private HBox tableButtons = new HBox();
   	private static TableView<DishItem> table = new TableView<DishItem>();
   	private static ArrayList<DishItem> dishItems = new ArrayList<DishItem>();
   	private static ObservableList<DishItem> observableDishItems = FXCollections.observableArrayList();
   	private EditDishItemDialogStage editDishItemDialogStage = new EditDishItemDialogStage();
	 
	public MenuList() {
        tableButtons.setSpacing(10);
        tableButtons.getChildren().addAll(searchField, searchButton, addButton, editButton, deleteButton);
        
        TableColumn<DishItem, String> dishNameCol = new TableColumn<DishItem,String>("Dish Name");
        dishNameCol.setCellValueFactory(new PropertyValueFactory("dishName"));
        TableColumn<DishItem,String> unitPriceCol = new TableColumn<DishItem,String>("Unit Price");
        unitPriceCol.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        TableColumn<DishItem, String> categoryCol = new TableColumn<DishItem,String>("Category");
        categoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoryName()));
        
        table.getColumns().setAll(categoryCol, dishNameCol, unitPriceCol);
        
        observableDishItems.clear();
        dishItems = Mysql.getDishItemsFromDatabase();
        observableDishItems = FXCollections.observableArrayList(dishItems);
        table.setItems(observableDishItems);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        getChildren().addAll(tableButtons, table);
		setMargin(tableButtons, new Insets(10));
		setMargin(table, new Insets(10));
		
		searchButton.setOnAction(new SearchDishItemHandler());
		addButton.setOnAction(new ShowEditDialogHandler(EditDishItemDialogStage.Mode.ADD));
		editButton.setOnAction(new ShowEditDialogHandler(EditDishItemDialogStage.Mode.EDIT));
		deleteButton.setOnAction(new DeleteDishItemHandler());
	}
	
	class SearchDishItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			searchDishItem(searchField.getText());
		}
	}
	
	class ShowEditDialogHandler implements EventHandler<ActionEvent> {
   		EditDishItemDialogStage.Mode mode;
   		public ShowEditDialogHandler(EditDishItemDialogStage.Mode mode) {
   			this.mode = mode;
   		}
		@Override
		public void handle(ActionEvent event) {
			boolean modeSelectionValid = editDishItemDialogStage.setMode(mode);
			if (modeSelectionValid == true) {
				editDishItemDialogStage.showEditDialog();
			}
		}
   	}
	
   	class DeleteDishItemHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			deleteDishItem();
		}
   	}
   	
	public static DishItem getSelectedDishItem() {
		return table.getSelectionModel().getSelectedItem();
	}
	
    public void searchDishItem(String inputDishName) {
    	observableDishItems.clear();
    	try (Connection connect = Mysql.getConnection();
    		PreparedStatement preStmt = connect.prepareStatement(
    				"select dishName, unitPrice, imageURI, onlineOrderDatabase.dishItem.categoryID, onlineOrderDatabase.category.categoryName "
    				+ "from onlineOrderDatabase.dishItem join onlineOrderDatabase.category "
    				+ "on onlineOrderDatabase.dishItem.categoryID = onlineOrderDatabase.category.categoryID "
    				+ "where dishName = ?");
    		){
    		preStmt.setString(1, inputDishName);
    		try (ResultSet rs = preStmt.executeQuery()){
    			if (rs.next()) {
        			String dishName = rs.getString("dishName");
    			    Double unitPrice = rs.getDouble("unitPrice");
    			    String imageURI = rs.getString("imageURI");
    			    Integer categoryID = rs.getInt("categoryID");
    			    String categoryName = rs.getString("categoryName");
    				observableDishItems.add(new DishItem(new Category(categoryID, categoryName), dishName, unitPrice, imageURI));
    				System.out.println("Data found");
    			} else {
    				System.out.println("No data found");
    			}
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    public static void addDishItem(DishItem dishItem) {
		if (!Mysql.isDishItemExisted(dishItem)) {
			observableDishItems.add(dishItem);
	    	try (Connection connect = Mysql.getConnection();
	   	         PreparedStatement preStmt = connect.prepareStatement(
	   	        		 "insert into onlineOrderDatabase.dishItem "
	   	         		+ "(dishName, unitPrice, imageURI, categoryID) "
	   	         		+ "values (?, ?, ?, ?)",
	   	         		Statement.RETURN_GENERATED_KEYS);
	       		){
	   			preStmt.setString(1, dishItem.getDishName());
	   			preStmt.setDouble(2, dishItem.getUnitPrice());
	   			preStmt.setString(3, dishItem.getImageUri());
	   			preStmt.setInt(4, dishItem.getCategoryId());
	   			int affectedRows = preStmt.executeUpdate();
	   			if (affectedRows > 0) {
	                try (ResultSet generatedKeys = preStmt.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                        int newDishId = generatedKeys.getInt(1);
	                        dishItem.setDishId(newDishId);  // 将数据库生成的自增主键值赋给dishItem
	                    } else {
	                        throw new SQLException("Creating dishItem failed, no ID obtained.");
	                    }
	                }
	            } else {
	                throw new SQLException("Creating dishItem failed, no rows affected.");
	            }
	   			System.out.println("Add dish item \"" + dishItem.getDishName() + "\" to database");
	   			}
	   		catch (SQLException e) {
		   		e.printStackTrace();
		   	}
		} else {
			System.out.println("\"" + dishItem.getDishName() + "\" already exists, please change dish name!");
		}
    }
    
    public static void editDishItem(DishItem dishItem, Category category, String dishName, Double unitPrice, String imageUri) {
    	DishItem selectedDishItem = table.getSelectionModel().getSelectedItem();
    	if ( (!selectedDishItem.getDishName().equals(dishName) && !Mysql.isDishItemExisted(dishItem)) ||
    			selectedDishItem.getDishName().equals(dishName)
    			) {
    		selectedDishItem.setCategory(category);
	    	selectedDishItem.setDishName(dishName);
	    	selectedDishItem.setUnitPrice(unitPrice);
	    	selectedDishItem.setImageUri(imageUri);
	    	table.refresh();
	    	try (Connection connect = Mysql.getConnection();
	   	         PreparedStatement preStmt = connect.prepareStatement(
	   	        		 "update onlineOrderDatabase.dishItem "
	   	         		+ "set dishName = ?, unitPrice = ?, imageURI = ?, categoryID = ? where dishID = ?");
	       		){
	   			preStmt.setString(1, dishName);
	   			preStmt.setDouble(2, unitPrice);
	   			preStmt.setString(3, imageUri);
	   			preStmt.setInt(4, category.getCategoryId());
	   			preStmt.setInt(5, selectedDishItem.getDishId());
	   			preStmt.executeUpdate();
	   			System.out.println("Edit dish item \""+ dishName + "\" to database");
	   			}
	   		catch (SQLException e) {
				e.printStackTrace();
	   			}
	    	} else if (!dishName.equals(selectedDishItem.getDishName()) && Mysql.isDishItemExisted(dishItem)){
				System.out.println("\"" + dishItem.getDishName() + "\" already exists, please change dish name!");
				} 
    }
    
    public void deleteDishItem() {
    	DishItem selectedDishItem = table.getSelectionModel().getSelectedItem();
    	if (selectedDishItem != null) {
        	observableDishItems.remove(selectedDishItem);
        	try (Connection connect = Mysql.getConnection();
       	         PreparedStatement preStmt = connect.prepareStatement("delete from onlineOrderDatabase.dishItem where (dishID = ?)");
           		){
       			preStmt.setInt(1, selectedDishItem.getDishId());
       			preStmt.executeUpdate();
       			System.out.print("Delete dish item: " + selectedDishItem.getDishName());
       			}
       		catch (SQLException e) {
   				e.printStackTrace();
   			}
    	} else {
			System.out.println("Please select a dish item");
		}
    }
}
		
class EditDishItemDialogStage extends Stage {
	private ChoiceBox<Category> categorySelection = new ChoiceBox<Category>();
	private TextField dishNameInput = new TextField();
	private TextField unitPriceInput = new TextField();
	private Button uploadButton = new Button("Upload Image");
	private String imageUri = "images/defaultimage.jpeg";
	private Image image = new Image(imageUri);
	private ImageView imageView = new ImageView();
	private Button confirmButton = new Button("Confirm");
	private GridPane editDishItemPane = new GridPane();
	private ArrayList<Category> categoryItems = Mysql.getCategoryItemsFromDatabase();
	private Category category;
	private String dishName;
	private Double unitPrice;
	private Mode mode;
	
	public EditDishItemDialogStage() {
		editDishItemPane.setAlignment(Pos.BASELINE_CENTER);
		editDishItemPane.setPadding(new Insets(10));
		editDishItemPane.setHgap(10);
		editDishItemPane.setVgap(10);
		
		for(Category categoryItem : categoryItems) {
			categorySelection.getItems().add(categoryItem);
		}
		categorySelection.setMaxWidth(180);
		imageView.setImage(image);
		imageView.setFitHeight(40);
		imageView.setFitWidth(40);
		
		editDishItemPane.add(new Label("*Category: "), 0, 0);
		editDishItemPane.add(categorySelection, 1, 0);
		editDishItemPane.add(new Label("*Dish Name: "), 0, 1);
		editDishItemPane.add(dishNameInput, 1, 1);
		editDishItemPane.add(new Label("*Unit Price: "), 0, 2);
		editDishItemPane.add(unitPriceInput, 1, 2);
		editDishItemPane.add(new Label("*Image: "), 0, 3);
		editDishItemPane.add(uploadButton, 1, 3);
		editDishItemPane.add(imageView, 1, 4);
		editDishItemPane.add(new Label("Required is marked * ."), 0, 5);
		editDishItemPane.add(confirmButton, 1, 6);
		
		uploadButton.setOnAction(new UploadImageHandler());
		confirmButton.setOnAction(new ConfirmHandler());
		
		Scene editDialogScene = new Scene(editDishItemPane, 400, 300);
		super.setTitle("Edit Dish Item");
		super.setScene(editDialogScene);
	}
	
	public void showEditDialog() {
		super.show();
	}
	
	public void closeEditDialog() {
		super.close();
	}
	
	public void setDialogInput(Category category, String dishName, String unitPrice) {
		categorySelection.setValue(category);
		dishNameInput.setText(dishName);
		unitPriceInput.setText(unitPrice);
	}
	
	public boolean setMode(Mode mode) {
		boolean modeSelectionValid = true;
		this.mode = mode;
		if (mode == Mode.ADD) {
			super.setTitle("Add Dish Item");
			setDialogInput(new Category(""), "", "");
		} else if(mode == Mode.EDIT) {
			DishItem selectedDishItem = MenuList.getSelectedDishItem();
			if (selectedDishItem != null) {
				super.setTitle("Edit Dish Item");
				setDialogInput(selectedDishItem.getCategory(), selectedDishItem.getDishName(), selectedDishItem.getUnitPrice().toString());
			} else {
				modeSelectionValid = false;
				System.out.println("Please select an item");
			}
		}
		return modeSelectionValid;
	}
	
	public enum Mode {
		ADD, EDIT
	}
	
	class UploadImageHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
			File selectedFile = fileChooser.showOpenDialog(null);

			if (selectedFile != null) {
				image = new Image(selectedFile.toURI().toString());
			} else {
				System.out.println("File selection canceled.");
			}
			imageView.setImage(image);
			imageUri = selectedFile.toURI().toString();
		}
	}
	
	class ConfirmHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			category = categorySelection.getValue();
			dishName = dishNameInput.getText();
			try {
				unitPrice = Double.parseDouble(unitPriceInput.getText());
			} catch (NumberFormatException e){
				System.out.println("Invalid input, unitPrice should be numbers");
			}
			if (category.toString().isEmpty() || dishName.isEmpty() || unitPriceInput.getText().isEmpty()) {
				System.out.println("Empty input");
			} else {
				DishItem newDishItem = new DishItem(category, dishName, unitPrice, imageUri);
				if (mode == Mode.ADD) {
					MenuList.addDishItem(newDishItem);
				} else if(mode == Mode.EDIT) {
					MenuList.editDishItem(newDishItem, category, dishName, unitPrice, imageUri);
				}
				categorySelection.setValue(category);
				closeEditDialog();
			}
		}
	}
	
//	class WarningStage extends Stage{
//		private String warningStmt;
//		
//		public WarningStage(String warningStmt) {
//			this.warningStmt = warningStmt;
//		}
//	}
   
}



 


