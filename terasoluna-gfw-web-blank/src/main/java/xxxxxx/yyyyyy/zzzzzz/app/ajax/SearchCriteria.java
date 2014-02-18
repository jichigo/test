package xxxxxx.yyyyyy.zzzzzz.app.ajax;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String freeWord;

    public String getFreeWord() {
        return freeWord;
    }

    public void setFreeWord(String freeWord) {
        this.freeWord = freeWord;
    }
}
