import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 
 // 생성된 데이터베이스에 스키마추가.
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
 
            //DB와 연결된 conn 객체로부터 Statement 객체 획득.
            stmt = conn.createStatement();
 
            //query 만들기
            StringBuilder sb = new StringBuilder();
            String sql = sb.append("create table if not exists covid19(")     //  covid table에 attribute 추가
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
 
            //query문 날리기
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
                //자원 해제
                if(conn != null && !conn.isClosed())
                    conn.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
 


