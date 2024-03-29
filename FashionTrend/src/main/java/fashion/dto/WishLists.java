package fashion.dto;


public class WishLists {
	
	private int wishlistId;
    private int userId;
    private int productId;
    private String productName;
    private String imageUrl;
    private String brandName;
    private int productPrice;
    
    
    public WishLists() {
    	
    	
    }
    
    public WishLists(String imageUrl,String brandName,String productName,int productPrice) {
    	this.imageUrl = imageUrl;
    	this.brandName = brandName;
    	this.productName = productName;
    	this.productPrice = productPrice;
    }
    
    
  


	public int getWishlistId() {
		return wishlistId;
	}


	public void setWishlistId(int wishlistId) {
		this.wishlistId = wishlistId;
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


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getBrandName() {
		return brandName;
	}


	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public int getProductPrice() {
		return productPrice;
	}


	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

    
    


 
	

}
