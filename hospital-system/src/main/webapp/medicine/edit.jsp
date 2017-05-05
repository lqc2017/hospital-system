<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>编辑处方</title>
<%@ include file="../source.jsp"%>
</head>
<body>
	<div style="width: 60%; margin: 10px 20%;">
		<form id="edit_form" class="form-horizontal">

			<input type="hidden" name="medicine.id" value="<s:property value='medicine.id'/>">

			<div class="form-group">
				<label class="col-lg-3 control-label">名称：</label>
				<div class="col-lg-3">
					<input type="text" class="form-control" name="medicine.name"
						value="<s:property value='medicine.name'/>">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-lg-3 control-label">类型：</label>
				<div class="col-lg-3">
					<select name="medicine.type" class="form-control">
						<option value="10" <s:if test="medicine.type==10">selected</s:if>>口服</option>
						<option value="20" <s:if test="medicine.type==20">selected</s:if>>外用</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-lg-3 control-label">单位：</label>
				<div class="col-lg-3">
					<select name="medicine.unit" class="form-control">
						<option value="10" <s:if test="medicine.unit==10">selected</s:if>>盒</option>
						<option value="20" <s:if test="medicine.unit==20">selected</s:if>>瓶</option>
						<option value="30" <s:if test="medicine.unit==30">selected</s:if>>包</option>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-lg-3 control-label">价格：</label>
				<div class="col-lg-3">
					<input type="text" class="form-control" name="medicine.price"
						value="<s:property value='medicine.price'/>">
				</div>
			</div>

		</form>
	</div>
	<script type="text/javascript">
	var form = $("#edit_form");

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
            	'medicine.name': {
                    validators: {
                        notEmpty: { message: '名称不能为空'},
                        stringLength: {
                        	min: 1,
                            max: 20,
                            message: '超过输入限制'
                        }
                    }
                },
                'medicine.price': {
                    validators: {
                    	notEmpty: { message: '总疗程不能为空'},
                        numeric: {message: '请输入数字'},
                        between: { inclusive: 'inclusive',min:0,max:10000000,message: '价格错误'},
                    }
                }
            }
        })
        
        var form = $('#edit_form');
        parent.validator = form.data('bootstrapValidator');
    })
</script>
</body>
</html>
