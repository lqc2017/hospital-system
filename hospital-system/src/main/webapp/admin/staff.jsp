<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>员工查看</title>
<%@ include file="../source.jsp"%>
</head>
<body>
	<div style="width: 95%; margin: 0 auto;">
		<ol class="breadcrumb">
			<li><a href="/admin/home">返回主页</a></li>
		</ol>
	</div>
	
	<ul id="myTab" class="nav nav-tabs">
		<li class="active"><a href="#doctor" data-toggle="tab">医生</a></li>
		<li><a href="#cashier" data-toggle="tab">收银员</a></li>
		<li><a href="#admin" data-toggle="tab">管理员</a></li>
	</ul>

	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="doctor">
			<div style="height: 500px; margin: 10px auto">
				<iframe frameborder="no"
					style="scrolling: auto; width: 100%; height: 100%;"
					src="/doctor/list"></iframe>
			</div>
		</div>

		<div class="tab-pane fade" id="cashier">
			<div style="height: 500px; margin: 10px auto">
				<iframe frameborder="no"
					style="scrolling: auto; width: 100%; height: 100%;"
					src="/cashier/list"></iframe>
			</div>
		</div>
		
		<div class="tab-pane fade" id="admin">
			<div style="height: 500px; margin: 10px auto">
				<iframe frameborder="no"
					style="scrolling: auto; width: 100%; height: 100%;"
					src="/admin/list"></iframe>
			</div>
		</div>

	</div>

	<script defer type="text/javascript">
		$('.form_datetime').datetimepicker({
			//language:  'fr',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 3,
			minView : 3,
			forceParse : 0,
			showMeridian : 1
		});
	</script>
</body>
</html>