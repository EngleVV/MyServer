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
public class DetailConnHelper {

	private Connection conn;

	public DetailConnHelper() {

		try {
			String url = "jdbc:mysql://127.0.0.1:3306/detail_record";
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

	public ResultSet query(String sql) {
		Statement st;
		try {
			st = conn.createStatement();
			return st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int update(DetailItem item) {
		Statement st;
		try {
			st = conn.createStatement();
			String sql = "update detail_record set consume_date = '"
					+ item.getDayDetailConsumeDate() + "', consume_type='"
					+ item.getDayDetailConsumeType() + "', account_type='"
					+ item.getDayDetailAccountType() + "', consume_amount='"
					+ item.getDayDetailConsumeAmount() + "' where id='"
					+ item.getUuid() + "'";
			return st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}

	}

	public int delete(String uuid) {
		Statement st;
		try {
			st = conn.createStatement();
			String sql = "delete from detail_record where id = '" + uuid + "'";
			return st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int insert(DetailItem item, String username) {
		Statement st;
		try {
			st = conn.createStatement();
			String sql = "insert into detail_record values('" + item.getUuid()
					+ "', '" + username + "', '"
					+ item.getDayDetailConsumeDate() + "', '"
					+ item.getDayDetailConsumeType() + "', '"
					+ item.getDayDetailAccountType() + "', '"
					+ item.getDayDetailConsumeAmount() + "', '"
					+ item.getLastModifyDate() + "')";
			return st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
