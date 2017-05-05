<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>未支付订单</title>
<%@ include file="../source.jsp"%>
<script src="/static/js/commons/paginator.js"></script>
</head>

<body>
	<input id="totalPages" type="hidden" value="<s:property value='#pageInfo.pages' />" />
	<input id="currentPn" type="hidden" value="<s:property value='#pageInfo.pageNum' />" />
	<div style="width: 85%; margin: 20px auto;">
		<form id="search_form" class="form-inline" method="GET"
			action="/order/patient_list">

			<input name="so.status" type="hidden" value="10" />
			<!-- 页码 -->
			<input name="pn" type="hidden" value="<s:property value='#pageInfo.pageNum' />" />
		</form>
	</div>

	<div class="table-responsive" style="width: 85%; margin: 30px auto;">
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="22%">编号</th>
					<th width="22%">创建时间</th>
					<th width="22%">状态</th>
					<th width="22%">总价</th>
					<th width="12%">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="orders" id="order">
					<tr>
						<td><s:property value="#order.id" /></td>
						<td><s:date name="#order.createTime"
								format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><s:if test="#order.status==10">待缴费</s:if>
								<s:elseif test="#order.status==20">待确认</s:elseif>
								<s:else>已确认</s:else></td>
						<td><s:property value="%{formatCurrency(#order.totalPrice)}" /></td>
						<td><div class="btn-toolbar">
								<button type="button" name="pay_btn"
									class="btn btn-sm btn-default" hoId="<s:property value='#order.id' />">缴费</button>
								<button type="button" name="detail_btn"
									class="btn btn-sm btn-info" hoId="<s:property value='#order.id' />">详情</button></div></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div id="paginator" style="width: 25%; margin: 10px auto;"></div>

		<form id="pay_form" method="post" action="/order/pay_order">
			<input type="hidden"
				name="<s:property value="#request._csrf.parameterName"/>"
				value="<s:property value="#request._csrf.token"/>" /> <input
				type="hidden" name="patientId"
				value="<s:property value='#patientId'/>"> <input
				type="hidden" name="orderId" value="">
		</form>
	</div>

	<script defer type="text/javascript">
		$("[name='pay_btn']").bind('click', function() {
			var form = $('#pay_form');
			var orderId = $(this).attr("hoId");
			form.find("input[name='orderId']").val(orderId);
			form.submit();
		});

		$("[name='detail_btn']").bind('click', function() {
			var orderId = parseInt($(this).attr("hoId"));
			parent.location.href = "/order/price_list?orderId=" + orderId;
		});
	</script>
</body>

</html>