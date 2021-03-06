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
	<div style="width: 85%; margin: 5px auto;">
		<form id="search_form" method="GET" action="/order/patient_list">
			<div class="checkbox">
				<label> <input type="checkbox" id="status"
					<s:if test="#so.status==20">checked</s:if>>待确认
					<input type="hidden" name="so.status" value="<s:if test='#so.status==20'>20</s:if>">
				</label>
			</div>
			<!-- 页码 -->
			<input name="pn" type="hidden"
				value="<s:property value='#pageInfo.pageNum' />" />
			<%-- <!-- 患者id -->
			<input name="patientId" type="hidden"
				value="<s:property value='patientId' />" /> --%>
		</form>
	</div>

	<div class="table-responsive" style="width: 85%; margin: 10px auto;">
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="18%">编号</th>
					<th width="18%">创建时间</th>
					<th width="18%">状态</th>
					<th width="18%">总价</th>
					<th width="18%">支付时间</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="orders" id="order">
					<tr>
						<td><s:property value="#order.id" /></td>
						<td><s:date name="#order.createTime"
								format="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
								<s:if test="#order.status==10">待缴费</s:if>
								<s:elseif test="#order.status==20">待确认</s:elseif>
								<s:else>支付成功<span style="color:green" class="glyphicon glyphicon-ok"></span></s:else>
							</td>
						<td><s:property value="%{formatCurrency(#order.totalPrice)}" /></td>
						<td><s:date name="#order.payTime" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><button type="button" id="detail_btn"
								class="btn btn-sm btn-info" hoId="<s:property value='#order.id' />">详情</button></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div id="paginator" style="width: 25%; margin: 10px auto;"></div>

	</div>

	<script defer type="text/javascript">
		$("button#detail_btn").bind('click', function() {
			var orderId = parseInt($(this).attr("hoId"));
			parent.location.href = "/order/price_list?orderId=" + orderId;
		});
		
		$("#status").bind('click', function() {
			var form = $("#search_form");
			if($(this).is(':checked')){
				$("input[name='so.status']").val("20");
			}
			else 
				$("input[name='so.status']").val("");
			$("input[name='pn']").val("1");
			$("#search_form").submit();
		})
	</script>
</body>

</html>