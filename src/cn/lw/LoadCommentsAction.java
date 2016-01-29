/*
 * LoadComments.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import cn.lw.items.CommentItem;

/**
 * @author Engle
 * 
 */
public class LoadCommentsAction {

	private String treeHoleUuid;

	private int pageIndex;

	/**
	 * @return the treeHoleUuid
	 */
	public String getTreeHoleUuid() {
		return treeHoleUuid;
	}

	/**
	 * @param treeHoleUuid
	 *            the treeHoleUuid to set
	 */
	public void setTreeHoleUuid(String treeHoleUuid) {
		this.treeHoleUuid = treeHoleUuid;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex
	 *            the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void LoadComments() {
		CommentConnHelper conn = new CommentConnHelper();
		List<CommentItem> commentsList = new ArrayList<CommentItem>();
		commentsList = conn.queryComments(treeHoleUuid, pageIndex);
		Gson gson = new Gson();
		String strJson = gson.toJson(commentsList);
		System.out.println(strJson);
		ResponseUtil.sendResponse(strJson);
		conn.close();
	}
}
