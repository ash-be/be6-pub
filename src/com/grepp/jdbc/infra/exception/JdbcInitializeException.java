package com.grepp.jdbc.infra.exception;

public class JdbcInitializeException extends RuntimeException{

    // 받아서 메시지
    public JdbcInitializeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcInitializeException(String message) {
        super(message);
    }
}
