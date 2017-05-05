<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>检查项目列表</title>
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
		<span style="float: right; margin: 5px 30px">收银员：<s:property value="#admin.name"/>,你好！<a
			href="/logout">登出</a></span>
	</div> --%>
	<div style="width: 95%; margin: 0 auto 15px auto;">
		<form id="search_form" class="form-inline" method="get">
				<div class="form-group">
					<label for="name">名称:</label> <input type="text" name="so.name"
						id="name" class="form-control" value="<s:property value='#so.name'/>">
				</div>
				
				<div class="form-group">
					<label for="name">内容:</label> <input type="text" name="so.content"
						id="name" class="form-control" value="<s:property value='#so.content'/>">
				</div>

				<!-- 页码 -->
				<input name="pn" type="hidden" />

			<div class="form-group">
				<div class="btn-toolbar">
					<a href="javascript:;"
						class="btn btn-default btn-search"><span
						class="glyphicon glyphicon-search"></span>查询</a> <a
						href="javascript:;" class="btn btn-default btn-reset">重置</a>
				</div>
			</div>

			<%-- <input name="cashierId" type="hidden" value="${cashier.id}"> --%>
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
					<th width="13%">项目编号</th>
						<th width="22%">项目名称</th>
						<th width="39%">内容</th>
						<th width="14%">价格</th>
						<th width="10%">操作</th>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="pageInfo.list" id="project">
					<tr>
						<td><s:property value="#project.id"/></td>
						<td><s:property value="#project.name"/></td>
						<td title="<s:property value='#project.content' />"><s:if
								test="#project.content.length() > 35">
								<s:property value="#project.content.substring(0,32)" />...</s:if> <s:else>
								<s:property value="#project.content" />
							</s:else>
						</td>
						<td><s:property value="%{formatCurrency(#project.price)}"/></td>
						<td>
							<div class="operation">
								<span title="修改"  name="edit" projectId="<s:property value='#project.id' />" class="glyphicon glyphicon-edit"></span>
								<span title="删除" name="delete" projectId="<s:property value='#project.id' />" class="glyphicon glyphicon-trash"></span>
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
		var projectId;
		var setWidth = 400;
		var setHeight = 470;
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		$(document).ajaxSend(function(e, xhr, options) {  
	    	xhr.setRequestHeader(header, token);  
		});
		
		$("[name='edit']").bind('click', function() {
			projectId = parseInt($(this).attr("projectId"));
			$("#update_dialog").dialog("open");
		});
		
		$("[name='delete']").bind('click', function() {
			projectId = parseInt($(this).attr("projectId"));
			$("#delete_dialog").dialog("open");
		});
		
		$("#update_dialog").dialog({autoOpen : false,
			modal : true,
			width : setWidth,
			height : setHeight,
			title : "修改处方",
			open : function() {
			$(this).append("<iframe></iframe>");
			var frame = $(this).children("iframe");
			var opt = {scrolling : "auto",width : "100%",height : "100%",frameborder : "0",
						src : "/project/edit?projectId="+ projectId};
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
						url : "/project/update",
						data : form.serialize(),
						datatype : 'json',
						success : function(datas,name) {
							if(datas.result=="success")
		                        toastr.success("修改成功！");
		                    if(datas.result=="fail")
		                        toastr.error("修改失败！");
						},
						error : function(jqXHR,textStatus,errorThrown) {/*alert(textStatus);*/}
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
							url : "/project/delete?projectId="+ projectId,
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