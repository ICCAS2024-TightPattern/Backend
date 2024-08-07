package com.rhkr8521.iccas_question.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)

public enum ErrorStatus {
    /**
     * 400 BAD_REQUEST
     */
    VALIDATION_REQUEST_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청값이 입력되지 않았습니다."),
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /**
     * 404 NOT_FOUND
     */
    NOT_FOUND_QUESTION(HttpStatus.NOT_FOUND, "해당 문제를 찾을 수 없습니다."),
    NOT_FOUND_THEME(HttpStatus.NOT_FOUND, "해당 테마를 찾을 수 없습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저ID를 찾을 수 없습니다."),
    NOT_FOUND_RESULT(HttpStatus.NOT_FOUND, "해당 유저의 게임 기록이 없습니다."),

    /**
     * 500 SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 에러가 발생했습니다."),
    BAD_GATEWAY_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "일시적인 에러가 발생하였습니다.\n잠시 후 다시 시도해주세요!"),
    Initial_Result_Fail(HttpStatus.INTERNAL_SERVER_ERROR, "결과 초기값 설정에 에러가 발생했습니다."),
    CHATGPT_RESPONSE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "chatGPT API 통신에 실패했습니다."),
    CHATGPT_INCORRECT_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "chatGPT API 응답이 비정상 입니다, 응답내용 : "),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getStatusCode() {
        return this.httpStatus.value();
    }
}
