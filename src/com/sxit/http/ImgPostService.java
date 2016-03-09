package com.sxit.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.sxit.utils.SOAP_UTILS;

import android.util.Log;

public class ImgPostService {

	/**
	 * 
	 * @param method
	 *            SOAP_UTILS
	 * @param property_nm
	 * @param property_va
	 * @return
	 */
	public static Object data(String method, String[] property_nm, Object[] property_va, byte[] data) {
		try {
			String url = SOAP_UTILS.HTTP_URL + method;
			for (int i = 0; i < property_va.length; i++) {
				url = url + "/" + property_va[i];
			}
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();

			HttpPost httpPost = new HttpPost(url);
			ByteArrayEntity arrayEntity = new ByteArrayEntity(data);
			arrayEntity.setContentType("application/octet-stream");
			httpPost.setEntity(arrayEntity);

			HttpResponse response = httpClient.execute(httpPost, localContext);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
			String sResponse = reader.readLine();

			return sResponse;
		} catch (Exception e) {
			Log.v("ImgPostService", "Some error came up");
			return null;

		}
	}
}
