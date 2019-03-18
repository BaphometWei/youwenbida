function searchtw(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $(".main-left").html("");
    commonAjax("/searchPro",callback,{q:getQueryString("q")});
    function callback(data){
        var showhtml = "";
        if(data.ans.length==0){
            showhtml +="<div class='problem' style='background: #ffffff;margin: 10px 0;'>"
            showhtml +="<img width='710' height='379' src='/img/searchnothing.jpg' />";
            showhtml +="</div>"
        }else {
            for (var k = 0; k < data.ans.length; k++) {
                showhtml += "<div class='problem' style='background: #ffffff;margin: 10px 0;'><a target='_blank' href='/problem?proid="+data.ans[k].problem.pid+"'><h2 class='problem-topic'>" + data.ans[k].problem.ptitle + "</h2></a>";
                showhtml += "<div class='problem-answer'><a href='/zhuye?id=" + data.ans[k].user.id + "' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='"+data.ans[k].user.img+"' /><span class='answername'>  " + data.ans[k].user.name + "</span>";
                showhtml += "<span class='answerqm'> " + data.ans[k].user.gxqm + "</span></a></div><div class='problem-zan'><span>" + data.ans[k].aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + data.ans[k].ahd;
                showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + data.ans[k].ahdrq + "</span></div><div class='problem-footer'>";
                showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + data.ans[k].dz + "' onclick='zantong(this)' alt='" + data.ans[k].aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + data.ans[k].aztsl + "</span></button>";
                showhtml += "<button class='problem-fandui' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isfd='" + data.ans[k].fd + "' onclick='fandui(this)' alt='" + data.ans[k].aid + "'><i class='layui-icon layui-icon-tread'></i> 踩</button>";
                showhtml += "<button class='problem-pinglun' alt='" + data.ans[k].aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + data.ans[k].aplsl + "</span>条评论</button>";
                showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + data.ans[k].sc + "' alt='" + data.ans[k].aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button></div><div class='pinglun'></div></div>";
            }
        }
        $(".main-left").append(showhtml);
    }
    init();
    if($(".problem-fold img").length >0) {
        var load = "true";
        $(".problem-fold img").on('load', function () {
            if (load == "true")
                show();
            load = "false";
        })
    }else{
        show();
    }
}
function searchhd(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $(".main-left").html("");
    commonAjax("/searchAns",callback,{q:getQueryString("q")});
    function callback(data){
        var showhtml = "";
        if(data.ans.length==0){
            showhtml +="<div class='problem' style='background: #ffffff;margin: 10px 0;'>"
            showhtml +="<img width='710' height='379' src='/img/searchnothing.jpg' />";
            showhtml +="</div>"
        }else {
            for (var k = 0; k < data.ans.length; k++) {
                showhtml += "<div class='problem' style='background: #ffffff;margin: 10px 0;'><a target='_blank' href='/problem?proid="+data.ans[k].problem.pid+"'><h2 class='problem-topic'>" + data.ans[k].problem.ptitle + "</h2></a>";
                showhtml += "<div class='problem-answer'><a href='/zhuye?id=" + data.ans[k].user.id + "' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='"+data.ans[k].user.img+"' /><span class='answername'>  " + data.ans[k].user.name + "</span>";
                showhtml += "<span class='answerqm'> " + data.ans[k].user.gxqm + "</span></a></div><div class='problem-zan'><span>" + data.ans[k].aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + data.ans[k].ahd;
                showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + data.ans[k].ahdrq + "</span></div><div class='problem-footer'>";
                showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + data.ans[k].dz + "' onclick='zantong(this)' alt='" + data.ans[k].aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + data.ans[k].aztsl + "</span></button>";
                showhtml += "<button class='problem-fandui' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isfd='" + data.ans[k].fd + "' onclick='fandui(this)' alt='" + data.ans[k].aid + "'><i class='layui-icon layui-icon-tread'></i> 踩</button>";
                showhtml += "<button class='problem-pinglun' alt='" + data.ans[k].aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + data.ans[k].aplsl + "</span>条评论</button>";
                showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + data.ans[k].sc + "' alt='" + data.ans[k].aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button></div><div class='pinglun'></div></div>";
            }
        }
        $(".main-left").append(showhtml);
    }
    init();
    if($(".problem-fold img").length >0) {
        var load = "true";
        $(".problem-fold img").on('load', function () {
            if (load == "true")
                show();
            load = "false";
        })
    }else{
        show();
    }
}
function searchuser(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $(".main-left").html("");
    commonAjax("/searchUser",callback,{q:getQueryString("q")});
    function callback(data){
        var showhtml = "";
        if(data.users.length==0){
            showhtml +="<div class='problem' style='background: #ffffff;margin: 10px 0;'>"
            showhtml +="<img width='710' height='379' src='/img/searchnothing.jpg' />";
            showhtml +="</div>"
        }else {
            for (var k = 0; k < data.users.length; k++) {
                var user = data.users[k];
                showhtml += "<div class='problem' style='border-radius:5px;background: #ffffff;margin: 10px 0;padding:15px 15px'>";
                showhtml += "<div class='uimg'><a href='zhuye?id="+user.id+"' target='_blank'><img width='80' height='80' src='"+user.img+"' /></a></div><div class='uxx'>";
                showhtml += "<div class='uname'><a href='zhuye?id="+user.id+"' target='_blank'>"+user.name+"</a></div>";
                if(user.gxqm!=null)
                    showhtml += "<div class='ugxqm'>"+user.gxqm+"</div>";
                showhtml += "<div class='uopxx'>"+user.hdsl+" 回答 · "+user.twsl+" 文章 · "+user.gzzsl+" 关注者</div></div><div class='uop'>";
                if(user.isgz==false)
                    showhtml += "<a id='guanzhu' style='margin-right:20px;padding:7px 10px;border-radius:5%;font-size:16px;font-weight:600;background: #0084FF;color:#ffffff;' onclick='guanzhu(this)' alt='"+user.id+"'><i class='layui-icon layui-icon-add-1'></i>关 注</a>";
                else
                    showhtml += "<a id='guanzhu' style='margin-right:20px;padding:7px 10px;border-radius:5%;font-size:16px;font-weight:600;background: #8590A6;color:#ffffff;' onclick='guanzhu(this)' alt='"+user.id+"'>取消关注</a>";
                showhtml += "</div><div class='clearfix'></div></div>";
            }
        }
        $(".main-left").append(showhtml);
    }
    init();
    if($(".problem-fold img").length >0) {
        var load = "true";
        $(".problem-fold img").on('load', function () {
            if (load == "true")
                show();
            load = "false";
        })
    }else{
        show();
    }
}
