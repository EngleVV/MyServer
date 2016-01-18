/*
 * GetDetailAction.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author Engle
 */
public class GetDetailAction implements Action {

	private List<DetailItem> detailList;

	private String username;

	private String sessionId;

	/**
	 * @return the detailList
	 */
	public List<DetailItem> getDetailList() {
		return detailList;
	}

	/**
	 * @param detailList
	 *            the detailList to set
	 */
	public void setDetailList(List<DetailItem> detailList) {
		this.detailList = detailList;
	}

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

	/*
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	public String execute() throws Exception {

		String user = (String) ActionContext.getContext().getSession()
				.get("user");
		if (null != user) {
			setDetailList(getData(user));
			return SUCCESS;
		} else {
			return LOGIN;
		}

	}

	/**
	 * 客户端从服务器同步数据
	 */
	public void ClientGetDetail() {
		SessionConnHelper conn = new SessionConnHelper();
		String strJson;
		if (conn.isValidSession(getUsername(), getSessionId())) {
			// 验证通过
			List<DetailItem> list = getData(getUsername());
			Gson gson = new Gson();
			strJson = gson.toJson(list);
		} else {
			// 验证不通过
			strJson = "{\"" + "result" + "\":\"false\"}";
		}
		System.out.println(strJson);
		ResponseUtil.sendResponse(strJson);
	}

	private List<DetailItem> getData(String user) {
		List<DetailItem> list = new ArrayList<DetailItem>();

		try {
			DetailConnHelper conn = new DetailConnHelper();
			String sql = "select id,consume_date,consume_type,account_type,consume_amount,last_modify_date from detail_record where user_id = '"
					+ user + "' order by consume_date desc";
			ResultSet resultSet = conn.query(sql);
			while (resultSet.next()) {
				DetailItem detailItem = new DetailItem();
				detailItem.setUuid(resultSet.getString("id"));
				detailItem.setDayDetailConsumeDate(resultSet.getTimestamp(
						"consume_date").toString());
				detailItem.setDayDetailConsumeType(resultSet
						.getString("consume_type"));
				detailItem.setDayDetailAccountType(resultSet
						.getString("account_type"));
				detailItem.setDayDetailConsumeAmount(String.format("%.2f",
						resultSet.getDouble("consume_amount")));
				detailItem.setLastModifyDate(resultSet
						.getTimestamp("last_modify_date").toString()
						.substring(0, 19));
				list.add(detailItem);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}
