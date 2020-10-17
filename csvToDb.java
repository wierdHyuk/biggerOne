import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
 // csv 파일을 DB에 저장
 // csv 파일 UTF-8로 바꿔줘야합니다.
 
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
 
            //DB와 연결된 conn 객체로부터 Statement 객체 획득.
            stmt = conn.createStatement();
 
           
            
            StringBuilder sp = new StringBuilder();   // dafault로 경로 지정이 되어있어서 covid.csv 파일을 아래 경로에 넣어줘야함
            String sql2 = sp.append("LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/covid.csv' INTO TABLE covid19 FIELDS TERMINATED BY ',';").toString();
            stmt.execute(sql2);  // csv파일을 생성된 covid19 DB에 저장

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