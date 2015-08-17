package com.lingcaibao.weixin.user.entity;


public class UserInfo {

	//alias
	public static final String TABLE_ALIAS = "UserInfo";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_ID = "userId";
	public static final String ALIAS_NICK_NAME = "nickName";
	public static final String ALIAS_HEAD_IMG_URL = "headImgUrl";
	public static final String ALIAS_REAL_NAME = "realName";
	public static final String ALIAS_GENDER = "gender";
	public static final String ALIAS_CARD_TYPE = "cardType";
	public static final String ALIAS_CARD_NO = "cardNo";
	public static final String ALIAS_EMAIL = "email";
	public static final String ALIAS_MOBILE = "mobile";
	public static final String ALIAS_LANGUAGE = "language";
	public static final String ALIAS_CITY = "city";
	public static final String ALIAS_PROVINCE = "province";
	public static final String ALIAS_COUNTRY = "country";
	public static final String ALIAS_OPERATOR = "operator";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_MODIFY_TIME = "modifyTime";

	/**
	 * 用户主键,uuid
	 */
	private String id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String headImgUrl;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 用户性别: 0 未知；1:男；2：女
	 */
	private Integer gender;
	/**
	 * 证件类型
	 */
	private Integer cardType;
	/**
	 * 证件号码
	 */
	private String cardNo;
	/**
	 * 用户邮箱
	 */
	private String email;
	/**
	 * 用户手机号码
	 */
	private String mobile;
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
	 * 操作者
	 */
	private String operator;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	private java.util.Date modifyTime;

	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	public java.lang.String getNickName() {
		return this.nickName;
	}
	
	public void setNickName(java.lang.String value) {
		this.nickName = value;
	}
	public java.lang.String getHeadImgUrl() {
		return this.headImgUrl;
	}
	
	public void setHeadImgUrl(java.lang.String value) {
		this.headImgUrl = value;
	}
	public java.lang.String getRealName() {
		return this.realName;
	}
	
	public void setRealName(java.lang.String value) {
		this.realName = value;
	}
	public Integer getGender() {
		return this.gender;
	}
	
	public void setGender(Integer value) {
		this.gender = value;
	}
	public Integer getCardType() {
		return this.cardType;
	}
	
	public void setCardType(Integer value) {
		this.cardType = value;
	}
	public java.lang.String getCardNo() {
		return this.cardNo;
	}
	
	public void setCardNo(java.lang.String value) {
		this.cardNo = value;
	}
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(java.lang.String value) {
		this.mobile = value;
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
	public java.lang.String getOperator() {
		return this.operator;
	}
	
	public void setOperator(java.lang.String value) {
		this.operator = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getModifyTime() {
		return this.modifyTime;
	}
	
	public void setModifyTime(java.util.Date value) {
		this.modifyTime = value;
	}
}