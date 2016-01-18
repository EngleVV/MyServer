/*
 * RegisterAction.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

/**
 * @author Engle
 */
public class RegisterAction {

	/** 用户名 */
	private String username;

	/** 密码 */
	private String password;

	/** 确认密码 */
	private String confirmPassword;

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
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * 验证用户名是否已经存在
	 */
	public void validateUsername() {
		SessionConnHelper conn = new SessionConnHelper();
		Boolean result = conn.isUserValid(username);
		String strJson = "{\"" + "result" + "\":\"" + result + "\"}";
		ResponseUtil.sendResponse(strJson);
	}

	/**
	 * 验证通过,成功注册
	 */
	public void register() {
		Boolean result = false;
		if (validateRegisterInfo()) {
			// 满足注册条件,可以注册
			SessionConnHelper conn = new SessionConnHelper();
			if (conn.insertUser(username, password) > 0) {
				// 插入成功
				result = true;
			}
			conn.close();
		}
		String strJson = "{\"" + "result" + "\":\"" + result + "\"}";
		System.out.println(strJson);
		ResponseUtil.sendResponse(strJson);
	}

	private Boolean validateRegisterInfo() {
		SessionConnHelper conn = new SessionConnHelper();
		if (username == null || password == null || confirmPassword == null)
			return false;

		if (conn.isUserValid(username)) {
			conn.close();
			if (password.length() >= 6 && password.length() <= 16) {
				if (password.equals(confirmPassword)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			conn.close();
			return false;
		}
	}
}
