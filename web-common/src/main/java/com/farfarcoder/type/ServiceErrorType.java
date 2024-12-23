package com.farfarcoder.type;

import org.springframework.http.HttpStatus;

public enum ServiceErrorType {
    //공통
    CREATED(HttpStatus.CREATED, 1, "등록"),

    //회원
    NOT_FOUND(HttpStatus.NOT_FOUND, 1, "해당 리소스를 찾을 수 없습니다."),
    DUPLICATE_ENTRY(HttpStatus.CONFLICT, 1, "이미 가입 되어있는 유저 입니다."),

    //External
    EXTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 1, "연동 오류"),

    //API Client 오류
    INVALID_USER_NAME(HttpStatus.BAD_REQUEST, 1, "이름은 한글, 영문 대소문자만 허용합니다."),
    ;

    HttpStatus httpStatus;
    int code;
    String message;

    ServiceErrorType(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
    public String getMessage() {
        return this.message;
    }
    public Integer getCode() {
        return code;
    }
}
