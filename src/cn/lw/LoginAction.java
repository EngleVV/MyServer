/*
 * LoginAction.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author Engle
 * 
 */
public class LoginAction implements Action {
	private String username;
	private String password;
	private String sessionId;

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId
	 *            the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String execute() throws Exception {
		if (LoginValidate()) {
			ActionContext.getContext().getSession().put("user", getUsername());
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public void userid() throws IOException {
		String jsonString;
		if (LoginValidate()) {
			HttpServletRequest request = ServletActionContext.getRequest();
			System.out.println(getUsername() + "登录成功");
			SessionConnHelper sessionConnHelper = new SessionConnHelper();
			SessionItem item = new SessionItem();
			item.setUsername(getUsername());
			item.setSessionId(request.getSession().getId());
			item.setCreateDate(CalendarUtils.toStandardDateString(new Date()));
			sessionConnHelper.insert(item);

			jsonString = "{\"" + "sessionId" + "\":\""
					+ request.getSession().getId() + "\"}";

			sessionConnHelper.close();
		} else {
			// 登录失败,返回空sessionId
			jsonString = "{\"sessionId\":\"\"}";
		}
		ResponseUtil.sendResponse(jsonString);

	}

	private Boolean LoginValidate() {
		SessionConnHelper conn = new SessionConnHelper();
		Boolean result = conn.queryUser(getUsername(), getPassword());
		conn.close();
		return result;
	}

	public void checkSessionId() throws IOException {

		String jsonString;
		SessionConnHelper conn = new SessionConnHelper();
		Boolean result = conn.isValidSession(getUsername(), getSessionId());
		conn.close();
		jsonString = "{\"" + "result" + "\":\"" + result + "\"}";
		System.out.println(jsonString);
		ResponseUtil.sendResponse(jsonString);
	}
}
