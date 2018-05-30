package com.solituder.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of REST Error types.
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
public enum ErrorCode {
    GLOBAL(2),
    AUTHENTICATION(10),
    JWT_TOKEN_EXPIRED(11);
//    SUCCESS(200),//成功
//    FAIL(400),//失败
//    UNAUTHORIZED(401),//未认证（签名错误）
//    NOT_FOUND(404),//接口不存在
//    INTERNAL_SERVER_ERROR(500);//服务器内部错误
    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
