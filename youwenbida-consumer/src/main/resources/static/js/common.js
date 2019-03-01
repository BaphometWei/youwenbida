var dlzt;
var prolayedit;
var prpgetText;
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
            $.ajax({
                type: "GET",
                url: "/deleteDzAnswer",
                data: {aid:$(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if(data.code==0){
                        $(obj).parent().parent().find(".problem-zan").find("span").text((parseInt($(obj).find("span").text().replace("已赞同", "")) - 1).toString());
                        $(obj).find("span").text("赞同" + (parseInt($(obj).find("span").text().replace("已赞同", "")) - 1).toString());
                        $(obj).attr('isdz', 'false');
                    }
                }
            })
        } else {
            $.ajax({
                type: "GET",
                url: "/dzAnswer",
                data: {aid:$(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if(data.code==0){
                        $(obj).parent().parent().find(".problem-zan").find("span").text((parseInt($(obj).find("span").text().replace("赞同", "")) + 1).toString());
                        $(obj).find("span").text("已赞同" + (parseInt($(obj).find("span").text().replace("赞同", "")) + 1).toString());
                        $(obj).attr('isdz', 'true');
                    }
                }
            })
        }
    }else{
        dlts();
    }
}
function shoucang(obj) {
    if(dlzt=="true") {
        if ($(obj).attr('issc') == 'true') {
            $.ajax({
                type: "GET",
                url: "/deleteScAnswer",
                data: {aid:$(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if(data.code==0){
                        $(obj).find("i").css('color', '#8590A6');
                        $(obj).attr('issc', 'false');
                    }
                }
            })
        } else {
            $.ajax({
                type: "GET",
                url: "/scAnswer",
                data: {aid:$(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if(data.code==0){
                        $(obj).find("i").css('color', 'yellow');
                        $(obj).attr('issc', 'true');
                    }
                }
            })
        }
    }else{
        dlts();
    }
}
function dianzan(obj){
    if(dlzt=="true") {
        if ($(obj).attr('isdz') == 'true') {
            $.ajax({
                type: "GET",
                url: "/deleteDzComment",
                data: {cid:$(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if(data.code==0){
                        $(obj).css("color", "#8590A6");
                        $(obj).find("span").text(parseInt($(obj).find("span").text()) - 1);
                        $(obj).attr("isdz", "false");
                    }
                }
            })
        } else {
            $.ajax({
                type: "GET",
                url: "/dzComment",
                data: {cid:$(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if(data.code==0){
                        $(obj).css("color", "yellow");
                        $(obj).find("span").text(parseInt($(obj).find("span").text()) + 1);
                        $(obj).attr("isdz", "true");
                    }
                }
            })
        }
    }else{
        dlts();
    }
}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
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

function getIntervalTime(date) {
    var ms = new Date().getTime() - new Date(date.replace(/-/g,"/")).getTime();
    if (ms < 0) return 0;
    var x = Math.floor(ms/1000/60/60);
    if(x>8760){
        return parseInt(x/8760).toString()+" 年前";
    }else if(x>720){
        return parseInt(x/720).toString()+" 个月前";
    }else if(x>168){
        return parseInt(x/168).toString()+" 周前";
    }else if(x>24){
        return parseInt(x/24).toString()+" 天前";
    }else if(x==0){
        return "刚刚";
    }else{
        return parseInt(x).toString()+" 小时前";
    }
}

function chakanhuifu(obj){
    $.ajax({
        type: "GET",
        url: "/getCom",
        data: {cid:$(obj).attr("alt")},
        async: false,
        success: function (data) {
            layui.use('layer', function(){
                var showhtml = "";
                showhtml += "<div class='pler' style='margin-right: 10px;'><div class='pler-header'><a><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                showhtml += "src='/img/touxiang.jpg' /><span class='answername' >  "+data.user.name+"</span></a><span class='pldate'>"+getIntervalTime(data.cdate)+"</span></div><div class='pler-body'>";
                showhtml += data.cpl+"</div><div class='pler-footer'>";
                showhtml += "<a class='pler-huifu' alt='hf"+data.cid+"'><i class='layui-icon layui-icon-chat'> </i>回复</a></div><div id='hf"+data.cid+"' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='"+data.cid+"' for='"+data.user.id+"'>回复</button></div></div>";
                showhtml += "<div class='hfsl'><h3><span>"+data.replies.length+"</span> 条回复</h3></div>";
                for(var j=0;j<data.replies.length;j++){
                    showhtml += "<div class='pler' style='margin-right: 10px;width: 670px;margin-left: 20px;'><div class='pler-header'><a><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                    showhtml += "src='/img/touxiang.jpg' /><span class='answername' >  "+data.replies[j].rzuser.name+"</span> 回复 <span class='hfanswer'>"+data.replies[j].rbzuser.name+"</span></a><span class='pldate'>"+getIntervalTime(data.replies[j].rdate)+"</span></div><div class='pler-body'>";
                    showhtml += data.replies[j].rr+"</div><div class='pler-footer'>";
                    showhtml += "<a class='pler-huifu' alt='hf"+data.replies[j].rid+"'><i class='layui-icon layui-icon-chat'> </i>回复</a></div><div id='hf"+data.replies[j].rid+"' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='"+data.replies[j].rid+"' for='"+data.replies[j].rzuser.id+"'>回复</button></div></div>";
                    }

                var layer = layui.layer;
                layer.open({
                    title: '查看对话',
                    area: ['710px','600px'],
                    offset: '50px',
                    btn: [],
                    content: showhtml,
                    // content: "<div class='pler'><div class='pler-header'><a><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='/img/touxiang.jpg' /><span class='answername'>  夏木</span></span></a></div><div class='pler-body'><p>jgijgijgkajdojhiodjgkdjakjgkdjaksjhkejfaijgjgjksjgijdfiohajdksjgaidsjijjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjagiajfksjgggggggggkdfjgggggggggggggggggglskjg</p><p>jgijgj啊啊啊啊啊啊啊啊啊啊啊7gugyfgfg啊啊啊ijgijgij以及高级挤公交四大金刚卡萨就赶快啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊</p></div><div class='pler-footer'><a class='pler-huifu'><i class='layui-icon layui-icon-chat'> </i>回复</a></div></div><div class='hfsl'><h3><span>834</span> 条回复</h3></div><div class='pler'><div class='pler-header'><a><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='/img/touxiang.jpg' /><span class='answername'>  夏木</span></a> 回复 <span class='hfanswer'>夏木</span></div><div class='pler-body'><p>jgijgijgkajdojhiodjgkdjakjgkdjaksjhkejfaijgjgjksjgijdfiohajdksjgaidsjijjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjagiajfksjgggggggggkdfjgggggggggggggggggglskjg</p><p>jgijgj啊啊啊啊啊啊啊啊啊啊啊7gugyfgfg啊啊啊ijgijgij以及高级挤公交四大金刚卡萨就赶快啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊</p></div><div class='pler-footer'><a class='pler-huifu'><i class='layui-icon layui-icon-chat'> </i>回复</a></div></div>"
                });
            });
        }
    })
}

function showhf(obj) {
    if($("#"+$(obj).attr("alt")+"").css("display")=="none") {
        $("#" + $(obj).attr("alt") + "").css("display", "block");
        $("#" + $(obj).attr("alt") + "").find("input").attr("placeholder", "回复 " + $(obj).parent().parent().find(".pler-header").find("span").html());
    }else{
        $("#" + $(obj).attr("alt") + "").css("display", "none");
    }
}

function hf(obj) {
    if(dlzt=="false"){
        dlts();
    }else{
        if($(obj).parent().find(".hf-input").val().length==0){
            var layer = layui.layer;
            layer.open({
                title: '提示',
                offset: '100px',
                btn: [],
                content: "内容不能为空！",
            });
        }else{
            var text = $(obj).parent().find(".hf-input").val();
            if (text.substring(0, 3) != "<p>")
                text = "<p>" + text + "</p>";
            $.ajax({
                type: "GET",
                url: "/insComRe",
                data: {rr: text, rbz: $(obj).attr("for"), rbc:$(obj).attr("alt")},
                async: false,
                success: function (data) {
                    var layer = layui.layer;
                    layer.open({
                        title: '提示',
                        offset: '100px',
                        btn: [],
                        content: data.msg
                    });
                }
            })
            $(obj).parent().find(".hf-input").val("");
        }
    }
}

function guanzhu(obj) {
    if(dlzt=="false")
        dlts();
    else {
        if ($("#guanzhu").html() == "取消关注") {
            $.ajax({
                type: "GET",
                url: "/deleteGz",
                data: {uid:$(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if(data.code==0) {
                        $(obj).css("background", "#0084FF");
                        $(obj).html("<i class='layui-icon layui-icon-add-1'></i>关 注");
                    }
                }
            })
        } else {
            $.ajax({
                type: "GET",
                url: "/gz",
                data: {uid:$(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if(data.code==0) {
                        $(obj).css("background", "#8590A6");
                        $(obj).html("取消关注");
                    }
                }
            })
        }
    }
}
function guanzhupro() {
    if(dlzt=="false")
        dlts();
    else {
        if ($("#guanzhu").html() == "取消关注") {
            $.ajax({
                type: "GET",
                url: "/deleteGzPro",
                data: {pid:getQueryString("proid")},
                async: false,
                success: function (data) {
                    if(data.code==0) {
                        $("#guanzhu").css("background", "#0084FF");
                        $("#guanzhu").html("<i class='layui-icon layui-icon-add-1'></i>关注问题");
                        $("#gzzsl").find("span").html(parseInt($("#gzzsl").find("span").html()) - 1);
                    }
                }
            })
        } else {
            $.ajax({
                type: "GET",
                url: "/gzPro",
                data: {pid:getQueryString("proid")},
                async: false,
                success: function (data) {
                    if(data.code==0) {
                        $("#guanzhu").css("background", "#8590A6");
                        $("#guanzhu").html("取消关注");
                        $("#gzzsl").find("span").html(parseInt($("#gzzsl").find("span").html()) + 1);
                    }
                }
            })
        }
    }
}
function zhanshi(){
    if($("#chakan").html()==' 查看详细资料'){
        $(".userxx-body").html($(".userxx-body2").html());
        $(".layui-icon-down").addClass("layui-icon-up");
        $(".layui-icon-down").removeClass("layui-icon-down");
        $("#chakan").html(" 收起详细资料")
    }else{
        $(".userxx-body").html($(".userxx-body1").html());
        $(".layui-icon-up").addClass("layui-icon-down");
        $(".layui-icon-up").removeClass("layui-icon-up");
        $("#chakan").html(" 查看详细资料")
    }
};

function chakanpl(obj) {
    var dom = $(obj);
    var dompl = $(obj).parent().parent().find(".pinglun");
    if(dompl.css("display")=="none") {
        dompl.html("");
        $.ajax({
            type: "GET",
            url: "/getAnsCom",
            data: {aid:dom.attr('alt')},
            async: false,
            success: function (data) {
                var showhtml = "<div class='plsl'><h3><span>"+dom.find("span").html()+"</span> 条评论</h3></div><div class='pinglun-body'></div>";
                showhtml += "";
                dompl.append(showhtml);
                for(var j=0;j<data.length;j++){
                    showhtml = "";
                    showhtml += "<div class='pler'><div class='pler-header'><a><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                    showhtml += "src='/img/touxiang.jpg' /><span class='answername' >  "+data[j].user.name+"</span></a><span class='pldate'>"+getIntervalTime(data[j].cdate)+"</span></div><div class='pler-body'>";
                    showhtml += data[j].cpl+"</div><div class='pler-footer'><a class='pler-dianzan' isdz='"+data[j].isdz+"' onclick='dianzan(this)' alt='"+data[j].cid+"'><i class='layui-icon layui-icon-praise'> </i> ";
                    showhtml += "<span>"+data[j].cdzsl+"</span></a><a class='pler-chakan' onclick='chakanhuifu(this)' alt='"+data[j].cid+"'><i class='layui-icon layui-icon-dialogue'>";
                    showhtml += "</i> 查看回复</a><a class='pler-huifu' alt='hf"+data[j].cid+"' onclick='showhf(this)'><i class='layui-icon layui-icon-chat'> </i>回复</a></div><div id='hf"+data[j].cid+"' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='"+data[j].cid+"' for='"+data[j].user.id+"' onclick='hf(this)'>回复</button></div></div>";
                    dompl.find(".pinglun-body").append(showhtml);
                }
                var aid1 = "ans1"+dom.attr('alt');
                var aid2 = "ans2"+dom.attr('alt');
                dompl.after("<div class='plhf' id='"+aid1+"'><div class='plhf-body' id='"+aid2+"'></div></div>")
                layui.use('layedit', function(){
                    var layedit = layui.layedit;
                    var getText = layedit.build(aid2, {
                        height: 42,  //设置编辑器高度
                    });
                    $("#"+aid1+"").append("<button class='plfh-footer' id='"+dom.attr("alt")+"'>发表评论</button><div class='clearfix'></div>");
                    $("#"+dom.attr("alt")+"").on("click",function () {
                        if(dlzt=="false")
                            dlts();
                        else{
                            var text = layedit.getContent(getText);
                            if(text.length==0){
                                var layer = layui.layer;
                                layer.open({
                                    title: '提示',
                                    offset: '100px',
                                    btn: [],
                                    content: "内容不能为空！",
                                });
                            }else {
                                if (text.substring(0, 3) != "<p>")
                                    text = "<p>" + text + "</p>";
                                $.ajax({
                                    type: "GET",
                                    url: "/insertAnsCom",
                                    data: {cbpl: dom.attr('alt'), cpl: text},
                                    async: false,
                                    success: function (data) {
                                        var layer = layui.layer;
                                        layer.open({
                                            title: '提示',
                                            offset: '100px',
                                            btn: [],
                                            content: data.msg
                                        });
                                        showhtml = "";
                                        showhtml += "<div class='pler'><div class='pler-header'><a><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                                        showhtml += "src='/img/touxiang.jpg' /><span class='answername' >  "+data.user.name+"</span></a><span class='pldate'>刚刚</span></div><div class='pler-body'>";
                                        showhtml += text+"</div><div class='pler-footer'><a class='pler-dianzan' isdz='false' onclick='dianzan(this)' alt='"+data.cid+"'><i class='layui-icon layui-icon-praise'> </i> ";
                                        showhtml += "<span>0</span></a><a class='pler-chakan' onclick='chakanhuifu(this)' alt='"+data.cid+"'><i class='layui-icon layui-icon-dialogue'>";
                                        showhtml += "</i> 查看回复</a><a class='pler-huifu' alt='hf"+data.cid+"' onclick='showhf(this)'><i class='layui-icon layui-icon-chat'> </i>回复</a></div><div id='hf"+data.cid+"' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='"+data.cid+"' for='"+data.user.id+"' onclick='hf(this)'>回复</button></div></div>";
                                        dompl.find(".pinglun-body").append(showhtml);
                                        layedit.setContent(getText, "");
                                    }
                                })
                            }
                        }
                    })
                });
            }
        })
        dompl.css("display", "block");
    }else {
        $(".plhf").remove();
        dompl.css("display", "none");
    }
    $(".pler-dianzan").each(function (i,val) {
        if($(this).attr("isdz")=="true"){
            $(this).css("color","yellow");
        }else{
            $(this).css("color","#8590A6");
        }
    });
}

function init() {
    $(".problem-dianzan").each(function (i,val) {
        if($(this).attr("isdz")=="true"){
            $(this).find("span").text("已赞同"+$(this).find("span").text());
        }else{
            $(this).find("span").text("赞同"+$(this).find("span").text());
        }
    });
    $(".problem-shoucang").each(function (i,val) {
        if($(this).attr("issc")=="true"){
            $(this).find("i").css('color','yellow');
        }else{
            $(this).find("i").css('color','#8590A6');
        }
    });
}

function ckppl(){
    $.ajax({
        type: "GET",
        url: "/getProCom",
        data: {pid:getQueryString("proid")},
        async: false,
        success: function (data) {
            var showhtml = "<div style='overflow:auto;height: 493px;overflow-x:hidden;'>";
            for(var i=0;i<data.coms.length;i++){
                var com = data.coms[i];
                showhtml += "<div class='pler' style='margin-right: 10px;'><div class='pler-header'><a><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                showhtml += "src='/img/touxiang.jpg' /><span class='answername' >  "+com.user.name+"</span></a><span class='pldate'>"+getIntervalTime(com.cdate)+"</span></div><div class='pler-body'>";
                showhtml += com.cpl+"</div><div class='pler-footer'>";
                showhtml += "<a class='pler-huifu' alt='hf"+com.cid+"'><i class='layui-icon layui-icon-chat'> </i>回复</a></div><div id='hf"+com.cid+"' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='"+com.cid+"' for='"+com.user.id+"'>回复</button></div></div>";
                for(var j=0;j<com.replies.length;j++){
                    var reply = com.replies[j];
                    showhtml += "<div class='pler' style='margin-right: 20px;width: 655px;margin-left: 20px;'><div class='pler-header'><a><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                    showhtml += "src='/img/touxiang.jpg' /><span class='answername' >  "+reply.rzuser.name+"</span></a> 回复 <span class='hfanswer'>"+reply.rbzuser.name+"</span><span class='pldate'>"+getIntervalTime(reply.rdate)+"</span></div><div class='pler-body'>";
                    showhtml += reply.rr+"</div><div class='pler-footer'>";
                    showhtml += "<a class='pler-huifu' alt='hf"+reply.rid+"'><i class='layui-icon layui-icon-chat'> </i>回复</a></div><div id='hf"+reply.rid+"' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='"+reply.rid+"' for='"+reply.rzuser.id+"'>回复</button></div></div>";
                }
            }
            showhtml += "</div><div class='plhf' style='margin-right: 10px'><div class='plhf-body' id='pid"+getQueryString("proid")+"'></div><button class='plfh-footer' onclick='hfppl()'>发表评论</button><div class='clearfix'></div></div>"
            layui.use('layer', function() {
                var layer = layui.layer;
                layer.open({
                    title: '评论',
                    area: ['710px','680px'],
                    offset: '50px',
                    btn: [],
                    content: showhtml,
                    success: function () {
                        layui.use('layedit', function() {
                             prolayedit = layui.layedit;
                             progetText = prolayedit.build("pid" + getQueryString("proid"), {
                                height: 42,  //设置编辑器高度
                            });
                        })

                    }
                });
            })

        }
    })
}
function hfppl() {
    if(dlzt=="false")
        dlts();
    else {
        var text = prolayedit.getContent(progetText);
        if(text.length==0){
            prolayedit.setContent(progetText,"内容不能为空!");
        }
        if(text.substring(0,3)!="<p>")
            text = "<p>" + text + "</p>";
        $.ajax({
            type: "GET",
            url: "/insProCom",
            data: {cbpl: getQueryString("proid"),cpl: text},
            async: false,
            success: function (data1) {
                layer.open({
                    title: '提示',
                    offset: '100px',
                    btn: [],
                    content: data1.msg
                });
                $(".propl").find("span").html(parseInt($(".propl").find("span").html())+1);
            }
        })
    }
}

function commonAjax(url,callback,data) {
    $.ajax({
        type: 'GET',
        url: url,
        data: data,
        async: false,
        success: function (data) {
            callback(data);
        }
    })
}