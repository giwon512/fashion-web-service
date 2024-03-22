package fashion.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBConnPool;
import fashion.dto.WishLists;
import fashion.dto.purChaseInfo;


public class purChaseInfoDao {
	Connection conn = null;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	private DBConnPool connPool; // DBConnPool for managing connections

	public purChaseInfoDao() {
		connPool = new DBConnPool();
	}

	

	
	public void addpurchaseInfo(purChaseInfo pur) {
		String sql = "insert into purchase_info(purchaseId, userId, productId, productName, quantity, amount, imageUrl, purchaseDate) "
				+ "values(purchaseId.nextVal, ?, ?, ?, ?, ?, ?, SYSDATE)";

		try (Connection conn = connPool.con;
                PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, pur.getUserId());
			stmt.setInt(2, pur.getProductId());
			stmt.setString(3, pur.getProductName());
			stmt.setInt(4, pur.getQuantity());
			stmt.setInt(5, pur.getAmount());
			stmt.setString(6, pur.getImageUrl());
			

			stmt.executeUpdate();
		} catch (Exception err) {
			System.out.println("setEmp()에서 오류 : " + err);
		} 
		

	}
		
	public purChaseInfo getPurchaseInfoByUserId(int userId) {
		purChaseInfo pur = null;
        String sql = "SELECT  * from purchaseInfo where userId = ?";

        try (Connection conn = connPool.con;
                PreparedStatement stmt = conn.prepareStatement(sql)) {
	
            stmt.setInt(1, userId);

            rs = stmt.executeQuery();

            while (rs.next()) {
            	pur = new purChaseInfo();
                pur.setPurchaseId(rs.getInt("purchaseId"));
                pur.setUserId(rs.getInt("userId"));
                pur.setProductId(rs.getInt("productId"));
                pur.setProductName(rs.getString("productName"));
                pur.setQuantity(rs.getInt("amount"));
                pur.setImageUrl(rs.getString(rs.getString("imageUrl")));
              
                
              
            }
        } catch (SQLException e) {
            System.out.println("getPurchaseInfoByUserId()에서 오류: " + e);
        } 
        
    

        return pur;
    }
	
	
//	public List<purChaseInfo> getPurchaseInfofindAll(int userId) {
//        List<purChaseInfo> resultList = new ArrayList<>();
//        String sql = "SELECT purchaseId, userId, productId, productName, item_name FROM purchase_info WHERE user_id = ?";
//
//        try {
//            stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, userId);
//
//            rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                purchase_info pur = new purchase_info();
//                pur.setProduct_id(rs.getString("product_id"));
//                pur.setPurchase_id(rs.getString("purchase_id"));
//                pur.setQuantity(rs.getInt("quantity"));
//                pur.setAmount(rs.getInt("amount"));
//                pur.setItem_name(rs.getString("item_name"));
//                // 여기서 user_id를 설정하지 않았습니다. 필요하다면 추가하세요.
//                resultList.add(pur);
//            }
//        } catch (SQLException e) {
//            System.out.println("getPurchaseInfoByUserId()에서 오류: " + e);
//        } finally {
//            freeConn();
//        }
//
//        return resultList;
//    }	
//	
//	
	
	
	
	
	
	
	
	
}
