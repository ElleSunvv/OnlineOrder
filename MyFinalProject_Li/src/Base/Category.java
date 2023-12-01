package Base;

public class Category {
	private String categoryId;
	private String categoryName;
	private static int categoryNum = 1;
	
	public Category() {
		
	}
	
	public Category(String categoryID, String categoryName) {
		setCategoryId(categoryID);
		setCategoryName(categoryName);
	}

	public Category(String categoryName) {
		setCategoryId("c" + categoryNum);
		setCategoryName(categoryName);
		categoryNum++;
	}

	public static int getCategoryNum() {
		return categoryNum;
	}

	public String toString() {
		return getCategoryName();
	}
	// setter categoryId
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	// getter categoryId
	public String getCategoryId() {
		return categoryId;
	}
	
	public String categoryIdProperty() {
		return categoryId;
	}
	
	// setter categoryName
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	// getter categoryName
	public String getCategoryName() {
		return categoryName;
	}
	
	public String categoryNameProperty() {
		return categoryName;
	}
}