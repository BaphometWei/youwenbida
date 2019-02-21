var dlzt;
function show()
{
    $(".problem-fold").each(function (i,val) {

        var text = $(".problem-fold")[i].innerHTML;
        var clientheight =parseInt($(".problem-fold")[i].clientHeight);
        var newBox = document.createElement("div");
        var btn = document.createElement("a");
        btn.className += "fold-button";
        btn.innerHTML = "";
        if($(".problem-fold")[i].clientHeight > 70){
            btn.innerHTML = "...显示全部";
            $(".problem-fold")[i].style.height = 70 + "px";
        }
        btn.onclick = function(){
            if(btn.innerHTML == "...显示全部")
            {
                btn.innerHTML = "收起";
                $(".problem-fold")[i].style.height = clientheight + "px";
            }
            else
            {
                btn.innerHTML = "...显示全部";
                $(".problem-fold")[i].style.height = 70 + "px";
            }
        }
        $(".controlfold")[i].appendChild(btn);
    })
}
function zantong(obj){
    if(dlzt=="true") {
        if ($(obj).attr('isdz') == 'true') {
            $(obj).find("span").text("赞同" + (parseInt($(obj).find("span").text().replace("已赞同", "")) - 1).toString());
            $(obj).attr('isdz', 'false');
        } else {
            $(obj).find("span").text("已赞同" + (parseInt($(obj).find("span").text().replace("赞同", "")) + 1).toString());
            $(obj).attr('isdz', 'true');
        }
    }else{
        dlts();
    }
}
function shoucang(obj) {
    if(dlzt=="true") {
        if ($(obj).attr('issc') == 'true') {
            $(obj).find("i").css('color', '#8590A6');
            $(obj).attr('issc', 'false');
        } else {
            $(obj).find("i").css('color', 'yellow');
            $(obj).attr('issc', 'true');
        }
    }else{
        dlts();
    }
}
function dianzan(obj){
    if(dlzt=="true") {
        if ($(obj).attr("isdz") == "true") {
            $(obj).css("color", "#8590A6");
            $(obj).find("span").text(parseInt($(obj).find("span").text()) - 1);
            $(obj).attr("isdz", "false");
        } else {
            $(obj).css("color", "yellow");
            $(obj).find("span").text(parseInt($(obj).find("span").text()) + 1);
            $(obj).attr("isdz", "true");
        }
    }else{
        dlts();
    }
}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function validateDlzt(){
    $.ajax({
        type: "GET",
        url: "/dlzt",
        async: false,
        success: function(data){
            if(data=="true"){
                $(".hide").css("display", "block");
                $(".show").css("display", "none");
            }
            dlzt = data;
        }
    });
}

function dlts() {
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.open({
            title: '提示',
            offset: '100px',
            btn: [],
            content: "请先登录！"
        })
    })
}