var dlzt;
var userid;
var prolayedit;
var prpgetText;
var anslayedit;
var ansgetText;

function show() {
    $(".problem-fold").each(function (i, val) {
        if ($(".controlfold").eq(i).html() == " " || $(".controlfold").eq(i).html() == "") {
            var text = $(".problem-fold")[i].innerHTML;
            var clientheight = parseInt($(".problem-fold")[i].clientHeight);
            var newBox = document.createElement("div");
            var btn = document.createElement("a");
            btn.className += "fold-button";
            btn.innerHTML = "";
            if ($(".problem-fold")[i].clientHeight > 90) {
                btn.innerHTML = "...显示全部";
                $(".problem-fold")[i].style.height = 90 + "px";
            }
            btn.onclick = function () {
                if (btn.innerHTML == "...显示全部") {
                    btn.innerHTML = "收起";
                    $(btn).parent().parent().find(".problem-fold").css("height", clientheight + "px");
                } else {
                    btn.innerHTML = "...显示全部";
                    $(btn).parent().parent().find(".problem-fold").css("height", "70px");
                }
            }
            $(".controlfold")[i].appendChild(btn);
        }
    })
}

function zantong(obj) {
    validateDlzt();
    if (dlzt == "true") {
        if ($(obj).attr('isdz') == 'true') {
            $.ajax({
                type: "GET",
                url: "/deleteDzAnswer",
                data: {aid: $(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(obj).parent().parent().find(".problem-zan").find("span").text((parseInt($(obj).find("span").text().replace("已赞同", "")) - 1).toString());
                        $(obj).find("span").text("赞同" + (parseInt($(obj).find("span").text().replace("已赞同", "")) - 1).toString());
                        $(obj).attr('isdz', 'false');
                        $(obj).css("color", "#ffffff");
                    }
                }
            })
        } else {
            $.ajax({
                type: "GET",
                url: "/dzAnswer",
                data: {aid: $(obj).attr("alt"), isfd: $(obj).next().attr("isfd")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(obj).parent().parent().find(".problem-zan").find("span").text((parseInt($(obj).find("span").text().replace("赞同", "")) + 1).toString());
                        $(obj).find("span").text("已赞同" + (parseInt($(obj).find("span").text().replace("赞同", "")) + 1).toString());
                        $(obj).attr('isdz', 'true');
                        $(obj).css("color", "yellow");
                        if ($(obj).next().attr("isfd") == "true") {
                            $(obj).next().css("color", "#ffffff");
                            $(obj).next().attr("isfd", "false");
                        }
                    }
                }
            })
        }
    } else {
        dlts();
    }
}

function fandui(obj) {
    validateDlzt();
    if (dlzt == "true") {
        if ($(obj).attr('isfd') == 'true') {
            $.ajax({
                type: "GET",
                url: "/deleteFdAnswer",
                data: {aid: $(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(obj).attr('isfd', 'false');
                        $(obj).css("color", "#ffffff");
                    }
                }
            })
        } else {
            $.ajax({
                type: "GET",
                url: "/fdAnswer",
                data: {aid: $(obj).attr("alt"), isdz: $(obj).prev().attr("isdz")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(obj).attr('isfd', 'true');
                        $(obj).css("color", "yellow");
                        if ($(obj).prev().attr("isdz") == "true") {
                            $(obj).parent().parent().find(".problem-zan").find("span").text((parseInt($(obj).prev().find("span").text().replace("已赞同", "")) - 1).toString());
                            $(obj).prev().find("span").text("赞同" + (parseInt($(obj).prev().find("span").text().replace("已赞同", "")) - 1).toString());
                            $(obj).prev().css("color", "#ffffff");
                            $(obj).prev().attr("isdz", "false");
                        }
                    }
                }
            })
        }
    } else {
        dlts();
    }
}

function shoucang(obj) {
    validateDlzt();
    if (dlzt == "true") {
        if ($(obj).attr('issc') == 'true') {
            $.ajax({
                type: "GET",
                url: "/deleteScAnswer",
                data: {aid: $(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(obj).find("i").css('color', '#8590A6');
                        $(obj).attr('issc', 'false');
                    }
                }
            })
        } else {
            $.ajax({
                type: "GET",
                url: "/scAnswer",
                data: {aid: $(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(obj).find("i").css('color', 'yellow');
                        $(obj).attr('issc', 'true');
                    }
                }
            })
        }
    } else {
        dlts();
    }
}

function dianzan(obj) {
    validateDlzt();
    if (dlzt == "true") {
        if ($(obj).attr('isdz') == 'true') {
            $.ajax({
                type: "GET",
                url: "/deleteDzComment",
                data: {cid: $(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
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
                data: {cid: $(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(obj).css("color", "yellow");
                        $(obj).find("span").text(parseInt($(obj).find("span").text()) + 1);
                        $(obj).attr("isdz", "true");
                    }
                }
            })
        }
    } else {
        dlts();
    }
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

function validateDlzt() {
    $.ajax({
        type: "GET",
        url: "/dlzt",
        async: false,
        success: function (data) {
            if (data.code == 0) {
                $(".hide").css("display", "block");
                $(".show").css("display", "none");
                commonAjax("/getWdCount", callback, {});
                function callback(data) {
                    $("#xiaoxi").html(data.wdntc);
                    $("#zhuye").attr("href", "zhuye?id=" + data.userid);
                }
                dlzt = "true";
            }else{
                dlzt = "false";
            }
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

function ts(msg) {
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.open({
            title: '提示',
            offset: '100px',
            btn: [],
            content: msg
        })
    })
}

function getIntervalTime(date) {
    var ms = new Date().getTime() - new Date(date.replace(/-/g, "/")).getTime();
    if (ms < 0) return 0;
    var x = Math.floor(ms / 1000 / 60 / 60);
    if (x > 8760) {
        return parseInt(x / 8760).toString() + " 年前";
    } else if (x > 720) {
        return parseInt(x / 720).toString() + " 个月前";
    } else if (x > 168) {
        return parseInt(x / 168).toString() + " 周前";
    } else if (x > 24) {
        return parseInt(x / 24).toString() + " 天前";
    } else if (x == 0) {
        return "刚刚";
    } else {
        return parseInt(x).toString() + " 小时前";
    }
}

function chakanhuifu(obj) {
    $.ajax({
        type: "GET",
        url: "/getCom",
        data: {cid: $(obj).attr("alt")},
        async: false,
        success: function (data) {
            layui.use('layer', function () {
                var showhtml = "";
                showhtml += "<div class='pler' style='margin-right: 10px;'><div class='pler-header'><a target='_blank' href='/zhuye?id=" + data.comment.user.id + "'><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                showhtml += "src='"+data.comment.user.img+"' /><span class='answername' >  " + data.comment.user.name + "</span></a><span class='pldate'>" + getIntervalTime(data.comment.cdate) + "</span></div><div class='pler-body'>";
                showhtml += data.comment.cpl + "</div><div class='pler-footer'>";
                showhtml += "</div></div>";
                showhtml += "<div class='hfsl'><h3><span>" + data.comment.replies.length + "</span> 条回复</h3></div>";
                for (var j = 0; j < data.comment.replies.length; j++) {
                    showhtml += "<div class='pler' style='margin-right: 10px;width: 670px;margin-left: 20px;'><div class='pler-header'><a target='_blank' href='/zhuye?id=" + data.comment.replies[j].rzuser.id + "'><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                    showhtml += "src='"+data.comment.replies[j].rzuser.img+"' /><span class='answername' >  " + data.comment.replies[j].rzuser.name + "</span></a> 回复 <a target='_blank' href='/zhuye?id="+data.comment.replies[j].rbzuser.id+"'><span class='answername' >" + data.comment.replies[j].rbzuser.name + "</span></a><span class='pldate'>" + getIntervalTime(data.comment.replies[j].rdate) + "</span></div><div class='pler-body'>";
                    showhtml += data.comment.replies[j].rr + "</div><div class='pler-footer'>";
                    showhtml += "<a class='pler-huifu' alt='hf" + data.comment.replies[j].rid + "' onclick='showhf(this)'><i class='layui-icon layui-icon-chat'> </i>回复</a>";
                    if (data.userid == data.comment.replies[j].rzuser.id)
                        showhtml += "<a class='pler-huifu' alt='" + data.comment.replies[j].rid + "' onclick='deleteop(this)' for='re'><i class='layui-icon layui-icon-delete'> </i>删除</a>";
                    showhtml += "</div><div id='hf" + data.comment.replies[j].rid + "' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='" + data.comment.cid + "' for='" + data.comment.replies[j].rid + "' lx='02' onclick='hf(this)'>回复</button></div></div>";
                }

                var layer = layui.layer;
                layer.open({
                    title: '查看对话',
                    area: ['710px', '600px'],
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
    if ($("#" + $(obj).attr("alt") + "").css("display") == "none") {
        $("#" + $(obj).attr("alt") + "").css("display", "block");
        $("#" + $(obj).attr("alt") + "").find("input").attr("placeholder", "回复 " + $(obj).parent().parent().find(".pler-header").find("span").html());
    } else {
        $("#" + $(obj).attr("alt") + "").css("display", "none");
    }
}

function hf(obj) {
    validateDlzt();
    if (dlzt == "false") {
        dlts();
    } else {
        if ($(obj).parent().find(".hf-input").val().length == 0) {
            var layer = layui.layer;
            layer.open({
                title: '提示',
                offset: '100px',
                btn: [],
                content: "内容不能为空！",
            });
        } else {
            var text = $(obj).parent().find(".hf-input").val();
            if (text.substring(0, 3) != "<p>")
                text = "<p>" + text + "</p>";
            $.ajax({
                type: "GET",
                url: "/insComRe",
                data: {rr: text, rbz: $(obj).attr("for"), alt: $(obj).attr("alt"), lx: $(obj).attr("lx")},
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
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        if ($("#guanzhu").html() == "取消关注") {
            $.ajax({
                type: "GET",
                url: "/deleteGz",
                data: {uid: $(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(obj).css("background", "#0084FF");
                        $(obj).html("<i class='layui-icon layui-icon-add-1'></i>关 注");
                    }
                }
            })
        } else {
            $.ajax({
                type: "GET",
                url: "/gz",
                data: {uid: $(obj).attr("alt")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(obj).css("background", "#8590A6");
                        $(obj).html("取消关注");
                    }
                }
            })
        }
    }
}

function guanzhupro() {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        if ($("#guanzhu").html() == "取消关注") {
            $.ajax({
                type: "GET",
                url: "/deleteGzPro",
                data: {pid: getQueryString("proid")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
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
                data: {pid: getQueryString("proid")},
                async: false,
                success: function (data) {
                    if (data.code == 0) {
                        $("#guanzhu").css("background", "#8590A6");
                        $("#guanzhu").html("取消关注");
                        $("#gzzsl").find("span").html(parseInt($("#gzzsl").find("span").html()) + 1);
                    }
                }
            })
        }
    }
}

function zhanshi() {
    if ($("#chakan").html() == ' 查看详细资料') {
        $(".userxx-body").html($(".userxx-body2").html());
        $(".layui-icon-down").addClass("layui-icon-up");
        $(".layui-icon-down").removeClass("layui-icon-down");
        $("#chakan").html(" 收起详细资料")
    } else {
        $(".userxx-body").html($(".userxx-body1").html());
        $(".layui-icon-up").addClass("layui-icon-down");
        $(".layui-icon-up").removeClass("layui-icon-up");
        $("#chakan").html(" 查看详细资料")
    }
};

function chakanpl(obj) {
    var dom = $(obj);
    var dompl = $(obj).parent().parent().find(".pinglun");
    if (dompl.css("display") == "none") {
        dompl.html("");
        $.ajax({
            type: "GET",
            url: "/getAnsCom",
            data: {aid: dom.attr('alt')},
            async: false,
            success: function (data) {
                var showhtml = "<div class='plsl'><h3><span id='plsl" + dom.attr('alt') + "'>" + dom.find("span").html() + "</span> 条评论</h3></div><div class='pinglun-body'></div>";
                showhtml += "";
                dompl.append(showhtml);
                for (var j = 0; j < data.commets.length; j++) {
                    showhtml = "";
                    showhtml += "<div class='pler'><div class='pler-header'><a target='_blank' href='/zhuye?id=" + data.commets[j].user.id + "'><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                    showhtml += "src='"+data.commets[j].user.img+"' /><span class='answername' >  " + data.commets[j].user.name + "</span></a><span class='pldate'>" + getIntervalTime(data.commets[j].cdate) + "</span></div><div class='pler-body'>";
                    showhtml += data.commets[j].cpl + "</div><div class='pler-footer'><a class='pler-dianzan' isdz='" + data.commets[j].isdz + "' onclick='dianzan(this)' alt='" + data.commets[j].cid + "'><i class='layui-icon layui-icon-praise'> </i> ";
                    showhtml += "<span>" + data.commets[j].cdzsl + "</span></a><a class='pler-chakan' onclick='chakanhuifu(this)' alt='" + data.commets[j].cid + "'><i class='layui-icon layui-icon-dialogue'>";
                    showhtml += "</i> 查看回复</a><a class='pler-huifu' alt='hf" + data.commets[j].cid + "' onclick='showhf(this)'><i class='layui-icon layui-icon-chat'> </i>回复</a>";
                    if (data.userid == data.commets[j].user.id)
                        showhtml += "<a class='pler-huifu' alt='" + data.commets[j].cid + "' for='com' onclick='deleteop(this)' hd='" + dom.attr('alt') + "'><i class='layui-icon layui-icon-delete'> </i>删除</a>";
                    showhtml += "</div><div id='hf" + data.commets[j].cid + "' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='" + data.commets[j].cid + "' for='" + data.commets[j].cid + "' lx='01' onclick='hf(this)'>回复</button></div></div>";
                    dompl.find(".pinglun-body").append(showhtml);
                }
                var aid1 = "ans1" + dom.attr('alt');
                var aid2 = "ans2" + dom.attr('alt');
                dompl.after("<div class='plhf' id='" + aid1 + "'><div class='plhf-body' id='" + aid2 + "'></div></div>")
                layui.use('layedit', function () {
                    var layedit = layui.layedit;
                    layedit.set({
                        uploadImage: {
                            url: "/imgupload", //接口url
                            type: 'post' //默认post
                        }
                    });
                    var getText = layedit.build(aid2, {
                        height: 100,  //设置编辑器高度
                    });
                    $("#" + aid1 + "").append("<button class='plfh-footer' id='" + dom.attr("alt") + "'>发表评论</button><div class='clearfix'></div>");
                    $("#" + dom.attr("alt") + "").on("click", function () {
                        validateDlzt();
                        if (dlzt == "false")
                            dlts();
                        else {
                            var text = layedit.getContent(getText);
                            if (text.length == 0) {
                                var layer = layui.layer;
                                layer.open({
                                    title: '提示',
                                    offset: '100px',
                                    btn: [],
                                    content: "内容不能为空！",
                                });
                            } else {
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
                                            content: '评论成功'
                                        });
                                        showhtml = "";
                                        showhtml += "<div class='pler'><div class='pler-header'><a><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                                        showhtml += "src='"+data.user.img+"' /><span class='answername' >  " + data.user.name + "</span></a><span class='pldate'>刚刚</span></div><div class='pler-body'>";
                                        showhtml += text + "</div><div class='pler-footer'><a class='pler-dianzan' isdz='false' onclick='dianzan(this)' alt='" + data.cid + "'><i class='layui-icon layui-icon-praise'> </i> ";
                                        showhtml += "<span>0</span></a><a class='pler-chakan' onclick='chakanhuifu(this)' alt='" + data.cid + "'><i class='layui-icon layui-icon-dialogue'>";
                                        showhtml += "</i> 查看回复</a><a class='pler-huifu' alt='hf" + data.cid + "' onclick='showhf(this)'><i class='layui-icon layui-icon-chat'> </i>回复</a></div><div id='hf" + data.cid + "' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='" + data.cid + "' for='" + data.cid + "' lx='01' onclick='hf(this)'>回复</button></div></div>";
                                        dompl.find(".pinglun-body").append(showhtml);
                                        $("#pls" + dom.attr("alt") + "").html(parseInt($("#pls" + dom.attr("alt") + "").html()) + 1);
                                        $("#plsl" + dom.attr("alt") + "").html(parseInt($("#plsl" + dom.attr("alt") + "").html()) + 1);
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
    } else {
        $(".plhf").remove();
        dompl.css("display", "none");
    }
    $(".pler-dianzan").each(function (i, val) {
        if ($(this).attr("isdz") == "true") {
            $(this).css("color", "yellow");
        } else {
            $(this).css("color", "#8590A6");
        }
    });
}

function init() {
    $(".problem-dianzan").each(function (i, val) {
        if ($(this).attr("isdz") == "true") {
            $(this).find("span").text("已赞同" + $(this).find("span").text());
            $(this).css("color", "yellow");
        }
        if ($(this).attr("isdz") == "false" && $(this).find("span").text().indexOf("赞同") == -1) {
            $(this).find("span").text("赞同" + $(this).find("span").text());
            $(this).css("color", "#ffffff");
        }
    });
    $(".problem-shoucang").each(function (i, val) {
        if ($(this).attr("issc") == "true") {
            $(this).find("i").css('color', 'yellow');
        } else {
            $(this).find("i").css('color', '#8590A6');
        }
    });
    $(".problem-fandui").each(function (i, val) {
        if ($(this).attr("isfd") == "true") {
            $(this).css("color", "yellow");
        } else {
            $(this).css("color", "#ffffff");
        }
    });
}

function ckppl() {
    $.ajax({
        type: "GET",
        url: "/getProCom",
        data: {pid: getQueryString("proid")},
        async: false,
        success: function (data) {
            var showhtml = "<div style='overflow:auto;height: 493px;overflow-x:hidden;'>";
            for (var i = 0; i < data.coms.length; i++) {
                var com = data.coms[i];
                showhtml += "<div class='pler' style='margin-right: 10px;'><div class='pler-header'><a target='_blank' href='/zhuye?id=" + com.user.id + "'><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                showhtml += "src='"+com.user.img+"' /><span class='answername' >  " + com.user.name + "</span></a><span class='pldate'>" + getIntervalTime(com.cdate) + "</span></div><div class='pler-body'>";
                showhtml += com.cpl + "</div><div class='pler-footer'>";
                showhtml += "<a class='pler-huifu' alt='h1f" + com.cid + "' onclick='showhf(this)'><i class='layui-icon layui-icon-chat'> </i>回复</a>";
                if (data.userid == com.user.id)
                    showhtml += "<a class='pler-huifu' alt='" + com.cid + "' for='com' onclick='deleteop(this)'><i class='layui-icon layui-icon-delete'> </i>删除</a>";
                showhtml += "</div><div id='h1f" + com.cid + "' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='" + com.cid + "' for='" + com.id + "' lx='01' onclick='hf(this)'>回复</button></div></div>";
                for (var j = 0; j < com.replies.length; j++) {
                    var reply = com.replies[j];
                    showhtml += "<div class='pler' style='margin-right: 20px;width: 655px;margin-left: 20px;'><div class='pler-header'><a target='_blank' href='/zhuye?id=" + reply.rzuser.id + "'><img class='Avatar AuthorInfo-avatar' width='24' height='24' ";
                    showhtml += "src='"+reply.rzuser.img+"' /><span class='answername' >  " + reply.rzuser.name + "</span></a> 回复 <a target='_blank' href='/zhuye?id="+reply.rbzuser.id+"'><span class='answername' >"+reply.rbzuser.name+"</span></a><span class='pldate'>" + getIntervalTime(reply.rdate) + "</span></div><div class='pler-body'>";
                    showhtml += reply.rr + "</div><div class='pler-footer'>";
                    showhtml += "<a class='pler-huifu' alt='h2f" + reply.rid + "' onclick='showhf(this)'><i class='layui-icon layui-icon-chat'> </i>回复</a>";
                    if (data.userid == reply.rzuser.id)
                        showhtml += "<a class='pler-huifu' alt='" + reply.rid + "' onclick='deleteop(this)' for='re'><i class='layui-icon layui-icon-delete'> </i>删除</a>";
                    showhtml += "</div><div id='h2f" + reply.rid + "' class='hf'><input class='hf-input layui-input' /><button class='hf-btn' alt='" + com.cid + "' for='" + reply.rid + "' lx='02' onclick='hf(this)'>回复</button></div></div>";
                }
            }
            showhtml += "</div><div class='plhf' style='margin-right: 10px'><div class='plhf-body' id='pid" + getQueryString("proid") + "'></div><button class='plfh-footer' onclick='hfppl()'>发表评论</button><div class='clearfix'></div></div>"
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    title: '评论',
                    area: ['710px', '680px'],
                    offset: '50px',
                    btn: [],
                    content: showhtml,
                    success: function () {
                        layui.use('layedit', function () {
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
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        var text = prolayedit.getContent(progetText);
        if (text.length == 0) {
            prolayedit.setContent(progetText, "内容不能为空!");
        }
        if (text.substring(0, 3) != "<p>")
            text = "<p>" + text + "</p>";
        $.ajax({
            type: "GET",
            url: "/insProCom",
            data: {cbpl: getQueryString("proid"), cpl: text},
            async: false,
            success: function (data1) {
                layer.open({
                    title: '提示',
                    offset: '100px',
                    btn: [],
                    content: "评论成功"
                });
                $(".propl").find("span").html(parseInt($(".propl").find("span").html()) + 1);
            }
        })
    }
}

function commonAjax(url, callback, data) {
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

function updatexx() {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        commonAjax("/updateuserxx", callback, {
            name: $("input[name='name']").val(),
            sex: $('input:radio[name="sex"]:checked').val(),
            gxqm: $("input[name='gxqm']").val(),
            location: $("input[name='location']").val(),
            industry: $("select[name='industry']").val(),
            gs: $("input[name='gs']").val(),
            education: $("input[name='education']").val(),
            introduction: $("textarea[name='introduction']").val()
        });

        function callback(data) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.open({
                    title: '提示',
                    offset: '100px',
                    btn: [],
                    content: data.msg
                })
            })
        }
    }
}

function qxsetyzhd(obj) {
    commonAjax("/yzhdop", callback, {aid: $(obj).attr("alt"), yzhd: 'f'});

    function callback(data) {
        if (data.code == 0) {
            $(obj).parent().parent().find(".yzhdbz").remove();
            $(obj).parent().parent().find(".yzbtn").attr("onclick", "setyzhd(this)");
            $(obj).parent().parent().find(".yzbtn").find("span").html("设为优质答案");
            if ($(".main-left").find(".main-problem").html() == "")
                $(".main-left").show();
            $(".main-left").find(".bankuai h4").html(parseInt($(".main-left").find(".bankuai h4").html().replace("条回答")) + 1 + "条回答");
            $(".main-left").find(".main-problem").prepend($(obj).parent().parent());
            if ($(".main-yzhd").find(".main-problem").html() == "")
                $(".main-yzhd").hide();
        }
    }
}

function setyzhd(obj) {
    commonAjax("/yzhdop", callback, {aid: $(obj).attr("alt"), yzhd: 't'});

    function callback(data) {
        if (data.code == 0) {
            $(obj).parent().parent().find(".problem-answer").prepend("<div class='problem-answer yzhdbz'><img width='20' height='20' src='/img/jiangzhang.png' /><span class='twztj'>本回答由提问者推介</span></div>");
            $(obj).parent().parent().find(".yzbtn").attr("onclick", "qxsetyzhd(this)");
            $(obj).parent().parent().find(".yzbtn").find("span").html("取消设为优质答案");
            if ($(".main-yzhd").find(".main-problem").html() == "")
                $(".main-yzhd").show();
            $(".main-yzhd").find(".main-problem").append($(obj).parent().parent());
            $(".main-left").find(".bankuai h4").html(parseInt($(".main-left").find(".bankuai h4").html().replace("条回答")) - 1 + "条回答");
            if ($(".main-left").find(".main-problem").html() == "")
                $(".main-left").hide();
        }
    }
}

function xiehuida(obj) {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else if ($(obj).attr("alt") == "false") {
        commonAjax("/getDlyh", callback, {});

        function callback(data) {
            if (data.code == 0) {
                var showhtml = "";
                showhtml += "<div class='problem-answer' style='margin-left: 15px;margin-top: 5px;'><a href='/zhuye?id=" + data.user.id + "' target='_blank'><img width='40' height='40' src='"+data.user.img+"' /><span class='answername'>  " + data.user.name + "</span>";
                showhtml += "<span class='answerqm'> " + data.user.gxqm + "</span></a></div>";
                showhtml += "<div class='plhf'><div class='plhf-body' id='" + getQueryString("proid") + "'></div><button class='plfh-footer' style='margin-right: 3px;margin-bottom: 5px' onclick='tjhd()'>提交回答</button><button class='plfh-footer' style='margin-right: 3px;margin-bottom: 5px' onclick='nmhd()'>匿名回答</button><div class='clearfix'></div></div>";
                $(".main-xhd").append(showhtml);
                layui.use('layedit', function () {
                    anslayedit = layui.layedit;
                    anslayedit.set({
                        uploadImage: {
                            url: "/imgupload", //接口url
                            type: 'post' //默认post
                        }
                    });
                    ansgetText = anslayedit.build(getQueryString("proid"));
                })
                $(obj).attr("alt", "true");
            }
        }
    }
}

function tjhd() {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else if (anslayedit.getContent(ansgetText) == "") {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '提示',
                offset: '100px',
                btn: [],
                content: "内容不能为空"
            })
        });
    } else {
        var text = anslayedit.getContent(ansgetText);
        if (text.indexOf("<p>") < 0)
            var text = "<p>" + text + "</p>";
        commonAjax("/xiehuida", callback, {ahd: text, ahdwt: getQueryString("proid")});

        function callback(data) {
            if (data.code == 0) {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.open({
                        title: '提示',
                        offset: '100px',
                        btn: [],
                        content: data.msg
                    })
                });
                commonAjax("/getAns", addAns, {aid: data.aid});

                function addAns(data) {
                    if (data.code == 0) {
                        if ($(".main-left").find(".main-problem").html() == "")
                            $(".main-left").show();
                        $(".main-left").find(".bankuai h4").html(parseInt($(".main-left").find(".bankuai h4").html().replace("条回答")) + 1 + "条回答");
                        var showhtml = "";
                        showhtml += "<div class='problem'>";
                        showhtml += "<div class='problem-answer'><a href='/zhuye?id=" + data.ans.user.id + "' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='"+data.ans.user.img+"' /><span class='answername'>  " + data.ans.user.name + "</span>";
                        showhtml += "<span class='answerqm'> " + data.ans.user.gxqm + "</span></a></div><div class='problem-zan'><span>" + data.ans.aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + data.ans.ahd;
                        showhtml += "</div><div class='controlfold'> </div><div class='data.answer-time'>发布于  <span>" + data.ans.ahdrq + "</span></div><div class='problem-footer'>";
                        showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + data.ans.dz + "' onclick='zantong(this)' alt='" + data.ans.aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + data.ans.aztsl + "</span></button>";
                        showhtml += "<button class='problem-fandui' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isfd='" + data.ans.fd + "' onclick='fandui(this)' alt='" + data.ans.aid + "'><i class='layui-icon layui-icon-tread'></i> 踩</button>";
                        showhtml += "<button class='problem-pinglun' alt='" + data.ans.aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + data.ans.aplsl + "</span>条评论</button>";
                        showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + data.ans.sc + "' alt='" + data.ans.aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button>";
                        showhtml += "</div><div class='pinglun'></div></div>";
                        $(".main-left").find(".main-problem").prepend(showhtml);
                    }
                    if ($(".ansnothing").length > 0) {
                        $(".ansnothing").parent().remove();
                    }
                    init();
                    if ($(".problem-fold img").length > 0) {
                        var load = "true";
                        $(".problem-fold img").on('load', function () {
                            if (load == "true")
                                show();
                            load = "false";
                        })
                    } else {
                        show();
                    }
                }

                anslayedit.setContent(ansgetText, "");
            }
        }
    }
}

function nmhd() {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else if (anslayedit.getContent(ansgetText) == "") {
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '提示',
                offset: '100px',
                btn: [],
                content: "内容不能为空"
            })
        });
    } else {
        var text = anslayedit.getContent(ansgetText);
        if (text.indexOf("<p>") < 0)
            var text = "<p>" + text + "</p>";
        commonAjax("/xiehuida", callback, {ahd: text, ahdwt: getQueryString("proid"),nm:'t'});

        function callback(data) {
            if (data.code == 0) {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.open({
                        title: '提示',
                        offset: '100px',
                        btn: [],
                        content: data.msg
                    })
                });
                commonAjax("/getAns", addAns, {aid: data.aid});

                function addAns(data) {
                    if (data.code == 0) {
                        if ($(".main-left").find(".main-problem").html() == "")
                            $(".main-left").show();
                        $(".main-left").find(".bankuai h4").html(parseInt($(".main-left").find(".bankuai h4").html().replace("条回答")) + 1 + "条回答");
                        var showhtml = "";
                        showhtml += "<div class='problem'>";
                        showhtml += "<div class='problem-answer'><a href='/zhuye?id=" + data.ans.user.id + "' target='_blank'><img class='Avatar AuthorInfo-avatar' width='24' height='24' src='"+data.ans.user.img+"' /><span class='answername'>  " + data.ans.user.name + "</span>";
                        showhtml += "<span class='answerqm'> " + data.ans.user.gxqm + "</span></a></div><div class='problem-zan'><span>" + data.ans.aztsl + "</span>人赞同了该回答</div><div class='problem-fold'>" + data.ans.ahd;
                        showhtml += "</div><div class='controlfold'> </div><div class='data.answer-time'>发布于  <span>" + data.ans.ahdrq + "</span></div><div class='problem-footer'>";
                        showhtml += "<button class='problem-dianzan' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isdz='" + data.ans.dz + "' onclick='zantong(this)' alt='" + data.ans.aid + "'><i class='layui-icon layui-icon-praise'></i> <span>" + data.ans.aztsl + "</span></button>";
                        showhtml += "<button class='problem-fandui' style='background: #FCC9C9;color:#ffffff;padding: 0 12px;' isfd='" + data.ans.fd + "' onclick='fandui(this)' alt='" + data.ans.aid + "'><i class='layui-icon layui-icon-tread'></i> 踩</button>";
                        showhtml += "<button class='problem-pinglun' alt='" + data.ans.aid + "' onclick='chakanpl(this)'><i class='layui-icon layui-icon-reply-fill'></i> <span>" + data.ans.aplsl + "</span>条评论</button>";
                        showhtml += "<button class='problem-shoucang' style='margin-left: 0px;' onclick='shoucang(this)' issc='" + data.ans.sc + "' alt='" + data.ans.aid + "'><i class='layui-icon layui-icon-rate-solid'></i> <span>收藏</span></button>";
                        showhtml += "</div><div class='pinglun'></div></div>";
                        $(".main-left").find(".main-problem").prepend(showhtml);
                    }
                    if ($(".ansnothing").length > 0) {
                        $(".ansnothing").parent().remove();
                    }
                    init();
                    if ($(".problem-fold img").length > 0) {
                        var load = "true";
                        $(".problem-fold img").on('load', function () {
                            if (load == "true")
                                show();
                            load = "false";
                        })
                    } else {
                        show();
                    }
                }

                anslayedit.setContent(ansgetText, "");
            }
        }
    }
}

function deleteChat(obj) {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        var dom = $(obj);
        layui.use('layer', function () {
            var layer = layui.layer;
            var index = layer.open({
                title: '提示',
                offset: '100px',
                btn: ['确定', '取消'],
                content: "确定删除?",
                yes: function () {
                    commonAjax("/deleteChat", callback, {chid: dom.attr("alt")});

                    function callback(data) {
                        if (data.code == 0) {
                            dom.parent().parent().parent().parent().remove();
                            layer.close(index);
                            ts("删除成功");
                        }
                    }
                },
                btn2: function () {
                    layer.close(index);
                }
            })
        })
    }
}

function edit(obj) {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        if ($(obj).attr("for") == "ans") {
            commonAjax("/getAns", callback, {aid: $(obj).attr("alt")});

            function callback(data) {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.open({
                        title: '编辑回答',
                        area: ['546px'],
                        offset: '100px',
                        btn: [],
                        content: "<div class='tiwen' style='margin-right: 10px;height: 280px;min-width: 520px'><div class='tiwen-header' style='display: none;'><input id='ptitle' type='text' placeholder='写下你的标题，准确地描述问题更容易得到解答'/></div><div id='tiwenedit' style='width: 512px'></div><div class='addtopic' style='margin-top: 10px'></div><button id='editans' class='layui-btn' style='float: right;background: #FCC9C9;border: 1px solid #FCC9C9;color: #ffffff;height: 30px;line-height: 28px'>更新</button></div>",
                        success: function (layero, index) {
                            layui.use('layedit', function () {
                                layedit = layui.layedit;
                                layedit.set({
                                    uploadImage: {
                                        url: "/imgupload", //接口url
                                        type: 'post' //默认post
                                    }
                                });
                                getText = layedit.build('tiwenedit', {
                                    height: 225
                                });
                                layedit.setContent(getText, data.ans.ahd);
                                $("#editans").click(function () {
                                    commonAjax("/updateAns", updateAns, {
                                        aid: $(obj).attr("alt"),
                                        ahd: layedit.getContent(getText)
                                    });

                                    function updateAns(data) {
                                        if (data.code == 0)
                                            ts("修改成功！");
                                    }
                                })
                            });
                            $(".layui-layer-content").css("width", "535px");
                            $(".layui-layer-content").css("height", "320px");
                        }
                    })
                })
            }
        }
        if ($(obj).attr("for") == "pro") {
            commonAjax("/getPro", callback, {id: $(obj).attr("alt")});

            function callback(data) {
                var layedit;
                var getText;
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.open({
                        title: '提问',
                        area: ['546px'],
                        offset: '30px',
                        btn: ['更新'],
                        content: "<div class='tiwen' style='margin-right: 10px;min-height: 420px;min-width: 520px'><div class='tiwen-header'><input style='width: 492px' id='ptitle' type='text' placeholder='写下你的标题，准确地描述问题更容易得到解答'/></div><div id='tiwenedit' style='width: 512px'><textarea id='proedit'>" + data.pms + "</textarea></div><div class='addtopic' style='margin-top: 10px'><div class='addtopic-input'><input id='inputTagator' name='inputTagator' class='inputTagator' placeholder='至少添加一个话题' /><span style='position: relative;top: 10px;color:#d4d4d4;left: 5px'>(至少添加一个话题)</span></div></div></div>",
                        success: function (layero, index) {
                            $("#ptitle").val(data.ptitle);
                            $("#inputTagator").val(data.pbq);
                            layui.use('layedit', function () {
                                layedit = layui.layedit;
                                layedit.set({
                                    uploadImage: {
                                        url: "/imgupload", //接口url
                                        type: 'post' //默认post
                                    }
                                });
                                getText = layedit.build('proedit', {
                                    height: 190, //设置编辑器高度
                                });
                            });
                            var arr = [];
                            commonAjax("/getTopic", callback, {});

                            function callback(data) {
                                for (var k = 0; k < data.topics.length; k++) {
                                    arr.push(data.topics[k].tname);
                                }
                            }

                            $('#inputTagator').tagator({
                                autocomplete: arr
                            });
                        },
                        yes: function (index, layero) {
                            if ($(".tiwen-header").find("input").val().length < 1) {
                                $(".tiwen-header").find("input").attr("placeholder", "标题不能为空");
                            } else if ($(".tiwen-header").find("input").val().length < 5) {
                                $(".tiwen-header").find("input").val("");
                                $(".tiwen-header").find("input").attr("placeholder", "标题不能少于4个字!");
                            } else if ($("#inputTagator").val().length < 1) {
                                $(".addtopic-input span").css("color", "red");
                            } else {
                                $.ajax({
                                    type: "GET",
                                    url: "/updatePro",
                                    data: {
                                        pid: $(obj).attr("alt"),
                                        ptitle: $("#ptitle").val(),
                                        pms: layedit.getContent(getText),
                                        pbq: $("#inputTagator").val()
                                    },
                                    success: function (data) {
                                        layer.open({
                                            title: '提示',
                                            offset: '100px',
                                            btn: [],
                                            content: '修改成功'
                                        })
                                    }
                                });
                            }
                        }
                    });
                });
            }
        }
    }
}

function deleteop(obj) {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        var url;
        var data;
        if ($(obj).attr("for") == "ans") {
            url = "/deleteAns";
            data = {aid: $(obj).attr("alt")};
        }
        if ($(obj).attr("for") == "pro") {
            url = "/deletePro";
            data = {id: $(obj).attr("alt")};
        }
        if ($(obj).attr("for") == "com") {
            url = "/deleteCom";
            data = {cid: $(obj).attr("alt")};
        }
        if ($(obj).attr("for") == "re") {
            url = "/deleteRe";
            data = {rid: $(obj).attr("alt")};
        }
        var dom = $(obj);
        layui.use('layer', function () {
            var layer = layui.layer;
            var index = layer.open({
                title: '提示',
                offset: '100px',
                btn: ['确定', '取消'],
                content: "确定删除?",
                yes: function () {
                    if ($(obj).attr("for") == "com") {
                        if ($(".propl").length > 0)
                            $(".propl").find("span").html(parseInt($(".propl").find("span").html()) - 1);
                        else {
                            $("#pls" + $(obj).attr("hd")).html(parseInt($("#pls" + $(obj).attr("hd")).html()) - 1);
                            $("#plsl" + $(obj).attr("hd")).html(parseInt($("#plsl" + $(obj).attr("hd")).html()) - 1);
                        }
                    }
                    commonAjax(url, callback, data);

                    function callback(data) {
                        if (data.code == 0) {
                            dom.parent().parent().remove();
                            layer.close(index);
                            ts("删除成功");
                        }
                    }
                },
                btn2: function () {
                    layer.close(index);
                }
            })
        })
    }
}

function yaoqing(obj) {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        var showhtml = "<div class='header-search' style='padding-top: 15px'><input style='width: 300px' type='text' oninput='OnInput(event)' id='ceshi' autocomplete='off'/>";
        showhtml += "<i class='layui-icon layui-icon-search' style='position:relative;top:10px;left:300px'></i>";
        showhtml += "<dl class='layui-nav-child searchuser1' id='ssjg' style='min-width: 240px;padding-top: 0px;padding-bottom: 0px;'> <div class='resou'>";
        showhtml += "</div></dl></div><div class='clearfix'></div><div class='users'></div><div><a style='float: right;margin-right: 32px' onclick='huanyquser()'>-换一批-</a></div>";
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                type:1,
                title: '邀请回答',
                area: ['380px', '440px'],
                offset: '100px',
                btn: [],
                tipsMore: true,
                content: showhtml,
                success: function () {
                    showhtml = "";
                    commonAjax("/selectRandUser", callback, {});

                    function callback(data) {
                        for (var k = 0; k < data.users.length; k++) {
                            showhtml += "<div class='user-item'><i class='layui-icon layui-icon-username'> </i> <a target='_blank' href='/zhuye?id=" + data.users[k].id + "'>" + data.users[k].name + "</a><a alt='" + data.users[k].id + "' class='yquser' onclick='yqhd(this)'>邀请</a></div>";
                        }
                        $(".users").append(showhtml);
                    }
                }
            })
        })
    }
}

function OnInput(event) {
    // alert ("The new content: " + event.target.value);
    $(".resou").html("");
    $(".searchuser1").css("display", "block");
    commonAjax("/oninputSearchUser", callback, {q: event.target.value});

    function callback(data) {
        var showhtml = "<div class='user-item'><a class='yquser' onclick='gbssmb()' style='background: #ffffff;color:#333;font-size: 12px'><i class='layui-icon layui-icon-close'></i></a></div><div class='clearfix'></div>";
        var i = 5;
        if (data.users.length > 0) {
            if (data.users.length < 5)
                i = data.users.length;
            for (var k = 0; k < i; k++) {
                showhtml += "<div class='user-item' style='width:265px'><i class='layui-icon layui-icon-username'> </i> <a target='_blank' href='/zhuye?id=" + data.users[k].id + "'>" + data.users[k].text + "</a><a alt='" + data.users[k].id + "' class='yquser' onclick='yqhd(this)'>邀请</a></div>";
            }
            $(".resou").append(showhtml);
        }
    }
}

function yqhd(obj) {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        commonAjax("/yqhd", callback, {uid: $(obj).attr("alt"), pid: getQueryString("proid")});

        function callback(data) {
            if (data.code == 0)
                layer.open({
                    type:1,
                    title: '提示',
                    offset: '100px',
                    btn: [],
                    tipsMore: true,
                    content: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邀请成功'
                })
        }
    }
}

function huanyquser() {
    $(".users").html("");
    var showhtml = "";
    commonAjax("/selectRandUser", callback, {});

    function callback(data) {
        for (var k = 0; k < data.users.length; k++) {
            showhtml += "<div class='user-item'><i class='layui-icon layui-icon-username'> </i> <a target='_blank' href='/zhuye?id=" + data.users[k].id + "'>" + data.users[k].name + "</a><a alt='" + data.users[k].id + "' class='yquser' onclick='yqhd(this)'>邀请</a></div>";
            $(".users").append(showhtml);
            showhtml = "";
        }
    }
}

function gbssmb() {
    $(".searchuser1").css("display", "none");
}


function quni(obj) {
    validateDlzt();
    if (dlzt == "false")
        dlts();
    else {
        commonAjax("/updateAns", updateAns, {
            aid: $(obj).attr("alt"),
            nm:'f'
        });

        function updateAns(data) {
            if (data.code == 0) {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.open({
                        type: 1,
                        title: '提示',
                        offset: '100px',
                        btn: [],
                        content: "&nbsp;&nbsp;&nbsp;取消匿名回答成功"
                    })
                })
                $(obj).remove();
            }
        }
    }
}