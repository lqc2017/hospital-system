<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>住院表</title>
<%@ include file="../source.jsp"%>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf" content="<s:property value='#request._csrf.token'/>" />
<meta name="_csrf_header" content="<s:property value='#request._csrf.headerName'/>" />
<script src="/static/js/commons/paginator.js"></script>
</head>

<body>
	<input id="totalPages" type="hidden" value="<s:property value='#pageInfo.pages' />" />
	<input id="currentPn" type="hidden" value="<s:property value='#pageInfo.pageNum' />" />
	<div style="width: 95%; margin: 30px auto;">
		<form id="search_form" class="form-inline" method="get"
			action="/hospitalization/list">
			<input name="pn" type="hidden" value="<s:property value='#pageInfo.pageNum' />" />

			<div class="form-group">
				<label for="so.patientName">姓名:</label> <input size="7" type="text"
					name="so.patientName" class="form-control" value="<s:property value='#so.patientName' />">
			</div>

			<div class="form-group">
				<label for="so.isLeave">是否出院:</label> <select name="so.isLeave"
					id="isLeave" class="form-control">
					<option value="" <s:if test="#so.isLeave==null">selected</s:if>>无</option>
					<option value="1" <s:if test="#so.isLeave==1">selected</s:if>>已出院</option>
					<option value="0" <s:if test="#so.isLeave==0">selected</s:if>>未出院</option>
				</select>
			</div>

			<div class="form-group">
				<label for="so.createTime">入院时间：</label>
				<div class="input-group">
					<input size="7" type="text" readonly class="form-control form_date"
						name="so.createTime"
						value="<s:date name='#so.createTime' format='yyyy-MM-dd'/>">
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>

			<div class="form-group">
				<label for="so.leaveTime">出院时间：</label>
				<div class="input-group">
					<input size="7" type="text" readonly class="form-control form_date"
						name="so.leaveTime"
						value="<s:date name='#so.createTime' format='yyyy-MM-dd'/>">
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>

			<div class="form-group">
				<label class="radio inline"> <input type="radio"
					name="so.order" value="0" <s:if test="#so.order==0||#so.order==null">checked</s:if>>
					按入院时间排序
				</label> <label class="radio inline"> <input type="radio"
					name="so.order" value="1" <s:if test="#so.order==1">checked</s:if>>
					按出院时间排序
				</label>
			</div>

			<div class="form-group">
				<div class="btn-toolbar">
					<a href="javascript:;" class="btn btn-default btn-search"><span
						class="glyphicon glyphicon-search"></span>查询</a> 
						<a href="javascript:;" class="btn btn-default btn-reset">重置</a>
				</div>
			</div>
		</form>
	</div>

	<div style="width: 95%; margin: 0 auto;">
		<ol class="breadcrumb">
			<li><a href="/doctor/home">返回主页</a></li>
		</ol>
	</div>
	<div class="table-responsive" style="width: 95%; margin: 10px auto;">
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="14%">姓名</th>
					<th width="5%">性别</th>
					<th width="8%">年龄</th>
					<th width="10%">住院时间</th>
					<th width="26%">症状</th>
					<th width="12%">是否出院</th>
					<th width="10%">出院时间</th>
					<th width="15%">操作</th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="hospitalizations" id="hospitalization" status="st">
					<tr>
						<td><s:property value="#hospitalization.patientName"/></td>
						<td><s:if test="#patients[#st.index].sex==0">男</s:if> <s:else>女</s:else></td>
						<td><s:property value="#patients[#st.index].age" /></td>
						<td><s:date name="#hospitalization.createTime" format="yyyy-MM-dd"/></td>
						<td title="<s:property value='#hospitalization.symptoms' />"><s:if
								test="#hospitalization.symptoms.length() > 23">
								<s:property value="#hospitalization.symptoms.substring(0,20)" />...</s:if> <s:else>
								<s:property value="#hospitalization.symptoms" />
							</s:else></td>
						<td><s:if test="#hospitalization.isLeave==0">未出院</s:if><s:else>已出院</s:else></td>
						<td><s:if test="#hospitalization.isLeave==0">
							无
							</s:if><s:else><s:date name="#hospitalization.leaveTime" format="yyyy-MM-dd"/></s:else></td>
						<td>
							<div class="btn-toolbar">
								<button type="button" name="detail_btn"
									class="btn btn-sm btn-info" hhId="<s:property value='#hospitalization.id'/>">详情</button>
								<s:if test="#hospitalization.isLeave==0">
									<button type="button" name="confirm_btn"
										class="btn btn-sm btn-primary" hhId="<s:property value='#hospitalization.id'/>">确认出院</button>
								</s:if>
							</div>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		
		<div id="paginator" style="width: 25%; margin: 10px auto;"></div>

	</div>
	
	<div id="confirm_dialog"></div>

	<script defer type="text/javascript">
		var hhId;
		var patientName;
		var form;
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});
		
		$("#confirm_dialog").dialog({autoOpen : false,width : 250,height : 150,modal : true,title : "确认",
			open : function() {$(this).append("是否确认患者"+patientName+"已出院？");},
			close : function() {$(this).empty();},
			buttons : [{text : "确认",click : function() {
					
					$.ajax({	
						async : false,
						type : "POST",
						url : "/hospitalization/confirm",
						data : form.serialize(),
						datatype : 'json',
						success : function(response) {
							if (response.result == "success") {
								toastr.success("确认成功！");
							}
							if (response.result == "fail")
								toastr.error("确认失败");
							},
						error : function(jqXHR,textStatus,errorThrown) {
									alert(textStatus);
								}
					});
					$(this).dialog("close");
					setTimeout(function(){
							form.attr("action", "/hospitalization/list");
							form.find("input[name='pn']").val('1');
							form.find("input[name='hospitalizationId']").remove();
							form.submit();
					},1500);
					}
					},
					{text : "取消",click : function() {$(this).dialog("close");}}]
		});
	
		$("[name='confirm_btn']").bind('click', function() {
			
			$(this).attr("disabled",true);
			form = $("#search_form");
			hhId = parseInt($(this).attr("hhId"));
			form.append("<input name='hospitalizationId' type='hidden' value='"+hhId+"' />");
			form.attr("action", "/hospitalization/confirm");
			
			patientName = $(this).parents("tr").find("td:first").text();
			
			$("#confirm_dialog").dialog("open");
		});

		$("[name='detail_btn']").bind('click', function() {
			hhId = parseInt($(this).attr("hhId"));
			location.href = "/hospitalization/detail?hospitalizationId=" + hhId;
		});

		$('.form_date').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			format : 'yyyy-mm-dd'
		});
	</script>
</body>

</html>