package com.futureinst.home.userinfo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseFragment;
import com.futureinst.login.LoginActivity;
import com.futureinst.model.global.Content;
import com.futureinst.model.order.UnDealOrderDAO;
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
import com.futureinst.utils.ImageCompressUtil;
import com.futureinst.utils.MyProgressDialog;
import com.futureinst.utils.MyToast;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.TableRow;
import android.widget.TextView;

public class UserInfoFragment2 extends BaseFragment {
	private UserInformationDAO userInformationDAO;
	private MyProgressDialog progressDialog;
	private SharePreferenceUtil preferenceUtil;
	private HttpPostParams httpPostParams;
	private HttpResponseUtils httpResponseUtils;
	private ImageView iv_headImag;
	private TextView tv_userName, tv_description;
	private TextView tv_message_count;
	private ImageView iv_message, iv_edit_description;
	private TableRow[] tableRows;
	private PushMessageUtils pushMessageUtils;
	private TextView tv_useableIcon,tv_depositCash;
	private boolean isStart;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setContentView(R.layout.fragment_userinfo);
		initView();
		setClickListener();
		query_user_record();
		getMessageCount();
		isStart =true;
	}
	@Override
	public void onResume() {
		query_user_record();
		getMessageCount();
		super.onResume();
	}
	// 获取未读消息数量
	private void getMessageCount() {
		int count = pushMessageUtils.getUnReadMessageCount();
		if (count > 0) {
			tv_message_count.setText(count + "");
			tv_message_count.setVisibility(View.VISIBLE);
		} else {
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
		iv_headImag = (ImageView) findViewById(R.id.iv_headImg);
		tableRows = new TableRow[5];
		tableRows[0] = (TableRow) findViewById(R.id.tableRow0);
		tableRows[1] = (TableRow) findViewById(R.id.tableRow1);
		tableRows[2] = (TableRow) findViewById(R.id.tableRow2);
		tableRows[3] = (TableRow) findViewById(R.id.tableRow3);
		tableRows[4] = (TableRow) findViewById(R.id.tableRow4);
		tv_useableIcon = (TextView) findViewById(R.id.tv_useableIcon);
		tv_depositCash = (TextView) findViewById(R.id.tv_depositCash);

	}
	
	// 初始化视图
	private void initData(UserInformationDAO userInfo) {
		if (!TextUtils.isEmpty(userInfo.getUser().getName())) {
			tv_userName.setText(userInfo.getUser().getName());
		}
		if (!TextUtils.isEmpty(userInfo.getUser().getDescription())) {
			tv_description.setText(userInfo.getUser().getDescription());
		}
		tv_useableIcon.setText(String.format("%.1f", userInfo.getAsset()));
		tv_depositCash.setText(String.format("%.1f", userInfo.getAssure()));
	}

	private void setClickListener() {
		iv_message.setOnClickListener(clickListener);
		iv_edit_description.setOnClickListener(clickListener);
		tv_userName.setOnClickListener(clickListener);
		tableRows[0].setOnClickListener(clickListener);
		tableRows[1].setOnClickListener(clickListener);
		tableRows[2].setOnClickListener(clickListener);
		tableRows[3].setOnClickListener(clickListener);
		tableRows[4].setOnClickListener(clickListener);
		iv_headImag.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_headImg:// 头像
//				showPicDialog();
				break;
			case R.id.tv_userName:// 用户名
				showEditName();
				break;
			case R.id.iv_edit_description:// 先知描述
				showEditDescription();
				break;
			case R.id.iv_message:// 消息
				startActivity(new Intent(getActivity(), PushMessageActivity.class));
				tv_message_count.setVisibility(View.INVISIBLE);
				break;
//			case R.id.tableRow0://我的资产
//				Intent intentAseet = new Intent(getActivity(), MyAseetActivity.class);
//				intentAseet.putExtra("userInfo", userInformationDAO);
//				startActivity(intentAseet);
//				break;
			case R.id.tableRow0:// 对账单
				startActivity(new Intent(getActivity(), UserCheckActivity.class));
				break;
			case R.id.tableRow1:// 未币商城
				MyToast.showToast(getActivity(), "即将上线，敬请期待！", 1);
				break;
			case R.id.tableRow2:// 常见问题
				startActivity(new Intent(getActivity(), FAQActivity.class));
				break;
			case R.id.tableRow3:// 关于我们
				startActivity(new Intent(getActivity(), AboutUsActivity.class));
				break;
			case R.id.tableRow4:// 退出登录
				loginOut();
				break;
			}
		}
	};
	private void loginOut(){
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_hold_delete_tip, null, false);
		final Dialog dialog = DialogShow.showDialog(getActivity(), view, Gravity.CENTER);
		TextView tv_tips = (TextView) view.findViewById(R.id.tv_tips);
		tv_tips.setText(getResources().getString(R.string.unhold_revoke));
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		tv_tips.setText(getResources().getString(R.string.login_out_tip));
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			} 
		});
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				preferenceUtil.setUUid("");
				startActivity(intent);
				getActivity().finish();
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	// 修改用户信息
	private void update_user(final String userName, final String description) {
		progressDialog.progressDialog();
		httpResponseUtils.postJson(
				httpPostParams.getPostParams(PostMethod.update_user.name(),
						PostType.user.name(), httpPostParams.update_user(preferenceUtil.getUUid(),
								preferenceUtil.getID() + "", userName, description)),
				UserInfo.class, new PostCommentResponseListener() {
					@Override
					public void requestCompleted(Object response) throws JSONException {
						progressDialog.cancleProgress();
						if (response == null)
							return;
						userInformationDAO.getUser().setName(userName);
						userInformationDAO.getUser().setDescription(description);
						initData(userInformationDAO);
					}
				});
	}

	// 获取用户信息
	private void query_user_record() {
//		progressDialog.progressDialog();
		httpResponseUtils
				.postJson(
						httpPostParams.getPostParams(PostMethod.query_user_record.name(), PostType.user_info.name(),
								httpPostParams.query_user_record(preferenceUtil.getID() + "",
										preferenceUtil.getUUid())),
						UserInformationInfo.class, new PostCommentResponseListener() {
							@Override
							public void requestCompleted(Object response) throws JSONException {
								progressDialog.cancleProgress();
								if (response == null)
									return;
								UserInformationInfo userInformationInfo = (UserInformationInfo) response;
								userInformationDAO = userInformationInfo.getUser_record();
								initData(userInformationDAO);
							}
						});
	}

	// 修改先知描述
	private void showEditDescription() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_edit_userinfo_description, null, false);
		final EditText et_description = (EditText) view.findViewById(R.id.et_user_desccription);
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		final Dialog dialog = DialogShow.showDialog(getActivity(), view, Gravity.CENTER);
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String description = et_description.getText().toString().trim();
				if (TextUtils.isEmpty(description))
					MyToast.showToast(getActivity(), "请输入内容！", 1);
				update_user(userInformationDAO.getUser().getName(), description);
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

	// 修改名称
	private void showEditName() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_edit_user_name, null, false);
		final EditText et_name = (EditText) view.findViewById(R.id.et_user_name);
		et_name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					if (s.toString().getBytes("GB2312").length > 14) {
						MyToast.showToast(getActivity(), getResources().getString(R.string.regist_userName_tip), 1);
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
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		final Dialog dialog = DialogShow.showDialog(getActivity(), view, Gravity.CENTER);
		btn_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = et_name.getText().toString().trim();
				if (TextUtils.isEmpty(name))
					MyToast.showToast(getActivity(), "请输入姓名！", 0);
				update_user(name, userInformationDAO.getUser().getDescription());
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("onActivityResult", "---arg1=" + requestCode + ",arg0=" + resultCode + "intent=" + data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case Content.REQUESTCODE_TAKE_CAMERA:// 相机
				String path = Content.CAMERA_PATH;
				path = ImageCompressUtil.getimage(path);
				List<String> list = new ArrayList<String>();
				list.add(path);
				// uploadPic(list);
				iv_headImag.setImageBitmap(BitmapFactory.decodeFile(path));
				break;
			case Content.REQUESTCODE_TAKE_LOCAL:// 相册
				String localPath = "";
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				if (selectedImage != null) {
					Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null,
							null);
					int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					localPath = cursor.getString(columnIndex);
					if (TextUtils.isEmpty(localPath)) {
						MyToast.showToast(getActivity(), "您选择的图片不存在", 0);
						return;
					}
					localPath = ImageCompressUtil.getimage(localPath);
					List<String> files = new ArrayList<String>();
					files.add(localPath);
					// uploadPic(files);
					iv_headImag.setImageBitmap(BitmapFactory.decodeFile(localPath));
				}
				break;
			}
		}
	}

	// 头像
	private void showPicDialog() {
		View view = LayoutInflater.from(getContext()).inflate(R.layout.view_pic_dialog, null, false);
		final Dialog dialog = DialogShow.showDialog(getActivity(), view, Gravity.BOTTOM);
		Button btn_camera = (Button) view.findViewById(R.id.btn_camera);
		Button btn_photo = (Button) view.findViewById(R.id.btn_photo);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		btn_camera.setOnClickListener(new OnClickListener() {// 相机
			@Override
			public void onClick(View v) {
				selectImageFromCamera();
				dialog.dismiss();
			}
		});
		btn_photo.setOnClickListener(new OnClickListener() {// 相册
			@Override
			public void onClick(View v) {
				selectImageFromLocal();
				dialog.dismiss();
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {// 取消
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private String selectImageFromCamera() {
		String localCameraPath = "";
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File dir = new File(Environment.getExternalStorageDirectory(), Content.PICTURE_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, String.valueOf(System.currentTimeMillis()) + ".png");
		localCameraPath = file.getPath();
		Uri imageUri = Uri.fromFile(file);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		Content.CAMERA_PATH = localCameraPath;
		startActivityForResult(openCameraIntent, Content.REQUESTCODE_TAKE_CAMERA);
		return localCameraPath;
	}

	private void selectImageFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
		} else {
			intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, Content.REQUESTCODE_TAKE_LOCAL);
	}
}
