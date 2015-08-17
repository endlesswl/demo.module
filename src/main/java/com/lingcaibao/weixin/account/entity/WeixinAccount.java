package com.lingcaibao.weixin.account.entity;

import java.util.Date;

import com.lingcaibao.weixin.core.util.DateFormatUtil;


public class WeixinAccount {

	//alias
	public static final String TABLE_ALIAS = "WeixinAccount";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ACCOUNT_NAME = "名称";
	public static final String ALIAS_ACCOUNT_USER_NAME = "登录账号";
	public static final String ALIAS_ACCOUNT_PASSWORD = "登录密码";
	public static final String ALIAS_ACCOUNT_APPID = "APPID";
	public static final String ALIAS_ACCOUNT_APPSECRET = "APPSECRET";
	public static final String ALIAS_ACCOUNT_TOKEN = "TOKEN";
	public static final String ALIAS_ACCOUNT_NUMBER = "微信号";
	public static final String ALIAS_ACCOUNT_TYPE = "类型";
	public static final String ALIAS_ACCOUNT_DESC = "描述";
	public static final String ALIAS_ACCOUNT_VERIFY_URL = "验证地址";
	public static final String ALIAS_ACCOUNT_OAUTH2_URL = "授权回调域名";
	public static final String ALIAS_OWNER_ID = "所属用户";
	public static final String ALIAS_STATUS = "状态";
	public static final String ALIAS_START_TIME = "开始时间";
	public static final String ALIAS_END_ITMA = "结束时间";
	public static final String ALIAS_ALLOW_URLS = "授权访问域名";
	public static final String ALIAS_CREATER_ID = "createrId";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_UPDATER_ID = "updaterId";
	public static final String ALIAS_UPDATE_TIME = "updateTime";
	
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 公众帐号名称
	 */
	private String accountName;
	/**
	 * 公众帐号登录账号
	 */
	private String accountUserName;
	/**
	 * 公众帐号登录密码
	 */
	private String accountPassword;
	/**
	 * 公众帐号APPID
	 */
	private String accountAppid;
	/**
	 * 公众帐号APPSECRET
	 */
	private String accountAppsecret;
	/**
	 * 公众帐号TOKEN
	 */
	private String accountToken;
	/**
	 * 公众微信号
	 */
	private String accountNumber;
	/**
	 * 公众号类型
	 */
	private String accountType;
	/**
	 * 公众帐号描述
	 */
	private String accountDesc;
	/**
	 * 公众帐号验证地址
	 */
	private String accountVerifyUrl;
	/**
	 * 授权回调域名
	 */
	private String accountOauth2Url;
	/**
	 * 所属用户ID
	 */
	private String ownerId;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 服务开始时间
	 */
	private java.util.Date startTime;
	/**
	 * 服务结束时间
	 */
	private java.util.Date endItma;
	/**
	 * 授权访问域名
	 */
	private String allowUrls;
	/**
	 * 创建者id
	 */
	private String createrId;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新着id
	 */
	private String updaterId;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
	/**
	 * 起止时间（用于接收form表单数据）
	 */
	private String dateRange;

	
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public java.lang.String getAccountName() {
		return this.accountName;
	}
	
	public void setAccountName(java.lang.String value) {
		this.accountName = value;
	}
	public java.lang.String getAccountUserName() {
		return this.accountUserName;
	}
	
	public void setAccountUserName(java.lang.String value) {
		this.accountUserName = value;
	}
	public java.lang.String getAccountPassword() {
		return this.accountPassword;
	}
	
	public void setAccountPassword(java.lang.String value) {
		this.accountPassword = value;
	}
	public java.lang.String getAccountAppid() {
		return this.accountAppid;
	}
	
	public void setAccountAppid(java.lang.String value) {
		this.accountAppid = value;
	}
	public java.lang.String getAccountAppsecret() {
		return this.accountAppsecret;
	}
	
	public void setAccountAppsecret(java.lang.String value) {
		this.accountAppsecret = value;
	}
	public java.lang.String getAccountToken() {
		return this.accountToken;
	}
	
	public void setAccountToken(java.lang.String value) {
		this.accountToken = value;
	}
	public java.lang.String getAccountNumber() {
		return this.accountNumber;
	}
	
	public void setAccountNumber(java.lang.String value) {
		this.accountNumber = value;
	}
	public java.lang.String getAccountType() {
		return this.accountType;
	}
	
	public void setAccountType(java.lang.String value) {
		this.accountType = value;
	}
	public java.lang.String getAccountDesc() {
		return this.accountDesc;
	}
	
	public void setAccountDesc(java.lang.String value) {
		this.accountDesc = value;
	}
	public java.lang.String getAccountVerifyUrl() {
		return this.accountVerifyUrl;
	}
	
	public void setAccountVerifyUrl(java.lang.String value) {
		this.accountVerifyUrl = value;
	}
	public java.lang.String getAccountOauth2Url() {
		return this.accountOauth2Url;
	}
	
	public void setAccountOauth2Url(java.lang.String value) {
		this.accountOauth2Url = value;
	}
	public java.lang.String getOwnerId() {
		return this.ownerId;
	}
	
	public void setOwnerId(java.lang.String value) {
		this.ownerId = value;
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	public java.util.Date getStartTime() {
		return this.startTime;
	}
	
	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	public java.util.Date getEndItma() {
		return this.endItma;
	}
	
	public void setEndItma(java.util.Date value) {
		this.endItma = value;
	}
	public java.lang.String getAllowUrls() {
		return this.allowUrls;
	}
	
	public void setAllowUrls(java.lang.String value) {
		this.allowUrls = value;
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

	public String getDateRange() {
		if (this.startTime!=null&&this.endItma!=null) {
			this.dateRange= DateFormatUtil.dateToString(this.startTime, "MM/dd/yyyy") + " - " +DateFormatUtil.dateToString(this.endItma, "MM/dd/yyyy");
		}
		
		return this.dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
		String ss[] =dateRange.split("-");
		this.startTime = DateFormatUtil.toDate(ss[0].trim(), "MM/dd/yyyy");
		this.endItma = DateFormatUtil.toDate(ss[1].trim(), "MM/dd/yyyy");
	}
}