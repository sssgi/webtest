console.log("*****Reply Module........");

var replyService = (function() {
	//이름없는 function()이 실행되어진 결과(json객체)를 받는 replyService
	function add(reply, callback, error) {
		console.log("add ireply...............");

		$.ajax({
			type : 'post',
			url : './ireply/create',
			data : JSON.stringify(reply),
			contentType : "application/json; charset=utf-8",
			success : function(result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}

	function getList(param, callback, error) {
		var imgno = param.imgno; //param.으로 값을 가져올 수 있음
		var sno = param.sno;
		var eno = param.eno;
		// alert(param.imgno);
	$.getJSON("./ireply/list/" + imgno + "/" + sno + "/" + eno + ".json", //비동기통신의 요청 uri, json형식을 선택
				function(data) { //결과를json형식으로 뿌림
					// alert(data);
					if (callback) { // callback이라는 함수가 있다면
						callback(data);//jason형식의 데이터를 인자로 받음. // 댓글 목록만 가져오는 경우
						// callback(data.replyCnt, data.list);= 댓글 숫자와 목록을 가져오는 경우
					}
				});

	}

	function getPage(param, callback, error) {
//		var nPage = param.nPage;
//		var nowPage = param.nowPage;
//		var col = param.col;
//		var word = param.word;
//		var imgno = param.imgno;

		$.ajax({
			type : 'get',
			url : "./ireply/page",
			data : param,
			contentType : "application/text; charset=utf-8",
			success : function(result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}

	function remove(rnum, callback, error) {
		$.ajax({
			type : 'delete',
			url : './ireply/' + rnum,
			success : function(deleteResult, status, xhr) {
				if (callback) {
					callback(deleteResult);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}

	function update(reply, callback, error) {

		console.log("rnum: " + reply.rnum);

		$.ajax({
			type : 'put',
			url : './ireply/' + reply.rnum,
			data : JSON.stringify(reply), // json객체지만 문자형식으로 데이터 보냄
			contentType : "application/json; charset=utf-8", //json형식으로 데이터 받음
			success : function(result, status, xhr) {
				if (callback) {
					callback(result);
				}
			},
			error : function(xhr, status, er) {
				if (error) {
					error(er);
				}
			}
		});
	}

	function get(rnum, callback, error) {

		$.get("./ireply/" + rnum + ".json", function(result) {

			if (callback) {
				callback(result);
			}

		}).fail(function(xhr, status, err) {
			if (error) {
				error();
			}
		});
	}

	;

	return {
		add : add, //key:add, function:add
		get : get,
		getList : getList,
		getPage : getPage,
		remove : remove,
		update : update
	}; //json객체를 return

})();
