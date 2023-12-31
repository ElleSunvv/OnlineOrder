package application;

public class DishItem {
	private Category category;
	private Integer dishId;
	private String dishName;
	private Double unitPrice;
	private String imageUri;
	
	public DishItem(Category category, String dishName, Double unitPrice, String imageUri) {
		this.category = category;
		this.dishName = dishName;
		this.unitPrice = unitPrice;
		this.imageUri = imageUri;
	}
	
	public DishItem(Category category, Integer dishId, String dishName, Double unitPrice, String imageUri) {
		this.category = category;
		this.dishId = dishId;
		this.dishName = dishName;
		this.unitPrice = unitPrice;
		this.imageUri = imageUri;
	}
	
	public Integer getDishId() {
		return dishId;
	}
	
	public String getDishName() {
		return dishName;
	}
	
	public Double getUnitPrice() {
		return unitPrice;
	}
	
	public String getImageUri() {
		return imageUri;
	}
	
	public Integer getCategoryId() {
		return category.getCategoryId();
	}
	
	public String getCategoryName() {
		return category.getCategoryName();
	}
	
	public Category getCategory() {
		return new Category(getCategoryId(), getCategoryName());
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}
	
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}
}

class Category {
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
