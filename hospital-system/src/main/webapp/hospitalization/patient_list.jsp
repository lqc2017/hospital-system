<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>住院表</title>
<%@ include file="../source.jsp"%>
<script src="/static/js/commons/paginator.js"></script>
</head>

<body>
	<input id="totalPages" type="hidden"
		value="<s:property value='#pageInfo.pages' />" />
	<input id="currentPn" type="hidden"
		value="<s:property value='#pageInfo.pageNum' />" />
	<div style="width: 85%; margin: 20px auto;">
		<form id="search_form" class="form-inline" method="GET"
			action="/hospitalization/patient_list">

			<div class="form-group">
				<label for="h_date">住院年月:</label>
				<div class="input-group date form_datetime col-md-8"
					data-date-format="yyyy-mm" data-link-field="h_date"
					data-link-format="yyyy-mm-dd">
					<input name="date" class="form-control" type="text"
						value="<s:date name='#so.createMonth' format='yyyy-MM'/>"
						readonly> <span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
				<input type="hidden" id="h_date"
					name="so.createMonth"
					value="<s:date name='#so.createMonth' format='yyyy-MM-dd'/>" />
			</div>
			<!-- 页码 -->
			<input name="pn" type="hidden"
				value="<s:property value='#pageInfo.pageNum' />" />
			<!-- 患者id -->
			<input name="patientId" type="hidden"
				value="<s:property value='patientId' />" />
		</form>
	</div>

	<div class="table-responsive" style="width: 85%; margin: 30px auto;">
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="15%">住院时间</th>
					<th width="40%">症状</th>
					<th width="15%">是否出院</th>
					<th width="15%">出院时间</th>
					<th width="15%">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="hospitalizations" id="hospitalization">
					<tr>
						<td><s:date name='#hospitalization.createTime'
								format='yyyy-MM-dd' /></td>
						<td title="<s:property value='#hospitalization.symptoms' />"><s:if
								test="#hospitalization.symptoms.length() > 18">
								<s:property value="#hospitalization.symptoms.substring(0,15)" />...</s:if>
							<s:else>
								<s:property value="#hospitalization.symptoms" />
							</s:else></td>
						<td><s:if test="#hospitalization.isLeave==0">未出院</s:if> <s:else>已出院</s:else>
						</td>
						<td><s:if test="#hospitalization.isLeave==0">无</s:if> <s:else>
								<s:date name='#hospitalization.leaveTime' format='yyyy-MM-dd' />
							</s:else></td>
						<td>
							<div class="btn-toolbar">
								<button type="button" id="detail_btn"
									class="btn btn-sm btn-info" hhId="<s:property value='#hospitalization.id'/>">详情</button>
							</div>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div id="paginator" style="width: 25%; margin: 10px auto;"></div>

	</div>

	<script defer type="text/javascript">
		$("button#detail_btn").bind('click', function() {
			var form = $("#search_form");
			var hhId = parseInt($(this).attr("hhId"));
			parent.location.href = "/hospitalization/detail?hospitalizationId=" + hhId;
		});

		$('.form_datetime').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 3,
			minView : 3,
			forceParse : 0,
		});

		$("input[name='date']").bind('change propertychange', function() {
			$("input[name='pn']").val("1");
			$("#search_form").submit();
		});
	</script>
</body>

</html>