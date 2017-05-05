/**
 * Created by 全琛 on 2017/2/27.
 */
var id;
var path;
/* data变量用于接受资源参数*/
var data;

$().ready(function() {
    path = "";
    /*初始化路径*/
    var basePath = window.location.pathname.split("/");
    for (var i = 1; i < basePath.length - 1; i++) {
        path += "/" + basePath[i];
    }
    
    /*$("button.btn-success").bind("click",function(){
    	$(this).addClass("disabled");
    	$(this).attr("disabled",true);
    });*/
    
    /*初始化检索按钮*/
    $(".btn-search").bind("click",function(){
    	var form = $(this).parents("#search_form");
    	form.find("input[name='pn']").val("1");
		form.submit();
    })
    
    /*初始化重置检索条件按钮*/
    $(".btn-reset").bind("click",function(){
    	var form = $(this).parents("#search_form");
    	form.find("input").val("");
		$("select").find("option").attr("selected", false);
		$("select").find("option:first").attr("selected", true);
		form.submit();
    })
    
});

/* 获取url参数*/
function GetQueryString(name){
    var url = window.location.search; //获取url中"?"符后的字串
    var urlParams = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            urlParams[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);
        }
    }
    // alert(urlParams[name]);
    return urlParams[name];
}

/* 获取url参数*/
function recomposeUrl(name,value)
{
    var url = window.location.search;
    var urlParams = new Object();
    var newPath;

    /* 初始化参数表urlParams*/
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            urlParams[strs[i].split("=")[0]]=decodeURI(strs[i].split("=")[1]);
        }

        /* 更新参数并重组url*/
        var basePath = window.location.pathname;
        var currentPath = basePath+"?";
        urlParams[name] = value;
        for(var key in urlParams) {
            currentPath +=  key+"="+urlParams[key]+"&";
        }
        newPath = currentPath.substr(0,currentPath.length-1);
        // alert(newPath);
    }
    else{
        newPath = window.location.pathname + "?"+name+ "=" + value;
    }
    return newPath;
}
/*  返回一组对象，对象属性为基本类型,array为对象数组，cnt为对象属性的个数 */
function jsonStrArray(array, cnt) {
	 var dataArray = {};
	 var dataJson = new Array();
	  $.each(array,function(index,obj){
	    if(dataArray[this.name]){
	      if(!dataArray[this.name].push){
	        dataArray[this.name] = [dataArray[this.name]];
	      }
	      dataArray[this.name].push(this.value || '');
	    }else{
	      dataArray[this.name] = this.value || '';
	    }
	    
	    if((index+1)%cnt==0){
	    	dataJson.push(JSON.stringify(dataArray));
	    	dataArray = {};
	    }
	  });
	  var result='[';
	  for(var i=0;i<dataJson.length;i++){
		//alert(dataJson[i]);
		result+=dataJson[i]+',';
	  }
	  var jsa = result.substr(0,result.length-1);
	  jsa+=']'
	  return jsa;
}

Number.prototype.formatMoney = function (places, symbol, thousand, decimal) {
    places = !isNaN(places = Math.abs(places)) ? places : 2;
    symbol = symbol !== undefined ? symbol : "$";
    thousand = thousand || ",";
    decimal = decimal || ".";
    var number = this,
        negative = number < 0 ? "-" : "",
        i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "",
        j = (j = i.length) > 3 ? j % 3 : 0;
    return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
}

var getLength = function (str) {
    ///<summary>获得字符串实际长度，中文2，英文1</summary>
    ///<param name="str">要获得长度的字符串</param>
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};

/** 
 * js截取字符串，中英文都能用 
 * @param str：需要截取的字符串 
 * @param len: 需要截取的长度 
 */
function cutStr(str, len) {
    var str_length = 0;
    var str_len = 0;
    str_cut = new String();
    str_len = str.length;
    for (var i = 0; i < str_len; i++) {
        a = str.charAt(i);
        str_length++;
        if (escape(a).length > 4) {
            //中文字符的长度经编码之后大于4  
            str_length++;
        }
        str_cut = str_cut.concat(a);
        if (str_length >= len) {
            str_cut = str_cut.concat("...");
            return str_cut;
        }
    }
}