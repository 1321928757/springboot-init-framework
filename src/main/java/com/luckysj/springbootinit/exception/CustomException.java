package com.luckysj.springbootinit.exception;

/**
 * 自定义级企业异常
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
