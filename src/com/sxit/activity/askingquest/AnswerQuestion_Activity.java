package com.sxit.activity.askingquest;

import java.util.ArrayList;
import java.util.List;

import lnpdit.lntv.tradingtime.R;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.sxit.activity.askingquest.adapter.AnswerQuestion_Adapter;
import com.sxit.activity.base.BaseActivity;
import com.sxit.activity.th.item.bean.AskQuestion_ListBean;
import com.sxit.entity.Adviser;
import com.sxit.entity.ChatMessage;
import com.sxit.entity.UserInfo;
import com.sxit.entity.anwser.Anwser;
import com.sxit.http.SoapRes;
import com.sxit.utils.EventCache;
import com.sxit.utils.SOAP_UTILS;
import com.sxit.utils.Utils;

/**
 * 解答 Acy
 * 
 * @author huanyu 类名称：AnswerQuestion_Activity 创建时间:2014-10-29 下午5:52:58
 */
public class AnswerQuestion_Activity extends BaseActivity {
	private ListView listView;
	private ImageView img_back;
	private List<Anwser> list = new ArrayList<Anwser>();
	AnswerQuestion_Adapter adapter;
	private Button bt_selectanswer;// 选中方案
	private int resultPosition;// 采纳位置
	private AskQuestion_ListBean bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_answer_list_layout);
		this.isParentActivity = false;
		intent = getIntent();
		bean = (AskQuestion_ListBean) intent.getSerializableExtra("AskQuestion_ListBean");
		initView();
		setListeners();
		setToUi();
		String[] property_va = new String[] { bean.getId() };
		soapService.getCommunReply(property_va, list);
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
		img_back = (ImageView) findViewById(R.id.img_back);
		bt_selectanswer = (Button) findViewById(R.id.bt_selectanswer);
	}

	private void setListeners() {
		img_back.setOnClickListener(this);
		bt_selectanswer.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (position != 0) {
					resultPosition = position;
					intent.setClass(AnswerQuestion_Activity.this, AnswerQuestionInfo_Activity.class);
					intent.putExtra("anwser", list.get(position).getAnwserId());
					intent.putExtra("isCheck", adapter.mapCk.get(position));
					startActivity(intent);
				}
			}
		});
	}

	private void setToUi() {
		Anwser anwser = new Anwser();
		anwser.setContent(bean.getContent());
		anwser.setTitle(getLoginUser().getRealname());
		anwser.setCrtime(bean.getCrtime());
		anwser.setDate(bean.getCrtime());
		anwser.setMark(bean.getMark());
		anwser.setMsg_direction(0);
		anwser.setAging(bean.getAging());
		anwser.setReplyId(bean.getId());// 问题id
		anwser.setUser_img(SOAP_UTILS.HEADER_URL + getLoginUser().getHeadpic());
		anwser.setContent_img(bean.getImgUrl());
		list.add(anwser);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:
			this.finish();
			System.out.println("back");
			break;
		case R.id.bt_selectanswer:
			if (adapter == null) {
				break;
			}
			int checkBoxPosition = adapter.checkAsw();
			if (checkBoxPosition != 0) {
				String[] property_va = new String[] { list.get(checkBoxPosition).getAnwserId() };
				soapService.updataBestAnswer(property_va);
			} else {
				Utils.showTextToast(this, getString(R.string.bestanswer_empty));
			}
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	// /**
	// * 接受解答详情中状态
	// * @param callisCheck check状态 0 选中，1 未选中
	// */
	// public void onEvent(String... callisCheck) {
	// if (callisCheck[1].equals("0")) {
	// adapter.changCk(resultPosition);
	// }
	// }

	public void onEvent(SoapRes res) {
		if (res.getCode().equals(SOAP_UTILS.METHOD.GETCOMMUNREPLY)) {
			list = (List<Anwser>) res.getObj();
			adapter = new AnswerQuestion_Adapter(AnswerQuestion_Activity.this, list);
			listView.setAdapter(adapter);
		}
		if (res.getCode().equals(SOAP_UTILS.METHOD.UPDATABESTANSWER)) {
			if (res.getObj() != null) {
				if (res.getObj().toString().equals("true")) {
					Utils.showTextToast(this, getString(R.string.check_success));
					finish();
					EventCache.commandActivity.post(
							new String[] { this.getClass().getName(), list.get(adapter.checkAsw()).getAnwserId() });
				} else {
					Utils.showTextToast(this, getString(R.string.check_fail));
				}
			} else {
				Utils.showTextToast(this, getString(R.string.check_fail));
			}
		}
	}
}
