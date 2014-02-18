package xxxxxx.yyyyyy.zzzzzz.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import xxxxxx.yyyyyy.zzzzzz.domain.model.Article;

public interface ArticleService {

    Page<Article> searchArticle(ArticleSearchCriteria criteria,
            Pageable pageable);

    Article find(Long articleId);

    Article create(Article newArticle);

    Article update(Article article);

    void delete(Long articleId);

}
