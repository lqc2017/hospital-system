<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>管理员主页</title>
<%@ include file="../source.jsp"%>
</head>
<body>
<span style="float: right; margin: 8px 30px"><s:property value="admin.name"/>,你好！<a
		href="/logout">登出</a></span>
 <button  class="btn btn-default" onclick="javascript:location.href='/admin/staff'">人员管理</button>
 <button  class="btn btn-default" onclick="javascript:location.href='/medicine/list'">药品管理</button>
 <button  class="btn btn-default" onclick="javascript:location.href='/project/list'">检查项目管理</button>
 
 <s:debug/>
 <script type="text/javascript">
 </script>
</body>
</html>