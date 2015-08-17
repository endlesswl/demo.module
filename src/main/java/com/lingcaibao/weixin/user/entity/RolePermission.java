package com.lingcaibao.weixin.user.entity;


public class RolePermission {

	//alias
	public static final String TABLE_ALIAS = "RolePermission";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_ROLEID = "roleid";
	public static final String ALIAS_PERMISSIONID = "permissionid";

	/**
	 * 
	 */
	private Long id;
	/**
	 * 
	 */
	private Long roleid;
	/**
	 * 
	 */
	private String permissionid;

	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.Long getRoleid() {
		return this.roleid;
	}
	
	public void setRoleid(java.lang.Long value) {
		this.roleid = value;
	}
	public java.lang.String getPermissionid() {
		return this.permissionid;
	}
	
	public void setPermissionid(java.lang.String value) {
		this.permissionid = value;
	}
}