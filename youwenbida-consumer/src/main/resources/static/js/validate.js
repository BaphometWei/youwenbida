var validatesignup;
function customBeforesignup(){
    var msg = "";

    if($("#signupre-password").val()!=$("#signuppassword").val())
        msg="输入密码不一致！";
    msg = checkpwd(msg);
    msg = checkname(msg);
    if(validatesignup==1)
        msg="账号已存在！";
    if($("#signupemail").val().length==0)
        msg="邮箱不能为空！";
    return showvalidate(msg);
}
$("#signupemail").blur(function () {
    if($("#signupemail").val()!="") {
        if(!checkemail())
            showvalidate("邮箱格式错误");
        else {
            $.ajax({
                type: "GET",
                url: "/validateSignup",
                data: {email: $("#signupemail").val()},
                success: function (data) {
                    if (data.code == 1) {
                        $("#validate").removeClass("alert-success");
                        $("#validate").addClass("alert-fail");
                    } else {
                        $("#validate").removeClass("alert-fail");
                        $("#validate").addClass("alert-success");
                    }
                    $("#validate").css('display', "block");
                    $("#validate").html(data.msg);
                    validatesignup = data.code;
                }
            });
        }
    }
})

function checkpwd(msg){
    if($("#signuppassword").val().length==0)
        return "密码不能为空！";
    var reg = /^[\w]{6,16}$/i;
    if(!reg.test($("#signuppassword").val()))
        return "密码长度为6-16位，只能包含字母、数字和下划线！";

    return msg;
}

function checkname(msg) {
    if($("#signupname").val().length==0)
        return "用户名不能为空！";
    var reg = /^[\u4E00-\u9FA5a-zA-Z0-9_]{3,10}$/;
    if(!reg.test($("#signupname").val()))
        return "用户名长度为3-10位，只能包含中文、英文字母、数字和下划线！";

    return msg;
}

function checkemail() {
    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    return reg.test($("#signupemail").val());
}

function showvalidate(msg) {
    if(msg!=""){
        $("#validate").removeClass("alert-success");
        $("#validate").addClass("alert-fail");
        $("#validate").css('display', "block");
        $("#validate").html(msg);
        return false;
    }
    return true;
}