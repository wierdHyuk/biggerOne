import java.sql.*;
 
// �ʱ� �����ͺ��̽��� ����
public class initializeSetting {
    public static void main(String[] args) {
        
        String url = "jdbc:mysql://127.0.0.1/?useSSL=false&user=root&password=root";    // user="�����ͺ��̽��̸�" password="�����ͺ��̽� ��й�ȣ"
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("����̹� ���� ����!");
            
            conn = DriverManager.getConnection(url);
            System.out.println("�����ͺ��̽� ���� ����!");
            
            stmt = conn.createStatement();
            String existsSql = "DROP DATABASE IF EXISTS `covid19`;";   
            String createSql = "CREATE DATABASE `covid19` default character set utf8 collate utf8_general_ci;";   //covid19�� �����ͺ��̽� ���� , UTF-8 �������� ���ڵ�
            
            stmt.executeUpdate(existsSql);
            stmt.executeUpdate(createSql);
            
            String sql2 = "SHOW DATABASES;"; //DB ����Ʈ ���
            
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


