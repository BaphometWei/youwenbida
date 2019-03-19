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


function searchuser() {
    commonAjax("/admin-getUserOp",callback,{op:$("#hid_op").val(),time:$("#hid_time").val()});
    function callback(data) {
        $("tbody").html("");
        var showhtml = "";
        for(var k=0;k<data.users.length;k++){
        showhtml += "<tr><td><input type='checkbox' /></td><td><a target='_blank' href='/zhuye?id="+data.users[k].id+"'><img width='24' height='24' src='"+data.users[k].img+"' /></a></td><td><a target='_blank' href='/zhuye?id="+data.users[k].id+"'>"+data.users[k].id+"</a></td>";
        showhtml += "<td>"+data.users[k].email+"</td><td><a target='_blank' href='/zhuye?id="+data.users[k].id+"'>"+data.users[k].name+"</a></td><td>"+data.users[k].twsl+"</td><td>"+data.users[k].hdsl+"</td></tr>";
        }
        $("tbody").append(showhtml);
        $("#hid_op").val(data.op);
        $("#hid_time").val(data.time);
        $("#op").val(data.op);
        $("#time").val(data.time);
        $("#jls").html("共 "+data.users.length+" 条记录");
    }
}

function searchuserByName() {
    commonAjax("/admin-searchUser",callback,{name:$("#textOfUserName").val()});
    function callback(data) {
        $("tbody").html("");
        var showhtml = "";
        for(var k=0;k<data.users.length;k++){
            showhtml += "<tr><td><input type='checkbox' /></td><td><a target='_blank' href='/zhuye?id="+data.users[k].id+"'><img width='24' height='24' src='"+data.users[k].img+"' /></a></td><td><a target='_blank' href='/zhuye?id="+data.users[k].id+"'>"+data.users[k].id+"</a></td>";
            showhtml += "<td>"+data.users[k].email+"</td><td><a target='_blank' href='/zhuye?id="+data.users[k].id+"'>"+data.users[k].name+"</a></td><td>"+data.users[k].twsl+"</td><td>"+data.users[k].hdsl+"</td></tr>";
        }
        $("tbody").append(showhtml);
        $("#jls").html("共 "+data.users.length+" 条记录");
    }
}