package com.lingcaibao.weixin.maneger.entity;


public class WeixinNewsitem {

	//alias
	public static final String TABLE_ALIAS = "WeixinNewsitem";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "消息名称";
	public static final String ALIAS_URL = "跳转链接";
	public static final String ALIAS_DESCRIPTION = "消息内容";
	public static final String ALIAS_IMAGEPATH = "图片链接";
	public static final String ALIAS_TITLE = "消息标题";
	public static final String ALIAS_ACCOUNT_ID = "accountId";
	public static final String ALIAS_NOTE = "备注";
	public static final String ALIAS_FLAG = "状态";
	public static final String ALIAS_CREATER_ID = "createrId";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_UPDATER_ID = "updaterId";
	public static final String ALIAS_UPDATE_TIME = "updateTime";

	/**
	 * 
	 */
	private String id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 消息跳转链接
	 */
	private String url;
	/**
	 * 消息描述
	 */
	private String description;
	/**
	 * 消息图片地址
	 */
	private String imagepath;
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 微信帐号id
	 */
	private String accountId;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 状态
	 */
	private String flag;
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
	 * 更新时间
	 */
	private java.util.Date updateTime;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	public java.lang.String getUrl() {
		return this.url;
	}
	
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	public java.lang.String getImagepath() {
		return this.imagepath;
	}
	
	public void setImagepath(java.lang.String value) {
		this.imagepath = value;
	}
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String value) {
		this.title = value;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}