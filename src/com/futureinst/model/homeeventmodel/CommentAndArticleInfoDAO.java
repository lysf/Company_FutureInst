package com.futureinst.model.homeeventmodel;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.comment.ArticleDAO;
import com.futureinst.model.comment.ArticleInfoDAO;
import com.futureinst.model.comment.CommentEventDetailInfoDAO;
import com.futureinst.model.comment.CommentInfoDAO;

import java.io.Serializable;

/**
 * Created by hao on 2015/10/23.
 */
public class CommentAndArticleInfoDAO extends BaseModel implements Serializable{
    private CommentEventDetailInfoDAO comment;
    private QueryEventDAO event;
    private ArticleInfoDAO article;

    public CommentEventDetailInfoDAO getComment() {
        return comment;
    }

    public void setComment(CommentEventDetailInfoDAO comment) {
        this.comment = comment;
    }

    public QueryEventDAO getEvent() {
        return event;
    }

    public void setEvent(QueryEventDAO event) {
        this.event = event;
    }

    public ArticleInfoDAO getArticle() {
        return article;
    }

    public void setArticle(ArticleInfoDAO article) {
        this.article = article;
    }
}
