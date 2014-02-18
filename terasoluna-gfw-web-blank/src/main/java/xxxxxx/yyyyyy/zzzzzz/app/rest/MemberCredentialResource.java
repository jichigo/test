package xxxxxx.yyyyyy.zzzzzz.app.rest;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class MemberCredentialResource implements Serializable {

    private static final long serialVersionUID = 1L;

    private String signInId;

    @JsonSerialize(include = Inclusion.NON_NULL)
    @NotNull
    @Size(min = 8)
    private String password;

    public String getSignInId() {
        return signInId;
    }

    public void setSignInId(String signInId) {
        this.signInId = signInId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
