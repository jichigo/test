package xxxxxx.yyyyyy.zzzzzz.domain.service;

import java.io.Serializable;

public class ArticleSearchCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
