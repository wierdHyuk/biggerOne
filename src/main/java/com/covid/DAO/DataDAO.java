package com.covid.DAO;

import com.covid.Model.PatientModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataDAO {
    public static void createDb() {
        String url = "jdbc:mysql://127.0.0.1/?&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "";
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
        String id = "";
        String pw = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);

            System.out.println("Successfully Connected!");

            stmt = conn.createStatement();

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

    public static List<PatientModel> selectAllFromTable(){
        String url = "jdbc:mysql://localhost/covid19?&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "root";
        String password = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<PatientModel> response= new ArrayList<>();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url,username,password);

            String regionCountQuery = "select * from patient";

            pstmt = conn.prepareStatement(regionCountQuery);

            rs = pstmt.executeQuery();
            while(rs.next())
            {
                PatientModel patientModel = PatientModel.PatientModelBuilder(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5));
                response.add(patientModel);
            }

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
        return response;
    }

    public static void getCountRegion(String region){
        String url = "jdbc:mysql://localhost/covid19?&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "root";
        String password = "";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url,username,password);

            String regionCountQuery = "select count(*) from patient where region = ?";

            pstmt = conn.prepareStatement(regionCountQuery);

            pstmt.setString(1,region);

            rs = pstmt.executeQuery();
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
            if(pstmt!=null) try { pstmt.close(); } catch(SQLException se) {}
            if(rs!=null) try { rs.close(); } catch(SQLException se) {}
        }
    }

    public static void modelToDB(List<PatientModel> patientModelList){
        Connection conn = null;
        Statement stmt = null;

        String url = "jdbc:mysql://localhost/covid19?&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
        String id = "";
        String pw = "";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);

            System.out.println("Successfully run!");

            stmt = conn.createStatement();


            for (PatientModel patient:patientModelList) {
                if(patient.getRegion().equals("기타")){
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                String sql = sb.append("insert into patient (id, confirmedDate, patientId, region, patientState) values('")
                        .append(patient.getId())
                        .append("','")
                        .append(patient.getConfirmedDate())
                        .append("','")
                        .append(patient.getPatientId())
                        .append("','")
                        .append(patient.getRegion())
                        .append("','")
                        .append(patient.getPatientState())
                        .append("')")
                        .toString();
                stmt.execute(sql);
            }

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

    public static List<Map<String,Long>> dayCount() {
        Connection conn = null;


        String url = "jdbc:mysql://127.0.0.1/covid19?&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
        String id = "root";
        String pw = "";

        List<Map<String, Long>> response = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);

            System.out.println("Extract patient num!\n");


            String sql = "SELECT confirmedDate, count(*) AS cnt FROM `covid19`.`patient` GROUP BY confirmedDate ORDER BY confirmedDate;";

            PreparedStatement pstmt4 = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = pstmt4.executeQuery();

            while (rs.next()) {
                Map<String, Long> data = new HashMap<>();
                data.put(rs.getString(1), rs.getLong(2));
                response.add(data);
            }

            for (Map<String,Long> data:response
                 ) {
                System.out.println(data);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return response;


    }

}
