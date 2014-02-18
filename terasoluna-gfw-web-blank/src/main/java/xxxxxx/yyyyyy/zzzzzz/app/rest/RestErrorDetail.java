package xxxxxx.yyyyyy.zzzzzz.app.rest;

public class RestErrorDetail {

    private final String code;

    private final String message;

    public RestErrorDetail(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
