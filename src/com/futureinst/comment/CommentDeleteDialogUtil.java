package com.futureinst.comment;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.futureinst.R;
import com.futureinst.utils.DialogShow;

/**
 * Created by hao on 2015/10/27.
 */
public class CommentDeleteDialogUtil {
    public static  CommentDeleteDialogUtil commentDeleteDialogUtil;
    public static CommentDeleteDialogUtil newInstance(){
        if(commentDeleteDialogUtil == null){
            commentDeleteDialogUtil = new CommentDeleteDialogUtil();
        }
        return commentDeleteDialogUtil;
    }

    public void show(Activity context,final DelCommentListener clickListener){
        View view = LayoutInflater.from(context).inflate(R.layout.view_comment_del_dialog,null);
        Button btn_del = (Button)view.findViewById(R.id.btn_delete);
        Button btn_cancel = (Button)view.findViewById(R.id.btn_cancel);
        final Dialog dialog = DialogShow.showDialog(context,view, Gravity.BOTTOM);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClickListener();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
     public interface DelCommentListener{
        void onClickListener();
    }

}
