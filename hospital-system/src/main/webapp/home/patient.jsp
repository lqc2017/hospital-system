<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>患者主页</title>
<%@ include file="../source.jsp"%>
</head>
<body>
<span style="float: right; margin: 8px 30px"><s:property value="patient.name"/>,你好！<a
		href="/logout">登出</a></span>
 <button id="information" class="btn btn-default">查看信息</button>
 
 <button id="" class="btn btn-default">预约</button>
 
 <button id="register" class="btn btn-default" <s:if test="#registered!=null&&#registered!=false">disabled</s:if>>
 <s:if test="#registered!=null&&#registered!=false">已挂号</s:if>
 <s:else>挂号</s:else>
</button>

<button id="appraise" class="btn btn-default" <s:if test="#notAppraisedCnt==0">title="无待评价记录" disabled</s:if>>
	<s:if test="#notAppraisedCnt!=0">
		<span class="Counter"><s:property value="#notAppraisedCnt"/></span>
	</s:if>评价
</button>

<button id="pay" class="btn btn-default" <s:if test="#unPaidCnt==0">title="无待缴费记录" disabled</s:if>>
	<s:if test="#unPaidCnt!=0">
		<span class="Counter"><s:property value="#unPaidCnt"/></span>
	</s:if>缴费
</button>
 
 <s:if test="#registered!=null&&#registered!=false">
		<table>
			<thead>
				<tr>
					<th>科室</th>
					<th>姓名</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><s:if test="#doctor.department==10">儿科</s:if> <s:if
							test="#doctor.department==20">骨科</s:if> <s:if
							test="#doctor.department==30">外科</s:if></td>
					<td><s:property value="#doctor.name"/></td>
					<td><button class="btn btn-default" onclick="javascript:location.href='/patient/register?patientId=<s:property value='patientId'/>'">重新挂号</button></td>
				</tr>
			</tbody>
		</table>
	</s:if>
 <s:debug/>
 <script type="text/javascript">
 
 	$("#information").bind("click",function(){
 		location.href="/patient/information?patientId=<s:property value='patient.id'/>";
 	})
 	
 	$("#register").bind("click",function(){
 		location.href="/patient/register?patientId=<s:property value='patient.id'/>";
 	})
 	
 	$("#appraise").bind("click",function(){
 		location.href="/patient/edit_appraise?patientId=<s:property value='patient.id'/>";
 	})
 	
 	$("#pay").bind("click",function(){
 		location.href="/order/patient_list?so.status=10";
 	})
 </script>
</body>
</html>