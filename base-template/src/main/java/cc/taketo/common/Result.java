package cc.taketo.common;

public class Result<T> {

    private String code;

    private String message;

    private T data;

    private Result(T data) {
        this.data = data;
    }

    public Result() {
    }

    public static Result success() {
        Result tResult = new Result<>();
        tResult.setCode(ResultCode.SUCCESS.code);
        tResult.setMessage(ResultCode.SUCCESS.message);
        return tResult;
    }


    public static <T> Result<T> success(T data) {
        Result<T> tResult = new Result<>(data);
        tResult.setCode(ResultCode.SUCCESS.code);
        tResult.setMessage(ResultCode.SUCCESS.message);
        return tResult;
    }

    public static Result error() {
        Result tResult = new Result<>();
        tResult.setCode(ResultCode.ERROR.code);
        tResult.setMessage(ResultCode.ERROR.message);
        return tResult;
    }

    public static Result error(String code, String msg) {
        Result tResult = new Result<>();
        tResult.setCode(code);
        tResult.setMessage(msg);
        return tResult;
    }

    public static Result error(String code, String msg, String detail) {
        Result tResult = new Result<>();
        tResult.setCode(code);
        tResult.setMessage(msg);
        tResult.setData(detail);
        return tResult;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
