package com.sxit.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import lnpdit.lntv.tradingtime.R;

public class ToastSign extends Toast {

	public ToastSign(Context context) {
		super(context);
	}

	public static Toast makeText(Context context, CharSequence texta, CharSequence textb, int duration) {
		Toast result = new Toast(context);

		// 获取LayoutInflater对象
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 由layout文件创建一个View对象
		View layout = inflater.inflate(R.layout.toast_sign, null);

		// 实例化ImageView和TextView对象
		TextView textView1 = (TextView) layout.findViewById(R.id.text1);
		TextView textView2 = (TextView) layout.findViewById(R.id.text2);

		textView1.setText(texta);
		textView2.setText(textb);

		result.setView(layout);
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);

		return result;
	}

}
