package Base;

public class DishItem {
	private Category category;
	private String dishId;
	private String dishName;
	private Double unitPrice;
	private String imageUri;
	private static int dishNum;
	
	public DishItem(Category category, String dishName, Double unitPrice, String imageUri) {
		this.category = category;
		this.dishId = category.getCategoryId() + dishNum;
		this.dishName = dishName;
		this.unitPrice = unitPrice;
		this.imageUri = imageUri;
		dishNum++;
	}
	
	public String getDishId() {
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
	
	public String getCategoryId() {
//		return super.getCategoryId();
		return category.getCategoryId();
	}
	
	public String getCategoryName() {
//		return super.getCategoryName();
		return category.getCategoryName();
	}
	
	public Category getCategory() {
		return new Category(getCategoryId(), getCategoryName());
	}
	
	public void setCategory(Category category) {
		this.category = category;
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

