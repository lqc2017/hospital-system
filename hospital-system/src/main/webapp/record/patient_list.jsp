<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>病例列表</title>
<%@ include file="../source.jsp"%>
<script src="/static/js/commons/paginator.js"></script>
</head>
<body>
	<input id="totalPages" type="hidden"
		value="<s:property value='#pageInfo.pages' />" />
	<input id="currentPn" type="hidden"
		value="<s:property value='#pageInfo.pageNum' />" />


	<div style="width: 85%; margin: 20px auto;">
		<form id="search_form" class="form-inline pull-top" method="GET"
			action="/record/patient_list">

			<div class="form-group">
				<label for="h_date">年月:</label>
				<div class="input-group date form_datetime col-md-8"
					data-date-format="yyyy-mm" data-link-field="h_date"
					data-link-format="yyyy-mm-dd">
					<input name="date" class="form-control" type="text"
						value="<s:date name='#so.createMonth' format='yyyy-MM'/>" readonly>
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
				<input type="hidden" id="h_date" name="so.createMonth"
					value="<s:date name='#so.createMonth' format='yyyy-MM-dd'/>" />
			</div>

			<!-- 页码 -->
			<input name="pn" type="hidden"
				value="<s:property value='#pageInfo.pageNum' />" />
			<!-- 患者id -->
			<input name="so.patientId" type="hidden"
				value="<s:property value='#so.patientId' />" />
		</form>
	</div>

	<div class="table-responsive" style="width: 85%; margin: 30px auto;">
		<table id="record_table" class="table table-hover">
			<thead>
				<tr>
					<th width="15%">日期</th>
					<th width="37.5%">症状</th>
					<th width="37.5%">注意事项</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="records" id="record">
					<tr>
						<td><s:date name='#record.createTime' format='yyyy-MM-dd' /></td>
						<td title="<s:property value='#record.symptoms' />"><s:if
								test="#record.symptoms.length() > 18">
								<s:property value="#record.symptoms.substring(0,15)" />...</s:if> <s:else>
								<s:property value="#record.symptoms" />
							</s:else></td>
						<td title="<s:property value='attentions' />"><s:if
								test="#record.attentions.length() > 18">
								<s:property value="#record.attentions.substring(0,15)" />...</s:if> <s:else>
								<s:property value="#record.attentions" />
							</s:else></td>
						<td><button type="button" id="detail_btn"
								class="btn btn-sm btn-info"
								hrId="<s:property value='#record.id'/>">详情</button></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div id="paginator" style="width: 25%; margin: 10px auto;"></div>
	</div>

	<script defer type="text/javascript">
		$('.form_datetime').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 3,
			minView : 3,
			forceParse : 0,
			showMeridian : 1
		});
		$("input[name='date']").bind('change propertychange', function() {
			$("input[name='pn']").val("1");
			$("#search_form").submit();
		});

		$("button#detail_btn").bind('click', function() {
			var record_id = parseInt($(this).attr("hrId"));
			parent.location.href = "/record/detail?recordId=" + record_id;
		});
	</script>

</body>
</html>