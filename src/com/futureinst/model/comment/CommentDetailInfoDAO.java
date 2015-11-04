package com.futureinst.model.comment;

import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.homeeventmodel.QueryEventDAO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by hao on 2015/10/29.
 */
public class CommentDetailInfoDAO extends BaseModel implements Serializable {
    private List<CommentDAO>childComments;
    private Map<String,CommentDAO> replyToCommentMap;
    private List<String> loves;
    private QueryEventDAO event;
    private CommentDAO parentComment;

    public Map<String, CommentDAO> getReplyToCommentMap() {
        return replyToCommentMap;
    }

    public void setReplyToCommentMap(Map<String, CommentDAO> replyToCommentMap) {
        this.replyToCommentMap = replyToCommentMap;
    }

    public List<CommentDAO> getChildComments() {
        return childComments;
    }

    public void setChildComments(List<CommentDAO> childComments) {
        this.childComments = childComments;
    }

    public List<String> getLoves() {
        return loves;
    }

    public void setLoves(List<String> loves) {
        this.loves = loves;
    }

    public QueryEventDAO getEvent() {
        return event;
    }

    public void setEvent(QueryEventDAO event) {
        this.event = event;
    }

    public CommentDAO getParentComment() {
        return parentComment;
    }

    public void setParentComment(CommentDAO parentComment) {
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return "CommentDetailInfoDAO{" +
                "childComments=" + childComments +
                ", event=" + event +
                ", parentComment=" + parentComment +
                '}';
    }
}
