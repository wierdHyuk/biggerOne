package DAO;

import java.sql.*;

public class DataDAO {
    public static void createDb() {
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

    public static void createTable(){
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
                    .append("confirmedDate varchar(20),")
                    .append("patientId varchar(20),")
                    .append("country varchar(20),")
                    .append("patientInfo varchar(40),")
                    .append("location varchar(40),")
                    .append("visitedPlace text,")
                    .append("contact text,")
                    .append("action text,")
                    .append("status varchar(10),")
                    .append("route text,")
                    .append("createdate varchar(40),")
                    .append("modifydate varchar(40),")
                    .append("share varchar(20)")
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



    public static void csvToDB(){
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
