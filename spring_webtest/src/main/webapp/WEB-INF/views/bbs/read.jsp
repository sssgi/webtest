<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_bbs.jsp" %> 

<%
	//BbsDTO dto = (BbsDTO)request.getAttribute("dto");
%>


<!DOCTYPE html> 
<html> 
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <script type="text/javascript">
    function updateM(){
      var url = "update";
      url +="?bbsno=${dto.bbsno}";
      url += "&col=${param.col}";
      url += "&word=${param.word}";
      url += "&nowPage=${param.nowPage}"
      
      location.href=url;   	
    }
    
    function deleteM(){
    var url = "delete";
    url +="?bbsno=${dto.bbsno}";
    url += "&col=${param.col}";
    url += "&word=${param.word}";
    url += "&nowPage=${param.nowPage}"
    
    location.href=url;   	
    }

    
    function replyM(){
    var url = "reply";
    url +="?bbsno=${dto.bbsno}";
    
    location.href=url;   	
  }
    
    function listM(){
    
    var url = "list";
    url += "?col=${param.col}"; //controller가 model에 저장한 데이터를 받아온게 X(model에 저장한 데이터를 받을때는 param을 쓰지않음)
    url += "&word=${param.word}";
    url += "&nowPage=${param.nowPage}"
    
    location.href=url;
    
    }
  
  </script>
  
 
</head>
<body>

<br><br>

<div class="container">

<H2>조회</H2>
	<div class="panel panel-default">
		<div class="panel-heading">번호</div>
		<div class="panel-body">${dto.bbsno}</div>
	
		<div class="panel-heading">제목</div>
		<div class="panel-body">${dto.title}</div>  
		  
		<div class="panel-heading">내용</div>
		<div class="panel-body">${dto.content}</div>
		
		<div class="panel-heading">작성자</div>
		<div class="panel-body">${dto.wname}</div>
		
		<div class="panel-heading">조회수</div>
		<div class="panel-body">${dto.viewcnt}</div>  
		
		<div class="panel-heading">날짜</div>
		<div class="panel-body">${dto.wdate}</div>

	</div>
	

	<div>
		<button type="button" class="btn" onclick="location.href='./create'">등록</button>
		<button type="button" class="btn" onclick="updateM();">수정</button>
		<button type="button" class="btn" onclick="deleteM();">삭제</button>
		<button type="button" class="btn" onclick="replyM();">답변</button>
		<button type="button" class="btn" onclick="listM();">목록</button>
	</div>
  
  <!-- 댓글시작 -->
	<br/>  
	<div class='row'>
		<div class="col-lg-12">
     		<!-- /.panel -->
			<div class="panel panel-default">

				<div class="panel-heading">
				 	<i class="fa fa-comments fa-fw"></i> 댓글
				 	<button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>New Reply</button>
				 </div>      

				<!-- /.panel-heading -->
				<div class="panel-body">        
					<ul class="chat list-group">
   					<li class="left clearfix" data-rno="12">
  						<div>
     						<div class="header">
						    	<strong class="primary-font">user1</strong>
						    	<small class="pull-right text-muted">2019-05-12</small>
							</div>
							
					    	<p>Good job!</p>
     					
     					</div>
   					</li>
  					</ul>
  					<!-- ./ end ul -->
				</div>
				
				<!-- /.panel .chat-panel -->
 				<div class="panel-footer"></div>
 
			</div>
		</div>
  	<!-- ./ end row -->
	</div>
	<!-- 댓글 끝 -->
	
	
	<!-- Modal -->	
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    	<div class="modal-dialog">
        	<div class="modal-content">
        		<div class="modal-header">
            		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
              		<h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            	</div>
            
            	<div class="modal-body">
              		<div class="form-group">
                		<label>내용</label> 
                		<textarea cols="10" rows="3" class="form-control" name='content'>New Reply!!!!</textarea> 
              		</div>      
              		<div class="form-group">
                		<label>아이디</label> 
                		<input class="form-control" name='id' value='${sessionScope.id}'><!--로그인 했을때 아이디값을 받음. modal을이용해서 수정이나 생성을 할때 세션에 아이디값이 존재해야함(로그인) -->
              		</div>
	              	<div class="form-group">
		                <label>등록날짜</label> 
		                <input class="form-control" name='regdate' value='2018-01-01 13:13'> <!--regdate는 글이 생성된 날짜를 받음  -->
	              	</div>
      			</div>
			    <div class="modal-footer">
			        <button id='modalModBtn' type="button" class="btn btn-warning">Modify</button>
			        <button id='modalRemoveBtn' type="button" class="btn btn-danger">Remove</button>
			        <button id='modalRegisterBtn' type="button" class="btn btn-primary">Register</button>
			        <button id='modalCloseBtn' type="button" class="btn btn-default">Close</button>
			    </div>          
			</div>
          	<!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
	</div>
    <!-- /.modal -->
       
	<script type="text/javascript" src="${root }/js/breply.js"></script>
	<!-- ajax를 처리하기 위함 -->
	
	<script type="text/javascript">
	$(document).ready(function () {
		  
		var bbsno = '<c:out value="${bbsno}"/>';
		var sno = '<c:out value="${sno}"/>';
		var eno = '<c:out value="${eno}"/>';
		var replyUL = $(".chat");
		  
		showList(); //댓글을 보여주기 위한
		
		function showList(){
		    replyService.getList({bbsno:bbsno,sno:sno,eno:eno}, function(list) {
		            
				var str="";
		     
		    	if(list == null || list.length == 0){
		     		return;
		     	}
		     
		     	for (var i = 0, len = list.length || 0; i < len; i++) {
		     		str +="<li class='list-group-item' data-rnum='"+list[i].rnum+"'>";
		    	 	str +="<div><div class='header'><strong class='primary-font'>"+list[i].id+"</strong>"; 
		    	 	str +="<small class='pull-right text-muted'>"+list[i].regdate+"</small></div>";
		    	 	str +=replaceAll(list[i].content,'\n','<br>')+"</div></li>";
		     	}
		 
		     	replyUL.html(str);
		     
		     	showReplyPage();	 
		 
			});//end getList function 호출   
		     
		}//end showList	  
		
		function replaceAll(str, searchStr, replaceStr) {		
			
			return str.split(searchStr).join(replaceStr);
		
		}
		 
		var nPage = '<c:out value="${nPage}"/>'; //bbsController에서 받아오는 데이터
		var nowPage = '<c:out value="${nowPage}"/>';
		var colx = '<c:out value="${col}"/>';
		var wordx = '<c:out value="${word}"/>';
		var replyPageFooter = $(".panel-footer");
		
		 
		function showReplyPage(){
			var param = "nPage="+nPage;
			
			param += "&nowPage="+nowPage;
			param += "&bbsno="+bbsno;
			param += "&col="+colx;
			param += "&word="+wordx;
							 
			replyService.getPage(param, function(paging) {
		 
		 		console.log(paging);
		 
		    	var str ="<div><small class='text-muted'>"+paging+"</small></div>";
		    	replyPageFooter.html(str);
		  
			});
		}//end showReplyPage
		
		
		var modal = $(".modal");
		var modalInputContent = modal.find("textarea[name='content']");
		var modalInputId = modal.find("input[name='id']");
		var modalInputRegDate = modal.find("input[name='regdate']");
		var modalModBtn = $("#modalModBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");
		var modalRegisterBtn = $("#modalRegisterBtn");
	
		//댓글 조회 클릭 이벤트 처리 
		$(".chat").on("click", "li", function(e){
		   
			var rnum = $(this).data("rnum"); //this = li
		   
		    //alert(rnum)
		    replyService.get(rnum, function(reply){
		   
				modalInputContent.val(reply.content); //var:데이터를 담는다는 의미
		    	modalInputId.closest("div").hide();
		    	modalInputRegDate.closest("div").hide();
		    	modal.data("rnum", reply.rnum);
		     
		    	modal.find("button[id !='modalCloseBtn']").hide();
		     
		     	if('${sessionScope.id}'==reply.id){
		        	modalModBtn.show();
		       		modalRemoveBtn.show();
		     	}
		     	
		     	$(".modal").modal("show");
		         
			});
		});
		
			 
		$("#modalCloseBtn").on("click", function(e){
		 
			modal.modal('hide');
		});
		
		
		modalModBtn.on("click", function(e){
		    var reply = {rnum:modal.data("rnum"), content: modalInputContent.val()};
		    //alert(reply.rnum);
		    replyService.update(reply, function(result){
		          
			    alert(result);
			    modal.modal("hide");
			    showList(); //갱신된 댓글 목록 가져오기
			      
		    });
		    
		});//modify 
		 
		 
		modalRemoveBtn.on("click", function (e){
		    
		    var rnum = modal.data("rnum");
		    
		    replyService.remove(rnum, function(result){
		          
		        alert(result);
		        modal.modal("hide");
		        showList();
		        
			});
		    
		});//remove
		
		$("#addReplyBtn").on("click", function(e){
			  
			if('${sessionScope.id}'==''){ //로그인이 안된상태
				
				if(confirm("로그인을 해야 댓글을 쓸수 있습니다.")) {
			        var url = "../member/login";
			       
			        url += "?col=${col}";
			        url += "&word=${word}";
			        url += "&nowPage=${nowPage}";
			        url += "&nPage=${nPage}";
			        url += "&bbsno=${bbsno}";
			        url += "&rurl=../bbs/read";
			   
			        location.href = url;
			    }
				
			}else{ //로그인이 된 상태
			 
				modalInputContent.val("");
				modalInputId.closest("div").hide();
				modalInputRegDate.closest("div").hide();
				modal.find("button[id !='modalCloseBtn']").hide();
				
				modalRegisterBtn.show();
  
				$(".modal").modal("show");
			  
			  
			}
		});
			 
			 
			modalRegisterBtn.on("click",function(e){
			  
				if(modalInputContent.val()==''){
					alert("댓글을 입력하세요")
					return ;
				}

				var reply = 
					{content: modalInputContent.val(),
			   		 id:'<c:out value="${sessionScope.id}"/>',
			      	 bbsno:'<c:out value="${bbsno}"/>'};
				
				//alert(reply.content);
				
				//호출시작
				replyService.add(reply, function(result){
  
 					alert(result);
  
 					modal.find("input").val("");
  					modal.modal("hide");
  
  					//showList(1);
  					showList();
			    
				}); //end add //호출끝
			  
			}); //end modalRegisterBtn.on	
		
				 
	}); //end $(document).ready
</script>
	
   
</div>
</body> 
</html> 

