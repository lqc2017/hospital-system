<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>病例表</title>
<%@ include file="../source.jsp"%>
<script src="/static/js/commons/paginator.js"></script>
</head>

<body>
	<input id="totalPages" type="hidden"
		value="<s:property value='#pageInfo.pages' />" />
	<input id="currentPn" type="hidden"
		value="<s:property value='#pageInfo.pageNum' />" />
	<div>
		<form id="search_form" class="form-inline" method="get"
			action="/record/doctor_list">
			<input name="pn" type="hidden"
				value="<s:property value='#pageInfo.pageNum' />" />
				
		</form>
	</div>

	<div class="table-responsive" style="width: 95%; margin: 10px auto;">
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="25%">就诊时间</th>
					<th width="25%">姓名</th>
					<th width="25%">症状</th>
					<th width="25%">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="records" id="record" status="st">
					<tr>
						<td><s:date name='#record.createTime' format='HH:mm:ss' /></td>
						<td><s:property value="#record.patientName" /></td>
						<td title="<s:property value='#record.symptoms' />"><s:if
								test="#record.symptoms.length() > 23">
								<s:property value="#record.symptoms.substring(0,20)" />...</s:if> <s:else>
								<s:property value="#record.symptoms" />
							</s:else></td>
						<td>
							<button type="button" name="detail_btn"
								class="btn btn-sm btn-info"
								hrId="<s:property value='#record.id'/>">查看</button>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div id="paginator" style="width: 25%; margin: 0 auto;"></div>

	</div>

	<!-- <h3>Session Scope (key==values)</h3> -->
	<%-- <%
  java.util.Enumeration<String> sessEnum = request.getSession()
	.getAttributeNames();
  while (sessEnum.hasMoreElements()) {
	String s = sessEnum.nextElement();
	out.print(s);
	out.println("==" + request.getSession().getAttribute(s));
%><br />
<%
  }
%> --%>
	<script defer type="text/javascript">
		$("[name='detail_btn']").bind('click', function() {
			$(this).attr("_id");
			hrId = parseInt($(this).attr("hrId"));
			location.href = "/record/detail?recordId=" + hrId;
		});
	</script>
</body>

</html>