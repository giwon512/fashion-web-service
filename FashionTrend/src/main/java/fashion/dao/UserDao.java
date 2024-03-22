package fashion.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.DBConnPool;
import fashion.dto.Member;

public class UserDao {
    
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    
 	 private DBConnPool connPool; // DBConnPool for managing connections
    
    public UserDao() {
     connPool = new DBConnPool();
    }
    

    
    public boolean checkDuplicateUserID(String name) {
        String sql = "SELECT COUNT(*) FROM member WHERE name=?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = connPool.con;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // 중복된 아이디가 존재하면 true 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch(SQLException sqle) {}
            if (stmt != null) try { stmt.close(); } catch(SQLException sqle) {}
            if (conn != null) try { conn.close(); } catch(SQLException sqle) {}
        }
        return false; // 아이디 중복 여부 확인 실패 시 기본값인 false 반환
    }
    
    
    public boolean insertUser(Member member) {
    	String sql = "insert into MEMBER(userId, name, password, gender,email,address) "
    			+ "values(member_seq.nextval,?,?,?,?,?)";
    	 
    	 try (Connection conn = connPool.con;
    	            PreparedStatement stmt = conn.prepareStatement(sql)) {


    	      stmt.setString(1, member.getName());
    	      stmt.setString(2, member.getPassword());
    	      stmt.setString(3, member.getGender()); 
    	      stmt.setString(4, member.getEmail());
    	      stmt.setString(5, member.getAddress());

    	      int affectedRows = stmt.executeUpdate();
    	      return affectedRows > 0;

    	} catch (Exception e) {
    	     System.out.println("insert에서의 오류");
    	     e.printStackTrace();	
    	     return false; // 오류 발생 시 false를 리턴하도록 수정
    	}
    }
    
    

    public boolean checkLogin(String name, String password) {
        String sql = "SELECT * FROM MEMBER WHERE name=? AND password=?";
        
 
        try (Connection conn = connPool.con;
                PreparedStatement stmt = conn.prepareStatement(sql)) {
        	
        	
      
            stmt.setString(1, name);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            return rs.next(); // 결과셋에 레코드가 있다면 로그인 성공
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        
      
        return false; // 로그인 실패 시 기본값인 false 반환
    }

    
    
    public int updateUser(Member member) {
        String sql = "UPDATE Member SET name = ?, password = ?, gender = ?, email = ?, address = ? WHERE userId = ?";
        int result = 0;


        try (Connection conn = connPool.con;
                PreparedStatement stmt = conn.prepareStatement(sql)) {
         
            
            
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getPassword());
            stmt.setString(3, member.getGender()); 
            stmt.setString(4, member.getEmail());
            stmt.setString(5, member.getAddress());
            stmt.setInt(6, member.getUserId());

            result = stmt.executeUpdate();
            
           System.out.println(member.getUserId());

        } catch (Exception err) {
            System.out.println("updateUser() 에서 오류 : " + err);
        } 
           

        return result;
    }
    
    
    public List<Member> findAllUsers() {
          List<Member> allList = new ArrayList<>();
          String sql = "SELECT * FROM MEMBER";


          try (Connection conn = connPool.con;
                  PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery(); // ResultSet 가져오기

            while (rs.next()) {
              Member member = new Member();
              member.setUserId(rs.getInt("USERID"));
              member.setPassword(rs.getString("PASSWORD"));
              member.setName(rs.getString("NAME"));
              member.setGender(rs.getString("GENDER")); // 첫 번째 문자를 성별로 설정
              member.setAddress(rs.getString("ADDRESS"));

              allList.add(member); // User 객체를 List에 추가
            }
            

          } catch (Exception err) {
            System.out.println("findAllUsers() error: " + err);
            
          } 
          return allList; // User 리스트 반환
        }
    
    
    
    public Member findUserById(int userId) {
        Member member=null;
        
        System.out.println("usersid : " + userId);
        String sql = "SELECT * FROM MEMBER WHERE USERID = ?";


        try (Connection conn = connPool.con;
                PreparedStatement stmt = conn.prepareStatement(sql)) {
           
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                member = new Member();
                member.setName(rs.getString("NAME"));
                member.setPassword(rs.getString("PASSWORD"));
                member.setGender(rs.getString("GENDER"));
                member.setEmail(rs.getString("EMAIL"));
                member.setAddress(rs.getString("ADDRESS"));
                
            }
            
           System.out.println(member.getAddress());
        } catch (Exception err) {
            System.out.println("findUserById() 에서 오류: " + err);
            
        } 
    


        return member;
    }
}