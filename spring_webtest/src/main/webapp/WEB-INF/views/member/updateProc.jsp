<%@ page contentType="text/html; charset=UTF-8" %> 
<%@ include file = "/ssi/ssi_member.jsp" %>

<!DOCTYPE html> 
<html> 
<head>
<title>회원수정</title>
<meta charset="utf-8">

</head>
<body>
<br><br>
<div class="container">
<h1 class="col-sm-offset-2 col-sm-10" >회원수정 확인</h1>
	<div class="well well-lg">
		<c:choose>
			<c:when test="${flag }">정보를 수정했습니다.</c:when>
			<c:otherwise>정보를 수정하지 못했습니다.</c:otherwise>
		</c:choose>
	</div>
	<button class="btn-default btn" onclick="location.href='read.do?id=${id}'">회원정보</button>
	<button class="btn-default btn" onclick="history.back()">다시시도</button>
</div>
</body> 
</html> 

