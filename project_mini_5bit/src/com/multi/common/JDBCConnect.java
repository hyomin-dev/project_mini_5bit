package com.multi.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCConnect {
    /*
     * SingleTon Pattern: 클래스에 대한 객체가 프로그램 구동내내 한개만 작성되어 사용 하게 함. static 으로 작성
     */

    // 기존 DB 처리 작업(select, insert, update, delete 등) 시 마다
    // 새로운 Connection 객체를 생성하여 DB와 연결하여 진행함
    // 이렇게 되면 한 사용자가 여러 개의 커넥션 객체를 만들어 DB와 접속을 하게 됨.
    // -> 여러 객체 생성으로 인한 메모리 낭비 + DB에는 접속 가능한 커넥션 객체 수 가 제한되어 있음.

    // 이를 해결하기 위하여 사용자당 커넥션 객체를 하나만 만들 수 있게 하여
    // 메모리 낭비 및 DB 연결 객체수 오버를 방지하게하는 싱글톤 패턴을 적용!

    // * 싱글톤(Single Tone) 패턴
    // 프로그램 구동 시 메모리에 객체를 딱 한개만 기록되게 하는 디자인 패턴
    // 모든 필드와 메소드를 static으로 선언하여
    // static 영역에 공용으로 사용할 수 있는 단 하나의 객체를 만듦.

    // 한 개의 공용 커넥션 객체를 저정할 참조변수 선언.
    // 외부에서 직접 접근을 할 수 없게 private 선언

    private static Connection conn = null;

    public static Connection getConnection(){
        try {
            if(conn == null || conn.isClosed()){
                Properties prop = new Properties();
                prop.load(new FileReader("resources/driver.properties"));
                Class.forName(prop.getProperty("driver"));

                System.out.println("드라이버 등록성공");


                String url = prop.getProperty("url");

                conn = DriverManager.getConnection(url , prop);
                System.out.println("conn=" + conn);

                conn.setAutoCommit(false);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return conn;
    }


    public static void close(Connection con) {
        try {
            if(con != null && !con.isClosed()) {
                System.out.println("닫기 ------------con");
                con.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static void commit(Connection con) {
        try {
            if(con != null && !con.isClosed()) {
                con.commit();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void rollback(Connection con) {
        try {
            if(con != null && !con.isClosed()) {
                con.rollback();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void close(Statement stmt) {
        try {
            if(stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static void close(ResultSet rset) {
        try {
            if(rset != null && !rset.isClosed()) {
                rset.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
