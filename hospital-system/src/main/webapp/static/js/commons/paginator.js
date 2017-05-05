/**
 * Created by 全琛 on 2017/2/23.
 */
var pagePerBar;
var staticBarSize = 5;
var totalPages;
var minFlip = 1;
var maxFlip;
var flipCnt;

$().ready(function(){
    totalPages = $("#totalPages").val();
    pagePerBar = staticBarSize>totalPages?totalPages:staticBarSize;
    if(pagePerBar==0) {
        $(".table-hover").append("未检索到数据！");
        pagePerBar = 1;
    }
    var pn = $("#currentPn").val();
    flipCnt = parseInt(pn)%pagePerBar==0?parseInt(parseInt(pn)/pagePerBar):parseInt(parseInt(pn)/pagePerBar)+1;

    if(totalPages%pagePerBar!=0)
        maxFlip = parseInt(totalPages/pagePerBar)+1;
    else{
        if(pagePerBar===totalPages)
            maxFlip = 1;
        else
            maxFlip = totalPages/pagePerBar;
    }
    if(flipCnt!=maxFlip) {
        initLi(pagePerBar);
        initPn((flipCnt-1)*pagePerBar+1,pagePerBar);
    }

    else {
        var barSize = totalPages-(flipCnt-1)*pagePerBar;
        initLi(barSize);
        initPn((flipCnt-1)*pagePerBar+1,barSize);
    }

    var pos = parseInt(pn)%(pagePerBar)==0?pagePerBar:parseInt(pn)%(pagePerBar);
    $("#paginator").find("ul li:eq("+pos+")").addClass("active");
});


function	initPn(start,n){
    var pos = start;
    for(var j=1;j<=n;j++){
        var a=$("#paginator").find("li:eq("+j+")").children("a");
        a.empty();
        a.append(""+pos);
        pos++;
    }
}

function	initLi(n){
    $("#paginator").append("<ul></ul>");
    var a = $("#paginator").children("ul");
    a.append("<li><a href='javascript:;' onclick='reposit(0)'>&laquo;</a></li>");
    for(var pos=1;pos<=n;pos++){
        var pn=(flipCnt-1)*pagePerBar+pos;
        //ie不兼容此onclick调用方式
        // a.append("<li><a href='javascript:;' onclick='setActive("+j+","+pn+")'>"+j+"</a></li>");
        a.append("<li><a href='javascript:;'></a></li>");
        /* 绑定跳转*/
        a.children("li:eq("+pos+")").children("a").bind("click",{param:pn},function(event){
        	var form = $("#search_form");
        	form.find("input[name='pn']").val(event.data.param);
        	form.submit();
            //window.location.href=recomposeUrl("pn",event.data.param);
        });
    }
    a.append("<li><a href='javascript:;' onclick='reposit(1)'>&raquo;</a></li></ul>");
    a.addClass("pagination");
}

function	reposit(e){
    if(e==0&&flipCnt>minFlip){
        this.location.href=recomposeUrl("pn",(flipCnt-1)*pagePerBar);
    }
    if(e==1&&flipCnt<maxFlip){
        this.location.href=recomposeUrl("pn",flipCnt*pagePerBar+1);
    }
}