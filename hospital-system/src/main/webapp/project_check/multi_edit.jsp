<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
    <title>填写疗程</title>
<%@ include file="../source.jsp"%>
<meta name="_csrf" content="<s:property value='#request._csrf.token'/>" />
<meta name="_csrf_header"
	content="<s:property value='#request._csrf.headerName'/>" />
</head>

<body>
	<div style="width: 60%; margin: 10px 20%;">
		<form role="form" id="data_form" method="post" action="/record/edit">
			<input type="hidden"
				name="<s:property value='#request._csrf.parameterName'/>"
				value="<s:property value='#request._csrf.token'/>" />

		</form>

		<form role="form" class="form-horizontal" id="add_form">
			<!-- 检查项目表单 -->
			<s:iterator value="#projects" id="project" status="st">
				<div style="border: 1px solid">
					<input type="hidden"
						name="projectChecks[<s:property value='#st.index'/>].projectId"
						value="<s:property value='#project.id'/>">

					<div class="form-group">
						<div class="controls">
							<label class="col-sm-3 control-label">项目名称：</label>
							<div class="col-sm-3">
								<input type="text" readonly class="form-control"
									value="<s:property value='#project.name'/>">
							</div>
						</div>
					</div>


					<div class="form-group">
						<label for="totalCount" class="col-sm-3 control-label">次数：</label>
						<div class="col-sm-3">
							<input type="text" class="form-control"
								name="projectChecks[<s:property value='#st.index'/>].totalCount">
						</div>
					</div>

					<div class="form-group">
						<label for="executeTime" class="col-sm-3 control-label">检查时间：</label>
						<div class="col-sm-4">
							<div class="input-group">
								<input type="text" readonly class="form-control form_date"
									name="projectChecks[<s:property value='#st.index'/>].executeTime">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div>
						</div>
					</div>

					<div class="form-group">
						<label for=describe class="col-sm-3 control-label">说明：</label>
						<div class="col-sm-5">
							<textarea class="form-control" rows="5"
								name="projectChecks[<s:property value='#st.index'/>].describe"></textarea>
						</div>
					</div>

				</div>
			</s:iterator>
		</form>
		<button name="submit" type="button" class="btn btn-success"
			style="float: right">添加</button>
	</div>

	<script type="text/javascript">
	var token = $("meta[name='_csrf']").attr("content");  
	var header = $("meta[name='_csrf_header']").attr("content");  
	$(document).ajaxSend(function(e, xhr, options) {  
	    xhr.setRequestHeader(header, token);  
	});
	
	$('.form_date').datetimepicker({
	    language:  'zh-CN',
	    weekStart: 1,
	    todayBtn:  1,
	    autoclose: 1,
	    todayHighlight: 1,
	    startView: 2,
	    minView: 2,
	    forceParse: 0,
	    format: 'yyyy-mm-dd'
	});
	$("button[name='submit']").bind('click',function(){
		var dataForm = $("#data_form");
		var data= $("#add_form").serializeArray();
	    
	    var bootstrapValidator = $("#add_form").data('bootstrapValidator');
        bootstrapValidator.validate();
        if(bootstrapValidator.isValid()){
        	$(this).attr("disabled",true);
        	$.ajax({
                async: false,
                cache: true,
                type: "POST",
                url: "/project_check/multi_add",
                data: $("#add_form").serialize(),
                //contentType: "application/json",
                datatype: 'json',
                success: function (data,name) {
                	if(name=='success'){
                		var ids = data.projectCheckIds;
						/* for(var i=0;i<ids.length;i++){
							alert("id:"+ids[i]);
						} */
                		//dataForm.submit();
                		location.href="/record/edit";
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
        }
	});
	
	$('#add_form')
    .bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        trigger:'change',
        fields: {
        	<s:iterator value="#projects" status="st">
        	'projectChecks[<s:property value='#st.index'/>].totalCount':{
                validators: {
                    notEmpty: { message: '总次数不能为空'},
                    numeric: {message: '请输入数字'},
                    between: {min:1,max:100,message: '范围在1到100之间'}
                }
            },
            'projectChecks[<s:property value='#st.index'/>].executeTime': {
                validators: {
                    notEmpty: { message: '日期不能为空'},
                    callback: {
                        message: '日期早于当前日期',
                        callback: function(value, validator) {
                        	var ss = validator.getFieldElements('executeTime');
                        	var dateStr = value.replace(/-/g,"/");
                        	var n = new Date(dateStr);
                        	var date = new Date((n/1000+86400)*1000);
                        	var now = new Date();
                        	//alert(date.toString());
                        	if(date>=now)
                        		return true
                        	return false;
                        }
                    }
                }
            },
            'projectChecks[<s:property value='#st.index'/>].describe': {
                validators: {
                	stringLength: {
                    	min: 0,
                        max: 100,
                        message: '超过输入限制'
                    }
                }
            }<s:if test="!#st.last">,</s:if>
            </s:iterator>
        }
    })
</script>
</body>
</html>

