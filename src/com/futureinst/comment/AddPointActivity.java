package com.futureinst.comment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.model.basemodel.UpFileDAO;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.ImageCompressUtil;
import com.futureinst.utils.MyToast;
import com.futureinst.widget.richeditor.RichEditor;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

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
        String content = et_point_content.getHtml().toString().trim();
//        Log.i(TAG,"--------content---"+content);
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
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageCompressUtil.selectImageFromLocal(AddPointActivity.this);

                hideSoftInputView();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Content.REQUESTCODE_TAKE_LOCAL) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
            String path = "";
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(index);
            }
            cursor.close();
            List<String> list = new ArrayList<>();
            list.add(ImageCompressUtil.getimage(path));
            uploadPic(list);
        }
    }

    //上传图片
    private void uploadPic(final List<String> files) {
        progressDialog.progressDialog();
        httpResponseUtils.UploadFileRequest(files, httpPostParams.getPostParams(PostMethod.upload_image.name(), "topic",
                httpPostParams.upLoadFile(preferenceUtil.getID() + "",
                        preferenceUtil.getUUid())), UpFileDAO.class, new PostCommentResponseListener() {
            @Override
            public void requestCompleted(Object response) throws JSONException {
                progressDialog.cancleProgress();
                if (response == null) {
                    return;
                }
                et_point_content.insertImage(((UpFileDAO) response).getSrc(),
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
