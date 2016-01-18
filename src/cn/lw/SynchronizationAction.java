/*
 * SynchronizationAction.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Engle
 * 
 */
public class SynchronizationAction {

	private String username;
	private String sessionId;
	private String detailList;

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
	 * @return the detailList
	 */
	public String getDetailList() {
		return detailList;
	}

	/**
	 * @param detailList
	 *            the detailList to set
	 */
	public void setDetailList(String detailList) {
		this.detailList = detailList;
	}

	/**
	 * 响应客户端的请求,上传数据至服务器
	 * 
	 * @throws SQLException
	 */
	public void execute() throws SQLException {
		SessionConnHelper sessionConnHelper = new SessionConnHelper();
		String sql = "select session_id from users_session where username = '"
				+ getUsername() + "'";
		String serverSessionId = sessionConnHelper.querySession(sql);
		Boolean result = false;
		String jsonString;
		if (getSessionId().equals(serverSessionId)) {
			System.out.println(detailList);
			Gson gson = new Gson();
			List<DetailItem> list = gson.fromJson(detailList,
					new TypeToken<List<DetailItem>>() {
					}.getType());
			DetailConnHelper connHelper = new DetailConnHelper();
			sql = "select * from detail_record where user_id='" + getUsername()
					+ "'";
			ResultSet rs = connHelper.query(sql);
			while (rs.next()) {
				int i = 0;
				boolean flag = true;
				for (i = 0; i < list.size(); i++) {
					// 数据库中存在对应数据
					if (list.get(i).getUuid().equals(rs.getString("id"))) {
						// 最后修改时间不一致
						if (!list
								.get(i)
								.getLastModifyDate()
								.equals(rs.getTimestamp("last_modify_date")
										.toString().substring(0, 19))) {
							// 本条记录需要update
							connHelper.update(list.get(i));
						}
						// 时间一致
						else {
							// 开始遍历rs的下一条数据
							// 讲本条从uuidList中移除
							list.remove(i);
							System.out.println("本条服务器已经存在");
						}
						flag = false;
						break;
					}
				}

				// 遍历list结束,没有找到对应记录,说明本地已经删除该记录,数据库需要同步,
				// 并非由break结束for循环
				if (flag) {
					// 本条记录需要删除
					connHelper.delete(rs.getString("id"));
					System.out.println("删除成功");
				}
			}

			// 遍历完rs,list中仍然有数据,说明该数据需要添加进服务器
			if (0 != list.size()) {
				// 这些记录需要新增
				for (DetailItem item : list) {
					System.out.println("插入成功");
					connHelper.insert(item, getUsername());
				}
			}
			connHelper.close();
			result = true;
		} else {
			// 登陆失败
			System.out.println("登陆失败");
		}
		sessionConnHelper.close();
		jsonString = "{\"" + "result" + "\":\"" + result + "\"}";
		ResponseUtil.sendResponse(jsonString);
	}
}
