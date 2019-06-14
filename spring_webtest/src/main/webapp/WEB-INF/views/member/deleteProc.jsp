<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_member.jsp" %> 



<!DOCTYPE html> 
<html> 
<head>
  <title>회원탈퇴</title>
  <meta charset="utf-8">

</head>
<body>

<br><br>

<div class="container">
<h1 class="col-sm-offset-2 col-sm-10" >회원탈퇴</h1>
	<div class="well well-lg">
		<c:choose>
			<c:when test="${flag}">탈퇴되었습니다. 자동 로그아웃됩니다.</c:when>
			<c:otherwise>회원탈퇴에 실패했습니다. 다시시도해주세요.</c:otherwise>
		</c:choose>  
  
		<div class="form-group">
    		<div class="col-sm-offset-2 col-sm-10">
      			<button class="btn-default btn" onclick="location.href='${root}/index.jsp'">확인</button>
      			<button class="btn-default btn" onclick="history.back()">취소</button>
    		</div>
  		</div>
	</div>
</div>
</body> 
</html> 

