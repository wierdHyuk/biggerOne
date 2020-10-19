import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 
 // ������ �����ͺ��̽��� ��Ű���߰�.
public class TableCreating {
    public static void main(String[] args){
        Connection conn = null;
        Statement stmt = null;
 
        String url = "jdbc:mysql://localhost/covid19?useSSL=false";   
        String id = "root";
        String pw = "root";
 
        try{
            Class.forName("com.mysql.jdbc.Driver");
 
            conn = DriverManager.getConnection(url, id, pw);
 
            System.out.println("Successfully Connected!");
 
            //DB�� ����� conn ��ü�κ��� Statement ��ü ȹ��.
            stmt = conn.createStatement();
 
            //query �����
            StringBuilder sb = new StringBuilder();
            String sql = sb.append("create table if not exists covid19(")     //  covid table�� attribute �߰�
            		.append("id varchar(20),")
            		.append("condate varchar(20),")
            		.append("patnum varchar(20),")
                    .append("ctr varchar(20),")
                    .append("patinfo varchar(40),")
                    .append("loc varchar(40),")
                    .append("trip text,")
                    .append("contact text,")
                    .append("manage text,")
                    .append("statement varchar(10),")
                    .append("route text,")
                    .append("regdate varchar(40),")
                    .append("moddate varchar(40),")
                    .append("exposure varchar(20)")
                    .append(")default character set utf8 collate utf8_general_ci;").toString();
 
            //query�� ������
            stmt.execute(sql);
           

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
 


