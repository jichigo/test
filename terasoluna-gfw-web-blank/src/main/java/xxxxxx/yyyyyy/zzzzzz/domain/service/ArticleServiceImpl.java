package xxxxxx.yyyyyy.zzzzzz.domain.service;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.date.DateFactory;
import org.terasoluna.gfw.common.exception.ResourceNotFoundException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;
import org.terasoluna.gfw.common.query.QueryEscapeUtils;

import xxxxxx.yyyyyy.zzzzzz.domain.model.Article;
import xxxxxx.yyyyyy.zzzzzz.domain.model.ArticleClass;
import xxxxxx.yyyyyy.zzzzzz.domain.repository.ArticleRepository;

@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    @Inject
    ArticleRepository articleRepository;

    @Inject
    DateFactory dateFactory;

    @Transactional(readOnly = true)
    public Page<Article> searchArticle(ArticleSearchCriteria criteria,
            Pageable pageable) {

        String word = QueryEscapeUtils
                .toContainingCondition(criteria.getWord());

        Page<Article> page = articleRepository.findPageBy(word, pageable);

        return page;
    }

    public Article find(Long articleId) {
        Article article = articleRepository.findOne(articleId);
        if (article == null) {
            throw new ResourceNotFoundException(ResultMessages.error().add(
                    ResultMessage
                            .fromText(String.format(
                                    "Article Not found. Article ID is '%d'",
                                    articleId))));
        }
        return article;
    }

    public Article create(Article newArticle) {
        newArticle.setPublishedDate(dateFactory.newDate());
//        newArticle.setArticleClass(ArticleClass.Internal);
        return articleRepository.saveAndFlush(newArticle);
    }

    public Article update(Article article) {
        Article storedArticle = articleRepository
                .findOneByIdWithinLocked(article.getArticleId());
        storedArticle.setTitle(article.getTitle());
        storedArticle.setOverview(article.getOverview());
        storedArticle.setContent(article.getContent());
        storedArticle.setPublishedBy(article.getPublishedBy());

        return articleRepository.saveAndFlush(storedArticle);
    }

    public void delete(Long articleId) {
        Article article = find(articleId);
        articleRepository.findOneByIdWithinLocked(article.getArticleId());
        articleRepository.delete(article);
        articleRepository.flush();
    }

}
