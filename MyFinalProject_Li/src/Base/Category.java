package Base;

public class Category {
	private Integer categoryId;
	private String categoryName;
	private static int categoryNum;
	
	public Category(Integer categoryId, String categoryName) {
		setCategoryId(categoryId);
		setCategoryName(categoryName);
	}

	public Category(String categoryName) {
		setCategoryName(categoryName);
		categoryNum++;
	}

	public static Integer getCategoryNum() {
		return categoryNum;
	}

	public String toString() {
		return getCategoryName();
	}
	// setter categoryId
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	// getter categoryId
	public Integer getCategoryId() {
		return categoryId;
	}
	
	public int categoryIdProperty() {
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