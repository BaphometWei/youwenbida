function commonAjax(url, callback, data) {
	$.ajax({
		type : 'GET',
		url : url,
		data : data,
		async : false,
		success : function(data) {
			callback(data);
		}
	})
}

function searchuser() {
	commonAjax("/admin-getUserOp", callback, {
		op : $("#hid_op").val(),
		time : $("#hid_time").val()
	});
	function callback(data) {
		$("tbody").html("");
		var showhtml = "";
		for (var k = 0; k < data.users.length; k++) {
			showhtml += "<tr><td><input type='checkbox' /></td><td><a target='_blank' href='/zhuye?id="
					+ data.users[k].id
					+ "'><img width='24' height='24' src='"
					+ data.users[k].img
					+ "' /></a></td><td><a target='_blank' href='/zhuye?id="
					+ data.users[k].id + "'>" + data.users[k].id + "</a></td>";
			showhtml += "<td>" + data.users[k].email
					+ "</td><td><a target='_blank' href='/zhuye?id="
					+ data.users[k].id + "'>" + data.users[k].name
					+ "</a></td><td>" + data.users[k].twsl + "</td><td>"
					+ data.users[k].hdsl + "</td></tr>";
		}
		$("tbody").append(showhtml);
		$("#hid_op").val(data.op);
		$("#hid_time").val(data.time);
		$("#op").val(data.op);
		$("#time").val(data.time);
		$("#jls").html("共 " + data.users.length + " 条记录");
	}
}

function searchtw() {
	commonAjax("/admin-getTw", callback, {
		op : $("#hid_op").val(),
		time : $("#hid_time").val()
	});
	function callback(data) {
		$("tbody").html("");
		var showhtml = "";
		for (var k = 0; k < data.pros.length; k++) {
			showhtml += "<tr><td><input type='checkbox' /></td><td><a target='_blank' href='/pro?proid="
					+ data.pros[k].pid
					+ "'>"
					+ data.pros[k].pid
					+ "</a></td><td><a target='_blank' href='/pro?proid="
					+ data.pros[k].pid
					+ "'>"
					+ data.pros[k].ptitle
					+ "</a></td>";
			showhtml += "<td><a target='_blank' href='/zhuye?id="
					+ data.pros[k].user.id
					+ "'>"
					+ data.pros[k].user.name
					+ "</a></td><td>"
					+ data.pros[k].ptcrq
					+ "</td><td>"
					+ data.pros[k].pbq
					+ "</td><td>"
					+ data.pros[k].phdsl
					+ "</td><td>"
					+ data.pros[k].pgzzsl
					+ "</td><td><button class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' dlx='tw' alt='"
					+ data.pros[k].pid
					+ "' onclick='admindelete(this)'><span class='am-icon-trash-o'></span> 删除</button></td></tr>";
		}
		$("tbody").append(showhtml);
		$("#hid_op").val(data.op);
		$("#hid_time").val(data.time);
		$("#op").val(data.op);
		$("#time").val(data.time);
		$("#jls").html("共 " + data.pros.length + " 条记录");
	}
}

function searchhd() {
	commonAjax("/admin-getHd", callback, {
		op : $("#hid_op").val(),
		time : $("#hid_time").val()
	});
	function callback(data) {
		$("tbody").html("");
		var showhtml = "";
		for (var k = 0; k < data.answers.length; k++) {
			showhtml += "<tr><td><input type='checkbox' /></td><td><a target='_blank' href='/pro?proid="
					+ data.answers[k].problem.pid
					+ "&aid="
					+ data.answers[k].aid
					+ "'>"
					+ data.answers[k].aid
					+ "</a></td><td><a target='_blank' href='/pro?proid="
					+ data.answers[k].problem.pid
					+ "'>"
					+ data.answers[k].problem.ptitle + "</a></td>";
			showhtml += "<td><a target='_blank' href='/zhuye?id="
					+ data.answers[k].user.id
					+ "'>"
					+ data.answers[k].user.name
					+ "</a></td><td>"
					+ data.answers[k].aztsl
					+ "</td><td>"
					+ data.answers[k].afdsl
					+ "</td><td>"
					+ data.answers[k].ascsl
					+ "</td><td>"
					+ data.answers[k].score
					+ "</td><td>"
					+ data.answers[k].ahdrq
					+ "</td><td><button class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' dlx='hd' alt='"
					+ data.answers[k].aid
					+ "' onclick='admindelete(this)'><span class='am-icon-trash-o'></span> 删除</button></td></tr>";
		}
		$("tbody").append(showhtml);
		$("#hid_op").val(data.op);
		$("#hid_time").val(data.time);
		$("#op").val(data.op);
		$("#time").val(data.time);
		$("#jls").html("共 " + data.answers.length + " 条记录");
	}
}

function searchuserByName() {
	commonAjax("/admin-searchUser", callback, {
		name : $("#textOfUserName").val()
	});
	function callback(data) {
		$("tbody").html("");
		var showhtml = "";
		for (var k = 0; k < data.users.length; k++) {
			showhtml += "<tr><td><input type='checkbox' /></td><td><a target='_blank' href='/zhuye?id="
					+ data.users[k].id
					+ "'><img width='24' height='24' src='"
					+ data.users[k].img
					+ "' /></a></td><td><a target='_blank' href='/zhuye?id="
					+ data.users[k].id + "'>" + data.users[k].id + "</a></td>";
			showhtml += "<td>" + data.users[k].email
					+ "</td><td><a target='_blank' href='/zhuye?id="
					+ data.users[k].id + "'>" + data.users[k].name
					+ "</a></td><td>" + data.users[k].twsl + "</td><td>"
					+ data.users[k].hdsl + "</td></tr>";
		}
		$("tbody").append(showhtml);
		$("#jls").html("共 " + data.users.length + " 条记录");
	}
}

function searchHdByName() {
	commonAjax("/admin-searchHdByUser", callback, {
		name : $("#textOfUserName").val()
	});
	function callback(data) {
		$("tbody").html("");
		var showhtml = "";
		for (var k = 0; k < data.answers.length; k++) {
			showhtml += "<tr><td><input type='checkbox' /></td><td><a target='_blank' href='/pro?proid="
					+ data.answers[k].problem.pid
					+ "&aid="
					+ data.answers[k].aid
					+ "'>"
					+ data.answers[k].aid
					+ "</a></td><td><a target='_blank' href='/pro?proid="
					+ data.answers[k].problem.pid
					+ "'>"
					+ data.answers[k].problem.ptitle + "</a></td>";
			showhtml += "<td><a target='_blank' href='/zhuye?id="
					+ data.answers[k].user.id
					+ "'>"
					+ data.answers[k].user.name
					+ "</a></td><td>"
					+ data.answers[k].aztsl
					+ "</td><td>"
					+ data.answers[k].afdsl
					+ "</td><td>"
					+ data.answers[k].ascsl
					+ "</td><td>"
					+ data.answers[k].score
					+ "</td><td>"
					+ data.answers[k].ahdrq
					+ "</td><td><button class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' dlx='hd' alt='"
					+ data.answers[k].aid
					+ "' onclick='admindelete(this)'><span class='am-icon-trash-o'></span> 删除</button></td></tr>";
		}
		$("tbody").append(showhtml);
		$("#jls").html("共 " + data.answers.length + " 条记录");
	}
}

function searchtwBySsdx() {
	var url;
	if ($("#ssdx").val() == "1")
		url = "/admin-searchTw";
	else
		url = "/admin-searchUserTw";
	commonAjax(url, callback, {
		name : $("#textOfUserName").val()
	});
	function callback(data) {
		$("tbody").html("");
		var showhtml = "";
		for (var k = 0; k < data.pros.length; k++) {
			showhtml += "<tr><td><input type='checkbox' /></td><td><a target='_blank' href='/pro?proid="
					+ data.pros[k].pid
					+ "'>"
					+ data.pros[k].pid
					+ "</a></td><td><a target='_blank' href='/pro?proid="
					+ data.pros[k].pid
					+ "'>"
					+ data.pros[k].ptitle
					+ "</a></td>";
			showhtml += "<td><a target='_blank' href='/zhuye?id="
					+ data.pros[k].user.id
					+ "'>"
					+ data.pros[k].user.name
					+ "</a></td><td>"
					+ data.pros[k].ptcrq
					+ "</td><td>"
					+ data.pros[k].pbq
					+ "</td><td>"
					+ data.pros[k].phdsl
					+ "</td><td>"
					+ data.pros[k].pgzzsl
					+ "</td><td><button class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' dlx='tw' alt='"
					+ data.pros[k].pid
					+ "' onclick='admindelete(this)'><span class='am-icon-trash-o'></span> 删除</button></td></tr>";
		}
		$("tbody").append(showhtml);
		$("#hid_op").val(data.op);
		$("#hid_time").val(data.time);
		$("#op").val(data.op);
		$("#time").val(data.time);
		$("#jls").html("共 " + data.pros.length + " 条记录");
	}
}

function admindelete(obj) {
	var data = {
		'lx' : $(obj).attr("dlx"),
		'alt' : $(obj).attr("alt")
	};
	commonAjax('/admin-delete', callback, data);
	function callback(result) {
		layui.use('layer', function() {
			var layer = layui.layer;
			layer.open({
				title : '提示',
				offset : '100px',
				btn : [],
				content : result.msg
			})
		})
	}
}

function getTopic(){
	commonAjax('admin-getTopic', callback, {});
	function callback(data) {
		$("tbody").html("");
		var showhtml = "";
		for (var k = 0; k < data.topics.length; k++) {
			showhtml += "<tr><td><input type='checkbox' /></td><td><a target='_blank' href='/topic?tid="+data.topics[k].tid+"'>"+data.topics[k].tid+"</a></td><td><a target='_blank' href='/topic?tid="+data.topics[k].tid+"'><img width='24' height='24' src='"+data.topics[k].timg+"' /></a></td>";
			showhtml += "<td><a target='_blank' href='/topic?tid="+data.topics[k].tid+"'>"+data.topics[k].tname+"</a></td><td>"+data.topics[k].tjj+"</td><td><button class='am-btn am-btn-default am-btn-xs am-text-danger am-hide-sm-only' dlx='topic' alt='"+ data.topics[k].tid+ "' onclick='admindelete(this)'><span class='am-icon-trash-o'></span> 删除</button></td>";
		}
		$("tbody").append(showhtml);
		$("#jls").html("共 " + data.topics.length + " 条记录");
	}
}