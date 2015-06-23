package com.futureinst.home.userinfo;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.model.usermodel.UserInformationDAO;
import com.futureinst.model.usermodel.UserInformationInfo;
import com.futureinst.net.HttpPostParams;
import com.futureinst.net.HttpResponseUtils;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.push.PushMessageUtils;
import com.futureinst.sharepreference.SharePreferenceUtil;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.ToastUtils;

import de.keyboardsurfer.android.widget.crouton.Style;

public class UserInfoFragment extends BaseFragment {
	private UserInformationDAO userInformationDAO;
	private MyProgressDialog progressDialog;
	private SharePreferenceUtil preferenceUtil;
	private HttpPostParams httpPostParams;
	private HttpResponseUtils httpResponseUtils;
	private TextView tv_userName,tv_description;
	private TextView tv_message_count;
	private ImageView iv_message,iv_edit_description;
	private TextView tv_asset;//可交易未币
	private TextView tv_assure;//保证金
	private Button btn_bill;//对账单
	private Button btn_store;//未币商城
	private Button btn_question;//常见问题
	private Button btn_aboutUs;//关于我们
	private PushMessageUtils pushMessageUtils;
	private Button btn_loginOut;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_user_info);
		initView();
		setClickListener();
		query_user_record();
		getMessageCount();
	}
	//获取未读消息数量
	private void getMessageCount(){
		int count = pushMessageUtils.getUnReadMessageCount();
		Log.i("count", "---------count--->>"+count);
		if(count>0){
			tv_message_count.setText(count+"");
			tv_message_count.setVisibility(View.VISIBLE);
		}else{
			tv_message_count.setText("0");
			tv_message_count.setVisibility(View.INVISIBLE);
		}
	}
	private void initView() {
		pushMessageUtils = PushMessageUtils.getInstance(getContext());
		progressDialog = MyProgressDialog.getInstance(getContext());
		preferenceUtil = SharePreferenceUtil.getInstance(getContext());
		httpResponseUtils = HttpResponseUtils.getInstace(getActivity());
		httpPostParams = HttpPostParams.getInstace();
		userInformationDAO = new UserInformationDAO();
		tv_userName = (TextView) findViewById(R.id.tv_userName);
		tv_description = (TextView) findViewById(R.id.tv_description);
		tv_message_count = (TextView) findViewById(R.id.tv_message_count);
		iv_message = (ImageView) findViewById(R.id.iv_message);
		iv_edit_description = (ImageView) findViewById(R.id.iv_edit_description);
		
		tv_asset = (TextView) findViewById(R.id.tv_asset);
		tv_assure = (TextView) findViewById(R.id.tv_assure);
		
		btn_bill = (Button) findViewById(R.id.btn_bill);
		btn_store = (Button) findViewById(R.id.btn_store);
		btn_question = (Button) findViewById(R.id.btn_question);
		btn_aboutUs = (Button) findViewById(R.id.btn_aboutUs);
		btn_loginOut = (Button) findViewById(R.id.btn_log_out);
		
	}
	//初始化视图
	private void initData(UserInformationDAO userInfo){
		if(!TextUtils.isEmpty(userInfo.getUser().getName())){
			tv_userName.setText(userInfo.getUser().getName());
		}
		if(!TextUtils.isEmpty(userInfo.getUser().getDescription())){
			tv_description.setText(userInfo.getUser().getDescription());
		}
		tv_asset.setText(String.format("%.1f", userInfo.getAsset()));
		tv_assure.setText(String.format("%.1f", userInfo.getAssure()));
	}
	private void setClickListener(){
		iv_message.setOnClickListener(clickListener);
		iv_edit_description.setOnClickListener(clickListener);
		tv_userName.setOnClickListener(clickListener);
		btn_bill.setOnClickListener(clickListener);
		btn_store.setOnClickListener(clickListener);
		btn_question.setOnClickListener(clickListener);
		btn_aboutUs.setOnClickListener(clickListener);
		btn_loginOut.setOnClickListener(clickListener);
	}
	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_userName://用户名
				showEditName();
				break;
			case R.id.iv_edit_description://先知描述
				showEditDescription();
				break;
			case R.id.iv_message://消息
				startActivity(new Intent(getActivity(), PushMessageActivity.class));
				tv_message_count.setVisibility(View.INVISIBLE);
				break;
			case R.id.btn_bill://对账单
				startActivity(new Intent(getActivity(), UserCheckActivity.class));
				break;
			case R.id.btn_store://未币商城
				ToastUtils.showToast(getActivity(), "即将上线，敬请期待！", 3000, Style.CONFIRM);
				break;
			case R.id.btn_question://常见问题
				startActivity(new Intent(getActivity(), FAQActivity.class));
				break;
			case R.id.btn_aboutUs://关于我们
				startActivity(new Intent(getActivity(), AboutUsActivity.class));
				break;
			case R.id.btn_log_out://退出登录
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				preferenceUtil.setUUid("");
				startActivity(intent);
				getActivity().finish();
				break;
			}
		}
	};
	//修改用户信息
	private void update_user(final String userName,final String description){
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.update_user.name(), PostType.user.name(), 
				httpPostParams.update_user(preferenceUtil.getUUid(), preferenceUtil.getID()+"", userName,description)), 
				UserInfo.class,
				new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if(response == null) return;
						userInformationDAO.getUser().setName(userName);
						userInformationDAO.getUser().setDescription(description);
						initData(userInformationDAO);
					}
				});
	}
	//获取用户信息
	private void query_user_record(){
		progressDialog.progressDialog();
		httpResponseUtils.postJson(httpPostParams.getPostParams(PostMethod.query_user_record.name(), PostType.user_info.name(), 
				httpPostParams.query_user_record(preferenceUtil.getID()+"",preferenceUtil.getUUid())), 
				UserInformationInfo.class,
				new PostCommentResponseListener() {
			@Override
			public void requestCompleted(Object response) throws JSONException {
				progressDialog.cancleProgress();
				if(response == null) return;
				UserInformationInfo userInformationInfo = (UserInformationInfo) response;
				userInformationDAO = userInformationInfo.getUser_record();
				initData(userInformationDAO);
			}
		});
	}
	//修改先知描述
	private void showEditDescription(){
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_edit_userinfo_description, null, false);
		final EditText et_description = (EditText) view.findViewById(R.id.et_user_desccription);
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		final Dialog dialog = DialogShow.showDialog(getActivity(), view, Gravity.CENTER);
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String description = et_description.getText().toString().trim();
				if(TextUtils.isEmpty(description))
					ToastUtils.showToast(getActivity(), "请输入内容！", 3000, Style.ALERT);
				update_user(userInformationDAO.getUser().getName(),description);
				dialog.dismiss();
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	//修改名称
	private void showEditName(){
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_edit_user_name, null, false);
		final EditText et_name = (EditText) view.findViewById(R.id.et_user_name);
		et_name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					if(s.toString().getBytes("GB2312").length > 14){
						ToastUtils.showToast(getActivity(), getResources().getString(R.string.regist_userName_tip), 2000, Style.ALERT);
						et_name.setText("");
						return;
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		final Dialog dialog = DialogShow.showDialog(getActivity(), view, Gravity.CENTER);
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = et_name.getText().toString().trim();
				if(TextUtils.isEmpty(name))
					ToastUtils.showToast(getActivity(), "请输入姓名！", 3000, Style.ALERT);
				update_user(name,userInformationDAO.getUser().getDescription());
				dialog.dismiss();
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}
