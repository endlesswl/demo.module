package com.lingcaibao.weixin.weixinuser.entity;


public class WeixinUserinfo {

	//alias
	public static final String TABLE_ALIAS = "WeixinUserinfo";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_SUBSCRIBE = "subscribe";
	public static final String ALIAS_OPENID = "openid";
	public static final String ALIAS_NICKNAME = "nickname";
	public static final String ALIAS_SEX = "sex";
	public static final String ALIAS_LANGUAGE = "language";
	public static final String ALIAS_CITY = "city";
	public static final String ALIAS_PROVINCE = "province";
	public static final String ALIAS_COUNTRY = "country";
	public static final String ALIAS_HEAD_IMG_URL = "headImgUrl";
	public static final String ALIAS_SUBSCRIBE_TIME = "subscribeTime";
	public static final String ALIAS_ACCOUNT_ID = "accountId";
	public static final String ALIAS_CREATE_TIME = "createTime";

	/**
	 * 
	 */
	private String id;
	/**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 */
	private String subscribe;
	/**
	 * 用户的标识，对当前公众号唯一
	 */
	private String openid;
	/**
	 * 用户的昵称
	 */
	private String nickname;
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private String sex;
	/**
	 * 用户的语言，简体中文为zh_CN
	 */
	private String language;
	/**
	 * 用户所在城市
	 */
	private String city;
	/**
	 * 用户所在省份
	 */
	private String province;
	/**
	 * 用户所在国家
	 */
	private String country;
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
	 */
	private String headImgUrl;
	/**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
	private String subscribeTime;
	/**
	 * 
	 */
	private String accountId;
	/**
	 * 
	 */
	private java.util.Date createTime;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public java.lang.String getSubscribe() {
		return this.subscribe;
	}
	
	public void setSubscribe(java.lang.String value) {
		this.subscribe = value;
	}
	public java.lang.String getOpenid() {
		return this.openid;
	}
	
	public void setOpenid(java.lang.String value) {
		this.openid = value;
	}
	public java.lang.String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(java.lang.String value) {
		this.nickname = value;
	}
	public java.lang.String getSex() {
		return this.sex;
	}
	
	public void setSex(java.lang.String value) {
		this.sex = value;
	}
	public java.lang.String getLanguage() {
		return this.language;
	}
	
	public void setLanguage(java.lang.String value) {
		this.language = value;
	}
	public java.lang.String getCity() {
		return this.city;
	}
	
	public void setCity(java.lang.String value) {
		this.city = value;
	}
	public java.lang.String getProvince() {
		return this.province;
	}
	
	public void setProvince(java.lang.String value) {
		this.province = value;
	}
	public java.lang.String getCountry() {
		return this.country;
	}
	
	public void setCountry(java.lang.String value) {
		this.country = value;
	}
	public java.lang.String getHeadImgUrl() {
		return this.headImgUrl;
	}
	
	public void setHeadImgUrl(java.lang.String value) {
		this.headImgUrl = value;
	}
	public java.lang.String getSubscribeTime() {
		return this.subscribeTime;
	}
	
	public void setSubscribeTime(java.lang.String value) {
		this.subscribeTime = value;
	}
	public java.lang.String getAccountId() {
		return this.accountId;
	}
	
	public void setAccountId(java.lang.String value) {
		this.accountId = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
}