package xxxxxx.yyyyyy.zzzzzz.domain.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import xxxxxx.yyyyyy.zzzzzz.domain.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    String QUERY_FIND_PAGE_BY = "SELECT a FROM Article a WHERE (a.title LIKE %:word% ESCAPE '~' OR a.overview LIKE %:word% ESCAPE '~') ORDER BY a.articleId DESC";

    @Query(QUERY_FIND_PAGE_BY)
    Page<Article> findPageBy(@Param("word") String word, Pageable pageable);

    @Query("SELECT a FROM Article a WHERE (a.title LIKE %:word% ESCAPE '~' OR a.overview LIKE %:word% ESCAPE '~')"
            + " AND a.publishedDate = :publishedDate")
    Page<Article> findPageBy(@Param("word") String word,
            @Param("publishedDate") Date publishedDate, Pageable pageable);

    // @Query(nativeQuery = true, name = "ArticleRepository.findOneByIdWithinLocked")
    // @Lock(LockModeType.PESSIMISTIC_WRITE)
    // @QueryHints(value = { @QueryHint(name = "javax.persistence.lock.timeout", value = "0") })
    @Query("SELECT a FROM Article a WHERE a.articleId = :articleId")
    Article findOneByIdWithinLocked(@Param("articleId") Long articleId);

    @Query("SELECT a FROM Article a"
            + " WHERE a.publishedDate = :publishedDate"
            + " ORDER BY a.articleId DESC")
    List<Article> findAllByPublishedDate(
            @Param("publishedDate") Date publishedDate);

    Page<Article> findPageByTitleContaining(String word, Pageable pageable);

}
