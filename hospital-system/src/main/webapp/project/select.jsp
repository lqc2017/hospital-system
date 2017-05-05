<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>添加检查</title>
<%@ include file="../source.jsp"%>
<meta name="_csrf" content="<s:property value='#request._csrf.token'/>" />
<meta name="_csrf_header" content="<s:property value='#request._csrf.headerName'/>" />
</head>

<body>
	<input id="totalPages" type="hidden" value="<s:property value='pageInfo.pages' />" />
	<input id="currentPn" type="hidden" value="<s:property value='pageInfo.pageNum' />" />

	<div
		style="width: 50%; margin: 10px 2.5%; float: left; display: inline">
		<div>
			<form id="search_form" class="form-inline pull-top">
				<div class="form-group">
					<label for="name">名称:</label> <input type="text" name="so.name"
						id="name" class="form-control" value="<s:property value='so.name'/>">
				</div>

				<!-- 页码 -->
				<input name="pn" type="hidden" />
			</form>
		</div>

		<div class="table-responsive" id="table_div">
			<table id="search_table" class="table table-hover">
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

				</tbody>
			</table>
			<div id="paginator" style="width: 50%; margin: 10px auto;"></div>
		</div>
	</div>

	<div
		style="width: 43%; margin: 10px 1%; float: left; display: inline">
		已添加： <br />
		<table id="add_table" class="table table-bordered">
			<tr>
				<th width="16%">项目编号</th>
				<th width="25%">项目名称</th>
				<th width="49%">内容</th>
				<th width="10%">操作</th>
			</tr>
		</table>
		<form id="hidden_form">
			<input type="hidden" name="<s:property value='#request._csrf.parameterName'/>"
				value="<s:property value='#request._csrf.token'/>" />
		</form>
		<div class="btn-toolbar" style="float: right;">
			<button name="submit" type="button" class="btn btn-default">确定</button>
			<button type="button" id="ret" onclick="javascript:history.go(-1)"
				class="btn btn-default">取消</button>
		</div>
	</div>
	<%
		java.util.Enumeration<String> sessEnum = request.getSession().getAttributeNames();
		while (sessEnum.hasMoreElements()) {
			String s = sessEnum.nextElement();
			out.print(s);
			out.println("==" + request.getSession().getAttribute(s));
	%><br />
	<%
		}
	%>
	<script type="text/javascript">
var ids = new Array();
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {  
    xhr.setRequestHeader(header, token);  
});

$("input[name='so.name']").bind('input propertychange', callback);
$("button[name='submit']").bind('click',function(){
	var form = $("#hidden_form");
	if($("#add_table").find("a").size() == 0){
		alert("请添加项目！");
		return;
	}else if($("#add_table").find("a").size() > 10){
		alert("项目数量不能超过10个！");
		return;
	}
	$(this).attr("disabled",true);
	//添加表每有一行记录就在隐藏form写一个药品id上传,后台用ids接收
	$("#add_table").find("a").each(function(){
		form.append("<input name='projectIds' type='hidden' value='"+$(this).attr("_id")+"'/>");
	});
	
    form.attr("action","/project_check/multi_edit");
    form.attr("method","post");
    form.submit(); 
});

function callback(flag) {
	var form = $("#search_form");
	$("input[name='pn']").val("1");
	
	$.ajax({
        async: false,
        cache: true,
        type: "POST",
        url: path + "/change_table",
        data: form.serialize(),
        datatype: 'json',
        success: function (data,name) {
        	//初始化页码和内容
        	var list = data.pageInfo.list;
        	$("#currentPn").val(data.pageInfo.pageNum);
        	reloadST(list);
        	reloadTAB();
        	reloadPaginator(data.pageInfo.pageNum,data.pageInfo.pages);
        },
        error: function (jqXHR, textStatus, errorThrown) {
        }
    });
}
/* 加载search_table */
function reloadST(records){
	$("#search_table").removeClass(); 
	var tbody = $("#search_table").find("tbody");
	tbody.children().remove();
	for(var i = 0; i < records.length; i++)
	{
		tbody.append("<tr><td></td><td></td><td></td><td></td><td></td></tr>");
		var tr = tbody.children("tr:eq("+i+")");
		for(var property in records[i]){
			switch (property){
			case 'id':
				tr.children("td:eq(0)").append(records[i][property]);
				break;
			case 'name':
				var str = records[i][property];
				tr.children("td:eq(1)").attr("title",str);
				if(getLength(str)>16)
			  		tr.children("td:eq(1)").append(cutStr(str,13));
				else
					tr.children("td:eq(1)").append(records[i][property]);
			  	break;
			case 'content':
				var str = records[i][property];
				tr.children("td:eq(2)").attr("title",str);
				if(getLength(str)>26)
			  		tr.children("td:eq(2)").append(cutStr(str,23));
				else
					tr.children("td:eq(2)").append(records[i][property]);
			  	break;
			case 'price':
				records[i][property] = '￥'+records[i][property].toFixed(2);
				tr.children("td:eq(3)").append(records[i][property]);
			  break;
			}
	    }
		if($.inArray(records[i].id,ids)==-1)
			tr.children("td:last").append("<a href='javascript:;' _id="+records[i].id+">添加</a>");
		else
			tr.children("td:last").append("已添加");
	}
	$("#search_table").addClass("table table-hover");
}
//加载按钮功能
function reloadTAB(){
	var a = $("#search_table").find("a");
	a.bind('click', function(){
		//存进
		ids.push(parseInt($(this).attr("_id")));
		//复制行内容到到表
		var tr = $(this).parents("tr:first");
		var content = "<tr>";
		tr.children("td").each(function(){
			if($(this).index()!=3){
				if($(this).index()==2){
					"<td title='"+$(this).attr("title")+"'>"+$(this).html()+"</td>"
					content +="<td title='"+$(this).attr("title")+"'>"+$(this).html()+"</td>";
				}
				else
					content +="<td>"+ $(this).html()+"</td>";
			}
		});
		content += "</tr>";
		var addTable = $("#add_table");
		addTable.append(content);
		//修改药品表按钮
		$(this).parent("td").text("已添加");
		
		//记加载删除所有删除按钮功能，点击时从添加表中删除，从并从ids中删除
		addTable.find("a").text("删除");
		addTable.find("a").bind("click",function(){
			var _id = parseInt($(this).attr("_id"));
			var index = $.inArray(_id,ids);
			$(this).parents("tr:first").remove();
			if(index>-1)
				ids.splice(index, 1);
			callback(1);
		}) 
	});
}
/* 加载分页栏 */
function reloadPaginator(pageNum,pages){
	var paginator = $("#paginator");
	paginator.children().remove();
	var contain = 5;
	if(pages<=contain){
		var start = 1;
		var end = pages;
	}
	else{
		var start = pageNum%contain==0?pageNum-contain+1:Math.floor(pageNum/contain)*contain+1;
		var end = Math.floor(pageNum/contain)==Math.floor(pages/contain)&&(pageNum%contain!=0)?pages:start+contain-1;
	}
	
	$("#paginator").append("<ul></ul>");
    var ul = $("#paginator").children("ul");
    if(start!=1)
    	ul.append("<li><a name='"+(start-1)+"' href='javascript:;'>&laquo;</a></li>");
    else
    	ul.append("<li><a href='javascript:;'>&laquo;</a></li>");
    for(var i=start;i<=end;i++){
        ul.append("<li><a name='"+i+"' href='javascript:;'>"+i+"</a></li>");
    }
    if(end!=pages)
    	ul.append("<li><a name='"+(end+1)+"' href='javascript:;'>&raquo;</a></li>");
    else
    	ul.append("<li><a href='javascript:;'>&raquo;</a></li></ul>");
    
    ul.addClass("pagination");
    pos = pageNum%contain==0?contain:pageNum%contain;
    ul.find("li:eq("+pos+")").addClass("active");
    ul.find("li a").bind("click",function(){
    	var pn = $(this).attr("name");
    	if(pn!=null&&pn!=undefined){
    		$("#currentPn").val(pn);
            callback(1);
    	}
    }); 
}

$().ready(function(){
	var pages = $("#totalPages").val();
	var pn = $("#currentPn").val();
	//向ids中添加已添加的项目id
	<s:iterator value="#spi" var="projectId" status="st">
		ids.push(<s:property value='#projectId'/>);
	</s:iterator> 
	//取项目表
	var data = [];
	
	<s:iterator value="pageInfo.list" var="project" status="st">
	data[<s:property value="#st.index"/>] = new Object();
	data[<s:property value="#st.index"/>].id = <s:property value="#project.id"/>;
	data[<s:property value="#st.index"/>].name = "<s:property value='#project.name'/>";
	data[<s:property value="#st.index"/>].content = "<s:property value='#project.content'/>";
	data[<s:property value="#st.index"/>].price = <s:property value='#project.price'/>;
	data[<s:property value="#st.index"/>].describe = "<s:property value='#project.describe'/>";
	</s:iterator>
	
	reloadST(data);
	reloadPaginator(pn,pages);
	reloadTAB();
});
</script>
</body>
</html>

