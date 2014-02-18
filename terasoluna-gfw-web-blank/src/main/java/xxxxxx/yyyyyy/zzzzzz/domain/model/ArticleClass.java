package xxxxxx.yyyyyy.zzzzzz.domain.model;

/**
 * The persistent class for the article_class database table.
 */
public enum ArticleClass {
    Internal("01"), International("02"), Economy("03"), Entertainment(
            "04"), Science("05"), InformationTechnology(
            "06"), Life("07"), Region("08");
    private final String code;

    private ArticleClass(String code) {
        this.code = code;
    }

    public static ArticleClass getArticleClass(String code) {
        for (ArticleClass articleClass : values()) {
            if (articleClass.code.equals(code)) {
                return articleClass;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

}
