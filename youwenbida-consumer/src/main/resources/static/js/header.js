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