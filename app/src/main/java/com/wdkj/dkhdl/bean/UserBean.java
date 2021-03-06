package com.wdkj.dkhdl.bean;

import java.io.Serializable;

/**
 * 登录用户实体（业务员）
 */
public class UserBean implements Serializable{



    private String name;//商户名称
    private String agent_id;//代理id
    private String salesman_id;//id
    private String role;//角色
    private String account;//登录用户名


    public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

    public String getSalesman_id() {
        return salesman_id;
    }

    public void setSalesman_id(String salesman_id) {
        this.salesman_id = salesman_id;
    }

    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
