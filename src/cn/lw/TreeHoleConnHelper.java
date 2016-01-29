/*
 * TreeHoleConnHelper.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.lw.items.TreeHoleItem;

/**
 * TreeHole辅助类
 * 
 * @author Engle
 */
public class TreeHoleConnHelper {

	private static final int COUNTPERPAGE = 5;

	private Connection conn;

	public TreeHoleConnHelper() {
		try {
			String url = "jdbc:mysql://127.0.0.1:3306/treehole";
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
	 * 获取treeHoleItem
	 * 
	 * @return 列表
	 */
	public List<TreeHoleItem> queryItems(int pageIndex) {
		try {

			Statement st;
			st = conn.createStatement();
			String sql = "select * from treehole_items order by date desc limit "
					+ COUNTPERPAGE * pageIndex + "," + COUNTPERPAGE;
			ResultSet rs = st.executeQuery(sql);
			List<TreeHoleItem> treeHoleList = new ArrayList<TreeHoleItem>();
			while (rs.next()) {
				TreeHoleItem treeHoleItem = new TreeHoleItem();
				treeHoleItem.setUuid(rs.getString("uuid"));
				treeHoleItem.setContent(rs.getString("content"));
				treeHoleItem.setUsername(rs.getString("username"));
				treeHoleItem.setDate(rs.getTimestamp("date").toString()
						.substring(0, 19));
				treeHoleItem.setPraises(rs.getInt("praises"));
				treeHoleItem.setComments(rs.getInt("comments"));
				treeHoleList.add(treeHoleItem);
			}
			return treeHoleList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean insertItem(TreeHoleItem treeHoleItem) {
		try {
			Statement st = conn.createStatement();
			String sql = "insert into treehole_items values('"
					+ treeHoleItem.getUuid() + "', '"
					+ treeHoleItem.getContent() + "', '"
					+ treeHoleItem.getUsername() + "', '"
					+ treeHoleItem.getDate() + "', '"
					+ treeHoleItem.getPraises() + "', '"
					+ treeHoleItem.getComments() + "')";
			int row = st.executeUpdate(sql);
			if (row > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addPraise(String uuid) {
		try {

			Statement st;
			st = conn.createStatement();
			String strLock = "lock table treehole_items write";
			st.execute(strLock);
			String sql = "update treehole_items set praises = praises + 1 where uuid = '"
					+ uuid + "'";
			String strUnlock = "unlock tables";
			int row = st.executeUpdate(sql);
			st.execute(strUnlock);
			if (row == 1)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public int queryPraises(String uuid) {
		try {

			Statement st;
			st = conn.createStatement();
			String sql = "select praises from treehole_items where uuid = '"
					+ uuid + "'";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				int praises = rs.getInt("praises");
				return praises;
			} else
				return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
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
