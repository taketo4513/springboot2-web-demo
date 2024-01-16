package cc.taekto.exception;

import cc.taekto.common.ResultCode;

public class CustomException extends RuntimeException {

    private String code;

    private String message;

    public CustomException(ResultCode resultCode) {
        this.code = resultCode.code;
        this.message = resultCode.message;
    }

    public CustomException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
