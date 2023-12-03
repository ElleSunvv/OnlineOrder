package Base;

public class DishItem {
	private Category category;
	private Integer dishId;
	private String dishName;
	private Double unitPrice;
	private String imageUri;
	private static int dishNum;
	
	public DishItem(Category category, String dishName, Double unitPrice, String imageUri) {
		this.category = category;
		this.dishName = dishName;
		this.unitPrice = unitPrice;
		this.imageUri = imageUri;
		dishNum++;
	}
	
	public DishItem(Category category, Integer dishId, String dishName, Double unitPrice, String imageUri) {
		this.category = category;
		this.dishId = dishId;
		this.dishName = dishName;
		this.unitPrice = unitPrice;
		this.imageUri = imageUri;
		dishNum++;
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
	
	public static int getDishNum() {
		return dishNum;
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

