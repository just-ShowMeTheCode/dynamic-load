package com.wdl.dynamicload.util;

import org.springframework.http.HttpStatus;

/**
 * @author Yang Shengdong
 */
public class BadRequestParam extends ApiException {

    private static final long serialVersionUID = 1L;

    public BadRequestParam(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
