package xxxxxx.yyyyyy.zzzzzz.app.sample;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import xxxxxx.yyyyyy.zzzzzz.domain.model.ArticleClass;

/**
 * The persistent class for the article database table.
 */
public class ArticleForm implements Serializable {
    private static final long serialVersionUID = 1L;

    public static interface ArticleCreate {
    }

    public static interface ArticleUpdate {
    }

    public static interface ArticleDelete {
    }

    public static interface ArticleLoad {
    }

    @NotNull(groups = { ArticleUpdate.class, ArticleDelete.class,
            ArticleLoad.class })
    private Long articleId;

    @NotEmpty
    private String content;

    @NotEmpty
    private String overview;

    @NotEmpty
    private String publishedBy;

    @NotEmpty
    private String title;

    @NotEmpty
    private String articleClassId;

    public ArticleForm() {
    }

    public Long getArticleId() {
        return this.articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPublishedBy() {
        return this.publishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        this.publishedBy = publishedBy;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleClassId() {
        return articleClassId;
    }

    public void setArticleClassId(String articleClassId) {
        this.articleClassId = articleClassId;
    }

}
