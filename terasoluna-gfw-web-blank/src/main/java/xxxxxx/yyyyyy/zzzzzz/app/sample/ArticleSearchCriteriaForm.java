package xxxxxx.yyyyyy.zzzzzz.app.sample;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class ArticleSearchCriteriaForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String word;

    private String sort;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
