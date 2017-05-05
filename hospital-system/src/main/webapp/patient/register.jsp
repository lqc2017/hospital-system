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

<h1>请选择科室和医生</h1>
	<select name="department">
		<option value="" selected>所有</option>
		<option value="10">儿科</option>
		<option value="20">外科</option>
		<option value="30">骨科</option>
	</select>
	<select name="doctorId">
		<s:iterator value="doctors" id="doctor">
			<option value="<s:property value='#doctor.id'/>"><s:property value='#doctor.name'/></option>
		</s:iterator>
	</select>
	
	<button  name="confirm" class="btn btn-default">确定</button>
 <script type="text/javascript">
	 $("select[name='department']").bind('change', function(){
		 $("select[name='doctorId']").children().remove();
		 var department = $("select[name='department']").children("option:selected").val();
		 $.ajax({
		        async: false,
		        cache: true,
		        type: "GET",
		        url: "/patient/select_doctor?department="+department,
		        datatype: 'json',
		        success: function (data,name) {
		        	var list = data.doctors;
		        	//alert("找到的医生数量:"+list.length);
		        	if(list.length!=0){
			        	for(var i=0;i<list.length;i++){
			        		$("select[name='doctorId']").append("<option value="+list[i].id+">"+list[i].name+"</option>");
			        	}
		        	}else{
		        		$("select[name='doctorId']").append("<option value=''>无</option>");
		        	}
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		        }
		    });
	 });
	 
	 $("button[name='confirm']").bind('click', function(){
		 var doctorId = $("select[name='doctorId']").find("option:selected").val();
		 $.ajax({
		        async: false,
		        cache: true,
		        type: "GET",
		        url: "/patient/add_into_queue?doctorId="+doctorId,
		        datatype: 'json',
		        success: function (data,name) {
		        	var result = data.result;
		        	if (result == "success") {
						toastr.success("挂号成功！");
						setTimeout(function(){
							if(data.message!=null)
							alert(data.message);
							location.href="/patient/home?registered=true&doctorId="+data.doctorId;
							},1500);
					}
					if (result == "fail")
						toastr.error("挂号失败");
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		        }
		    });
	 })
 </script>
</body>
</html>