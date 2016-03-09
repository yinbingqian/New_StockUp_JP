package com.sxit.activity.discuss;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.main.ImagePagerActivity;
import com.sxit.entity.UserInfo;
import com.sxit.entity.discuss.DiscussItem;
import com.sxit.http.HttpPostService;
import com.sxit.http.ImgPostService;
import com.sxit.http.SoapRes;
import com.sxit.utils.ImageTool;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

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
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

/**
 * 新闻详情
 * 
 * @author huanyu 类名称：NewsInfo_Activity 创建时间:2014-10-29 下午3:18:47
 */
public class Discuss_Edit_Activity extends BaseActivity {
	Context context;

	ImageView img_back;
	ImageView img_send;
	ImageView img1;
	ImageView img2;
	RelativeLayout layout_type;
	AutoCompleteTextView edit_content;
	Button bt_send;
	TextView tv_tagname;

	DiscussItem disitem;
	private AlertDialog dialog;
	
	private File sdcardTempFile = new File(Environment.getExternalStorageDirectory(), "head.jpg");// 图片截图路径
	String img1_name = "no";
	String img2_name = "no";
	String tag_name = "";
	String tag_value = "";
	UserInfo user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss_edit);
		this.isParentActivity = false;
		context = this;
		Intent intent = this.getIntent();
		disitem = (DiscussItem) intent.getSerializableExtra("discuss");

		initView();
		setListeners();
		user = getUserInfo();
	}

	private void sendPoint() {
//		Toast.makeText(context, img1_name+"|"+img2_name + "|" + tag_name, Toast.LENGTH_SHORT).show();
		if(edit_content.getText().toString().trim().equals("")){
			Toast.makeText(context, "帖子内容不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if(tag_value.equals("")){
			Toast.makeText(context, "请选择消息类别", Toast.LENGTH_SHORT).show();
			return;
		}
//		String[] property_va = new String[] { user.getid(), edit_content.getText().toString(), tag_value, img1_name, img2_name };
//		soapService.discussionSubmit(property_va);
		String[] property_nm = { "userid","content", "col", "img1", "img2" };
		Object[] property_va = { user.getid(), edit_content.getText().toString(), tag_value, img1_name, img2_name };
		new discussionSubmitPostTask().execute(property_nm, property_va);
	}

	private void initView() {
		img_back = (ImageView) findViewById(R.id.img_back);
		img_send = (ImageView) findViewById(R.id.img_send);
		img1 = (ImageView) findViewById(R.id.img1);
		img2 = (ImageView) findViewById(R.id.img2);
		layout_type = (RelativeLayout) findViewById(R.id.layout_type);
		edit_content = (AutoCompleteTextView) findViewById(R.id.content);
		bt_send = (Button) findViewById(R.id.bt_send);
		tv_tagname = (TextView) findViewById(R.id.tag_name);
	}

	private void setListeners() {
		layout_type.setClickable(true);

		img_back.setOnClickListener(this);
		img_send.setOnClickListener(this);
		bt_send.setOnClickListener(this);
		img1.setOnClickListener(this);
		img2.setOnClickListener(this);
		layout_type.setOnClickListener(this);
		
		img2.setEnabled(false);
	}
	
	private void getPic(int index){
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
	}
	
	private void showPic(int index){
		ArrayList<String> imageUrls = new ArrayList<String>();
		if(!img1_name.equals("no")){
			imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + img1_name);
		}
		if(!img2_name.equals("no")){
			imageUrls.add(SOAP_UTILS.HTTP_DISCUSS_PATH + img2_name);
		}
		imageBrower(index, imageUrls);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.img_back:
			finish();
			break;
		case R.id.img1:
			if(img1_name.equals("no")){				
				getPic(1);
			}else{
				showPic(0);
			}
			break;
		case R.id.img2:
			if(img2_name.equals("no")){				
				getPic(2);
			}else{
				showPic(1);
			}
			break;
		case R.id.layout_type:
			Intent intent_type = new Intent();
			intent_type.setClass(Discuss_Edit_Activity.this, Discuss_Type_Activity.class);
			startActivityForResult(intent_type, 102);
			break;
		case R.id.img_send:
		case R.id.bt_send:
			sendPoint();
			break;
		default:
			break;
		}
	}

	public void onEvent(SoapRes obj) {
		super.onEvent(obj);
		if (obj.getCode().equals(SOAP_UTILS.METHOD.DISCUSSIONSUBMIT)) {
			String result = obj.getObj().toString();
			if (result.equals("success")) {
				Utils.showTextToast(context, "发布成功");
				finish();
			} else {
				Utils.showTextToast(context, result);
			}
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
				cropImage(uri, 360, 360, 101);
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
						String[] property_nm = { "streamLength","suffix" };
						Object[] property_va = { data_array.length, "jpg" };
						new postHeaderTask().execute(property_nm, property_va, data_array);
//						img1.setImageBitmap(photo);
						if(img1_name.equals("no")){
							img1.setImageBitmap(photo);
							img2.setEnabled(true);
							img2.setBackgroundResource(R.drawable.pic_add);
						}else{
							img2.setImageBitmap(photo);
						}
					}
				}
			}
			if(requestCode == 102){
				try {					
					tag_name = data.getStringExtra("title");
					tag_value = data.getStringExtra("value");
					
					tv_tagname.setText(tag_name);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	class discussionSubmitPostTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            img_send.setEnabled(false);
            bt_send.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            Object res_obj = (Object) HttpPostService.data(SOAP_UTILS.METHOD.DISCUSSIONSUBMITPOST, (String[]) params[0],(Object[]) params[1]);
            return res_obj;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            img_send.setEnabled(true);
            bt_send.setEnabled(true);
            try {
				JSONObject json_res = new JSONObject(result.toString());
				JSONObject json_obj = new JSONObject(json_res.get("DiscussionSubmitPostResult").toString());
				if(json_obj.get("Result").toString().equals("success")){
					Toast.makeText(Discuss_Edit_Activity.this, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
					finish();
				}else if(json_obj.get("Result").toString().equals("error")){
					Toast.makeText(Discuss_Edit_Activity.this, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	
	class postHeaderTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            img1.setEnabled(false);
            img2.setEnabled(false);
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            Object res_obj = (Object) ImgPostService.data(SOAP_UTILS.METHOD.PICUPLOADSTREAM, (String[]) params[0],(Object[]) params[1], (byte[])params[2]);
            return res_obj;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            img1.setEnabled(true);
            img2.setEnabled(true);
            try {
				JSONObject json_obj = new JSONObject(result.toString());
				if(json_obj.get("Result").toString().equals("success")){
					if(img1_name.equals("no")){
						img1_name = json_obj.get("Message").toString();
					}else{
						img2_name = json_obj.get("Message").toString();
					}
					Toast.makeText(Discuss_Edit_Activity.this, "图像上传成功", Toast.LENGTH_SHORT).show();
				}else if(json_obj.get("Result").toString().equals("error")){
					Toast.makeText(Discuss_Edit_Activity.this, json_obj.get("Message").toString(), Toast.LENGTH_SHORT).show();
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
//		intent.putExtra("outputX", outputX);
//		intent.putExtra("outputY", outputY);
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		intent.putExtra("scale", true);
		startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 打开图片查看器
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
	}
}
