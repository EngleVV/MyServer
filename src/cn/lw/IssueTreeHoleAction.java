/*
 * IssueTreeHoleAction.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

import cn.lw.items.TreeHoleItem;

import com.google.gson.Gson;

/**
 * @author Engle
 * 
 */
public class IssueTreeHoleAction {

	private String username;
	private String sessionId;
	private String treeHoleItem;

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

	/**
	 * @return the treeHoleItem
	 */
	public String getTreeHoleItem() {
		return treeHoleItem;
	}

	/**
	 * @param treeHoleItem
	 *            the treeHoleItem to set
	 */
	public void setTreeHoleItem(String treeHoleItem) {
		this.treeHoleItem = treeHoleItem;
	}

	public void IssueTreeHole() {
		SessionConnHelper conn = new SessionConnHelper();
		String strJson;
		Boolean result = false;
		if (conn.isValidSession(username, sessionId)) {
			// 验证通过
			TreeHoleConnHelper treeHoleConn = new TreeHoleConnHelper();
			Gson gson = new Gson();
			TreeHoleItem item = gson.fromJson(treeHoleItem, TreeHoleItem.class);
			result = treeHoleConn.insertItem(item);
		}
		strJson = "{\"result\":\"" + result + "\"}";
		System.out.println(strJson);
		ResponseUtil.sendResponse(strJson);
	}

}
