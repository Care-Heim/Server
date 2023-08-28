package com.spring.careHeim.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request 오류, Response 오류
     */
    // Common
    POST_USERS_INVALID_ID(false, HttpStatus.BAD_REQUEST.value(), "아이디 형식을 확인해주세요."),
    POST_USERS_EXISTS_ID(false,HttpStatus.BAD_REQUEST.value(),"이미 가입한 아이디가 존재합니다."),
    POST_USERS_INVALID_PW(false, HttpStatus.BAD_REQUEST.value(), "비밀번호 형식을 확인해주세요."),
    POST_USERS_EMPTY_NAME(false, HttpStatus.BAD_REQUEST.value(), "이름을 입력해주세요."),
    POST_USERS_EMPTY_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,HttpStatus.BAD_REQUEST.value(),"이미 가입한 이메일이 존재합니다."),
    POST_USERS_EMPTY_BIRTH(false, HttpStatus.BAD_REQUEST.value(), "생년월일을 입력해주세요"),
    POST_USERS_INVALID_BIRTH(false, HttpStatus.BAD_REQUEST.value(), "생년월일 형식을 확인해주세요."),
    POST_USERS_EMPTY_NICKNAME(false, HttpStatus.BAD_REQUEST.value(), "닉네임을 입력해주세요."),
    POST_USERS_INVALID_NICKNAME(false, HttpStatus.BAD_REQUEST.value(), "닉네임 형식을 확인해주세요."),
    NOT_EQUAL_VERIFICATION_CODE(false, HttpStatus.BAD_REQUEST.value(), "인증 번호가 일치하지 않습니다."),
    FIND_FAIL_USER_NAME_EMAIL(false,HttpStatus.NOT_FOUND.value(), "이름과 이메일이 정확히 입력되었는지 확인해주세요."),
    FIND_FAIL_ID(false, HttpStatus.NOT_FOUND.value(), "입력한 아이디와 일치하는 회원정보가 없습니다."),
    FIND_FAIL_INVITATION(false, HttpStatus.NOT_FOUND.value(), "초대 요청 목록이 존재하지 않습니다."),
    FAILED_TO_LOGIN(false,HttpStatus.NOT_FOUND.value(),"아이디와 비밀번호가 일치하지 않습니다."),

    /**
     * 500 : Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}