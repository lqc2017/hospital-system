<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>住院填写</title>
<%@ include file="../source.jsp"%>
</head>

<body>
	<div style="width: 90%; margin: 10px 5%; border: 1px solid;">
	<%-- <h3>Session Scope (key==values)</h3>
	<%
		java.util.Enumeration<String> sessEnum = request.getSession().getAttributeNames();
		while (sessEnum.hasMoreElements()) {
			String s = sessEnum.nextElement();
			out.print(s);
			out.println("==" + request.getSession().getAttribute(s));
	%><br />
	<%
		}
	%> --%>
		<!-- 将已有的处方和检查id集合传入到下一个页面 -->
		<form id="hospitalization_form" class="form-horizontal" method="post">
			<input type="hidden" name="hospitalization.patientId" value="<s:property value='#patient.id' />" />
			<input type="hidden" name="hospitalization.doctorId" value="<s:property value='#doctorId' />" />
			<input type="hidden" name="hospitalization.patientName" value="<s:property value='#patient.name' />" />

			<input type="hidden" name="<s:property value='#request._csrf.parameterName'/>"
				value="<s:property value='#request._csrf.token'/>" />
			<div class="form-group">
				<div style="width: 10%; margin: 10px 45%;">
					<h1>住院</h1>
				</div>
			</div>

			<div class="form-group" style="width: 80%; margin: 10px auto;">
					<label class="col-lg-2 control-label">姓名：</label> <label><s:property value="#patient.name" /></label>
			</div>

			<div class="form-group" style="width: 80%; margin: 10px auto;">
					<label class="col-lg-2 control-label">性别：</label> <label> <s:if test="#patient.sex==0">男</s:if>
						<s:else>女</s:else>
					</label>
			</div>

			<div class="form-group" style="width: 80%; margin: 10px auto;">
					<label class="col-lg-2 control-label">年龄：</label> <label><s:property value="#patient.age" /></label>
			</div>

			<div class="form-group" style="width: 80%; margin: 10px auto;">
				<label for=symptoms class="col-lg-2 control-label">症状：</label>
					<textarea class="form-control" rows="5" name="hospitalization.symptoms"></textarea>
			</div>

			<div class="form-group" style="width: 80%; margin: 10px auto;">
					<label for=describe class="col-lg-2 control-label">说明：</label>
					<textarea class="form-control" rows="4" name="hospitalization.describe"></textarea>
			</div>

			<div class="form-group">
				<div class="btn-toolbar" style="width: 18%; margin: 10px auto;">
					<button type="button" id="hospitalization_add" class="btn btn-success">添加住院</button>
				</div>
			</div>
		</form>
		<div id="update_dialog"></div>
		<div id="delete_dialog"></div>
	</div>

	<script type="text/javascript">
		var setWidth = 400;
		var setHeight = 600;
		var validator;
		var form = $("#hospitalization_form");


		//添住院
		$("#hospitalization_add").bind('click', function() {
			var bootstrapValidator = form.data('bootstrapValidator');
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				$.ajax({
					async : false,
					cache : true,
					type : "POST",
					url : "/hospitalization/add",
					data : form.serialize(),
					datatype : 'json',
					success : function(data) {
						alert("生成的hospitalizationId:"+data.hospitalizationId);
						if (data.result == 'success') {
							$.ajax({
								async : false,
								cache : true,
								type : "GET",
								url : "/order/add_hh_order?hospitalizationId="+data.hospitalizationId,
								datatype : 'json',
								success : function(data) {
									if (data.result == 'success') {
										alert("生成的orderId:"+data.orderId);
									}else{
										alert("生成的order失败,失败信息:"+data.message);
									}
								},
								error : function(jqXHR, textStatus, errorThrown) {}
							});
						}else{
							alert("信息:"+data.message);
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {}
				});
				parent.$("[name='hospitalizationEdit']",parent.document).text("住院已填写");
				parent.$("[name='hospitalizationEdit']",parent.document).attr("disabled",true);
				$.ajax({
					async : false,
					cache : true,
					type : "GET",
					url : "/doctor/update_peek?type=1",
					datatype : 'json',
					success : function(data) {
					},
					error : function(jqXHR, textStatus, errorThrown) {}
				});
				parent.$("#hospitalization_dialog",parent.document).dialog("close");
			}
		});
		
		

		$().ready(function() {
							$('#hospitalization_form').bootstrapValidator({
								message : 'This value is not valid',
								feedbackIcons : {
									valid : 'glyphicon glyphicon-ok',
									invalid : 'glyphicon glyphicon-remove',
									validating : 'glyphicon glyphicon-refresh'
								},
								trigger : 'change',
								fields : {
									'hospitalization.symptoms' : {
										validators : {
											notEmpty : {
												message : '症状不能为空'
											},
											stringLength : {
												min : 0,
												max : 150,
												message : '超过输入限制'
											}
										}
									},
									'hospitalization.describe' : {
										validators : {
											stringLength : {
												min : 0,
												max : 100,
												message : '超过输入限制'
											}
										}
									}
								}
							});
			})
	</script>
</body>
</html>

