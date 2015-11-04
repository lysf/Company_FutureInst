package com.futureinst.model.comment;

import com.futureinst.model.basemodel.BaseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/10/28.
 */
public class CommentEventDetailInfoDAO extends BaseModel {
    private List<CommentDAO> comments;
    private Map<String,CommentDAO> lastChildCommentMap;
    private List<String> loves;

    public Map<String, CommentDAO> getLastChildCommentMap() {
        return lastChildCommentMap;
    }

    public void setLastChildCommentMap(Map<String, CommentDAO> lastChildCommentMap) {
        this.lastChildCommentMap = lastChildCommentMap;
    }

    public List<String> getLoves() {
        return loves;
    }

    public void setLoves(List<String> loves) {
        this.loves = loves;
    }

    public List<CommentDAO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDAO> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "CommentEventDetailInfoDAO{" +
                "comments=" + comments +
                '}';
    }
}
