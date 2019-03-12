function showjj(obj) {
    // alert($(".hidder").html());
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $('.main-problem').html($(".hidder").html());
}

function showtl(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $(".main-problem").html("");
    commonAjax("/getTopicAns", showhd, {tid: getQueryString("tid")});
    function showhd(data) {
        var showhtml = "";
        for(var k=0;k<data.length;k++) {
            showhtml += "<div class='problem'><a target='_blank' href='/problem?proid="+data[k].problem.pid+"'><h2 class='problem-title'>" + data[k].problem.ptitle + "</h2></a>";
            showhtml += "<div class='problem-answer'><a href='/zhuye?id="+data[k].user.id+"' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='/img/touxiang.jpg' /><span class='answername'>  " + data[k].user.name + "</span>";
            showhtml += "<span class='answerqm'> " + data[k].user.gxqm + "</span></a></div><div class='problem-zan'><span>" + data[k].aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + data[k].ahd;
            showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + data[k].ahdrq + "</span></div><div class='problem-footer'>";
            showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + data[k].dz + "' onclick='zantong(this)' alt='" + data[k].aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + data[k].aztsl + "</span></button>";
            showhtml += "<button class='problem-fandui' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isfd='" + data[k].fd + "' onclick='fandui(this)' alt='" + data[k].aid + "'><i class='layui-icon layui-icon-tread'></i> 踩</button>";
            showhtml += "<button class='problem-pinglun' alt='" + data[k].aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span id='pls"+data[k].aid+"'>" + data[k].aplsl + "</span>条评论</button>";
            showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + data[k].sc + "' alt='" + data[k].aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button></div><div class='pinglun'></div></div>";
        }
        $(".main-problem").append(showhtml);
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

function showjh(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $(".main-problem").html("");
    commonAjax("/getHotTopicAns", showhd, {tid: getQueryString("tid")});
    function showhd(data) {
        var showhtml = "";
        for(var k=0;k<data.length;k++) {
            showhtml += "<div class='problem'><a target='_blank' href='/problem?proid="+data[k].problem.pid+"'><h2 class='problem-title'>" + data[k].problem.ptitle + "</h2></a>";
            showhtml += "<div class='problem-answer'><a href='/zhuye?id="+data[k].user.id+"' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='/img/touxiang.jpg' /><span class='answername'>  " + data[k].user.name + "</span>";
            showhtml += "<span class='answerqm'> " + data[k].user.gxqm + "</span></a></div><div class='problem-zan'><span>" + data[k].aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + data[k].ahd;
            showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + data[k].ahdrq + "</span></div><div class='problem-footer'>";
            showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + data[k].dz + "' onclick='zantong(this)' alt='" + data[k].aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + data[k].aztsl + "</span></button>";
            showhtml += "<button class='problem-fandui' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isfd='" + data[k].fd + "' onclick='fandui(this)' alt='" + data[k].aid + "'><i class='layui-icon layui-icon-tread'></i> 踩</button>";
            showhtml += "<button class='problem-pinglun' alt='" + data[k].aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span id='pls"+data[k].aid+"'>" + data[k].aplsl + "</span>条评论</button>";
            showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + data[k].sc + "' alt='" + data[k].aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button></div><div class='pinglun'></div></div>";
        }
        $(".main-problem").append(showhtml);
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