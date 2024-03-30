package fashion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import common.DBConnPool;
import fashion.dto.Preference;

public class PreferenceDao {
	
	private DBConnPool connPool; // DBConnPool for managing connections

	public PreferenceDao() {
		connPool = new DBConnPool();
	}
	
	public void insertUserPreferences(int userId, String[] brands, String[] categories) {
	    String insertSQL = "INSERT INTO PREFERENCE (prefId,userId, category, brand) VALUES (PRE_SEQ.nextval,?, ?, ?)";
	    
	    // HashSet을 사용하여 중복을 방지할 수 있습니다.
	    Set<String> uniquePreferences = new HashSet<>();
	    
	    try (Connection conn = connPool.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

	        for (String brand : brands) {
	            for (String category : categories) {
	                String uniqueKey = userId + ":" + brand + ":" + category;
	                if (uniquePreferences.add(uniqueKey)) { // 중복되지 않는 경우에만 삽입
	                    stmt.setInt(1, userId);
	                    stmt.setString(2, category);
	                    stmt.setString(3, brand);
	                    stmt.addBatch();
	                }
	            }
	        }
	        stmt.executeBatch();
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}

	public List<Preference> selectUserPreferences(int userId) {
	    List<Preference> preferences = new ArrayList<>();
	    Set<String> uniqueBrands = new HashSet<>();
	    Set<String> uniqueCategories = new HashSet<>();
	    String sql = "SELECT P.prefId, P.userId, P.category, P.brand " +
	                 "FROM PREFERENCE P INNER JOIN MEMBER M ON P.userId = M.userId " +
	                 "WHERE P.userId = ?";

	    try (Connection conn = connPool.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, userId);

	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                String category = rs.getString("category");
	                String brand = rs.getString("brand");
	                String uniqueKeyForBrand = userId + ":brand:" + brand;
	                String uniqueKeyForCategory = userId + ":category:" + category;

	                if (brand != null && uniqueBrands.add(uniqueKeyForBrand)) {
	                    Preference prefForBrand = new Preference();
	                    prefForBrand.setBrand(brand);
	                    preferences.add(prefForBrand);
	                }

	                if (category != null && uniqueCategories.add(uniqueKeyForCategory)) {
	                    Preference prefForCategory = new Preference();
	                    prefForCategory.setCategory(category);
	                    preferences.add(prefForCategory);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }

	    return preferences;
	}
	
	
	
    
	public void resetAndInsertUserPreferences(int userId, String[] brands, String[] categories) {
	    String deleteSQL = "DELETE FROM PREFERENCE WHERE userId = ?";
	    String insertSQL = "INSERT INTO PREFERENCE (prefId, userId, category, brand) VALUES (PRE_SEQ.nextval, ?, ?, ?)";
	    
	    try (Connection conn = connPool.getConnection();
	         PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {
	        
	        // 사용자의 모든 기존 선호도 정보 삭제
	        deleteStmt.setInt(1, userId);
	        deleteStmt.executeUpdate();

	        // 새로운 선호도 정보 삽입
	        try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
	            Set<String> uniquePreferences = new HashSet<>(); // 중복 방지를 위한 Set

	            for (String brand : brands) {
	                for (String category : categories) {
	                    String uniqueKey = userId + ":" + brand + ":" + category;
	                    if (uniquePreferences.add(uniqueKey)) { // 중복되지 않는 경우에만 삽입
	                        insertStmt.setInt(1, userId);
	                        insertStmt.setString(2, category);
	                        insertStmt.setString(3, brand);
	                        insertStmt.addBatch();
	                    }
	                }
	            }
	            insertStmt.executeBatch(); // 배치 실행
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	    }
	}

	




}
