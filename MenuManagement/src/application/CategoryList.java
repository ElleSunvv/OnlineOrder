package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

@SuppressWarnings({ "unchecked"})
public class CategoryList extends VBox {
	private Button addButton = new Button("Add");
	private Button deleteButton = new Button("Delete");
	private HBox tableButtons = new HBox();
	private static TableView<Category> table = new TableView<Category>();
	private static ArrayList<Category> categoryItems = new ArrayList<Category>();
	private static ObservableList<Category> observableCategoryItems = FXCollections.observableArrayList();
	private String sceneTitle;
	private EditCategoryDialogStage editCategoryDialogStage = new EditCategoryDialogStage();
 
	public CategoryList() {
	    tableButtons.setSpacing(10);
	    tableButtons.getChildren().addAll(addButton,deleteButton);
	
	    TableColumn<Category, String> categoryIdCol = new TableColumn<Category,String>("Category ID");
	    TableColumn<Category, String> categoryNameCol = new TableColumn<Category,String>("Category Name");
	    
	    categoryIdCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoryId().toString()));
	    categoryNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategoryName()));
	    
	    table.getColumns().setAll(categoryIdCol, categoryNameCol);
	    
	    observableCategoryItems.clear();
	    categoryItems = Mysql.getCategoryItemsFromDatabase();
	    observableCategoryItems = FXCollections.observableArrayList(categoryItems);
	    table.setItems(observableCategoryItems);
	    table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	
	    getChildren().addAll(tableButtons, table);
		setMargin(tableButtons, new Insets(10));
		setMargin(table, new Insets(10));
		
		addButton.setOnAction(new ShowEditDialogHandler());
		deleteButton.setOnAction(new DeleteCategoryHandler());
	}

	public String getTitle() {
		return sceneTitle;
	}
	 
	public static void addCategory(Category category) {
		if (!Mysql.isCategoryExisted(category)) {
			observableCategoryItems.add(category);
			try (Connection connect = Mysql.getConnection();
			         PreparedStatement preStmt = connect.prepareStatement(
			        		 "insert into onlineOrderDatabase.category "
			        		 + "(categoryName) "
				         	 + "values (?)",
			   	         		Statement.RETURN_GENERATED_KEYS);
		    		){
					preStmt.setString(1, category.getCategoryName());
					int affectedRows = preStmt.executeUpdate();
		   			if (affectedRows > 0) {
		                try (ResultSet generatedKeys = preStmt.getGeneratedKeys()) {
		                    if (generatedKeys.next()) {
		                        int newCategoryId = generatedKeys.getInt(1);
		                        category.setCategoryId(newCategoryId);  // 将数据库生成的自增主键值赋给category
		                    } else {
		                        throw new SQLException("Creating category failed, no ID obtained.");
		                    }
		                }
		            } else {
		                throw new SQLException("Creating category failed, no rows affected.");
		            }
					System.out.println("Add category \"" + category.getCategoryName() + "\" to database");
					}
				catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("category already existed: " + category.getCategoryName());
			}
	}
	
	public void deleteCategory() {
		Category selectedCategory = table.getSelectionModel().getSelectedItem();
    	if (selectedCategory != null) {
        	observableCategoryItems.remove(selectedCategory);
        	try (Connection connect = Mysql.getConnection();
       	         PreparedStatement preStmt = connect.prepareStatement("delete from onlineOrderDatabase.category where (categoryId = ?)");
           		){
       			preStmt.setInt(1, selectedCategory.getCategoryId());
       			preStmt.executeUpdate();
       			System.out.print("Delete category item: " + selectedCategory.getCategoryName());
       			}
       		catch (SQLException e) {
   				e.printStackTrace();
   			}
    	} else {
			System.out.println("Please select a category item");
		}
	}
	 
	class ShowEditDialogHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			editCategoryDialogStage.showEditDialog();
		}
	}
	
	class DeleteCategoryHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent arg0) {
			deleteCategory();
		}
	}
}

class EditCategoryDialogStage extends Stage {
	private TextField categoryNameInput = new TextField();
	private Button confirmButton = new Button("Confirm");
	private GridPane editCategoryPane = new GridPane();
	private String categoryName;
	
	public EditCategoryDialogStage() {
		editCategoryPane.setAlignment(Pos.BASELINE_CENTER);
		editCategoryPane.setPadding(new Insets(10));
		editCategoryPane.setHgap(10);
		editCategoryPane.setVgap(10);
		
		editCategoryPane.add(new Label("*Category: "), 0, 0);
		editCategoryPane.add(categoryNameInput, 1, 0);
		editCategoryPane.add(new Label("Required is marked * ."), 0, 1);
		editCategoryPane.add(confirmButton, 1, 2);
		
		confirmButton.setOnAction(new ConfirmHandler());
		
		Scene editDialogScene = new Scene(editCategoryPane, 400, 300);
		super.setTitle("Edit Category Item");
		super.setScene(editDialogScene);
	}
	
	public void showEditDialog() {
		super.show();
	}
	
	public void closeEditDialog() {
		super.close();
	}
	
	class ConfirmHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			categoryName = categoryNameInput.getText();
			if (categoryName.isEmpty()) {
				System.out.println("Empty input");
			} else {
				Category newCategory = new Category(categoryName);
				CategoryList.addCategory(newCategory);
				closeEditDialog();
			}
		}
	}
}