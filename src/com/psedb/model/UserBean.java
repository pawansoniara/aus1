/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psedb.model;

public class UserBean {
   private String userType;
   private String userName;
   private Integer accessId;
   private String loginName;
   private Byte id;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAccessId() {
        return accessId;
    }

    public void setAccessId(Integer accessId) {
        this.accessId = accessId;
    }

    @Override
    public String toString() {
        return userType +" : "+userName+" : "+accessId; //To change body of generated methods, choose Tools | Templates.
    }

	public Byte getId() {
		return id;
	}

	public void setId(Byte id) {
		this.id = id;
	}
    
    
}
