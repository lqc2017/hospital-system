<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
    <title>编辑检查项目</title>
    <%@ include file="../source.jsp" %>
</head>
<body>
<div style="width:60%;margin:10% 20%;">
<form id="edit_form" class="form-horizontal">
	<input type="hidden" name="<s:property value='#request._csrf.parameterName'/>"
				value="<s:property value='#request._csrf.token'/>" />
	
    <input type="hidden" name="projectCheck.id" value="<s:property value='projectCheck.id'/>">
    <input type="hidden" name="projectCheck.isChecked" value="<s:property value='projectCheck.isChecked'/>">
    <input type="hidden" name="projectCheck.currentCount" value="<s:property value='projectCheck.currentCount'/>">
		
		<div class="form-group">
            <label class="col-lg-3 control-label">项目名称：</label>
            <div class="col-lg-5">
            	<input type="text" readonly class="form-control" value="<s:property value='#project.name'/>">
            </div>
        </div>
        
        
        <div class="form-group" >
            <label for="totalCount" class="col-lg-3 control-label">总次数：</label>
            <div class="col-lg-2">
            	<input type="text" class="form-control" name="projectCheck.totalCount" value="<s:property value='projectCheck.totalCount'/>">
            </div>
        </div>
        
        <div class="form-group" >
            <label for="executeTime" class="col-lg-3 control-label">检查时间：</label>
            <div class="col-lg-5">
            <div class="input-group">
            	<input type="text" readonly class="form-control form_date" name="projectCheck.executeTime" value="<s:date name='projectCheck.executeTime' format='yyyy-MM-dd'/>">
            	<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
            </div>
        </div>
        
        <div class="form-group" >
            <label for=describe class="col-lg-3 control-label">说明：</label>
            <div class="col-lg-5">
            	<textarea class="form-control" rows="5" name="projectCheck.describe" ><s:property value='projectCheck.describe'/></textarea>
            </div>
        </div>
</form>
</div>
<script type="text/javascript">
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

$().ready(function() {
	$('#edit_form')
    .bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        trigger:'change',
        fields: {
        	'projectCheck.totalCount': {
                validators: {
                    notEmpty: { message: '总次数不能为空'},
                    numeric: {message: '请输入数字'},
                    between: {min:1,max:100,message: '范围在1到100之间'},
                }
            },
            'projectCheck.executeTime': {
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
            'projectCheck.describe': {
                validators: {
                	stringLength: {
                    	min: 0,
                        max: 100,
                        message: '超过输入限制'
                    }
                }
            }
        }
    })
    var form = $('#edit_form');
    parent.validator = form.data('bootstrapValidator');
});


</script>
</body>
</html>
