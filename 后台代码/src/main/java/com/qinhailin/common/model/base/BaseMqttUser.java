package com.qinhailin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseMqttUser<M extends BaseMqttUser<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setUsername(java.lang.String username) {
		set("username", username);
		return (M)this;
	}
	
	public java.lang.String getUsername() {
		return getStr("username");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}
	
	public java.lang.String getPassword() {
		return getStr("password");
	}

	public M setSalt(java.lang.String salt) {
		set("salt", salt);
		return (M)this;
	}
	
	public java.lang.String getSalt() {
		return getStr("salt");
	}

	public M setIsSuperuser(java.lang.Boolean isSuperuser) {
		set("is_superuser", isSuperuser);
		return (M)this;
	}
	
	public java.lang.Boolean getIsSuperuser() {
		return get("is_superuser");
	}

	public M setCreated(java.util.Date created) {
		set("created", created);
		return (M)this;
	}
	
	public java.util.Date getCreated() {
		return get("created");
	}

}
