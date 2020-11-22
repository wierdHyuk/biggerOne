package com.covid.DAO;

import com.covid.Model.PatientModel;

import java.sql.*;
import java.util.List;

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

    public static void getCountRegion(){
        String url = "jdbc:mysql://localhost/covid19?&useSSL=false&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "";
        String password = "";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url,username,password);

            stmt = conn.createStatement();


            String cnt_dobong = "select count(*) from patient where region = '도봉구'";
            String cnt_kangbuk = "select count(*) from patient where region = '강북구'";
            String cnt_nowon = "select count(*) from patient where region = '노원구'";
            String cnt_jungryang = "select count(*) from patient where region = '중량구'";
            String cnt_dongdaemoon = "select count(*) from patient where region = '동대문구'";
            String cnt_sungbook = "select count(*) from patient where region = '성북구'";
            String cnt_kwangjin = "select count(*) from patient where region = '광진구'";
            String cnt_sungdong = "select count(*) from patient where region = '성동구'";
            String cnt_joong = "select count(*) from patient where region = '중구'";
            String cnt_jongro = "select count(*) from patient where region = '종로구'";
            String cnt_eunpyeong = "select count(*) from patient where region = '은평구'";
            String cnt_seodaemoon = "select count(*) from patient where region = '서대문구'";
            String cnt_mapo = "select count(*) from patient where region = '마포구'";
            String cnt_kwangseo = "select count(*) from patient where region = '광서구'";
            String cnt_yangcheon = "select count(*) from patient where region = '양천구'";
            String cnt_kuro = "select count(*) from patient where region = '구로구'";
            String cnt_yeongdeungpo = "select count(*) from patient where region = '영등포구'";
            String cnt_kuemcheon = "select count(*) from patient where region = '금천구'";
            String cnt_dongjak = "select count(*) from patient where region = '동작구'";
            String cnt_kwanak = "select count(*) from patient where region = '관악구'";
            String cnt_seocho = "select count(*) from patient where region = '서초구'";
            String cnt_kangnam = "select count(*) from patient where region = '강남구'";
            String cnt_songpa = "select count(*) from patient where region = '송파구'";
            String cnt_kangdong = "select count(*) from patient where region = '강동구'";

            rs = stmt.executeQuery(cnt_dobong);
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

}
