<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>填写病例</title>
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
		<form id="record_form" class="form-horizontal" method="post">
			<input type="hidden" name="record.patientId" value="<s:property value='#patient.id' />" />
			<input type="hidden" name="record.doctorId" value="<s:property value='record.doctorId' />" />
			<input type="hidden" name="record.patientName" value="<s:property value='#patient.name' />" />

			<input type="hidden" name="<s:property value='#request._csrf.parameterName'/>"
				value="<s:property value='#request._csrf.token'/>" />
			<div class="form-group">
				<div style="width: 10%; margin: 10px 45%;">
					<h1>病例</h1>
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
					<textarea class="form-control" rows="5" name="record.symptoms"><s:property value="record.symptoms" /></textarea>
			</div>

			<div class="form-group" style="width: 80%; margin: 10px auto;">
					<label for=attentions class="col-lg-2 control-label">注意事项：</label>
					<textarea class="form-control" rows="4" name="record.attentions"><s:property
								value="record.attentions" /></textarea>
			</div>

			<div class="form-group">
				<div style="width: 80%; margin: 10px auto;">
					<div style="margin:3px 0px" class="btn-toolbar">
						<button type="button" id="hps_add" class="btn btn-default"
							style="float: right">开处方</button>
					</div>
					<!-- 处方表 -->
					<table id="hpss_table" class="table table-bordered">
						<tr>
							<th width="29%">药品名称</th>
							<th width="14%">开药数量</th>
							<th width="12%">总疗程</th>
							<th width="30%">服用指导</th>
							<th width="15%">操作</th>
						</tr>
						<s:iterator value="#prescriptions" id="prescription" status="st">
							<tr>
								<td><s:property value="#medicines[#st.index].name" /></td>
								<td><s:property value="#prescription.count" /> <s:if
										test="#medicine[#st.index].unit==10">盒</s:if> <s:elseif
										test="#medicine[#st.index].unit==20">瓶</s:elseif> <s:elseif
										test="#medicine[#st.index].unit==30">包</s:elseif></td>
								<td><s:property value="#prescription.count" /></td>
								<td title="<s:property value='#prescription.instructor'/>">
								<s:if test="#prescription.instructor.length() > 10"><s:property value="#prescription.instructor.substring(0,10)" />...</s:if>
									<s:else>
										<s:property value="#prescription.instructor" />
									</s:else></td>
								<td>
								<div class="operation">
										<span  name="hps_edit" hpsId="<s:property value='#prescription.id' />" class="glyphicon glyphicon-edit"></span>
										<span  name="hps_delete" hpsId="<s:property value='#prescription.id' />" class="glyphicon glyphicon-trash"></span>
								</div>
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
			</div>

			<div class="form-group">
				<div style="width: 80%; margin: 10px auto;">
					<div style="margin:3px 0px" class="btn-toolbar">
						<button type="button" id="hpc_add" class="btn btn-default"
							style="float: right">添加检查</button>

					</div>
					<!-- 项目表 -->
					<table id="hpcs_table" class="table table-bordered">
						<tr>
							<th width="29%">项目名称</th>
							<th width="13%">执行次数</th>
							<th width="15%">执行时间</th>
							<th width="28%">描述</th>
							<th width="15%">操作</th>
						</tr>
						<s:iterator value="#projectChecks" id="projectCheck" status="st">
							<tr>
								<td><s:property value="#projects[#st.index].name" /></td>
								<td><s:property value="#projectCheck.totalCount" /></td>
								<td><s:date name="#projectCheck.executeTime" format="yyyy-MM-dd"/></td>
								<td title="<s:property value='#projectCheck.describe'/>">
									<s:if test="#projectCheck.describe.length() > 10"><s:property value="#projectCheck.describe.substring(0,9)" />...</s:if>
									<s:else><s:property value="#projectCheck.describe" /></s:else>
								</td>
								<td>
									<div class="operation">
										<span  name="hpc_edit" hpcId="<s:property value='#projectCheck.id' />" class="glyphicon glyphicon-edit"></span>
										<span  name="hpc_delete" hpcId="<s:property value='#projectCheck.id' />" class="glyphicon glyphicon-trash"></span>
									</div>
								</td>
							</tr>
						</s:iterator>
					</table>
				</div>
			</div>

			<div class="form-group">
				<div class="btn-toolbar" style="width: 18%; margin: 10px auto;">
					<button type="button" id="record_add" class="btn btn-success">生成病例</button>
				</div>
			</div>
		</form>
		<div id="update_dialog"></div>
		<div id="delete_dialog"></div>
	</div>

	<script type="text/javascript">
		var setWidth = 400;
		var setHeight = 600;
		var hpsId;
		var hpcId;
		var flag; //flag为0时对处方进行操作，为1时对检查进行操作
		var validator;
		var form = $("#record_form");

		//处方按钮
		$("[name='hps_edit']").bind('click', function() {
			flag = 0;
			hpsId = parseInt($(this).attr("hpsId"));
			$("#update_dialog").dialog("open");
		});
		$("[name='hps_delete']").bind('click', function() {
			flag = 0;
			hpsId = parseInt($(this).attr("hpsId"));
			$("#delete_dialog").dialog("open");
		});
		$("#hps_add").on("click", function() {
			var bootstrapValidator = form.data('bootstrapValidator');
			form.attr("action", "/medicine/select");
			bootstrapValidator.defaultSubmit();
		});

		//检查按钮
		$("[name='hpc_edit']").bind('click', function() {
			flag = 1;
			hpcId = parseInt($(this).attr("hpcId"));
			$("#update_dialog").dialog("open");
		});
		$("[name='hpc_delete']").bind('click', function() {
			flag = 1;
			hpcId = parseInt($(this).attr("hpcId"));
			$("#delete_dialog").dialog("open");
		});
		$("#hpc_add").bind('click', function() {
			var bootstrapValidator = form.data('bootstrapValidator');
			form.attr("action", "/project/select");
			bootstrapValidator.defaultSubmit();
		});

		//添病例
		$("#record_add").bind('click', function() {
			var bootstrapValidator = form.data('bootstrapValidator');
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				$.ajax({
					async : false,
					cache : true,
					type : "POST",
					url : "/record/add",
					data : form.serialize(),
					datatype : 'json',
					success : function(data) {
						alert("生成的recordId:"+data.recordId);
						if (data.result == 'success') {
							$.ajax({
								async : false,
								cache : true,
								type : "GET",
								url : "/order/add_hr_order?recordId="+data.recordId,
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
				parent.$("[name='recordEdit']",parent.document).text("病例已填写");
				parent.$("[name='recordEdit']",parent.document).attr("disabled",true);
				$.ajax({
					async : false,
					cache : true,
					type : "GET",
					url : "/doctor/update_peek?type=0",
					datatype : 'json',
					success : function(data) {
					},
					error : function(jqXHR, textStatus, errorThrown) {}
				});
				parent.$("#record_dialog",parent.document).dialog("close");
			}
		});
		
		

		$().ready(function() {
			$("#update_dialog").dialog({autoOpen : false,
										modal : true,
										width : setWidth,
										height : setHeight,
										title : "修改",
										open : function() {
										$(this).append("<iframe></iframe>");
										var frame = $(this).children("iframe");
										var opt = {scrolling : "auto",width : "100%",height : "100%",frameborder : "0",
													src : flag == 0 ? "/prescription/edit?prescriptionId="+ hpsId: "/project_check/edit?projectCheckId="+ hpcId
													};
													//alert(opt.src);
										frame.attr(opt);
										},
										close : function() {
												$(this).children().remove();
												},
										buttons : [{
												text : "确认修改",
												click : function() {
												var frame = $(this).children("iframe");
												var form = frame.contents().find('#edit_form');
												validator.validate();
												if (!validator.isValid()) {
													return;
												}
												$.ajax({
													async : false,
													cache : true,
													type : "POST",
													url : flag == 0 ? "/prescription/update": "/project_check/update",
													data : form.serialize(),
													datatype : 'json',
													success : function(datas,name) {
														if (name == "success") {
															toastr.success("修改成功！");
															if (flag == 0) {
																var data = datas.prescription;
																var tr = $("[hpsId='"+ hpsId+ "']:eq(0)").parents("tr");
																var unit = tr.children("td:eq(1)").text().replace(/[0-9]*/g,'');
																tr.children("td:eq(1)").text(data.count.toString()+ unit);
																tr.children("td:eq(2)").text(data.totalCourse);
																var td = tr.children("td:eq(3)");
																td.attr("title",data.instructor);
																if (getLength(data.instructor)>22) {
																	td.text(cutStr(data.instructor,19));
																} else td.text(data.instructor);
															} else {
																var data = datas.projectCheck;
																var tr = $("[hpcId='"+ hpcId+ "']:eq(0)").parents("tr");
																tr.children("td:eq(1)").text(data.totalCount);
																tr.children("td:eq(2)").text(data.executeTime.substring(0,10));
																var td = tr.children("td:eq(3)");
																if (data.describe != null) {
																	td.attr("title",data.describe);
																	if (getLength(data.describe)>18) {
																		td.text(cutStr(data.describe,15));
																	}else td.text(data.describe);
																	}}} else
																					toastr.error("修改失败！");
													},
													error : function(jqXHR,textStatus,errorThrown) {/*alert(textStatus);*/}
												});
												$("#update_dialog").dialog("close");
											}},
											{text : "取消",
											click : function() {$(this).dialog("close");
															}
														} ]
											});

							$("#delete_dialog").dialog({autoOpen : false,width : 200,height : 150,modal : true,title : "删除",
												open : function() {$(this).append("确认要删除这条记录吗？");},
												close : function() {$(this).empty();},
												buttons : [{text : "确认",
															click : function() {
																$.ajax({	async : false,
																			type : "GET",
																			url : flag == 0 ? "/prescription/delete?prescriptionId="+ hpsId:"/project_check/delete?projectCheckId="+ hpcId,
																			datatype : 'json',
																			success : function(response) {
																				if (response.result == "success") {
																					if (flag == 0) {
																						var tr = $("[hpsId='"+ hpsId+ "']:eq(1)").parents("tr");
																						tr.remove();
																					} else {
																						var tr = $("[hpcId='"+ hpcId+ "']:eq(1)").parents("tr");
																						tr.remove();
																					}
																					toastr.success("删除成功！");
																				}
																				if (response.result == "fail")
																					toastr.error("删除失败");
																			},
																			error : function(
																					jqXHR,
																					textStatus,
																					errorThrown) {
																				alert(textStatus);
																			}
																		});
																$(
																		"#delete_dialog")
																		.dialog(
																				"close");
															}
														},
														{
															text : "取消",
															click : function() {
																$(this)
																		.dialog(
																				"close");
															}
														} ]
											});

							$('#record_form').bootstrapValidator({
								message : 'This value is not valid',
								feedbackIcons : {
									valid : 'glyphicon glyphicon-ok',
									invalid : 'glyphicon glyphicon-remove',
									validating : 'glyphicon glyphicon-refresh'
								},
								trigger : 'change',
								fields : {
									'record.symptoms' : {
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
									'record.attentions' : {
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

