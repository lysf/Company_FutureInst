package com.futureinst.home.userinfo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.futureinst.R;
import com.futureinst.baseui.BaseActivity;
import com.futureinst.global.Content;
import com.futureinst.model.basemodel.UpFileDAO;
import com.futureinst.model.usermodel.UserDAO;
import com.futureinst.model.usermodel.UserInfo;
import com.futureinst.net.PostCommentResponseListener;
import com.futureinst.net.PostMethod;
import com.futureinst.net.PostType;
import com.futureinst.roundimageutils.RoundedImageView;
import com.futureinst.utils.DialogShow;
import com.futureinst.utils.ImageLoadOptions;
import com.futureinst.utils.MyToast;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.soundcloud.android.crop.Crop;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources.NotFoundException;
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

public class ModifyPersoanlInfoActivity extends BaseActivity {
	private RoundedImageView iv_headImg;
	private EditText et_name,et_description;
	private UserDAO user;
	private BroadcastReceiver receiver;
	@Override
	protected void localOnCreate(Bundle savedInstanceState) {
		setTitle("编辑个人信息");
		getRightButton().setText("保存");
        getLeftImageView().setImageDrawable(getResources().getDrawable(R.drawable.back));
        getRightButton().setTextColor(getResources().getColor(R.color.right_btn));
		setContentView(R.layout.activity_modify_personal_info);
		initView();
	}
	@Override
	protected void onRightClick(View view) {
		super.onRightClick(view);
		String name = et_name.getText().toString().trim();
		String description = et_description.getText().toString().trim();
		if(TextUtils.isEmpty(name)){
			MyToast.getInstance().showToast(ModifyPersoanlInfoActivity.this, "请输入昵称", 0);
			return;
		}
		if(TextUtils.isEmpty(description)){
			MyToast.getInstance().showToast(ModifyPersoanlInfoActivity.this, "请输入先知态度", 0);
			return;
		}
		if(name.equals(user.getName()))
			name = null;
		update_user(user.getHeadImage(), name, description);
	}
	private void initView() {
		user = (UserDAO) getIntent().getSerializableExtra("user");
		iv_headImg = (RoundedImageView) findViewById(R.id.iv_headImg);
		et_name = (EditText) findViewById(R.id.et_name);
		et_description = (EditText) findViewById(R.id.et_description);
		ImageLoader.getInstance().displayImage(user.getHeadImage(), iv_headImg, ImageLoadOptions.getOptions(R.drawable.logo));
		if(!TextUtils.isEmpty(user.getName())){
			et_name.setText(user.getName());
		}
		if(!TextUtils.isEmpty(user.getDescription())){
			et_description.setText(user.getDescription());
		}
		et_name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					if (s.toString().getBytes("GB2312").length > 14) {
						MyToast.getInstance().showToast(ModifyPersoanlInfoActivity.this, getResources().getString(R.string.regist_userName_tip), 1);
						et_name.setText("");
						return;
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (NotFoundException e) {
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
		iv_headImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPicDialog();
			}
		});
		receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if(action.equals("crop")){
					Uri uri = intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
					String path = uri.getPath();
					List<String> files = new ArrayList<String>();
					files.add(path);
					uploadPic(files);
				}
			}
		};
		IntentFilter filter = new IntentFilter();
		filter.addAction("crop");
		registerReceiver(receiver, filter);
	}
	
	//上传图片
		private void uploadPic(final List<String> files){
			progressDialog.progressDialog();
			httpResponseUtils.UploadFileRequest(files, httpPostParams.getPostParams(PostMethod.upload_image.name(), "topic",
					httpPostParams.upLoadFile(preferenceUtil.getID() + "",
							preferenceUtil.getUUid())), UpFileDAO.class, new PostCommentResponseListener() {
				
				@Override
				public void requestCompleted(Object response) throws JSONException {
					progressDialog.cancleProgress();
					if(response == null) {
						return;
					}
					iv_headImg.setImageBitmap(BitmapFactory.decodeFile(files.get(0)));
					UpFileDAO upFileDAO = (UpFileDAO) response;
					user.setHeadImage(upFileDAO.getSrc());
				}
			});
		}
		// 修改用户信息
		private void update_user(String headImage,String name,String description) {
			progressDialog.progressDialog();
			httpResponseUtils.postJson(
					httpPostParams.getPostParams(PostMethod.update_user.name(),
							PostType.user.name(), httpPostParams.update_user(preferenceUtil.getUUid(),
									preferenceUtil.getID() + "", name,description,headImage)),
					UserInfo.class, new PostCommentResponseListener() {
						@Override
						public void requestCompleted(Object response) throws JSONException {
							progressDialog.cancleProgress();
							if (response == null)
								return;
							finish();
						}
					});
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			Log.i("onActivityResult", "---arg1=" + requestCode + ",arg0=" + resultCode + "intent=" + data);
			
			if(resultCode == Crop.REQUEST_CROP){
				iv_headImg.setImageURI(Crop.getOutput(data));
				
			}else if (resultCode == RESULT_OK) {
				switch (requestCode) {
				case Content.REQUESTCODE_TAKE_CAMERA:// 相机
					String path = Content.CAMERA_PATH;
					Uri source = Uri.fromFile(new File(path));
					beginCrop(source);
					
					break;
				case Content.REQUESTCODE_TAKE_LOCAL:// 相册
					Uri selectedImage = data.getData();
					beginCrop(selectedImage);
					break;
				}
			}
		}
		private void beginCrop(Uri source) {
	        Uri outputUri = Uri.fromFile(new File(getCacheDir(), "cropped"));
	        new Crop(source).output(outputUri).asSquare().start(this);
	    }
		// 头像
		private void showPicDialog() {
			View view = LayoutInflater.from(this).inflate(R.layout.view_pic_dialog, null, false);
			final Dialog dialog = DialogShow.showDialog(this, view, Gravity.BOTTOM);
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
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(receiver !=null){
			unregisterReceiver(receiver);
		}
	}
}
