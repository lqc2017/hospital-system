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
	<div>
		<form id="search_form" class="form-inline" method="get"
			action="/hospitalization/doctor_list">
			<input name="pn" type="hidden" value="<s:property value='#pageInfo.pageNum' />" />
		</form>
	</div>

	<div class="table-responsive" style="width: 95%; margin: 10px auto;">
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="25%">住院时间</th>
					<th width="25%">姓名</th>
					<th width="25%">症状</th>
					<th width="25%">操作</th>
				</tr>
			</thead>
			<tbody>
			<s:iterator value="hospitalizations" id="hospitalization" status="st">
					<tr>
						<td><s:date name="#hospitalization.createTime" format="HH:mm:ss"/></td>
						<td><s:property value="#hospitalization.patientName"/></td>
						<td title="<s:property value='#hospitalization.symptoms' />"><s:if
								test="#hospitalization.symptoms.length() > 23">
								<s:property value="#hospitalization.symptoms.substring(0,20)" />...</s:if> <s:else>
								<s:property value="#hospitalization.symptoms" />
							</s:else></td>
						<td>
							<div class="btn-toolbar">
								<button type="button" name="detail_btn"
									class="btn btn-sm btn-info" hhId="<s:property value='#hospitalization.id'/>">详情</button>
							</div>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		
		<div id="paginator" style="width: 25%; margin: 0 auto;"></div>

	</div>
	
	<div id="confirm_dialog"></div>

	<script defer type="text/javascript">
		$("[name='detail_btn']").bind('click', function() {
			hhId = parseInt($(this).attr("hhId"));
			location.href = "/hospitalization/detail?hospitalizationId=" + hhId;
		});

	</script>
</body>

</html>