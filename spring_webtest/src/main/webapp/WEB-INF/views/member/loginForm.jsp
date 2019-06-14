<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_member.jsp" %>

<!DOCTYPE html> 
<html> 
<head>
  <title>로그인 처리</title>
  <meta charset="utf-8">
  <script type="text/javascript">
  
	function findId(){
		var url = "${root}/member/idFind";
		wr = window.open(url, "아이디찾기", "width=500, height=500");
		wr.moveTo( ((window.screen.width - 500)/2), ((window.screen.height - 500)/2) );	  
	}
 
	function findPasswd(){
		var url = "${root }/member/passwdFind";
		wr = window.open(url, "비밀번호찾기", "width=500, height=500");
		wr.moveTo( ((window.screen.width - 500)/2), ((window.screen.height - 500)/2) );	  
	}
  
  
  </script>
</head>
<body>

<br><br>
<div class="container">
<h1 class="col-sm-offset-2 col-sm-10" >로그인</h1>
	<form class="form-horizontal" action="${root}/member/login" method="post">
		<input type="hidden" name="rurl" value="${param.rurl}">
		<input type="hidden" name="bbsno" value="${param.bbsno}">
		<input type="hidden" name="imgno" value="${param.imgno}">
		<input type="hidden" name="nowPage" value="${param.nowPage}">
		<input type="hidden" name="nPage" value="${param.nPage}">
		<input type="hidden" name="col" value="${param.col}">
		<input type="hidden" name="word" value="${param.word}">
		
  		<div class="form-group">
			<label class="control-label col-sm-2" for="id">아이디</label>
    		<div class="col-sm-6">
      			<input type="text" name="id" id="id" class="form-control" required="required" value="${c_id_val }">
      			      			
      			<c:choose>
      				<c:when test="${c_id=='Y' }"> 
      	 				<input type='checkbox' name='c_id' value='Y' checked='checked'/>ID저장
      	 			</c:when>
					<c:otherwise>
					 	<input type='checkbox' name='c_id'  value='Y'/>ID저장 
					</c:otherwise> 
				</c:choose>
    		</div>
  		</div>
  
  		<div class="form-group">
   			<label class="control-label col-sm-2" for="passwd">비밀번호</label>
    		<div class="col-sm-6">
      			<input type="password" name="passwd" id="passwd" class="form-control" required="required">
    		</div>
  		</div>
  
  		<div class="form-group">
    		<div class="col-sm-offset-2 col-sm-">
			    <button class="btn-default btn">로그인</button>
			    <button class="btn-default btn" type="button" onclick="location.href='${root}/member/agreement.do'">회원가입</button>
			    <button class="btn-default btn" type="button" onclick="findId();">아이디찾기</button>
			    <button class="btn-default btn" type="button" onclick="findPasswd();">비밀번호찾기</button>
    		</div>
		</div>
	</form>
</div>
</body> 
</html> 

