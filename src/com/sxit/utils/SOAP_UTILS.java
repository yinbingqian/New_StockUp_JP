package com.sxit.utils;

public class SOAP_UTILS {
	public class METHOD {
		// 首页初始化
		public static final String GETLIST = "getList";
		// 首页新闻分页
		public static final String GETLISTPAGE = "GetListPage";
		// 股市动态
		public static final String GETLISTBYSTOCK = "GetListByStock";
		// 智囊观点
		public static final String GETLISTBYGUEST = "GetListByGuest";
		// 图文直播
		public static final String GETLISTLIVE = "GetListLive";
		// 用户登陆信息--用户名|密码
		public static final String USERINFOLOGIN = "UserInfoLogin";
		// 用户签到
		public static final String USERCHECK = "UserCheck";
		// 更新用户信息
		public static final String USERINFOBYID = "UserInfoByID";
		// 多空列表初始化
		public static final String GETDUELVOTE = "GetDuelVote";
		// 多空分页
		public static final String GETDUELVOTEPAGE ="GetDuelVotePage";
		// 多空投票
		public static final String DUELVOTE = "DuelVote";
		// 多空投票
		public static final String VIEWPOINTSUPPORT = "ViewPointSupport";
		// 微信一键登录
		public static final String ONEKEYLOGIN = "OneKeyLogin";
		// 用户注册
		public static final String USERREGISTERED = "UserRegistered";
		// 绑定手机号
		public static final String BINDPHONENUM = "codeCheckSimbind";
		// 确认验证码
		public static final String CODEVERIFY = "CodeVerify";
		// 多空对决发布观点
		public static final String VIEWPOINTADD = "ViewPointAdd";
		// 头像上传
		public static final String USEREDITOR_HEADSTREAM = "UserEditor_headStream";
		// 图像上传
		public static final String PICUPLOADSTREAM = "PicUploadStream";
		// 讨论组发帖POST
		public static final String DISCUSSIONSUBMITPOST = "DiscussionSubmitPost";
		// 多空对决发帖POST
		public static final String VIEWPOINTADDPOST = "ViewPointAddPost";
		//
		public static final String DISCUSSIONREPLYHOSTPOST = "DiscussionReplyHostPost";
		//
		public static final String DISCUSSIONREPLYOTHERPOST = "DiscussionReplyOtherPost";
		//
		public static final String QUESTIONSUBMITPOST = "QuestionSubmitPost";
		//
		public static final String ANSWERSUBMITPOST = "AnswerSubmitPost";
		// 修改用户昵称
		public static final String USEREDITOR_REALNAME = "UserEditor_RealName";
		// 修改用户性别
		public static final String USEREDITOR_SEX = "UserEditor_Sex";
		// 修改用户生日
		public static final String USEREDITOR_STOCKAGE = "UserEditor_StockAge";
		// 修改用户密码
		public static final String USEREDITOR_PASSWORD = "UserEditor_Password";
		// 讨论组列表
		public static final String GETDISCUSSIONLIST = "GetDiscussionList";
		// 我的帖子列表
		public static final String GETMYDISCUSSIONLIST = "GetMyDiscussionList";
		// 讨论组类别
		public static final String GETDISCUSSIONCOLUMN = "GetDiscussionColumn";
		// 讨论组类别
		public static final String GETQUESTIONCOLUMN = "GetQuestionColumn";
		// 讨论组类别
		public static final String DISCUSSIONSUBMIT = "DiscussionSubmit";
		// 讨论组类别
		public static final String QUESTIONSUBMIT = "QuestionSubmit";
		// 讨论组类别
		public static final String ANSWERSUBMIT = "AnswerSubmit";
		// 获取我的提问
		public static final String GETQUESTIONLIST = "GetQuestionList";
		// 获取解答列表
		public static final String GETANSWERLIST = "GetAnswerList";
		// 获取提问详情
		public static final String GETQUESTIONJSON = "GetQuestionJson";
		// 采纳解答
		public static final String SETANSWERUSEFUL = "AnswerUseful";
		// 获取帖子回复
		public static final String GETDISCUSSIONREPLY = "GetDiscussionReply";
		// 讨论组点赞
		public static final String DISCUSSIONUSERSUPPORT = "DiscussionUserSupport";
		// 讨论组回复楼主
		public static final String DISCUSSIONREPLYHOST = "DiscussionReplyHost";
		// 讨论组回复其他楼
		public static final String DISCUSSIONREPLYOTHER = "DiscussionReplyOther";
		// 我的解答已采纳
		public static final String MYANSWERUSEFUL = "MyAnswerUseful";
		// 我的解答进行中
		public static final String MYANSWERING = "MyAnswerIng";
		// 我的解答详情
		public static final String MYANSWERJSON = "MyAnswerJson";

		// 分析师登陆信息--用户名|密码
		public static final String ADMINLOGIN = "AdminLogin";
		// 问答回复信息添加
		public static final String COMMUNREPLYADD = "CommunReplyAdd";
		// 问答信息添加
		public static final String COMMUNICATIONADD = "CommunicationAdd";
		// 分析师列表
		public static final String GETADMIN = "GetAdmin";
		// 返回栏目信息
		public static final String GETCOLUMNS = "GetColumns";
		// 问答回复列表
		public static final String GETCOMMUNREPLY = "GetCommunReply";
		// 分析师回复列表
		public static final String GETCOMMUNREPLYANA = "GetCommunReplyAna";
		// 根据回复ID返回详细信息
		public static final String GETCOMMUNREPLYBYID = "GetCommunReplyByID";
		// 问答分类显示
		public static final String GETCOMMUNICATION = "GetCommunication";
		// 用户个人全部问答列表
		public static final String GETCOMMUNICATIONALL = "GetCommunicationALL";
		// 用户全部问答列表
		public static final String GETCOMMUNICATIONALLUSER = "GetCommunicationAllUser";
		// 未解决问题
		public static final String GETCOMMUNICATIONANA = "GetCommunicationAna";
		// 返回新闻内容
		public static final String GETNEWSCONTENT = "GetNewsContent";
		// 返回最新新闻列表
		public static final String GETNEWSTITLE = "GetNewsTitle";
		// 接收消息首页信息 TOUSER：接收端用户ID
		public static final String INTERACTIONHOMEPAGE = "InteractionHomepage";
		// 接收用户获取推送信息 TOUSER：用户ID,HISTORYTIME：历史时间
		public static final String INTERACTIONMESSAGE = "InteractionMessage";
		// 接收用户获取推送信息 TOUSER：用户ID,HISTORYTIME：历史时间
		public static final String INTERACTIONMESSAGEANA = "InteractionMessageAna";
		// 接收用户获取推送信息 ID：消息ID,INFOTYPE:1消息/2研报，infoDirection:1用户-分析师/2分析师-用户
		public static final String INTERACTIONMESSAGEBYID = "InteractionMessageByID";
		// 互动内容提交，DEVTYPE:1Android/2iPhone，infoType:1消息/2研报，infoDirection:1用户-分析师/2分析师-用户
		public static final String INTERACTIONSUBMIT = "InteractionSubmit";
		// 根据回复ID选择最佳答案
		public static final String UPDATABESTANSWER = "UpdataBestAnswer";
		// 用户充值
		public static final String USERPAYMENT = "UserPayment";
		// 密码重置
		public static final String USERPASSWORDRESET = "UserPasswordReset ";
		// 用户名校验：返回true可用|false不可用
		public static final String USERNAMECHECK = "UserNameCheck ";
		// 编辑信息options:1昵称;2性别;3股龄;4投资风格;
		public static final String USEREDITOR = "UserEditor";
		// 编辑城市信息
		public static final String USEREDITOR_CITY = "UserEditor_city";
		// 编辑头像，其中images：图片的BASE64转码
		public static final String USEREDITOR_HEAD = "UserEditor_head";
		// 研报列表
		public static final String GETREPORTLIST = "GetReportList";
		// 研报提交，reportType:研报分类
		public static final String REPORTSUBMIT = "ReportSubmit";
		// 返回研报内容
		public static final String GETREPORTCONTENT = "GetReportContent";
		// 跟新红心数值
		public static final String HEARTCOUNTUPDATE = "HeartCountUpdate";
		// 获取订单信息
		public static final String GETORDERINFO = "MobileAlipay";
		// 获取验证码
		public static final String GETCODE = "SmsVerify";
		// 获取嘉宾
		public static final String GETGUESTTEAM = "GetGuestTeam";
		// 获取嘉宾详情
		public static final String GETGUESTDETAIL = "GetUserGuestDetail";
		// 获取达人
		public static final String GETEXPERTTEAM = "GetExpertTeam";
		// 获取达人详情
		public static final String GETEXPERTDETAIL = "GetExpertDetail";
		// 获取牛人解盘
		public static final String USEREXPERTDETAILCHANGED = "UserExpertDetailChanged";
		// 获取wap评论
		public static final String GETNEWSCOMMENT = "GetNewsComment";
		// 获取wap评论
		public static final String ADDNEWSCOMMENT = "AddNewsComment";
		// 获取正在直播
		public static final String GETLIVING = "GetLiving";
		// 获取全部直播
		public static final String GETLIVINGALL = "GetLivingALl";
		// 获取热门直播
		public static final String GETLIVINGHOT = "GetLivingHot";
		// 获取正在直播详情
		public static final String GETLIVINGDETAIL = "GetLivingDetail";
		// 获取互动问答
		public static final String GETINTERACTQA = "GetInteractQA";
		// 修改直播关注状态
		public static final String GETATTENTIONLIVEUSER = "GetAttentionLiveUser";
		// 直播大厅轮巡
		public static final String GETLIVINGUPDATE = "GetLivingUpdate";
		// 直播大厅轮巡
		public static final String USERINFOLOGINUPDATEIMEI = "UserInfoLoginUpdateImei";
		// 直播大厅轮巡
		public static final String UPDATEIMEI = "UpdateImei";
		// 忘记密码中重置密码
		public static final String USERPASSWORDFIND = "UserPasswordFind";
		// 获取cc视频信息
		public static final String GETCCLIVINGINFOSINGLE = "GetCcLivingInfoSingle";
		
	}

	public class ACTION {
		// 首页初始化
		public static final String LIVECIRCLE = "livecircle";
	}
	
	public class ERROR {
		public static final String ERR0000 = "ERR 000";
		public static final String ERR0001 = "ERR 001";
		public static final String ERR0002 = "ERR 002";
		public static final String ERR0003 = "ERR 003";
		public static final String ERR0004 = "ERR 004";
		public static final String ERR0005 = "ERR 005 XML解析错误";
		public static final String ERR0006 = "ERR 006 本地错误";

	}

	public static final String NAMESPACE = "MobileNewspaper";
	public static final String IP = "http://192.168.1.104:8027/guzhang";
//	public static final String IP = "http://backlogin.guzhang.tv/guzhang";
	public static final String HEADER_URL = "http://192.168.1.104:8027/guzhang/upload/headpic/";
//	public static final String HEADER_URL = "http://backlogin.guzhang.tv/guzhang/upload/headpic/";
	public static final String URL = IP + "/phoneinvoke.asmx?wsdl";
//新版股涨用
	public static final String HTTP_BACKLOGINIP = "http://backlogin.guzhang.tv";
//	public static final String HTTP_BACKLOGINIP = "http://123.56.88.189:8027";
//	public static final String HTTP_IP = "http://192.168.1.118:8027";
//	public static final String HTTP_IP = "http://192.168.1.118:8080";
	public static final String HTTP_PATHIP = "http://share.guzhang.tv";
//	public static final String HTTP_URL =  HTTP_BACKLOGINIP + "/guzhang/";
//	public static final String HTTP_URL =  "http://192.168.1.104:8027/guzhang/";//内网地址
//	public static final String HTTP_URL =  "http://123.56.88.189:8027/guzhangTest/";//外网测试地址
	public static final String HTTP_URL =  "http://123.56.88.189:8027/guzhang/";//外网测试地址
//	public static final String HTTP_URL =  HTTP_IP + "/guzhang/";
	public static final String HTTP_HEAD_PATH = HTTP_BACKLOGINIP + "/upload/headpic/";
//	public static final String HTTP_HEAD_PATH = HTTP_IP + "/upload/headpic/";
	public static final String HTTP_ADT_PATH = HTTP_BACKLOGINIP +"/manage/pic/";
//	public static final String HTTP_ADT_PATH = HTTP_IP +"/manage/pic/";
	public static final String HTTP_NEWSINFO_PATH = HTTP_PATHIP +"/share/content.aspx?id=";
	public static final String HTTP_NEWSSHARE_PATH = HTTP_PATHIP +"/share/content.aspx?type=android&id=";
	public static final String HTTP_ADVERTINFO_PATH = HTTP_PATHIP +"/ad.aspx?id=";
	public static final String HTTP_ADVERTSHARE_PATH = HTTP_PATHIP +"/ad.aspx?type=android&id=";
	public static final String HTTP_DUELSHARE_PATH = HTTP_PATHIP +"/share/duokong.aspx?type=android";
	public static final String HTTP_LIVESHARE_PATH = HTTP_PATHIP +"/share/live.aspx?type=android";
	public static final String HTTP_MAG_PATH = HTTP_BACKLOGINIP +"/manage/magpic/";
//	public static final String HTTP_MAG_PATH = HTTP_IP +"/manage/magpic/";
	public static final String HTTP_DISCUSS_PATH = HTTP_BACKLOGINIP +"/upload/discussion/";
//	public static final String HTTP_DISCUSS_PATH = HTTP_IP +"/upload/discussion/";

	public static final String URL_WITHOUT_WSDL = IP + "/phoneinvoke.asmx";
	public static final String PIC_FILE = IP + "/manage/pic/";
	public static final String PIC_JOURNAL = IP + "/manage/magpic/";
	public static final String PIC_PUSH = IP + "/upload/";
	public static final String PIC_YANBAO = IP + "/manage/pic/";
	public static final String URL_SERVER = IP + "/apksource/version.xml";
	public static final String VIDEO_PATH = IP + "/manage/videofile/";
	public static final String AUDIO_PATH = IP + "/audio/";
	public static final String COL_PATH = IP + "/columns.xml";
	public static final String HEAD_PIC_PATH = IP + "/upload/headpic/";
	public static final String COLUMN_PIC_PATH = IP + "/upload/column/";
	
	// login type
	public static final int ADMIN = 0;// 顾问
	public static final int USER = 1;// 投资者
	// definitions xmlns:s="http://www.w3.org/2001/XMLSchema"
	// xmlns:soap12="http://schemas.xmlsoap.org/wsdl/soap12/"
	// xmlns:mime="http://schemas.xmlsoap.org/wsdl/mime/"
	// xmlns:tns="MobileNewspaper"
	// xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
	// xmlns:tm="http://microsoft.com/wsdl/mime/textMatching/"
	// xmlns:http="http://schemas.xmlsoap.org/wsdl/http/"
	// xmlns:soapenc="http://schemas.xmlsoap.org/soap/encoding/"
	// xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
	// targetNamespace="MobileNewspaper"

}
