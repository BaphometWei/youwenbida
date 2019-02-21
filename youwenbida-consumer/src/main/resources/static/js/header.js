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
                area: ['536px', '400px'],
                offset: '80px',
                btn: ['发布问题'],
                content: "<div class='tiwen'><div class='tiwen-header'><img class='Avatar AuthorInfo-avatar' style='cursor: pointer;' width='40' height='40' src='/img/touxiang.jpg' /><input id='ptitle' type='text' placeholder='写下你的问题，准确地描述问题更容易得到解答'/></div><div id='tiwenedit'></div></div>",
                success: function (layero, index) {
                    layui.use('layedit', function () {
                        layedit = layui.layedit;
                        getText = layedit.build('tiwenedit', {
                            height: 200 //设置编辑器高度
                        });
                    });
                    $(".layui-layer-content").css("height", "362");
                    $(".layui-layer-content").css("height", (parseInt($(".layui-layer-content").height()) - 50).toString());
                },
                yes: function (index, layero) {
                    if ($(".tiwen-header").find("input").val().length < 1) {
                        $(".tiwen-header").find("input").attr("placeholder", "标题不能为空");
                    } else {
                        $.ajax({
                            type: "GET",
                            url: "/insertPro",
                            data: {ptitle: $("#ptitle").val(), pms: layedit.getContent(getText)},
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