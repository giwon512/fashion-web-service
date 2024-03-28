package fashion.dto;

import java.time.LocalDate;



public class purChaseInfo {
    private int purchaseId; // 구매아이디
    private int userId; // 유저 아이디
    private int productId; // 프로덕트 아이디
    private String productName; // 상품이름
    private int quantity; // 구매수량
    private int amount; // 구매금액
    private String imageUrl;
    private LocalDate purchaseDate; // LocalDate 사용
    
    
   public purChaseInfo() {
	   
   }

    public purChaseInfo(String imageUrl, String productName, LocalDate purchaseDate, int productId, int quantity, int amount) {
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.purchaseDate = purchaseDate;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
    

    
	

    
    
    
    
	
}