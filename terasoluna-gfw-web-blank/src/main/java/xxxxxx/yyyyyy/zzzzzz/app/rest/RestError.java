package xxxxxx.yyyyyy.zzzzzz.app.rest;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class RestError {

    private final String code;

    private final String message;

    @JsonSerialize(include = Inclusion.NON_EMPTY)
    private final List<RestErrorDetail> details = new ArrayList<RestErrorDetail>();

    public RestError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<RestErrorDetail> getDetails() {
        return details;
    }

    public RestError addDetail(RestErrorDetail detail) {
        details.add(detail);
        return this;
    }

}
