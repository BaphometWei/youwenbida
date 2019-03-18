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
                    showhtml += "<a target='_blank' href='/problem?proid="+op.problem.pid+"'><h2 class='problem-title'>" + op.problem.ptitle + "</h2></a>";
                    showhtml += "</div>";
                    $(".main-problem").append(showhtml);
                } else {
                    showhtml += "<div class='problem'>";
                    if (op.olx == "1")
                        showhtml += "<div class='problem-zan'>赞同了该回答 <span class='odate'>" + getIntervalTime(op.odate) + "</span></div>";
                    else
                        showhtml += "<div class='problem-zan'>收藏了该回答 <span class='odate'>" + getIntervalTime(op.odate) + "</span></div>";
                    showhtml += "<h2 class='problem-title'>" + op.answer.problem.ptitle + "</h2>";
                    showhtml += "<div class='problem-answer'><a href='/zhuye?id="+op.answer.user.id+"' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='"+op.answer.user.img+"' /><span class='answername'>  " + op.answer.user.name + "</span>";
                    showhtml += "<span class='answerqm'> " + op.answer.user.gxqm + "</span></a></div><div class='problem-zan'><span>" + op.answer.aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + op.answer.ahd;
                    showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + op.answer.ahdrq + "</span></div><div class='problem-footer'>";
                    showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + op.answer.dz + "' onclick='zantong(this)' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + op.answer.aztsl + "</span></button>";
                    showhtml += "<button class='problem-fandui' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isfd='" + op.answer.fd + "' onclick='fandui(this)' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-tread'></i> 踩</button>";
                    showhtml += "<button class='problem-pinglun' alt='" + op.answer.aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + op.answer.aplsl + "</span>条评论</button>";
                    showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + op.answer.sc + "' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button></div><div class='pinglun'></div></div>";
                    $(".main-problem").append(showhtml);
                }
            }
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
                showhtml += "<a target='_blank' href='/problem?proid="+answer.problem.pid+"'><h2 class='problem-title'>" + answer.problem.ptitle + "</h2></a>";
                showhtml += "<div class='problem-answer'><a href='/zhuye?id="+answer.user.id+"' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='"+answer.user.img+"' /><span class='answername'>  " + answer.user.name + "</span>";
                showhtml += "<span class='answerqm'> " + answer.user.gxqm + "</span></a></div><div class='problem-zan'><span>" + answer.aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + answer.ahd;
                showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + answer.ahdrq + "</span></div><div class='problem-footer'>";
                showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + answer.dz + "' onclick='zantong(this)' alt='" + answer.aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + answer.aztsl + "</span></button>";
                showhtml += "<button class='problem-fandui' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isfd='" + answer.fd + "' onclick='fandui(this)' alt='" + answer.aid + "'><i class='layui-icon layui-icon-tread'></i> 踩</button>";
                showhtml += "<button class='problem-pinglun' alt='" + answer.aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + answer.aplsl + "</span>条评论</button>";
                showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + answer.sc + "' alt='" + answer.aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button>";
                if(data.br==true) {
                    showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='edit(this)' alt='" + answer.aid + "' for='ans'><i class='layui-icon layui-icon-edit'></i> <span>编辑</span></button>";
                    showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='deleteop(this)' alt='" + answer.aid + "' for='ans'><i class='layui-icon layui-icon-delete'></i> <span>删除</span></button>";
                    if(answer.nm=="t")
                        showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='quni(this)' alt='" + answer.aid + "' for='ans'><i class='layui-icon layui-icon-menu-fill'></i> <span>取匿</span></button>";
                }
                showhtml += "</div><div class='pinglun'></div></div>";
                $(".main-problem").append(showhtml);
            }

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
                showhtml += "<a target='_blank' href='/problem?proid="+pro.pid+"'><h2 class='problem-title'>" + pro.ptitle + "</h2></a>";
                showhtml += "<div class='problem-zan'>"+pro.ptcrq;
                if(data.br==true) {
                    showhtml += "<button class='problem-shoucang' style='margin-left: 10px;' onclick='edit(this)' alt='" + pro.pid + "' for='pro'><i class='layui-icon layui-icon-edit'></i> <span>编辑</span></button>";
                    showhtml += "<button class='problem-shoucang' style='margin-left: 10px;' onclick='deleteop(this)' alt='" + pro.pid + "' for='pro'><i class='layui-icon layui-icon-delete'></i> <span>删除</span></button>";
                }
                showhtml += "</div></div>";
                $(".main-problem").append(showhtml);
            }
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
                showhtml += "<a target='_blank' href='/problem?proid="+op.answer.problem.pid+"'><h2 class='problem-title'>" + op.answer.problem.ptitle + "</h2></a>";
                showhtml += "<div class='problem-answer'><a href='/zhuye?id="+op.answer.user.id+"' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='"+op.answer.user.img+"' /><span class='answername'>  " + op.answer.user.name + "</span>";
                showhtml += "<span class='answerqm'> " + op.answer.user.gxqm + "</span></a></div><div class='problem-zan'><span>" + op.answer.aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + op.answer.ahd;
                showhtml += "</div><div class='controlfold'> </div><div class='answer-time'>发布于  <span>" + op.answer.ahdrq + "</span></div><div class='problem-footer'>";
                showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + op.answer.dz + "' onclick='zantong(this)' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + op.answer.aztsl + "</span></button>";
                showhtml += "<button class='problem-fandui' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isfd='" + op.answer.fd + "' onclick='fandui(this)' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-tread'></i> 踩</button>";
                showhtml += "<button class='problem-pinglun' alt='" + op.answer.aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + op.answer.aplsl + "</span>条评论</button>";
                showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + op.answer.sc + "' alt='" + op.answer.aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button></div><div class='pinglun'></div></div>";
                $(".main-problem").append(showhtml);
            }
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
}
function usergz(obj) {
    $(".navop").removeClass("navop");
    $("#gzxx").addClass("navop");
    var showhtml = "";
    showhtml += "<a onclick='usergz(this)'><h4 class='bkgzop' style='padding: 10px 20px;display: inline;line-height: 39px;'>" + $(".bankuai").find("h4").html().substring(0, 1) + "关注的人" + "</h4></a>";
    showhtml += "<a onclick='userbgz(this)'><h4 style='padding: 10px 20px;display: inline;line-height: 39px;'>"  + "关注他的人" + "</h4></a>";
    showhtml += "<a onclick='gztw(this)'><h4 style='padding: 10px 20px;display: inline;line-height: 39px;'>" + $(".bankuai").find("h4").html().substring(0, 1) + "关注的问题" + "</h4></a>";
    $(".bankuai").html(showhtml);
    showhtml = "";
    zhuyeAjax("/getUserGz",callback);
    function callback(data) {
        $(".main-problem").html("");
        if (data.users.length == 0) {
            $(".main-problem").html("<img style='margin-left: 220px' width='300' height='230' src='/img/nothing.png' />");
        }else {
            for (var k = 0; k < data.users.length; k++) {
                var user = data.users[k];
                showhtml += "<div class='problem' style='border-radius:5px;background: #ffffff;padding:15px 15px'>";
                showhtml += "<div class='uimg'><a href='zhuye?id="+user.id+"' target='_blank'><img width='80' height='80' src='"+user.img+"' /></a></div><div class='uxx'>";
                showhtml += "<div class='uname'><a href='zhuye?id="+user.id+"' target='_blank'>"+user.name+"</a></div>";
                if(user.gxqm!=null)
                    showhtml += "<div class='ugxqm'>"+user.gxqm+"</div>";
                showhtml += "<div class='uopxx'>"+user.hdsl+" 回答 · "+user.twsl+" 提问 · "+user.gzzsl+" 关注者</div></div><div class='uop'>";
                if(user.isgz==false)
                    showhtml += "<a id='guanzhu' style='margin-right:20px;padding:7px 10px;border-radius:5%;font-size:16px;font-weight:600;background: #0084FF;color:#ffffff;' onclick='guanzhu(this)' alt='"+user.id+"'><i class='layui-icon layui-icon-add-1'></i>关 注</a>";
                else
                    showhtml += "<a id='guanzhu' style='margin-right:20px;padding:7px 10px;border-radius:5%;font-size:16px;font-weight:600;background: #8590A6;color:#ffffff;' onclick='guanzhu(this)' alt='"+user.id+"'>取消关注</a>";
                showhtml += "</div><div class='clearfix'></div></div>";
            }
        }
        $(".main-problem").append(showhtml);
    }
}
function userbgz(obj) {
    $(".bkgzop").removeClass("bkgzop");
    $(obj).find("h4").addClass("bkgzop");
    var showhtml = "";
    zhuyeAjax("/getGzUser",callback);
    function callback(data) {
        $(".main-problem").html("");
        if (data.users.length == 0) {
            $(".main-problem").html("<img style='margin-left: 220px' width='300' height='230' src='/img/nothing.png' />");
        }else {
            for (var k = 0; k < data.users.length; k++) {
                var user = data.users[k];
                showhtml += "<div class='problem' style='border-radius:5px;background: #ffffff;padding:15px 15px'>";
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
        $(".main-problem").append(showhtml);
    }
}

function gztw(obj) {
    $(".bkgzop").removeClass("bkgzop");
    $(obj).find("h4").addClass("bkgzop");
    var showhtml = "";
    zhuyeAjax("/getGzTw",callback);
    function callback(data) {
        $(".main-problem").html("");
        if (data.pros.length == 0) {
            $(".main-problem").html("<img style='margin-left: 220px' width='300' height='230' src='/img/nothing.png' />");
        }else {
            for (var k = 0; k < data.pros.length; k++) {
                var pro = data.pros[k];
                showhtml += "<div class='problem'>";
                showhtml += "<a target='_blank' href='/problem?proid="+pro.pid+"'><h2 class='problem-title'>" + pro.ptitle + "</h2></a>";
                showhtml += "<div class='uopxx'>"+pro.ptcrq+" 回答 · "+pro.pgzzsl+" 个回答 · "+pro.phdsl+" 个关注</div>";
                showhtml += "</div>";
            }
        }
        $(".main-problem").append(showhtml);
    }
}




