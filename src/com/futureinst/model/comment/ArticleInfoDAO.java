package com.futureinst.model.comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hao on 2015/10/29.
 */
public class ArticleInfoDAO implements Serializable{
    private List<ArticleDAO> articles;
    private List<String> loves;
    private int size;

    public List<ArticleDAO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDAO> articles) {
        this.articles = articles;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getLoves() {
        return loves;
    }

    public void setLoves(List<String> loves) {
        this.loves = loves;
    }

    @Override
    public String toString() {
        return "ArticleInfoDAO{" +
                "articles=" + articles +
                ", loves=" + loves +
                ", size=" + size +
                '}';
    }
}
