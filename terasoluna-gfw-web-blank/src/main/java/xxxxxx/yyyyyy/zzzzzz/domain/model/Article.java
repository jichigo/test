package xxxxxx.yyyyyy.zzzzzz.domain.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the article database table.
 */
@javax.persistence.Entity
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "ARTICLE_ARTICLEID_GENERATOR", table = "ID_MNG", pkColumnName = "ID_CLASS", valueColumnName = "ID_VALUE", pkColumnValue = "article_id", allocationSize = 1)
    // @SequenceGenerator(name = "ARTICLE_ARTICLEID_GENERATOR", sequenceName = "SEQ_ARTICLE")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ARTICLE_ARTICLEID_GENERATOR")
    // @GeneratedValue(strategy = GenerationType.IDENTITY/*, generator = "ARTICLE_ARTICLEID_GENERATOR"*/)
    // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTICLE_ARTICLEID_GENERATOR")
    @Column(name = "article_id")
    private Long articleId;

    private String content;

    private String overview;

    @Column(name = "published_by")
    private String publishedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "published_date")
    private Date publishedDate;

    private String title;

    @Version
    private Long version;

    @Transient
    private ArticleClass articleClass;

    public Article() {
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

    public Date getPublishedDate() {
        return this.publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArticleClass getArticleClass() {
        return this.articleClass;
    }

    public void setArticleClass(ArticleClass articleClass) {
        this.articleClass = articleClass;
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "article_class_id")
    protected String getDbArticleClass() {
        return this.articleClass == null ? null : articleClass.getCode();
    }

    protected void setDbArticleClass(String articleClassCode) {
        this.articleClass = ArticleClass.getArticleClass(articleClassCode);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
