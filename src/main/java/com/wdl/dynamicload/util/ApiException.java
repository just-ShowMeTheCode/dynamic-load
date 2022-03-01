package com.wdl.dynamicload.util;

import org.springframework.http.HttpStatus;

/**
 * 自定义异常的基类
 * 
 * @author Yang Shengdong
 *
 */
public class ApiException extends RuntimeException {
    static final long serialVersionUID = 1L;
    private final HttpStatus status;

    public ApiException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
