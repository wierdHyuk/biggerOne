import java.sql.*;
 
// 초기 데이터베이스를 생성
public class initializeSetting {
    public static void main(String[] args) {
        
        String url = "jdbc:mysql://127.0.0.1/?useSSL=false&user=root&password=root";    // user="데이터베이스이름" password="데이터베이스 비밀번호"
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("드라이버 연결 성공!");
            
            conn = DriverManager.getConnection(url);
            System.out.println("데이터베이스 접속 성공!");
            
            stmt = conn.createStatement();
            String existsSql = "DROP DATABASE IF EXISTS `covid19`;";   
            String createSql = "CREATE DATABASE `covid19` default character set utf8 collate utf8_general_ci;";   //covid19란 데이터베이스 생성 , UTF-8 버젼으로 인코딩
            
            stmt.executeUpdate(existsSql);
            stmt.executeUpdate(createSql);
            
            String sql2 = "SHOW DATABASES;"; //DB 리스트 출력
            
            rs = stmt.executeQuery(sql2);
            while(rs.next())
            {
                System.out.println(rs.getString(1));
            }
            
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if(conn!=null) try { conn.close(); } catch(SQLException se) {}
            if(stmt!=null) try { stmt.close(); } catch(SQLException se) {}
            if(rs!=null) try { rs.close(); } catch(SQLException se) {}
        }
    }
}


