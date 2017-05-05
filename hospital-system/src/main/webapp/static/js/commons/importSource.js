/**
 * Created by 全琛 on 2017/2/27.
 */
var baseScripts = ["/js/core/jquery.js",
    "/js/core/jquery-ui.min.js",
    "/js/core/bootstrap.min.js"];
var resultScripts = [];

function addScripts(extendScripts) {
    resultScripts = baseScripts.concat(extendScripts);
}

function loadScript(url,fn){
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = url;
    // alert("1:"+url);
    script.onload = script.onreadystatechange = function () {
        if(!script.readyState||'loaded'===script.readyState||'complete'===script.readyState){
            alert(url+"\n"+script.readyState);
            fn && fn();
        }
    };
    // script.src = url;
    document.head.appendChild(script);
}

function scriptResource(count,callback){
    if(count == resultScripts.length){
        callback&&callback();
    }else{
        loadScript(resultScripts[count],function(){
            // document.getElementById("test").innerHTML+=resultScripts[count]+";<br>";
            scriptResource(++count,callback);
        });
    }
}



