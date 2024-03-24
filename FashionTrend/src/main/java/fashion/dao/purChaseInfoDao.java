package fashion.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.DBConnPool;

import fashion.dto.purChaseInfo;


public class purChaseInfoDao {

	private ResultSet rs;
	
	private DBConnPool connPool; // DBConnPool for managing connections

	public purChaseInfoDao() {
		connPool = new DBConnPool();
	}

	

	
	public void addpurchaseInfo(purChaseInfo pur) {
		String sql = "insert into purchase_info(purchaseId, userId, productId, productName, quantity, amount, imageUrl, purchaseDate) "
				+ "values(purchaseId.nextVal, ?, ?, ?, ?, ?, ?, SYSDATE)";

		try (Connection conn = connPool.getConnection();
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

        try (Connection conn = connPool.getConnection();
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
        } catch (Exception e) {
            System.out.println("getPurchaseInfoByUserId()에서 오류: " + e);
        } 
        
    

        return pur;
    }
	
	
	public List<purChaseInfo> getPurchaseInfofindAll(int userId) {
		purChaseInfo pur = null;
        List<purChaseInfo> resultList = new ArrayList<>();
        String sql = "SELECT  * from purchaseInfo where userId = ?";

     try (Connection conn = connPool.getConnection();
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
        } catch (Exception e) {
            System.out.println("getPurchaseInfofindAll()에서 오류: " + e);
        } 
     


        return resultList;
    }	
	

	
	
	
	
	
	
	
	
}