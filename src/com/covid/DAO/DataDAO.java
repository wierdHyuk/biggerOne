package com.covid.DAO;

import java.sql.*;

public class DataDAO {
    public static void createDb() {
        String url = "jdbc:mysql://127.0.0.1/?&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "root";
        String password = "";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url,username,password);

            stmt = conn.createStatement();
            String existsSql = "DROP DATABASE IF EXISTS `covid19`;";
            String createSql = "CREATE DATABASE `covid19` default character set utf8 collate utf8_general_ci;";

            stmt.executeUpdate(existsSql);
            stmt.executeUpdate(createSql);

            String sql2 = "SHOW DATABASES;";

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

        String url = "jdbc:mysql://localhost/covid19?&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
        String id = "root";
        String pw = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);

            System.out.println("Successfully Connected!");

            stmt = conn.createStatement();

            //query �����
            StringBuilder sb = new StringBuilder();
            String sql = sb.append("create table if not exists patient(")    
                    .append("id varchar(20) PRIMARY KEY,")
                    .append("confirmedDate varchar(20),")
                    .append("patientId varchar(20),")
                    .append("region varchar(40),")
                    .append("patientState varchar(10)")
                    .append(")default character set utf8 collate utf8_general_ci;").toString();

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

        String url = "jdbc:mysql://localhost/covid19?&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
        String id = "root";
        String pw = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);

            System.out.println("Successfully run!");

            stmt = conn.createStatement();



            StringBuilder sp = new StringBuilder();
            String sql2 = sp.append("LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.6/Uploads/covid.csv' INTO TABLE covid19 FIELDS TERMINATED BY ',';").toString();
            stmt.execute(sql2);

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
