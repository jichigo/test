package xxxxxx.yyyyyy.zzzzzz.domain.model;

import java.io.Serializable;

public class MemberCredential implements Serializable {

    private static final long serialVersionUID = 1L;

    private String signInId;

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
