package com.futureinst.model.point;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.ArticleDAO;
import com.futureinst.model.homeeventmodel.QueryEventDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/10/29.
 */
public class PointInfoDAO extends BaseModel implements Serializable {
    private List<ArticleDAO> articles;
    private ArticleDAO today_article;
    private String top_url;

    public List<ArticleDAO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDAO> articles) {
        this.articles = articles;
    }

    public ArticleDAO getToday_article() {
        return today_article;
    }

    public void setToday_article(ArticleDAO today_article) {
        this.today_article = today_article;
    }

    public String getTop_url() {
        return top_url;
    }

    public void setTop_url(String top_url) {
        this.top_url = top_url;
    }

    @Override
    public String toString() {
        return "PointInfoDAO{" +
                "articles=" + articles +
                ", today_article=" + today_article +
                '}';
    }
}
