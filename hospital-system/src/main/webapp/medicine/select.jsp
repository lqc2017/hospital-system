<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>添加药品</title>
<%@ include file="../source.jsp"%>
<!-- default header name is X-CSRF-TOKEN -->
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
					<label for="type">类型:</label> <select name="so.type" id="type"
						class="form-control">
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
			</form>
		</div>

		<div class="table-responsive" id="table_div">
			<table id="search_table" class="table table-hover">
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

				</tbody>
			</table>
			<div id="paginator" style="width: 50%; margin: 10px auto;"></div>
		</div>
	</div>

	<div
		style="width: 40%; margin: 10px 2.5%; float: left; display: inline">
		已添加： <br />
		<table id="add_table" class="table table-bordered">
		<thead>
			<tr>
				<th width="20%">药品编号</th>
				<th width="50%">药品名称</th>
				<th width="15%">类型</th>
				<th width="15%">操作</th>
			</tr>
			</thead>
			<tbody></tbody>
		</table>
		<form id="hidden_form">
			<input type="hidden" name="<s:property value='#request._csrf.parameterName'/>"
				value="<s:property value='#request._csrf.token'/>" />
		</form>
		<div class="btn-toolbar" style="float:right;">
			<button name="submit" type="button" class="btn btn-default">确定</button>
			<button type="button" id="ret" onclick="javascript:history.go(-1)"
				class="btn btn-default">取消</button>
		</div>
	</div>


	<hr />

	<%-- <h3>Session Scope (key==values)</h3>
	<%
		java.util.Enumeration<String> sessEnum = request.getSession().getAttributeNames();
		while (sessEnum.hasMoreElements()) {
			String s = sessEnum.nextElement();
			out.print(s);
			out.println("==" + request.getSession().getAttribute(s));
	%><br />
	<%
		}
	%> --%>

	<script type="text/javascript">
var ids = new Array();
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {  
    xhr.setRequestHeader(header, token);  
});

$("input[name='so.name']").bind('input propertychange', callback);
$("select[name='so.type']").bind('change', callback);
$("button[name='submit']").bind('click',function(){
	var form = $("#hidden_form");
	if($("#add_table").find("a").size() == 0){
		alert("请添加药品！");
		return;
	}else if($("#add_table").find("a").size() > 10){
		alert("药品数量不能超过10个！");
		return;
	}
	$(this).attr("disabled",true);
	
	//添加表每有一行记录就在隐藏form写一个药品id上传,后台用ids接收
	$("#add_table").find("a").each(function(){
		form.append("<input name='medicineIds' type='hidden' value='"+$(this).attr("_id")+"'/>");
	});
	
	
    form.attr("action","/prescription/multi_edit");
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
        url: "/medicine/change_table",
        data: form.serialize(),
        datatype: 'json',
        success: function (data,name) {
        	//alert(data);//结果：cid:C0 \n ctext:区县  
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
		tbody.append("<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
		var tr = tbody.children("tr:eq("+i+")");
		for(var property in records[i]){
			//alert(property);
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
			case 'type':
			  switch (records[i][property]){
  				case 10:
  					records[i][property] = '口服';
  				  break;
  				case 20:
  					records[i][property] = '外用';
  				  break;
  				}
			  tr.children("td:eq(2)").append(records[i][property]);
			  break;
			case 'unit':
				switch (records[i][property]){
  				case 10:
  					records[i][property] = '盒';
  				  break;
  				case 20:
  					records[i][property] = '瓶';
  				  break;
  				case 30:
  					records[i][property] = '包';
  				  break;
  				}
				tr.children("td:eq(3)").append(records[i][property]);
			  break;
			case 'price':
				records[i][property] = '￥'+records[i][property].toFixed(2);
				tr.children("td:eq(4)").append(records[i][property]);
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
	var cnt = $("#add_table").find("tbody").children("tr").length;
	a.bind('click', function(){
		//存进
		ids.push(parseInt($(this).attr("_id")));
		//复制行内容到到表
		var tr = $(this).parents("tr:first");
		var content = "<tr>";
		tr.children("td").each(function(){
			if($(this).index()<3||$(this).index()>4)
				content +="<td>"+ $(this).html()+"</td>";
		});
		content += "</tr>";
		var addTable = $("#add_table");
		addTable.children("tbody").append(content);
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

	//向ids中添加已添加的药品id
	 <s:iterator value="#smi" var="medicineId" status="st">
		ids.push(<s:property value='#medicineId'/>);
	</s:iterator> 
	//取药品表
	//var length = ${fn:length(pageInfo.list)}
	var data = [];
	<s:iterator value="pageInfo.list" var="medicine" status="st">
	data[<s:property value="#st.index"/>] = new Object();
	data[<s:property value="#st.index"/>].id = <s:property value="#medicine.id"/>;
	data[<s:property value="#st.index"/>].name = "<s:property value='#medicine.name'/>";
	data[<s:property value="#st.index"/>].type = <s:property value='#medicine.type'/>;
	data[<s:property value="#st.index"/>].unit = <s:property value='#medicine.unit'/>;
	data[<s:property value="#st.index"/>].price = <s:property value='#medicine.price'/>;
	data[<s:property value="#st.index"/>].describe = "<s:property value='#medicine.describe'/>";
	</s:iterator>
	
	reloadST(data);
	reloadPaginator(pn,pages);
	reloadTAB();
});
</script>
</body>
</html>

