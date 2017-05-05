<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>评价页</title>
<%@ include file="../source.jsp"%>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf" content="<s:property value="#request._csrf.token"/>" />
<meta name="_csrf_header" content="<s:property value="#request._csrf.headerName"/>" />
</head>
<body>
	<form class="form-horizontal" id="add_form">
		<s:if test="#records.size()!=0"><h1>病例:</h1>
		<br/></s:if>
		<s:iterator value="#records" id="record" status="st">
		<div style="width:80%;margin:0px 0px 13% 2%;">
			<div style="border: 1px solid">
				<div class="form-group">
					<div class="controls">
						<label class="col-sm-3 control-label">就诊时间：</label>
						<div class="col-sm-3">
						<s:date name="#record.createTime" format="yyyy-MM-dd"/>
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="controls">
						<label class="col-sm-3 control-label">科室：</label>
						<div class="col-sm-3">
							<s:if test="#hrDoctors[#st.index].department==10">儿科</s:if> <s:if
							test="#hrDoctors[#st.index].department==20">骨科</s:if> <s:if
							test="#hrDoctors[#st.index].department==30">外科</s:if>
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="controls">
						<label class="col-sm-3 control-label">主治医师：</label>
						<div class="col-sm-3">
							<s:property value="#hrDoctors[#st.index].name"/>
						</div>
					</div>
				</div>
				
				<div class="form-group">
						<label for=describe class="col-sm-3 control-label">症状：</label>
						<div class="col-sm-5">
							<textarea class="form-control" rows="5" readonly><s:property value='#record.symptoms'/></textarea>
						</div>
				</div>

			</div>

				<div name="hr_rank" style="width:20%;margin:5px 0;float:right;">
					<input type="hidden" name="doctorId" value="<s:property value='#hrDoctors[#st.index].id'/>">
					<input type="hidden" name="type" value="1">
					<input type="hidden" name="recordId" value="<s:property value='#record.id'/>">
					<input type="hidden" name="doctorId" value="<s:property value='#hrDoctors[#st.index].id'/>">
					<label class="radio"> <input type="radio" name="hr_rank<s:property value='#st.index'/>" value="1" checked>好评</label> 
					<label class="radio"> <input type="radio" name="hr_rank<s:property value='#st.index'/>" value="0">中评</label>
					<label class="radio"> <input type="radio" name="hr_rank<s:property value='#st.index'/>" value="-1">差评</label>
					<button name="submit" type="button" class="btn btn-default">评价</button>
				</div>
			</div>
		</s:iterator>
		
		<s:if test="#hospitalizations.size()!=0"><h1>住院:</h1>
		<br/></s:if>
		<s:iterator value="#hospitalizations" id="hospitalization" status="st">
		<div style="width:80%;margin:0px 0px 13% 2%;">
			<div style="border: 1px solid">
				<div class="form-group">
					<div class="controls">
						<label class="col-sm-3 control-label">入院时间：</label>
						<s:date name="#hospitalization.createTime" format="yyyy-MM-dd"/>
					</div>
				</div>
				
				<div class="form-group">
					<div class="controls">
						<label class="col-sm-3 control-label">出院时间：</label>
						<s:date name="#hospitalization.leaveTime" format="yyyy-MM-dd"/>
					</div>
				</div>
				
				<div class="form-group">
					<div class="controls">
						<label class="col-sm-3 control-label">科室：</label>
						<div class="col-sm-3">
							<s:if test="#hhDoctors[#st.index].department==10">儿科</s:if> <s:if
							test="#hhDoctors[#st.index].department==20">骨科</s:if> <s:if
							test="#hhDoctors[#st.index].department==30">外科</s:if>
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="controls">
						<label class="col-sm-3 control-label">主治医师：</label>
						<div class="col-sm-3">
							<s:property value="#hhDoctors[#st.index].name"/>
						</div>
					</div>
				</div>
				
				<div class="form-group">
						<label for=describe class="col-sm-3 control-label">症状：</label>
						<div class="col-sm-5">
							<textarea class="form-control" rows="5" readonly><s:property value='#hospitalization.symptoms'/></textarea>
						</div>
				</div>

			</div>
			
			<div name="hh_rank" style="width:20%;margin:5px 0;float:right;">
					<input type="hidden" name="doctorId" value="<s:property value='#hhDoctors[#st.index].id'/>">
					<input type="hidden" name="type" value="1">
					<input type="hidden" name="hospitalizationId" value="<s:property value='#hospitalization.id'/>">
					<input type="hidden" name="doctorId" value="<s:property value='#hhDoctors[#st.index].id'/>">
					<label class="radio"> <input type="radio" name="hh_rank<s:property value='#st.index'/>" value="1" checked>好评</label> 
					<label class="radio"> <input type="radio" name="hh_rank<s:property value='#st.index'/>" value="0">中评</label>
					<label class="radio"> <input type="radio" name="hh_rank<s:property value='#st.index'/>" value="-1">差评</label>
					<button name="submit" type="button" class="btn btn-default">评价</button>
				</div>
		</div>
		</s:iterator>
	</form>
	
	<form id="update_form">
		<input type="hidden" name="type">
		<input type="hidden" name="rank">
		<input type="hidden" name="id">
		<input type="hidden" name="doctorId">
	</form>
	
 <script type="text/javascript">
		var rank;
		var doctorId;
		var id;
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		$(document).ajaxSend(function(e, xhr, options) {
			xhr.setRequestHeader(header, token);
		});

		$("div[name='hh_rank']").find("button[name='submit']")
				.bind(
						"click",
						function() {
							var rankForm = $(this).parent("div");
							rank = rankForm.find(":checked").val();
							doctorId = rankForm.find("input[name='doctorId']")
									.val();
							id = rankForm.find(
									"input[name='hospitalizationId']").val();

							update(1);
						})

		$("div[name='hr_rank']").find("button[name='submit']").bind("click",
				function() {
					var rankForm = $(this).parent("div");
					rank = rankForm.find(":checked").val();
					doctorId = rankForm.find("input[name='doctorId']").val();
					id = rankForm.find("input[name='recordId']").val();

					update(0);
				})

		function update(type) {
			var form = $("#update_form");

			form.find("input[name='rank']").val(rank);
			form.find("input[name='doctorId']").val(doctorId);

			form.find("input[name='id']").val(id);
			form.find("input[name='type']").val(type);

			$.ajax({
				async : false,
				cache : true,
				type : "POST",
				url : "/patient/appraise",
				data : form.serialize(),
				datatype : 'json',
				success : function(datas, name) {
					if (datas.result == "success")
						toastr.success("感谢您的填写！");
					if (datas.result == "fail") {
						toastr.error("提交失败！");
						alert(datas.message);
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(textStatus);
				}
			});

			setTimeout(function() {
				location.reload();
			}, 1500);
		}
	</script>
</body>
</html>