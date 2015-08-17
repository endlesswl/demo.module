package com.lingcaibao.weixin.openApi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.w3c.dom.Document;

import weixin.lingcaibao.bean.receivemessage.Msg4Event;
import weixin.lingcaibao.bean.receivemessage.Msg4Head;
import weixin.lingcaibao.bean.receivemessage.Msg4Text;
import weixin.lingcaibao.util.DefaultSession;
import weixin.lingcaibao.util.HandleMessageAdapter;

import com.lingcaibao.weixin.core.service.RedisServiceImpl;
import com.lingcaibao.weixin.core.util.DocumentUtil;
import com.lingcaibao.weixin.core.util.WeixinUtil;

/**
 * 微信服务器配置验证及消息自动回复
 * @author weil
 * @version V1.0
 * @Title: WxManageController.java
 * @Description:
 * @date 2014年7月31日 下午3:55:07
 */
@Controller
@RequestMapping(value = "/lcb/wx/")
public class WxManageController {

	private static Logger logger = LoggerFactory
			.getLogger(WxManageController.class);
	// 零彩宝Token
	private static final String TOKEN = "lingcaibao";

	@Autowired
	private MemcachedClient memcachedClient;

	@Autowired
	private RedisServiceImpl redisService;
	
	
	/**
	 * 处理微信服务器验证
	 * @param model
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "validate/{server_wx}", method = RequestMethod.GET)
	@ResponseBody
	public String checkIn(HttpServletRequest request,
			@PathVariable(value = "server_wx") String serverId)
			throws IOException {
		request.setCharacterEncoding("utf-8");
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		logger.info("--微信加密签名--- checkIn---:{}", signature);
		if (StringUtils.isEmpty(signature)) {
			logger.info("---result---:{}", "微信加密签名为空");
			return "validate fail";// 请求验证成功，返回随机码
		}
		boolean check = WeixinUtil.check(TOKEN, signature, timestamp, nonce);
		if (check) {
			logger.info("--微信加密签名通过-result---:{}", "ok");
			return echostr;// 请求验证成功，返回随机码
		} else {
			return "validate fail";
		}
	}

	/**
	 * 处理微信服务器发过来的各种消息，包括：文本、图片、地理位置、音乐等等
	 */
	@RequestMapping(value = "validate/{server_wx}", method = RequestMethod.POST)
	public void callback(HttpServletRequest request,
			final HttpServletResponse response,
			RedirectAttributes redirectAttributes,@PathVariable(value = "server_wx") final  String serverId) throws ServletException,
			IOException {
		logger.info("-----自动回复---serverId:{}", serverId);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("utf-8");
		InputStream is = request.getInputStream();
		OutputStream os = response.getOutputStream();
	
		final DefaultSession session = DefaultSession.newInstance();
		try {
			session.addOnHandleMessageListener(new HandleMessageAdapter() {
				@Override
				public void onTextMsg(Msg4Text msg) {
					logger.info("FromUserName:{},MsgId:{},ToUserName:{}",msg.getFromUserName(), msg.getMsgId(),msg.getToUserName());
					String documentString = redisService.get(serverId+"_"+msg.getContent());
					
//					Document document = (Document) memcachedClient.get(serverId+"_"+msg.getContent());
					if (StringUtils.isNotBlank(documentString)) {
						Document document = DocumentUtil.convertStringToDocument(documentString);
						Msg4Head mit = new Msg4Head();
						mit.setFromUserName(msg.getToUserName());
						mit.setToUserName(msg.getFromUserName());
						mit.setCreateTime(msg.getCreateTime());
						session.callback(document,mit,serverId);
					}else {
//						document = (Document) memcachedClient.get(serverId+"_未匹配回复");
						documentString = redisService.get(serverId+"_未匹配回复");
						Document document = DocumentUtil.convertStringToDocument(documentString);
						Msg4Head mit = new Msg4Head();
						mit.setFromUserName(msg.getToUserName());
						mit.setToUserName(msg.getFromUserName());
						mit.setCreateTime(msg.getCreateTime());
						session.callback(document,mit,serverId);
						/*Msg4Text mit = new Msg4Text();
						mit.setFromUserName(msg.getToUserName());
						mit.setToUserName(msg.getFromUserName());
						mit.setCreateTime(msg.getCreateTime());
						mit.setContent("嗨，等您好久了[咖啡]\n\n小招用绳命保证，将为您提供优质服务[害羞]\n\n点击☞<a href=\"http://market.cmbchina.com/ccard/wap/sqxyk/sqxyk.html?&WT.mc_id=N1700QY30487679100WX\">为您免费上门办理</a>\n\n点击☞<a href=\"https://ccclub.cmbchina.com/mca/MRecContract.aspx?cardsel=&WT.mc_id=MH7WXBJCD140222A\">推荐办卡送积分</a>\n\n点击☞<a href=\"http://campaign.app.qq.com/dom/npsb/jump.jsp?pkgName=com.cmbchina.ccd.pluto.cmbActivity&ckey=1267627453136\">掌上生活4.0</a>\n\n进入官方APP处理卡片相关问题[愉快]回复【新手礼】每天N多大礼包[礼物]\n\n每天18:00刷新\n【本来生活】水果、【米米米卡】代金券、【MT】【刀塔】【英雄战魂】游戏礼包等你来领！");
//						mit.setContent("如果，小招没有正确回复您的问题，菜单也没有您要的答案。\n\n请别急，每个工作日都会有小编在线为您回复问题[愉快]\n\n	点击☞<a href=\"http://campaign.app.qq.com/dom/npsb/jump.jsp?pkgName=com.cmbchina.ccd.pluto.cmbActivity&ckey=1267627453136\">掌上生活</a> 进入官方APP处理卡片相关问题[愉快]\n\n输入XX天气，比如\"北京天气\"，即可查询天气信息[太阳]");
						session.callback(mit);*/
						
					}
				}

				@Override
				public void onEventMsg(Msg4Event msg) {
					logger.info("事件处理--:{}");
					String msgType = msg.getHead().getMsgType();
					String event = msg.getEvent();
					String eventKey = msg.getEventKey();
					String toUserName = msg.getToUserName();
					String fromUserName = msg.getFromUserName();
					String createTime = msg.getCreateTime();
					logger.info("event:{},eventKey:{},msgType:{}", msgType, eventKey, event);
					logger.info("toUserName:{},fromUserName:{},createTime:{}", toUserName, fromUserName, createTime);
					if (msgType.equals("event") ) {
						if (event.equals(Msg4Event.SUBSCRIBE)) {
//							Document document = (Document) memcachedClient.get(serverId+"_关注回复");
							String documentString = redisService.get(serverId+"_关注回复");
							if (StringUtils.isNotBlank(documentString)) {
								Document document = DocumentUtil.convertStringToDocument(documentString);
								Msg4Head mit = new Msg4Head();
								mit.setFromUserName(msg.getToUserName());
								mit.setToUserName(msg.getFromUserName());
								mit.setCreateTime(msg.getCreateTime());
								session.callback(document,mit,serverId);
							}else {
								documentString = redisService.get(serverId+"_未匹配回复");
								Document document = DocumentUtil.convertStringToDocument(documentString);
								Msg4Head mit = new Msg4Head();
								mit.setFromUserName(msg.getToUserName());
								mit.setToUserName(msg.getFromUserName());
								mit.setCreateTime(msg.getCreateTime());
								session.callback(document,mit,serverId);
								/*Msg4Text mit = new Msg4Text();
								mit.setFromUserName(msg.getToUserName());
								mit.setToUserName(msg.getFromUserName());
								mit.setCreateTime(msg.getCreateTime());
								mit.setContent("嗨，等您好久了[咖啡]\n\n小招用绳命保证，将为您提供优质服务[害羞]\n\n点击☞<a href=\"http://market.cmbchina.com/ccard/wap/sqxyk/sqxyk.html?&WT.mc_id=N1700QY30487679100WX\">为您免费上门办理</a>\n\n点击☞<a href=\"https://ccclub.cmbchina.com/mca/MRecContract.aspx?cardsel=&WT.mc_id=MH7WXBJCD140222A\">推荐办卡送积分</a>\n\n点击☞<a href=\"http://campaign.app.qq.com/dom/npsb/jump.jsp?pkgName=com.cmbchina.ccd.pluto.cmbActivity&ckey=1267627453136\">掌上生活4.0</a>\n\n进入官方APP处理卡片相关问题[愉快]回复【新手礼】每天N多大礼包[礼物]\n\n每天18:00刷新\n【本来生活】水果、【米米米卡】代金券、【MT】【刀塔】【英雄战魂】游戏礼包等你来领！");
//								mit.setContent("嗨，等您好久了[咖啡]\n\n小招用绳命保证，将为您提供优质服务[害羞]\n\n点击☞<a href=\"http://market.cmbchina.com/ccard/wap/sqxyk/sqxyk.html?utm_source=yd&utm_medium=own&utm_campaign=NH7WXBJSC1402220\">我要办卡</a>\n\n点击☞<a href=\"https://ccclub.cmbchina.com/mca/MRecContract.aspx?cardsel=&WT.mc_id=MH7WXBJCD140222A\">推荐办卡送积分</a>\n\n点击☞<a href=\"http://campaign.app.qq.com/dom/npsb/jump.jsp?pkgName=com.cmbchina.ccd.pluto.cmbActivity&ckey=1267627453136\">掌上生活</a> 进入官方APP处理卡片相关问题[愉快]\n\n回复【MT】得百元大礼包[害羞]");
								session.callback(mit);*/
							}	
						}else if (event.equals(Msg4Event.CLICK)){
//							Document document = (Document) memcachedClient.get(serverId+"_"+eventKey);
							String documentString = redisService.get(serverId+"_"+eventKey);
							if (StringUtils.isNotBlank(documentString)) {
								Document document = DocumentUtil.convertStringToDocument(documentString);
								Msg4Head mit = new Msg4Head();
								mit.setFromUserName(msg.getToUserName());
								mit.setToUserName(msg.getFromUserName());
								mit.setCreateTime(msg.getCreateTime());
								session.callback(document,mit,serverId);
							}else {
//								document = (Document) memcachedClient.get(serverId+"_未匹配回复");
								documentString = redisService.get(serverId+"_未匹配回复");
								Document document = DocumentUtil.convertStringToDocument(documentString);
								Msg4Head mit = new Msg4Head();
								mit.setFromUserName(msg.getToUserName());
								mit.setToUserName(msg.getFromUserName());
								mit.setCreateTime(msg.getCreateTime());
								session.callback(document,mit,serverId);
							}
						}
					} 
				}

			});
			// 必须调用这两个方法
			// 如果不调用close方法，将会出现响应数据串到其它Servlet中。
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.process(is, os);// 处理微信消息
			session.close();// 关闭Session
		}

	}
}
