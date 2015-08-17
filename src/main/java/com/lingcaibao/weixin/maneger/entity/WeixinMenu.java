package com.lingcaibao.weixin.maneger.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import weixin.lingcaibao.bean.MenuButtons.Button;


public class WeixinMenu {

	//alias
	public static final String TABLE_ALIAS = "WeixinMenu";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_NAME = "name";
	public static final String ALIAS_ORDERS = "orders";
	public static final String ALIAS_TYPE = "type";
	public static final String ALIAS_URL = "url";
	public static final String ALIAS_KEY = "key";
	public static final String ALIAS_PRENTS_ID = "prentsId";
	public static final String ALIAS_ACCOUNT_ID = "accountId";
	public static final String ALIAS_CREATER_ID = "createrId";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_UPDATER_ID = "updaterId";
	public static final String ALIAS_UPDATE_TIME = "updateTime";

	private List<WeixinMenu> subWeimenus = new ArrayList<WeixinMenu>();
	/**
	 * 
	 */
	private String id;
	/**
	 * menu名称
	 */
	private String name;
	/**
	 * menu单组排序
	 */
	private Integer orders;
	/**
	 * click or view
	 */
	private String type;
	/**
	 * 如果type=view url不能为空
	 */
	private String url;
	/**
	 * 如果type=click url不能为空
	 */
	private String key;
	/**
	 * 父ID
	 */
	private String prentsId;
	/**
	 * 微信帐号id
	 */
	private String accountId;
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
	public java.lang.Integer getOrders() {
		return this.orders;
	}
	
	public void setOrders(java.lang.Integer value) {
		this.orders = value;
	}
	public java.lang.String getType() {
		return this.type;
	}
	
	public void setType(java.lang.String value) {
		this.type = value;
	}
	public java.lang.String getUrl() {
		return this.url;
	}
	
	public void setUrl(java.lang.String value) {
		this.url = value;
	}
	public java.lang.String getKey() {
		return this.key;
	}
	
	public void setKey(java.lang.String value) {
		this.key = value;
	}
	public java.lang.String getPrentsId() {
		return this.prentsId;
	}
	
	public void setPrentsId(java.lang.String value) {
		this.prentsId = value;
	}
	public java.lang.String getAccountId() {
		return this.accountId;
	}
	
	public void setAccountId(java.lang.String value) {
		this.accountId = value;
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

	public List<WeixinMenu> getSubWeimenus() {
		return subWeimenus;
	}

	public void setSubWeimenus(List<WeixinMenu> subWeimenus) {
		this.subWeimenus = subWeimenus;
	}
	
	/**
	 * 
	 * @return
	 */
	public Button toMenuButton() {
		Button button = new Button();
		button.setKey(StringUtils.isNotBlank(this.getKey())?this.getKey():null);
		button.setName(StringUtils.isNotBlank(this.getName())?this.getName():null);
		button.setType(StringUtils.isNotBlank(this.getType())?this.getType():null);
		button.setUrl(StringUtils.isNotBlank(this.getUrl())?this.getUrl():null);
		List<WeixinMenu> weixinMenus =this.getSubWeimenus();
		List<Button> supButtons = new ArrayList<Button>(); 
		for (WeixinMenu weixinMenu : weixinMenus) {
			Button button1 = new Button();
			button1.setKey(StringUtils.isNotBlank(weixinMenu.getKey())?weixinMenu.getKey():null);
			button1.setName(StringUtils.isNotBlank(weixinMenu.getName())?weixinMenu.getName():null);
			button1.setType(StringUtils.isNotBlank(weixinMenu.getType())?weixinMenu.getType():null);
			button1.setUrl(StringUtils.isNotBlank(weixinMenu.getUrl())?weixinMenu.getUrl():null);
			supButtons.add(button1);
		}
		if (supButtons.size()>0) {
			button.setSub_button(supButtons);
		}
		return button;
		
	}
}