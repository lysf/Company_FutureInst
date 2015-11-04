package com.futureinst.model.point;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.ArticleDAO;

import java.io.Serializable;

/**
 * Created by hao on 2015/11/4.
 */
public class ArticleDetailDAO extends BaseModel implements Serializable{
    private int love;
    private String detail;
    private ArticleDAO article;
    private int award;

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ArticleDAO getArticle() {
        return article;
    }

    public void setArticle(ArticleDAO article) {
        this.article = article;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    @Override
    public String toString() {
        return "ArticleDetailDAO{" +
                "love=" + love +
                ", detail='" + detail + '\'' +
                ", article=" + article +
                ", award=" + award +
                '}';
    }
}
