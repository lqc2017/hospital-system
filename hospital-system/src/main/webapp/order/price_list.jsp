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
	<s:if test="#ho.type==0">
		<s:if test="#hmscnt>0">
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
								<td>
								<s:if test="#medicine.unit==10" >盒</s:if>
								<s:elseif test="#medicine.unit==20" >瓶</s:elseif>
								<s:elseif test="#medicine.unit==30" >包</s:elseif>
								</td>
								<td><s:property value="%{formatCurrency(#medicine.price)}" /></td>
								<td><s:property
										value="%{formatCurrency(#medicine.price*#hpss[#st.index].count)}" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</s:if>

		<s:if test="#hpscnt>0">
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
								<td><s:property value="%{formatCurrency(#project.price)}" /></td>
								<td><s:property
										value="%{formatCurrency(#project.price*#hpcs[#st.index].totalCount)}" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
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

	<div style="width: 10%; margin: 10px 45%;">
		<button type="button" onclick="javascript:history.go(-1)"
			class="btn btn-default">返回</button>
	</div>

	<script defer type="text/javascript">
	var tr = $("#medicine_table").children("tbody").find("tr");
	var str = tr.find("td:eq(4)").text().split("￥");
	var totalPrice = 0;
	for(var i=1;i<str.length;i++){
		var o = str[i].split(",");
		if(o.length>1){
			str[i]= '';
			for(var j in o)
				str[i]+=o[j];
		}
		totalPrice += parseFloat(str[i]);
	}
	totalPrice = totalPrice.formatMoney(2, '￥', ',', '.');
	$("#medicine_table").append("<tr><td colspan='5' align='center'>总价:"+totalPrice+"</td></tr>");
	
	tr = $("#project_table").children("tbody").find("tr");
	var str = tr.find("td:eq(3)").text().split("￥");
	totalPrice = 0;
	for(var i=1;i<str.length;i++){
		var o = str[i].split(",");
		if(o.length>1){
			str[i]= '';
			for(var j in o)
				str[i]+=o[j];
		}
		totalPrice += parseFloat(str[i]);
	}
	totalPrice = totalPrice.formatMoney(2, '￥', ',', '.');
	$("#project_table").append("<tr><td colspan='4' align='center'>总价:"+totalPrice+"</td></tr>");
	
</script>
</body>

</html>