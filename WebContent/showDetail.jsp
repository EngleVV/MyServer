<%@page import="cn.lw.DetailItem"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*, com.opensymphony.xwork2.util.*"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>消费记录明细</title>
</head>
<body>
	<table border="1">
		<caption>个人消费明细</caption>
		<tr>
			<td>消费日期</td>
			<td>消费类型</td>
			<td>消费账户</td>
			<td>消费金额</td>
		</tr>
		<!-- 迭代输入ValueStack中的detail对象,其中status是迭代的序号 -->
		<s:iterator value="detailList" status="index">
			<!-- 判断序号是否为奇数 -->
			<s:if test="#index.odd == true">
				<tr style="background-color: #cccccc">
			</s:if>
			<!-- 判断迭代元素的序号是否为偶数 -->
			<s:else>
				<tr>
			</s:else>
			<td><s:property value="dayDetailConsumeDate" /></td>
			<td><s:property value="dayDetailConsumeType" /></td>
			<td><s:property value="dayDetailAccountType" /></td>
			<td><s:property value="dayDetailConsumeAmount" /></td>
		</s:iterator>
	</table>
</body>
</html>