<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_member.jsp" %> 

<!DOCTYPE html> 
<html> 
<head>
<title>회원정보</title>
<meta charset="urf-8">
<style type="text/css">
	th,td{
 		text-align: center !important;
	}	  
</style>
<script type="text/javascript">
	$(document).ready(function(){
	 var modal = '${msg}'; //Controller에서 가져온 데이터
	 checkModal(modal); //modal생성
	 
	 //뒤로가기할때 modal 안보여주는 코드 1
	 history.replaceState({},null,null);
	 
	 function checkModal(modal){ //modal 생성함수 선언
	     if(history.state) return; //코드2
	     if('${msg}'){ //javascript에서는 데이터가 존재하면 조건 true
	         $(".modal-body").html(modal);
	         $("#myModal").modal("show");
	     }
	   }
	});
</script>
<script type="text/javascript">
  	function filedown(){
  		var url = "${root}/download";
  		url += "?dir=/member/storage";
  		url += "&filename=${dto.fname}";
  		location.href=url;	
  	}
	  
	function del(){
		var url = "delete";
	  	url +="?id=${dto.id}";
	  	location.href = url;  		
	}
	  	
	function update(){
		var url = "update";
		url +="?id=${dto.id}";
		url +="&oldfile=${dto.fname} ";
	  	location.href = url;
	}	
	  	  
  	function updatePw(){
	  	var url = "updatePw";
		url +="?id=${dto.id}";
		location.href = url;	
	}
	  
 	function updateFile(){
  		var url = "updateFile";
  		url += "?id=${dto.id}";
  		url += "&oldfile=${dto.fname}"
  		location.href = url;
  	}
  	
  	function listM(){
  		var url = "${root}/admin/list";
  		location.href = url;  		
  	}

</script>

</head>
<body>

<br><br>
<div class="container">
<h1 class="col-sm-offset-2 col-sm-10" >회원정보</h1>
	<table class="table table-bordered">
    	<tr>
      		<td colspan="2" style="text-align: center;">
        	<img src ="./storage/${dto.fname}" class="img-rounded" width="350px" height=350px">
      		</td>
    	</tr>
    
	    <tr>
	    	<th>아이디</th>
	    	<td>${dto.id}</td>
	    </tr>
	    
    	<tr>
	    	<th>성명</th>
	    	<td>${dto.mname}</td>
	    </tr>          
	  
	    <tr>
	    	<th>전화번호</th>
	   		<td>${dto.id}</td>
	    </tr>
	        
    	<tr>
      		<th>이메일</th>
      		<td>${dto.email}</td>
    	</tr>     
    
	    <tr>
	    	<th>우편번호</th>
	    	<td>${dto.zipcode}</td>
	    </tr>     
	  
	    <tr>
	    	<th>주소</th>
	    	<td>${dto.address1} ${dto.address2 }</td>
	    </tr>     
	  
	    <tr>
	    	<th>직업</th>
	    	<td>직업코드:${dto.job} (${util:jobName(dto.job)})</td>
	    </tr>     
	  
	    <tr>
	    	<th>날짜</th>
	    	<td>${dto.mdate}</td>
	    </tr>     
	  
	</table>
  
	<div>
	    <button class="btn btn-default" onclick="update()">정보수정</button>
	    <button class="btn btn-default" onclick="del()">회원탈퇴</button>  
	    <c:if test="${not empty dto.id && grade !='A' }">	 
	    <button class="btn btn-default" onclick="updateFile()">사진수정</button>
	    <button class="btn btn-default" onclick="updatePw()">패스워드수정</button>
	    <button class="btn btn-default" onclick="filedown()">다운로드</button>
	    </c:if>
	    <c:if test="${not empty dto.id && grade =='A' }">
	    <button class="btn btn-default" onclick="listM()">회원목록</button> 
		</c:if>
	</div>
	 
	<div class="container">
	  <!-- Modal -->
	  <div class="modal fade" id="myModal" role="dialog">
	    <div class="modal-dialog modal-sm">
	      <div class="modal-content">
	        <div class="modal-header">
	      
	          <h4 class="modal-title">알림 메세지!</h4>
	        </div>
	        <div class="modal-body">
	          <p>This is a small modal.</p>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-default" data-dismiss="modal" >Close</button>
	        </div>
	      </div>
	    </div>
	  </div>
	</div>
</div>
</body> 
</html> 

