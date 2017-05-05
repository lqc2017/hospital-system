<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>医生主页</title>
<%@ include file="../source.jsp"%>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf" content="<s:property value='#request._csrf.token'/>" />
<meta name="_csrf_header" content="<s:property value='#request._csrf.headerName'/>" />
</head>
<body>
<span style="float: right; margin: 8px 30px"><s:property value="doctor.name"/>,你好！<a
		href="/doctor/logout">登出</a></span>
 <button id="record" class="btn btn-default" onclick="javascript:location.href='/record/list?so.doctorId=<s:property value="doctor.id"/>'">查看病例</button>
 
 <button id="hospitalization" class="btn btn-default" onclick="javascript:location.href='/hospitalization/list?so.doctorId=<s:property value="doctor.id"/>'">查看住院</button>

	<button id="information" class="btn btn-default" onclick="javascript:location.href='/doctor/diagnose'">诊断</button>
	<br />等待列表
	<br />
	<div style="width:30%;">
		<table class="table" id="queue">
			<thead>
				<tr>
					<td>序号</td>
					<td>姓名</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
	<div>
	<br /> 今日已诊<br />
	
		<div style="width:50%;height:400px">
		病例：<br />
		<iframe frameborder="no" style="scrolling: auto; width: 100%; height: 100%;" src="/record/doctor_list"></iframe>
		</div>
		
		<div style="width:50%;height:400px">
		住院：<br />
		<iframe frameborder="no" style="scrolling: auto; width: 100%; height: 100%;" src="/hospitalization/doctor_list"></iframe>
		</div>
	</div>
 <s:debug/>
 <script type="text/javascript">
	 var token = $("meta[name='_csrf']").attr("content");
	 var header = $("meta[name='_csrf_header']").attr("content");
	 $(document).ajaxSend(function(e, xhr, options) {  
	     xhr.setRequestHeader(header, token);  
	 });
	 
	<s:if test="#parameters.empty != null">
		toastr.warning("队列为空");
	</s:if>
	
 	getQueue();
 	setInterval("getQueue()",5000);
 	/* session失效时间 */
 	setTimeout(function(){location.href="/login?logout"},60000*120);
 	
 	function getQueue(){
 		tbody = $("#queue").find("tbody");
 		tbody.children().remove();
 		$.ajax({
	        async: false,
	        cache: true,
	        type: "GET",
	        url: "/doctor/show_queue",
	        datatype: 'json',
	        success: function (datas,name) {
	        	if(name="success"){
		        	var patients = datas.patients;
		        	for(var i=0;i<patients.length;i++){
		        		tbody.append("<tr><td>"+(i+1)+"</td><td>"+patients[i].name+"</td><td></td></tr>");
		        	}
	        	}
	        },
	        error: function (jqXHR, textStatus, errorThrown) {
	        }
	    });
 	}
 </script>
</body>
</html>