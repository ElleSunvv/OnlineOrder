package Base;

public class Category {
	private Integer categoryId;
	private String categoryName;
	
	public Category(Integer categoryId, String categoryName) {
		setCategoryId(categoryId);
		setCategoryName(categoryName);
	}

	public Category(String categoryName) {
		setCategoryName(categoryName);
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
	
	// setter categoryName
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	// getter categoryName
	public String getCategoryName() {
		return categoryName;
	}
	
}