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

			<input type="hidden" name="project.id" value="<s:property value='project.id'/>">

			<div class="form-group">
				<label class="col-lg-3 control-label">名称：</label>
				<div class="col-lg-3">
					<input type="text" class="form-control" name="project.name"
						value="<s:property value='project.name'/>">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-lg-3 control-label">价格：</label>
				<div class="col-lg-3">
					<input type="text" class="form-control" name="project.price"
						value="<s:property value='project.price'/>">
				</div>
			</div>

			<div class="form-group">
				<label class="col-lg-3 control-label">内容：</label>
				<div class="col-lg-3">
					<textarea class="form-control" rows="4" name="project.content"><s:property value='project.content'/></textarea>
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
            	'project.name': {
                    validators: {
                        notEmpty: { message: '名称不能为空'},
                        stringLength: {
                        	min: 1,
                            max: 20,
                            message: '超过输入限制'
                        }
                    }
                },
                'project.price': {
                    validators: {
                    	notEmpty: { message: '总疗程不能为空'},
                        numeric: {message: '请输入数字'},
                        between: { inclusive: 'inclusive',min:0,max:10000000,message: '价格错误'},
                    }
                },
                'project.content': {
                    validators: {
                    	notEmpty: { message: '内容不能为空'},
                    	stringLength: {
                        	min: 1,
                            max: 100,
                            message: '超过输入限制'
                        }
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
