package fashion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.DBConnPool;
import fashion.dto.WishLists;


public class wishListsDao {
	
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	private DBConnPool connPool; // DBConnPool for managing connections
	
	public wishListsDao() {
		 connPool = new DBConnPool();
	}
	
	
	public void insertWishLists(WishLists wish) {
		
		String sql = "insert into Wishlists(wishlistId, userId, productId, imageUrl,brandName,productPrice) "
				+ "values(wishlists_seq.NEXTVAL,?,?,?,?,?)";
			
		 try (Connection conn = connPool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
	
				
		
				stmt.setInt(1, wish.getUserId());
				stmt.setInt(2, wish.getProductId());
				stmt.setString(3, wish.getImageUrl());
				stmt.setString(4, wish.getBrandName());
				stmt.setInt(5, wish.getProductPrice());
				
				stmt.executeUpdate();
				
			}catch(Exception err) {
				System.out.println("insertWithLists() 에서 오류 : " +err);
			}
		
		
	}
	
	
	public List<WishLists> findAllWishLists(int userId) {
		
		  List<WishLists> allList = new ArrayList<>();
		  String sql = "select * from wishlists where userId = ?";

		  try (Connection conn = connPool.getConnection();
	                PreparedStatement stmt = conn.prepareStatement(sql)) {
			  
			 stmt.setInt(1, userId);
	        
		    ResultSet rs = stmt.executeQuery(); // ResultSet 가져오기

		    while (rs.next()) {
		      WishLists wish = new WishLists();
		        wish.setWishlistId(rs.getInt("wishlistId"));
	            wish.setUserId(rs.getInt("userId"));
	            wish.setProductId(rs.getInt("productId"));
	            wish.setProductName(rs.getString("productName"));
	            wish.setImageUrl(rs.getString("imageUrl"));
	            wish.setBrandName(rs.getString("brandName"));
	            wish.setProductPrice(rs.getInt("productPrice"));

		      allList.add(wish); // WishLists 객체를 List에 추가
		    }
		    

		  } catch (Exception err) {
		    System.out.println("findAllWishLists() error: " + err);
		  } 
	
		  return allList; // WishLists 리스트 반환
		}
	
	
	
	public WishLists findWishListById(int wishlistId) {
	    WishLists wish = null;
	    String sql = "SELECT * FROM WISHLISTS WHERE wishlistId = ?";

	    try (Connection conn = connPool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
	      
	        stmt.setInt(1, wishlistId);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	        	wish.setWishlistId(rs.getInt("wishlistId"));
	            wish.setUserId(rs.getInt("userId"));
	            wish.setProductId(rs.getInt("productId"));
	            wish.setProductName(rs.getString("productName"));
	            wish.setImageUrl(rs.getString("imageUrl"));
	            wish.setBrandName(rs.getString("brandName"));
	            wish.setProductPrice(rs.getInt("productPrice"));
	        }
	        
	       System.out.println(wish);
	    } catch (Exception err) {
	        System.out.println("findWishListById() 에서 오류: " + err);
	    } 
	    


	    return wish;
	}
	
	public List<WishLists> findWishListByName(String name) {
		
	    WishLists wish = null;

		  List<WishLists> allList = new ArrayList<>();
	    String sql = "SELECT * FROM WISHLISTS WHERE name = ?";

	    try (Connection conn = connPool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
	      
	        stmt.setString(1, name);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	        	wish.setWishlistId(rs.getInt("wishlistId"));
	            wish.setUserId(rs.getInt("userId"));
	            wish.setProductId(rs.getInt("productId"));
	            wish.setProductName(rs.getString("productName"));
	            wish.setImageUrl(rs.getString("imageUrl"));
	            wish.setBrandName(rs.getString("brandName"));
	            wish.setProductPrice(rs.getInt("productPrice"));
	            
	            allList.add(wish); // WishLists 객체를 List에 추가
	        }
	        
	       System.out.println(wish);
	    } catch (Exception err) {
	        System.out.println("findWishListByName() 에서 오류: " + err);
	    } 
	    


	    return allList;
	}


	
	
	
	
	
	
	
	
	
	

}
