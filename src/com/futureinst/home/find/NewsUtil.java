package com.futureinst.home.find;

import android.content.Context;
import android.content.Intent;

import com.futureinst.charge.ChargeGoodsListActivity;
import com.futureinst.comment.CommentActivity;
import com.futureinst.comment.CommentDetailSecondActivity;
import com.futureinst.home.HomeActivity;
import com.futureinst.home.eventdetail.EventDetailActivity;
import com.futureinst.personalinfo.other.PersonalShowActivity;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.todaytask.TodayTaskActivity;

/**
 * Created by hao on 2015/11/1.
 */
public class NewsUtil {
    //0：默认的网页；1:用户；2：事件；3：事件评论；4：事件评论详情；
    // 5：观点；6：观点评论；7：观点评论详情；8：APP首页（./home）；
    // 9：充值页面(./charge)；10：每日任务(./daily_task)；11：个人信息编辑(./profile_edit)；
    private int type = 1;

    private String user_id;//  ./user/
    private String event_id;//  ./event/
    private String comment_id;//  /comments#
    private String comment_parent_id;//  ?parent_id=
    private String article_id;//   ./article/


    //解析
    public void analysisNews(String news){
        if(news.contains("./user/")){//只有用户
            setUser_id(news.replace("./user/", ""));
            setType(1);
            return;
        }
        else if(news.contains("./event/")){//事件
            if(news.contains("/comments#")){//事件评论
                if(news.contains("?parent_id=")){//评论详情
                    setComment_id(news.substring(news.indexOf("?parent_id=") + "?parent_id=".length()));
                    setComment_parent_id(news.substring(news.indexOf("/comments#") + "/comments#".length(), news.indexOf("?parent_id=")));
                    setEvent_id(news.substring(news.indexOf("./event/") + "./event/".length(), news.indexOf("/comments#")));
                    setType(4);
                    return;
                }
                setComment_id(news.substring(news.indexOf("/comments#") + "/comments#".length()));
                setEvent_id(news.substring(news.indexOf("./event/") + "./event/".length(), news.indexOf("/comments#")));
                setType(3);
                return;
            }
            setEvent_id(news.substring(news.indexOf("./event/") + "./event/".length()));
            setType(2);
            return;
        }
        else if(news.contains("./article/")){//观点文章
            if(news.contains("/comments#")){//观点文章评论
                if(news.contains("?parent_id=")){//观点文章评论详情
                    setComment_id(news.substring(news.indexOf("?parent_id=")+"?parent_id=".length()));
                    setComment_parent_id(news.substring(news.indexOf("/comments#") + "/comments#".length(), news.indexOf("?parent_id=")));
                    setArticle_id(news.substring(news.indexOf("./article/") + "./article/".length(), news.indexOf("/comments#")));
                    setType(7);
                    return;
                }
                setComment_id(news.substring(news.indexOf("/comments#") + "/comments#".length()));
                setArticle_id(news.substring(news.indexOf("./article/") + "./article/".length(), news.indexOf("/comments#")));
                setType(6);
                return;
            }
            setArticle_id(news.substring(news.indexOf("./article/") + "./article/".length()));
            setType(5);
            return;
        }
        else if(news.contains("./home")){//首页
            setType(8);
            return;
        }
        else if(news.contains("./charge")){//充值
            setType(9);
            return;
        }
        else if(news.contains("./daily_task")){//每日任务
            setType(10);
            return;
        }
        else if(news.contains("./profile_edit")){//编辑个人信息
            setType(11);
            return;
        }
        else{
            setType(0);
            return;
        }
    }
    //点击事件响应(true:命中，事件消耗；false:默认的网页，其他处理)
    public boolean clickListener(Context context,String url){
        //解析url类别
        analysisNews(url);
        SharePreferenceUtil preferenceUtil = SharePreferenceUtil.getInstance(context);
        switch (getType()){
            case 1:
                if(this.getUser_id().equals(preferenceUtil.getID()+"")){
                    ((HomeActivity)context).setTab(3);
                }else{
                    Intent intent = new Intent(context, PersonalShowActivity.class);
                    intent.putExtra("id", this.getUser_id());
                    context.startActivity(intent);
                }
                return true;
            case 2:
                Intent eventIntent = new Intent(context, EventDetailActivity.class);
                eventIntent.putExtra("eventId",this.getEvent_id());
                context.startActivity(eventIntent);
                return true;
            case 3:
                Intent eventCommentIntent = new Intent(context, CommentActivity.class);
                eventCommentIntent.putExtra("eventId",this.getEvent_id());
                context.startActivity(eventCommentIntent);
                return true;
            case 4:
                Intent eventCommentDetailIntent = new Intent(context, CommentDetailSecondActivity.class);
                eventCommentDetailIntent.putExtra("event_id",this.getEvent_id());
                eventCommentDetailIntent.putExtra("from",true);
                eventCommentDetailIntent.putExtra("comment_id",this.getComment_id());
                eventCommentDetailIntent.putExtra("parent_id",this.getComment_parent_id());
                context.startActivity(eventCommentDetailIntent);
                return true;
            case 5:
            case 6:
                Intent articleIntent = new Intent(context, ArticleDetailActivity.class);
                articleIntent.putExtra("article_id", this.getArticle_id());
                context.startActivity(articleIntent);
                return true;
            case 7:
                Intent articleCommentDetailIntent = new Intent(context, CommentDetailSecondActivity.class);
                articleCommentDetailIntent.putExtra("article_id",this.getArticle_id());
                articleCommentDetailIntent.putExtra("from",false);
                articleCommentDetailIntent.putExtra("comment_id",this.getComment_id());
                articleCommentDetailIntent.putExtra("parent_id",this.getComment_parent_id());
                context.startActivity(articleCommentDetailIntent);
                return true;
            case 8://首页
                Intent homeIntent = new Intent(context,HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(homeIntent);
                return true;
            case 9://充值
                Intent chargeIntent = new Intent(context, ChargeGoodsListActivity.class);
                context.startActivity(chargeIntent);
                return true;
            case 10://每日任务
                Intent taskIntent = new Intent(context, TodayTaskActivity.class);
                context.startActivity(taskIntent);
                return true;
            case 11://编辑个人信息

                return true;
        }
        return false;
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
