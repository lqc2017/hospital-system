<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>跳转页面</title>
</head>
<body style="bgcolor: gray;">
	<h1>支付成功...正在跳转</h1>
	<div id="sp"></div>

	<script type="text/javascript">
		onload = function() {
			setInterval(go, 1000);
		};
		var x = 3; //利用了全局变量来执行 
		function go() {
			x--;
			if (x > 0) {
				document.getElementById("sp").innerHTML = x; //每次设置的x的值都不一样了。 
			} else {
				location.href = "/patient/information?patientId=<s:property value='#patientId'/>";
			}
		}
	</script>
</body>
</html>