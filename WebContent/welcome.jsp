<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>成功页面</title>
</head>
<body>欢迎,${sessionScope.user},成功登陆！！3秒后为你自动跳转<br/>
  如果页面没有自动加载，请
  <a href="GetDetail.action">单击这里</a>
</body>
<SCRIPT type="text/javascript">
	setTimeout("loady()", 3000);
	function loady() {
		window.location = 'GetDetail.action';
	}
</SCRIPT>

</html>