<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>价目表</title>
<%@ include file="../source.jsp"%>
</head>

<body>
	<div style="width: 60%; margin: 30px auto;">
		<form class="form-horizontal">
			<s:if test="#ho.type==0">
				<s:if test="#hmscnt > 0">
					<div class="form-group">
						<label class="col-lg-2 control-label">处方：</label>
						<div style="width: 60%; margin: 10px auto;">
							<table id="medicine_table" class="table table-bordered">
								<thead>
									<tr>
										<th>药品名称</th>
										<th>开药数量</th>
										<th>单位</th>
										<th>单价</th>
										<th>总价</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="hms" id="medicine" status="st">
										<tr>
											<td><s:property value="#medicine.name" /></td>
											<td><s:property value="#hpss[#st.index].count" /></td>
											<td><s:property value="#medicine.unit" /></td>
											<td><s:property
													value="%{formatCurrency(#medicine.price)}" /></td>
											<td><s:property
													value="%{formatCurrency(#medicine.price*#hpss[#st.index].count)}" /></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</s:if>

				<s:if test="#hpscnt>0">
					<div class="form-group">
						<div style="width: 60%; margin: 10px auto;">
							<table id="project_table" class="table table-bordered">
								<thead>
									<tr>
										<th>项目名称</th>
										<th>总次数</th>
										<th>单价</th>
										<th>总价</th>
									</tr>
								</thead>
								<tbody>
									<s:iterator value="hps" id="project" status="st">
										<tr>
											<td><s:property value="#project.name" /></td>
											<td><s:property value="#hpcs[#st.index].totalCount" /></td>
											<td><s:property
													value="%{formatCurrency(#project.price)}" /></td>
											<td><s:property
													value="%{formatCurrency(#project.price*#hpcs[#st.index].totalCount)}" /></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</s:if>
			</s:if>


			<s:if test="#ho.type==1">
				<div style="width: 60%; margin: 10px auto;">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>入院时间</th>
								<th>住院费用</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><s:date name="#time" format="yyyy-MM-dd HH:mm:ss" /></td>
								<td><s:property value="%{formatCurrency(#ho.totalPrice)}" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</s:if>

			<div class="form-group">
				<div style="width: 60%; margin: 10px auto;">
					<label style="float: right">总金额 ：<s:property value="%{formatCurrency(#ho.totalPrice)}" /></label>
				</div>
			</div>
		</form>

	</div>

	<div style="width: 60%; margin: 30px auto;">
		<div style="width: 60%; margin: 10px auto;">
			<form id="pay_form" class="form-inline" style="float: right;">
				<input type="hidden" name="<s:property value="#request._csrf.parameterName"/>"  value="<s:property value="#request._csrf.token"/>" />
				 <input name="orderId" type="hidden"
					value="<s:property value='orderId'/>" /> <input name="patientId" type="hidden"
					value="<s:property value='#patientId'/>" />

				<div class="form-group">
					<label for="payWay">支付方式:</label> <select name="payWay"
						class="form-control">
						<option value="10" selected>现金</option>
						<option value="20">银行卡</option>
						<option value="30">支付宝</option>
					</select>
				</div>

				<div class="form-group">
					<div class="btn-toolbar">
						<button type="button" id="pay_btn" class="btn btn-primary">付款</button>
						<button type="button" onclick="javascript:history.go(-1)" class="btn btn-default">取消</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script defer type="text/javascript">
		$("button#pay_btn").bind('click', function() {
			$(this).attr("disabled",true);
			var form = $("#pay_form");
			form.attr("action", "/order/pay");
			form.attr("method", "post");
			form.submit();
		});

		var tr = $("#medicine_table").children("tbody").find("tr");
		var str = tr.find("td:eq(4)").text().split("￥");
		var totalPrice = 0;
		for (var i = 1; i < str.length; i++) {
			var o = str[i].split(",");
			if (o.length > 1) {
				str[i] = '';
				for ( var j in o)
					str[i] += o[j];
			}
			totalPrice += parseFloat(str[i]);
		}
		totalPrice = totalPrice.formatMoney(2, '￥', ',', '.');
		$("#medicine_table").append(
				"<tr><td colspan='5' align='center'>总价:" + totalPrice
						+ "</td></tr>");

		tr = $("#project_table").children("tbody").find("tr");
		var str = tr.find("td:eq(3)").text().split("￥");
		totalPrice = 0;
		for (var i = 1; i < str.length; i++) {
			var o = str[i].split(",");
			if (o.length > 1) {
				str[i] = '';
				for ( var j in o)
					str[i] += o[j];
			}
			totalPrice += parseFloat(str[i]);
		}
		totalPrice = totalPrice.formatMoney(2, '￥', ',', '.');
		$("#project_table").append(
				"<tr><td colspan='4' align='center'>总价:" + totalPrice
						+ "</td></tr>");
	</script>
</body>

</html>