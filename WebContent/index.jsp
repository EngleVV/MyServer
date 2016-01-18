<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>登陆页面</title>
</head>
<body>
	<s:form action="Login">
		<s:textfield name="username" label="用户名" />
		<s:textfield name="password" label="密码" />
		<s:submit value="登陆" />
	</s:form>
</body>
</html>