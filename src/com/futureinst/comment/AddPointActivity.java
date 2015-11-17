package com.futureinst.comment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyToast;
import com.futureinst.widget.richeditor.RichEditor;

import org.json.JSONException;

/**
 * Created by hao on 2015/10/22.
 */
public class AddPointActivity extends BaseActivity {
    private EditText et_point_title;
    private RichEditor et_point_content;
    private String event_id;
    private ImageView iv_image;

    private EditText et_point;
    @Override
    protected void localOnCreate(Bundle savedInstanceState) {
        setTitle("写观点");
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        setRight("发送");
        setContentView(R.layout.activity_add_point);
        initView();
    }

    @Override
    protected void onRightClick(View view) {
        super.onRightClick(view);//添加观点
        String title = et_point_title.getText().toString().trim();
        String content = et_point.getText().toString().trim();
        if(judgeDate(title,content)){
            add_article(event_id,title,content);
        }

    }

    private void initView() {
        event_id = getIntent().getStringExtra("event_id");
        et_point_title = (EditText)findViewById(R.id.et_point_title);
        et_point_content = (RichEditor)findViewById(R.id.et_point_content);
        iv_image = (ImageView)findViewById(R.id.iv_image);
        et_point = (EditText)findViewById(R.id.et_point);


        et_point_content.setEditorHeight(300);
        et_point_content.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override public void onTextChange(String text) {
//                mPreview.setText(text);
            }
        });
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_point_content.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
            }
        });

    }

    //添加观点
    private void add_article(String event_id,String title,String content){
        progressDialog.progressDialog();
        Content.isPull = true;
        httpResponseUtils.postJson(httpPostParams.getPostParams(
                        PostMethod.add_article.name(), PostType.article.name(),
                        httpPostParams.add_article(preferenceUtil.getID() + "", preferenceUtil.getUUid(),
                                event_id, title, content)),
                BaseModel.class,
                new PostCommentResponseListener() {
                    @Override
                    public void requestCompleted(Object response) throws JSONException {
                        progressDialog.cancleProgress();
                        Content.isPull = false;
                        if(response == null) return;
                        MyToast.getInstance().showToast(AddPointActivity.this, "您的观点已提交，请等待审核", 1);
                        finish();

                    }
                });
    }
    //判断数据是否合理
    private boolean judgeDate(String title,String content){
        if(TextUtils.isEmpty(title)){
            MyToast.getInstance().showToast(AddPointActivity.this, "请输入标题", 0);
            return false;
        }
        if(TextUtils.isEmpty(content)){
            MyToast.getInstance().showToast(AddPointActivity.this, "请输入您的观点", 0);
            return false;
        }
        return true;
    }
}
