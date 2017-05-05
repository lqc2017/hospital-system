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
	<div style="width: 95%; margin: 30px auto;">
		<form id="search_form" class="form-inline" method="get"
			action="/record/list">
			<input name="pn" type="hidden"
				value="<s:property value='#pageInfo.pageNum' />" />
				
			<div class="form-group">
				<label for="so.patientName">姓名:</label> <input type="text"
					name="so.patientName" class="form-control"
					value="<s:property value='#so.patientName' />">
			</div>

			<div class="form-group">
				<label for="so.createTime">就诊时间：</label>
				<div class="input-group">
					<input type="text" readonly class="form-control form_date"
						name="so.createTime"
						value="<s:date name='#so.createTime' format='yyyy-MM-dd' />">
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
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
					<th width="15%">姓名</th>
					<th width="8%">性别</th>
					<th width="8%">年龄</th>
					<th width="13%">就诊时间</th>
					<th width="23%">症状</th>
					<th width="23%">注意事项</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="records" id="record" status="st">
					<tr>
						<td><s:property value="#record.patientName" /></td>
						<td><s:if test="#patients[#st.index].sex==0">男</s:if> <s:else>女</s:else></td>
						<td><s:property value="#patients[#st.index].age" /></td>
						<td><s:date name='#record.createTime' format='yyyy-MM-dd' /></td>
						<td title="<s:property value='#record.symptoms' />"><s:if
								test="#record.symptoms.length() > 23">
								<s:property value="#record.symptoms.substring(0,20)" />...</s:if> <s:else>
								<s:property value="#record.symptoms" />
							</s:else></td>
						<td title="<s:property value='attentions' />"><s:if
								test="#record.attentions.length() > 23">
								<s:property value="#record.attentions.substring(0,20)" />...</s:if> <s:else>
								<s:property value="#record.attentions" />
							</s:else>
						<td>
							<button type="button" id="detail_btn"
								class="btn btn-sm btn-info"
								hrId="<s:property value='#record.id'/>">详情</button>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div id="paginator" style="width: 25%; margin: 10px auto;"></div>

	</div>
	<hr />

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
		$("button#detail_btn").bind('click', function() {
			$(this).attr("_id");
			hrId = parseInt($(this).attr("hrId"));
			location.href = "/record/detail?recordId=" + hrId;
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