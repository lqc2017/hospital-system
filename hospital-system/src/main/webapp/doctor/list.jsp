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


	<div style="width: 95%; margin: 0 auto 15px auto;">
		<form id="search_form" class="form-inline pull-top" method="GET"
			action="/doctor/list">

			<div class="form-group">
				<label for="name">姓名:</label> <input type="text" name="so.name"
					id="name" class="form-control"
					value="<s:property value='so.name'/>">
			</div>
			
			<div class="form-group">
				<label for="type">科室:</label> <select name="so.department" id="department"
					class="form-control">
					<option value="" <s:if test="#so.department==null">selected</s:if>>所有</option>
					<option value="10" <s:if test="#so.department==10">selected</s:if>>儿科</option>
					<option value="20" <s:if test="#so.department==20">selected</s:if>>外科</option>
					<option value="30" <s:if test="#so.department==30">selected</s:if>>骨科</option>
				</select>
			</div>
			
			<div class="form-group">
				<label for="type">排序:</label> <select name="so.order" id="order"
					class="form-control">
					<option value="0" <s:if test="#so.order==null||#so.order==0">selected</s:if>>姓名</option>
					<option value="1" <s:if test="#so.order==1">selected</s:if>>工龄</option>
					<option value="2" <s:if test="#so.order==2">selected</s:if>>称赞数</option>
				</select>
			</div>
			
			<div class="form-group">
				<div class="btn-toolbar">
					<a href="javascript:;" class="btn btn-default btn-search"><span
						class="glyphicon glyphicon-search"></span>查询</a> 
						<a href="javascript:;" class="btn btn-default btn-reset">重置</a>
				</div>
			</div>

			<!-- 页码 -->
			<input name="pn" type="hidden"
				value="<s:property value='#pageInfo.pageNum' />" />
		</form>
	</div>

	<div class="table-responsive" style="width: 95%; margin: 30px auto;">
		<table id="record_table" class="table table-hover">
			<thead>
				<tr>
					<th width="">姓名</th>
					<th width="">年龄</th>
					<th width="">性别</th>
					<th width="">工龄</th>
					<th width="">科室</th>
					<th width="">称赞数</th>
					<th width="">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="doctors" id="doctor">
					<tr>
						<td><s:property value="#doctor.name"/></td>
						<td><s:property value="#doctor.age"/></td>
						<td><s:if test="#doctor.sex==0">男</s:if><s:else>女</s:else></td>
						<td><s:property value="#doctor.seniority"/></td>
						<td>
							<s:if test="#doctor.department==10">儿科</s:if>
							<s:elseif test="#doctor.department==20">外科</s:elseif>
							<s:elseif test="#doctor.department==30">骨科</s:elseif>
						</td>
						<td><s:property value="#doctor.praise"/></td>
						<td></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div id="paginator" style="width: 25%; margin: 10px auto;"></div>
	</div>

	<script defer type="text/javascript">
		
	</script>

</body>
</html>