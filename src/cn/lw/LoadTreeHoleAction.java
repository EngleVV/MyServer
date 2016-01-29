/*
 * LoadTreeHoleAction.java
 * https://github.com/EngleVV/MyRepository
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package cn.lw;

import java.util.ArrayList;
import java.util.List;

import cn.lw.items.TreeHoleItem;

import com.google.gson.Gson;

/**
 * @author Engle
 * 
 */
public class LoadTreeHoleAction {

	/** 分页索引 */
	private int pageIndex;

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

	/**
	 * 获取treehole数据
	 */
	public void LoadTreeHole() {
		List<TreeHoleItem> treeHoleList = new ArrayList<TreeHoleItem>();
		TreeHoleConnHelper conn = new TreeHoleConnHelper();
		treeHoleList = conn.queryItems(pageIndex);
		Gson gson = new Gson();
		String strJson = gson.toJson(treeHoleList);
		System.out.println(strJson);
		ResponseUtil.sendResponse(strJson);
		conn.close();
	}

}
