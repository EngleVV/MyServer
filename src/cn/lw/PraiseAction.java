/*
 * PraiseAction.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

/**
 * @author Engle
 * 
 */
public class PraiseAction {

	private String uuid;

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void Praise() {
		TreeHoleConnHelper conn = new TreeHoleConnHelper();
		boolean result = conn.addPraise(uuid);
		int praises = conn.queryPraises(uuid);
		String strJson = null;
		if (result && praises >= 0) {
			// 返回当前赞的数目
			strJson = "{\"" + "praises" + "\":\"" + praises + "\"}";
		} else {
			strJson = "{\"" + "result" + "\":\"" + result + "\"}";
		}
		System.out.println(strJson);
		ResponseUtil.sendResponse(strJson);
		conn.close();
	}
}
