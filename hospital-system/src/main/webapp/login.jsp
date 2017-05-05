<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>登陆界面 </title>
		<%@ include file="source.jsp"%>
		<link href="/static/css/app.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
	</head>

	<body>
		<div id="mainWrapper">
			<div class="login-container">
				<div class="login-card">
					<div class="login-form">
						<form action="/login" method="post" class="form-horizontal">
							<s:if test="#parameters.error != null">
								<div class="alert alert-danger">
									<p>Invalid username and password.</p>
								</div>
							</s:if>
							<s:if test="#parameters.logout != null">
								<div class="alert alert-success">
									<p>成功退出登录</p>
								</div>
							</s:if>
							<s:if test="#parameters.timeout != null">
								<div class="alert alert-danger">
									<p>会话超时，重新登录</p>
								</div>
							</s:if>
							<div class="input-group input-sm">
								<label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
								<input type="text" class="form-control" id="username" name="ssoId" placeholder="用户名" required>
							</div>
							<div class="input-group input-sm">
								<label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label> 
								<input type="password" class="form-control" id="password" name="password" placeholder="密码" required>
							</div>
							<input type="hidden" name="<s:property value="#request._csrf.parameterName"/>"  value="<s:property value="#request._csrf.token"/>" />
								
							<div class="form-actions">
								<input type="submit"
									class="btn btn-block btn-primary btn-default" value="Log in">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<s:debug></s:debug>
<%
  java.util.Enumeration<String> sessEnum = request.getSession()
	.getAttributeNames();
  while (sessEnum.hasMoreElements()) {
	String s = sessEnum.nextElement();
	out.print(s);
	out.println("==" + request.getSession().getAttribute(s));
%><br />
<%
  }
%>
	</body>
</html>