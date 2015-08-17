package com.lingcaibao.weixin.maneger.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;


public class WeixinAutoresponse {
	
	/**
	 * 关键词自动回复分隔符
	 */
	public static final String MULTI_KEYWORD_SEP="_";

	//alias
	public static final String TABLE_ALIAS = "WeixinAutoresponse";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_KEYWORD = "关键词";
	public static final String ALIAS_MSGTYPE = "消息类型";
	public static final String ALIAS_RESCONTENT = "回复内容";
	public static final String ALIAS_MESSAGE_ID = "模版消息";
	public static final String ALIAS_ACCOUNT_ID = "帐号ID";
	public static final String ALIAS_NOTE = "备注";
	public static final String ALIAS_CREATER_ID = "createrId";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_UPDATER_ID = "updaterId";
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	public static final String ALIAS_FLAG = "状态";
	/**
	 * 
	 */
	private String id;
	/**
	 * 关键词
	 */
	private String keyword;
	/**
	 * 消息类型 （如文本，点击等）
	 */
	private String msgtype;
	/**
	 * 类型为文本消息时有内容
	 */
	private String rescontent;
	/**
	 * 消息id （类型不为文本消息时有内容）
	 */
	private String messageId;
	/**
	 * 微信公众帐号id
	 */
	private String accountId;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 创建者id
	 */
	private String createrId;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新者id
	 */
	private String updaterId;
	/**
	 * 状态
	 */
	private String flag;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	private WeixinNewsitem weixinNewsitem;
	
	/**
	 * 图文消息绑定图文
	 */
	private List<WeixinNewsitem> weixinNewsitems = new ArrayList<WeixinNewsitem>();;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public java.lang.String getKeyword() {
		return this.keyword;
	}
	
	public void setKeyword(java.lang.String value) {
		this.keyword = value;
	}
	public java.lang.String getMsgtype() {
		return this.msgtype;
	}
	
	public void setMsgtype(java.lang.String value) {
		this.msgtype = value;
	}
	public java.lang.String getRescontent() {
		return this.rescontent;
	}
	
	public void setRescontent(java.lang.String value) {
		this.rescontent = value;
	}
	public java.lang.String getMessageId() {
		return this.messageId;
	}
	
	public void setMessageId(java.lang.String value) {
		this.messageId = value;
	}
	public java.lang.String getAccountId() {
		return this.accountId;
	}
	
	public void setAccountId(java.lang.String value) {
		this.accountId = value;
	}
	public java.lang.String getNote() {
		return this.note;
	}
	
	public void setNote(java.lang.String value) {
		this.note = value;
	}
	public java.lang.String getCreaterId() {
		return this.createrId;
	}
	
	public void setCreaterId(java.lang.String value) {
		this.createrId = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.lang.String getUpdaterId() {
		return this.updaterId;
	}
	
	public void setUpdaterId(java.lang.String value) {
		this.updaterId = value;
	}
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}

	public WeixinNewsitem getWeixinNewsitem() {
		return weixinNewsitem;
	}

	public void setWeixinNewsitem(WeixinNewsitem weixinNewsitem) {
		this.weixinNewsitem = weixinNewsitem;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<WeixinNewsitem> getWeixinNewsitems() {
		return weixinNewsitems;
	}

	public void setWeixinNewsitems(List<WeixinNewsitem> weixinNewsitems) {
		this.weixinNewsitems = weixinNewsitems;
	}
}