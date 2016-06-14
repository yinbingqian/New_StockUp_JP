package com.sxit.http;

import java.util.List;

import com.sxit.entity.anwser.Anwser;

/**
 * webService请求接口
 * 
 * @author huanyu 类名称：ISoapService 创建时间:2014-11-4 下午7:08:50
 */
public interface ISoapService extends IASoapService {
	/**
	 * 首页初始化--用户名|密码
	 * 
	 * @param property_va
	 */
	void getList(Object[] property_va, boolean isPage);

	/**
	 * 首页新闻分页--用户名|密码
	 * 
	 * @param property_va
	 */
	void getListPage(Object[] property_va, boolean isPage);

	/**
	 * 首页新闻分页--用户名|密码
	 * 
	 * @param property_va
	 */
	void getListByStock(Object[] property_va, boolean isPage);

	/**
	 * 首页新闻分页--用户名|密码
	 * 
	 * @param property_va
	 */
	void getListByGuest(Object[] property_va, boolean isPage);

	/**
	 * 首页新闻分页--用户名|密码
	 * 
	 * @param property_va
	 */
	void getListLive(Object[] property_va, boolean isPage);
	
	/**
	 * 用户登录
	 * 
	 * @param property_va
	 */
	void userInfoLogin(Object[] property_va);
	
	/**
	 * 用户更新
	 * 
	 * @param property_va
	 */
	void userInfoById(Object[] property_va, String headpic);
	
	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void getDuelVote(Object[] property_va);
	
	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void getDuelVotePage(Object[] property_va);

	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void duelVote(Object[] property_va);
	
	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void viewPointSupport(Object[] property_va, int position);
	
	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void oneKeyLogin(Object[] property_va, String headpic);

	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void userRegistered(Object[] property_va);
	
	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void viewPointAdd(Object[] property_va);
	
	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void userEditor_RealName(Object[] property_va);

	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void userEditor_Password(Object[] property_va);

	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void userEditor_StockAge(Object[] property_va);

	/**
	 * 多空刷新初始化
	 * 
	 * @param property_va
	 */
	void userEditor_Sex(Object[] property_va);

	/**
	 * 鑾峰彇鍢夊鍒楄〃
	 * 
	 * @param property_va
	 */
	void getGuestTeam(Object[] property_va, boolean isPage);
	/**
	 * 鑾峰彇鍢夊璇︽儏
	 * 
	 * @param property_va
	 */
	void getGuestDetail(Object[] property_va, boolean isPage);

	/**
	 * 鑾峰彇杈句汉鍒楄〃
	 * 
	 * @param property_va
	 */
	void getExpertTeam(Object[] property_va, boolean isPage);
	/**
	 * 鑾峰彇杈句汉璇︽儏
	 * 
	 * @param property_va
	 */
	void getExpertDetail(Object[] property_va, boolean isPage);
	/**
	 * 讨论组列表
	 * 
	 * @param property_va
	 */
	void getDiscussionList(Object[] property_va, boolean isPage);
	/**
	 * 讨论组列表
	 * 
	 * @param property_va
	 */
	void getMyDiscussionList(Object[] property_va, boolean isPage);
	/**
	 * 讨论组列表
	 * 
	 * @param property_va
	 */
	void getDiscussionColumn(Object[] property_va);
	/**
	 * 讨论组列表
	 * 
	 * @param property_va
	 */
	void getQuestionColumn(Object[] property_va);
	/**
	 * 讨论组发帖
	 * 
	 * @param property_va
	 */
	void discussionSubmit(Object[] property_va);
	/**
	 * 讨论组发帖
	 * 
	 * @param property_va
	 */
	void questionSubmit(Object[] property_va);
	/**
	 * 讨论组发帖
	 * 
	 * @param property_va
	 */
	void answerSubmit(Object[] property_va);
	/**
	 * 鑾峰彇瑙ｇ瓟鍒楄〃
	 * 
	 * @param property_va
	 */

	void getQuestionList(Object[] property_va, boolean isPage);

	/**
	 * 鑾峰彇瑙ｇ瓟鍒楄〃
	 * 
	 * @param property_va
	 */
	void getAnswerList(Object[] property_va, boolean isPage);

	/**
	 * 鑾峰彇鎻愰棶璇︽儏
	 * 
	 * @param property_va
	 */

	void getQuestionDetail(Object[] property_va, boolean isPage);
	/**
	 * 采纳解答
	 * 
	 * @param property_va
	 */

	void setAnswerUseful(Object[] property_va);
	
	/**
	 * 获取论坛回复
	 * 
	 * @param property_va
	 */
	
	void getDiscussionReply(Object[] property_va, boolean isPage);

	/**
	 * 获取论坛回复
	 * 
	 * @param property_va
	 */
	
	void discussionUserSupport(Object[] property_va, boolean isUp);
	
	/**
	 * 回复楼主
	 * 
	 * @param property_va
	 */
	
	void discussionReplyHost(Object[] property_va);
	
	/**
	 * 回复其他楼
	 * 
	 * @param property_va
	 */
	
	void discussionReplyOther(Object[] property_va);
	
	/**
	 * 我的解答已采纳
	 * 
	 * @param property_va
	 */
	void getMyAnswerUse(Object[] property_va, boolean isPage);

	/**
	 * 我的解答进行中
	 * 
	 * @param property_va
	 */
	void getMyAnswerIng(Object[] property_va, boolean isPage);

	/**
	 * 我的解答详情
	 * 
	 * @param property_va
	 */

	void getMyAnswerDetail(Object[] property_va, boolean isPage);
	
	
	
	
	
	/**
	 * 分析师登陆信息--用户名|密码
	 * 
	 * @param property_va
	 */
	void adminLogin(Object[] property_va);

	/**
	 * 问答回复信息添加
	 * 
	 * @param property_va
	 */
	void communReplyAdd(Object[] property_va);

	/**
	 * 问答信息添加
	 * 
	 * @param property_va
	 */
	void communicationAdd(Object[] property_va);

	/**
	 * 分析师列表
	 * 
	 * @param property_va
	 */
	void getAdmin(Object[] property_va);

	/**
	 * 返回栏目信息
	 * 
	 * @param property_va
	 */
	void getColumns(Object[] property_va, boolean isPage);

	/**
	 * 问答回复列表
	 * 
	 * @param property_va
	 */
	void getCommunReply(Object[] property_va, List<Anwser> list);

	/**
	 * 分析师回复列表
	 * 
	 * @param property_va
	 */
	void getCommunReplyAna(Object[] property_va);

	/**
	 * 根据回复id返回详细信息
	 * 
	 * @param property_va
	 */
	void getCommunReplyByID(Object[] property_va);

	/**
	 * 问答分类显示
	 * 
	 * @param property_va
	 */
	void getCommunication(Object[] property_va);

	/**
	 * 用户个人全部问答列表
	 * 
	 * @param property_va
	 * @param isPage
	 *            是否做分页
	 */
	void getCommunicationALL(Object[] property_va, boolean isPage);

	/**
	 * 用户全部问答列表
	 * 
	 * @param property_va
	 * @param isPage
	 */
	void getCommunicationAllUser(Object[] property_va, boolean isPage);

	/**
	 * 未解决问题
	 * 
	 * @param property_va
	 */
	void getCommunicationAna(Object[] property_va);

	/**
	 * 返回新闻内容
	 * 
	 * @param property_va
	 */
	void getNewsContent(Object[] property_va);

	/**
	 * 返回最新新闻列表
	 * 
	 * @param property_va
	 */
	void getNewsTitle(Object[] property_va, boolean isPage);

	/**
	 * 接收消息首页信息 toUser：接收端用户id
	 * 
	 * @param property_va
	 */
	void interactionHomepage(Object[] property_va);

	/**
	 * 接收用户获取推送信息 toUser：用户id,historyTime：历史时间
	 * 
	 * @param property_va
	 */
	void interactionMessage(Object[] property_va);

	/**
	 * 接收用户获取推送信息 toUser：用户id,historyTime：历史时间,Ana
	 * 
	 * @param property_va
	 */
	void interactionMessageAna(Object[] property_va);

	/**
	 * 接收用户获取推送信息 ID：消息id,infoType:1消息/2研报，infoDirection:1用户-分析师/2分析师-用户
	 * 
	 * @param property_va
	 */
	void interactionMessageByID(Object[] property_va);

	/**
	 * 互动内容提交，devType:1Android/2iPhone，infoType:1消息/2研报，infoDirection:1用户-分析师/2
	 * 分析师-用户
	 * 
	 * @param property_va
	 */
	void interactionSubmit(Object[] property_va);

	/**
	 * 根据回复id选择最佳答案
	 * 
	 * @param property_va
	 */
	void updataBestAnswer(Object[] property_va);

	/**
	 * 根据回复id选择最佳答案
	 * 
	 * @param property_va
	 */
	void userPayment(Object[] property_va);

	/**
	 * 充值密码
	 * 
	 * @param property_va
	 */
	void userPasswordReset(Object[] property_va);

	/**
	 * 用户名校验：返回true可用|false不可用
	 * 
	 * @param property_va
	 */
	void userNameCheck(Object[] property_va);

	/**
	 * 编辑信息options:1昵称;2性别;3股龄;4投资风格;
	 * 
	 * @param property_va
	 */
	void userEditor(Object[] property_va);

	/**
	 * 编辑城市信息
	 * 
	 * @param property_va
	 */
	void userEditor_city(Object[] property_va);

	/**
	 * 编辑头像，其中images：图片的BASE64转码
	 * 
	 * @param property_va
	 */
	void userEditor_head(Object[] property_va);

	/**
	 * 研报列表
	 * 
	 * @param property_va
	 */
	void getReportList(Object[] property_va, boolean isPage);

	/**
	 * 研报提交，reportType:研报分类
	 * 
	 * @param property_va
	 */
	void reportSubmit(Object[] property_va);

	/**
	 * 返回研报内容
	 * 
	 * @param property_va
	 */
	void getReportContent(Object[] property_va);

	/**
	 * 更新红心设定
	 * 
	 * @param property_va
	 */
	void heartCountUpdate(Object[] property_va);

	/**
	 * 获取订单信息
	 * 
	 * @param property_va
	 */
	void getOrderInfoService(Object[] property_va);

	/**
	 * 获取验证码
	 * 
	 * @param property_va
	 */
	void getCode(Object[] property_va);

	/**
	 * 牛人解盘
	 * 
	 * @param property_va
	 */
	void userExpertDetailChanged(Object[] property_va, boolean isPage);
	

	/**
	 *wap页评论列表
	 * 
	 * @param property_va
	 */
	void getNewsComment(Object[] property_va, boolean isPage);
	
	/**
	 *正在直播
	 * 
	 * @param property_va
	 */
	void getLiving(Object[] property_va, boolean isPage);
	
	/**
	 *全部直播
	 * 
	 * @param property_va
	 */
	void getLivingALl(Object[] property_va, boolean isPage);
	/**
	 *热门直播
	 * 
	 * @param property_va
	 */
	void getLivingHot(Object[] property_va, boolean isPage);
	
	/**
	 *正在直播详情
	 * 
	 * @param property_va
	 */
	void getLivingDetail(Object[] property_va, boolean isPage);
	
	/**
	 *互动问答
	 * 
	 * @param property_va
	 */
	void getInteractQA(Object[] property_va, boolean isPage);
	
	/**
	 *直播关注状态
	 * 
	 * @param property_va
	 */
	void getAttentionLiveUser(Object[] property_va);
	
	/**
	 *直播大厅轮巡
	 * 
	 * @param property_va
	 */
	void getLivingUpdate(Object[] property_va, boolean isPage);
	
	/**
	 *直播大厅轮巡
	 * 
	 * @param property_va
	 */
	void updateImei(Object[] property_va);
	
	/**
	 *直播大厅轮巡
	 * 
	 * @param property_va
	 */
	void userInfoLoginUpdateImei(Object[] property_va);

	/**
	 * 绑定手机号
	 * 
	 * @param property_va
	 */
	void bindPhoneNum(Object[] property_va);

	/**
	 * 确认验证码
	 * 
	 * @param property_va
	 */
	void codeVerify(Object[] property_va);
	
	/**
	 * 忘记密码中重置密码
	 * 
	 * @param property_va
	 */
	void userPasswordFind(Object[] property_va);
	
	/**
	 * 获取cc视频信息 --分析师ID
	 * 
	 * @param property_va
	 */
	void getCcLivingInfoSingle(Object[] property_va);
	
}
