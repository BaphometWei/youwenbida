var useropurl="/getUserOp";
var userAnsurl="/getUserAns";
var userscurl="/getUserSc";
var usertwurl="/getUserPro";
function zhuyeAjax(url,callback) {
    $.ajax({
        type: 'GET',
        url: url,
        data: {uid:getQueryString("id")},
        async: false,
        success: function (data) {
            callback(data);
        }
    })
}

function userop(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $(".bankuai").html("<h4 class='bkop'>"+$(".bankuai").find("h4").html().substring(0,1)+ "的" + $(obj).html() + "</h4>");
    zhuyeAjax(useropurl,callback);
    function callback(data) {
        $(".main-problem").html("");
        if (data.ops.length == 0) {
            $(".main-problem").html("<img style='margin-left: 220px' width='300' height='230' src='/img/nothing.png' />");
        }else {
            for (var i = 0; i < data.ops.length; i++) {
                var op = data.ops[i];
                var showhtml = "";
                if (op.olx == "3") {
                    showhtml += "<div class='problem'>";
                    showhtml += "<div class='problem-zan'>关注了该问题 <span class='odate'>" + getIntervalTime(op.odate) + "</span></div>";
                    showhtml += "<h2 class='problem-title'>" + op.problem.ptitle + "</h2>";
                    showhtml += "</div>";
                    $(".main-problem").append(showhtml);
                } else {
                    showhtml += "<div class='problem'>";
                    if (op.olx == "1")
                        showhtml += "<div class='problem-zan'>赞同了该回答 <span class='odate'>" + getIntervalTime(op.odate) + "</span></div>";
                    else
                        showhtml += "<div class='problem-zan'>收藏了该回答 <span class='odate'>" + getIntervalTime(op.odate) + "</span></div>";
                    showhtml += "<h2 class='problem-title'>" + op.answer.problem.ptitle + "</h2>";
                    showhtml += "<div class='problem-answer'><a href='/zhuye?id="+op.answer.user.id+"' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='/img/touxiang.jpg' /><span class='answername'>  " + op.answer.user.name + "</span>";
                    showhtml += "<span class='answerqm'> " + op.answer.user.gxqm + "</span></a></div><div class='problem-zan'><span>" + op.answer.aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + op.answer.ahd;
                    showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + op.answer.ahdrq + "</span></div><div class='problem-footer'>";
                    showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + op.answer.dz + "' onclick='zantong(this)' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + op.answer.aztsl + "</span></button>";
                    showhtml += "<button class='problem-pinglun' alt='" + op.answer.aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + op.answer.aplsl + "</span>条评论</button>";
                    showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + op.answer.sc + "' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button></div><div class='pinglun'></div></div>";
                    $(".main-problem").append(showhtml);
                }
            }
        }
        init();
        show();
    }
}
function userans(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $(".bankuai").html("<h4 class='bkop'>"+$(".bankuai").find("h4").html().substring(0,1)+ "的" + $(obj).html() + "</h4>");
    zhuyeAjax(userAnsurl,callback);
    function callback(data) {
        $(".main-problem").html("");
        if (data.ans.length == 0) {
            $(".main-problem").html("<img style='margin-left: 220px' width='300' height='230' src='/img/nothing.png' />");
        }else {
            for (var i = 0; i < data.ans.length; i++) {
                var answer = data.ans[i];
                var showhtml = "";
                showhtml += "<div class='problem'>";
                showhtml += "<h2 class='problem-title'>" + answer.problem.ptitle + "</h2>";
                showhtml += "<div class='problem-answer'><a href='/zhuye?id="+answer.user.id+"' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='/img/touxiang.jpg' /><span class='answername'>  " + answer.user.name + "</span>";
                showhtml += "<span class='answerqm'> " + answer.user.gxqm + "</span></a></div><div class='problem-zan'><span>" + answer.aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + answer.ahd;
                showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + answer.ahdrq + "</span></div><div class='problem-footer'>";
                showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + answer.dz + "' onclick='zantong(this)' alt='" + answer.aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + answer.aztsl + "</span></button>";
                showhtml += "<button class='problem-pinglun' alt='" + answer.aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + answer.aplsl + "</span>条评论</button>";
                showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + answer.sc + "' alt='" + answer.aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button></div><div class='pinglun'></div></div>";
                $(".main-problem").append(showhtml);
            }

        }
        init();
        show();
    }

}
function usertw(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $(".bankuai").html("<h4 class='bkop'>"+$(".bankuai").find("h4").html().substring(0,1)+ "的" + $(obj).html() + "</h4>");
    zhuyeAjax(usertwurl,callback);
    function callback(data) {
        $(".main-problem").html("");
        if (data.pros.length == 0) {
            $(".main-problem").html("<img style='margin-left: 220px' width='300' height='230' src='/img/nothing.png' />");
        }else {
            for (var i = 0; i < data.pros.length; i++) {
                var pro = data.pros[i];
                var showhtml = "";
                showhtml += "<div class='problem'>";
                showhtml += "<h2 class='problem-title'>" + pro.ptitle + "</h2>";
                showhtml += "<div class='problem-zan'>"+pro.ptcrq+"</div>";
                showhtml += "</div>";
                $(".main-problem").append(showhtml);
            }
        }
        init();
        show();
    }
}
function usersc(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    $(".bankuai").html("<h4 class='bkop'>"+$(".bankuai").find("h4").html().substring(0,1)+ "的" + $(obj).html() + "</h4>");
    zhuyeAjax(userscurl,callback);
    function callback(data) {
        $(".main-problem").html("");
        if (data.ops.length == 0) {
            $(".main-problem").html("<img style='margin-left: 220px' width='300' height='230' src='/img/nothing.png' />");
        }else {
            for (var i = 0; i < data.ops.length; i++) {
                var op = data.ops[i];
                var showhtml = "";
                showhtml += "<div class='problem'>";
                showhtml += "<div class='problem-zan'>收藏了该回答 <span class='odate'>" + getIntervalTime(op.odate) + "</span></div>";
                showhtml += "<h2 class='problem-title'>" + op.answer.problem.ptitle + "</h2>";
                showhtml += "<div class='problem-answer'><a href='/zhuye?id="+op.answer.user.id+"' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='/img/touxiang.jpg' /><span class='answername'>  " + op.answer.user.name + "</span>";
                showhtml += "<span class='answerqm'> " + op.answer.user.gxqm + "</span></a></div><div class='problem-zan'><span>" + op.answer.aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + op.answer.ahd;
                showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + op.answer.ahdrq + "</span></div><div class='problem-footer'>";
                showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + op.answer.dz + "' onclick='zantong(this)' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + op.answer.aztsl + "</span></button>";
                showhtml += "<button class='problem-pinglun' alt='" + op.answer.aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + op.answer.aplsl + "</span>条评论</button>";
                showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + op.answer.sc + "' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button></div><div class='pinglun'></div></div>";
                $(".main-problem").append(showhtml);
            }
        }
        init();
        show();
    }
}
function usergz(obj) {
    $(".navop").removeClass("navop");
    $(obj).addClass("navop");
    var showhtml = "";
    showhtml += "<a><h4 class='bkop' style='display: inline;line-height: 39px;'>" + $(".bankuai").find("h4").html().substring(0, 1) + "关注的人" + "</h4></a>";
    showhtml += "<a><h4 style='display: inline;line-height: 39px;'>"  + "关注他的人" + "</h4></a>";
    showhtml += "<a><h4 style='display: inline;line-height: 39px;margin-left: 20px'>" + $(".bankuai").find("h4").html().substring(0, 1) + "关注的问题" + "</h4></a>";
    $(".bankuai").html(showhtml);

}



