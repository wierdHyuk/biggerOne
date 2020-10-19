import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 // csv ������ DB�� ����
 // csv ���� UTF-8�� �ٲ�����մϴ�.
 
public class csvToDb {
    public static void main(String[] args){
        Connection conn = null;
        Statement stmt = null;
 
        String url = "jdbc:mysql://localhost/covid19?useSSL=false";   
        String id = "root";
        String pw = "root";
 
        try{
            Class.forName("com.mysql.jdbc.Driver");
 
            conn = DriverManager.getConnection(url, id, pw);
 
            System.out.println("Successfully run!");
 
            //DB�� ����� conn ��ü�κ��� Statement ��ü ȹ��.
            stmt = conn.createStatement();
 
           
            
            StringBuilder sp = new StringBuilder();   // dafault�� ��� ������ �Ǿ��־ covid.csv ������ �Ʒ� ��ο� �־������
            String sql2 = sp.append("LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/covid.csv' INTO TABLE covid19 FIELDS TERMINATED BY ',';").toString();
            stmt.execute(sql2);  // csv������ ������ covid19 DB�� ����

        }
 
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{
                //�ڿ� ����
                if(conn != null && !conn.isClosed())
                    conn.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}