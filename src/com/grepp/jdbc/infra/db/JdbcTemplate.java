package com.grepp.jdbc.infra.db;

import com.grepp.jdbc.infra.exception.DataAccessException;
import com.grepp.jdbc.infra.exception.JdbcInitializeException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcTemplate {

    private String url;
    private String user;
    private String password;

    private static JdbcTemplate instance;

    // 초기화 안 된 경우 메시지 던지려고
    public static JdbcTemplate getInstance(){
        if(instance == null)
            throw new JdbcInitializeException("JdbcTemplate not initialized, please call init()");
        return instance;
    }

    // 초기화하는 코드....
    public static JdbcTemplate init(String url, String user, String password){
        if(instance == null){
            instance = new JdbcTemplate(url, user, password);
        }

        return instance;
    }

    // jdbc 모듈화!
    // 생성자 생성
    private JdbcTemplate(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;

        // 호출되는 시점에 jdbc 드라이버 로드
        // 모든 예외 처리 취소
        // 이곳에서 예정되는 예외 처리
        // 드라이버 풀 네임 제대로 못 잡는 예외임,
        // 이건 개발자 휴먼에러니까, 프로그램이 멈추는 게 맞아서 throw 함

        // 한 번만 하면 되는 애들이라서 싱글톤으로 만들어서, 싱글톤 객체에서 가져다 쓰는 방법을 하면 좋음
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // 예외 터지면 throw하는 것으로 처리
            throw new JdbcInitializeException(e.getMessage(), e);
        }
    }

    // connection, close 는 private 처리 안 하는 이유는?
    public Connection getConnection()  {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            return conn;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void commit(Connection conn){
        try {
            conn.commit();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void rollback(Connection conn){
        try {
            conn.rollback();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    public void close(Connection conn) {
        try {
            // 엥 널 처리가 어쨌다고???
            if(conn == null || conn.isClosed()) return;
            conn.close();
        } catch (SQLException e) {
            // 닫힐 때 나는 예외는 어쩔 수가 없으니 런타임익셉션으로 보냄
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}