<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>挂号页</title>
<%@ include file="../source.jsp"%>
</head>
<body>
	<div style="width: 95%; margin: 0 auto;">
		<ol class="breadcrumb">
			<li><a href="/doctor/home">返回主页</a></li>
		</ol>
	</div>
	
	<div>
		<label>姓名：<s:property value="#patient.name" /></label> <br/>
		<label>性别：<s:if test='#patient.sex==0'>男</s:if> <s:else>女</s:else></label>  <br/>
		<label>年龄：<s:property value="#patient.age" /></label> <br/>
	</div>
	<div>
		<button  name="recordEdit" class="btn btn-default" 
			<s:if test="#peekStatus=='hrEdited'||#peekStatus=='both'">disabled</s:if>>
			<s:if test="#peekStatus=='hrEdited'||#peekStatus=='both'">病例已填写</s:if><s:else>填写病例</s:else></button>
		<button  name="hospitalizationEdit" class="btn btn-default"
			<s:if test="#peekStatus=='hhEdited'||#peekStatus=='both'">disabled</s:if>>
			<s:if test="#peekStatus=='hhEdited'||#peekStatus=='both'">住院已填写</s:if><s:else>填写住院</s:else></button>
		<button  name="next" class="btn btn-default" onclick="javascript:location.href='/doctor/next'">下一位</button>
	</div>
	
	<div id="record_dialog"></div>
	<div id="hospitalization_dialog"></div>
	
 <script type="text/javascript">
 var setWidth=1000;
 var setHeight=700;
 
 $("#record_dialog").dialog({autoOpen : false,
		modal : true,
		width : setWidth,
		height : setHeight,
		title : "填写病例",
		open : function() {
		$(this).append("<iframe></iframe>");
		var frame = $(this).children("iframe");
		var opt = {scrolling : "auto",width : "100%",height : "100%",frameborder : "no",
					src : "/record/edit?patientId=<s:property value='#patient.id' />"};
		frame.attr(opt);
		},
		close: function () {
			$(this).children().remove();
			/* 关闭对话框时清空保留信息 */
			$.ajax({
		        async: false,
		        cache: true,
		        type: "GET",
		        url: "/record/clear_session",
		        datatype: 'json',
		        success: function (datas,name) {
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		        }
		    });
}});
 
  $("#hospitalization_dialog").dialog({autoOpen : false,
		modal : true,
		width : setWidth,
		height : setHeight,
		title : "填写住院",
		open : function() {
		$(this).append("<iframe></iframe>");
		var frame = $(this).children("iframe");
		var opt = {scrolling : "auto",width : "100%",height : "100%",frameborder : "no",
					src : "/hospitalization/edit?patientId=<s:property value='#patient.id' />"};
		frame.attr(opt);
		},
		close: function () {
			$(this).children().remove();
			}
	}); 
 
 $("[name='recordEdit']").bind("click",function(){
	 $("#record_dialog").dialog("open");
 });
 
 $("[name='hospitalizationEdit']").bind("click",function(){
	 $("#hospitalization_dialog").dialog("open");
 }); 
 </script>
</body>
</html>