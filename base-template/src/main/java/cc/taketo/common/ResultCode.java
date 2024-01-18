package cc.taketo.common;

public enum ResultCode {

    SUCCESS("0", "成功"),
    ERROR("-1", "系统异常");

    public String code;
    public String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
