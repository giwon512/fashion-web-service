package common;

import java.sql.Connection;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;




public class DBConnPool {
    private DataSource dataSource;

    public DBConnPool() {
        try {
            // 커넥션 풀 (DataSource) 얻기
            Context initCtx = new InitialContext();
            Context ctx = (Context) initCtx.lookup("java:comp/env");
            dataSource = (DataSource) ctx.lookup("dbcp_myoracle");

            System.out.println("DB 커넥션 풀 초기화 성공");
        } catch (Exception e) {
            System.out.println("DB 커넥션 풀 초기화 실패");
            e.printStackTrace();
        }
    }

    // 커넥션 풀에서 Connection 객체를 가져오는 메소드
    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
	

	

}