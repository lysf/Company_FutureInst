package com.futureinst.home.eventdetail.eventdetailabout;

import android.app.Activity;
import android.util.Log;

import com.futureinst.model.homeeventmodel.QueryEventDAO;
import com.futureinst.utils.lottery.LotteryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2015/12/18.
 */
public class OrderTip {
    //下单提示（分享、评论）
    
    public static void orderTip(final Activity activity,final QueryEventDAO event,int commentNum, int share_award,int attitude) {
        List<Double> orignalRates = new ArrayList<>();
        if (commentNum == 0
                && share_award == 0) {//该事件还未评论过，也没有分享过，提示评论或分享
            orignalRates.add(0.3);
            orignalRates.add(0.2);
            orignalRates.add(0.5);
            switch (LotteryUtil.lottery(orignalRates)) {
                case 0://分享
                    ShareCommentDialog.showShareCommentDialog(activity, attitude, event, share_award);
                    break;
                case 1://评论
                    EditCommentDialog.showEditCommentDialog(activity, attitude, event.getId()+"");
                    break;
                case 2://什么都不做
                    break;
            }
        } else if (commentNum == 0
                && share_award > 0) {//未评论，有过分享
            orignalRates.add(0.5);
            orignalRates.add(0.5);
            if (LotteryUtil.lottery(orignalRates) == 0) {//评论
                EditCommentDialog.showEditCommentDialog(activity, attitude, event.getId()+"");
            }
        } else if (commentNum > 0
                && share_award == 0) {//有过评论，没有分享
            orignalRates.add(0.5);
            orignalRates.add(0.5);
            if (LotteryUtil.lottery(orignalRates) == 0) {//分享
                ShareCommentDialog.showShareCommentDialog(activity, attitude, event, share_award);
            }
        }
    }
}
