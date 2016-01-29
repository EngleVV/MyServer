/*
 * CommentConnHelper.java
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

import cn.lw.items.CommentItem;

/**
 * @author Engle
 * 
 */
public class CommentConnHelper {
	private static final int COUNTPERPAGE = 5;

	private Connection conn;

	public CommentConnHelper() {
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

	List<CommentItem> queryComments(String itemUuid, int pageIndex) {

		try {
			List<CommentItem> commentsList = new ArrayList<CommentItem>();
			Statement st = conn.createStatement();
			String sql = "select * from treehole_comments where item_uuid = '"
					+ itemUuid + "' order by date limit " + COUNTPERPAGE
					* pageIndex + "," + COUNTPERPAGE;
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				CommentItem commentItem = new CommentItem();
				commentItem.setUuid(rs.getString("uuid"));
				commentItem.setItemUuid(rs.getString("item_uuid"));
				commentItem.setContent(rs.getString("content"));
				commentItem.setUsername(rs.getString("username"));
				commentItem.setDate(rs.getTimestamp("date").toString()
						.substring(0, 19));
				commentItem.setPraises(rs.getInt("praises"));
				commentsList.add(commentItem);
			}
			return commentsList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
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
