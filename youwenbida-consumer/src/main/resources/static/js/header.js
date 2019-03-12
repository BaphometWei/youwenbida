layui.use('element', function(){
    var element = layui.element;
});
$("#search-input").focus(function () {
    $(".layui-nav-child").css("display","block");
})
$("#search-input").blur(function () {
    $(".layui-nav-child").css("display","none");
})
$("#cleanhistory").mousedown(function(event){
    event.preventDefault();
    $(".searchis").remove();
});
function tiwen() {
    if(dlzt=="true") {
        var layedit;
        var getText;
        layui.use('layer', function () {
            var layer = layui.layer;
            layer.open({
                title: '提问',
                area: ['546px'],
                offset: '30px',
                btn: ['发布问题'],
                content: "<div class='tiwen' style='margin-right: 10px;min-height: 420px;min-width: 520px'><div class='tiwen-header'><input id='ptitle' type='text' placeholder='写下你的标题，准确地描述问题更容易得到解答'/></div><div id='tiwenedit' style='width: 512px'></div><div class='addtopic' style='margin-top: 10px'><div class='addtopic-input'><input id='inputTagator' name='inputTagator' class='inputTagator' placeholder='至少添加一个话题' /><span style='position: relative;top: 10px;color:#d4d4d4;left: 5px'>(至少添加一个话题)</span></div></div></div>",
                success: function (layero, index) {
                    layui.use('layedit', function () {
                        layedit = layui.layedit;
                        getText = layedit.build('tiwenedit', {
                            height: 190, //设置编辑器高度
                            width:520
                        });
                    });
                    var arr = [];
                    commonAjax("/getTopic",callback,{});
                    function callback(data){
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
                    }else if ($(".tiwen-header").find("input").val().length < 5) {
                        $(".tiwen-header").find("input").val("");
                        $(".tiwen-header").find("input").attr("placeholder", "标题不能少于4个字!");
                    }else if ($("#inputTagator").val().length < 1) {
                        $(".addtopic-input span").css("color","red");
                    }else {
                        $.ajax({
                            type: "GET",
                            url: "/insertPro",
                            data: {ptitle: $("#ptitle").val(), pms: layedit.getContent(getText), pbq:$("#inputTagator").val()},
                            success: function (data) {
                                layer.open({
                                    title: '提示',
                                    offset: '100px',
                                    btn: [],
                                    content: data.msg
                                })
                            }
                        });
                    }

                }
            });
        });
    }else{
        dlts();
    }
}

function checksearchvalue() {
    if($("#search-input").val().length==0){
        $("#search-input").attr("placeholder","搜索内容不能为空");
        return false;
    }
    return true;
}