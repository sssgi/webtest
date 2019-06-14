<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_bbs.jsp" %> 
<%	//boolean flag = (Boolean)request.getAttribute("flag"); %>

<!DOCTYPE html> 
<html> 
<head>
<style type="text/css">
  #red{
	color:red;
  }
</style>
 
</head>
<body>
<div class="container">
<br><br>
<h1 class="col-sm-offset-2 col-sm-10" >삭제</h1>
<div class= "well well-lg">
	<c:choose>
		<c:when test="${flag}">답변이 있는 글이므로 삭제할 수 없습니다.<br>
		<button class='btn' onclick='history.back()'>다시시도</button>
		</c:when>
		<c:otherwise>	
		<form class="form-horizontal" action="delete" method="post">
	  		<input type="hidden" name="bbsno" value="${param.bbsno}">
			<input type="hidden" name="col" value="${param.col}">
			<input type="hidden" name="word" value="${param.word }">
			<input type="hidden" name="nowPage" value="${param.nowPage }">
   		
	   		<div class="form-group">
	    		<label class="control-label col-sm-2" for="passwd">비밀번호</label>
	     		<div class="col-sm-6">
	       			<input type="password" name="passwd" id="passwd" class="form-control">
	     		</div>
	   		</div>

	   		<p id="red" class="col-sm-offset-2 col-sm-5">삭제하면 복구할 수 없습니다.</p>
	
	   		<div class="form-group"> 
	    		<div class="col-sm-offset-2 col-sm-10">
	      			<button type="submit" class="btn btn-default">삭제</button>
	      			<button type="reset" class="btn btn-default">취소</button>
	    		</div>
	  		</div>
		</form>
		</c:otherwise>
	</c:choose>
</div>
</div>
</body> 
</html> 

