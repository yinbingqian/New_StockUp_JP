package com.sxit.activity.usersetting;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
import com.sxit.db.DBHelper;
import com.sxit.entity.LoginUser;
import com.sxit.entity.UserInfo;
import com.sxit.http.ImgPostService;
import com.sxit.http.SoapRes;
import com.sxit.instance.Instance;
import com.sxit.utils.ImageTool;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;
import com.sxit.utils.XCRoundImageView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 编辑资料 Acy
 * 
 * @author huanyu 类名称：UserData_Activity 创建时间:2014-11-3 上午9:05:59
 */
public class UserSetting_Activity extends BaseActivity {
	private ImageView img_back;
	private XCRoundImageView header;
	private RelativeLayout rl_name, rl_password, rl_sex, rl_birth, rl_head;// 昵称，密码，性别，股龄，投资风格
	private TextView tv_name, tv_sex, tv_birth;
	private AutoCompleteTextView actv_password;
	//
	private AlertDialog dialog;
	private File sdcardTempFile = new File(Environment.getExternalStorageDirectory(), "head.jpg");// 图片截图路径
	UserInfo user;
	Context context;
	DBHelper dbh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usersetting);
		context = this;
		initView();
		setListeners();
		initDB();
	}

	private void initDB() {
		dbh = new DBHelper(context);
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		header = (XCRoundImageView) findViewById(R.id.header);
		rl_name = (RelativeLayout) findViewById(R.id.rl_name);
		rl_password = (RelativeLayout) findViewById(R.id.rl_password);
		rl_birth = (RelativeLayout) findViewById(R.id.rl_brith);
		rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
		rl_head = (RelativeLayout) findViewById(R.id.rl_head);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_birth = (TextView) findViewById(R.id.tv_birth);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		actv_password = (AutoCompleteTextView) findViewById(R.id.actv_password);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		rl_name.setOnClickListener(this);
		rl_password.setOnClickListener(this);
		rl_sex.setOnClickListener(this);
		rl_head.setOnClickListener(this);
		rl_birth.setOnClickListener(this);
	}

	public void setUI() {
		user = getUserInfo();
		tv_name.setText(user.getrealname());
		tv_sex.setText(user.getsex().equals("1") ? "男" : "女");
		if (user.getbirth().equals("stockAge")) {
			tv_birth.setText("2015-12-12");
		} else {
			tv_birth.setText(user.getbirth());
		}

		Instance.imageLoader.displayImage(user.getheadpic(), header, Instance.user_options);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.rl_name:
			intent.setClass(UserSetting_Activity.this, UserSetting_Name_Activity.class);
			intent.putExtra("name", tv_name.getText().toString());
			startActivity(intent);
			break;
		case R.id.rl_password:
			intent.setClass(UserSetting_Activity.this, UserSetting_Password_Activity.class);
			intent.putExtra("password", actv_password.getText().toString());
			startActivity(intent);
			break;
		case R.id.rl_sex:
			intent.setClass(UserSetting_Activity.this, UserSetting_Sex_Activity.class);
			intent.putExtra("sex", tv_sex.getText().toString());
			startActivity(intent);
			break;
		case R.id.rl_brith:
			intent.setClass(UserSetting_Activity.this, UserSetting_Birth_Activity.class);
			intent.putExtra("birth", tv_birth.getText().toString());
			startActivity(intent);
			break;
		case R.id.rl_head:
			if (dialog == null) {
				dialog = new AlertDialog.Builder(this)
						.setItems(new String[] { "相机", "相册" }, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (which == 0) {
									Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
									openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(sdcardTempFile));
									startActivityForResult(openCameraIntent, 100);
								} else {
									Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
									openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
											"image/*");
									startActivityForResult(openAlbumIntent, 100);
								}
							}
						}).create();
			}
			if (!dialog.isShowing()) {
				dialog.show();
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == 100) {
				Uri uri = null;
				if (data != null) {
					uri = data.getData();
					System.out.println("Data");
				} else {
					uri = Uri.fromFile(sdcardTempFile);
				}
				cropImage(uri, 100, 100, 101);
			}
			if (requestCode == 101) {
				Bitmap photo = null;
				Uri photoUri = data.getData();
				if (photoUri != null) {
					photo = BitmapFactory.decodeFile(photoUri.getPath());
				}
				if (photo == null) {
					Bundle extra = data.getExtras();
					if (extra != null) {
						photo = (Bitmap) extra.get("data");
						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);

						byte[] data_array = ImageTool.bitmapToBytes(photo);
						String[] property_nm = { "userid", "streamLength" };
						Object[] property_va = { user.getid(), data_array.length };
						new postHeaderTask().execute(property_nm, property_va, data_array);
						header.setImageBitmap(photo);
					}
				}
			}
		}
	}

	class postHeaderTask extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			rl_head.setEnabled(false);
		}

		@Override
		protected Object doInBackground(Object... params) {
			System.out.println(">>>>>");
			Object res_obj = (Object) ImgPostService.data(SOAP_UTILS.METHOD.USEREDITOR_HEADSTREAM, (String[]) params[0],
					(Object[]) params[1], (byte[]) params[2]);
			return res_obj;
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			rl_head.setEnabled(true);
			try {
				JSONObject json_obj = new JSONObject(result.toString());
				if (json_obj.get("Result").toString().equals("success")) {
					dbh.updateUser(user.getid(), "HEADPIC",
							SOAP_UTILS.HTTP_HEAD_PATH + json_obj.get("Message").toString());
					setUI();
					Toast.makeText(UserSetting_Activity.this, "头像修改成功", Toast.LENGTH_SHORT).show();
				} else if (json_obj.get("Result").toString().equals("error")) {
					Toast.makeText(UserSetting_Activity.this, json_obj.get("Message").toString(), Toast.LENGTH_SHORT)
							.show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 截取图片
	public void cropImage(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, requestCode);
	}

	public void onEvent(String... str) {
		for (String name : str) {
			if (name.equals(UserSetting_Name_Activity.class.getName())) {
				tv_name.setText(str[1]);
			}
			if (name.equals(UserSetting_Password_Activity.class.getName())) {

			}
			if (name.equals(UserSetting_Sex_Activity.class.getName())) {
				tv_sex.setText(str[1]);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		setUI();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.USEREDITOR_CITY)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
					LoginUser user = getLoginUser();
					// user.setProvince(province);
					// user.setCity(city);
					// setLoginUser(user);
					// address.setText(province + " · " + city);
					Utils.showTextToast(this, getString(R.string.edit_success));
				} else {
					Utils.showTextToast(this, getString(R.string.edit_password_fail));
				}
			} else {
				Utils.showTextToast(this, getString(R.string.edit_password_fail));
			}
		} else if (res.getCode().equals(SOAP_UTILS.METHOD.USEREDITOR_HEAD)) {
			if (res.getObj() != null) {
				LoginUser user = getLoginUser();
				user.setHeadpic(res.getObj().toString());
				setLoginUser(user);
				Instance.imageLoader.displayImage(SOAP_UTILS.HEADER_URL + getLoginUser().getHeadpic(), header,
						Instance.user_options);
			} else {
				Utils.showTextToast(this, getString(R.string.edit_password_fail));
			}
		}
	}
}
