/*
 * ConnectionHelper.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库辅助类
 * 
 * @author Engle
 * 
 */
public class SessionConnHelper {

	private Connection conn;

	public SessionConnHelper() {

		try {
			String url = "jdbc:mysql://127.0.0.1:3306/user_info";
			String username = "root";
			String password = "123456";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取用户登陆sessionid
	 * 
	 * @param sql
	 *            查询语句
	 * @return 用户sessionid
	 */
	public String querySession(String sql) {
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				return rs.getString("session_id");
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 验证用户是否存在
	 * 
	 * @param user
	 *            用户名
	 * @param pwd
	 *            密码
	 * @return 结果
	 */
	public Boolean queryUser(String user, String pwd) {
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("select * from users where username = '"
							+ user + "' and password = '" + pwd + "'");
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 插入sessionid,如果已存在则覆盖
	 * 
	 * @param item
	 *            包含sessionid和username
	 * @return 返回行数
	 */
	public int insert(SessionItem item) {
		Statement st;
		try {
			st = conn.createStatement();
			String sql = "insert into users_session values('"
					+ item.getUsername() + "', '" + item.getSessionId()
					+ "', '" + item.getCreateDate()
					+ "') ON DUPLICATE KEY UPDATE session_id='"
					+ item.getSessionId() + "', create_date='"
					+ item.getCreateDate() + "'";
			return st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 验证sessionid是否有效
	 * 
	 * @param username
	 *            用户名
	 * @param sessionId
	 *            用户登录sessionid
	 * @return 结果
	 */
	public Boolean isValidSession(String username, String sessionId) {
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("select * from users_session where username = '"
							+ username
							+ "' and session_id = '"
							+ sessionId
							+ "'");
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// -----------------------注册用------------------------
	Boolean isUserValid(String username) {
		Statement st;
		try {
			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("select * from users where username = '"
							+ username + "'");
			if (rs.next()) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int insertUser(String username, String password) {
		Statement st;
		try {
			st = conn.createStatement();
			String sql = "insert into users values('" + username + "', '"
					+ password + "')";
			return st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 关闭数据库连接
	 */
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
