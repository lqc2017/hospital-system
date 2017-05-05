<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>信息查看</title>
<%@ include file="../source.jsp"%>
</head>
<body>

	<div style="width: 95%; margin: 0 auto;">
		<ol class="breadcrumb">
			<li><a href="/patient/home">返回主页</a></li>
		</ol>
	</div>
	<%-- <input id="active" type="hidden"
		value="<s:property value='#active' />" /> --%>
	<span style="float: right; margin: 8px 30px"><s:property value="#patient.name"/>,你好！<a
		href="/logout">登出</a></span>
	<ul id="myTab" class="nav nav-tabs">
		<li class="active"><a href="#information" data-toggle="tab">
				个人信息 </a></li>
		<li><a href="#record" data-toggle="tab">病例</a></li>
		<li><a href="#hospitalization" data-toggle="tab">住院</a></li>
		<li><a href="#order" data-toggle="tab">已付款订单</a></li>
	</ul>

	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="information">
			<div style="height: 500px; margin: 10px auto">
				<form class="form-horizontal">
					<div class="form-group">
						<label class="col-lg-2 control-label">姓名：</label>
						<div style="width: 60%; margin: 10px 20%">
							<label class="col-lg-2"><s:property value="#patient.name"/></label>
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-2 control-label">性别：</label>
						<div style="width: 60%; margin: 10px 20%">
							<label class="col-lg-2"> 
									<s:if test="#patient.sex==0">男</s:if>
									<s:if test="#patient.sex==1">女</s:if>
							</label>
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-2 control-label">年龄：</label>
						<div style="width: 60%; margin: 10px 20%">
							<label class="col-lg-2"><s:property value="#patient.age"/></label>
						</div>
					</div>
				</form>
			</div>
		</div>

		<div class="tab-pane fade" id="record">
			<div style="height: 500px; margin: 10px auto">
				<iframe frameborder="no"
					style="scrolling: auto; width: 100%; height: 100%;"
					src="/record/patient_list?so.patientId=<s:property value='#patient.id'/>"></iframe>
			</div>
		</div>

		<div class="tab-pane fade" id="hospitalization">
			<div style="height: 500px; margin: 10px auto">
				<iframe frameborder="no"
					style="scrolling: auto; width: 100%; height: 100%;"
					src="/hospitalization/patient_list?so.patientId=<s:property value='#patient.id'/>"></iframe>
			</div>
		</div>

		<%-- <div class="tab-pane fade" id="order">
			<div style="height: 500px; margin: 10px auto">
				<iframe frameborder="no"
					style="scrolling: auto; width: 100%; height: 100%;"
					src="/order/patient_list?so.status=10&patientId=<s:property value='#patient.id'/>"></iframe>
			</div>

		</div> --%>
		<div class="tab-pane fade" id="order">
			<div style="height: 500px; margin: 10px auto">
				<iframe frameborder="no"
					style="scrolling: auto; width: 100%; height: 100%;"
					src="/order/patient_list"></iframe>
			</div>
		</div>
	</div>

	<form id="pay_form" method="post" action="/order/pay_order">
		<input type="hidden" name="<s:property value="#request._csrf.parameterName"/>"  value="<s:property value="#request._csrf.token"/>" />
		 <input type="hidden" name="patientId"
			value="<s:property value='#patient.id'/>"> <input type="hidden" name="orderId"
			value="">
	</form>

	<script defer type="text/javascript">
		/* var active = "${active}";
		if (active != '') {
			$("#myTabContent").children("div").removeClass("active in");
			$("#myTabContent").children("#${active}").addClass("active in");

			$("#myTab").children().removeClass("active");
			$("#myTabContent").children("#${active}").addClass("active in");
			$("#myTab").find("a[href='#${active}']").parent("li").addClass(
					"active");
			if (active == 'paid' || active == 'unPaid') {
				$("#myTab").children(".dropdown").addClass("active");
			}
		} */
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