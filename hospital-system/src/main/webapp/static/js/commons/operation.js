/**
 * Created by 全琛 on 2017/2/21.
 */
var id;
var path;
/*对话框宽高*/
var setWidth = 400;
var setHeight = 610;

$().ready(function(){

    /*初始化对话框*/
    $( "#add_dialog" ).dialog({autoOpen: false, modal: true, width: setWidth, height: setHeight, title: "添加",
        open: function () {
            $(this).append("<iframe></iframe>");
            var frame = $(this).children("iframe");
            var opt = {scrolling:"auto",width:"100%",height:"100%",frameborder:"0",src:path+"/add"};
            frame.attr(opt);
        },
        close: function () {$(this).children().remove();},
        buttons: [{text: "确认提交", click: function() {
            var frame = $(this).children("iframe");
            var form = frame.contents().find('#add_form');
            data.validate();
            if(!data.isValid()){
                return ;
            }
            $.ajax({
                async: false,
                cache: true,
                type: "POST",
                url: path + "/add_confirm",
                data: form.serialize(),
                datatype: 'json',
                success: function (response) {
                    if (response.result == "success")
                        toastr.success("添加成功");
                    if (response.result == "fail")
                        toastr.error("添加失败");
                },
                error: function (jqXHR, textStatus, errorThrown) {
                }
            });
            $(this).dialog("close");
            setTimeout(function(){location.href = window.location.pathname;},1500)
        }
        },{text: "取消", click: function() {$( this ).dialog( "close" );}}]
    });

    $( "#update_dialog" ).dialog({autoOpen: false, modal: true, width: setWidth, height: setHeight, title: "修改",
        open: function () {
            $(this).append("<iframe></iframe>");
            var frame = $(this).children("iframe");
            var opt = {scrolling:"auto",width:"100%",height:"100%",frameborder:"0",src:path+"/edit?id="+id};
            frame.attr(opt);
        },
        close: function () {$(this).children().remove();},
        buttons: [{text: "确认修改", click: function() {
            var frame = $(this).children("iframe");
            var form = frame.contents().find('#update_form');
            data.validate();
            if(!data.isValid()){
                return ;
            }
            $.ajax({async: false,cache: true, type: "POST", url:path+"/update_confirm", data:form.serialize(),datatype:'json',
                success: function(response) {
                    if(response.result=="success")
                        toastr.success("修改成功！");
                    if(response.result=="fail")
                        toastr.error("修改失败！");
                },
                error: function (jqXHR, textStatus, errorThrown) {/*alert(textStatus);*/}
            });
            $( this ).dialog( "close" );
            setTimeout(function(){location.href = window.location.pathname;},1500)
            }
        },{text: "取消", click: function() {$( this ).dialog( "close" );}}]
    });

    $( "#delete_dialog" ).dialog({autoOpen: false, width: 200, height: 150, modal: true, title: "删除",
        open: function () {$(this).append("确认要删除这条记录吗？");},
        close: function () {$(this).empty();},
        buttons: [{text: "确认", click: function() {
            $.ajax({async: false, type: "GET", url:path+"/delete?id="+id, datatype:'json',
                success: function(response) {
                    if(response.result=="success")
                        toastr.success("已删除！");
                    if(response.result=="fail")
                        toastr.error("删除失败");
                },
                error: function (jqXHR, textStatus, errorThrown) {alert(textStatus);}
            });
            $(this).dialog( "close" );
            setTimeout(function(){location.href = window.location.pathname;},1500)}
        }, {text: "取消", click: function() {$( this ).dialog( "close" );}}]
    });
})
// 记录
function operate_record(opt,_id) {
    if(opt == 1)
        $( "#add_dialog" ).dialog( "open" );
    else
    {
        id=_id;
        if(opt == 2)
            $( "#update_dialog" ).dialog( "open" );
        if(opt == 3)
            $( "#delete_dialog" ).dialog( "open" );
    }
}