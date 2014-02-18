package xxxxxx.yyyyyy.zzzzzz.app.ajax;

import java.io.Serializable;

public class ErrorResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private String itemPath;

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

    public String getItemPath() {
        return itemPath;
    }

    public void setItemPath(String itemPath) {
        this.itemPath = itemPath;
    }

}
