<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>填写疗程</title>
<%@ include file="../source.jsp"%>
<meta http-equiv="content-type" content="application/json">
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf" content="<s:property value='#request._csrf.token'/>" />
<meta name="_csrf_header" content="<s:property value='#request._csrf.headerName'/>" />
</head>

<body>
	<div style="width: 60%; margin: 10px 20%;">
		<form role="form" id="data_form" method="post" action="/record/edit">
			<input type="hidden" name="<s:property value='#request._csrf.parameterName'/>"
				value="<s:property value='#request._csrf.token'/>" />
		
		</form>

		<form id="add_form" class="form-horizontal">
			<!-- 处方信息表单 -->
			<s:iterator value="#medicines" id="medicine" status="st">
				<div style="border: 1px solid">
					<input type="hidden" name="prescriptions[<s:property value='#st.index'/>].medicineId"
						value="<s:property value='#medicine.id'/>">
						

					<div class="form-group">
						<label class="col-sm-3 control-label">药品名称：</label>
						<div class="col-sm-3">
							<input type="text" readonly class="form-control"
								value="<s:property value='#medicine.name'/>">
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-3 control-label">药品类型：</label>
						<div class="col-sm-3">
							<input type="text" readonly class="form-control"
								<s:if test = "#medicine.type==10">value="口服"</s:if>
								<s:elseif test = "#medicine.type==20">value="外用"</s:elseif>>
						</div>
					</div>

					<div class="form-group">
						<label for="count" class="col-sm-3 control-label">数量：</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="prescriptions[<s:property value='#st.index'/>].count"
							<s:if test="#medicine.unit==10">placeholder="盒"</s:if>
							<s:elseif test="#medicine.unit==20">placeholder="瓶"</s:elseif>
							<s:elseif test="#medicine.unit==30">placeholder="包"</s:elseif>>
						</div>
					</div>

					<div class="form-group">
						<label for="totalCourse" class="col-sm-3 control-label">总疗程：</label>
						<div class="col-sm-2">
							<input type="text" class="form-control" name="prescriptions[<s:property value='#st.index'/>].totalCourse">
						</div>
					</div>

					<div class="form-group">
						<label for=instructor class="col-sm-3 control-label">服用指导：</label>
						<div class="col-sm-5">
							<textarea class="form-control" rows="5" name="prescriptions[<s:property value='#st.index'/>].instructor"></textarea>
						</div>
					</div>

				</div>
			</s:iterator>
		</form>
		<button name="submit" type="button" class="btn btn-success"
			style="float: right">添加</button>
	</div>

	<script type="text/javascript">
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});

		$("button[name='submit']").bind('click', function() {
			var dataForm = $("#data_form");
			var data = $("#add_form").serializeArray();
			var ss = jsonStrArray(data, 4);

			var bootstrapValidator = $("#add_form").data('bootstrapValidator');
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				$(this).attr("disabled",true);
				$.ajax({
					async : false,
					cache : true,
					type : "POST",
					url : "/prescription/multi_add",
					data : $("#add_form").serialize(),
					datatype : 'json',
					success : function(data, name) {
						if (name == 'success') {
							/* var ids = data.prescriptionIds;
							for(var i=0;i<ids.length;i++){
								alert("id:"+ids[i]);
							} */
							dataForm.submit();
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
					}
				});
			}
		});

		$('#add_form').bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			trigger : 'change',
			fields : {
				<s:iterator value="#medicines" id="medicine" status="st">
				'prescriptions[<s:property value='#st.index'/>].count' : {
					validators : {
						notEmpty : {
							message : '数量不能为空'
						},
						numeric : {
							message : '请输入数字'
						},
						between : {
							min : 1,
							max : 100,
							message : '范围在1到100之间'
						},
					}
				},
				'prescriptions[<s:property value='#st.index'/>].totalCourse' : {
					validators : {
						notEmpty : {
							message : '总疗程不能为空'
						},
						numeric : {
							message : '请输入数字'
						},
						between : {
							min : 1,
							max : 100,
							message : '范围在1到100之间'
						},
					}
				},
				'prescriptions[<s:property value='#st.index'/>].instructor' : {
					validators : {
						notEmpty : {
							message : '说明不能为空'
						},
						stringLength : {
							min : 0,
							max : 100,
							message : '超过输入限制'
						}
					}
				}<s:if test="!#st.last">,</s:if>
				</s:iterator> 
			}
		})
	</script>
</body>
</html>

