<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>药品列表</title>
<%@ include file="/source.jsp"%>
<script src="/static/js/commons/paginator.js"></script>
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf" content="<s:property value="#request._csrf.token"/>" />
<meta name="_csrf_header" content="<s:property value="#request._csrf.headerName"/>" />
</head>

<body>
	<input id="totalPages" type="hidden"
		value="<s:property value='pageInfo.pages' />" />
	<input id="currentPn" type="hidden"
		value="<s:property value='pageInfo.pageNum' />" />
	<%-- <div style="width: 95%; height: 40px; margin: 5px auto;">
		<span style="float: right; margin: 5px 30px">管理员：<s:property value="#admin.name"/>,你好！<a
			href="/logout">登出</a></span>
	</div> --%>
	<div style="width: 95%; margin: 0 auto 15px auto;">
		<form id="search_form" class="form-inline" method="get">
			<div class="form-group">
					<label for="type">类型:</label> <select name="so.type" id="type"
						class="form-control">
						<option value="" <s:if test="#so.type==null">selected</s:if>>无</option>
						<option value="10" <s:if test="#so.type==10">selected</s:if>>口服</option>
						<option value="20" <s:if test="#so.type==20">selected</s:if>>外用</option>
					</select>
				</div>

				<div class="form-group">
					<label for="name">名称:</label> <input type="text" name="so.name"
						id="name" class="form-control" value="<s:property value='so.name'/>">
				</div>

				<!-- 页码 -->
				<input name="pn" type="hidden" value="<s:property value='pageInfo.pageNum' />" />

			<div class="form-group">
				<div class="btn-toolbar">
					<a href="javascript:;"
						class="btn btn-default btn-search"><span
						class="glyphicon glyphicon-search"></span>查询</a> <a
						href="javascript:;" class="btn btn-default btn-reset">重置</a>
				</div>
			</div>
		</form>
	</div>
	
	<div style="width: 95%; margin: 0 auto;">
		<ol class="breadcrumb">
			<li><a href="/admin/home">返回主页</a></li>
		</ol>
	</div>

	<div class="table-responsive" style="width: 95%; margin: 30px auto;">
		<table class="table table-hover">
			<thead>
				<tr>
					<th width="15%">药品编号</th>
					<th width="30%">药品名称</th>
					<th width="15%">类型</th>
					<th width="10%">单位</th>
					<th width="20%">价格</th>
					<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="pageInfo.list" id="medicine">
					<tr>
						<td><s:property value="#medicine.id"/></td>
						<td><s:property value="#medicine.name"/></td>
						<td><s:if test="#medicine.type==10">口服</s:if> <s:elseif
								test="#medicine.type==20">外用</s:elseif></td>
						<td><s:if test="#medicine.unit==10">盒</s:if> <s:elseif
								test="#medicine.unit==20">瓶</s:elseif> <s:elseif
								test="#medicine.unit==30">包</s:elseif></td>
						<td><s:property value="%{formatCurrency(#medicine.price)}"/></td>
						<td>
							<div class="operation">
								<span name="edit"
									medicineId="<s:property value='#medicine.id' />"
									class="glyphicon glyphicon-edit"></span> <span
									name="delete" medicineId="<s:property value='#medicine.id' />"
									class="glyphicon glyphicon-trash"></span>
							</div>
						</td>
					</tr>
				</s:iterator>
			</tbody>
		</table>

		<div id="paginator" style="width: 23%; margin: 10px auto;"></div>

	</div>
	
	<div id="update_dialog"></div>
	<div id="delete_dialog"></div>

	<script defer type="text/javascript">
		var validator;
		var medicineId;
		var setWidth = 400;
		var setHeight = 510;
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		$(document).ajaxSend(function(e, xhr, options) {  
	    	xhr.setRequestHeader(header, token);  
		});
		
		$("[name='edit']").bind('click', function() {
			medicineId = parseInt($(this).attr("medicineId"));
			$("#update_dialog").dialog("open");
		});
		
		$("[name='delete']").bind('click', function() {
			medicineId = parseInt($(this).attr("medicineId"));
			$("#delete_dialog").dialog("open");
		});
		
		$("#update_dialog").dialog({autoOpen : false,
			modal : true,
			width : setWidth,
			height : setHeight,
			title : "修改药品",
			open : function() {
			$(this).append("<iframe></iframe>");
			var frame = $(this).children("iframe");
			var opt = {scrolling : "auto",width : "100%",height : "100%",frameborder : "0",
						src : "/medicine/edit?medicineId="+ medicineId};
			frame.attr(opt);
			},
			close: function () {$(this).children().remove();},
			buttons : [{
					text : "确认修改",
					click : function() {
					var frame = $(this).children("iframe");
					var form = frame.contents().find('#edit_form');
					validator.validate();
					if (!validator.isValid()) {
						return;
					}
					$.ajax({
						async : false,
						cache : true,
						type : "POST",
						url : "/medicine/update",
						data : form.serialize(),
						datatype : 'json',
						success : function(datas,name) {
							if(datas.result=="success")
		                        toastr.success("修改成功！");
		                    if(datas.result=="fail")
		                        toastr.error("修改失败！");
						},
						error : function(jqXHR,textStatus,errorThrown) {alert(textStatus);}
					});
					$( this ).dialog( "close" );
		            setTimeout(function(){$("#search_form").submit();},1500);
				}},
				{text : "取消",
				click : function() {$(this).dialog("close");}} ]
			});
		
		$("#delete_dialog").dialog({autoOpen : false,width : 200,height : 150,modal : true,title : "删除",
			open : function() {$(this).append("确认要删除这条记录吗？");},
			close : function() {$(this).empty();},
			buttons : [{text : "确认",click : function() {
					$.ajax({	
					async : false,
					type : "GET",
					url : "/medicine/delete?medicineId="+ medicineId,
					datatype : 'json',
					success : function(response) {
						if (response.result == "success") {
							toastr.success("删除成功！");
						}
						if (response.result == "fail")
							toastr.error("删除失败");
						},
					error : function(jqXHR,textStatus,errorThrown) {
								alert(textStatus);
							}
					});
						$("#delete_dialog").dialog("close");
						setTimeout(function(){location.reload();},1500);
					}
					},
					{text : "取消",click : function() {$(this).dialog("close");}}]
		});
	</script>
</body>

</html>