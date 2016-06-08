package lnpdit.lntv.tradingtime.wxapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import com.sxit.http.ISoapService;
import com.sxit.http.SoapService;
import com.sxit.utils.Utils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * 微信分享回调，类名固定
 * 
 * @author huanyu 类名称：WXEntryActivity 创建时间:2014-11-20 下午1:46:09
 */
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
	private IWXAPI api;
	public static final String APP_id = "wx84b8e5819e8cab1d";
	public static final String AppSecret = "1d3a184d961e2b9dcb2d3c937923a24b";
	// public static final String AppSecret =
	// "300346ca58ed3910162f3edf448302e0";
	public static BaseResp mResp = null;
	Context context;
	public ISoapService soapService = new SoapService();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		context = this;
		api = WXAPIFactory.createWXAPI(this, APP_id, true);
		api.registerApp(APP_id);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq resp) {
		// TODO Auto-generated method stub
		String result = "";
		switch (resp.hashCode()) {
		case BaseResp.ErrCode.ERR_OK:
			result = "分享成功";
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = "分享取消";
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = "分享失败";
			break;
		default:
			result = "分享异常";
			break;
		}
		Utils.showTextToast(this, result);
		finish();
	}

	@Override
	public void onResp(BaseResp resp) {
		// TODO Auto-generated method stub
		try {
			String code = ((SendAuth.Resp) resp).code;// 获取code后需要去获取access_token
			new GetToken().execute(code);
		} catch (Exception e) {
			// TODO: handle exception
			Utils.showTextToast(this, "分享成功");
			finish();
		}
	}

	class GetToken extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			System.out.println(">>>>>");
			String code = (String) params[0];
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + APP_id + "&secret=" + AppSecret
					+ "&code=" + code + "&grant_type=authorization_code";
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClient = new DefaultHttpClient();
			// 发送请求
			String result = "";
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity(), "UTF_8");
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			try {
				JSONObject token_json = new JSONObject(result.toString());
				String access_token = token_json.getString("access_token");
				String expires_in = token_json.getString("expires_in");
				String refresh_token = token_json.getString("refresh_token");
				String openid = token_json.getString("openid");
				String scope = token_json.getString("scope");
				String unionid = token_json.getString("unionid");
				new GetUserInfo().execute(access_token, expires_in, refresh_token, openid, scope, unionid);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.showTextToast(context, "token获取失败");
				finish();
			}
		}
	}

	class GetUserInfo extends AsyncTask<Object, Object, Object> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Object doInBackground(Object... params) {
			System.out.println(">>>>>");
			String access_token = (String) params[0];
			String expires_in = (String) params[1];
			String refresh_token = (String) params[2];
			String openid = (String) params[3];
			String scope = (String) params[4];
			String unionid = (String) params[5];

			String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid;
			HttpGet httpGet = new HttpGet(url);
			HttpClient httpClient = new DefaultHttpClient();
			// 发送请求
			String result = "";
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity(), "UTF_8");
				return result;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			try {
				JSONObject token_json = new JSONObject(result.toString());
				String openid = token_json.getString("openid");
				String nickname = token_json.getString("nickname");
				String sex = token_json.getString("sex");
				String province = token_json.getString("province");
				String city = token_json.getString("city");
				String country = token_json.getString("country");
				String headimgurl = token_json.getString("headimgurl");
				String privilege = token_json.getString("privilege");
				String unionid = token_json.getString("unionid");


				Object[] property_va = { openid, nickname };
				soapService.oneKeyLogin(property_va, headimgurl);
				finish();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.showTextToast(context, "获取微信用户信息失败");
				finish();
			}
		}
	}

}
