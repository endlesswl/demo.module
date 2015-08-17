package com.lingcaibao.weixin.maneger.service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import weixin.lingcaibao.bean.receivemessage.Msg;
import weixin.lingcaibao.util.WXXmlElementName;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.lingcaibao.weixin.core.entity.Result;
import com.lingcaibao.weixin.core.service.RedisListOpsService;
import com.lingcaibao.weixin.core.service.RedisServiceImpl;
import com.lingcaibao.weixin.core.util.DateProvider;
import com.lingcaibao.weixin.core.util.DocumentUtil;
import com.lingcaibao.weixin.core.util.UUIDUtils;
import com.lingcaibao.weixin.maneger.entity.WeixinAutoresponse;
import com.lingcaibao.weixin.maneger.entity.WeixinMenu;
import com.lingcaibao.weixin.maneger.entity.WeixinNewsitem;
import com.lingcaibao.weixin.maneger.repository.WeixinAutoresponseDao;

/**
* @Title: 
* @Description: 
* @Author jhe   
* @Date 2013 - 2014
* @Version V1.0
* @Copyright © 2013 掌信彩通信息科技(中国)有限公司. All rights reserved.
*/
// Spring Service Bean的标识.
@Service
@Transactional
public class WeixinAutoresponseService {

	private static Logger logger = LoggerFactory.getLogger(WeixinAutoresponseService.class);

	@Autowired
	private WeixinAutoresponseDao weixinAutoresponseDao;
	
	@Autowired
	private WeixinNewsitemService weixinNewsitemService;
	
	@Autowired
	private WeixinMenuService weixinMenuService;
	
	@Autowired
	private RedisServiceImpl redisService;
	
	@Autowired
	private MemcachedClient memcachedClient;
	/** Document构建类 */
	private static DocumentBuilder builder;
	static{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		//格式化工厂对象
	}
	private DateProvider dateProvider = DateProvider.DEFAULT;

	/**
	 * 分页查询
	 * 
	 * @param searchParams
	 *            查询条件
	 * @param pageable
	 *            分页参数
	 * @return
	 */
	public Page<WeixinAutoresponse> searchPage(Map<String, Object> searchParams,
			Pageable pageable) {
		return weixinAutoresponseDao.searchPage(searchParams, pageable);
	}

	/**
	 * 不分页查询
	 * 
	 * @param searchParas
	 *            查询条件
	 * @return
	 */
	public List<WeixinAutoresponse> search(Map<String, Object> searchParas) {
		return weixinAutoresponseDao.search(searchParas);
	}

	
	public WeixinAutoresponse get(String id) {
		return weixinAutoresponseDao.get(id);
	}

	public void insert(WeixinAutoresponse weixinAutoresponse) {
		weixinAutoresponse.setId(UUIDUtils.getShortUUID());
		weixinAutoresponse.setCreateTime(dateProvider.getDate());
		weixinAutoresponseDao.insert(weixinAutoresponse);
	}
	
	public void update(WeixinAutoresponse weixinAutoresponse) {
		WeixinAutoresponse oldWeixinAutoresponse = this.get(weixinAutoresponse.getId());
		//更改关键词，重新推送消息
		if (!oldWeixinAutoresponse.getKeyword().equals(weixinAutoresponse.getKeyword())) {
//			this.deleteAutoResponseMessege(oldWeixinAutoresponse);
			this.deleteAutoResponseMessegeRedis(oldWeixinAutoresponse);
			if (weixinAutoresponse.getFlag().equals("启用")) {
				this.publishAutoResponseMessege(weixinAutoresponse,1);
			}
		}
		//更改状态，重新推送消息
		if (oldWeixinAutoresponse.getFlag().equals("启用")&&weixinAutoresponse.getFlag().equals("停用")) {
//			this.deleteAutoResponseMessege(oldWeixinAutoresponse);
			this.deleteAutoResponseMessegeRedis(oldWeixinAutoresponse);
		}else if (oldWeixinAutoresponse.getFlag().equals("停用")&&weixinAutoresponse.getFlag().equals("启用")) {
			this.publishAutoResponseMessege(weixinAutoresponse,1);
		}
		weixinAutoresponse.setUpdateTime(dateProvider.getDate());
		weixinAutoresponseDao.update(weixinAutoresponse);
	}
	
	/**
	 * 删除自动回复消息  memcache
	 * @param weixinAutoresponse
	 * @return
	 */
	public Result deleteAutoResponseMessegeMemcache(WeixinAutoresponse weixinAutoresponse){
		logger.debug("delete AutoResponse memcached key {}",weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getKeyword());
		if (weixinAutoresponse.getKeyword().contains(WeixinAutoresponse.MULTI_KEYWORD_SEP)) {
			for (String key:weixinAutoresponse.getKeyword().split(WeixinAutoresponse.MULTI_KEYWORD_SEP)) {
				memcachedClient.delete(weixinAutoresponse.getAccountId()+"_"+key);
			}
		}else {
			memcachedClient.delete(weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getKeyword());
		}
		return new Result(true);
	}
	
	/**
	 * 删除自动回复消息  redis
	 * @param weixinAutoresponse
	 * @return
	 */
	public Result deleteAutoResponseMessegeRedis(WeixinAutoresponse weixinAutoresponse){
		logger.debug("delete AutoResponse redis key {}",weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getKeyword());
		if (weixinAutoresponse.getKeyword().contains(WeixinAutoresponse.MULTI_KEYWORD_SEP)) {
			for (String key:weixinAutoresponse.getKeyword().split(WeixinAutoresponse.MULTI_KEYWORD_SEP)) {
				redisService.delete(weixinAutoresponse.getAccountId()+"_"+key);
			}
		}else {
			redisService.delete(weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getKeyword());
		}
		redisService.delete(weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getId());//删除menu自动回复
		return new Result(true);
	}
	
	public  void setAutoResponseMessegeMemcache(WeixinAutoresponse weixinAutoresponse,Document document) {
		logger.debug("memcache发布自动回复关键词：{} ",weixinAutoresponse.getKeyword());
		if (weixinAutoresponse.getKeyword().contains(WeixinAutoresponse.MULTI_KEYWORD_SEP)) {
			for (String key:weixinAutoresponse.getKeyword().split(WeixinAutoresponse.MULTI_KEYWORD_SEP)) {
				logger.info("设置自动回复key:{}",weixinAutoresponse.getAccountId()+"_"+key);
				memcachedClient.set(weixinAutoresponse.getAccountId()+"_"+key, 365*24*60*60, document);
			}
		} else {
			logger.info("设置自动回复key:{}", weixinAutoresponse.getAccountId() + "_" + weixinAutoresponse.getKeyword());
			memcachedClient.set(weixinAutoresponse.getAccountId() + "_" + weixinAutoresponse.getKeyword(), 365 * 24 * 60 * 60,
					document);
		}
	}
	
	/**
	 * 设置自动回复消息 redis
	 * @param weixinAutoresponse
	 * @param document
	 * @param type 1为用户输入关键词自动回复，2为menu关键词key回复
	 */
	public  void setAutoResponseMessegeRedis(WeixinAutoresponse weixinAutoresponse,Document document,int type) {
		
		if (type==1) {
			logger.debug("redis发布自动回复关键词--用户输入：{} ",weixinAutoresponse.getKeyword());
			if (weixinAutoresponse.getKeyword().contains(WeixinAutoresponse.MULTI_KEYWORD_SEP)) {
				
					for (String key:weixinAutoresponse.getKeyword().split(WeixinAutoresponse.MULTI_KEYWORD_SEP)) {
						logger.info("设置自动回复key：{}",weixinAutoresponse.getAccountId()+"_"+key);
						logger.info("内容回复：{}",DocumentUtil.convertDocumentToString(document));
						redisService.set(weixinAutoresponse.getAccountId()+"_"+key,DocumentUtil.convertDocumentToString(document));
					}
				
				
			}else {
				logger.info("设置自动回复key:{}", weixinAutoresponse.getAccountId() + "_" + weixinAutoresponse.getKeyword());
				logger.info("内容回复：{}",DocumentUtil.convertDocumentToString(document));
				redisService.set(weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getKeyword(), DocumentUtil.convertDocumentToString(document));
			}
			HashMap<String, Object> searchParas = Maps.newHashMap();
			searchParas.put("accountId", weixinAutoresponse.getAccountId());
			searchParas.put("type", "click");
			searchParas.put("key", weixinAutoresponse.getId());
			List<WeixinMenu> weixinMenus = weixinMenuService.search(searchParas);
			if (weixinMenus!=null&&weixinMenus.size()!=0) {
				redisService.set(weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getId(),DocumentUtil.convertDocumentToString(document));
			}
			
		}else if (type==2) {
			logger.debug("redis发布自动回复关键词--menu：{} ",weixinAutoresponse.getKeyword());
			redisService.set(weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getId(),DocumentUtil.convertDocumentToString(document));
		}
	}
	
	/**
	 *  发布自动回复
	 * @param weixinAutoresponse
	 * @param type 1为用户输入关键词自动回复，2为menu关键词回复
	 * @return
	 */
	public Result publishAutoResponseMessege(WeixinAutoresponse weixinAutoresponse,int type) {
//		logger.debug("add AutoResponse memcached key :{}", weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getKeyword());
		Document document = builder.newDocument();
		Element root = document.createElement(WXXmlElementName.ROOT);
		if (StringUtils.isNotBlank(weixinAutoresponse.getMessageId())&&!weixinAutoresponse.getMessageId().equals("0")) {
			List<WeixinNewsitem> weixinNewsitems=null;
			if (weixinAutoresponse.getMessageId().contains(",")) {
				weixinNewsitems=weixinNewsitemService.searchByIds("'"+weixinAutoresponse.getMessageId().replace(",", "','")+"'");
			}else {
				weixinNewsitems=weixinNewsitemService.searchByIds("'"+weixinAutoresponse.getMessageId()+"'");
			}
			if (weixinNewsitems!=null&&weixinNewsitems.size()!=0) {
				Element articleCountElement = document.createElement(WXXmlElementName.ARTICLE_COUNT);
				articleCountElement.setTextContent(String.valueOf(weixinNewsitems.size()));
				Element articlesElement = document.createElement(WXXmlElementName.ARTICLES);
				for (WeixinNewsitem weixinNewsitem : weixinNewsitems) {
					Element itemElement = document.createElement(WXXmlElementName.ITEM);
					Element titleElement = document.createElement(WXXmlElementName.TITLE);
					titleElement.setTextContent(weixinNewsitem.getTitle());
					Element descriptionElement = document.createElement(WXXmlElementName.DESCRITION);
					descriptionElement.setTextContent(weixinNewsitem.getDescription());
					Element picUrlElement = document.createElement(WXXmlElementName.PIC_URL);
					picUrlElement.setTextContent(weixinNewsitem.getImagepath());
					Element urlElement = document.createElement(WXXmlElementName.URL);
					urlElement.setTextContent(weixinNewsitem.getUrl());
					itemElement.appendChild(titleElement);
					itemElement.appendChild(descriptionElement);
					itemElement.appendChild(picUrlElement);
					itemElement.appendChild(urlElement);
					articlesElement.appendChild(itemElement);
				}
				Element msgTypeElement = document.createElement(WXXmlElementName.MSG_TYPE);
				msgTypeElement.setTextContent(Msg.MSG_TYPE_IMAGE_TEXT);
				root.appendChild(articleCountElement);
				root.appendChild(articlesElement);
				root.appendChild(msgTypeElement); 
				document.appendChild(root);
//				setAutoResponseMessegeMemcache(weixinAutoresponse, document);
				setAutoResponseMessegeRedis(weixinAutoresponse, document,type);
			}else {
				return new Result(false,weixinAutoresponse.getKeyword()+":初始化失败",weixinAutoresponse);
			}
		}else {
			Element contentElement = document.createElement(WXXmlElementName.CONTENT);
			contentElement.setTextContent(weixinAutoresponse.getRescontent());
			Element msgTypeElement = document.createElement(WXXmlElementName.MSG_TYPE);
			msgTypeElement.setTextContent(Msg.MSG_TYPE_TEXT);
			root.appendChild(contentElement); 
			root.appendChild(msgTypeElement); 
			document.appendChild(root);
			setAutoResponseMessegeRedis(weixinAutoresponse, document,type);
//			setAutoResponseMessegeMemcache(weixinAutoresponse, document);
//			memcachedClient.set(weixinAutoresponse.getAccountId()+"_"+weixinAutoresponse.getKeyword(), 30*24*60*60, document);
		}
		return new Result(true);
	}

	public void delete(String id) {
		WeixinAutoresponse weixinAutoresponse=this.get(id);
//		this.deleteAutoResponseMessege(weixinAutoresponse);
		this.deleteAutoResponseMessegeRedis(weixinAutoresponse);
		weixinAutoresponseDao.delete(id);
	}
	
	/**
	 * 预设关注回复和未匹配回复
	 * @param id 微信帐号id
	 */
	public void defaultMessege(String id){
		WeixinAutoresponse weixinAutoresponse = new WeixinAutoresponse();
		weixinAutoresponse.setId(UUIDUtils.getShortUUID());
		weixinAutoresponse.setAccountId(id);
		//TODO  weixinAutoresponse.setCreaterId();
		weixinAutoresponse.setCreateTime(dateProvider.getDate());
		weixinAutoresponse.setFlag("停用");
		weixinAutoresponse.setKeyword("关注回复");
//		weixinAutoresponse.setMessageId("");
		weixinAutoresponse.setMsgtype("消息");
		//weixinAutoresponse.setNote("");
		weixinAutoresponse.setRescontent("感谢关注微信号");
		//weixinAutoresponse.setWeixinNewsitem("");
		this.insert(weixinAutoresponse);
		weixinAutoresponse.setId(UUIDUtils.getShortUUID());
		weixinAutoresponse.setKeyword("未匹配回复");
		weixinAutoresponse.setRescontent("不知道你需要什么哦");
		this.insert(weixinAutoresponse);
	}

	public void setDateProvider(DateProvider dateProvider) {
		this.dateProvider = dateProvider;
	}

	/**
	 * 发布menu回复内容
	 * @param weixinMenus
	 * @return
	 */
	public Result publishMenuResponse(List<WeixinMenu> weixinMenus) {
		for (WeixinMenu weixinMenu : weixinMenus) {
			if (weixinMenu.getSubWeimenus()!=null&&weixinMenu.getSubWeimenus().size()!=0) {
				for (WeixinMenu weixinMenu1 : weixinMenu.getSubWeimenus()) {
					if (weixinMenu1.getType().equals("click")) {
						WeixinAutoresponse weixinAutoresponse = this.get(weixinMenu1.getKey());
						this.publishAutoResponseMessege(weixinAutoresponse,2);
					}
					
				}
			}
		}
		return new Result(true);
	}

}
