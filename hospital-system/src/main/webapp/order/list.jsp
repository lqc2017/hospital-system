<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单列表</title>
<%@ include file="/source.jsp"%>
<script src="/static/js/commons/paginator.js"></script>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf" content="<s:property value='#request._csrf.token'/>" />
<meta name="_csrf_header" content="<s:property value='#request._csrf.headerName'/>" />
</head>

<body>
	<input id="totalPages" type="hidden"
		value="<s:property value='#pageInfo.pages' />" />
	<input id="currentPn" type="hidden"
		value="<s:property value='#pageInfo.pageNum' />" />
	<%-- <div style="width: 95%; height: 40px; margin: 5px auto;">
		<span style="float: right; margin: 5px 30px">收银员：<s:property value="#cashier.name"/>,你好！<a
			href="/logout">登出</a></span>
	</div> --%>
	<div style="width: 95%; margin: 0 auto 15px auto;">
		<form id="search_form" class="form-inline" method="get" action="/order/list">
			<div class="form-group">
				<label for="so.payer">姓名:</label> <input type="text" name="so.payer"
					class="form-control" value="<s:property value="#so.payer" />">
			</div>

			<div class="form-group">
				<label for="so.status">状态:</label> <select name="so.status" id="status"
					class="form-control">
					<option value="">无</option>
					<option value="10" <s:if test="#so.status==10">selected</s:if>>待缴费</option>
					<option value="20" <s:if test="#so.status==20">selected</s:if>>待确认</option>
					<option value="30" <s:if test="#so.status==30">selected</s:if>>已确认</option>
				</select>
			</div>

			<div class="form-group">
				<label for="so.createTime">创建时间：</label>
				<div class="input-group">
					<input type="text" readonly class="form-control form_date"
						name="so.createTime"
						value="<s:date name='#so.createTime' format='yyyy-MM-dd' />">
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar"></span></span>
				</div>
			</div>

			<div class="form-group">
				<label for="so.payTime">支付时间：</label>
				<div class="input-group">
					<input type="text" readonly class="form-control form_date"
						name="so.payTime"
						value="<s:date name='#so.payTime' format='yyyy-MM-dd' />">
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

			<%-- <input name="cashierId" type="hidden" value="${cashier.id}"> --%>
			<!-- 页码 -->
			<input name="pn" type="hidden"
				value="<s:property value='#pageInfo.pageNum' />" />
		</form>
	</div>
	
	<div style="width: 95%; margin: 0 auto;">
		<ol class="breadcrumb">
			<li><a href="/cashier/home">返回主页</a></li>
		</ol>
	</div>

	<div class="table-responsive" style="width: 95%; margin: 30px auto;">
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="13%">姓名</th>
					<th width="12%">应缴金额</th>
					<th width="12%">优惠金额</th>
					<th width="12%">实缴金额</th>
					<th width="13%">创建时间</th>
					<th width="5%">状态</th>
					<th width="13%">支付时间</th>
					<th width="7%">支付方式</th>
					<th width="13%">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="orders" id="order">
					<tr>
						<td><s:property value="#order.payer" /></td>
						<td><s:property value="%{formatCurrency(#order.totalPrice)}" /></td>
						<td><s:property value="%{formatCurrency(#order.subsidy)}" /></td>
						<td><s:property value="%{formatCurrency(#order.paidPrice)}" /></td>
						<td><s:date name="#order.createTime"
								format="yyyy-MM-dd HH:mm:ss" /></td>
						<td><s:if test="#order.status==10">待缴费</s:if> <s:elseif
								test="#order.status==20">待确认</s:elseif> <s:elseif
								test="#order.status==30">已确认</s:elseif></td>
						<td><s:if test="#order.status==20||#order.status==30">
								<s:date name="#order.payTime" format="yyyy-MM-dd HH:mm:ss" />
							</s:if> <s:else>无</s:else></td>
						<td><s:if test="#order.payWay==10">现金</s:if> <s:elseif
								test="#order.payWay==20">银行卡</s:elseif> <s:elseif
								test="#order.payWay==30">支付宝</s:elseif></td>

						<td><div class="btn-toolbar">
								<button type="button" name="detail_btn"
									class="btn btn-sm btn-info"
									hoId="<s:property value='#order.id' />">价目表</button>
								<s:if test="#order.status==20">
									<button type="button" name="confirm_btn"
										class="btn btn-sm btn-primary"
										hoId="<s:property value='#order.id' />">确认</button>
								</s:if>
								<s:elseif test="#order.status==30">
									<%-- <button type="button" id="cancel_btn"
										class="btn btn-sm btn-primary"
										hoId="<s:property value='#order.id' />">取消确认</button> --%>
								</s:elseif>
							</div></td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div id="paginator" style="width: 23%; margin: 10px auto;"></div>

	</div>
	<div id="confirm_dialog"></div>

	<script defer type="text/javascript">
	var form;
	var hoId;
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
		xhr.setRequestHeader(header, token);
	});
	
	$("#confirm_dialog").dialog({autoOpen : false,width : 250,height : 150,modal : true,title : "确认",
		open : function() {$(this).append("是否确认订单已缴费？");},
		close : function() {$(this).empty();},
		buttons : [{text : "确认",click : function() {
				
				$.ajax({	
					async : false,
					type : "POST",
					url : "/order/confirm",
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
						form.attr("action", "/order/list");
						form.find("input[name='pn']").val('1');
						form.find("input[name='orderId']").remove();
						form.submit();
				},1500);
				}
				},
				{text : "取消",click : function() {$(this).dialog("close");}}]
	});
	
		$("[name='detail_btn']").bind('click', function() {
			var hoId = parseInt($(this).attr("hoId"));
			location.href = "/order/price_list?orderId=" + hoId;
		});

		/* $("button#confirm_btn").bind('click',function() {
							$(this).attr("disabled",true);
							var hoId = parseInt($(this).attr("hoId"));
							var form = $("#search_form");
							form.attr("action", "/order/confirm_order");
							form.append("<input name='orderId' type='hidden' value='"+hoId+"'>");
							form.submit();
						}); */
		
		$("[name='confirm_btn']").bind('click', function() {
			$(this).attr("disabled",true);
			hoId = parseInt($(this).attr("hoId"));
			form = $("#search_form");
			form.append("<input name='orderId' type='hidden' value='"+hoId+"'>");
			
			$("#confirm_dialog").dialog("open");
		});

		/* $("button#cancel_btn").bind('click',function() {
							$(this).attr("disabled",true);
							var hoId = parseInt($(this).attr("hoId"));
							var form = $("#search_form");
							form.attr("action", "/order/cancel_order");
							form.append("<input name='orderId' type='hidden' value='"+hoId+"'>");
							form.submit();
						});
 */
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