package com.futureinst.home.find;

/**
 * Created by hao on 2015/11/1.
 */
public class NewsUtil {
    private int type = 1;//1:用户；2：事件；3：事件评论；4：事件评论详情；5：观点；6：观点评论；7：观点评论详情
    private String user_id;//  ./user/
    private String event_id;//  ./event/
    private String comment_id;//  /comments#
    private String comment_parent_id;//  ?parent_id=
    private String article_id;//   ./article/
    public static NewsUtil newsUtil;
    public static NewsUtil getInstance(){
        if(newsUtil == null){
            newsUtil = new NewsUtil();
        }
        return newsUtil;
    }

    //解析
    public void analysisNews(String news){
        if(news.contains("./user/")){//只有用户
            setUser_id(news.replace("./user/",""));
            setType(1);
            return;
        }
        if(news.contains("./event/")){//事件
            if(news.contains("/comments#")){//事件评论
                if(news.contains("?parent_id=")){//评论详情
                    setComment_parent_id(news.substring(news.indexOf("?parent_id=")+"?parent_id=".length()));
                    setComment_id(news.substring(news.indexOf("/comments#") + "/comments#".length(), news.indexOf("?parent_id=")));
                    setEvent_id(news.substring(news.indexOf("./event/") + "./event/".length(),news.indexOf("/comments#")));
                    setType(4);
                    return;
                }
                setComment_id(news.substring(news.indexOf("/comments#") + "/comments#".length()));
                setEvent_id(news.substring(news.indexOf("./event/") + "./event/".length(),news.indexOf("/comments#")));
                setType(3);
                return;
            }
            setEvent_id(news.substring(news.indexOf("./event/") + "./event/".length()));
            setType(2);
            return;
        }
        if(news.contains("./article/")){//观点文章
            if(news.contains("/comments#")){//观点文章评论
                if(news.contains("?parent_id=")){//观点文章评论详情
                    setComment_parent_id(news.substring(news.indexOf("?parent_id=")+"?parent_id=".length()));
                    setComment_id(news.substring(news.indexOf("/comments#") + "/comments#".length(), news.indexOf("?parent_id=")));
                    setEvent_id(news.substring(news.indexOf("./article/") + "./article/".length(),news.indexOf("/comments#")));
                    setType(7);
                    return;
                }
                setComment_id(news.substring(news.indexOf("/comments#") + "/comments#".length()));
                setEvent_id(news.substring(news.indexOf("./article/") + "./article/".length(),news.indexOf("/comments#")));
                setType(6);
                return;
            }
            setEvent_id(news.substring(news.indexOf("./article/") + "./article/".length()));
            setType(5);
            return;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_parent_id() {
        return comment_parent_id;
    }

    public void setComment_parent_id(String comment_parent_id) {
        this.comment_parent_id = comment_parent_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }
}
