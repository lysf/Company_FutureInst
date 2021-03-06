package com.futureinst.comment;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.model.basemodel.BaseModel;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.utils.MyToast;
import com.futureinst.widget.CustomView_Image_Text;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddCommentActivity extends BaseActivity {
	private LinearLayout ll_add_comment;
	private CustomView_Image_Text btn_good,btn_bad;
	private EditText et_comment;
    private int attitude = 0;//1:看好，3：不看好
	private int flag = 0;
	String event_id;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("写评论");
		getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
		setRight("发送");
//		getRightButton().setTextColor(getResources().getColor(R.color.text_color_white));
//		getRightButton().setBackgroundColor(Color.parseColor("#F8E71C"));
		setContentView(R.layout.activity_add_comment);
		initView();
	}
	@Override
	protected void onLeftImageViewClick(View view) {
		super.onLeftImageViewClick(view);
		finish();
	}
	@Override
	protected void onRightClick(View view) {
		super.onRightClick(view);
		if(judgeDate()){
			addComment(flag, et_comment.getText().toString().trim());
		}
	}
	private void initView() {
		ll_add_comment = (LinearLayout) findViewById(R.id.ll_add_comment);
        attitude = getIntent().getIntExtra("attitude",0);
		event_id = getIntent().getStringExtra("eventId");
		btn_good = (CustomView_Image_Text) findViewById(R.id.btn_attention_good);
		btn_bad = (CustomView_Image_Text) findViewById(R.id.btn_attention_bad);
		et_comment = (EditText) findViewById(R.id.et_comment);
		btn_good.setOnClickListener(clickListener);
		btn_bad.setOnClickListener(clickListener);
		ll_add_comment.setOnClickListener(clickListener);
        switch (attitude){
            case 1:
                btn_good.setSelected(true);
                btn_bad.setSelected(false);
                flag = 1;
                break;
            case 3:
                btn_good.setSelected(false);
                btn_bad.setSelected(true);
                flag = 2;
                break;
            default:break;
        }


	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_attention_good://看好
				btn_good.setSelected(true);
				btn_bad.setSelected(false);
				flag = 1;
				break;
			case R.id.btn_attention_bad://不看好
				btn_good.setSelected(false);
				btn_bad.setSelected(true);
				flag = 2;
				break;
			case R.id.ll_add_comment:
				hideSoftInputView();
				break;
			}
			
		}
	};
	//添加评论
		private void addComment(int attitude,String content){
			progressDialog.progressDialog();
				httpResponseUtils.postJson(httpPostParams.getPostParams(
						PostMethod.add_comment.name(), PostType.comment.name(), 
						httpPostParams.add_comment(preferenceUtil.getID()+"", preferenceUtil.getUUid(), event_id, attitude, content)), 
						BaseModel.class, 
						new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if(response == null) return;
						MyToast.getInstance().showToast(AddCommentActivity.this, "您的评论发布成功", 1);
						finish();
					}
				});
			}
	private boolean judgeDate(){
			if(flag == 0){
				MyToast.getInstance().showToast(this, "请选择您的态度", 0);
				return false;
			}
			if(TextUtils.isEmpty(et_comment.getText().toString().trim())){
				MyToast.getInstance().showToast(this, "请填写您的评论", 0);
				return false;
			}
			return true;
	}
}
