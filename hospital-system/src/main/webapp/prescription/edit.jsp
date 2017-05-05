<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
    <title>编辑处方</title>
    <%@ include file="../source.jsp" %>
</head>
<body>
<div style="width:60%;margin:10% 20%;">
<form id="edit_form" class="form-horizontal">
	<input type="hidden" name="<s:property value='#request._csrf.parameterName'/>"
				value="<s:property value='#request._csrf.token'/>" />
	
    <input type="hidden" name="prescription.id" value="<s:property value='prescription.id'/>">
     <input type="hidden" name="prescription.currentCourse" value="<s:property value='prescription.currentCourse'/>">
		
		<div class="form-group">
            <label class="col-lg-3 control-label">药品名称：</label>
            <div class="col-lg-5">
            	<input type="text" readonly class="form-control" value="<s:property value='#medicine.name'/>">
            </div>
        </div>
        
        
        <div class="form-group" >
            <label for="count" class="col-lg-3 control-label">数量：</label>
            <div class="col-lg-2">
            	<input type="text" class="form-control" name="prescription.count" value="<s:property value='prescription.count'/>">
            </div>
        </div>
        
        <div class="form-group" >
            <label for="totalCourse" class="col-lg-3 control-label">总疗程：</label>
            <div class="col-lg-2">
            	<input type="text" class="form-control" name="prescription.totalCourse" value="<s:property value='prescription.totalCourse'/>">
            </div>
        </div>
        
        <div class="form-group" >
            <label for=instructor class="col-lg-3 control-label">服用指导：</label>
            <div class="col-lg-5">
            	<textarea class="form-control" rows="5" name="prescription.instructor" ><s:property value='prescription.instructor'/></textarea>
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
        	'prescriptions.count' : {
				validators : {
					notEmpty : {
						message : '数量不能为空'
					},
					numeric : {
						message : '请输入数字'
					},
					between : {
						min : 1,
						max : 100,
						message : '范围在1到100之间'
					},
				}
			},
			'prescriptions.totalCourse' : {
				validators : {
					notEmpty : {
						message : '总疗程不能为空'
					},
					numeric : {
						message : '请输入数字'
					},
					between : {
						min : 1,
						max : 100,
						message : '范围在1到100之间'
					},
				}
			},
			'prescriptions.instructor' : {
				validators : {
					notEmpty : {
						message : '说明不能为空'
					},
					stringLength : {
						min : 0,
						max : 100,
						message : '超过输入限制'
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
