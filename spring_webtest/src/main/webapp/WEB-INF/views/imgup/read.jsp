<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_img.jsp" %> 

<!DOCTYPE html> 
<html> 
<head>
<title>사진보기</title>
<meta charset="utf-8">
<script type="text/javascript">
  	function readGo(imgno){
  		var url = "./read.do";
  		url += "?imgno="+imgno;	
  		
  		location.href=url;
  	}
  	
   	function updateGo(){
		var url = "./update.do";
		url += "?imgno=${dto.imgno}";
		url += "&oldfile=${dto.fname}";
		location.href=url;
	}
   	
   	function deleteGo(){
		var url = "./delete.do";
		url += "?imgno=${dto.imgno}";
		location.href=url;
	}
  
</script>
<style>
	.curImg{
		margin-right:0;
		border-style:solid;
		border-width: 3px;
		border-color: red;
	}

	.td_padding{
		padding:5px 5px;
	}

	.td_padding img{width:89px; height:89px;}
	.glyphicon-chevron-left{color:#FF5675 !important; float:left !important; left:0 !important; margin-left:10px !important; padding:20px !important; position:absolute !important; top:45% !important; cursor:pointer !important; transition:all 0.5s !important;}
	.glyphicon-chevron-right{color:#FF5675 !important; float:right !important; right:0 !important; margin-right:10px !important; padding:20px !important; position:absolute !important; top:45% !important; cursor:pointer !important; transition:all 0.5s !important;}
	.glyphicon-chevron-left:hover, .glyphicon-chevron-right:hover{border-radius:50px !important; opacity: 0.3 !important; background-color:#ededed !important;}
	
	th{text-align:center !important; width:30%;}
	
</style>
</head>

<body>
<br><br><br>
<div class="container text-center">
<h1>사진</h1>
	<div style="display:inline-block;">
		<div style="position:relative; display:block; width:500px;">
			<c:if test="${currentLeftId>0 }"><span class="glyphicon glyphicon-chevron-left" onclick="readGo(${currentLeftId});"></span></c:if>
			<c:if test="${currentRightId>0 }"><span class="glyphicon glyphicon-chevron-right" onclick="readGo(${currentRightId});"></span></c:if>
			<img src="./storage/${dto.fname}" width="100%" class="img-thumbnail">				
		</div>
			
		<div style="display:block; width:500px;">
			<table style="width: 100%;">
	  			<tr>
	  			<c:forEach var="i" begin="0" end="4">
	  			<c:choose>
					<c:when test="${files[i]=='0'}">
					<td class="td_padding">
			   	  		<img style="opacity: 0.5;" src="./storage/default.jpg" width="100%">
					<td>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${noArr[i]==param.imgno}">
							<td class="td_padding">
					  			<a href="javascript:readGo('${noArr[i]}')">
								<img class="curImg" src="./storage/${files[i]}" width="100%" border="0">
				  	  			</a>
							</td>
							</c:when>
							<c:otherwise>
							<td class="td_padding">
								<a href="javascript:readGo('${noArr[i]}')">
								<img src="./storage/${files[i]}" width="100%" border="0">
								</a>
							</td>
							</c:otherwise>
					</c:choose>
					</c:otherwise>				  				
	  			</c:choose>
	  			</c:forEach>
				</tr>
			</table>
		</div>
			
		<div style="display:block; width:500px;">
			<table class="table table-bordered" style="width: 100%;">
				<tr>
					<th>성명</th>
					<td>${dto.name}</td>
				</tr>
				<tr>
					<th>제목</th>
					<td>${dto.title}</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>${dto.content}</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${dto.viewcnt}</td>
				</tr>
			</table>
		</div>
	</div>
		
	<br><br>
 
	<div class="form-group">
		<div class="col-sm-offset-3 col-sm-6 col-sm-offset-3">   
	    	<button class="btn-default btn" type="button" onclick="location.href='./list'">목록</button>
	    	<button class="btn-default btn" type="button" onclick="updateGo()">수정</button>      
	    	<button class="btn-default btn" type="button" onclick="deleteGo()">삭제</button>
		</div>
	</div>
	
</div>	
	<!-- 댓글시작 -->
	<br/>  
	<div class='container'>
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
  	<!-- ./ end container -->
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
    
    	<script type="text/javascript" src="${root }/js/ireply.js"></script>
	<!-- ajax를 처리하기 위함 -->
	
	<script type="text/javascript">
	$(document).ready(function () {
		  
		var imgno = '<c:out value="${imgno}"/>';
		var sno = '<c:out value="${sno}"/>';
		var eno = '<c:out value="${eno}"/>';
		var replyUL = $(".chat");
		  
		showList(); //댓글을 보여주기 위한
		
		function showList(){
		    replyService.getList({imgno:imgno,sno:sno,eno:eno}, function(list) {
		            
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
		 
		var nPage = '<c:out value="${nPage}"/>'; //imgupController에서 받아오는 데이터
		var nowPage = '<c:out value="${nowPage}"/>';
		var colx = '<c:out value="${col}"/>';
		var wordx = '<c:out value="${word}"/>';
		var replyPageFooter = $(".panel-footer");
		
		 
		function showReplyPage(){
			var param = "nPage="+nPage;
			
			param += "&nowPage="+nowPage;
			param += "&imgno="+imgno;
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
			        url += "&imgno=${imgno}";
			        url += "&rurl=../imgup/read";
			   
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
			      	 imgno:'<c:out value="${imgno}"/>'};
				
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


</body> 
</html> 

